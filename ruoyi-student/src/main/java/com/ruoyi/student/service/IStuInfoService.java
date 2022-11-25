package com.ruoyi.student.service;

import java.util.List;
import com.ruoyi.student.domain.StuInfo;

/**
 * 学生信息Service接口
 * 
 * @author wangzq
 * @date 2022-11-21
 */
public interface IStuInfoService 
{
    /**
     * 查询学生信息
     * 
     * @param id 学生信息主键
     * @return 学生信息
     */
    public StuInfo selectStuInfoById(Long id);

    /**
     * 查询学生信息列表
     * 
     * @param stuInfo 学生信息
     * @return 学生信息集合
     */
    public List<StuInfo> selectStuInfoList(StuInfo stuInfo);

    /**
     * 新增学生信息
     * 
     * @param stuInfo 学生信息
     * @return 结果
     */
    public int insertStuInfo(StuInfo stuInfo);

    /**
     * 修改学生信息
     * 
     * @param stuInfo 学生信息
     * @return 结果
     */
    public int updateStuInfo(StuInfo stuInfo);

    /**
     * 批量删除学生信息
     * 
     * @param ids 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteStuInfoByIds(Long[] ids);

    /**
     * 删除学生信息信息
     * 
     * @param id 学生信息主键
     * @return 结果
     */
    public int deleteStuInfoById(Long id);

    /**
     * 变更学生标签：当日返校 → 在校
     * @return
     */
    int changeTagReturn2InSchool();

    /**
     * 变更学生标签：当日离校 → 不在校
     * @return
     */
    int changeTagLeave2Out();

    /**
     *
     * @param stuInfoList
     * @return
     */
    List<StuInfo> matchDict(List<StuInfo> stuInfoList);

}
