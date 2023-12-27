package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.ProductStatusConstants;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.ProductDetailVO;
import com.ruoyi.system.domain.vo.ProductVO;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Resource
    private ProductSkuServiceImpl productSkuService;

    @Override
    public Page<Product> getPage(ProductQueryParamDTO queryParam) {
        Page<Product> page = new Page<>(queryParam.getPage().longValue(), queryParam.getLimit().longValue());
        return this.page(page, getQueryWrapper(queryParam));
    }

    private LambdaQueryWrapper<Product> getQueryWrapper(ProductQueryParamDTO queryParam) {
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery(Product.class);
        if (!ObjectUtils.isEmpty(queryParam.getStartDate())) {
            wrapper.gt(Product::getCreateDate, queryParam.getStartDate());
        }
        if (!ObjectUtils.isEmpty(queryParam.getEndDate())) {
            wrapper.lt(Product::getCreateDate, queryParam.getEndDate());
        }
        if (!ObjectUtils.isEmpty(queryParam.getCategoryId())) {
            wrapper.eq(Product::getCategoryId, queryParam.getCategoryId());
        }
        if (!ObjectUtils.isEmpty(queryParam.getStatus())) {
            wrapper.eq(Product::getStatus, queryParam.getStatus());
        }
        if (!ObjectUtils.isEmpty(queryParam.getName())) {
            wrapper.like(Product::getName, queryParam.getName());
        }
        if (!ObjectUtils.isEmpty(queryParam.getProductId())) {
            wrapper.eq(Product::getProductId, queryParam.getProductId());
        }
        wrapper.eq(Product::getIsDel, ProductStatusConstants.NORMAL);
        return wrapper;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean create(ProductDTO productDTO) {
        //商品信息
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        long id = IdWorker.getId(product);
        product.setProductId(id);

        //sku信息
        if (!ObjectUtils.isEmpty(productDTO.getSkuList())) {
            List<ProductSku> productSkus = new ArrayList<>();
            for (ProductSkuDTO item : productDTO.getSkuList()) {
                ProductSku productSku = new ProductSku();
                BeanUtils.copyProperties(item, productSku);
                productSku.setProductId(id);
                productSkus.add(productSku);
            }
            productSkuService.saveBatch(productSkus);
        }

        product.setSkuAttr(getSkuAttr(id));
        save(product);

        return true;
    }

    private String getSkuAttr(long productId) {
        ProductSku productSku = productSkuService.getOne(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productId)
                .orderByAsc(ProductSku::getPrice).last("limit 1"));
        SkuAttrDTO skuAttrDTO = new SkuAttrDTO();
        BeanUtils.copyProperties(productSku, skuAttrDTO);
        skuAttrDTO.setSkuId(productSku.getId());
        return JSON.toJSONString(skuAttrDTO);
    }

    @Override
    public ProductDTO detail(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return null;
        }

        ProductDTO ret = new ProductDTO();

        //商品详情
        Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, id).last("limit 1"));
        if (ObjectUtils.isEmpty(product)) {
            return ret;
        }
        BeanUtils.copyProperties(product, ret);

        //商品sku信息
        List<ProductSkuDTO> skuList = new ArrayList<>();
        List<ProductSku> productSkuList = productSkuService.list(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id));
        for (ProductSku productSku : productSkuList) {
            ProductSkuDTO productSkuDTO = new ProductSkuDTO();
            BeanUtils.copyProperties(productSku, productSkuDTO);
            skuList.add(productSkuDTO);
        }
        ret.setSkuList(skuList);

        return ret;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean update(ProductDTO productDTO) {
        if (ObjectUtils.isEmpty(productDTO.getProductId())) {
            return false;
        }

        List<ProductSku> productSkus = getUpdateProductSku(productDTO.getSkuList());
        productSkuService.saveOrUpdateBatch(productSkus);

        LambdaUpdateWrapper<Product> productUpdateWrapper = new LambdaUpdateWrapper<>();
        productUpdateWrapper.eq(Product::getProductId, productDTO.getProductId());
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        product.setSkuAttr(getSkuAttr(productDTO.getProductId()));
        this.update(product, productUpdateWrapper);

        return true;
    }

    private List<ProductSku> getUpdateProductSku(List<ProductSkuDTO> skuList) {
        List<ProductSku> productSkus = new ArrayList<>();
        for (ProductSkuDTO productSkuDTO : skuList) {
            if (ObjectUtils.isEmpty(productSkuDTO.getId())) {
                ProductSku productSku = new ProductSku();

                productSku.setProductId(productSkuDTO.getProductId());
                productSku.setCountyId(productSkuDTO.getCountyId());
                productSku.setCountyName(productSkuDTO.getCountyName());
                productSku.setPrice(productSkuDTO.getPrice());
                productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                productSku.setStock(productSkuDTO.getStock());
                productSkus.add(productSku);
            } else {
                ProductSku productSku = productSkuService.getById(productSkuDTO.getId());
                if (!ObjectUtils.isEmpty(productSku)) {
                    productSku.setCountyId(productSkuDTO.getCountyId());
                    productSku.setCountyName(productSkuDTO.getCountyName());
                    productSku.setPrice(productSkuDTO.getPrice());
                    productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                    productSku.setStock(productSkuDTO.getStock());
                    productSkus.add(productSku);
                }
            }
        }

        return productSkus;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean handleUpdatePrice(UpdateProductDTO productDTO) {
        if (ObjectUtils.isEmpty(productDTO.getProductId()) || ObjectUtils.isEmpty(productDTO.getPrice()) || productDTO.getPrice() < 0) {
            return false;
        }

        Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, productDTO.getProductId()).last("limit 1"));
        if (ObjectUtils.isEmpty(product)) {
            return false;
        }

        String skuAttr = product.getSkuAttr();
        SkuAttrDTO skuAttrDTO = JSON.parseObject(skuAttr, SkuAttrDTO.class);
        skuAttrDTO.setPrice(productDTO.getPrice());
        product.setSkuAttr(JSON.toJSONString(skuAttrDTO));
        this.save(product);

        ProductSku productSku = productSkuService.getById(skuAttrDTO.getSkuId());
        if (!ObjectUtils.isEmpty(productSku)) {
            productSku.setPrice(productDTO.getPrice());
            productSkuService.save(productSku);
        }

        return true;
    }

    @Override
    public boolean batchUpdateStatus(BatchUpdateProductDTO productDTO) {
        List<Product> productList = new ArrayList<>();
        for (Long id : productDTO.getProductIds()) {
            Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, id).last("limit 1"));
            if (ObjectUtils.isEmpty(product)) {
                continue;
            }
            if (productDTO.getStatus().equals(product.getStatus())) {
                continue;
            }
            product.setStatus(productDTO.getStatus());
            productList.add(product);
        }

        if (productList.size() > 0) {
            this.updateBatchById(productList);
        }

        return true;
    }

    @Override
    public boolean batchUpdateShowStatus(BatchUpdateProductDTO productDTO) {
        List<Product> productList = new ArrayList<>();
        for (Long id : productDTO.getProductIds()) {
            Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, id).last("limit 1"));
            if (ObjectUtils.isEmpty(product)) {
                continue;
            }
            if (productDTO.getShowStatus().equals(product.getStatus())) {
                continue;
            }
            product.setIsShow(productDTO.getShowStatus());
            productList.add(product);
        }

        if (productList.size() > 0) {
            this.updateBatchById(productList);
        }

        return true;
    }

    @Override
    public boolean batchDeleteProducts(BatchUpdateProductDTO productDTO) {
        List<Product> productList = new ArrayList<>();
        for (Long id : productDTO.getProductIds()) {
            Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, id).last("limit 1"));
            if (ObjectUtils.isEmpty(product)) {
                continue;
            }
            product.setIsShow(ProductStatusConstants.HIDE);
            product.setIsDel(ProductStatusConstants.DEL);
            productList.add(product);
        }

        if (productList.size() > 0) {
            this.updateBatchById(productList);
        }

        return true;
    }

    @Override
    public Page<ProductVO> getNormalList(ProductRequest request) {
        Page<Product> productPage = getNormalProductPage(request);

        Page<ProductVO> ret = new Page<>(request.getPage().longValue(), request.getLimit().longValue());
        List<ProductVO> productVOList = new ArrayList<>();
        BeanUtils.copyProperties(productPage, ret);
        for (Product product : productPage.getRecords()) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVO.setSkuAttr(JSON.parseObject(product.getSkuAttr(), SkuAttrDTO.class));
            productVOList.add(productVO);
        }
        ret.setRecords(productVOList);
        return ret;
    }

    private Page<Product> getNormalProductPage(ProductRequest request) {
        Page<Product> page = new Page<>(request.getPage().longValue(), request.getLimit().longValue());

        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery(Product.class);
        if (!ObjectUtils.isEmpty(request.getCategoryId())) {
            wrapper.eq(Product::getCategoryId, request.getCategoryId());
        }
        if (!ObjectUtils.isEmpty(request.getName())) {
            wrapper.like(Product::getName, request.getName());
        }
        wrapper.eq(Product::getStatus, ProductStatusConstants.LISTING);
        wrapper.eq(Product::getIsShow, ProductStatusConstants.SHOW);
        wrapper.eq(Product::getIsDel, ProductStatusConstants.NORMAL);
        wrapper.select(Product::getProductId, Product::getCategoryId, Product::getAppType, Product::getName, Product::getAlbumPics, Product::getPic);
        return this.page(page, wrapper);
    }

    @Override
    public ProductDetailVO getProductDetailBySkuId(Long skuId) {
        ProductDetailVO ret = new ProductDetailVO();

        //查询sku信息
        ProductSku productSku = productSkuService.getById(skuId);
        if (ObjectUtils.isEmpty(productSku)) {
            return ret;
        }

        //查询商品信息
        Product product = this.getOne(new LambdaQueryWrapper<Product>()
                .eq(Product::getProductId, productSku.getProductId())
                .eq(Product::getStatus, ProductStatusConstants.LISTING)
                .eq(Product::getIsShow, ProductStatusConstants.SHOW)
                .eq(Product::getIsDel, ProductStatusConstants.NORMAL)
                .last("limit 1"));
        if (ObjectUtils.isEmpty(product)) {
            return ret;
        }
        BeanUtils.copyProperties(product, ret);

        //设置该商品sku, 目前只有一个sku属性 直接使用当前sku
        List<ProductSkuDTO> skuList = new ArrayList<>();
        ProductSkuDTO productSkuDTO = new ProductSkuDTO();
        BeanUtils.copyProperties(productSku, productSkuDTO);
        skuList.add(productSkuDTO);
        ret.setSkuList(skuList);

        return ret;
    }
}
