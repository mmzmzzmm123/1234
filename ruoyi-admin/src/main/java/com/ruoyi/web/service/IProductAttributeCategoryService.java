package com.ruoyi.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.web.entity.ProductAttributeCategory;

public interface IProductAttributeCategoryService {
    Page<ProductAttributeCategory> getList(Integer pageNum, Integer pageSize);
}
