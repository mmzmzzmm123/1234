package com.ruoyi.message.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.message.domain.MessageSiteRecords;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * 站内信消息Service接口
 *
 * @author lixin
 * @date 2022-12-24
 */
public interface IMessageSiteRecordsService extends IService<MessageSiteRecords> {
    /**
     * 查询站内信消息
     *
     * @param msgSiteId 站内信消息主键
     * @return 站内信消息
     */
    public MessageSiteRecords selectMessageSiteRecordsByMsgSiteId(Long msgSiteId);

    /**
     * 查询站内信消息列表
     *
     * @param messageSiteRecords 站内信消息
     * @return 站内信消息集合
     */
    public List<MessageSiteRecords> selectMessageSiteRecordsList(MessageSiteRecords messageSiteRecords);

    /**
     * 新增站内信消息
     *
     * @param messageSiteRecords 站内信消息
     * @return 结果
     */
    @CacheEvict(value = CacheConstants.USER_UNREAD_COUNT_KEY, key = "#messageSiteRecords.toUserId")
    public int insertMessageSiteRecords(MessageSiteRecords messageSiteRecords);

    /**
     * 修改站内信消息
     *
     * @param messageSiteRecords 站内信消息
     * @return 结果
     */
    public int updateMessageSiteRecords(MessageSiteRecords messageSiteRecords);

    /**
     * 批量删除站内信消息
     *
     * @param msgSiteIds 需要删除的站内信消息主键集合
     * @return 结果 用户ids
     */
    public Set<Long> deleteMessageSiteRecordsByMsgSiteIds(Long[] msgSiteIds);

    /**
     * 删除站内信消息信息
     *
     * @param msgSiteId 站内信消息主键
     * @return 结果 用户id
     */
    public Long deleteMessageSiteRecordsByMsgSiteId(Long msgSiteId);

    /**
     * 删除超过30天的消息
     */
    @CacheEvict(value = CacheConstants.USER_UNREAD_COUNT_KEY, allEntries = true)
    public void deleteHistory();

    /**
     * 已读消息
     *
     * @param id
     * @param toUserId
     */
    @CacheEvict(value = CacheConstants.USER_UNREAD_COUNT_KEY, key = "#toUserId")
    public void readOne(long id, long toUserId);

    /**
     * 删除消息
     *
     * @param id
     * @param toUserId
     */
    @CacheEvict(value = CacheConstants.USER_UNREAD_COUNT_KEY, key = "#toUserId")
    public void removeOne(long id, long toUserId);

    /**
     * 获得自己的未读的消息数量
     *
     * @param toUserId
     * @return
     */
    @Cacheable(value = CacheConstants.USER_UNREAD_COUNT_KEY, key = "#toUserId", unless = "#result == null")
    public long getSelfUnReadCount(long toUserId);

    /**
     * 拉取站内信最新消息
     * @param toUserId
     * @param lastId 最后读取的id
     * @return
     */
    public List<MessageSiteRecords> pullLast(Long toUserId, long lastId);
    /**
     * 拉取站内信最新消息(倒序)
     * @param toUserId
     * @param lastId 最后读取的id
     * @return
     */
    public List<MessageSiteRecords> pullReLast(Long toUserId, long lastId);
}
