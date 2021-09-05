package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeWeekplanitemMapper;
import com.ruoyi.project.benyi.domain.ByThemeWeekplanitem;
import com.ruoyi.project.benyi.service.IByThemeWeekplanitemService;

/**
 * 主题整合周计划明细Service业务层处理
 *
 * @author tsbz
 * @date 2020-08-26
 */
@Service
public class ByThemeWeekplanitemServiceImpl implements IByThemeWeekplanitemService {
    @Autowired
    private ByThemeWeekplanitemMapper byThemeWeekplanitemMapper;

    /**
     * 查询主题整合周计划明细
     *
     * @param id 主题整合周计划明细ID
     * @return 主题整合周计划明细
     */
    @Override
    public ByThemeWeekplanitem selectByThemeWeekplanitemById(Long id) {
        return byThemeWeekplanitemMapper.selectByThemeWeekplanitemById(id);
    }

    /**
     * 查询主题整合周计划明细列表
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 主题整合周计划明细
     */
    @Override
    public List<ByThemeWeekplanitem> selectByThemeWeekplanitemList(ByThemeWeekplanitem byThemeWeekplanitem) {
        return byThemeWeekplanitemMapper.selectByThemeWeekplanitemList(byThemeWeekplanitem);
    }

    /**
     * 新增主题整合周计划明细
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 结果
     */
    @Override
    public int insertByThemeWeekplanitem(ByThemeWeekplanitem byThemeWeekplanitem) {
        byThemeWeekplanitem.setCreateTime(DateUtils.getNowDate());
        return byThemeWeekplanitemMapper.insertByThemeWeekplanitem(byThemeWeekplanitem);
    }

    /**
     * 修改主题整合周计划明细
     *
     * @param byThemeWeekplanitem 主题整合周计划明细
     * @return 结果
     */
    @Override
    public int updateByThemeWeekplanitem(ByThemeWeekplanitem byThemeWeekplanitem) {
        byThemeWeekplanitem.setUpdateTime(DateUtils.getNowDate());
        return byThemeWeekplanitemMapper.updateByThemeWeekplanitem(byThemeWeekplanitem);
    }

    /**
     * 批量删除主题整合周计划明细
     *
     * @param ids 需要删除的主题整合周计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByThemeWeekplanitemByIds(Long[] ids) {
        return byThemeWeekplanitemMapper.deleteByThemeWeekplanitemByIds(ids);
    }

    /**
     * 删除主题整合周计划明细信息
     *
     * @param id 主题整合周计划明细ID
     * @return 结果
     */
    @Override
    public int deleteByThemeWeekplanitemById(Long id) {
        return byThemeWeekplanitemMapper.deleteByThemeWeekplanitemById(id);
    }

    /**
     * 删除主题整合周计划明细信息
     *
     * @param pid 主题周计划主键
     * @return 结果
     */
    @Override
    public int deleteByThemeWeekplanitemByPId(String pid) {
        return byThemeWeekplanitemMapper.deleteByThemeWeekplanitemByPId(pid);
    }
}
