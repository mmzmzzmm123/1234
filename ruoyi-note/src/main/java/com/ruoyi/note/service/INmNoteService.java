package com.ruoyi.note.service;

import java.util.List;
import com.ruoyi.note.domain.NmNote;

/**
 * 便签管理Service接口
 *
 * @author wang
 * @date 2020-09-12
 */
public interface INmNoteService
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
     * 批量删除便签管理
     *
     * @param noteIds 需要删除的便签管理ID
     * @return 结果
     */
    public int deleteNmNoteByIds(Long[] noteIds);

    /**
     * 删除便签管理信息
     *
     * @param noteId 便签管理ID
     * @return 结果
     */
    public int deleteNmNoteById(Long noteId);

    /**
     * 用户根据ID查询便签
     *
     * @param noteId 便签管理ID
     * @return 便签管理
     */
    public NmNote selectNmNoteuserById(Long noteId,Long userID);

    /**
     * 用户便签内容 实时储存到数据库
     *
     * @param  nmNote
     * @return
     */
    public int userUpdateNote(NmNote nmNote);

    /**
     * redis的文章 转移更新到MongoDb
     *
     * @param
     * @return
     */
    public void redisToMongonDB();
}
