package com.onethinker.bk.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.WebInfo;
import com.onethinker.bk.mapper.WebInfoMapper;
import com.onethinker.bk.service.IWebInfoService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 网站信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class WebInfoServiceImpl extends ServiceImpl<WebInfoMapper, WebInfo> implements IWebInfoService {
    @Resource
    private WebInfoMapper webInfoMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 修改网站信息
     *
     * @param webInfo 网站信息
     * @return 结果
     */
    @Override
    public int updateWebInfo(WebInfo webInfo) {
        int i = webInfoMapper.updateWebInfo(webInfo);
        // 删除相关网站信息
        redisCache.deleteObject(CacheEnum.WEB_INFO.getCode());
        return i;
    }
    @Override
    public WebInfo getWebInfo() {
        String redisKey = CacheEnum.WEB_INFO.getCode();

        if (redisCache.hasKey(redisKey)) {
            return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), WebInfo.class);
        }
        WebInfo webInfo = webInfoMapper.selectWebInfoById(1L);

        redisCache.setCacheObject(redisKey, webInfo, 1, TimeUnit.DAYS);
        return webInfo;
    }

    @Override
    public String getRandomAvatar(String key, String ipAddr) {
        WebInfo webInfo = getWebInfo();
        if (Objects.isNull(webInfo)) {
            return "";
        }
        String randomAvatar = webInfo.getRandomAvatar();
        List<String> randomAvatars = JSON.parseArray(randomAvatar, String.class);
        if (!CollectionUtils.isEmpty(randomAvatars)) {
            if (StringUtils.hasText(key)) {
                return randomAvatars.get(hashLocation(key, randomAvatars.size()));
            } else if (StringUtils.hasText(ipAddr)) {
                return randomAvatars.get(hashLocation(ipAddr, randomAvatars.size()));
            } else {
                return randomAvatars.get(0);
            }

        }
        return "";
    }

    public static int hashLocation(String key, int length) {
        int h = key.hashCode();
        return (h ^ (h >>> 16)) & (length - 1);
    }
}
