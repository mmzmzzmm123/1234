package com.ruoyi.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.web.dto.ProductParam;
import com.ruoyi.web.dto.ProductQueryParamDTO;
import com.ruoyi.web.entity.Product;

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
    boolean create(ProductParam pmsProductAttributeParam);

    /**
     * 获取商品编辑信息
     */
    ProductParam updateInfo(Long id);
}
