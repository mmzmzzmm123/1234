package com.ruoyi.system.service.impl;

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
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.ProductDetailVO;
import com.ruoyi.system.domain.vo.ProductVO;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.CountryBusinessEstimateInput;
import com.ruoyi.system.extend.data.CountryBusinessEstimateOutput;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            List<ProductSkuDTO> skuList = new ArrayList<>();
            ProductSkuDTO productSkuDTO = JSON.parseObject(productDTO.getSkuAttr(), ProductSkuDTO.class);
            skuList.add(productSkuDTO);
            productDTO.setSkuAttr("");
            productDTO.setSkuList(skuList);

            countryCodeList.add(productSkuDTO.getCountyCode());
        }

        Map<String, Long> stockEstimateMap = getStockMapByCountyCode(countryCodeList);
        for (ProductDTO productDTO : ret.getRecords()) {
            List<ProductSkuDTO> skuList = productDTO.getSkuList();
            skuList.forEach(x -> x.setStock(stockEstimateMap.get(x.getCountyCode())));
            productDTO.setSkuList(skuList);
        }

        return ret;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public R<Product> create(ProductDTO productDTO) {
        long count = count(new LambdaQueryWrapper<Product>().eq(Product::getName, productDTO.getName()));
        if (count > 0) {
            return R.fail(ErrInfoConfig.getDynmic(11012));
        }

        long id = IdWorker.getId();
        List<ProductSku> productSkus = new ArrayList<>();
        for (ProductSkuDTO item : productDTO.getSkuList()) {
            ProductSku productSku = new ProductSku();
            BeanUtils.copyProperties(item, productSku);
            productSku.setProductId(id);
            productSkus.add(productSku);
        }
        productSkuService.saveBatch(productSkus);

        Product product = setProduct(productDTO, id);
        product.setOperatorUser(productDTO.getOperatorUser());
        product.setOperatorUserId(productDTO.getOperatorUserId());
        save(product);

        return R.ok(product);
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
        product.setSkuAttr(getSkuAttr(id));
        return product;
    }

    private String getSkuAttr(long productId) {
        ProductSku productSku = productSkuService.getOne(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId)
                .orderByAsc(ProductSku::getPrice).last("limit 1"));
        SkuAttrDTO skuDTO = new SkuAttrDTO();
        BeanUtils.copyProperties(productSku, skuDTO);
        return JSON.toJSONString(skuDTO);
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
            if (!ret.containsKey(countryCode)) {
                ret.put(countryCode, getCountryBusinessEstimate(countryCode));
            }
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

    private long getCountryBusinessEstimateByClient(String countryCode) {
        long startTime = System.currentTimeMillis();

        CountryBusinessEstimateInput input = new CountryBusinessEstimateInput();
        input.setPriorityOp(0);
        input.setRobotsCountryCode(new ArrayList<>());
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

        long count = count(new LambdaQueryWrapper<Product>().eq(Product::getName, productDTO.getName())
                .ne(Product::getProductId, productDTO.getProductId()));
        if (count > 0) {
            return R.fail(ErrInfoConfig.getDynmic(11012));
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

        ProductSkuDTO skuAttrDTO = JSON.parseObject(product.getSkuAttr(), ProductSkuDTO.class);
        skuAttrDTO.setPrice(productDTO.getPrice());
        UpdateWrapper<Product> updateProduct = new UpdateWrapper<>();
        updateProduct.eq("product_id", product.getProductId()).set("sku_attr", JSON.toJSONString(skuAttrDTO));
        update(null, updateProduct);

        long price = BigDecimal.valueOf(productDTO.getPrice()).multiply(BigDecimal.valueOf(100L)).longValue();

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
}
