package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeWeekplanMapper;
import com.ruoyi.project.benyi.domain.ByThemeWeekplan;
import com.ruoyi.project.benyi.service.IByThemeWeekplanService;

/**
 * 主题整合周计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-25
 */
@Service
public class ByThemeWeekplanServiceImpl implements IByThemeWeekplanService {
    @Autowired
    private ByThemeWeekplanMapper byThemeWeekplanMapper;

    /**
     * 查询主题整合周计划
     *
     * @param id 主题整合周计划ID
     * @return 主题整合周计划
     */
    @Override
    public ByThemeWeekplan selectByThemeWeekplanById(String id) {
        return byThemeWeekplanMapper.selectByThemeWeekplanById(id);
    }

    /**
     * 查询主题整合周计划列表
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 主题整合周计划
     */
    @Override
    public List<ByThemeWeekplan> selectByThemeWeekplanList(ByThemeWeekplan byThemeWeekplan) {
        return byThemeWeekplanMapper.selectByThemeWeekplanList(byThemeWeekplan);
    }

    /**
     * 新增主题整合周计划
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 结果
     */
    @Override
    public int insertByThemeWeekplan(ByThemeWeekplan byThemeWeekplan) {
        byThemeWeekplan.setCreateTime(DateUtils.getNowDate());
        return byThemeWeekplanMapper.insertByThemeWeekplan(byThemeWeekplan);
    }

    /**
     * 修改主题整合周计划
     *
     * @param byThemeWeekplan 主题整合周计划
     * @return 结果
     */
    @Override
    public int updateByThemeWeekplan(ByThemeWeekplan byThemeWeekplan) {
        return byThemeWeekplanMapper.updateByThemeWeekplan(byThemeWeekplan);
    }

    /**
     * 批量删除主题整合周计划
     *
     * @param ids 需要删除的主题整合周计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeWeekplanByIds(String[] ids) {
        return byThemeWeekplanMapper.deleteByThemeWeekplanByIds(ids);
    }

    /**
     * 删除主题整合周计划信息
     *
     * @param id 主题整合周计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeWeekplanById(String id) {
        return byThemeWeekplanMapper.deleteByThemeWeekplanById(id);
    }
}
