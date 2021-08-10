package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzNoticeMapper;
import com.ruoyi.system.domain.QzNotice;
import com.ruoyi.system.service.IQzNoticeService;

/**
 * 通知公告Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzNoticeServiceImpl implements IQzNoticeService 
{
    @Autowired
    private QzNoticeMapper qzNoticeMapper;

    /**
     * 查询通知公告
     * 
     * @param noticeId 通知公告主键
     * @return 通知公告
     */
    @Override
    public QzNotice selectQzNoticeByNoticeId(Long noticeId)
    {
        return qzNoticeMapper.selectQzNoticeByNoticeId(noticeId);
    }

    /**
     * 查询通知公告列表
     * 
     * @param qzNotice 通知公告
     * @return 通知公告
     */
    @Override
    public List<QzNotice> selectQzNoticeList(QzNotice qzNotice)
    {
        return qzNoticeMapper.selectQzNoticeList(qzNotice);
    }

    /**
     * 新增通知公告
     * 
     * @param qzNotice 通知公告
     * @return 结果
     */
    @Override
    public int insertQzNotice(QzNotice qzNotice)
    {
        qzNotice.setCreateTime(DateUtils.getNowDate());
        return qzNoticeMapper.insertQzNotice(qzNotice);
    }

    /**
     * 修改通知公告
     * 
     * @param qzNotice 通知公告
     * @return 结果
     */
    @Override
    public int updateQzNotice(QzNotice qzNotice)
    {
        qzNotice.setUpdateTime(DateUtils.getNowDate());
        return qzNoticeMapper.updateQzNotice(qzNotice);
    }

    /**
     * 批量删除通知公告
     * 
     * @param noticeIds 需要删除的通知公告主键
     * @return 结果
     */
    @Override
    public int deleteQzNoticeByNoticeIds(Long[] noticeIds)
    {
        return qzNoticeMapper.deleteQzNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除通知公告信息
     * 
     * @param noticeId 通知公告主键
     * @return 结果
     */
    @Override
    public int deleteQzNoticeByNoticeId(Long noticeId)
    {
        return qzNoticeMapper.deleteQzNoticeByNoticeId(noticeId);
    }
}
