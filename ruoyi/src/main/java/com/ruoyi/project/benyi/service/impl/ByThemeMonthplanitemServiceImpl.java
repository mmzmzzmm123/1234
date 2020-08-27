package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeMonthplanitemMapper;
import com.ruoyi.project.benyi.domain.ByThemeMonthplanitem;
import com.ruoyi.project.benyi.service.IByThemeMonthplanitemService;

/**
 * 主题整合周计划Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-25
 */
@Service
public class ByThemeMonthplanitemServiceImpl implements IByThemeMonthplanitemService {
    @Autowired
    private ByThemeMonthplanitemMapper byThemeMonthplanitemMapper;

    /**
     * 查询主题整合周计划
     *
     * @param id 主题整合周计划ID
     * @return 主题整合周计划
     */
    @Override
    public ByThemeMonthplanitem selectByThemeMonthplanitemById(String id) {
        return byThemeMonthplanitemMapper.selectByThemeMonthplanitemById(id);
    }

    /**
     * 查询主题整合周计划列表
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 主题整合周计划
     */
    @Override
    public List<ByThemeMonthplanitem> selectByThemeMonthplanitemList(ByThemeMonthplanitem byThemeMonthplanitem) {
        return byThemeMonthplanitemMapper.selectByThemeMonthplanitemList(byThemeMonthplanitem);
    }

    /**
     * 新增主题整合周计划
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 结果
     */
    @Override
    public int insertByThemeMonthplanitem(ByThemeMonthplanitem byThemeMonthplanitem) {
        byThemeMonthplanitem.setCreateTime(DateUtils.getNowDate());
        return byThemeMonthplanitemMapper.insertByThemeMonthplanitem(byThemeMonthplanitem);
    }

    /**
     * 修改主题整合周计划
     *
     * @param byThemeMonthplanitem 主题整合周计划
     * @return 结果
     */
    @Override
    public int updateByThemeMonthplanitem(ByThemeMonthplanitem byThemeMonthplanitem) {
        return byThemeMonthplanitemMapper.updateByThemeMonthplanitem(byThemeMonthplanitem);
    }

    /**
     * 批量删除主题整合周计划
     *
     * @param ids 需要删除的主题整合周计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeMonthplanitemByIds(String[] ids) {
        return byThemeMonthplanitemMapper.deleteByThemeMonthplanitemByIds(ids);
    }

    /**
     * 删除主题整合周计划信息
     *
     * @param id 主题整合周计划ID
     * @return 结果
     */
    @Override
    public int deleteByThemeMonthplanitemById(String id) {
        return byThemeMonthplanitemMapper.deleteByThemeMonthplanitemById(id);
    }
}
