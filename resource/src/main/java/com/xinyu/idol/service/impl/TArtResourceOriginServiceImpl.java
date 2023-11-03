package com.xinyu.idol.service.impl;

import java.util.List;

import com.xinyu.idol.common.core.domain.model.LoginUser;
import com.xinyu.idol.common.utils.DateUtils;
import com.xinyu.idol.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xinyu.idol.mapper.TArtResourceOriginMapper;
import com.xinyu.idol.domain.TArtResourceOrigin;
import com.xinyu.idol.service.ITArtResourceOriginService;

/**
 * 美术资源上传原件Service业务层处理
 * 
 * @author liyu
 * @date 2023-10-31
 */
@Service
public class TArtResourceOriginServiceImpl implements ITArtResourceOriginService 
{
    @Autowired
    private TArtResourceOriginMapper tArtResourceOriginMapper;


    /**
     * 查询美术资源上传原件
     * 
     * @param id 美术资源上传原件主键
     * @return 美术资源上传原件
     */
    @Override
    public TArtResourceOrigin selectTArtResourceOriginById(String id)
    {
        return tArtResourceOriginMapper.selectTArtResourceOriginById(id);
    }

    /**
     * 查询美术资源上传原件列表
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 美术资源上传原件
     */
    @Override
    public List<TArtResourceOrigin> selectTArtResourceOriginList(TArtResourceOrigin tArtResourceOrigin)
    {
        return tArtResourceOriginMapper.selectTArtResourceOriginList(tArtResourceOrigin);
    }

    /**
     * 新增美术资源上传原件
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 结果
     */
    @Override
    public int insertTArtResourceOrigin(TArtResourceOrigin tArtResourceOrigin)
    {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        tArtResourceOrigin.setCreateTime(DateUtils.getNowDate());
        tArtResourceOrigin.setCreateBy(loginUser.getUser().getUserId().toString());


        return tArtResourceOriginMapper.insertTArtResourceOrigin(tArtResourceOrigin);
    }

    /**
     * 修改美术资源上传原件
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 结果
     */
    @Override
    public int updateTArtResourceOrigin(TArtResourceOrigin tArtResourceOrigin)
    {
        tArtResourceOrigin.setUpdateTime(DateUtils.getNowDate());
        return tArtResourceOriginMapper.updateTArtResourceOrigin(tArtResourceOrigin);
    }

    /**
     * 批量删除美术资源上传原件
     * 
     * @param ids 需要删除的美术资源上传原件主键
     * @return 结果
     */
    @Override
    public int deleteTArtResourceOriginByIds(String[] ids)
    {
        return tArtResourceOriginMapper.deleteTArtResourceOriginByIds(ids);
    }

    /**
     * 删除美术资源上传原件信息
     * 
     * @param id 美术资源上传原件主键
     * @return 结果
     */
    @Override
    public int deleteTArtResourceOriginById(String id)
    {
        return tArtResourceOriginMapper.deleteTArtResourceOriginById(id);
    }
}
