package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysLiveSchedulMapper;
import com.stdiet.custom.domain.SysLiveSchedul;
import com.stdiet.custom.service.ISysLiveSchedulService;

/**
 * 直播排班Service业务层处理
 *
 * @author xzj
 * @date 2021-05-12
 */
@Service
public class SysLiveSchedulServiceImpl implements ISysLiveSchedulService
{
    @Autowired
    private SysLiveSchedulMapper sysLiveSchedulMapper;

    /**
     * 查询直播排班
     *
     * @param id 直播排班ID
     * @return 直播排班
     */
    @Override
    public SysLiveSchedul selectSysLiveSchedulById(Long id)
    {
        return sysLiveSchedulMapper.selectSysLiveSchedulById(id);
    }

    /**
     * 查询直播排班列表
     *
     * @param sysLiveSchedul 直播排班
     * @return 直播排班
     */
    @Override
    public List<SysLiveSchedul> selectSysLiveSchedulList(SysLiveSchedul sysLiveSchedul)
    {
        return sysLiveSchedulMapper.selectSysLiveSchedulList(sysLiveSchedul);
    }

    /**
     * 新增直播排班
     *
     * @param sysLiveSchedul 直播排班
     * @return 结果
     */
    @Override
    public int insertSysLiveSchedul(SysLiveSchedul sysLiveSchedul)
    {
        sysLiveSchedul.setCreateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.insertSysLiveSchedul(sysLiveSchedul);
    }

    /**
     * 修改直播排班
     *
     * @param sysLiveSchedul 直播排班
     * @return 结果
     */
    @Override
    public int updateSysLiveSchedul(SysLiveSchedul sysLiveSchedul)
    {
        sysLiveSchedul.setUpdateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.updateSysLiveSchedul(sysLiveSchedul);
    }

    /**
     * 批量删除直播排班
     *
     * @param ids 需要删除的直播排班ID
     * @return 结果
     */
    @Override
    public int deleteSysLiveSchedulByIds(Long[] ids)
    {
        return sysLiveSchedulMapper.deleteSysLiveSchedulByIds(ids);
    }

    /**
     * 删除直播排班信息
     *
     * @param id 直播排班ID
     * @return 结果
     */
    @Override
    public int deleteSysLiveSchedulById(Long id)
    {
        return sysLiveSchedulMapper.deleteSysLiveSchedulById(id);
    }

    /**
     * 根据直播时间查询是否重叠
     */
    @Override
    public SysLiveSchedul getLiveSchedulByLiveTime(SysLiveSchedul sysLiveSchedul){
        return sysLiveSchedulMapper.getLiveSchedulByLiveTime(sysLiveSchedul);
    }

    /**
     * 更新部分字段
     * @param sysLiveSchedul
     * @return
     */
    @Override
    public int updateSysLiveSchedulById(SysLiveSchedul sysLiveSchedul){
        sysLiveSchedul.setUpdateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.updateSysLiveSchedulById(sysLiveSchedul);
    }

    /**
     * 根据ID查询上一条记录
     * @param sysLiveSchedul
     * @return
     */
    @Override
    public SysLiveSchedul getLastLiveSchedulById(SysLiveSchedul sysLiveSchedul){
        return sysLiveSchedulMapper.getLastLiveSchedulById(sysLiveSchedul);
    }
}