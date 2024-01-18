package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.ProductDetailVO;
import com.ruoyi.system.domain.vo.ProductVO;

import java.util.List;

/**
 * 商品管理Service
 */
public interface IProductService {
    /**
     * 后台查询商品
     */
    Page<ProductDTO> getPage(ProductQueryParamDTO productQueryParam);

    /**
     * 新增商品
     */
    Product create(ProductDTO pmsProductAttributeParam);

    /**
     * 获取编辑商品信息
     */
    R<ProductDTO> detail(Long id);

    /**
     * 更新商品
     */
    R<String> update(ProductDTO productDTO);

    R<String> handleUpdatePrice(UpdateProductDTO productDTO);

    R<String> batchUpdateStatus(BatchUpdateProductStatusDTO productDTO);

    R<String> batchUpdateShowStatus(BatchUpdateProductShowStatusDTO productDTO);

    R<String> batchDeleteProducts(BatchUpdateProductDTO productDTO);

    /**
     * 前台商品列表
     */
    Page<ProductVO> getNormalList(ProductRequest request);

    /**
     * 前台获取商品详情
     */
    R<ProductDetailVO> getProductDetailBySkuId(Long skuId);


    List<Product> queryEnableProduct();

    void syncStocks(String countryCode);

    void syncAllStocks();

}
