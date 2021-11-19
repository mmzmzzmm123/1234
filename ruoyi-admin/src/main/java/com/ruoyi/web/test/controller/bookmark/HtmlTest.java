package com.ruoyi.web.test.controller.bookmark;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ZipUtil;
import com.ruoyi.BackupsYunPan;
import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.file.MimeTypeUtils;
import com.ruoyi.web.test.controller.BaseSpringBootTest;
import com.ruoyi.web.test.controller.baiduyunpan.Constant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
    @Autowired
    private BackupsYunPan backupsYunPan;

    @Test
    public void ss(){
        try {
            System.out.println(DateUtil.now());
        Thread.sleep(10000);
//共享数据10；每一个线程里面-1
        MyRunner2 myRunner2=new MyRunner2();
        for(int i=0;i<10;i++){
            Thread thread2=new Thread(myRunner2);
            //不同的线程传入同一个对象，达到线程共享的目的
            thread2.start();
            //无序，由操作系统来调度
        }

            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //百度 备份
//        backupsYunPan.BaiDuYunBackups("D:\\data\\ces\\","day4_MySQL性能优化总结.zip",Constant.ATOKEN);
    }

    class MyRunner2 implements Runnable{

        @Override
        public void run() {


            int max=100,min=1;
            long randomNum = System.currentTimeMillis();
            int ran3 = (int) (randomNum%(max-min)+min);

            Long beginTime = new Date().getTime();

            String filePath = "D:/demo/" + "user_1/" + ran3+"/bookmark_bacpks/";
            String filePathZip = "D:/demo/" + "user_1/"+ran3 + "/bookmark_zip/";
            String fileName = "藏趣云_" + DateUtil.format(DateUtil.date(), "yyyy_MM_dd");

            try {
                StringBuilder sb = new StringBuilder();
                sb.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                        "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                        "<TITLE>Bookmarks</TITLE>\n" +
                        "<H1>Bookmarks 藏趣云</H1>");
                sb.append("<DL>\n");
                sb.append(exportToHtml(1L));
                sb.append("</DL>\n");

                File file = new File(filePath + fileName + MimeTypeUtils.HTML);
                if (!file.getParentFile().exists()) { //如果文件的目录不存在
                    file.getParentFile().mkdirs(); //创建目录
                }
                if (file.exists()) {
                    //文件存在
                }
                OutputStream output = new FileOutputStream(file);

                //3: 准备好实现内容的输出 将字符串变为字节数组
                byte data[] = sb.toString().getBytes();
                output.write(data);
                output.close();
                //打包
                ZipUtil.zip(filePath, filePathZip + fileName + MimeTypeUtils.ZIP);

                String downUrl = backupsYunPan.BaiDuYunBackups(filePathZip, fileName + MimeTypeUtils.ZIP, Constant.ATOKEN);
                //删除目录内文件
                FileUtils.deleteFile(filePathZip + fileName + MimeTypeUtils.ZIP);
                FileUtils.deleteFile(filePath + fileName + MimeTypeUtils.HTML);
                //备份成功了 存数据库

            } catch (Exception e) {
                logger.error("异常：", e);
            }

            System.out.println("耗时:"+(new Date().getTime()-beginTime));
        }
    }


    @Test
    public void test3() {
        Long beginTime = new Date().getTime();

        String filePath = "D:/demo/" + "user_1" + "/bookmark_bacpks/";
        String filePathZip = "D:/demo/" + "user_1" + "/bookmark_zip/";
        String fileName = "藏趣云_" + DateUtil.format(DateUtil.date(), "yyyy_MM_dd");

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE NETSCAPE-Bookmark-file-1>\n" +
                    "<META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\">\n" +
                    "<TITLE>Bookmarks</TITLE>\n" +
                    "<H1>Bookmarks 藏趣云</H1>");
            sb.append("<DL>\n");
            sb.append(exportToHtml(1L));
            sb.append("</DL>\n");

            File file = new File(filePath + fileName + MimeTypeUtils.HTML);
            if (!file.getParentFile().exists()) { //如果文件的目录不存在
                file.getParentFile().mkdirs(); //创建目录
            }
            if (file.exists()) {
                //文件存在
            }
            OutputStream output = new FileOutputStream(file);

            //3: 准备好实现内容的输出 将字符串变为字节数组
            byte data[] = sb.toString().getBytes();
            output.write(data);
            output.close();
            //打包
            ZipUtil.zip(filePath, filePathZip + fileName + MimeTypeUtils.ZIP);

            String downUrl = backupsYunPan.BaiDuYunBackups(filePathZip, fileName + MimeTypeUtils.ZIP, Constant.ATOKEN);
            //删除目录内文件
            FileUtils.deleteFile(filePathZip + fileName + MimeTypeUtils.ZIP);
            FileUtils.deleteFile(filePath + fileName + MimeTypeUtils.HTML);
            //备份成功了 存数据库

        } catch (Exception e) {
            logger.error("异常：", e);
        }

        System.out.println("耗时:"+(new Date().getTime()-beginTime));
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
