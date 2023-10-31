package com.ruoyi.onethinker.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.onethinker.mapper.ActivityTypeMapper;
import com.ruoyi.onethinker.domain.ActivityType;
import com.ruoyi.onethinker.service.IActivityTypeService;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Resource;

/**
 * 活动类型Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class ActivityTypeServiceImpl implements IActivityTypeService {
    @Resource
    private ActivityTypeMapper activityTypeMapper;

    /**
     * 查询活动类型
     *
     * @param id 活动类型主键
     * @return 活动类型
     */
    @Override
    public ActivityType selectActivityTypeById(Long id) {
        return activityTypeMapper.selectActivityTypeById(id);
    }

    /**
     * 查询活动类型列表
     *
     * @param activityType 活动类型
     * @return 活动类型
     */
    @Override
    public List<ActivityType> selectActivityTypeList(ActivityType activityType) {
        return activityTypeMapper.selectActivityTypeList(activityType);
    }

    /**
     * 新增活动类型
     *
     * @param activityType 活动类型
     * @return 结果
     */
    @Override
    public int insertActivityType(ActivityType activityType) {
            return activityTypeMapper.insertActivityType(activityType);
    }

    /**
     * 修改活动类型
     *
     * @param activityType 活动类型
     * @return 结果
     */
    @Override
    public int updateActivityType(ActivityType activityType) {
        return activityTypeMapper.updateActivityType(activityType);
    }

    /**
     * 批量删除活动类型
     *
     * @param ids 需要删除的活动类型主键
     * @return 结果
     */
    @Override
    public int deleteActivityTypeByIds(Long[] ids) {
        return activityTypeMapper.deleteActivityTypeByIds(ids);
    }

    /**
     * 删除活动类型信息
     *
     * @param id 活动类型主键
     * @return 结果
     */
    @Override
    public int deleteActivityTypeById(Long id) {
        return activityTypeMapper.deleteActivityTypeById(id);
    }
}
