package com.ruoyi.api.user.service;

import com.ruoyi.api.user.model.vo.ApiUserLevelConfigVo;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.LongUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.mapper.UserLevelConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author LAM
 * @date 2023/9/20 14:26
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserLevelService {

    private final UserLevelConfigMapper userLevelConfigMapper;
    private final RedisCache redisCache;


    /**
     * 获取用户等级配置数据
     *
     * @return 结果
     */
    public List<ApiUserLevelConfigVo> selectUserLevelConfig() {
        log.info("获取用户等级配置数据：开始");
        List<ApiUserLevelConfigVo> voList = new ArrayList<>();
        String key = RedisKeyConstants.USER_LEVEL_CONFIG_DATA;
        if (redisCache.hasKey(key)) {
            voList = redisCache.getCacheList(key);
            log.info("获取用户等级配置数据：完成，存在缓存，直接返回：{}", voList);
            return voList;
        }
        List<UserLevelConfig> userLevelConfigs = userLevelConfigMapper.selectUserLevelConfigList(new UserLevelConfig());
        for (UserLevelConfig item : userLevelConfigs) {
            ApiUserLevelConfigVo vo = new ApiUserLevelConfigVo();
            BeanUtils.copyBeanProp(vo, item);
            voList.add(vo);
        }
        redisCache.setCacheList(key, voList);
        redisCache.expire(key, LongUtils.generateRandomNumber(1, 24), TimeUnit.HOURS);
        log.info("获取用户等级配置数据：完成，返回数据：{}", voList);
        return voList;
    }
}
