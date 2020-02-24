package com.ruoyi.project.mr.base.service;

import com.ruoyi.project.mr.base.domain.BaseAttendanceGroup;
import com.ruoyi.project.system.domain.SysPost;

import java.util.List;

/**
 * 考勤组管理Service接口
 *
 * @author mr
 * @date 2020-02-03
 */
public interface IBaseAttendanceGroupService
{
    /**
     * 查询考勤组管理
     *
     * @param id 考勤组管理ID
     * @return 考勤组管理
     */
    public BaseAttendanceGroup selectBaseAttendanceGroupById(String id);

    /**
     * 查询考勤组管理列表
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 考勤组管理集合
     */
    public List<BaseAttendanceGroup> selectBaseAttendanceGroupList(BaseAttendanceGroup baseAttendanceGroup);

    /**
     * 查询所有考勤组
     *
     * @return 考勤组列表
     */
    public List<BaseAttendanceGroup> selectGroupAll();

    /**
     * 新增考勤组管理
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 结果
     */
    public int insertBaseAttendanceGroup(BaseAttendanceGroup baseAttendanceGroup);

    /**
     * 修改考勤组管理
     *
     * @param baseAttendanceGroup 考勤组管理
     * @return 结果
     */
    public int updateBaseAttendanceGroup(BaseAttendanceGroup baseAttendanceGroup);

    /**
     * 批量删除考勤组管理
     *
     * @param ids 需要删除的考勤组管理ID
     * @return 结果
     */
    public int deleteBaseAttendanceGroupByIds(String[] ids);

    /**
     * 删除考勤组管理信息
     *
     * @param id 考勤组管理ID
     * @return 结果
     */
    public int deleteBaseAttendanceGroupById(String id);
}
