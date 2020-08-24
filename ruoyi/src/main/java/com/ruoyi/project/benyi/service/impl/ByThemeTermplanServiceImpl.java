package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeTermplanMapper;
import com.ruoyi.project.benyi.domain.ByThemeTermplan;
import com.ruoyi.project.benyi.service.IByThemeTermplanService;

/**
 * 主题整合学期计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-24
 */
@Service
public class ByThemeTermplanServiceImpl implements IByThemeTermplanService {
    @Autowired
    private ByThemeTermplanMapper byThemeTermplanMapper;

    /**
     * 查询主题整合学期计划
     *
     * @param id 主题整合学期计划ID
     * @return 主题整合学期计划
     */
    @Override
    public ByThemeTermplan selectByThemeTermplanById(String id) {
        return byThemeTermplanMapper.selectByThemeTermplanById(id);
    }

    /**
     * 查询主题整合学期计划列表
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 主题整合学期计划
     */
    @Override
    public List<ByThemeTermplan> selectByThemeTermplanList(ByThemeTermplan byThemeTermplan) {
        return byThemeTermplanMapper.selectByThemeTermplanList(byThemeTermplan);
    }

    /**
     * 新增主题整合学期计划
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 结果
     */
    @Override
    public int insertByThemeTermplan(ByThemeTermplan byThemeTermplan) {
        byThemeTermplan.setCreateTime(DateUtils.getNowDate());
        return byThemeTermplanMapper.insertByThemeTermplan(byThemeTermplan);
    }

    /**
     * 修改主题整合学期计划
     *
     * @param byThemeTermplan 主题整合学期计划
     * @return 结果
     */
    @Override
    public int updateByThemeTermplan(ByThemeTermplan byThemeTermplan) {
        return byThemeTermplanMapper.updateByThemeTermplan(byThemeTermplan);
    }

    /**
     * 批量删除主题整合学期计划
     *
     * @param ids 需要删除的主题整合学期计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeTermplanByIds(String[] ids) {
        return byThemeTermplanMapper.deleteByThemeTermplanByIds(ids);
    }

    /**
     * 删除主题整合学期计划信息
     *
     * @param id 主题整合学期计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeTermplanById(String id) {
        return byThemeTermplanMapper.deleteByThemeTermplanById(id);
    }
}
