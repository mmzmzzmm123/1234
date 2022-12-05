package com.ruoyi.student.mapper;

import java.util.List;
import com.ruoyi.student.domain.StuStateHis;

/**
 * 学生历史状态Mapper接口
 * 
 * @author wangzq
 * @date 2022-12-05
 */
public interface StuStateHisMapper 
{
    /**
     * 查询学生历史状态
     * 
     * @param id 学生历史状态主键
     * @return 学生历史状态
     */
    public StuStateHis selectStuStateHisById(Long id);

    /**
     * 查询学生历史状态列表
     * 
     * @param stuStateHis 学生历史状态
     * @return 学生历史状态集合
     */
    public List<StuStateHis> selectStuStateHisList(StuStateHis stuStateHis);

    /**
     * 新增学生历史状态
     * 
     * @param stuStateHis 学生历史状态
     * @return 结果
     */
    public int insertStuStateHis(StuStateHis stuStateHis);

    /**
     * 修改学生历史状态
     * 
     * @param stuStateHis 学生历史状态
     * @return 结果
     */
    public int updateStuStateHis(StuStateHis stuStateHis);

    /**
     * 删除学生历史状态
     * 
     * @param id 学生历史状态主键
     * @return 结果
     */
    public int deleteStuStateHisById(Long id);

    /**
     * 批量删除学生历史状态
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStuStateHisByIds(Long[] ids);
}
