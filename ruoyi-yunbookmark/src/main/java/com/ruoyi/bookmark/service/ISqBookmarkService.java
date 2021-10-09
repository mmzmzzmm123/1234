package com.ruoyi.bookmark.service;

import java.util.List;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.pojo.SqBookmarkReq;
import com.ruoyi.common.utils.bookmarkhtml.HtmlName;

/**
 * 书签管理Service接口
 *
 * @author wanghao
 * @date 2020-08-02
 */
public interface ISqBookmarkService
{
    /**
     * 测试 通用mapper
     */
    public List<SqBookmark> selectByID(Long userID);

    /**
     * 查询用户栏目下书签管理
     *
     * @param menuID 栏目ID
     * @param userID 用户ID
     * @param sort 排序方式
     * @param sousou 搜索
     * @param bookmarkStar 0非星标 1星标
     * @param start 书签状态
     * @return 书签管理
     */
    public List<SqBookmark> selectBymenuIdUserID(Long menuID,Long userID,Integer sort,String sousou,Integer bookmarkStar,Integer start);



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
     * 批量删除书签管理
     *
     * @param bookmarkIds 需要删除的书签管理ID
     * @return 结果
     */
    public int deleteSqBookmarkByIds(Long[] bookmarkIds);

    /**
     * 删除书签管理信息
     *
     * @param bookmarkId 书签管理ID
     * @return 结果
     */
    public int deleteSqBookmarkById(Long bookmarkId);

    /**
     * 修改目录下书签状态为 删除状态
     *
     * @param
     * @return 结果
     */
    public void updateSqBookmarkBymenuId(Long menuId);
    /**
     * 回收站
     * @return
     */
    public List<SqBookmark> selectBydelete(Long userId);

    /**
     * 用户全部书签
     * @return
     */
    List<SqBookmark> selectByUseridList(Long userId);


    /**
     * 根据MenuId查询当前目录下 有多少书签数量
     * @return
     */
    public int selectByMenuIdCount(Long menuId);

    /**
     * 通过url 查询用户是否已经添加
     *
     * @param  url
     * @param  userId
     * @return
     */
    List<SqBookmark> selectByUrlUserID(String url, Long userId);

    /**
     * 批量添加html导入的数据
     *
     * @param
     * @return
     */
    void addALLBookMarkByHtml(List<HtmlName> list,Long userID);


    /**
     * 书签设置星标 取消星标
     *
     * @param bookmarkStr 0不是星标 1是星标
     * @param bookmarkId
     * @param userId
     * @return
     */
    int updateBookmarkStarById(Long userId, Long bookmarkId, Integer bookmarkStr);
    /**
     * 删除重复的书签
     *
     */
    int bookmarkDeleteRepetition(Long userId);
    /**
     *获取所有重复的书签
     *
     * */
    List<SqBookmark> bookmarkRepetition(Long userId);

    /**
     *  重新计算目录下的书签数量和 目录的结构
     */
    public void bookmarkMenuCount(Long userId,Long menuId);

    /**
     * @Description: 导出书签文件html功能
     *
     * @param  userId
     * @return StringBuilder
     * @Date
     * @author: wanghao
     *
     */
    StringBuilder exportToHtml(Long userId);

    /**
     * @Description: 查询书签管理列表 聚合>> 星标 稍后看 最新书签
     *
     * @param
     * @return
     * @Date
     * @author: wanghao
     *
     */
    List<SqBookmark> listByUserAndPolymerization(SqBookmark sqBookmark);


    /**
     *  用户根据标签ID 查询对应的书签 分页
     *  @param
     *
     */
    List<SqBookmark> getlistByTag(SqBookmarkReq sqBookmarkReq);
}
