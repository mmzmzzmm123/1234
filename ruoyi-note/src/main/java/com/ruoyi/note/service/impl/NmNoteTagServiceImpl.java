package com.ruoyi.note.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteTagMapper;
import com.ruoyi.note.domain.NmNoteTag;
import com.ruoyi.note.service.INmNoteTagService;

/**
 * 便签标签Service业务层处理
 * 
 * @author wang
 * @date 2020-09-12
 */
@Service
public class NmNoteTagServiceImpl implements INmNoteTagService 
{
    @Autowired
    private NmNoteTagMapper nmNoteTagMapper;

    /**
     * 查询便签标签
     * 
     * @param id 便签标签ID
     * @return 便签标签
     */
    @Override
    public NmNoteTag selectNmNoteTagById(Long id)
    {
        return nmNoteTagMapper.selectNmNoteTagById(id);
    }

    /**
     * 查询便签标签列表
     * 
     * @param nmNoteTag 便签标签
     * @return 便签标签
     */
    @Override
    public List<NmNoteTag> selectNmNoteTagList(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.selectNmNoteTagList(nmNoteTag);
    }

    /**
     * 新增便签标签
     * 
     * @param nmNoteTag 便签标签
     * @return 结果
     */
    @Override
    public int insertNmNoteTag(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.insertNmNoteTag(nmNoteTag);
    }

    /**
     * 修改便签标签
     * 
     * @param nmNoteTag 便签标签
     * @return 结果
     */
    @Override
    public int updateNmNoteTag(NmNoteTag nmNoteTag)
    {
        return nmNoteTagMapper.updateNmNoteTag(nmNoteTag);
    }

    /**
     * 批量删除便签标签
     * 
     * @param ids 需要删除的便签标签ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteTagByIds(Long[] ids)
    {
        return nmNoteTagMapper.deleteNmNoteTagByIds(ids);
    }

    /**
     * 删除便签标签信息
     * 
     * @param id 便签标签ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteTagById(Long id)
    {
        return nmNoteTagMapper.deleteNmNoteTagById(id);
    }
}
