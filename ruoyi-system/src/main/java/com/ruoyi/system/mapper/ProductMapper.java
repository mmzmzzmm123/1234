package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.system.domain.dto.ProductDTO;
import com.ruoyi.system.domain.dto.ProductQueryParamDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    Page<ProductDTO> page(Page page, @Param("dto") ProductQueryParamDTO dto);

    void batchUpdateStatus(@Param("status") int status, @Param("ids") List<Long> ids);

    void batchUpdateShowStatus(@Param("status") int status, @Param("ids") List<Long> ids);

    void batchDel(@Param("ids") List<Long> ids);
}
