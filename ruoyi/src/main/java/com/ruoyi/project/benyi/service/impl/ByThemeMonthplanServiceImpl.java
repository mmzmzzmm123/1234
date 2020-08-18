package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeMonthplanMapper;
import com.ruoyi.project.benyi.domain.ByThemeMonthplan;
import com.ruoyi.project.benyi.service.IByThemeMonthplanService;

/**
 * 主题整合月计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-17
 */
@Service
public class ByThemeMonthplanServiceImpl implements IByThemeMonthplanService {
    @Autowired
    private ByThemeMonthplanMapper byThemeMonthplanMapper;

    /**
     * 查询主题整合月计划
     *
     * @param id 主题整合月计划ID
     * @return 主题整合月计划
     */
    @Override
    public ByThemeMonthplan selectByThemeMonthplanById(Long id) {
        return byThemeMonthplanMapper.selectByThemeMonthplanById(id);
    }

    /**
     * 查询主题整合月计划列表
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 主题整合月计划
     */
    @Override
    public List<ByThemeMonthplan> selectByThemeMonthplanList(ByThemeMonthplan byThemeMonthplan) {
        return byThemeMonthplanMapper.selectByThemeMonthplanList(byThemeMonthplan);
    }

    /**
     * 新增主题整合月计划
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 结果
     */
    @Override
    public int insertByThemeMonthplan(ByThemeMonthplan byThemeMonthplan) {
        byThemeMonthplan.setCreateTime(DateUtils.getNowDate());
        return byThemeMonthplanMapper.insertByThemeMonthplan(byThemeMonthplan);
    }

    /**
     * 修改主题整合月计划
     *
     * @param byThemeMonthplan 主题整合月计划
     * @return 结果
     */
    @Override
    public int updateByThemeMonthplan(ByThemeMonthplan byThemeMonthplan) {
        return byThemeMonthplanMapper.updateByThemeMonthplan(byThemeMonthplan);
    }

    /**
     * 批量删除主题整合月计划
     *
     * @param ids 需要删除的主题整合月计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeMonthplanByIds(Long[] ids) {
        return byThemeMonthplanMapper.deleteByThemeMonthplanByIds(ids);
    }

    /**
     * 删除主题整合月计划信息
     *
     * @param id 主题整合月计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeMonthplanById(Long id) {
        return byThemeMonthplanMapper.deleteByThemeMonthplanById(id);
    }
}
