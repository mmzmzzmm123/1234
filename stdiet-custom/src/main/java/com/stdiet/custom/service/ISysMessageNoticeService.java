package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.domain.entityEnum.MessageNoticeEnum;
import com.stdiet.custom.dto.response.MessageNoticeResponse;

/**
 * 客户消息通知Service接口
 *
 * @author xzj
 * @date 2021-04-26
 */
public interface ISysMessageNoticeService
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
     * 批量删除客户消息通知
     *
     * @param ids 需要删除的客户消息通知ID
     * @return 结果
     */
    public int deleteSysMessageNoticeByIds(Long[] ids);

    /**
     * 删除客户消息通知信息
     *
     * @param id 客户消息通知ID
     * @return 结果
     */
    public int deleteSysMessageNoticeById(Long id);

    /**
     * 根据客户ID查询客户信息（包含私有信息以及公共消息）
     * @param sysMessageNotice
     * @return
     */
    public List<MessageNoticeResponse> getCustomerMessage(SysMessageNotice sysMessageNotice);

    /**
     * 消息发送
     * @param messageNoticeEnum
     * @param sysMessageNotice
     * @return
     */
    public int sendMessageNoticeToCustomer(MessageNoticeEnum messageNoticeEnum, SysMessageNotice sysMessageNotice);

    /**
     * 根据客户ID查询客户消息数量
     * @param sysMessageNotice
     * @return
     */
    public int getCustomerMessageCount(SysMessageNotice sysMessageNotice);

    /**
     * 发送打卡点评消息
     * @param sysWxUserLog
     */
    public void sendPunchCommentMessage(SysWxUserLog sysWxUserLog);
}