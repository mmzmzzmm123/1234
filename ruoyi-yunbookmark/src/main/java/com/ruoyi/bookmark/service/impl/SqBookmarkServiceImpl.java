package com.ruoyi.bookmark.service.impl;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.ruoyi.bookmark.domain.SqBookmarkTag;

import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.mapper.SqBookmarkTagMapper;
import com.ruoyi.bookmark.mapper.SqMenuMapper;
import com.ruoyi.bookmark.mapper.SqTagMapper;

import com.ruoyi.bookmark.service.ISqTagService;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import com.ruoyi.common.utils.bookmarkhtml.HtmlName;
import com.ruoyi.common.utils.bookmarkhtml.ImportHtml;
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
    public List<SqBookmark> selectBymenuIdUserID(Long menuID, Long userID,Integer sort,String sousou,Integer bookmarkStar,Integer start) {
        return sqBookmarkMapper.selectBymenuIdUserID(menuID,userID, sort,sousou,bookmarkStar,start);
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
        //获取官网urls
        try {
            sqBookmark.setUrls(ImportHtml.Urlutils(new URL(sqBookmark.getUrl())));
        } catch (MalformedURLException e) {
            logger.info("用户ID:"+sqBookmark.getUserid()+",新增书签"+sqBookmark.getUrl()+"获取网址的 主机信息 报错 -"+new Date());
        }
        if(null==sqBookmark.getDescription()||"".equals(sqBookmark.getDescription())){
            sqBookmark.setDescription(sqBookmark.getTitle());
        }
        //转换传入的父级ID
        sqBookmark.setMenuId(sqBookmark.getParentId());
        int i= sqBookmarkMapper.insertSqBookmark(sqBookmark);

        //给对应目录 +1 并且设置为目录
        sqMenuMapper.updateCountAdd(new Long[]{sqBookmark.getMenuId()},1);


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
        //未修改前的信息
        SqBookmark sqbm=sqBookmarkMapper.selectSqBookmarkById(sqBookmark.getBookmarkId());
        //传入的标签
        int i =sqBookmarkMapper.updateSqBookmark(sqBookmark);
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0){
            return i;
        }

        //是否移动目录
        if (!sqbm.getMenuId().toString().equals(sqBookmark.getMenuId().toString())){
            //给原目录 -1
            sqMenuMapper.updateCountReduce(new Long[]{sqBookmark.getMenuId()},1);
            //新目录 +1
            sqMenuMapper.updateCountAdd(new Long[]{sqBookmark.getMenuId()},1);
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
        return sqBookmarkMapper.selectByUseridList(userId);
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
        return sqBookmarkMapper.countBookMakeByMenuId(menuId);

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

    @Override
    public void addALLBookMarkByHtml(List<HtmlName> list,Long userID) {
        //获取所有的 *目录*文件夹
        List<HtmlName> listMenu= list.stream().filter(m-> m.getState().equals("0")).collect(Collectors.toList());
        //id排序 防止已添加的父id漏修改 id升序
        listMenu.sort(Comparator.comparing(HtmlName::getId));
        //顶级目录ID
        Long k = null;
        //1.添加目录
        for (HtmlName h : listMenu) {
            String id=h.getId();
            //添加 返回id
            SqMenu sqMenu = new SqMenu(userID,h.getTitle(),Long.valueOf(h.getParentId()), Const.MenuIocURL);
            int countId =  sqMenuMapper.insertSqMenu(sqMenu);
            if(countId!=0){
                k=Long.valueOf(sqMenu.getMenuId().toString());
                //批量修改对应的父目录id
                listMenu = ImportHtml.listFilter(listMenu,k,id);
                //批量修改对应的书签id
                list = ImportHtml.listFilter(list,k,id);
            }
        }
        //2.添加书签
        try {
            for (HtmlName h : list) {
                if (!h.getState().equals("0") && h.getState().equals(Const.BOOKMARK_STATE_FLAG)) {
                    SqBookmark sqBookmark = new SqBookmark();
                    sqBookmark.setUserid(userID);
                    sqBookmark.setTitle(StringUtils.substring(h.getTitle(), 0, 30));
                    sqBookmark.setUrl(h.getUrl());
                    sqBookmark.setUrls(ImportHtml.Urlutils(new URL(h.getUrl())));

                    if (StringUtils.isBlank(h.getDescription())) {
                        sqBookmark.setDescription(StringUtils.substring(h.getTitle(), 0, 30));
                    } else {
                        sqBookmark.setDescription(StringUtils.substring(h.getDescription(), 0, 40));
                    }
                    sqBookmark.setMenuId(Long.valueOf(h.getParentId()));
                    sqBookmark.setCreateTime(new Date());
                    sqBookmarkMapper.insertSqBookmark(sqBookmark);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.info("导入书签,获取出错!");
        }

        //3.开始计算目录的结构 和 目录的书签数量
            bookmarkMenuCount(userID,k);

    }

    @Override
    public int updateBookmarkStarById(Long userId, String bookmarkId, int bookmarkStr) {
        SqBookmark sqBookmark =new SqBookmark();
        sqBookmark.setBookmarkId(Long.valueOf(bookmarkId));
        sqBookmark.setUserid(userId);
        sqBookmark.setBookmarkStar(bookmarkStr==1?1:0);
        return sqBookmarkMapper.updateSqBookmark(sqBookmark);
    }

    @Override
    public int bookmarkDeleteRepetition(Long userId) {
        return sqBookmarkMapper.bookmarkDeleteRepetition(userId);
    }

    @Override
    public List<SqBookmark> bookmarkRepetition(Long userId) {
        return sqBookmarkMapper.bookmarkRepetition(userId);
    }
    @Override
    public void bookmarkMenuCount(Long userId,Long menuId){
        SqMenu sqMenu2 = new SqMenu();
        sqMenu2.setUserId(userId);
//        sqMenu2.setParentId(menuId);
        List<SqMenu> sqs = sqMenuMapper.select(sqMenu2);
        for (SqMenu s : sqs) {
            SqMenu sq = new SqMenu();
            sq.setParentId(s.getMenuId());
            List<SqMenu> lists = sqMenuMapper.select(sq);
            if (lists!=null&&!lists.isEmpty())
            {
                int count = sqBookmarkMapper.countBookMakeByMenuId(s.getMenuId());
                SqMenu sqMenu = new SqMenu();
                sqMenu.setMenuId(s.getMenuId());
                sqMenu.setSubordinate(1);
                sqMenu.setBookmarkCount(count);
                sqMenuMapper.updateSqMenu(sqMenu);
            }else{
                int count = sqBookmarkMapper.countBookMakeByMenuId(s.getMenuId());
                SqMenu sqMenu = new SqMenu();
                sqMenu.setMenuId(s.getMenuId());
                sqMenu.setSubordinate(0);
                sqMenu.setBookmarkCount(count);
                sqMenuMapper.updateSqMenu(sqMenu);
            }

        }
    }


}
