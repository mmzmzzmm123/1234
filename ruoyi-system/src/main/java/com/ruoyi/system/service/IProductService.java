package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;

/**
 * 商品管理Service
 */
public interface IProductService {
    /**
     * 分页查询商品
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
}
