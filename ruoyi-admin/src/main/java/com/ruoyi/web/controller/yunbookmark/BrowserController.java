package com.ruoyi.web.controller.yunbookmark;



import com.ruoyi.bookmark.domain.SqBookmark;
import com.ruoyi.bookmark.domain.SqMenu;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import com.ruoyi.common.utils.bookmarkhtml.HtmlName;
import com.ruoyi.common.utils.bookmarkhtml.ImportHtml;
import com.ruoyi.common.utils.StringUtils;
import org.jsoup.HttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.net.ssl.SSLHandshakeException;
import java.net.URL;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


/**
 * @auther: Wang
 * @Date:
 * 功能描述: 导入书签 导出书签
 */

@RestController
@RequestMapping("/IO")
public class BrowserController extends BaseController {
    public static Logger logger =  LoggerFactory.getLogger(BrowserController.class);

    @Autowired
    private ISqMenuService iSqMenuService;
    @Autowired
    private ISqBookmarkService iSqBookmarkService;



    @RequestMapping("/import")
    @PreAuthorize("@ss.hasPermi('bookmark:bookmark:list')")
    public AjaxResult importCollect(@RequestParam("htmlFile") MultipartFile htmlFile){
        logger.debug("开始上传状态是:");
        SysUser sysUser=getAuthUser();
        Long userID= sysUser.getUserId();
        //防止重复上传




        try {
            List<HtmlName> list =  ImportHtml.addMenuAndBookmark(htmlFile.getInputStream());
            if(null == list || list.isEmpty()){
                return AjaxResult.error("未获取到url连接,空书签文件");
            }
            //获取所有的 *目录*文件夹
            List<HtmlName> listMenu= list.stream().filter(m-> m.getState().equals("0")).collect(Collectors.toList());
            //id排序 防止已添加的父id漏修改 id升序
            listMenu.sort(Comparator.comparing(HtmlName::getId));
            //1.添加目录
            for (HtmlName h : listMenu) {
                String id=h.getId();
                //添加 返回id
                SqMenu sqMenu = new SqMenu(userID,h.getTitle(),Long.valueOf(h.getParentId()), Const.MenuIocURL);
                int countId =  iSqMenuService.insertSqMenu(sqMenu);
                if(countId!=0){
                Long k=Long.valueOf(sqMenu.getMenuId().toString());
                //批量修改对应的父目录id
                listMenu = ImportHtml.listFilter(listMenu,k,id);
                //批量修改对应的书签id
                list = ImportHtml.listFilter(list,k,id);
                }
            }
            //2.添加书签
            for (HtmlName h : list) {
                if (!h.getState().equals("0")&&h.getState().equals(Const.BOOKMARK_STATE_FLAG)) {
				SqBookmark sqBookmark =new SqBookmark();
                sqBookmark.setUserid(userID);
                sqBookmark.setTitle(h.getTitle());
                sqBookmark.setUrl(h.getUrl());
                sqBookmark.setUrls(ImportHtml.Urlutils(new URL(h.getUrl())));
                if(StringUtils.isBlank(h.getDescription())){
                    sqBookmark.setDescription(h.getTitle());
                }else{
                    sqBookmark.setDescription(h.getDescription());
                }
                sqBookmark.setMenuId(Long.valueOf(h.getParentId()));
                sqBookmark.setCreateTime(new Date());
                iSqBookmarkService.insertSqBookmark(sqBookmark);
                }
            }

        }catch (SSLHandshakeException e){
            logger.error("文章解析出错:",e);
        }
        catch (DataIntegrityViolationException e){
            logger.error("导入存储异常:",e);
        }
        catch (HttpStatusException a){
            logger.error("文档解析错误:",a);
        }
        catch (Exception e) {
            logger.error("导入html异常:",e);
        }
        return AjaxResult.success("导入成功");
    }



    /**
     * 导入收藏文章
     */
    public void importHtml(Map<String, String> map,Long menuID,Long userId){
        for(Entry<String, String> entry : map.entrySet()){
            try {
                //获取URL后查询最新的URL信息
                Map<String, String> result = ImportHtml.getCollectFromUrl(entry.getKey());
                SqBookmark sqBookmark =new SqBookmark();
                sqBookmark.setUserid(userId);
                sqBookmark.setTitle(entry.getValue());
                sqBookmark.setUrl(entry.getKey());
                sqBookmark.setUrls(ImportHtml.Urlutils(new URL(entry.getKey())));
                if(StringUtils.isBlank(result.get("description"))){
                    sqBookmark.setDescription(entry.getValue());
                }else{
                    sqBookmark.setDescription(result.get("description"));
                }
                sqBookmark.setMenuId(menuID);
                sqBookmark.setCreateTime(new Date());
                iSqBookmarkService.insertSqBookmark(sqBookmark);
            } catch (Exception e) {
                logger.error("导入存储异常：",e);
            }
        }

    }



}
