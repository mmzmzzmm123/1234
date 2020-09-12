package com.ruoyi.note.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteContentMapper;
import com.ruoyi.note.domain.NmNoteContent;
import com.ruoyi.note.service.INmNoteContentService;

/**
 * 文章内容Service业务层处理
 * 
 * @author wang
 * @date 2020-09-12
 */
@Service
public class NmNoteContentServiceImpl implements INmNoteContentService 
{
    @Autowired
    private NmNoteContentMapper nmNoteContentMapper;

    /**
     * 查询文章内容
     * 
     * @param noteId 文章内容ID
     * @return 文章内容
     */
    @Override
    public NmNoteContent selectNmNoteContentById(Long noteId)
    {
        return nmNoteContentMapper.selectNmNoteContentById(noteId);
    }

    /**
     * 查询文章内容列表
     * 
     * @param nmNoteContent 文章内容
     * @return 文章内容
     */
    @Override
    public List<NmNoteContent> selectNmNoteContentList(NmNoteContent nmNoteContent)
    {
        return nmNoteContentMapper.selectNmNoteContentList(nmNoteContent);
    }

    /**
     * 新增文章内容
     * 
     * @param nmNoteContent 文章内容
     * @return 结果
     */
    @Override
    public int insertNmNoteContent(NmNoteContent nmNoteContent)
    {
        return nmNoteContentMapper.insertNmNoteContent(nmNoteContent);
    }

    /**
     * 修改文章内容
     * 
     * @param nmNoteContent 文章内容
     * @return 结果
     */
    @Override
    public int updateNmNoteContent(NmNoteContent nmNoteContent)
    {
        return nmNoteContentMapper.updateNmNoteContent(nmNoteContent);
    }

    /**
     * 批量删除文章内容
     * 
     * @param noteIds 需要删除的文章内容ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteContentByIds(Long[] noteIds)
    {
        return nmNoteContentMapper.deleteNmNoteContentByIds(noteIds);
    }

    /**
     * 删除文章内容信息
     * 
     * @param noteId 文章内容ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteContentById(Long noteId)
    {
        return nmNoteContentMapper.deleteNmNoteContentById(noteId);
    }
}
