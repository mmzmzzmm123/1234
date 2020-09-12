package com.ruoyi.note.service;

import java.util.List;
import com.ruoyi.note.domain.NmNoteContent;

/**
 * 文章内容Service接口
 * 
 * @author wang
 * @date 2020-09-12
 */
public interface INmNoteContentService 
{
    /**
     * 查询文章内容
     * 
     * @param noteId 文章内容ID
     * @return 文章内容
     */
    public NmNoteContent selectNmNoteContentById(Long noteId);

    /**
     * 查询文章内容列表
     * 
     * @param nmNoteContent 文章内容
     * @return 文章内容集合
     */
    public List<NmNoteContent> selectNmNoteContentList(NmNoteContent nmNoteContent);

    /**
     * 新增文章内容
     * 
     * @param nmNoteContent 文章内容
     * @return 结果
     */
    public int insertNmNoteContent(NmNoteContent nmNoteContent);

    /**
     * 修改文章内容
     * 
     * @param nmNoteContent 文章内容
     * @return 结果
     */
    public int updateNmNoteContent(NmNoteContent nmNoteContent);

    /**
     * 批量删除文章内容
     * 
     * @param noteIds 需要删除的文章内容ID
     * @return 结果
     */
    public int deleteNmNoteContentByIds(Long[] noteIds);

    /**
     * 删除文章内容信息
     * 
     * @param noteId 文章内容ID
     * @return 结果
     */
    public int deleteNmNoteContentById(Long noteId);
}
