package com.ruoyi.project.benyi.service.impl;

import java.util.List;
                                                                                            import com.ruoyi.common.utils.DateUtils;
            import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByMathTermplanitemMapper;
import com.ruoyi.project.benyi.domain.ByMathTermplanitem;
import com.ruoyi.project.benyi.service.IByMathTermplanitemService;

/**
 * 游戏数学学期计划明细Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-29
 */
@Service
public class ByMathTermplanitemServiceImpl implements IByMathTermplanitemService
{
    @Autowired
    private ByMathTermplanitemMapper byMathTermplanitemMapper;

    /**
     * 查询游戏数学学期计划明细
     *
     * @param id 游戏数学学期计划明细ID
     * @return 游戏数学学期计划明细
     */
    @Override
    public ByMathTermplanitem selectByMathTermplanitemById(Long id)
    {
        return byMathTermplanitemMapper.selectByMathTermplanitemById(id);
    }

    /**
     * 查询游戏数学学期计划明细列表
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 游戏数学学期计划明细
     */
    @Override
    public List<ByMathTermplanitem> selectByMathTermplanitemList(ByMathTermplanitem byMathTermplanitem)
    {
        return byMathTermplanitemMapper.selectByMathTermplanitemList(byMathTermplanitem);
    }

    /**
     * 新增游戏数学学期计划明细
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 结果
     */
    @Override
    public int insertByMathTermplanitem(ByMathTermplanitem byMathTermplanitem)
    {
                                                                                                                                                            byMathTermplanitem.setCreateTime(DateUtils.getNowDate());
                                                                    return byMathTermplanitemMapper.insertByMathTermplanitem(byMathTermplanitem);
    }

    /**
     * 修改游戏数学学期计划明细
     *
     * @param byMathTermplanitem 游戏数学学期计划明细
     * @return 结果
     */
    @Override
    public int updateByMathTermplanitem(ByMathTermplanitem byMathTermplanitem)
    {
                                                                                                                                                                                                    byMathTermplanitem.setUpdateTime(DateUtils.getNowDate());
                            return byMathTermplanitemMapper.updateByMathTermplanitem(byMathTermplanitem);
    }

    /**
     * 批量删除游戏数学学期计划明细
     *
     * @param ids 需要删除的游戏数学学期计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByMathTermplanitemByIds(Long[] ids)
    {
        return byMathTermplanitemMapper.deleteByMathTermplanitemByIds(ids);
    }

    /**
     * 删除游戏数学学期计划明细信息
     *
     * @param id 游戏数学学期计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByMathTermplanitemById(Long id)
    {
        return byMathTermplanitemMapper.deleteByMathTermplanitemById(id);
    }
}
