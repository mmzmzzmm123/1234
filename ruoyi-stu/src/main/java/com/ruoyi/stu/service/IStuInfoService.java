package com.ruoyi.stu.service;

import java.util.List;
import com.ruoyi.stu.domain.StuInfo;

/**
 * 学生信息Service接口
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
public interface IStuInfoService 
{
    /**
     * 查询学生信息
     * 
     * @param stuId 学生信息主键
     * @return 学生信息
     */
    public StuInfo selectStuInfoByStuId(Long stuId);

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
     * @param stuIds 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deleteStuInfoByStuIds(Long[] stuIds);

    /**
     * 删除学生信息信息
     * 
     * @param stuId 学生信息主键
     * @return 结果
     */
    public int deleteStuInfoByStuId(Long stuId);

    int selectStuInfoByStuNo(String stuNo);

    List<StuInfo> clsAndClsYear();

    /**
     * 查询某个年级学生总人数
     *
     * @param clsYear 需要查询的年级
     */

    int selectStuNumByYear(String clsYear);
}
