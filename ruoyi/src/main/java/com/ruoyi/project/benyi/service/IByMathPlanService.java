package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByMathPlan;

/**
 * 游戏数学方案Service接口
 *
 * @author tsbz
 * @date 2020-07-21
 */
public interface IByMathPlanService
{
    /**
     * 查询游戏数学方案
     *
     * @param id 游戏数学方案ID
     * @return 游戏数学方案
     */
    public ByMathPlan selectByMathPlanById(Long id);

    /**
     * 查询游戏数学方案列表
     *
     * @param byMathPlan 游戏数学方案
     * @return 游戏数学方案集合
     */
    public List<ByMathPlan> selectByMathPlanList(ByMathPlan byMathPlan);

    /**
     * 新增游戏数学方案
     *
     * @param byMathPlan 游戏数学方案
     * @return 结果
     */
    public int insertByMathPlan(ByMathPlan byMathPlan);

    /**
     * 修改游戏数学方案
     *
     * @param byMathPlan 游戏数学方案
     * @return 结果
     */
    public int updateByMathPlan(ByMathPlan byMathPlan);

    /**
     * 批量删除游戏数学方案
     *
     * @param ids 需要删除的游戏数学方案ID
     * @return 结果
     */
    public int deleteByMathPlanByIds(Long[] ids);

    /**
     * 删除游戏数学方案信息
     *
     * @param id 游戏数学方案ID
     * @return 结果
     */
    public int deleteByMathPlanById(Long id);
}
