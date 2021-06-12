package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.dto.response.VideoClassifyResponse;
import com.stdiet.custom.mapper.SysNutritionalVideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysVideoClassifyMapper;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.service.ISysVideoClassifyService;

/**
 * 视频分类Service业务层处理
 *
 * @author xzj
 * @date 2021-05-05
 */
@Service
public class SysVideoClassifyServiceImpl implements ISysVideoClassifyService
{
    @Autowired
    private SysVideoClassifyMapper sysVideoClassifyMapper;

    @Autowired
    private SysNutritionalVideoMapper sysNutritionalVideoMapper;

    /**
     * 查询视频分类
     *
     * @param id 视频分类ID
     * @return 视频分类
     */
    @Override
    public SysVideoClassify selectSysVideoClassifyById(Long id)
    {
        return sysVideoClassifyMapper.selectSysVideoClassifyById(id);
    }

    /**
     * 查询视频分类列表
     *
     * @param sysVideoClassify 视频分类
     * @return 视频分类
     */
    @Override
    public List<SysVideoClassify> selectSysVideoClassifyList(SysVideoClassify sysVideoClassify)
    {
        return sysVideoClassifyMapper.selectSysVideoClassifyList(sysVideoClassify);
    }

    /**
     * 新增视频分类
     *
     * @param sysVideoClassify 视频分类
     * @return 结果
     */
    @Override
    public int insertSysVideoClassify(SysVideoClassify sysVideoClassify)
    {
        sysVideoClassify.setCreateTime(DateUtils.getNowDate());
        return sysVideoClassifyMapper.insertSysVideoClassify(sysVideoClassify);
    }

    /**
     * 修改视频分类
     *
     * @param sysVideoClassify 视频分类
     * @return 结果
     */
    @Override
    public int updateSysVideoClassify(SysVideoClassify sysVideoClassify)
    {
        sysVideoClassify.setUpdateTime(DateUtils.getNowDate());
        return sysVideoClassifyMapper.updateSysVideoClassify(sysVideoClassify);
    }

    /**
     * 批量删除视频分类
     *
     * @param ids 需要删除的视频分类ID
     * @return 结果
     */
    @Override
    public int deleteSysVideoClassifyByIds(Long[] ids)
    {
        return sysVideoClassifyMapper.deleteSysVideoClassifyByIds(ids);
    }

    /**
     * 删除视频分类信息
     *
     * @param id 视频分类ID
     * @return 结果
     */
    @Override
    public int deleteSysVideoClassifyById(Long id)
    {
        return sysVideoClassifyMapper.deleteSysVideoClassifyById(id);
    }

    private boolean delChildrenClassify(Long id){
        //判断分类下是否存在视频，存在视频不能删除

        return true;
    }

    /**
     * 获取所有类别
     * @return
     */
    @Override
    public List<SysVideoClassify> getAllClassify(SysVideoClassify sysVideoClassify){
        return sysVideoClassifyMapper.getAllClassify(sysVideoClassify);
    }

    /**
     * 按分类层级分类，返回视频目录列表
     *
     * @param sysVideoClassify 视频分类
     * @return 视频分类集合
     */
    public List<SysVideoClassify> getAllClassifyAndVideo(Long parentId){
        return sysVideoClassifyMapper.getOneAllClassifyAndVideo(parentId);
    }
}