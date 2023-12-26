package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.ProductAttribute;

public interface IProductAttributeService {
    Page<ProductAttribute> getList(Integer cid, Integer pageNum, Integer pageSize);
}
