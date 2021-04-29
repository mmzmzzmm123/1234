package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysNutritionalVideoMapper;
import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.service.ISysNutritionalVideoService;

/**
 * 营养视频Service业务层处理
 *
 * @author xzj
 * @date 2021-04-29
 */
@Service
public class SysNutritionalVideoServiceImpl implements ISysNutritionalVideoService
{
    @Autowired
    private SysNutritionalVideoMapper sysNutritionalVideoMapper;

    /**
     * 查询营养视频
     *
     * @param id 营养视频ID
     * @return 营养视频
     */
    @Override
    public SysNutritionalVideo selectSysNutritionalVideoById(Long id)
    {
        return sysNutritionalVideoMapper.selectSysNutritionalVideoById(id);
    }

    /**
     * 查询营养视频列表
     *
     * @param sysNutritionalVideo 营养视频
     * @return 营养视频
     */
    @Override
    public List<SysNutritionalVideo> selectSysNutritionalVideoList(SysNutritionalVideo sysNutritionalVideo)
    {
        return sysNutritionalVideoMapper.selectSysNutritionalVideoList(sysNutritionalVideo);
    }

    /**
     * 新增营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    @Override
    public int insertSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo)
    {
        sysNutritionalVideo.setCreateTime(DateUtils.getNowDate());
        return sysNutritionalVideoMapper.insertSysNutritionalVideo(sysNutritionalVideo);
    }

    /**
     * 修改营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    @Override
    public int updateSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo)
    {
        sysNutritionalVideo.setUpdateTime(DateUtils.getNowDate());
        return sysNutritionalVideoMapper.updateSysNutritionalVideo(sysNutritionalVideo);
    }

    /**
     * 批量删除营养视频
     *
     * @param ids 需要删除的营养视频ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionalVideoByIds(Long[] ids)
    {
        return sysNutritionalVideoMapper.deleteSysNutritionalVideoByIds(ids);
    }

    /**
     * 删除营养视频信息
     *
     * @param id 营养视频ID
     * @return 结果
     */
    @Override
    public int deleteSysNutritionalVideoById(Long id)
    {
        return sysNutritionalVideoMapper.deleteSysNutritionalVideoById(id);
    }

    /**
     * 获取视频
     * @param videoId
     * @return
     */
    public SysNutritionalVideo selectSysNutritionalVideByVideoId(String videoId){
        return sysNutritionalVideoMapper.selectSysNutritionalVideByVideoId(videoId);
    }
}