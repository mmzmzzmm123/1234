package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByThemeActivityMapper;
import com.ruoyi.project.benyi.domain.ByThemeActivity;
import com.ruoyi.project.benyi.service.IByThemeActivityService;

/**
 * 主题整合活动Service业务层处理
 *
 * @author tsbz
 * @date 2020-07-01
 */
@Service
public class ByThemeActivityServiceImpl implements IByThemeActivityService {
    @Autowired
    private ByThemeActivityMapper byThemeActivityMapper;

    /**
     * 查询主题整合活动
     *
     * @param id 主题整合活动ID
     * @return 主题整合活动
     */
    @Override
    public ByThemeActivity selectByThemeActivityById(Long id) {
        return byThemeActivityMapper.selectByThemeActivityById(id);
    }

    /**
     * 查询主题整合活动列表
     *
     * @param byThemeActivity 主题整合活动
     * @return 主题整合活动
     */
    @Override
    public List<ByThemeActivity> selectByThemeActivityList(ByThemeActivity byThemeActivity) {
        return byThemeActivityMapper.selectByThemeActivityList(byThemeActivity);
    }

    /**
     * 新增主题整合活动
     *
     * @param byThemeActivity 主题整合活动
     * @return 结果
     */
    @Override
    public int insertByThemeActivity(ByThemeActivity byThemeActivity) {
        byThemeActivity.setCreateTime(DateUtils.getNowDate());
        return byThemeActivityMapper.insertByThemeActivity(byThemeActivity);
    }

    /**
     * 修改主题整合活动
     *
     * @param byThemeActivity 主题整合活动
     * @return 结果
     */
    @Override
    public int updateByThemeActivity(ByThemeActivity byThemeActivity) {
        return byThemeActivityMapper.updateByThemeActivity(byThemeActivity);
    }

    /**
     * 批量删除主题整合活动
     *
     * @param ids 需要删除的主题整合活动ID
     * @return 结果
     */
    @Override
    public int deleteByThemeActivityByIds(Long[] ids) {
        return byThemeActivityMapper.deleteByThemeActivityByIds(ids);
    }

    /**
     * 删除主题整合活动信息
     *
     * @param id 主题整合活动ID
     * @return 结果
     */
    @Override
    public int deleteByThemeActivityById(Long id) {
        return byThemeActivityMapper.deleteByThemeActivityById(id);
    }

    /**
     * 查询主题整合活动列表
     *
     * @param ids 主题整合活动
     * @return 主题整合活动集合
     */
    @Override
    public List<ByThemeActivity> selectByThemeActivityByThemeIds(Long[] ids){
        return byThemeActivityMapper.selectByThemeActivityByThemeIds(ids);
    }
}
