package com.ruoyi.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.message.domain.MessageSiteRecords;
import com.ruoyi.message.mapper.MessageSiteRecordsMapper;
import com.ruoyi.message.service.IMessageSiteRecordsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 站内信消息Service业务层处理
 *
 * @author lixin
 * @date 2022-12-24
 */
@Service
@AllArgsConstructor
public class MessageSiteRecordsServiceImpl extends ServiceImpl<MessageSiteRecordsMapper, MessageSiteRecords> implements IMessageSiteRecordsService {
    private MessageSiteRecordsMapper messageSiteRecordsMapper;
    private RedisCache redisCache;

    /**
     * 查询站内信消息
     *
     * @param msgSiteId 站内信消息主键
     * @return 站内信消息
     */
    @Override
    public MessageSiteRecords selectMessageSiteRecordsByMsgSiteId(Long msgSiteId) {
        return messageSiteRecordsMapper.selectMessageSiteRecordsByMsgSiteId(msgSiteId);
    }

    /**
     * 查询站内信消息列表
     *
     * @param messageSiteRecords 站内信消息
     * @return 站内信消息
     */
    @Override
    public List<MessageSiteRecords> selectMessageSiteRecordsList(MessageSiteRecords messageSiteRecords) {
        return messageSiteRecordsMapper.selectMessageSiteRecordsList(messageSiteRecords);
    }

    /**
     * 新增站内信消息
     *
     * @param messageSiteRecords 站内信消息
     * @return 结果
     */
    @Override
    public int insertMessageSiteRecords(MessageSiteRecords messageSiteRecords) {
        messageSiteRecords.setCreateTime(DateUtils.getNowDate());
        return messageSiteRecordsMapper.insertMessageSiteRecords(messageSiteRecords);
    }

    /**
     * 修改站内信消息
     *
     * @param messageSiteRecords 站内信消息
     * @return 结果
     */
    @Override
    public int updateMessageSiteRecords(MessageSiteRecords messageSiteRecords) {
        return messageSiteRecordsMapper.updateMessageSiteRecords(messageSiteRecords);
    }

    /**
     * 批量删除站内信消息
     *
     * @param msgSiteIds 需要删除的站内信消息主键
     * @return 结果
     */
    @Override
    public Set<Long> deleteMessageSiteRecordsByMsgSiteIds(Long[] msgSiteIds) {
        Set<Long> userIds = new HashSet<>();
        MessageSiteRecords messageSiteRecords = null;
        for (Long id : msgSiteIds) {
            messageSiteRecords = this.getById(id);
            if (messageSiteRecords != null) {
                redisCache.deleteObject(CacheConstants.USER_UNREAD_COUNT_KEY + messageSiteRecords.getToUserId());
                userIds.add(messageSiteRecords.getToUserId());
            }
        }
        if (messageSiteRecordsMapper.deleteMessageSiteRecordsByMsgSiteIds(msgSiteIds) > 0) {
            return userIds;
        }
        return userIds;
    }

    /**
     * 删除站内信消息信息
     *
     * @param msgSiteId 站内信消息主键
     * @return 结果
     */
    @Override
    public Long deleteMessageSiteRecordsByMsgSiteId(Long msgSiteId) {
        Long userId = null;
        MessageSiteRecords messageSiteRecords = this.getById(msgSiteId);
        if (messageSiteRecords != null) {
            redisCache.deleteObject(CacheConstants.USER_UNREAD_COUNT_KEY + messageSiteRecords.getToUserId());
            userId = messageSiteRecords.getToUserId();
        }
        if (messageSiteRecordsMapper.deleteMessageSiteRecordsByMsgSiteId(msgSiteId) > 0) {
            return userId;
        }
        return null;
    }

    @Override
    public void deleteHistory() {
        LambdaUpdateWrapper<MessageSiteRecords> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.lt(MessageSiteRecords::getCreateTime, DateUtils.addDays(DateUtils.getNowDate(), -30));
        this.remove(lambdaUpdateWrapper);
    }

    @Override
    public void readOne(long id, long toUserId) {
        LambdaUpdateWrapper<MessageSiteRecords> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(MessageSiteRecords::getMsgSiteId, id).eq(MessageSiteRecords::getToUserId, toUserId);
        lambdaUpdateWrapper.set(MessageSiteRecords::getHasRead, '1');
        this.update(lambdaUpdateWrapper);
    }

    @Override
    public void removeOne(long id, long toUserId) {
        LambdaUpdateWrapper<MessageSiteRecords> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(MessageSiteRecords::getMsgSiteId, id).eq(MessageSiteRecords::getToUserId, toUserId);
        this.remove(lambdaUpdateWrapper);
    }

    @Override
    public long getSelfUnReadCount(long toUserId) {
        LambdaUpdateWrapper<MessageSiteRecords> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        lambdaUpdateWrapper.eq(MessageSiteRecords::getHasRead, '0').eq(MessageSiteRecords::getToUserId, toUserId);
        return this.count(lambdaUpdateWrapper);
    }

    @Override
    public List<MessageSiteRecords> pullLast(Long toUserId, long lastId) {
        LambdaQueryWrapper<MessageSiteRecords> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MessageSiteRecords::getToUserId, toUserId).gt(MessageSiteRecords::getMsgSiteId, lastId);
        IPage page = new Page(1, 50);
        return this.baseMapper.selectPage(page, lambdaQueryWrapper).getRecords();
    }

    @Override
    public List<MessageSiteRecords> pullReLast(Long toUserId, long lastId) {
        LambdaQueryWrapper<MessageSiteRecords> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(MessageSiteRecords::getToUserId, toUserId).lt(MessageSiteRecords::getMsgSiteId, lastId);
        lambdaQueryWrapper.orderByDesc(MessageSiteRecords::getMsgSiteId);
        IPage page = new Page(1, 50);
        return this.baseMapper.selectPage(page, lambdaQueryWrapper).getRecords();
    }
}
