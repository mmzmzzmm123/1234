package com.onethinker.activity.service;

import cn.hutool.core.util.StrUtil;
import com.onethinker.activity.domain.Activity;
import com.onethinker.activity.dto.ActivityReqDTO;
import com.onethinker.activity.dto.ActivityResDTO;
import com.onethinker.activity.mapper.ActivityMapper;
import com.onethinker.activity.platform.ActivityStorage;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.ActivityTypeEnum;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.Tools;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author yangyouqi
 * @date 2024/5/31
 */
@Log4j2
@Service
public class ActivityStorageService {

    @Autowired
    private RedisCache redisCache;

    private ConcurrentHashMap<String, ActivityStorage> activityStorageMap = new ConcurrentHashMap<>();

    @Autowired
    private ActivityMapper activityMapper;


    private final String REDIS_KEY = CacheEnum.ACTIVITY_REDIS_KEY.getCode();

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 获取对应的存储平台，如果存储平台不存在则抛出异常
     */
    public <T extends ActivityStorage> T getActivityStorageVerify(ActivityTypeEnum activityTypeEnum) {
        T activityStorage = this.getActivityStorage(activityTypeEnum);
        if (activityStorage == null) {
            throw new RuntimeException(StrUtil.format("没有找到对应的实例平台！platform:{}", activityTypeEnum.getDesc()));
        }
        return activityStorage;
    }

    /**
     * 获取对应活动平台
     */
    private <T extends ActivityStorage> T getActivityStorage(ActivityTypeEnum activityTypeEnum) {
        try {
            if (this.activityStorageMap.get(activityTypeEnum.name()) == null) {
                Assert.isTrue(!ObjectUtils.isEmpty(activityTypeEnum), "userTypeEnum is null ");
                Class<?> clazz = Class.forName(activityTypeEnum.getInterfaceClass());
                Constructor<?> constructor = clazz.getConstructor(ApplicationContext.class);
                constructor.setAccessible(true);
                ActivityStorage instance = (ActivityStorage) constructor.newInstance(applicationContext);
                this.activityStorageMap.put(activityTypeEnum.name(),instance);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ActivityStorage newActivityStorage = this.activityStorageMap.get(activityTypeEnum.name());
        return Tools.cast(newActivityStorage);
    }


    /**
     * 新增活动
     *
     * @param reqDTO 活动
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertActivity(ActivityReqDTO reqDTO) {
        // 常规校验
        reqDTO.existsParams();
        Activity activity = new Activity();
        BeanUtils.copyProperties(reqDTO, activity);
        activity.setCreateTime(DateUtils.getNowDate());
        activity.setWeight(System.currentTimeMillis());
        int i = activityMapper.insertActivity(activity);
        if (i == 0) {
            throw new RuntimeException("活动创建有误，请稍后再试");
        }
        // 保存活动明细数据
        getActivityStorageVerify(reqDTO.getActivityType()).saveActivitCtr(reqDTO);
    }

    /**
     * 修改活动
     *
     * @param reqDTO 活动
     * @return 结果
     */
    public void updateActivity(ActivityReqDTO reqDTO) {
        // 更新对应活动明细数据
        getActivityStorageVerify(reqDTO.getActivityType()).updateEntry(reqDTO);
        // 更新活动主表信息
        Activity activity = new Activity();
        BeanUtils.copyProperties(reqDTO,activity);
        activity.setUpdateTime(DateUtils.getNowDate());
        activityMapper.updateActivity(activity);
        // 删除缓存信息
        redisCache.deleteObject(REDIS_KEY + activity.getId());
    }

    /**
     * 查询活动信息
     * @param activityId 活动
     * @return 结果
     */
    public ActivityResDTO queryActivityBaseInfo(Long activityId) {
        Assert.notNull(activityId, "activityId is null");
        String redisKey = REDIS_KEY + activityId;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheObject(redisKey);
        }
        Activity activity = activityMapper.selectActivityById(activityId);
        ActivityResDTO activityResDTO = new ActivityResDTO();
        BeanUtils.copyProperties(activity,activityResDTO);
        // 获取活动明细数据
        try {
            getActivityStorageVerify(ActivityTypeEnum.valueOf(activity.getActivityType())).getActivityInfo(activityResDTO);
        }catch (Exception e) {
            log.error("getActivityBaseInfo error,activityType:{},activityId:{},errorMsg:{}",activity.getActivityType(),activityId,e.getMessage());
        }
        // 保存到redis
        redisCache.setCacheObject(REDIS_KEY + activity.getId(), activityResDTO,15, TimeUnit.DAYS);
        return activityResDTO;
    }

    /**
     * 批量查询活动数据
     * @param reqDTO
     * @return
     */
    public List<ActivityResDTO> queryActivityBaseInfoList(Activity reqDTO) {
        List<Activity> activities = activityMapper.selectActivityList(reqDTO);
        if (activities.isEmpty()) {
            return Lists.newArrayList();
        }
        return activities.parallelStream().map(e -> queryActivityBaseInfo(e.getId())).collect(Collectors.toList());
    }

    public List<ActivityResDTO> queryActivityDetailByActivityType(ActivityTypeEnum activityTypeEnum) {
        Assert.notNull(activityTypeEnum, "activityTypeEnum is null");
        Activity activity = new Activity();
        activity.setActivityType(activityTypeEnum.name());
        return this.queryActivityBaseInfoList(activity);
    }
}
