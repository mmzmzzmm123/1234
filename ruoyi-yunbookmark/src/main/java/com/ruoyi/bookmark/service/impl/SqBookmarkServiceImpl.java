package com.ruoyi.bookmark.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.service.ISqBookmarkService;

/**
 * 书签管理Service业务层处理
 *
 * @author wanghao
 * @date 2020-08-02
 */
@Service
public class SqBookmarkServiceImpl implements ISqBookmarkService
{
    @Autowired
    private SqBookmarkMapper sqBookmarkMapper;

    @Override
    public List<SqBookmark> selectByID(Long userID) {

        return sqBookmarkMapper.selectAll();
    }

    /**
     * 查询书签管理
     *
     * @param menuID 栏目ID
     * @param userID 用户ID
     * @return 书签管理
     */
    @Override
    public List<SqBookmark> selectBymenuIdUserID(Long menuID, Long userID) {
        return sqBookmarkMapper.selectBymenuIdUserID(menuID,userID);
    }

    /**
     * 查询书签管理
     *
     * @param bookmarkId 书签管理ID
     * @return 书签管理
     */
    @Override
    public SqBookmark selectSqBookmarkById(Long bookmarkId)
    {
        return sqBookmarkMapper.selectSqBookmarkById(bookmarkId);
    }

    /**
     * 查询书签管理列表
     *
     * @param sqBookmark 书签管理
     * @return 书签管理
     */
    @Override
    public List<SqBookmark> selectSqBookmarkList(SqBookmark sqBookmark)
    {
        return sqBookmarkMapper.selectSqBookmarkList(sqBookmark);
    }

    /**
     * 新增书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    @Override
    public int insertSqBookmark(SqBookmark sqBookmark)
    {
        sqBookmark.setCreateTime(DateUtils.getNowDate());
        return sqBookmarkMapper.insertSqBookmark(sqBookmark);
    }

    /**
     * 修改书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    @Override
    public int updateSqBookmark(SqBookmark sqBookmark)
    {
        return sqBookmarkMapper.updateSqBookmark(sqBookmark);
    }

    /**
     * 批量删除书签管理
     *
     * @param bookmarkIds 需要删除的书签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkByIds(Long[] bookmarkIds)
    {
        return sqBookmarkMapper.deleteSqBookmarkByIds(bookmarkIds);
    }

    /**
     * 删除书签管理信息
     *
     * @param bookmarkId 书签管理ID
     * @return 结果
     */
    @Override
    public int deleteSqBookmarkById(Long bookmarkId)
    {
        return sqBookmarkMapper.deleteSqBookmarkById(bookmarkId);
    }


    /**
     * 修改目录下书签状态为 删除状态
     *
     * @param
     * @return 结果
     */
    @Override
    public void updateSqBookmarkBymenuId(Long menuId) {
         sqBookmarkMapper.updateSqBookmarkBymenuId(menuId);

    }
    /**
     * 回收站
     * @return
     */
    @Override
    public List<SqBookmark> selectBydelete(Long userId) {
        SqBookmark sqBookmark= new SqBookmark();
        sqBookmark.setUserid(userId);
        sqBookmark.setIdelete(1); //已经删除的书签
        return sqBookmarkMapper.select(sqBookmark);
    }
    /**
     * 用户全部书签 不包括已经删除的
     * @return
     */
    @Override
    public List<SqBookmark> selectByUseridList(Long userId) {
        SqBookmark sqBookmark= new SqBookmark();
        sqBookmark.setUserid(userId);
        sqBookmark.setIdelete(0); //未删除的书签
        return sqBookmarkMapper.select(sqBookmark);
    }
}
