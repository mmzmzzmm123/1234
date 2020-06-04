package com.ruoyi.project.benyi.service;

import java.util.List;
import com.ruoyi.project.benyi.domain.ByDayFlowUnscramble;

/**
 * 一日流程解读Service接口
 *
 * @author tsbz
 * @date 2020-06-04
 */
public interface IByDayFlowUnscrambleService
{
    /**
     * 查询一日流程解读
     *
     * @param id 一日流程解读ID
     * @return 一日流程解读
     */
    public ByDayFlowUnscramble selectByDayFlowUnscrambleById(Long id);

    /**
     * 查询一日流程解读列表
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 一日流程解读集合
     */
    public List<ByDayFlowUnscramble> selectByDayFlowUnscrambleList(ByDayFlowUnscramble byDayFlowUnscramble);

    /**
     * 新增一日流程解读
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 结果
     */
    public int insertByDayFlowUnscramble(ByDayFlowUnscramble byDayFlowUnscramble);

    /**
     * 修改一日流程解读
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 结果
     */
    public int updateByDayFlowUnscramble(ByDayFlowUnscramble byDayFlowUnscramble);

    /**
     * 批量删除一日流程解读
     *
     * @param ids 需要删除的一日流程解读ID
     * @return 结果
     */
    public int deleteByDayFlowUnscrambleByIds(Long[] ids);

    /**
     * 删除一日流程解读信息
     *
     * @param id 一日流程解读ID
     * @return 结果
     */
    public int deleteByDayFlowUnscrambleById(Long id);
}
