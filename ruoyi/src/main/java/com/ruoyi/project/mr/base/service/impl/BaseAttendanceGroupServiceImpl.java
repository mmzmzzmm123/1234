package com.ruoyi.project.mr.base.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.system.domain.SysPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mr.base.mapper.BaseAttendanceGroupMapper;
import com.ruoyi.project.mr.base.domain.BaseAttendanceGroup;
import com.ruoyi.project.mr.base.service.IBaseAttendanceGroupService;

/**
 * 考勤组管理Service业务层处理
 *
 * @author mr
 * @date 2020-02-03
 */
@Service
public class BaseAttendanceGroupServiceImpl implements IBaseAttendanceGroupService
{
    @Autowired
    private BaseAttendanceGroupMapper baseAttendanceGroupMapper;

    /**
     * 查询考勤组管理
     *
     * @param id 考勤组管理ID
     * @return 考勤组管理
     */
    @Override
    public BaseAttendanceGroup selectBaseAttendanceGroupById(String id)
    {
        return baseAttendanceGroupMapper.selectBaseAttendanceGroupById(id);
    }

    /**
     * 查询考勤组管理列表
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 考勤组管理
     */
    @Override
    public List<BaseAttendanceGroup> selectBaseAttendanceGroupList(BaseAttendanceGroup baseAttendanceGroup)
    {
        return baseAttendanceGroupMapper.selectBaseAttendanceGroupList(baseAttendanceGroup);
    }

    /**
     * 查询所有考勤组
     *
     * @return 考勤组列表
     */
    @Override
    public List<BaseAttendanceGroup> selectGroupAll()
    {
        return baseAttendanceGroupMapper.selectGroupAll();
    }

    /**
     * 新增考勤组管理
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 结果
     */
    @Override
    public int insertBaseAttendanceGroup(BaseAttendanceGroup baseAttendanceGroup)
    {
        baseAttendanceGroup.setCreateTime(DateUtils.getNowDate());
        return baseAttendanceGroupMapper.insertBaseAttendanceGroup(baseAttendanceGroup);
    }

    /**
     * 修改考勤组管理
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 结果
     */
    @Override
    public int updateBaseAttendanceGroup(BaseAttendanceGroup baseAttendanceGroup)
    {
        baseAttendanceGroup.setUpdateTime(DateUtils.getNowDate());
        return baseAttendanceGroupMapper.updateBaseAttendanceGroup(baseAttendanceGroup);
    }

    /**
     * 批量删除考勤组管理
     *
     * @param ids 需要删除的考勤组管理ID
     * @return 结果
     */
    @Override
    public int deleteBaseAttendanceGroupByIds(String[] ids)
    {
        return baseAttendanceGroupMapper.deleteBaseAttendanceGroupByIds(ids);
    }

    /**
     * 删除考勤组管理信息
     *
     * @param id 考勤组管理ID
     * @return 结果
     */
    @Override
    public int deleteBaseAttendanceGroupById(String id)
    {
        return baseAttendanceGroupMapper.deleteBaseAttendanceGroupById(id);
    }
}
