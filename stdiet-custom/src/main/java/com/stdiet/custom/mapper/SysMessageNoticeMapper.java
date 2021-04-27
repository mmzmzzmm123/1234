package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysMessageNotice;
import com.stdiet.custom.dto.response.MessageNoticeResponse;

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
}