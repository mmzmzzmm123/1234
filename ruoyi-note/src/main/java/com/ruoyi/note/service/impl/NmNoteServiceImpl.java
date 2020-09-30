package com.ruoyi.note.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.note.mapper.NmNoteMapper;
import com.ruoyi.note.domain.NmNote;
import com.ruoyi.note.service.INmNoteService;

/**
 * 便签管理Service业务层处理
 *
 * @author wang
 * @date 2020-09-12
 */
@Service
public class NmNoteServiceImpl implements INmNoteService
{
    @Autowired
    private NmNoteMapper nmNoteMapper;

    /**
     * 查询便签管理
     *
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    @Override
    public NmNote selectNmNoteById(Long noteId)
    {
        return nmNoteMapper.selectNmNoteById(noteId);
    }

    /**
     * 查询便签管理列表
     *
     * @param nmNote 便签管理
     * @return 便签管理
     */
    @Override
    public List<NmNote> selectNmNoteList(NmNote nmNote)
    {
        return nmNoteMapper.selectNmNoteList(nmNote);
    }

    /**
     * 新增便签管理
     *
     * @param nmNote 便签管理
     * @return 结果
     */
    @Override
    public int insertNmNote(NmNote nmNote)
    {
        nmNote.setCreateTime(DateUtils.getNowDate());
        return nmNoteMapper.insertNmNote(nmNote);
    }

    /**
     * 修改便签管理
     *
     * @param nmNote 便签管理
     * @return 结果
     */
    @Override
    public int updateNmNote(NmNote nmNote)
    {
        nmNote.setUpdateTime(DateUtils.getNowDate());
        return nmNoteMapper.updateNmNote(nmNote);
    }

    /**
     * 批量删除便签管理
     *
     * @param noteIds 需要删除的便签管理ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteByIds(Long[] noteIds)
    {
        return nmNoteMapper.deleteNmNoteByIds(noteIds);
    }

    /**
     * 删除便签管理信息
     *
     * @param noteId 便签管理ID
     * @return 结果
     */
    @Override
    public int deleteNmNoteById(Long noteId)
    {
        return nmNoteMapper.deleteNmNoteById(noteId);
    }

    /**
     * 用户根据ID查询便签
     *
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    @Override
    public NmNote selectNmNoteuserById(Long noteId,Long userID) {
        NmNote nmNote = new NmNote();
        nmNote.setNoteId(noteId);
        nmNote.setUserId(userID);
        return nmNoteMapper.selectOne(nmNote);
    }


}
