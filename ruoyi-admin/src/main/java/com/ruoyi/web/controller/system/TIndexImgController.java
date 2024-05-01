package com.ruoyi.web.controller.system;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.config.ServerConfig;
import org.springframework.beans.factory.annotation.Value;
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
import com.ruoyi.system.domain.TIndexImg;
import com.ruoyi.system.service.ITIndexImgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 首页图片配置Controller
 * 
 * @author ruoyi
 * @date 2024-04-24
 */
@RestController
@RequestMapping("/system/indexImg")
public class TIndexImgController extends BaseController
{
    @Autowired
    private ITIndexImgService tIndexImgService;

    @Autowired
    private ServerConfig serverConfig;


    /**
     * 查询首页图片配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:list')")
    @GetMapping("/list")
    public TableDataInfo list(TIndexImg tIndexImg)
    {
        startPage();
        List<TIndexImg> list = tIndexImgService.selectTIndexImgList(tIndexImg);
        return getDataTable(list);
    }

    /**
     * 导出首页图片配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:export')")
    @Log(title = "首页图片配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TIndexImg tIndexImg)
    {
        List<TIndexImg> list = tIndexImgService.selectTIndexImgList(tIndexImg);
        ExcelUtil<TIndexImg> util = new ExcelUtil<TIndexImg>(TIndexImg.class);
        util.exportExcel(response, list, "首页图片配置数据");
    }

    /**
     * 获取首页图片配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tIndexImgService.selectTIndexImgById(id));
    }

    /**
     * 新增首页图片配置
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:add')")
    @Log(title = "首页图片配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(MultipartFile file)
    {
        if (file.isEmpty()) {
            return error("请选择一个文件！");
        }
        try {
            String fileName = file.getOriginalFilename();
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String url = FileUploadUtils.upload(filePath, file);

            TIndexImg tIndexImg = new TIndexImg();
            tIndexImg.setImgName(fileName);
            tIndexImg.setImgPath(url);
            return toAjax(tIndexImgService.insertTIndexImg(tIndexImg));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success();
    }

    /**
     * 修改首页图片配置
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:edit')")
    @Log(title = "首页图片配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TIndexImg tIndexImg)
    {
        return toAjax(tIndexImgService.updateTIndexImg(tIndexImg));
    }

    /**
     * 删除首页图片配置
     */
    @PreAuthorize("@ss.hasPermi('system:indexImg:remove')")
    @Log(title = "首页图片配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tIndexImgService.deleteTIndexImgByIds(ids));
    }
}
