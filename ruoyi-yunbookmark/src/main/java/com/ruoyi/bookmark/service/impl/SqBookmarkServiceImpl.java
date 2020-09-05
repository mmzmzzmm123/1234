package com.ruoyi.bookmark.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import com.ruoyi.bookmark.domain.SqBookmarkTag;
import com.ruoyi.bookmark.domain.SqTag;
import com.ruoyi.bookmark.domain.SqUserTag;
import com.ruoyi.bookmark.mapper.SqBookmarkTagMapper;
import com.ruoyi.bookmark.mapper.SqTagMapper;
import com.ruoyi.bookmark.mapper.SqUserTagMapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
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
        //删除书签 现在的标签
        SqBookmarkTag sqBookmarkTag=new SqBookmarkTag();
        sqBookmarkTag.setBookmarkId(sqBookmark.getBookmarkId());
        sqBookmarkTagMapper.delete(sqBookmarkTag);
        //删除的书签ID
        String deletetag="";
        //新增的书签ID
        String addtag="";
        //传入的标签
        List<Map<String,Object>> listmap=sqBookmark.getSqTags();
        //给文章添加标签
        Long[] tags= new Long[listmap.size()];

        //文章添加书签
        SqBookmarkTag bookamrktag=new SqBookmarkTag();
        int i=0;
        for (Map<String,Object> map:listmap) {
            for (Map.Entry<String, Object> entry : map.entrySet()){

                if(Integer.parseInt(String.valueOf(map.get("tagId")))<0){
                   //创建新的标签 返回id给map    并且添加用户的个人书签记录
                    //1.新增标签
                        //1.1查询书签是否存在
                        SqTag sqTag=new SqTag();
                        SqUserTag sqUserTag =new SqUserTag();
                        sqTag.setName(String.valueOf(map.get("name")));
                        List<SqTag> taglist=sqTagMapper.selectSqTagList(sqTag);
                        //存在返回ID
                        if (taglist!=null&&!taglist.isEmpty()){
                            map.put("tagId",taglist.get(0).getId());
                            logger.debug("传入的新标签 tagid="+taglist.get(0).getId());
                            //添加到用戶个人书签里面去
                            //1.用户是否已经有这个书签记录了
                            sqUserTag.setUserId(sqBookmark.getUserid());
                            sqUserTag.setTagId(taglist.get(0).getId());

                          List<SqUserTag> sqUserTags = sqUserTagMapper.selectSqUserTagList(sqUserTag);

                          if (sqUserTags!=null&&!sqUserTags.isEmpty()){
                              addtag +=sqUserTags.get(0).getTagId().toString()+",";
                          }else {
                              sqUserTag.setIcount(1);
                              sqUserTag.setIorder(1);
                              sqUserTagMapper.insertSqUserTag(sqUserTag);
                          }

                        }else {
                            //不存在 >>创建 返回ID
                            sqTag.setUserId(sqBookmark.getUserid());
                            sqTag.setTagType("P");
                            sqTag.setIcount(1);
                            sqTag.setStatus(0);
                            try {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                                Date dateStart = df.parse(df.format(new Date()));
                                sqTag.setCreateTime(dateStart);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            int tagid=sqTagMapper.insertUseGeneratedKeys(sqTag);
                            logger.debug("传入的新标签 tagid="+sqTag.getId());
                            map.put("tagId",sqTag.getId());
                            //添加到用戶个人书签里面去
                            sqUserTag.setUserId(sqBookmark.getUserid());
                            sqUserTag.setTagId(Long.valueOf(sqTag.getId()));
                            sqUserTag.setIcount(1);
                            sqUserTag.setIorder(1);
                            sqUserTagMapper.insertSqUserTag(sqUserTag);
                        }

                }
                if (!String.valueOf(map.get("name")).equals("TAGDELETE")){
                    //给文章添加书签
                    bookamrktag.setBookmarkId(sqBookmark.getBookmarkId());
                    bookamrktag.setTagId(Long.valueOf(map.get("tagId").toString()));
                    sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
                }else{
                    deletetag += map.get("tagId").toString()+",";
                }
                break;
            }
        }

        //个人标签引用数量 批量-1
       String[] tagreduce=deletetag.split(",");

        if (tagreduce!=null&&tagreduce.length>0){
        Long[] num = new Long[tagreduce.length];
        for (int idx = 0; idx < tagreduce.length; idx++) {
            if(!tagreduce[idx].equals("")&&tagreduce[idx]!=null){
            num[idx] = Long.parseLong(tagreduce[idx]);
            }
        }
        sqUserTagMapper.updateCountReduce(num,sqBookmark.getUserid());
        }

        //个人标签引用数量 批量+1
        String[] tagadd=addtag.split(",");
        if (tagadd!=null&&tagadd.length>0) {
            Long[] add = new Long[tagadd.length];
            for (int idx = 0; idx < tagadd.length; idx++) {
                if(!tagadd[idx].equals("")&&tagadd[idx]!=null) {
                    add[idx] = Long.parseLong(tagadd[idx]);
                }
            }
            sqUserTagMapper.updateCountAdd(add, sqBookmark.getUserid());
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
