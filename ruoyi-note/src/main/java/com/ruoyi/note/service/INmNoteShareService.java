package com.ruoyi.note.service;

import java.util.List;
import com.ruoyi.note.domain.NmNoteShare;

/**
 * share书签分享Service接口
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
public interface INmNoteShareService 
{
    /**
     * 查询share书签分享
     * 
     * @param noteId share书签分享ID
     * @return share书签分享
     */
    public NmNoteShare selectNmNoteShareById(Long noteId);

    /**
     * 查询share书签分享列表
     * 
     * @param nmNoteShare share书签分享
     * @return share书签分享集合
     */
    public List<NmNoteShare> selectNmNoteShareList(NmNoteShare nmNoteShare);

    /**
     * 新增share书签分享
     * 
     * @param nmNoteShare share书签分享
     * @return 结果
     */
    public int insertNmNoteShare(NmNoteShare nmNoteShare);

    /**
     * 修改share书签分享
     * 
     * @param nmNoteShare share书签分享
     * @return 结果
     */
    public int updateNmNoteShare(NmNoteShare nmNoteShare);

    /**
     * 批量删除share书签分享
     * 
     * @param noteIds 需要删除的share书签分享ID
     * @return 结果
     */
    public int deleteNmNoteShareByIds(Long[] noteIds);

    /**
     * 删除share书签分享信息
     * 
     * @param noteId share书签分享ID
     * @return 结果
     */
    public int deleteNmNoteShareById(Long noteId);
}
