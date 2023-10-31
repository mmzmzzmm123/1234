package com.ruoyi.onethinker.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.onethinker.mapper.ActivityMapper;
import com.ruoyi.onethinker.domain.Activity;
import com.ruoyi.onethinker.service.IActivityService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

/**
 * 活动Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class ActivityServiceImpl implements IActivityService {
    @Resource
    private ActivityMapper activityMapper;

    /**
     * 查询活动
     *
     * @param id 活动主键
     * @return 活动
     */
    @Override
    public Activity selectActivityById(Long id) {
        return activityMapper.selectActivityById(id);
    }

    /**
     * 查询活动列表
     *
     * @param activity 活动
     * @return 活动
     */
    @Override
    public List<Activity> selectActivityList(Activity activity) {
        return activityMapper.selectActivityList(activity);
    }

    /**
     * 新增活动
     *
     * @param activity 活动
     * @return 结果
     */
    @Override
    public int insertActivity(Activity activity) {
        activity.setCreateTime(DateUtils.getNowDate());
        return activityMapper.insertActivity(activity);
    }

    /**
     * 修改活动
     *
     * @param activity 活动
     * @return 结果
     */
    @Override
    public int updateActivity(Activity activity) {
        activity.setUpdateTime(DateUtils.getNowDate());
        return activityMapper.updateActivity(activity);
    }

    /**
     * 批量删除活动
     *
     * @param ids 需要删除的活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityByIds(Long[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    /**
     * 删除活动信息
     *
     * @param id 活动主键
     * @return 结果
     */
    @Override
    public int deleteActivityById(Long id) {
        return activityMapper.deleteActivityById(id);
    }
}
