package com.ruoyi.web.test.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HtmlUtil;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.utils.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Wang
 * @Date: 2021/04/19 21:16
 * 功能描述:
 */
public class HtmlTest extends BaseSpringBootTest {

    @Autowired
    private ISqMenuService iSqMenuService;
    @Autowired
    private ISqBookmarkService iSqBookmarkService;

    @Test
    public void test3() {
        String favoritesId = "2";

    if(StringUtils.isNotBlank(favoritesId))
    {
        HttpServletResponse response=null;
        try {
            String fileName = "cqy_" + DateUtil.now() + ".html";
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                    "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                    "<TITLE>Bookmarks</TITLE>\n" +
                    "<H1>Bookmarks</H1>");
            sb.append("<DL>\n");
            sb.append(exportToHtml(1L));
            sb.append("</DL>\n");
            //1:使用File类创建一个要操作的文件路径
            File file = new File("D:" + File.separator + "demo" + File.separator + fileName);
            if(!file.getParentFile().exists()){ //如果文件的目录不存在
                file.getParentFile().mkdirs(); //创建目录

            }
            //2: 实例化OutputString 对象
            OutputStream output = new FileOutputStream(file);

            //3: 准备好实现内容的输出
            //将字符串变为字节数组
            byte data[] = sb.toString().getBytes();
            output.write(data);
            //4: 资源操作的最后必须关闭
            output.close();

        } catch (Exception e) {
            logger.error("异常：", e);
        }
        }
    }


    /**
     * 导出到html文件
     * @param
     */
    public StringBuilder exportToHtml(Long userId){

        SqMenu sqMenu=new SqMenu();
        sqMenu.setUserId(userId);
        SqBookmark sqBookmark = new SqBookmark();
        sqBookmark.setUserid(userId);
        //目录
        List<SqMenu> menuList = iSqMenuService.selectSqMenuList(sqMenu);
        Map<Long, List<SqMenu>> mapMenu =  menuList.stream().collect(Collectors.groupingBy(SqMenu::getParentId));
        //书签
        List<SqBookmark> bookMarkList = iSqBookmarkService.selectSqBookmarkList(sqBookmark);
        Map<Long, List<SqBookmark>> mapBookMark  = bookMarkList.stream().collect(Collectors.groupingBy(SqBookmark::getMenuId));

        List<SqMenu> sqMenuList = mapMenu.get(0L);
        StringBuilder str = new StringBuilder();
        str = traverseFile_recursion(sqMenuList,str,mapBookMark,mapMenu);
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







}
