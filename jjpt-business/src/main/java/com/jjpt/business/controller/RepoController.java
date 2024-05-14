package com.jjpt.business.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jjpt.business.modules.dto.*;
import com.jjpt.business.modules.entity.Repo;
import com.jjpt.business.service.QuRepoService;
import com.jjpt.business.service.RepoService;
import com.ruoyi.common.core.controller.ApiRest;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 题库信息Controller
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@RestController
@RequestMapping("/business/repo")
public class RepoController extends BaseController
{
    @Autowired
    private RepoService baseService;

    @Autowired
    private QuRepoService quRepoService;

    /**
     * 添加或修改
     * @param reqDTO
     * @return
     */

    @RequestMapping(value = "/save", method = { RequestMethod.POST})
    public AjaxResult save(@RequestBody RepoDTO reqDTO) {
        baseService.save(reqDTO);
        return super.success();
    }

    /**
     * 批量删除
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/delete", method = { RequestMethod.POST})
    public AjaxResult edit(@RequestBody BaseIdsReqDTO reqDTO) {
        //根据ID删除
        baseService.removeByIds(reqDTO.getIds());
        return super.success();
    }


    /**
     * 查找详情
     * @param reqDTO
     * @return
     */

    @RequestMapping(value = "/detail", method = { RequestMethod.POST})
    public AjaxResult find(@RequestBody BaseIdReqDTO reqDTO) {
        Repo entity = baseService.getById(reqDTO.getId());
        RepoDTO dto = new RepoDTO();
        BeanUtils.copyProperties(entity, dto);
        return super.success(dto);
    }

    /**
     * 分页查找
     * @param reqDTO
     * @return
     */
    @RequestMapping(value = "/paging", method = { RequestMethod.POST})
    public ApiRest<Page<RepoRespDTO>> paging(@RequestBody PagingReqDTO<RepoReqDTO> reqDTO) {

        //分页查询并转换
        Page<RepoRespDTO> page = baseService.paging(reqDTO);
        return super.successOK(page);
    }


    @RequestMapping(value = "/batch-action", method = { RequestMethod.POST})
    public AjaxResult batchAction(@RequestBody QuRepoBatchReqDTO reqDTO) {

        //分页查询并转换
        quRepoService.batchAction(reqDTO);
        return super.success();
    }









}
