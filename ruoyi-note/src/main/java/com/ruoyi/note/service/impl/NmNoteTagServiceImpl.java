package com.ruoyi.note.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteTagMapper;
import com.ruoyi.note.domain.NmNoteTag;
import com.ruoyi.note.service.INmNoteTagService;

/**
 * 便签的标签Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-20
 */
@Service
public class NmNoteTagServiceImpl implements INmNoteTagService 
{
    @Autowired
    private NmNoteTagMapper nmNoteTagMapper;

    /**
     * 查询便签的标签
     * 
     * @param noteTagId 便签的标签ID
     * @return 便签的标签
     */
    @Override
    public NmNoteTag selectNmNoteTagById(Long noteTagId)
    {
        return nmNoteTagMapper.selectNmNoteTagById(noteTagId);
    }

    /**
     * 查询便签的标签列表
     * 
     * @param nmNoteTag 便签的标签
     * @return 便签的标签
     */
    @Override
    public List<NmNoteTag> selectNmNoteTagList(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.selectNmNoteTagList(nmNoteTag);
    }

    /**
     * 新增便签的标签
     * 
     * @param nmNoteTag 便签的标签
     * @return 结果
     */
    @Override
    public int insertNmNoteTag(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.insertNmNoteTag(nmNoteTag);
    }

    /**
     * 修改便签的标签
     * 
     * @param nmNoteTag 便签的标签
     * @return 结果
     */
    @Override
    public int updateNmNoteTag(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.updateNmNoteTag(nmNoteTag);
    }

    /**
     * 批量删除便签的标签
     * 
     * @param noteTagIds 需要删除的便签的标签ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteTagByIds(Long[] noteTagIds)
    {
        return nmNoteTagMapper.deleteNmNoteTagByIds(noteTagIds);
    }

    /**
     * 删除便签的标签信息
     * 
     * @param noteTagId 便签的标签ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteTagById(Long noteTagId)
    {
        return nmNoteTagMapper.deleteNmNoteTagById(noteTagId);
    }
}
