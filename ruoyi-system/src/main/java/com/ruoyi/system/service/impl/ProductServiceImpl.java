package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.domain.dto.ProductSkuDTO;
import com.ruoyi.system.domain.dto.SkuAttrDTO;
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
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery(Product.class);
        if (!ObjectUtils.isEmpty(queryParam.getStartDate())) {
            wrapper.gt(Product::getCreateDate, queryParam.getStartDate());
        }
        if (!ObjectUtils.isEmpty(queryParam.getEndDate())) {
            wrapper.gt(Product::getCreateDate, queryParam.getEndDate());
        }
        if (!ObjectUtils.isEmpty(queryParam.getCategoryId())) {
            wrapper.eq(Product::getCategoryId, queryParam.getCategoryId());
        }
        if (!ObjectUtils.isEmpty(queryParam.getStatus())) {
            wrapper.eq(Product::getStatus, queryParam.getStatus());
        }
        if (!ObjectUtils.isEmpty(queryParam.getName())) {
            wrapper.eq(Product::getName, queryParam.getName());
        }
        if (!ObjectUtils.isEmpty(queryParam.getProductId())) {
            wrapper.eq(Product::getProductId, queryParam.getProductId());
        }
        return this.page(page, wrapper);
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
                productSku.setPrice(productSkuDTO.getPrice());
                productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                productSku.setStock(productSkuDTO.getStock());
                productSkus.add(productSku);
            } else {
                ProductSku productSku = productSkuService.getById(productSkuDTO.getId());
                if (!ObjectUtils.isEmpty(productSku)) {
                    productSku.setCountyId(productSkuDTO.getCountyId());
                    productSku.setPrice(productSkuDTO.getPrice());
                    productSku.setPriceUnit(productSkuDTO.getPriceUnit());
                    productSku.setStock(productSkuDTO.getStock());
                    productSkus.add(productSku);
                }
            }
        }

        return productSkus;
    }
}
