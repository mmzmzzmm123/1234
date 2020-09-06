package com.ruoyi.bookmark.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.bookmark.domain.SqBookmarkTag;
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.bookmark.mapper.SqBookmarkTagMapper;
import com.ruoyi.bookmark.mapper.SqTagMapper;
import com.ruoyi.bookmark.mapper.SqUserTagMapper;
import com.ruoyi.bookmark.service.ISqTagService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import sun.dc.pr.PRError;

import javax.print.DocFlavor;

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
    private SqUserTagMapper sqUserTagMapper;
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

      int i= sqBookmarkMapper.insertUseGeneratedKeys(sqBookmark);


        sqBookmark.setCreateTime(DateUtils.getNowDate());
        //传入的标签
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0){
            return sqBookmarkMapper.insertSqBookmark(sqBookmark);
        }
        String addtag="";
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
                        addtag += tagmap.get("tagId").toString();
                        map.put("tagId", tagmap.get("tagId"));
                    }
                }
                bookmarkTag.put(Long.valueOf(map.get("tagId").toString()),sqBookmark.getBookmarkId());
            }
        }

        //给文章添加书签
        for (Map.Entry<Long,Long> tag:bookmarkTag.entrySet()){
            bookamrktag.setBookmarkId(tag.getValue());
            bookamrktag.setTagId(tag.getKey());
            sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
        }

        //个人标签引用数量 批量+1
        if (!addtag.equals("") && addtag.length()>0) {
            addtag=addtag.substring(0,addtag.length()-1);
            String[] add = addtag.split(",");
            Long[] num = (Long[]) ConvertUtils.convert(add,Long.class);
            sqUserTagMapper.updateCountReduce(num, sqBookmark.getUserid());
        }

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
        //删除的书签ID
        String deletetag = "";
        //新增的书签ID
        String addtag = "";
        //传入的标签
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0){
            return sqBookmarkMapper.updateSqBookmark(sqBookmark);
        }
        //给文章添加标签
        HashMap<Long,Long> bookmarkTag=new HashMap<Long,Long>();
        //文章添加书签
        SqBookmarkTag bookamrktag = new SqBookmarkTag();
        int i = 0;
        for (Map<String, Object> map : listmap) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                //新增书签
                if (Integer.parseInt(String.valueOf(map.get("tagId"))) < 0) {
                    Map<String, Object> tagmap = iSqTagService.addtag(String.valueOf(map.get("name")), sqBookmark.getUserid());
                    for (Map.Entry<String, Object> tag : tagmap.entrySet()) {
                        addtag += tagmap.get("tagId").toString();
                        map.put("tagId", tagmap.get("tagId"));
                    }
                }
                //删除书签
                if (!String.valueOf(map.get("name")).equals("TAGDELETE")) {
                    bookmarkTag.put(Long.valueOf(map.get("tagId").toString()),sqBookmark.getBookmarkId());
                } else {
                    deletetag += map.get("tagId").toString() + ",";
                }
                break;
            }
        }
        //删除书签 现在的所有标签
        SqBookmarkTag sqBookmarkTag = new SqBookmarkTag();
        sqBookmarkTag.setBookmarkId(sqBookmark.getBookmarkId());
        sqBookmarkTagMapper.delete(sqBookmarkTag);

        //给文章添加书签
        for (Map.Entry<Long,Long> tag:bookmarkTag.entrySet()){
            bookamrktag.setBookmarkId(tag.getValue());
            bookamrktag.setTagId(tag.getKey());
            sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
        }

        //个人标签引用数量 批量-1
        if (!deletetag.equals("") && deletetag.length()>0) {
           deletetag=deletetag.substring(0,deletetag.length()-1);
           String[] tagreduce = deletetag.split(",");
           Long[] num = (Long[]) ConvertUtils.convert(tagreduce,Long.class);
           sqUserTagMapper.updateCountReduce(num, sqBookmark.getUserid());
        }
        //个人标签引用数量 批量+1
        if (!addtag.equals("") && addtag.length()>0) {
            addtag=addtag.substring(0,addtag.length()-1);
            String[] add = addtag.split(",");
            Long[] num = (Long[]) ConvertUtils.convert(add,Long.class);
            sqUserTagMapper.updateCountReduce(num, sqBookmark.getUserid());
        }

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


}
