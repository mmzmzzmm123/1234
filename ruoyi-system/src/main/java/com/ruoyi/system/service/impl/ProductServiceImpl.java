package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.domain.dto.ProductParam;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.domain.dto.ProductSkuDTO;
import com.ruoyi.system.mapper.ProductBundleMapper;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.mapper.ProductSkuMapper;
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

    @Resource
    private BoundleServiceImpl boundleService;

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    private ProductBundleMapper productBundleMapper;

    @Override
    public Page<Product> getPage(ProductQueryParamDTO productQueryParam) {
        Page<Product> page = new Page<>(productQueryParam.getPage().longValue(), productQueryParam.getLimit().longValue());
        return this.page(page);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean create(ProductParam productParam) {
        //商品信息
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        Long id = IdWorker.getId(product);
        product.setProductId(id);

        //组合信息
//        if (!ObjectUtils.isEmpty(productParam.getBoundle()) && !ObjectUtils.isEmpty(productParam.getBoundleList())) {
//            product.setBoundle(1);
//
//            List<ProductBundle> productBundleList = new ArrayList<>();
//            for (BoundleDTO boundleDTO : productParam.getBoundleList()) {
//                ProductBundle productBundle = new ProductBundle();
//                productBundle.setProductId(id);
//                productBundle.setBundleId(boundleDTO.getId());
//                productBundle.setEntityType(boundleDTO.getType());
//                productBundleList.add(productBundle);
//            }
//
//            boundleService.saveBatch(productBundleList);
//        }

        //sku信息
        if (!ObjectUtils.isEmpty(productParam.getSkuStockList())) {
            List<ProductSku> productSkus = new ArrayList<>();
            for (ProductSkuDTO item : productParam.getSkuStockList()) {
                ProductSku productSku = new ProductSku();
                BeanUtils.copyProperties(item, productSku);
                productSku.setProductId(id);
                productSkus.add(productSku);
            }
            productSkuService.saveBatch(productSkus);
        }

//        ProductSku productSku = productSkuService.getOne(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id).last("limit 1"));
//        product.setSkuAttr();
//        save(product);

        return true;
    }

    @Override
    public ProductParam updateInfo(Long id) {
        ProductParam ret = new ProductParam();

        //商品详情
        Product product = this.getOne(new LambdaQueryWrapper<Product>().eq(Product::getProductId, id).last("limit 1"));
        if (ObjectUtils.isEmpty(product)) {
            throw new ServiceException("商品不存在");
        }

        BeanUtils.copyProperties(product, ret);
        //组合信息
//        if (product.getBoundle() > 0) {
//            List<BoundleDTO> boundleDTOList = new ArrayList<>();
//
//            List<ProductBundle> productBundles = boundleService.list(new LambdaQueryWrapper<ProductBundle>().eq(ProductBundle::getProductId, id));
//            for (ProductBundle item : productBundles) {
//                BoundleDTO boundleDTO = new BoundleDTO();
//                BeanUtils.copyProperties(item, boundleDTO);
//                boundleDTOList.add(boundleDTO);
//            }
//            ret.setBoundleList(boundleDTOList);
//        }

        //商品sku信息
        List<ProductSkuDTO> skuStockList = new ArrayList<>();
        List<ProductSku> productSkuList = productSkuService.list(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, id));
        for (ProductSku productSku : productSkuList) {
            ProductSkuDTO productSkuDTO = new ProductSkuDTO();
            BeanUtils.copyProperties(productSku, productSkuDTO);
            skuStockList.add(productSkuDTO);
        }
        ret.setSkuStockList(skuStockList);

        return ret;
    }
}
