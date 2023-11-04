package com.ruoyi.onethinker.service.impl;

import java.util.List;

import com.ruoyi.common.enums.ActivityTypeEnum;
import com.ruoyi.common.utils.DateUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.onethinker.dto.ActivityReqDTO;
import com.ruoyi.onethinker.factory.ActivityDetailFactory;
import com.ruoyi.onethinker.factory.service.IActivityDetailService;
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

    @Autowired
    private ActivityDetailFactory activityDetailFactory;

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
     * @param reqDTO 活动
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertActivity(ActivityReqDTO reqDTO) {
        // 常规校验
        reqDTO.existsParams();
        Activity activity = new Activity();
        BeanUtils.copyProperties(reqDTO,activity);
        activity.setCreateTime(DateUtils.getNowDate());
        activity.setWeight(System.currentTimeMillis());
        int i = activityMapper.insertActivity(activity);
        if (i == 0) {
            throw new RuntimeException("活动创建有误，请稍后再试");
        }
        // 保存活动明细数据
        IActivityDetailService activityDetailService = activityDetailFactory.queryActivityDetailByActivityType(reqDTO.getActivityType());
        int result = activityDetailService.saveEntry(reqDTO);
        if (result == 0) {
            throw new RuntimeException("活动明细创建有误，请稍后再试");
        }
        return result;
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
