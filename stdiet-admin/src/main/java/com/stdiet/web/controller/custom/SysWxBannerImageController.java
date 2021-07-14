package com.stdiet.custom.controller;

import java.util.ArrayList;
import java.util.List;

import com.stdiet.common.utils.oss.AliyunOSSUtils;
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
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysWxBannerImage;
import com.stdiet.custom.service.ISysWxBannerImageService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 小程序banner图Controller
 *
 * @author xzj
 * @date 2021-07-13
 */
@RestController
@RequestMapping("/custom/wxBannerImage")
public class SysWxBannerImageController extends BaseController
{
    @Autowired
    private ISysWxBannerImageService sysWxBannerImageService;

    /**
     * 查询小程序banner图列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxBannerImage sysWxBannerImage)
    {
        startPage();
        List<SysWxBannerImage> list = sysWxBannerImageService.selectSysWxBannerImageList(sysWxBannerImage);
        if(list != null && list.size() > 0){
            List<String> urlList = new ArrayList<>();
            for (SysWxBannerImage banner : list) {
                urlList.add(banner.getBannerUrl());
            }
            List<String> previewBannerUrlList = AliyunOSSUtils.generatePresignedUrl(urlList);
            int index = 0;
            for (SysWxBannerImage banner : list) {
                banner.setPreviewBannerUrl(previewBannerUrlList.get(index++));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出小程序banner图列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:export')")
    @Log(title = "小程序banner图", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxBannerImage sysWxBannerImage)
    {
        List<SysWxBannerImage> list = sysWxBannerImageService.selectSysWxBannerImageList(sysWxBannerImage);
        ExcelUtil<SysWxBannerImage> util = new ExcelUtil<SysWxBannerImage>(SysWxBannerImage.class);
        return util.exportExcel(list, "wxBannerImage");
    }

    /**
     * 获取小程序banner图详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        SysWxBannerImage sysWxBannerImage = sysWxBannerImageService.selectSysWxBannerImageById(id);
        if(sysWxBannerImage != null){
            sysWxBannerImage.setPreviewBannerUrl(AliyunOSSUtils.generatePresignedUrl(sysWxBannerImage.getBannerUrl()));
        }
        return AjaxResult.success(sysWxBannerImage);
    }

    /**
     * 新增小程序banner图
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:add')")
    @Log(title = "小程序banner图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxBannerImage sysWxBannerImage)
    {
        return toAjax(sysWxBannerImageService.insertSysWxBannerImage(sysWxBannerImage));
    }

    /**
     * 修改小程序banner图
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:edit')")
    @Log(title = "小程序banner图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxBannerImage sysWxBannerImage)
    {
        return toAjax(sysWxBannerImageService.updateSysWxBannerImage(sysWxBannerImage));
    }

    /**
     * 删除小程序banner图
     */
    @PreAuthorize("@ss.hasPermi('custom:wxBannerImage:remove')")
    @Log(title = "小程序banner图", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysWxBannerImageService.deleteSysWxBannerImageByIds(ids));
    }
}