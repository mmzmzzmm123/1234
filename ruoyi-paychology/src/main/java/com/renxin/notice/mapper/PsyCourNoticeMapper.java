package com.renxin.notice.mapper;

import com.renxin.notice.domain.PsyCourNotice;

import java.util.List;

/**
 * 咨询师、用户通知Mapper接口
 * 
 * @author renxin
 * @date 2024-06-17
 */
public interface PsyCourNoticeMapper 
{
    /**
     * 查询咨询师、用户通知
     * 
     * @param noticeId 咨询师、用户通知主键
     * @return 咨询师、用户通知
     */
    public PsyCourNotice selectPsyCourNoticeByNoticeId(Long noticeId);

    /**
     * 查询咨询师、用户通知列表
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 咨询师、用户通知集合
     */
    public List<PsyCourNotice> selectPsyCourNoticeList(PsyCourNotice psyCourNotice);

    /**
     * 新增咨询师、用户通知
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 结果
     */
    public int insertPsyCourNotice(PsyCourNotice psyCourNotice);

    /**
     * 修改咨询师、用户通知
     * 
     * @param psyCourNotice 咨询师、用户通知
     * @return 结果
     */
    public int updatePsyCourNotice(PsyCourNotice psyCourNotice);

    /**
     * 删除咨询师、用户通知
     * 
     * @param noticeId 咨询师、用户通知主键
     * @return 结果
     */
    public int deletePsyCourNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除咨询师、用户通知
     * 
     * @param noticeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyCourNoticeByNoticeIds(Long[] noticeIds);
}
