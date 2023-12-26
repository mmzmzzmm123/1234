package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.ProductAttributeCategory;

public interface IProductAttributeCategoryService {
    Page<ProductAttributeCategory> getList(Integer pageNum, Integer pageSize);
}
