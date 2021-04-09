package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByDayFlowStandardMapper;
import com.ruoyi.project.benyi.domain.ByDayFlowStandard;
import com.ruoyi.project.benyi.service.IByDayFlowStandardService;

/**
 * 一日流程标准Service业务层处理
 *
 * @author tsbz
 * @date 2020-05-18
 */
@Service
public class ByDayFlowStandardServiceImpl implements IByDayFlowStandardService {
    @Autowired
    private ByDayFlowStandardMapper byDayFlowStandardMapper;

    /**
     * 查询一日流程标准
     *
     * @param sid 一日流程标准ID
     * @return 一日流程标准
     */
    @Override
    public ByDayFlowStandard selectByDayFlowStandardById(Long sid) {
        return byDayFlowStandardMapper.selectByDayFlowStandardById(sid);
    }

    /**
     * 查询一日流程标准列表
     *
     * @param byDayFlowStandard 一日流程标准
     * @return 一日流程标准
     */
    @Override
    public List<ByDayFlowStandard> selectByDayFlowStandardList(ByDayFlowStandard byDayFlowStandard) {
        return byDayFlowStandardMapper.selectByDayFlowStandardList(byDayFlowStandard);
    }

    /**
     * 查询一日流程标准列表
     *
     * @param byDayFlowStandard 一日流程标准
     * @return 一日流程标准集合
     */
    @Override
    public List<ByDayFlowStandard> selectByDayFlowStandardListAssessment(ByDayFlowStandard byDayFlowStandard) {
        return byDayFlowStandardMapper.selectByDayFlowStandardListAssessment(byDayFlowStandard);
    }

    /**
     * 新增一日流程标准
     *
     * @param byDayFlowStandard 一日流程标准
     * @return 结果
     */
    @Override
    public int insertByDayFlowStandard(ByDayFlowStandard byDayFlowStandard) {
        return byDayFlowStandardMapper.insertByDayFlowStandard(byDayFlowStandard);
    }

    /**
     * 修改一日流程标准
     *
     * @param byDayFlowStandard 一日流程标准
     * @return 结果
     */
    @Override
    public int updateByDayFlowStandard(ByDayFlowStandard byDayFlowStandard) {
        return byDayFlowStandardMapper.updateByDayFlowStandard(byDayFlowStandard);
    }

    /**
     * 批量删除一日流程标准
     *
     * @param sids 需要删除的一日流程标准ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowStandardByIds(Long[] sids) {
        return byDayFlowStandardMapper.deleteByDayFlowStandardByIds(sids);
    }

    /**
     * 删除一日流程标准信息
     *
     * @param sid 一日流程标准ID
     * @return 结果
     */
    @Override
    public int deleteByDayFlowStandardById(Long sid) {
        return byDayFlowStandardMapper.deleteByDayFlowStandardById(sid);
    }
}
