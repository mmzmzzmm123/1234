package com.ruoyi.project.benyi.mapper;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByMathTermplan;

/**
 * 游戏数学学期计划Mapper接口
 *
 * @author tsbz
 * @date 2020-10-29
 */
public interface ByMathTermplanMapper
{
    /**
     * 查询游戏数学学期计划
     *
     * @param id 游戏数学学期计划ID
     * @return 游戏数学学期计划
     */
    public ByMathTermplan selectByMathTermplanById(String id);

    /**
     * 查询游戏数学学期计划列表
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 游戏数学学期计划集合
     */
    public List<ByMathTermplan> selectByMathTermplanList(ByMathTermplan byMathTermplan);

    /**
     * 新增游戏数学学期计划
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 结果
     */
    public int insertByMathTermplan(ByMathTermplan byMathTermplan);

    /**
     * 修改游戏数学学期计划
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 结果
     */
    public int updateByMathTermplan(ByMathTermplan byMathTermplan);

    /**
     * 删除游戏数学学期计划
     *
     * @param id 游戏数学学期计划ID
     * @return 结果
     */
    public int deleteByMathTermplanById(String id);

    /**
     * 批量删除游戏数学学期计划
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteByMathTermplanByIds(String[] ids);
}
