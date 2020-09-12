package com.ruoyi.note.service;

import java.util.List;
import com.ruoyi.note.domain.NmNoteTag;

/**
 * 便签标签Service接口
 * 
 * @author wang
 * @date 2020-09-12
 */
public interface INmNoteTagService 
{
    /**
     * 查询便签标签
     * 
     * @param id 便签标签ID
     * @return 便签标签
     */
    public NmNoteTag selectNmNoteTagById(Long id);

    /**
     * 查询便签标签列表
     * 
     * @param nmNoteTag 便签标签
     * @return 便签标签集合
     */
    public List<NmNoteTag> selectNmNoteTagList(NmNoteTag nmNoteTag);

    /**
     * 新增便签标签
     * 
     * @param nmNoteTag 便签标签
     * @return 结果
     */
    public int insertNmNoteTag(NmNoteTag nmNoteTag);

    /**
     * 修改便签标签
     * 
     * @param nmNoteTag 便签标签
     * @return 结果
     */
    public int updateNmNoteTag(NmNoteTag nmNoteTag);

    /**
     * 批量删除便签标签
     * 
     * @param ids 需要删除的便签标签ID
     * @return 结果
     */
    public int deleteNmNoteTagByIds(Long[] ids);

    /**
     * 删除便签标签信息
     * 
     * @param id 便签标签ID
     * @return 结果
     */
    public int deleteNmNoteTagById(Long id);
}
