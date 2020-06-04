package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayFlowUnscrambleMapper;
import com.ruoyi.project.benyi.domain.ByDayFlowUnscramble;
import com.ruoyi.project.benyi.service.IByDayFlowUnscrambleService;

/**
 * 一日流程解读Service业务层处理
 *
 * @author tsbz
 * @date 2020-06-04
 */
@Service
public class ByDayFlowUnscrambleServiceImpl implements IByDayFlowUnscrambleService {
    @Autowired
    private ByDayFlowUnscrambleMapper byDayFlowUnscrambleMapper;

    /**
     * 查询一日流程解读
     *
     * @param id 一日流程解读ID
     * @return 一日流程解读
     */
    @Override
    public ByDayFlowUnscramble selectByDayFlowUnscrambleById(Long id) {
        return byDayFlowUnscrambleMapper.selectByDayFlowUnscrambleById(id);
    }

    /**
     * 查询一日流程解读列表
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 一日流程解读
     */
    @Override
    public List<ByDayFlowUnscramble> selectByDayFlowUnscrambleList(ByDayFlowUnscramble byDayFlowUnscramble) {
        return byDayFlowUnscrambleMapper.selectByDayFlowUnscrambleList(byDayFlowUnscramble);
    }

    /**
     * 新增一日流程解读
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 结果
     */
    @Override
    public int insertByDayFlowUnscramble(ByDayFlowUnscramble byDayFlowUnscramble) {
        byDayFlowUnscramble.setCreateTime(DateUtils.getNowDate());
        return byDayFlowUnscrambleMapper.insertByDayFlowUnscramble(byDayFlowUnscramble);
    }

    /**
     * 修改一日流程解读
     *
     * @param byDayFlowUnscramble 一日流程解读
     * @return 结果
     */
    @Override
    public int updateByDayFlowUnscramble(ByDayFlowUnscramble byDayFlowUnscramble) {
        return byDayFlowUnscrambleMapper.updateByDayFlowUnscramble(byDayFlowUnscramble);
    }

    /**
     * 批量删除一日流程解读
     *
     * @param ids 需要删除的一日流程解读ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowUnscrambleByIds(Long[] ids) {
        return byDayFlowUnscrambleMapper.deleteByDayFlowUnscrambleByIds(ids);
    }

    /**
     * 删除一日流程解读信息
     *
     * @param id 一日流程解读ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowUnscrambleById(Long id) {
        return byDayFlowUnscrambleMapper.deleteByDayFlowUnscrambleById(id);
    }
}
