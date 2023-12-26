package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.ProductAttribute;
import com.ruoyi.system.mapper.ProductAttributeMapper;
import com.ruoyi.system.service.IProductAttributeService;
import org.springframework.stereotype.Service;

@Service
public class ProductAttributeServiceImpl extends ServiceImpl<ProductAttributeMapper, ProductAttribute> implements IProductAttributeService {
    @Override
    public Page<ProductAttribute> getList(Integer cid, Integer pageNum, Integer pageSize) {
        Page<ProductAttribute> page = new Page<>(pageNum, pageSize);
        return this.page(page, new LambdaQueryWrapper<ProductAttribute>().eq(ProductAttribute::getProductAttributeCategoryId, cid));
    }
}
