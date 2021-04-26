package com.ruoyi.web.controller.yunbookmark;




import cn.hutool.core.date.DateUtil;
import com.ruoyi.bookmark.service.ISqBookmarkService;
import com.ruoyi.bookmark.service.ISqMenuService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.bookmarkhtml.Const;
import com.ruoyi.common.utils.bookmarkhtml.HtmlName;
import com.ruoyi.common.utils.bookmarkhtml.ImportHtml;
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
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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

    //导入
    @RequestMapping("/import")
    @PreAuthorize("@ss.hasPermi('bookmark:browser:import')")
    public AjaxResult importCollect(@RequestParam("htmlFile") MultipartFile htmlFile){
        long startTime = System.currentTimeMillis();
        logger.info("开始上传状态是:"+ startTime );
        SysUser sysUser=getAuthUser();
        Long userID= sysUser.getUserId();
        //防止重复上传
        Long htmlFlag = iSqMenuService.noRepetition(Const.IMPORTHTML,userID,300L);
        if(htmlFlag.intValue()!=0){
            return AjaxResult.success("导入操作频繁请"+htmlFlag.intValue()+"秒后再操作!");
        }

        try {
            //解析HTML
            List<HtmlName> list =  ImportHtml.addMenuAndBookmark(htmlFile.getInputStream());
            if(null == list || list.isEmpty()){
                return AjaxResult.error("未获取到url连接,空书签文件");
            }
          //处理导入的html数据
          iSqBookmarkService.addALLBookMarkByHtml(list,userID);
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

        long endTime = System.currentTimeMillis();

        float seconds = (endTime - startTime) / 1000F;
        logger.info("导入用时:"+ seconds +"秒");

        return AjaxResult.success("导入成功");

    }



    /**
     *  导出全部书签
     */
    @RequestMapping("/export")
//    @PreAuthorize("@ss.hasPermi('bookmark:browser:export')")
    public void exportCollect(HttpServletResponse response) {
        long startTime = System.currentTimeMillis();
        logger.info("导出书签:" + startTime + "用户ID:" + getAuthUser().getUserId());
        try {
            String fileName = "cqy_" + DateUtil.now() + ".html";
            StringBuilder sb = iSqBookmarkService.exportToHtml(getAuthUser().getUserId());
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "content-disposition"); //Access-Control-Expose-Headers 让前端可以取Content-Disposition
            response.setHeader("content-disposition", "attachment;filename=" + fileName);
            response.setHeader("Pargam", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().print(sb);
        } catch (Exception e) {
            logger.error("异常：", e);
        }

        long endTime = System.currentTimeMillis();
        float seconds = (endTime - startTime) / 1000F;
        logger.info("导出用时:" + seconds+"秒");
    }


}
