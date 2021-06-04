package com.ruoyi.web.controller.qtjskhgl;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.ruoyi.common.utils.SecurityUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhwj;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhwjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 群体教师考核文件管理Controller
 *
 * @author tsbz
 * @date 2021-06-03
 */
@RestController
@RequestMapping("/qtjskhgl/qtjskhwj")
public class TsbzQtjskhwjController extends BaseController {
    @Autowired
    private ITsbzQtjskhwjService tsbzQtjskhwjService;

    /**
     * 查询群体教师考核文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjskhwj tsbzQtjskhwj) {
        tsbzQtjskhwj.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        startPage();
        List<TsbzQtjskhwj> list = tsbzQtjskhwjService.selectTsbzQtjskhwjList(tsbzQtjskhwj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师考核文件管理列表
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:export')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjskhwj tsbzQtjskhwj) {
        List<TsbzQtjskhwj> list = tsbzQtjskhwjService.selectTsbzQtjskhwjList(tsbzQtjskhwj);
        ExcelUtil<TsbzQtjskhwj> util = new ExcelUtil<TsbzQtjskhwj>(TsbzQtjskhwj.class);
        return util.exportExcel(list, "qtjskhwj");
    }

    /**
     * 获取群体教师考核文件管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjskhwjService.selectTsbzQtjskhwjById(id));
    }

    /**
     * 新增群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:add')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjskhwj tsbzQtjskhwj) {
        tsbzQtjskhwj.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzQtjskhwj.setCreateTime(new Date());
        tsbzQtjskhwj.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(tsbzQtjskhwjService.insertTsbzQtjskhwj(tsbzQtjskhwj));
    }

    /**
     * 修改群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:edit')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjskhwj tsbzQtjskhwj) {
        return toAjax(tsbzQtjskhwjService.updateTsbzQtjskhwj(tsbzQtjskhwj));
    }

    /**
     * 删除群体教师考核文件管理
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:remove')")
    @Log(title = "群体教师考核文件管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjskhwjService.deleteTsbzQtjskhwjByIds(ids));
    }

    /**
     * 下载
     * @param response
     */
    @PreAuthorize("@ss.hasPermi('qtjskhgl:qtjskhwj:list')")
    @GetMapping(value = "/download/{ids}")
    public void Download(HttpServletResponse response,@PathVariable Long [] ids) {
        System.out.println("start");
        //存放--服务器上zip文件的目录
        String directory = "D:\\tmp\\";
        File directoryFile = new File(directory);
        if (!directoryFile.isDirectory() && !directoryFile.exists()) {
            directoryFile.mkdirs();
        }
        //设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = formatter.format(new Date()) + ".zip";
        String strZipPath = directory + "\\" + zipFileName;

        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try {
            //构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            List<TsbzQtjskhwj> list=tsbzQtjskhwjService.selectTsbzQtjskhwjListByIds(ids);
            for (int i = 0; i < list.size(); i++) {
                //解码获取真实路径与文件名
                String realFileName = java.net.URLDecoder.decode(list.get(i).getFilename(), "UTF-8");
                String realFilePath = java.net.URLDecoder.decode(list.get(i).getFilepath(), "UTF-8");
                File file = new File(realFilePath);
                //TODO:未对文件不存在时进行操作，后期优化。
                if (file.exists()) {
                    zipSource = new FileInputStream(file);//将需要压缩的文件格式化为输入流
                    /**
                     * 压缩条目不是具体独立的文件，而是压缩包文件列表中的列表项，称为条目，就像索引一样这里的name就是文件名,
                     * 文件名和之前的重复就会导致文件被覆盖
                     */
                    ZipEntry zipEntry = new ZipEntry(realFileName);//在压缩目录中文件的名字
                    zipStream.putNextEntry(zipEntry);//定位该压缩条目位置，开始写入文件到压缩包中
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while ((read = bufferStream.read(buf, 0, 1024 * 10)) != -1) {
                        zipStream.write(buf, 0, read);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (null != bufferStream) bufferStream.close();
                if (null != zipStream) {
                    zipStream.flush();
                    zipStream.close();
                }
                if (null != zipSource) zipSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //判断系统压缩文件是否存在：true-把该压缩文件通过流输出给客户端后删除该压缩文件  false-未处理
        if (zipFile.exists()) {
            //downImg(response, zipFileName, strZipPath);
            zipFile.delete();
        }

    }

}
