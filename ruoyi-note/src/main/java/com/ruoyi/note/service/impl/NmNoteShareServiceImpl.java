package com.ruoyi.note.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteShareMapper;
import com.ruoyi.note.domain.NmNoteShare;
import com.ruoyi.note.service.INmNoteShareService;

/**
 * share书签分享Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
@Service
public class NmNoteShareServiceImpl implements INmNoteShareService 
{
    @Autowired
    private NmNoteShareMapper nmNoteShareMapper;

    /**
     * 查询share书签分享
     * 
     * @param noteId share书签分享ID
     * @return share书签分享
     */
    @Override
    public NmNoteShare selectNmNoteShareById(Long noteId)
    {
        return nmNoteShareMapper.selectNmNoteShareById(noteId);
    }

    /**
     * 查询share书签分享列表
     * 
     * @param nmNoteShare share书签分享
     * @return share书签分享
     */
    @Override
    public List<NmNoteShare> selectNmNoteShareList(NmNoteShare nmNoteShare)
    {
        return nmNoteShareMapper.selectNmNoteShareList(nmNoteShare);
    }

    /**
     * 新增share书签分享
     * 
     * @param nmNoteShare share书签分享
     * @return 结果
     */
    @Override
    public int insertNmNoteShare(NmNoteShare nmNoteShare)
    {
        nmNoteShare.setCreateTime(DateUtils.getNowDate());
        return nmNoteShareMapper.insertNmNoteShare(nmNoteShare);
    }

    /**
     * 修改share书签分享
     * 
     * @param nmNoteShare share书签分享
     * @return 结果
     */
    @Override
    public int updateNmNoteShare(NmNoteShare nmNoteShare)
    {
        nmNoteShare.setUpdateTime(DateUtils.getNowDate());
        return nmNoteShareMapper.updateNmNoteShare(nmNoteShare);
    }

    /**
     * 批量删除share书签分享
     * 
     * @param noteIds 需要删除的share书签分享ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteShareByIds(Long[] noteIds)
    {
        return nmNoteShareMapper.deleteNmNoteShareByIds(noteIds);
    }

    /**
     * 删除share书签分享信息
     * 
     * @param noteId share书签分享ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteShareById(Long noteId)
    {
        return nmNoteShareMapper.deleteNmNoteShareById(noteId);
    }
}
