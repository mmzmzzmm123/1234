package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                                                        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMathPlanMapper;
import com.ruoyi.project.benyi.domain.ByMathPlan;
import com.ruoyi.project.benyi.service.IByMathPlanService;

/**
 * 游戏数学方案Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-21
 */
@Service
public class ByMathPlanServiceImpl implements IByMathPlanService
{
    @Autowired
    private ByMathPlanMapper byMathPlanMapper;

    /**
     * 查询游戏数学方案
     *
     * @param id 游戏数学方案ID
     * @return 游戏数学方案
     */
    @Override
    public ByMathPlan selectByMathPlanById(Long id)
    {
        return byMathPlanMapper.selectByMathPlanById(id);
    }

    /**
     * 查询游戏数学方案列表
     *
     * @param byMathPlan 游戏数学方案
     * @return 游戏数学方案
     */
    @Override
    public List<ByMathPlan> selectByMathPlanList(ByMathPlan byMathPlan)
    {
        return byMathPlanMapper.selectByMathPlanList(byMathPlan);
    }

    /**
     * 新增游戏数学方案
     *
     * @param byMathPlan 游戏数学方案
     * @return 结果
     */
    @Override
    public int insertByMathPlan(ByMathPlan byMathPlan)
    {
                                                                                                                                                                                                                                            return byMathPlanMapper.insertByMathPlan(byMathPlan);
    }

    /**
     * 修改游戏数学方案
     *
     * @param byMathPlan 游戏数学方案
     * @return 结果
     */
    @Override
    public int updateByMathPlan(ByMathPlan byMathPlan)
    {
                                                                                                                                                                                                                                            return byMathPlanMapper.updateByMathPlan(byMathPlan);
    }

    /**
     * 批量删除游戏数学方案
     *
     * @param ids 需要删除的游戏数学方案ID
     * @return 结果
     */
    @Override
    public int deleteByMathPlanByIds(Long[] ids)
    {
        return byMathPlanMapper.deleteByMathPlanByIds(ids);
    }

    /**
     * 删除游戏数学方案信息
     *
     * @param id 游戏数学方案ID
     * @return 结果
     */
    @Override
    public int deleteByMathPlanById(Long id)
    {
        return byMathPlanMapper.deleteByMathPlanById(id);
    }
}
