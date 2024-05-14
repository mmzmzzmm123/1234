package com.jjpt.business.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jjpt.business.modules.dto.PagingReqDTO;
import com.jjpt.business.modules.dto.RepoDTO;
import com.jjpt.business.modules.dto.RepoReqDTO;
import com.jjpt.business.modules.dto.RepoRespDTO;
import com.jjpt.business.modules.entity.Repo;

/**
* <p>
* 题库业务类
* </p>
*
* @author 聪明笨狗
* @since 2020-05-25 13:23
*/
public interface RepoService extends IService<Repo> {

    /**
    * 分页查询数据
    * @param reqDTO
    * @return
    */
    Page<RepoRespDTO> paging(PagingReqDTO<RepoReqDTO> reqDTO);


    /**
     * 保存
     * @param reqDTO
     */
    void save(RepoDTO reqDTO);
}
