package com.ruoyi.bookmark.service.impl;


import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.ruoyi.bookmark.domain.SqBookmarkTag;

import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.mapper.SqBookmarkTagMapper;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import com.ruoyi.bookmark.mapper.SqTagMapper;

import com.ruoyi.bookmark.service.ISqTagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import tk.mybatis.mapper.entity.Example;


/**
 * 书签管理Service业务层处理
 *
 * @author wanghao
 * @date 2020-08-02
 */
@Service
public class SqBookmarkServiceImpl implements ISqBookmarkService
{
    public static Logger logger =  LoggerFactory.getLogger(SqBookmarkServiceImpl.class);
    @Autowired
    private SqBookmarkMapper sqBookmarkMapper;

    @Autowired
    private SqBookmarkTagMapper sqBookmarkTagMapper;

    @Autowired
    private SqTagMapper sqTagMapper;

    @Autowired
    private SqMenuMapper sqMenuMapper;


    @Autowired
    private ISqTagService iSqTagService;

    @Override
    public List<SqBookmark> selectByID(Long userID) {

        return sqBookmarkMapper.selectAll();
    }

    /**
     * 查询书签管理
     *
     * @param menuID 栏目ID
     * @param userID 用户ID
     * @param sort 排序方式
     * @param sousou 搜索
     * @return 书签管理
     */
    @Override
    public List<SqBookmark> selectBymenuIdUserID(Long menuID, Long userID,Integer sort,String sousou) {
        return sqBookmarkMapper.selectBymenuIdUserID(menuID,userID, sort,sousou);
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
        if(null==sqBookmark.getDescription()||"".equals(sqBookmark.getDescription())){
            sqBookmark.setDescription(sqBookmark.getTitle());
        }

      int i= sqBookmarkMapper.insertSqBookmark(sqBookmark);
        //传入的标签
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0){
           return i;
        }

        //给文章添加标签
        HashMap<Long,Long> bookmarkTag=new HashMap<Long,Long>();
        //文章添加书签
        SqBookmarkTag bookamrktag = new SqBookmarkTag();

        for (Map<String, Object> map : listmap) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //新增书签
                if (Integer.parseInt(String.valueOf(map.get("tagId"))) < 0) {
                    Map<String, Object> tagmap = iSqTagService.addtag(String.valueOf(map.get("name")), sqBookmark.getUserid());
                    for (Map.Entry<String, Object> tag : tagmap.entrySet()) {
                        bookmarkTag.put(Long.valueOf(tagmap.get("tagId").toString()),sqBookmark.getBookmarkId());
                    }
                }else {
                    //原本就有的 标签
                    bookmarkTag.put(Long.valueOf(map.get("tagId").toString()),sqBookmark.getBookmarkId());
                }
            }
        }
        //删除之前的标签
        SqBookmarkTag sqBookmarkTag=new SqBookmarkTag();
        sqBookmarkTag.setBookmarkId(sqBookmark.getBookmarkId());
        sqBookmarkTagMapper.delete(sqBookmarkTag);
        //给文章添加书签
        for (Map.Entry<Long,Long> tag:bookmarkTag.entrySet()){
            bookamrktag.setBookmarkId(tag.getValue());
            bookamrktag.setTagId(tag.getKey());
            sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
        }

        //给对应目录 +1
        Long[] menuIds= new Long[1];
        menuIds[0]=sqBookmark.getMenuId();
        sqMenuMapper.updateCountAdd(menuIds,1);
        return i;
    }

    /**
     * 修改书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    @Override
    public int updateSqBookmark(SqBookmark sqBookmark) {
//        //删除的书签ID
//        String deletetag = "";
//        //新增的书签ID
//        String addtag = "";
        //传入的标签
        int i =sqBookmarkMapper.updateSqBookmark(sqBookmark);
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0){
            return i;
        }



        //给文章添加标签
        HashMap<Long,Long> bookmarkTag=new HashMap<Long,Long>();
        //文章添加书签
        SqBookmarkTag bookamrktag = new SqBookmarkTag();

        for (Map<String, Object> map : listmap) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //新增书签
                if (Integer.parseInt(String.valueOf(map.get("tagId"))) < 0) {
                    Map<String, Object> tagmap = iSqTagService.addtag(String.valueOf(map.get("name")), sqBookmark.getUserid());
                    for (Map.Entry<String, Object> tag : tagmap.entrySet()) {
                        bookmarkTag.put(Long.valueOf(tagmap.get("tagId").toString()),sqBookmark.getBookmarkId());
                    }
                }else {
                    //原本就有的 标签
                    bookmarkTag.put(Long.valueOf(map.get("tagId").toString()),sqBookmark.getBookmarkId());
                }

            }
        }
        //删除之前的标签
        SqBookmarkTag sqBookmarkTag=new SqBookmarkTag();
        sqBookmarkTag.setBookmarkId(sqBookmark.getBookmarkId());
        sqBookmarkTagMapper.delete(sqBookmarkTag);

        //给文章添加书签
        for (Map.Entry<Long,Long> tag:bookmarkTag.entrySet()){
            bookamrktag.setBookmarkId(tag.getValue());
            bookamrktag.setTagId(tag.getKey());
            sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
        }


        return i;
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
     * 根据menuID 修改目录下所有书签状态为 删除状态
     *
     * @param menuId
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

    /**
     * 根据MenuId查询当前目录下 有多少书签数量
     *
     * @param  menuId
     * @return 结果
     */
    @Override
    public int selectByMenuIdCount(Long menuId)
    {
        SqBookmark sqBookmark=new SqBookmark();
        sqBookmark.setMenuId(menuId);
       List<SqBookmark> sqBookmarks=sqBookmarkMapper.select(sqBookmark);
       if (sqBookmarks!=null&&!sqBookmarks.isEmpty()){
           return sqBookmarks.size();
       }else {
           return 0;
       }
    }

    /**
     * 通过url 查询用户是否已经添加
     *
     * @param  url
     * @param  userId
     * @return
     */
    @Override
    public List<SqBookmark> selectByUrlUserID(String url, Long userId) {

        Example example = new Example(SqBookmark.class);
        // 排序
        example.setOrderByClause("create_time desc");
        // 条件查询
        example.createCriteria()
                .andEqualTo("url", url)
                .andEqualTo("userid",userId);
        return sqBookmarkMapper.selectByExample(example);
    }


}
