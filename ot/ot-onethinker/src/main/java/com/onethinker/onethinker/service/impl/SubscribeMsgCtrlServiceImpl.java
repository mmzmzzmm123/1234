package com.onethinker.onethinker.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.onethinker.domain.SubscribeMsgCtrl;
import com.onethinker.onethinker.mapper.SubscribeMsgCtrlMapper;
import com.onethinker.onethinker.service.ISubscribeMsgCtrlService;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.StringUtils;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订阅消息控制Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-15
 */
@Service
@Log4j2
public class SubscribeMsgCtrlServiceImpl extends ServiceImpl<SubscribeMsgCtrlMapper, SubscribeMsgCtrl> implements ISubscribeMsgCtrlService {
    @Resource
    private SubscribeMsgCtrlMapper subscribeMsgCtrlMapper;
    @Resource
    private RedisCache redisCache;

    /**
     * 查询订阅消息控制
     *
     * @param id 订阅消息控制主键
     * @return 订阅消息控制
     */
    @Override
    public SubscribeMsgCtrl selectSubscribeMsgCtrlById(Long id) {
        return subscribeMsgCtrlMapper.selectSubscribeMsgCtrlById(id);
    }

    /**
     * 查询订阅消息控制列表
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 订阅消息控制
     */
    @Override
    public List<SubscribeMsgCtrl> selectSubscribeMsgCtrlList(SubscribeMsgCtrl subscribeMsgCtrl) {
        return subscribeMsgCtrlMapper.selectSubscribeMsgCtrlList(subscribeMsgCtrl);
    }

    /**
     * 新增订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    @Override
    public int insertSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl) {
        subscribeMsgCtrl.setCreateTime(DateUtils.getNowDate());
        return subscribeMsgCtrlMapper.insertSubscribeMsgCtrl(subscribeMsgCtrl);
    }

    /**
     * 修改订阅消息控制
     *
     * @param subscribeMsgCtrl 订阅消息控制
     * @return 结果
     */
    @Override
    public int updateSubscribeMsgCtrl(SubscribeMsgCtrl subscribeMsgCtrl) {
        subscribeMsgCtrl.setUpdateTime(DateUtils.getNowDate());
        return subscribeMsgCtrlMapper.updateSubscribeMsgCtrl(subscribeMsgCtrl);
    }

    /**
     * 批量删除订阅消息控制
     *
     * @param ids 需要删除的订阅消息控制主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeMsgCtrlByIds(Long[] ids) {
        return subscribeMsgCtrlMapper.deleteSubscribeMsgCtrlByIds(ids);
    }

    /**
     * 删除订阅消息控制信息
     *
     * @param id 订阅消息控制主键
     * @return 结果
     */
    @Override
    public int deleteSubscribeMsgCtrlById(Long id) {
        return subscribeMsgCtrlMapper.deleteSubscribeMsgCtrlById(id);
    }

    @Override
    public SubscribeMsgCtrl findEntryByTemplateId(String templateId) {
        Assert.isTrue(!StringUtils.isEmpty(templateId), "模版id不能为空");
        String redisKey = CacheEnum.SUBSCRIBE_MSG_KEY.getCode() + templateId;
        if (redisCache.hasKey(redisKey)) {
            return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(), SubscribeMsgCtrl.class);
        }
        LambdaQueryWrapper<SubscribeMsgCtrl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubscribeMsgCtrl::getTemplateId, templateId).eq(SubscribeMsgCtrl::getEnabled, SubscribeMsgCtrl.STATE_TYPE_ENABLED);
        SubscribeMsgCtrl subscribeMsgCtrl = subscribeMsgCtrlMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(subscribeMsgCtrl)) {
            return null;
        }
        redisCache.setCacheObject(redisKey, subscribeMsgCtrl);
        return subscribeMsgCtrl;
    }
}
