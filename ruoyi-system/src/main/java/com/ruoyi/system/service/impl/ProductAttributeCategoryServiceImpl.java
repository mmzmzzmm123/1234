package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.ProductAttributeCategory;
import com.ruoyi.system.mapper.ProductAttributeCategoryMapper;
import com.ruoyi.system.service.IProductAttributeCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements IProductAttributeCategoryService {
    @Override
    public Page<ProductAttributeCategory> getList(Integer pageNum, Integer pageSize) {
        Page<ProductAttributeCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }
}
