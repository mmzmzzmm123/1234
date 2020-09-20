package com.ruoyi.note.service;

import java.util.List;
import com.ruoyi.note.domain.NmNoteTag;

/**
 * 便签的标签Service接口
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
public interface INmNoteTagService 
{
    /**
     * 查询便签的标签
     * 
     * @param noteTagId 便签的标签ID
     * @return 便签的标签
     */
    public NmNoteTag selectNmNoteTagById(Long noteTagId);

    /**
     * 查询便签的标签列表
     * 
     * @param nmNoteTag 便签的标签
     * @return 便签的标签集合
     */
    public List<NmNoteTag> selectNmNoteTagList(NmNoteTag nmNoteTag);

    /**
     * 新增便签的标签
     * 
     * @param nmNoteTag 便签的标签
     * @return 结果
     */
    public int insertNmNoteTag(NmNoteTag nmNoteTag);

    /**
     * 修改便签的标签
     * 
     * @param nmNoteTag 便签的标签
     * @return 结果
     */
    public int updateNmNoteTag(NmNoteTag nmNoteTag);

    /**
     * 批量删除便签的标签
     * 
     * @param noteTagIds 需要删除的便签的标签ID
     * @return 结果
     */
    public int deleteNmNoteTagByIds(Long[] noteTagIds);

    /**
     * 删除便签的标签信息
     * 
     * @param noteTagId 便签的标签ID
     * @return 结果
     */
    public int deleteNmNoteTagById(Long noteTagId);
}
