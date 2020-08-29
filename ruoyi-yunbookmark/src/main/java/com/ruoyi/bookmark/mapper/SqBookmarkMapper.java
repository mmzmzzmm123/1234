package com.ruoyi.bookmark.mapper;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.common.mybatisMapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 书签管理Mapper接口
 *
 * @author wanghao
 * @date 2020-08-02F
 */
public interface SqBookmarkMapper extends MyMapper<SqBookmark>
{
    /**
     * 查询栏目下书签管理
     *
     * @param menuID 栏目ID
     * @param userID 用户ID
     * @return 书签管理
     */
    public List<SqBookmark> selectBymenuIdUserID(@Param("menuID") Long menuID, @Param("userID") Long userID);
    /**
     * @auther: Wang
     * @date: 2020/08/29 23:03
     * 功能描述: 回收站
     */

    public List<SqBookmark> selectBydelete(@Param("userID") Long userID);
    /**
     * 查询书签管理
     *
     * @param bookmarkId 书签管理ID
     * @return 书签管理
     */
    public SqBookmark selectSqBookmarkById(Long bookmarkId);

    /**
     * 查询书签管理列表
     *
     * @param sqBookmark 书签管理
     * @return 书签管理集合
     */
    public List<SqBookmark> selectSqBookmarkList(SqBookmark sqBookmark);

    /**
     * 新增书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    public int insertSqBookmark(SqBookmark sqBookmark);

    /**
     * 修改书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    public int updateSqBookmark(SqBookmark sqBookmark);

    /**
     * 删除书签管理
     *
     * @param bookmarkId 书签管理ID
     * @return 结果
     */
    public int deleteSqBookmarkById(Long bookmarkId);

    /**
     * 批量删除书签管理
     *
     * @param bookmarkIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSqBookmarkByIds(Long[] bookmarkIds);

    /**
     * 修改目录下书签状态为 删除状态
     *
     * @param
     * @return 结果
     */
    public void updateSqBookmarkBymenuId(Long menuId);
}
