package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.constant.ProductStatusConstants;
import com.ruoyi.common.constant.RedisKeyConstans;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.ProductCategoryType;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.ProductDetailVO;
import com.ruoyi.system.domain.vo.ProductVO;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.*;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Resource
    private ProductSkuServiceImpl productSkuService;

    @Resource
    private RedisCache redisCache;

    @Override
    public Page<ProductDTO> getPage(ProductQueryParamDTO queryParam) {
        Page<ProductDTO> page = new Page<>(queryParam.getPage(), queryParam.getLimit());
        Page<ProductDTO> ret = baseMapper.page(page, queryParam);
        if (ret.getRecords().isEmpty()) {
            return ret;
        }

        //规格属性
        List<String> countryCodeList = new ArrayList<>();
        for (ProductDTO productDTO : ret.getRecords()) {
            ProductSkuDTO productSkuDTO = JSON.parseObject(productDTO.getSkuAttr(), ProductSkuDTO.class);
            productDTO.setSkuList(Collections.singletonList(productSkuDTO));

            countryCodeList.add(productSkuDTO.getCountyCode());
        }

        Map<String, Long> stockEstimateMap = getStockMapByCountyCode(countryCodeList);
        for (ProductDTO productDTO : ret.getRecords()) {
            List<ProductSkuDTO> skuList = productDTO.getSkuList();
            skuList.forEach(x -> {
                x.setStock(stockEstimateMap.get(x.getCountyCode()));
                x.setPrice(BigDecimal.valueOf(x.getPrice()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue());
            });
            productDTO.setSkuList(skuList);
        }

        return ret;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Product create(ProductDTO productDTO) {
        long id = IdWorker.getId();
        List<ProductSku> productSkus = new ArrayList<>();
        for (ProductSkuDTO item : productDTO.getSkuList()) {
            ProductSku productSku = new ProductSku();
            BeanUtil.copyProperties(item, productSku);
            productSku.setPrice(BigDecimal.valueOf(item.getPrice()).multiply(BigDecimal.valueOf(100L)).longValue());
            productSku.setProductId(id);
            productSkus.add(productSku);
        }
        productSkuService.saveBatch(productSkus);

        Product product = setProduct(productDTO, id);
        product.setOperatorUser(productDTO.getOperatorUser());
        product.setOperatorUserId(productDTO.getOperatorUserId());
        save(product);

        return product;
    }

    private Product setProduct(ProductDTO productDTO, long id) {
        Product product = new Product();
        product.setProductId(id);
        product.setName(productDTO.getName());
        product.setAppType(productDTO.getAppType());
        product.setCategoryId(productDTO.getCategoryId());
        product.setPic(productDTO.getPic());
        product.setAlbumPics(productDTO.getAlbumPics());
        product.setStatus(productDTO.getStatus());
        product.setIsShow(productDTO.getIsShow());
        product.setIntro(productDTO.getIntro());
        product.setSkuAttr(getOneSkuAttr(id));
        return product;
    }

    private String getOneSkuAttr(long productId) {
        ProductSku productSku = getOneSkuByProductId(productId);
        SkuAttrDTO skuDTO = new SkuAttrDTO();
        BeanUtils.copyProperties(productSku, skuDTO);
        return JSON.toJSONString(skuDTO);
    }

    public ProductSku getOneSkuByProductId(long productId) {
        return productSkuService.getOne(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId)
                .orderByAsc(ProductSku::getPrice).last("limit 1"));
    }

    @Override
    public R<ProductDTO> detail(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "id参数错误"));
        }

        ProductDTO ret = new ProductDTO();
        Product product = getOneNormalProductById(id);
        if (ObjectUtils.isEmpty(product)) {
            return R.fail(ErrInfoConfig.getDynmic(11008));
        }

        BeanUtils.copyProperties(product, ret);

        List<String> countryCodeList = new ArrayList<>();
        //设置商品规格信息
        List<ProductSkuDTO> skuList = new ArrayList<>();
        List<ProductSku> productSkuList = productSkuService.list(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id));
        for (ProductSku productSku : productSkuList) {
            ProductSkuDTO productSkuDTO = new ProductSkuDTO();
            BeanUtils.copyProperties(productSku, productSkuDTO);

            double price = BigDecimal.valueOf(productSku.getPrice()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();
            productSkuDTO.setPrice(price);
            skuList.add(productSkuDTO);

            countryCodeList.add(productSku.getCountyCode());
        }

        Map<String, Long> stockEstimateMap = getStockMapByCountyCode(countryCodeList);
        skuList.forEach(x -> x.setStock(stockEstimateMap.get(x.getCountyCode())));
        ret.setSkuList(skuList);

        return R.ok(ret);
    }

    //获取服务支撑量
    public Map<String, Long> getStockMapByCountyCode(List<String> countryCodeList) {
        countryCodeList = countryCodeList.stream().distinct().collect(Collectors.toList());
        Map<String, Long> ret = new HashMap<>();
        for (String countryCode : countryCodeList) {
            ret.put(countryCode, getCountryBusinessEstimate(countryCode));
        }
        return ret;
    }

    public long getCountryBusinessEstimate(String countryCode) {
        String key = RedisKeyConstans.COUNTRY_BUSINESS_ESTIMATE + countryCode;
        Long usersNum = redisCache.getCacheObject(key);
        if (null != usersNum) {
            return usersNum;
        }

        usersNum = getCountryBusinessEstimateByClient(countryCode);
        redisCache.setCacheObject(key, usersNum, 10, TimeUnit.MINUTES);
        return usersNum;
    }

    public long getCountryBusinessEstimateWithoutCache(String countryCode, List<String> robotsCountryCodes) {
        String key = RedisKeyConstans.COUNTRY_BUSINESS_ESTIMATE + countryCode;

        long usersNum = getCountryBusinessEstimateByClient(countryCode, robotsCountryCodes);
        redisCache.setCacheObject(key, usersNum, 10, TimeUnit.MINUTES);
        return usersNum;
    }

    private long getCountryBusinessEstimateByClient(String countryCode) {
        return getCountryBusinessEstimateByClient(countryCode, new ArrayList<>());
    }


    private long getCountryBusinessEstimateByClient(String countryCode, List<String> robotsCountryCodes) {
        long startTime = System.currentTimeMillis();

        CountryBusinessEstimateInput input = new CountryBusinessEstimateInput();
        input.setPriorityOp(0);
        input.setRobotsCountryCode(robotsCountryCodes);
        input.setVcToCountryCode(countryCode);
        try {
            UtTouchResult<CountryBusinessEstimateOutput> result = UtTouchJoinRoomClient.countryBusinessEstimate(input);

            log.info("getCountryBusinessEstimate-ByClient-运行时间: {}}", (System.currentTimeMillis() - startTime) / 1000);
            if (!ObjectUtils.isEmpty(result.getData())) {
                return result.getData().getUsersNum();
            }
        } catch (Exception e) {
            log.error("getCountryBusinessEstimate: {}, {}", countryCode, e);
        }
        return 0L;
    }

    private Product getOneNormalProductById(long id) {
        return getOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getProductId, id).eq(Product::getIsDel, ProductStatusConstants.NORMAL).last("limit 1"));
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public R<String> update(ProductDTO productDTO) {
        if (ObjectUtils.isEmpty(productDTO.getProductId())) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "id参数错误"));
        }

        Product product = getOneNormalProductById(productDTO.getProductId());
        if (ObjectUtils.isEmpty(product)) {
            return R.fail(ErrInfoConfig.getDynmic(11008));
        }

        List<ProductSku> productSkus = getUpdateProductSku(productDTO.getProductId(), productDTO.getSkuList());
        productSkuService.saveOrUpdateBatch(productSkus);

        Product entity = setProduct(productDTO, product.getProductId());
        updateById(entity);

        return R.ok();
    }

    private List<ProductSku> getUpdateProductSku(Long productId, List<ProductSkuDTO> skuList) {
        List<ProductSku> productSkus = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO : skuList) {

            long price = BigDecimal.valueOf(productSkuDTO.getPrice()).multiply(BigDecimal.valueOf(100L)).longValue();
            if (ObjectUtils.isEmpty(productSkuDTO.getId())) {
                ProductSku productSku = new ProductSku();
                productSku.setProductId(productId);
                productSku.setCountyCode(productSkuDTO.getCountyCode());
                productSku.setCountyName(productSkuDTO.getCountyName());
                productSku.setPrice(price);
                productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                productSkus.add(productSku);
            } else {
                ProductSku productSku = productSkuService.getById(productSkuDTO.getId());
                if (!ObjectUtils.isEmpty(productSku)) {
                    productSku.setCountyCode(productSkuDTO.getCountyCode());
                    productSku.setCountyName(productSkuDTO.getCountyName());
                    productSku.setPrice(price);
                    productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                    productSkus.add(productSku);
                }
            }
        }

        return productSkus;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public R<String> handleUpdatePrice(UpdateProductDTO productDTO) {
        Product product = getOneNormalProductById(productDTO.getProductId());
        if (ObjectUtils.isEmpty(product)) {
            return R.fail(ErrInfoConfig.getDynmic(11008));
        }
        long price = BigDecimal.valueOf(productDTO.getPrice()).multiply(BigDecimal.valueOf(100L)).longValue();

        ProductSkuDTO skuAttrDTO = JSON.parseObject(product.getSkuAttr(), ProductSkuDTO.class);
        skuAttrDTO.setPrice((double) price);
        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        updateProduct.eq("product_id", product.getProductId()).set("sku_attr", JSON.toJSONString(skuAttrDTO));
        update(null, updateProduct);


        UpdateWrapper<ProductSku> updateSku = new UpdateWrapper<>();
        updateSku.eq("id", skuAttrDTO.getId()).set("price", price);
        productSkuService.update(null, updateSku);

        return R.ok();
    }

    @Override
    public R<String> batchUpdateStatus(BatchUpdateProductStatusDTO productDTO) {
        baseMapper.batchUpdateStatus(productDTO.getStatus(), productDTO.getProductIds());
        return R.ok();
    }

    @Override
    public R<String> batchUpdateShowStatus(BatchUpdateProductShowStatusDTO productDTO) {
        baseMapper.batchUpdateShowStatus(productDTO.getShowStatus(), productDTO.getProductIds());
        return R.ok();
    }

    @Override
    public R<String> batchDeleteProducts(BatchUpdateProductDTO productDTO) {
        baseMapper.batchDel(productDTO.getProductIds());
        return R.ok();
    }

    @Override
    public Page<ProductVO> getNormalList(ProductRequest request) {
        Page<ProductVO> ret = new Page<>(request.getPage().longValue(), request.getLimit().longValue());
        Page<Product> productPage = getNormalProduct(request);
        if (productPage.getRecords().isEmpty()) {
            return ret;
        }

        List<String> countryCodeList = new ArrayList<>();
        List<ProductVO> productVOList = new ArrayList<>();
        BeanUtils.copyProperties(productPage, ret);
        for (Product product : productPage.getRecords()) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVO.setSkuAttr(JSON.parseObject(product.getSkuAttr(), ProductSkuDTO.class));
            productVOList.add(productVO);

            countryCodeList.add(productVO.getSkuAttr().getCountyCode());
        }

        Map<String, Long> stockEstimateMap = getStockMapByCountyCode(countryCodeList);
        for (ProductVO productVO : productVOList) {
            ProductSkuDTO skuAttr = productVO.getSkuAttr();
            skuAttr.setStock(stockEstimateMap.get(skuAttr.getCountyCode()));
            skuAttr.setPrice(BigDecimal.valueOf(skuAttr.getPrice()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue());
            productVO.setSkuAttr(skuAttr);
        }

        ret.setRecords(productVOList);
        return ret;
    }

    private Page<Product> getNormalProduct(ProductRequest request) {
        Page<Product> page = new Page<>(request.getPage().longValue(), request.getLimit().longValue());

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtils.isEmpty(request.getCategoryId())) {
            wrapper.eq(Product::getCategoryId, request.getCategoryId());
        }
        if (!ObjectUtils.isEmpty(request.getName())) {
            wrapper.like(Product::getName, request.getName());
        }
        wrapper.eq(Product::getStatus, ProductStatusConstants.LISTING);
        wrapper.eq(Product::getIsShow, ProductStatusConstants.SHOW);
        wrapper.eq(Product::getIsDel, ProductStatusConstants.NORMAL);
        wrapper.orderByDesc(Product::getCreateDate);
        wrapper.select(Product::getProductId, Product::getCategoryId, Product::getAppType, Product::getName, Product::getAlbumPics, Product::getPic, Product::getIntro, Product::getSkuAttr);
        return page(page, wrapper);
    }

    @Override
    public R<ProductDetailVO> getProductDetailBySkuId(Long skuId) {
        ProductDetailVO ret = new ProductDetailVO();

        ProductSku productSku = productSkuService.getById(skuId);
        if (ObjectUtils.isEmpty(productSku)) {
            return R.fail(ErrInfoConfig.getDynmic(11007));
        }

        Product product = getOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getProductId, productSku.getProductId())
                .eq(Product::getStatus, ProductStatusConstants.LISTING)
                .eq(Product::getIsShow, ProductStatusConstants.SHOW)
                .eq(Product::getIsDel, ProductStatusConstants.NORMAL)
                .last("limit 1"));
        if (ObjectUtils.isEmpty(product)) {
            return R.fail(ErrInfoConfig.getDynmic(11008));
        }
        BeanUtils.copyProperties(product, ret);

        //设置该商品sku属性, 目前只有一个属性 直接使用
        List<ProductSkuDTO> skuList = new ArrayList<>();
        ProductSkuDTO productSkuDTO = new ProductSkuDTO();
        BeanUtils.copyProperties(productSku, productSkuDTO);
        double price = BigDecimal.valueOf(productSku.getPrice()).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();
        productSkuDTO.setPrice(price);
        skuList.add(productSkuDTO);

        List<String> countryCodeList = new ArrayList<>();
        countryCodeList.add(skuList.get(0).getCountyCode());
        Map<String, Long> stockEstimateMap = getStockMapByCountyCode(countryCodeList);
        skuList.forEach(x -> x.setStock(stockEstimateMap.get(x.getCountyCode())));

        ret.setSkuList(skuList);

        return R.ok(ret);
    }


    @Override
    public void syncStocks(String countryCode) {
        this.getCountryBusinessEstimateWithoutCache(countryCode, new ArrayList<>());
    }


    @Override
    public List<Product> queryEnableProduct() {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Product::getIsDel, 0)
                .eq(Product::getCategoryId, ProductCategoryType.PULL_PEOPLE.getId());
        return this.list(queryWrapper);
    }

    @Override
    public void syncAllStocks() {
        log.info("同步商品支撑量开始");
        List<Product> products = this.queryEnableProduct();

        // 获取所有商品的国家
        List<String> productCountryCodes = products.stream()
                .map(Product::getProductSku)
                .filter(Objects::nonNull)
                .map(ProductSku::getCountyCode)
                .distinct()
                .collect(Collectors.toList());
        log.info("待同步商品支撑量国家列表 {}", JSON.toJSONString(productCountryCodes));

        log.info("获取优先国家列表");
        List<UtTouchCountryData> targetCountries =
                CollectionUtils.emptyIfNull(productCountryCodes).stream()
                        .map(UtTouchCountryData::new)
                        .collect(Collectors.toList());
        CountryBusinessPriorityRuleReq input = new CountryBusinessPriorityRuleReq();
        input.setPriorityRobots(1);
        input.setRobotsCountryList(targetCountries);
        UtTouchResult<PriorityTarget> result = UtTouchJoinRoomClient.countryBusinessPriorityCountryRule(input);
        log.info("获取优先国家列表完成 {} {}", JSON.toJSONString(input), JSON.toJSONString(result));

        // 将响应结果封装成Map
        final Map<String, List<String>> countryPriorities = Optional.of(result).map(UtTouchResult::getData).map(PriorityTarget::getTargetList).orElse(new ArrayList<>())
                .stream().collect(Collectors.toMap(PriorityTarget.TargetList::getCountryCode, PriorityTarget.TargetList::getPriorityRobots, (x, y) -> x));

        // 查询商品国家的库存(支撑量)信息 - 异步操作
        CollectionUtils.emptyIfNull(productCountryCodes).forEach(it ->
                CompletableFuture.runAsync(() -> this.getCountryBusinessEstimateWithoutCache(it, countryPriorities.getOrDefault(it, new ArrayList<>())))
        );
        log.info("同步商品支撑量完成");
    }
}
