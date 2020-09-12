package com.ruoyi.note.mapper;

import java.util.List;
import com.ruoyi.note.domain.NmNote;

/**
 * 便签管理Mapper接口
 * 
 * @author wang
 * @date 2020-09-12
 */
public interface NmNoteMapper 
{
    /**
     * 查询便签管理
     * 
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    public NmNote selectNmNoteById(Long noteId);

    /**
     * 查询便签管理列表
     * 
     * @param nmNote 便签管理
     * @return 便签管理集合
     */
    public List<NmNote> selectNmNoteList(NmNote nmNote);

    /**
     * 新增便签管理
     * 
     * @param nmNote 便签管理
     * @return 结果
     */
    public int insertNmNote(NmNote nmNote);

    /**
     * 修改便签管理
     * 
     * @param nmNote 便签管理
     * @return 结果
     */
    public int updateNmNote(NmNote nmNote);

    /**
     * 删除便签管理
     * 
     * @param noteId 便签管理ID
     * @return 结果
     */
    public int deleteNmNoteById(Long noteId);

    /**
     * 批量删除便签管理
     * 
     * @param noteIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNmNoteByIds(Long[] noteIds);
}
