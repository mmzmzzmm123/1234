package com.ruoyi.project.system.service;

import java.util.List;
import com.ruoyi.project.system.domain.ByTeacherJbxx;

/**
 * 教师基本信息Service接口
 * 
 * @author tsbz
 * @date 2020-04-21
 */
public interface IByTeacherJbxxService 
{
    /**
     * 查询教师基本信息
     * 
     * @param id 教师基本信息ID
     * @return 教师基本信息
     */
    public ByTeacherJbxx selectByTeacherJbxxById(Long id);

    /**
     * 查询教师基本信息列表
     * 
     * @param byTeacherJbxx 教师基本信息
     * @return 教师基本信息集合
     */
    public List<ByTeacherJbxx> selectByTeacherJbxxList(ByTeacherJbxx byTeacherJbxx);

    /**
     * 新增教师基本信息
     * 
     * @param byTeacherJbxx 教师基本信息
     * @return 结果
     */
    public int insertByTeacherJbxx(ByTeacherJbxx byTeacherJbxx);

    /**
     * 修改教师基本信息
     * 
     * @param byTeacherJbxx 教师基本信息
     * @return 结果
     */
    public int updateByTeacherJbxx(ByTeacherJbxx byTeacherJbxx);

    /**
     * 批量删除教师基本信息
     * 
     * @param ids 需要删除的教师基本信息ID
     * @return 结果
     */
    public int deleteByTeacherJbxxByIds(Long[] ids);

    /**
     * 删除教师基本信息信息
     * 
     * @param id 教师基本信息ID
     * @return 结果
     */
    public int deleteByTeacherJbxxById(Long id);
}
