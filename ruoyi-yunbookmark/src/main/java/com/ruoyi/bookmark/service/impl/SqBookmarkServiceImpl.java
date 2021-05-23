package com.ruoyi.bookmark.service.impl;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.jacoco.agent.rt.internal_f3994fa.core.internal.flow.IFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bookmark.mapper.SqBookmarkMapper;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.genid.GenId;


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
        SqBookmark sqBookmark = sqBookmarkMapper.selectSqBookmarkById(bookmarkId);
        sqBookmark.setSqTags(sqBookmarkTagMapper.selectSqTaListById(sqBookmark.getBookmarkId()));
        return sqBookmark;
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
    @Transactional
    public int insertSqBookmark(SqBookmark sqBookmark)
    {
        //判断标签是否已经存在了 999
        List<SqBookmark> list = sqBookmarkMapper.select(new SqBookmark(sqBookmark.getUrl(),sqBookmark.getUserid()));
        if (!(list==null||list.isEmpty()))
            return 999;


        JSONArray objects = new JSONArray();
        JSONObject json = null;
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
        //给对应目录 +1 并且设置为目录
        sqMenuMapper.updateCountAdd(new Long[]{sqBookmark.getMenuId()},1);

        //传入的标签
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0||listmap.size()>5){
           return sqBookmarkMapper.insertSqBookmark(sqBookmark);
        }

        //给文章添加标签
        HashMap<Long,Long> bookmarkTag=new HashMap<Long,Long>();
        //文章添加书签
        SqBookmarkTag bookamrktag = new SqBookmarkTag();

        for (Map<String, Object> map : listmap) {
                //新增书签
                if (Integer.parseInt(String.valueOf(map.get("tagId"))) < 0) {
                    Map<String, Object> tagmap = iSqTagService.addtag(String.valueOf(map.get("name")), sqBookmark.getUserid());
                        bookmarkTag.put(Long.valueOf(tagmap.get("tagId").toString()),sqBookmark.getBookmarkId());
                        json.put("name",map.get("name").toString());
                        json.put("tagId",map.get("tagId").toString());
                        objects.add(json);
                }
        }
        //给文章添加书签
        for (Map.Entry<Long,Long> tag:bookmarkTag.entrySet()){
            bookamrktag.setBookmarkId(tag.getValue());
            bookamrktag.setTagId(tag.getKey());
            sqBookmarkTagMapper.insertSqBookmarkTag(bookamrktag);
        }
        //TAG书签串
        sqBookmark.setTagNameAll(objects.toString());
        return sqBookmarkMapper.insertSqBookmark(sqBookmark);
    }

    /**
     * 修改书签管理
     *
     * @param sqBookmark 书签管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateSqBookmark(SqBookmark sqBookmark) {

        //未修改前的信息
        SqBookmark sqbm=sqBookmarkMapper.selectSqBookmarkById(sqBookmark.getBookmarkId());


        //是否移动目录
        if (!sqbm.getMenuId().toString().equals(sqBookmark.getMenuId().toString())){
            //给原目录 -1
            sqMenuMapper.updateCountReduce(new Long[]{sqBookmark.getMenuId()},1);
            //新目录 +1
            sqMenuMapper.updateCountAdd(new Long[]{sqBookmark.getMenuId()},1);
        }
        List<Map<String, Object>> listmap = sqBookmark.getSqTags();
        if (listmap==null||listmap.isEmpty()||listmap.size()==0||listmap.size()>5){
            return sqBookmarkMapper.updateSqBookmark(sqBookmark); //修改书签信息
        }
        JSONArray objects = new JSONArray();
        JSONObject json = null;
        //给文章添加标签
        HashMap<Long,Long> bookmarkTag=new HashMap<Long,Long>();
        //文章添加书签
        SqBookmarkTag bookamrktag = new SqBookmarkTag();

        for (Map<String, Object> map : listmap) {
            json = new JSONObject();
                //新增书签
                if (Integer.parseInt(String.valueOf(map.get("tagId"))) < 0) {
                    Map<String, Object> tagmap = iSqTagService.addtag(map.get("name").toString(), sqBookmark.getUserid());
                        bookmarkTag.put(Long.valueOf(tagmap.get("tagId").toString()),sqBookmark.getBookmarkId());
                    json.put("name",map.get("name").toString());
                    json.put("tagId",map.get("tagId").toString());
                    objects.add(json);
                }else {
                    //修改前的书签 不包含已移除的
                    if(!map.get("name").equals("TAGDELETE")){
                        json.put("name",map.get("name").toString());
                        json.put("tagId",map.get("tagId").toString());
                        objects.add(json);
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
        //标签名称串
        sqBookmark.setTagNameAll(objects.toString());
        return sqBookmarkMapper.updateSqBookmark(sqBookmark); //修改书签信息

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
    public int updateBookmarkStarById(Long userId, Long bookmarkId, Integer bookmarkStr) {
        return sqBookmarkMapper.updateSqBookmarkByStar(bookmarkId,userId,bookmarkStr==1?0:1);
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

    /**
     * @Description: 导出书签文件html功能
     *
     * @param  userId
     * @return StringBuilder
     * @Date
     * @author: wanghao
     *
     */
    @Override
    public StringBuilder exportToHtml(Long userId) {

        SqMenu sqMenu=new SqMenu();
        sqMenu.setUserId(userId);
        SqBookmark sqBookmark = new SqBookmark();
        sqBookmark.setUserid(userId);
        //目录
        List<SqMenu> menuList = sqMenuMapper.selectSqMenuList(sqMenu);
        Map<Long, List<SqMenu>> mapMenu =  menuList.stream().collect(Collectors.groupingBy(SqMenu::getParentId));
        //书签
        List<SqBookmark> bookMarkList = sqBookmarkMapper.selectSqBookmarkList(sqBookmark);
        Map<Long, List<SqBookmark>> mapBookMark  = bookMarkList.stream().collect(Collectors.groupingBy(SqBookmark::getMenuId));

        List<SqMenu> sqMenuList = mapMenu.get(0L);
        StringBuilder str = new StringBuilder();
        str.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                "<TITLE>Bookmarks</TITLE>\n" +
                "<H1>Bookmarks</H1>");
        str.append("<DL>\n");
        str.append(traverseFile_recursion(sqMenuList,str,mapBookMark,mapMenu));
        str.append("</DL>\n");
        return str;
    }


    /**
     * @Description:递归书签导出功能
     *
     * @param  * @param sqMenuList
     * @param str
     * @param mapBookMark
     * @param mapMenu
     * @return java.lang.StringBuilder
     * @Date
     * @author: wanghao
     *
     */
    public StringBuilder traverseFile_recursion (List<SqMenu> sqMenuList,StringBuilder str,Map<Long, List<SqBookmark>> mapBookMark,Map<Long, List<SqMenu>> mapMenu) {

        if (sqMenuList != null && !sqMenuList.isEmpty()) {
            for (SqMenu f : sqMenuList) {
                str.append("<DT><H3 ADD_DATE=\"1584277207\" LAST_MODIFIED=\"0\">").append(f.getMenuName()).append("</H3>\n");
                str.append("<DL>\n");
                List<SqBookmark> bookmarksList = mapBookMark.get(f.getMenuId());
                if (bookmarksList != null && !bookmarksList.isEmpty()) {
                    for (SqBookmark b : bookmarksList) {
                        str.append("<DT><A HREF=\""+b.getUrl()+"\" TARGET=\"_blank\">"+b.getTitle()+"</A>\n");
                    }
                }
                traverseFile_recursion(mapMenu.get(f.getMenuId()),str,mapBookMark,mapMenu);
                str.append("</DL>\n");
            }
        }
        return str;
    }



    /**
     * @Description: 查询书签管理列表 聚合>> 星标 稍后看 最新书签
     *
     * @param
     * @return
     * @Date
     * @author: wanghao
     *
     */
    @Override
    public List<SqBookmark> listByUserAndPolymerization(SqBookmark sqBookmark) {

        SqBookmark search = new SqBookmark();
        search.setUserid(sqBookmark.getUserid());
        search.setSort(sqBookmark.getSort());
        search.setSousuo(sqBookmark.getSousuo());
        //未删除的
        search.setIdelete(Const.BKDELETE);


        if (sqBookmark.getType()==null){
            //搜索情况下走这里
            return sqBookmarkMapper.listByUserAndPolymerization(search);
        }
        if(sqBookmark.getType().equals(Const.RECYCLE)){
            search.setIdelete(Const.BKNOTDELETE);//删除的
        }else if (sqBookmark.getType().equals(Const.NEWEST)){//最新的
            //无处理
        }else if(sqBookmark.getType().equals(Const.ASTERISK)){
            search.setBookmarkStar(1);
        }else if(sqBookmark.getType().equals(Const.SEEYOULATER)){//稍后看
            search.setSeeYouLater(1);
        }else {
            //默认的目录Muidid
            search.setMenuId(Long.valueOf(sqBookmark.getType()));
        }

        return sqBookmarkMapper.listByUserAndPolymerization(search);
    }


}
