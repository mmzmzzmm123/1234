package com.ruoyi.push.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.push.consts.CommandConst;
import com.ruoyi.push.domain.vo.CommandVo;
import com.ruoyi.push.service.IPushService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lixin
 * @Date 2022/12/29 15:10
 */
@Component
public class PushServiceImpl implements IPushService {

    @Autowired
    private RedisCache redisCache;

    @Override
    public List<CommandVo> pull(String groupName, long lastTime) {
        if (lastTime <= 0) {
            lastTime = DateUtils.addSeconds(DateUtils.getNowDate(), -CommandConst.MSG_CACHE_TIME).getTime();
        }

        // 获取消息
        List<CommandVo> commandVos = redisCache.zSetSortRange(groupName, lastTime, Long.MAX_VALUE).stream().map(e -> JSON.parseObject(e, CommandVo.class)).collect(Collectors.toList());
        return commandVos;
    }

    @Override
    public void cleanExpirationAll() {
        long lastTime = DateUtils.addSeconds(DateUtils.getNowDate(), -CommandConst.MSG_CACHE_TIME).getTime() - 1;

        // 删除全局过期消息
        redisCache.removeRangeByScore(CommandConst.GROUP_NAME_G, 0, lastTime);

        // 获取当前有缓存的记录
        Collection<String> keys = redisCache.keys(CommandConst.GROUP_NAME_U + "*");
        for (String key : keys) {
            // 删除用户过期消息
            redisCache.removeRangeByScore(key, 0, lastTime);
        }
    }

    @Override
    public void addCommand(String groupName, CommandVo vo) {

        // 先生成 时间和 签名
        vo.generate();

        // 添加消息
        redisCache.zSetAdd(groupName, JSON.toJSONString(vo), vo.getD());
    }

    @Override
    public List<CommandVo> pullSelf(long userId, long lastTime) {
        CommandVo lastTimeCommandVo = null;
        CommandVo lastTimeGCommandVo = null;
        CommandVo pullLastTimeCommandVo = null;

        // 拉取用户消息
        List<CommandVo> commandVoList = pull(CommandConst.GROUP_NAME_U + userId, lastTime);

        if (commandVoList.size() > 0) {
            lastTimeCommandVo = commandVoList.get(commandVoList.size() - 1);
        }
        // 拉取全局消息
        List<CommandVo> commandVoListG = pull(CommandConst.GROUP_NAME_G, lastTime);
        if (commandVoListG.size() > 0) {
            lastTimeGCommandVo = commandVoListG.get(commandVoListG.size() - 1);
        }
        commandVoList.addAll(commandVoListG);

        long lastPullTime = 0;
        if (lastTimeCommandVo == null || lastTimeGCommandVo == null) {
            lastPullTime = DateUtils.getNowDate().getTime();
        } else if ((lastTimeCommandVo != null && lastTimeGCommandVo == null) || lastTimeCommandVo.getD() >= lastTimeGCommandVo.getD()) {
            lastPullTime = lastTimeCommandVo.getD();
        } else if (lastTimeCommandVo == null && lastTimeGCommandVo != null || lastTimeCommandVo.getD() <= lastTimeGCommandVo.getD()) {
            lastPullTime = lastTimeGCommandVo.getD();
        }

        // 添加一条拉取记录最后时间
        pullLastTimeCommandVo = new CommandVo(CommandConst.CMD_PULL_LAST_TIME, lastPullTime);
        pullLastTimeCommandVo.generate();
        commandVoList.add(pullLastTimeCommandVo);

        return commandVoList;
    }

    @Override
    public void addPullSitemsg(long userId, Object content) {
        addCommand(CommandConst.GROUP_NAME_U + userId, new CommandVo(CommandConst.CMD_PULL_SITEMSG, content));
        addCommand(CommandConst.GROUP_NAME_U + userId, new CommandVo(CommandConst.CMD_PULL_SITECOUNT, ""));
    }

    @Override
    public void addPullSitecount(long userId) {
        addCommand(CommandConst.GROUP_NAME_U + userId, new CommandVo(CommandConst.CMD_PULL_SITECOUNT, ""));
    }

    @Override
    public void addGlobalNotice(String content) {
        addCommand(CommandConst.GROUP_NAME_G, new CommandVo(CommandConst.CMD_GLOBAL_NOTICE, content));
    }
}
