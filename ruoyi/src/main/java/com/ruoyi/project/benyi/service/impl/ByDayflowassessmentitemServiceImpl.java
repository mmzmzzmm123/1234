package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayflowassessmentitemMapper;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentitem;
import com.ruoyi.project.benyi.service.IByDayflowassessmentitemService;

/**
 * 幼儿园一日流程评估得分项Service业务层处理
 *
 * @author tsbz
 * @date 2021-04-05
 */
@Service
public class ByDayflowassessmentitemServiceImpl implements IByDayflowassessmentitemService {
    @Autowired
    private ByDayflowassessmentitemMapper byDayflowassessmentitemMapper;

    /**
     * 查询幼儿园一日流程评估得分项
     *
     * @param id 幼儿园一日流程评估得分项ID
     * @return 幼儿园一日流程评估得分项
     */
    @Override
    public ByDayflowassessmentitem selectByDayflowassessmentitemById(Long id) {
        return byDayflowassessmentitemMapper.selectByDayflowassessmentitemById(id);
    }

    /**
     * 查询幼儿园一日流程评估得分项列表
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 幼儿园一日流程评估得分项
     */
    @Override
    public List<ByDayflowassessmentitem> selectByDayflowassessmentitemList(ByDayflowassessmentitem byDayflowassessmentitem) {
        return byDayflowassessmentitemMapper.selectByDayflowassessmentitemList(byDayflowassessmentitem);
    }

    /**
     * 新增幼儿园一日流程评估得分项
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 结果
     */
    @Override
    public int insertByDayflowassessmentitem(ByDayflowassessmentitem byDayflowassessmentitem) {
        byDayflowassessmentitem.setCreateTime(DateUtils.getNowDate());
        return byDayflowassessmentitemMapper.insertByDayflowassessmentitem(byDayflowassessmentitem);
    }

    /**
     * 修改幼儿园一日流程评估得分项
     *
     * @param byDayflowassessmentitem 幼儿园一日流程评估得分项
     * @return 结果
     */
    @Override
    public int updateByDayflowassessmentitem(ByDayflowassessmentitem byDayflowassessmentitem) {
        return byDayflowassessmentitemMapper.updateByDayflowassessmentitem(byDayflowassessmentitem);
    }

    /**
     * 批量删除幼儿园一日流程评估得分项
     *
     * @param ids 需要删除的幼儿园一日流程评估得分项ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentitemByIds(Long[] ids) {
        return byDayflowassessmentitemMapper.deleteByDayflowassessmentitemByIds(ids);
    }

    /**
     * 删除幼儿园一日流程评估得分项信息
     *
     * @param id 幼儿园一日流程评估得分项ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentitemById(Long id) {
        return byDayflowassessmentitemMapper.deleteByDayflowassessmentitemById(id);
    }

    /**
     * 删除幼儿园一日流程评估得分项
     *
     * @param pId 幼儿园一日流程评估得分项ID
     * @return 结果
     */
    @Override
    public int deleteByDayflowassessmentitemByPid(Long pId) {
        return byDayflowassessmentitemMapper.deleteByDayflowassessmentitemByPid(pId);
    }
}
