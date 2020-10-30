package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByMathTermplanitem;

/**
 * 游戏数学学期计划明细Mapper接口
 *
 * @author tsbz
 * @date 2020-10-29
 */
public interface ByMathTermplanitemMapper
{
    /**
     * 查询游戏数学学期计划明细
     *
     * @param id 游戏数学学期计划明细ID
     * @return 游戏数学学期计划明细
     */
    public ByMathTermplanitem selectByMathTermplanitemById(Long id);

    /**
     * 查询游戏数学学期计划明细列表
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 游戏数学学期计划明细集合
     */
    public List<ByMathTermplanitem> selectByMathTermplanitemList(ByMathTermplanitem byMathTermplanitem);

    /**
     * 新增游戏数学学期计划明细
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 结果
     */
    public int insertByMathTermplanitem(ByMathTermplanitem byMathTermplanitem);

    /**
     * 修改游戏数学学期计划明细
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 结果
     */
    public int updateByMathTermplanitem(ByMathTermplanitem byMathTermplanitem);

    /**
     * 删除游戏数学学期计划明细
     *
     * @param id 游戏数学学期计划明细ID
     * @return 结果
     */
    public int deleteByMathTermplanitemById(Long id);

    /**
     * 批量删除游戏数学学期计划明细
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByMathTermplanitemByIds(Long[] ids);
}
