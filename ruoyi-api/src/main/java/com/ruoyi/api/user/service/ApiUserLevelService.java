package com.ruoyi.api.user.service;

import com.ruoyi.api.user.model.vo.ApiUserLevelConfigVo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.user.domain.UserLevelConfig;
import com.ruoyi.user.mapper.UserLevelConfigMapper;
import com.ruoyi.user.mapper.UserLevelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * */
    public List<ApiUserLevelConfigVo> selectUserLevelConfig() {

        return null;
    }
}
