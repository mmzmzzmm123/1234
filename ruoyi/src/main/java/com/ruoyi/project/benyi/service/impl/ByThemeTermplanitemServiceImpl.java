package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeTermplanitemMapper;
import com.ruoyi.project.benyi.domain.ByThemeTermplanitem;
import com.ruoyi.project.benyi.service.IByThemeTermplanitemService;

/**
 * 主题整合学期计划明细Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-24
 */
@Service
public class ByThemeTermplanitemServiceImpl implements IByThemeTermplanitemService {
    @Autowired
    private ByThemeTermplanitemMapper byThemeTermplanitemMapper;

    /**
     * 查询主题整合学期计划明细
     *
     * @param id 主题整合学期计划明细ID
     * @return 主题整合学期计划明细
     */
    @Override
    public ByThemeTermplanitem selectByThemeTermplanitemById(Long id) {
        return byThemeTermplanitemMapper.selectByThemeTermplanitemById(id);
    }

    /**
     * 查询主题整合学期计划明细列表
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 主题整合学期计划明细
     */
    @Override
    public List<ByThemeTermplanitem> selectByThemeTermplanitemList(ByThemeTermplanitem byThemeTermplanitem) {
        return byThemeTermplanitemMapper.selectByThemeTermplanitemList(byThemeTermplanitem);
    }

    /**
     * 新增主题整合学期计划明细
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 结果
     */
    @Override
    public int insertByThemeTermplanitem(ByThemeTermplanitem byThemeTermplanitem) {
        return byThemeTermplanitemMapper.insertByThemeTermplanitem(byThemeTermplanitem);
    }

    /**
     * 修改主题整合学期计划明细
     *
     * @param byThemeTermplanitem 主题整合学期计划明细
     * @return 结果
     */
    @Override
    public int updateByThemeTermplanitem(ByThemeTermplanitem byThemeTermplanitem) {
        byThemeTermplanitem.setUpdateTime(DateUtils.getNowDate());
        return byThemeTermplanitemMapper.updateByThemeTermplanitem(byThemeTermplanitem);
    }

    /**
     * 批量删除主题整合学期计划明细
     *
     * @param ids 需要删除的主题整合学期计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByThemeTermplanitemByIds(Long[] ids) {
        return byThemeTermplanitemMapper.deleteByThemeTermplanitemByIds(ids);
    }

    /**
     * 删除主题整合学期计划明细信息
     *
     * @param id 主题整合学期计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByThemeTermplanitemById(Long id) {
        return byThemeTermplanitemMapper.deleteByThemeTermplanitemById(id);
    }
}
