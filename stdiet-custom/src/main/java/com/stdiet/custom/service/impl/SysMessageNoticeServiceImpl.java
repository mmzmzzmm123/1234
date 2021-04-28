package com.stdiet.custom.service.impl;

import java.util.Date;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.domain.entityEnum.MessageNoticeEnum;
import com.stdiet.custom.dto.response.MessageNoticeResponse;
import com.stdiet.custom.service.ISysCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysMessageNoticeMapper;
import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.service.ISysMessageNoticeService;

/**
 * 客户消息通知Service业务层处理
 *
 * @author xzj
 * @date 2021-04-26
 */
@Service
public class SysMessageNoticeServiceImpl implements ISysMessageNoticeService
{
    @Autowired
    private SysMessageNoticeMapper sysMessageNoticeMapper;

    @Autowired
    private ISysCustomerService sysCustomerService;

    /**
     * 查询客户消息通知
     *
     * @param id 客户消息通知ID
     * @return 客户消息通知
     */
    @Override
    public SysMessageNotice selectSysMessageNoticeById(Long id)
    {
        return sysMessageNoticeMapper.selectSysMessageNoticeById(id);
    }

    /**
     * 查询客户消息通知列表
     *
     * @param sysMessageNotice 客户消息通知
     * @return 客户消息通知
     */
    @Override
    public List<SysMessageNotice> selectSysMessageNoticeList(SysMessageNotice sysMessageNotice)
    {
        return sysMessageNoticeMapper.selectSysMessageNoticeList(sysMessageNotice);
    }

    /**
     * 新增客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    @Override
    public int insertSysMessageNotice(SysMessageNotice sysMessageNotice)
    {
        sysMessageNotice.setCreateTime(DateUtils.getNowDate());
        return sysMessageNoticeMapper.insertSysMessageNotice(sysMessageNotice);
    }

    /**
     * 修改客户消息通知
     *
     * @param sysMessageNotice 客户消息通知
     * @return 结果
     */
    @Override
    public int updateSysMessageNotice(SysMessageNotice sysMessageNotice)
    {
        sysMessageNotice.setUpdateTime(DateUtils.getNowDate());
        return sysMessageNoticeMapper.updateSysMessageNotice(sysMessageNotice);
    }

    /**
     * 批量删除客户消息通知
     *
     * @param ids 需要删除的客户消息通知ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageNoticeByIds(Long[] ids)
    {
        return sysMessageNoticeMapper.deleteSysMessageNoticeByIds(ids);
    }

    /**
     * 删除客户消息通知信息
     *
     * @param id 客户消息通知ID
     * @return 结果
     */
    @Override
    public int deleteSysMessageNoticeById(Long id)
    {
        return sysMessageNoticeMapper.deleteSysMessageNoticeById(id);
    }

    /**
     * 根据客户ID查询客户信息（包含私有信息以及公共消息）
     * @param sysMessageNotice
     * @return
     */
    @Override
    public List<MessageNoticeResponse> getCustomerMessage(SysMessageNotice sysMessageNotice){
        List<MessageNoticeResponse> responsesList = sysMessageNoticeMapper.getCustomerMessage(sysMessageNotice);
        if(responsesList != null && responsesList.size() > 0){
            for (MessageNoticeResponse messageNoticeResponse : responsesList) {
                messageNoticeResponse.setMessageTypeName(MessageNoticeEnum.getNoticeEnumByType(messageNoticeResponse.getMessageType()).getName());
            }
        }
        return responsesList;
    }

    /**
     * 消息发送
     * @param messageNoticeEnum
     * @param sysMessageNotice
     * @return
     */
    @Override
    public int sendMessageNoticeToCustomer(MessageNoticeEnum messageNoticeEnum, SysMessageNotice sysMessageNotice){
        sysMessageNotice.setMessageProperty(messageNoticeEnum.getProperty());
        sysMessageNotice.setMessageType(messageNoticeEnum.getType());
        return sysMessageNoticeMapper.insertSysMessageNotice(sysMessageNotice);
    }

    /**
     * 根据客户ID查询客户消息数量
     * @param sysMessageNotice
     * @return
     */
    @Override
    public int getCustomerMessageCount(SysMessageNotice sysMessageNotice){
        return sysMessageNoticeMapper.getCustomerMessageCount(sysMessageNotice);
    }

    /**
     * 发送打卡点评消息
     * @param sysWxUserLog
     */
    @Override
    @Async
    public void sendPunchCommentMessage(SysWxUserLog sysWxUserLog){
        if(sysWxUserLog == null || StringUtils.isEmpty(sysWxUserLog.getOpenid())){
            return;
        }
        SysCustomer sysCustomer = sysCustomerService.getCustomerByOpenId(sysWxUserLog.getOpenid());
        if(sysCustomer != null){
            SysMessageNotice sysMessageNotice = new SysMessageNotice();
            sysMessageNotice.setMessageProperty(1);
            sysMessageNotice.setMessageType(MessageNoticeEnum.punchComment.getType());
            sysMessageNotice.setReadType(0);
            sysMessageNotice.setMessageCustomer(sysCustomer.getId());
            sysMessageNotice.setMessageTitle(String.format(MessageNoticeEnum.punchComment.getTitleTemplate(), DateUtils.dateTime(sysWxUserLog.getLogTime())));
            sysMessageNotice.setMessageContent(sysWxUserLog.getComment());
            sendMessageNoticeToCustomer(MessageNoticeEnum.punchComment, sysMessageNotice);
        }
    }
}