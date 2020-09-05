package com.ruoyi.bookmark.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.bookmark.domain.SqBookmarkTag;

/**
 * 书签标签管理Service接口
 *
 * @author ruoyi
 * @date 2020-09-04
 */
public interface ISqBookmarkTagService
{
    /**
     * 查询该篇文章的 所有书签
     *
     * @param
     * @return 书签_标签
     */
    public List<Map<String,Object>> selectSqTaListById(Long bookmarkId);
    /**
     * 查询书签标签管理
     *
     * @param id 书签标签管理ID
     * @return 书签标签管理
     */
    public SqBookmarkTag selectSqBookmarkTagById(Long id);

    /**
     * 查询书签标签管理列表
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 书签标签管理集合
     */
    public List<SqBookmarkTag> selectSqBookmarkTagList(SqBookmarkTag sqBookmarkTag);

    /**
     * 新增书签标签管理
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 结果
     */
    public int insertSqBookmarkTag(SqBookmarkTag sqBookmarkTag);

    /**
     * 修改书签标签管理
     *
     * @param sqBookmarkTag 书签标签管理
     * @return 结果
     */
    public int updateSqBookmarkTag(SqBookmarkTag sqBookmarkTag);

    /**
     * 批量删除书签标签管理
     *
     * @param ids 需要删除的书签标签管理ID
     * @return 结果
     */
    public int deleteSqBookmarkTagByIds(Long[] ids);

    /**
     * 删除书签标签管理信息
     *
     * @param id 书签标签管理ID
     * @return 结果
     */
    public int deleteSqBookmarkTagById(Long id);
}
