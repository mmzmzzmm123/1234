package com.stdiet.custom.mapper;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.dto.response.MessageNoticeResponse;
import org.apache.ibatis.annotations.Param;

/**
 * 客户消息通知Mapper接口
 *
 * @author xzj
 * @date 2021-04-26
 */
public interface SysMessageNoticeMapper
{
    /**
     * 查询客户消息通知
     *
     * @param id 客户消息通知ID
     * @return 客户消息通知
     */
    public SysMessageNotice selectSysMessageNoticeById(Long id);

    /**
     * 查询客户消息通知列表
     *
     * @param sysMessageNotice 客户消息通知
     * @return 客户消息通知集合
     */
    public List<SysMessageNotice> selectSysMessageNoticeList(SysMessageNotice sysMessageNotice);

    /**
     * 新增客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    public int insertSysMessageNotice(SysMessageNotice sysMessageNotice);

    /**
     * 修改客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    public int updateSysMessageNotice(SysMessageNotice sysMessageNotice);

    /**
     * 删除客户消息通知
     *
     * @param id 客户消息通知ID
     * @return 结果
     */
    public int deleteSysMessageNoticeById(Long id);

    /**
     * 批量删除客户消息通知
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysMessageNoticeByIds(Long[] ids);

    /**
     * 根据客户ID查询客户信息（包含私有信息以及公共消息）
     * @param sysMessageNotice
     * @return
     */
    public List<MessageNoticeResponse> getCustomerMessage(SysMessageNotice sysMessageNotice);

    /**
     * 根据客户ID查询客户消息数量
     * @param sysMessageNotice
     * @return
     */
    public int getCustomerMessageCount(SysMessageNotice sysMessageNotice);

    /**
     * 根据客户ID查询打卡点评消息（包含点评营养师信息、客户信息、点评内容）
     * @param cusId 客户ID
     * @return
     */
    List<Map<String,Object>> getPunchCommentMessageByCusId(@Param("cusId")Long cusId);

    /**
     * 查询打卡点评未读消息数量
     * @param cusId 客户ID
     * @return
     */
    int getUnReadPunchMessageNum(@Param("cusId")Long cusId);

    /**
     * 根据打卡ID和openid查询点赞消息
     * @param punchId 打卡ID
     * @param openid 点赞用户openid
     * @return
     */
    SysMessageNotice getMessageByPunchIdAndOpenId(@Param("punchId")Long punchId, @Param("openid")String openid);

    /**
     * 根据客户ID查询点赞消息列表
     * @param cusId 客户ID
     * @return
     */
    List<Map<String,Object>> getPunchDynamicThumbsUpMessage(Long cusId);

    /**
     * 查询未读点赞数量
     * @param cusId 客户ID
     * @return
     */
    int getUnReadThumbsUpMessageNum(Long cusId);

    /**
     * 根据消息类型、客户ID、消息关键词查询消息
     */
    SysMessageNotice getMessageByCusIdAndTypeKey(SysMessageNotice sysMessageNotice);

    /**
     * 根据客户ID查询服务消息（食谱发送消息、执行反馈回复消息）
     * @return
     */
    List<Map<String,Object>> getServiceMessage(Long cusId);

    /**
     * 根据客户ID查询未读服务消息数量
     * @param cusId
     * @return
     */
    int getUnReadServiceMessageNum(Long cusId);
}