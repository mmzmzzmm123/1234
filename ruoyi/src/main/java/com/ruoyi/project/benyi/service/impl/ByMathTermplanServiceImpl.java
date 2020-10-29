package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                                                                                                                import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMathTermplanMapper;
import com.ruoyi.project.benyi.domain.ByMathTermplan;
import com.ruoyi.project.benyi.service.IByMathTermplanService;

/**
 * 游戏数学学期计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-29
 */
@Service
public class ByMathTermplanServiceImpl implements IByMathTermplanService
{
    @Autowired
    private ByMathTermplanMapper byMathTermplanMapper;

    /**
     * 查询游戏数学学期计划
     *
     * @param id 游戏数学学期计划ID
     * @return 游戏数学学期计划
     */
    @Override
    public ByMathTermplan selectByMathTermplanById(String id)
    {
        return byMathTermplanMapper.selectByMathTermplanById(id);
    }

    /**
     * 查询游戏数学学期计划列表
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 游戏数学学期计划
     */
    @Override
    public List<ByMathTermplan> selectByMathTermplanList(ByMathTermplan byMathTermplan)
    {
        return byMathTermplanMapper.selectByMathTermplanList(byMathTermplan);
    }

    /**
     * 新增游戏数学学期计划
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 结果
     */
    @Override
    public int insertByMathTermplan(ByMathTermplan byMathTermplan)
    {
                                                                                                                                                                                                                                                                                                        byMathTermplan.setCreateTime(DateUtils.getNowDate());
                            return byMathTermplanMapper.insertByMathTermplan(byMathTermplan);
    }

    /**
     * 修改游戏数学学期计划
     *
     * @param byMathTermplan 游戏数学学期计划
     * @return 结果
     */
    @Override
    public int updateByMathTermplan(ByMathTermplan byMathTermplan)
    {
                                                                                                                                                                                                                                                                                                        return byMathTermplanMapper.updateByMathTermplan(byMathTermplan);
    }

    /**
     * 批量删除游戏数学学期计划
     *
     * @param ids 需要删除的游戏数学学期计划ID
     * @return 结果
     */
    @Override
    public int deleteByMathTermplanByIds(String[] ids)
    {
        return byMathTermplanMapper.deleteByMathTermplanByIds(ids);
    }

    /**
     * 删除游戏数学学期计划信息
     *
     * @param id 游戏数学学期计划ID
     * @return 结果
     */
    @Override
    public int deleteByMathTermplanById(String id)
    {
        return byMathTermplanMapper.deleteByMathTermplanById(id);
    }
}
