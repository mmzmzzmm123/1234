package com.ruoyi.web.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.web.entity.ProductAttribute;

public interface IProductAttributeService {
    Page<ProductAttribute> getList(Integer cid, Integer pageNum, Integer pageSize);
}
