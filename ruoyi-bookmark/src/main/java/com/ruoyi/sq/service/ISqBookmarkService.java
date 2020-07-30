package com.ruoyi.sq.service;

import java.util.List;
import com.ruoyi.sq.domain.SqBookmark;

/**
 * 书签Service接口
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public interface ISqBookmarkService 
{
    /**
     * 查询书签
     * 
     * @param bookmarkId 书签ID
     * @return 书签
     */
    public SqBookmark selectSqBookmarkById(Long bookmarkId);

    /**
     * 查询书签列表
     * 
     * @param sqBookmark 书签
     * @return 书签集合
     */
    public List<SqBookmark> selectSqBookmarkList(SqBookmark sqBookmark);

    /**
     * 新增书签
     * 
     * @param sqBookmark 书签
     * @return 结果
     */
    public int insertSqBookmark(SqBookmark sqBookmark);

    /**
     * 修改书签
     * 
     * @param sqBookmark 书签
     * @return 结果
     */
    public int updateSqBookmark(SqBookmark sqBookmark);

    /**
     * 批量删除书签
     * 
     * @param bookmarkIds 需要删除的书签ID
     * @return 结果
     */
    public int deleteSqBookmarkByIds(Long[] bookmarkIds);

    /**
     * 删除书签信息
     * 
     * @param bookmarkId 书签ID
     * @return 结果
     */
    public int deleteSqBookmarkById(Long bookmarkId);
}
