package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QzNotice;

/**
 * 通知公告Service接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface IQzNoticeService 
{
    /**
     * 查询通知公告
     * 
     * @param noticeId 通知公告主键
     * @return 通知公告
     */
    public QzNotice selectQzNoticeByNoticeId(Long noticeId);

    /**
     * 查询通知公告列表
     * 
     * @param qzNotice 通知公告
     * @return 通知公告集合
     */
    public List<QzNotice> selectQzNoticeList(QzNotice qzNotice);

    /**
     * 新增通知公告
     * 
     * @param qzNotice 通知公告
     * @return 结果
     */
    public int insertQzNotice(QzNotice qzNotice);

    /**
     * 修改通知公告
     * 
     * @param qzNotice 通知公告
     * @return 结果
     */
    public int updateQzNotice(QzNotice qzNotice);

    /**
     * 批量删除通知公告
     * 
     * @param noticeIds 需要删除的通知公告主键集合
     * @return 结果
     */
    public int deleteQzNoticeByNoticeIds(Long[] noticeIds);

    /**
     * 删除通知公告信息
     * 
     * @param noticeId 通知公告主键
     * @return 结果
     */
    public int deleteQzNoticeByNoticeId(Long noticeId);
}
