package com.ruoyi.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.web.entity.ProductAttributeCategory;
import com.ruoyi.web.mapper.ProductAttributeCategoryMapper;
import com.ruoyi.web.service.IProductAttributeCategoryService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeCategoryServiceImpl extends ServiceImpl<ProductAttributeCategoryMapper, ProductAttributeCategory> implements IProductAttributeCategoryService {
    @Override
    public Page<ProductAttributeCategory> getList(Integer pageNum, Integer pageSize) {
        Page<ProductAttributeCategory> page = new Page<>(pageNum, pageSize);
        return this.page(page);
    }
}
