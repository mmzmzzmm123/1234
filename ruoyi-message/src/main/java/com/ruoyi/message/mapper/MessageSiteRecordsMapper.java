package com.ruoyi.message.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.message.domain.MessageSiteRecords;

/**
 * 站内信消息Mapper接口
 * 
 * @author lixin
 * @date 2022-12-24
 */
public interface MessageSiteRecordsMapper extends BaseMapper<MessageSiteRecords>
{
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
    public int insertMessageSiteRecords(MessageSiteRecords messageSiteRecords);

    /**
     * 修改站内信消息
     *
     * @param messageSiteRecords 站内信消息
     * @return 结果
     */
    public int updateMessageSiteRecords(MessageSiteRecords messageSiteRecords);

    /**
     * 删除站内信消息
     *
     * @param msgSiteId 站内信消息主键
     * @return 结果
     */
    public int deleteMessageSiteRecordsByMsgSiteId(Long msgSiteId);

    /**
     * 批量删除站内信消息
     *
     * @param msgSiteIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageSiteRecordsByMsgSiteIds(Long[] msgSiteIds);
}
