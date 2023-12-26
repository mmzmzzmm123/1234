package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;

public interface IPortalProductService {
    /**
     * 商品分类
     */
    Page<Product> getList(ProductRequest request);
}
