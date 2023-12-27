package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.system.domain.dto.BatchUpdateProductDTO;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import com.ruoyi.system.domain.dto.UpdateProductDTO;
import com.ruoyi.system.domain.vo.ProductDetailVO;
import com.ruoyi.system.domain.vo.ProductVO;

/**
 * 商品管理Service
 */
public interface IProductService {
    /**
     * 后台查询商品
     */
    Page<Product> getPage(ProductQueryParamDTO productQueryParam);

    /**
     * 新增商品
     */
    boolean create(ProductDTO pmsProductAttributeParam);

    /**
     * 获取编辑商品信息
     */
    ProductDTO detail(Long id);

    /**
     * 更新商品
     */
    boolean update(ProductDTO productDTO);

    boolean handleUpdatePrice(UpdateProductDTO productDTO);

    boolean batchUpdateStatus(BatchUpdateProductDTO productDTO);

    boolean batchUpdateShowStatus(BatchUpdateProductDTO productDTO);

    boolean batchDeleteProducts(BatchUpdateProductDTO productDTO);

    /**
     * 前台商品列表
     */
    Page<ProductVO> getNormalList(ProductRequest request);

    /**
     * 前台获取商品详情
     */
    ProductDetailVO getProductDetailBySkuId(Long skuId);
}
