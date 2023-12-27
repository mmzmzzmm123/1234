package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.app.ProductRequest;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.system.mapper.ProductMapper;
import com.ruoyi.system.service.IPortalProductService;
import org.springframework.stereotype.Service;

@Service
public class PortalProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IPortalProductService {
    @Override
    public Page<Product> getList(ProductRequest request) {

        return null;
    }
}
