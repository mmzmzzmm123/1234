package com.renxin.notice.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import com.renxin.notice.domain.PsyCourNotice;
import com.renxin.notice.mapper.PsyCourNoticeMapper;
import com.renxin.notice.service.IPsyCourNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 咨询师、用户通知Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-17
 */
@Service
public class PsyCourNoticeServiceImpl implements IPsyCourNoticeService
{
    @Autowired
    private PsyCourNoticeMapper psyCourNoticeMapper;

    /**
     * 查询咨询师、用户通知
     * 
     * @param noticeId 咨询师、用户通知主键
     * @return 咨询师、用户通知
     */
    @Override
    public PsyCourNotice selectPsyCourNoticeByNoticeId(Long noticeId)
    {
        return psyCourNoticeMapper.selectPsyCourNoticeByNoticeId(noticeId);
    }

    /**
     * 查询咨询师、用户通知列表
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 咨询师、用户通知
     */
    @Override
    public List<PsyCourNotice> selectPsyCourNoticeList(PsyCourNotice psyCourNotice)
    {
        return psyCourNoticeMapper.selectPsyCourNoticeList(psyCourNotice);
    }

    /**
     * 新增咨询师、用户通知
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 结果
     */
    @Override
    public int insertPsyCourNotice(PsyCourNotice psyCourNotice)
    {
        psyCourNotice.setCreateTime(DateUtils.getNowDate());
        return psyCourNoticeMapper.insertPsyCourNotice(psyCourNotice);
    }

    /**
     * 修改咨询师、用户通知
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 结果
     */
    @Override
    public int updatePsyCourNotice(PsyCourNotice psyCourNotice)
    {
        psyCourNotice.setUpdateTime(DateUtils.getNowDate());
        return psyCourNoticeMapper.updatePsyCourNotice(psyCourNotice);
    }

    /**
     * 批量删除咨询师、用户通知
     * 
     * @param noticeIds 需要删除的咨询师、用户通知主键
     * @return 结果
     */
    @Override
    public int deletePsyCourNoticeByNoticeIds(Long[] noticeIds)
    {
        return psyCourNoticeMapper.deletePsyCourNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除咨询师、用户通知信息
     * 
     * @param noticeId 咨询师、用户通知主键
     * @return 结果
     */
    @Override
    public int deletePsyCourNoticeByNoticeId(Long noticeId)
    {
        return psyCourNoticeMapper.deletePsyCourNoticeByNoticeId(noticeId);
    }
}
