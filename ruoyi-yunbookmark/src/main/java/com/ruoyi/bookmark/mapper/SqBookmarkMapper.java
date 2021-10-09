package com.ruoyi.bookmark.mapper;

import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.pojo.SqBookmarkReq;
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
    public List<SqBookmark> selectBymenuIdUserID(@Param("menuID") Long menuID, @Param("userID") Long userID,@Param("sort")Integer sort,@Param("sousuo") String sousuo,@Param("bookmarkStar") Integer bookmarkStar,@Param("start") Integer start);

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
     * 设置书签的星标
     *
     * @param bookmarkId
     * @param userId
     * @return 结果
     */
    public int updateSqBookmarkByStar(@Param("bookmarkId") Long bookmarkId,@Param("userId")Long userId,@Param("bookmarkStar") Integer bookmarkStar);

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
    /**
     * 查询用户全部书签不包含删除的书签
     *  @param
     * @return 结果
     */
    public List<SqBookmark> selectByUseridList(Long userId);

    /**
     * 根据MenuId查询当前目录下 有多少书签数量
     *
     * @param  menuId
     * @return 结果
     */
    public int countBookMakeByMenuId(Long menuId);
    /**
     *  刪除重复的书签
     */
    public int bookmarkDeleteRepetition(Long userId);
    /**
     *  获取重复的标签
     */
    List<SqBookmark> bookmarkRepetition(Long userId);

    /**
     * @Description: 查询 最新 稍后读 星标
     *
     * @param
     * @return
     * @author: wanghao
     *
     */
    List<SqBookmark> listByUserAndPolymerization(SqBookmark sqBookmark);

    List<SqBookmark> getlistByTag(SqBookmarkReq sqBookmarkReq);
}
