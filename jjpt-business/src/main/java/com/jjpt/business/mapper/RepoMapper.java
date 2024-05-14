package com.jjpt.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jjpt.business.modules.dto.RepoReqDTO;
import com.jjpt.business.modules.dto.RepoRespDTO;
import com.jjpt.business.modules.entity.Repo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* <p>
* 题库Mapper
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
public interface RepoMapper extends BaseMapper<Repo> {

    /**
     * 分页查询题库
     * @param page
     * @param query
     * @return
     */
    Page<RepoRespDTO> paging(Page page, @Param("query") RepoReqDTO query);

    List<RepoRespDTO> pagings(@Param("query") RepoReqDTO query);

}
