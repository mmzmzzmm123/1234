package com.ruoyi.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.web.entity.ProductAttribute;
import com.ruoyi.web.mapper.ProductAttributeMapper;
import com.ruoyi.web.service.IProductAttributeService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements IProductAttributeService {
    @Override
    public Page<ProductAttribute> getList(Integer cid, Integer pageNum, Integer pageSize) {
        Page<ProductAttribute> page = new Page<>(pageNum, pageSize);
        return this.page(page, new LambdaQueryWrapper<ProductAttribute>().eq(ProductAttribute::getProductAttributeCategoryId, cid));
    }
}
