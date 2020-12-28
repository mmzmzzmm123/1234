package com.ruoyi.web.controller.hotel;

import java.io.IOException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hotel.domain.HtlThemeConfig;
import com.ruoyi.hotel.domain.HtlThemeInfo;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlThemeConfigService;
import com.ruoyi.hotel.service.IHtlThemeInfoService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;

/**
 * 主题详情Controller
 * 
 * @author sucheng
 * @date 2020-12-25
 */
@RestController
@RequestMapping("/hotel/theme")
public class HtlThemeInfoController extends BaseController
{
    @Autowired
    private IHtlThemeInfoService htlThemeInfoService;

    @Autowired
    private IHtlThemeConfigService htlThemeConfigService;
    
    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;
    
	/**
	 * 获取所有主题详细信息
	 */
	@ApiOperation("获取所有主题详细信息")
	@PreAuthorize("@ss.hasPermi('hotel:theme:list')")
	@GetMapping(value = "/list")
	public AjaxResult list()
    {
		HtlThemeInfo htlThemeInfo = new HtlThemeInfo();
		return AjaxResult.success(htlThemeInfoService.selectHtlThemeInfoList(htlThemeInfo));
    }

    /**
     * 上传主题图片
     * @throws IOException 
     */
    @ApiOperation("上传主题图片")
    @ApiImplicitParam(name = "themeLayout", value = "主题版式（0：横板；1：竖版）", required = true, dataType = "int") 
    @PreAuthorize("@ss.hasPermi('hotel:theme:add')")
    @Log(title = "主题详情", businessType = BusinessType.INSERT)
	@PostMapping(value = "/upload")
    public AjaxResult upload(@RequestParam("themePicture") MultipartFile file,Integer themeLayout) throws IOException
    {
        if (!file.isEmpty())
        {
			String pic = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
			SysUser sysUser = SecurityUtils.getLoginUser().getUser();
			HtlThemeInfo htlThemeInfo = new HtlThemeInfo();
			htlThemeInfo.setThemeLayout(themeLayout);
			htlThemeInfo.setThemeName(file.getOriginalFilename());
			htlThemeInfo.setThemePicturePath(pic);
			htlThemeInfo.setCreateBy(sysUser.getUserName());
			htlThemeInfoService.insertHtlThemeInfo(htlThemeInfo);
			return AjaxResult.success(htlThemeInfo);
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    
    /**
     * 重命名图片名称
     */
    @ApiOperation("重命名图片名称")
    @PreAuthorize("@ss.hasPermi('hotel:theme:edit')")
    @Log(title = "主题详情", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/rename")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "themeId", value = "主题ID", required = true, dataType = "long"),
		@ApiImplicitParam(name = "themeName", value = "主题名称", required = true, dataType = "String")
	})
	public AjaxResult rename(Long themeId, String themeName) throws IOException {
		HtlThemeInfo htlThemeInfo = htlThemeInfoService.selectHtlThemeInfoById(themeId);
		if (null == htlThemeInfo) {
			return AjaxResult.error("未找到主题信息，请检查传入参数");
		}
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		htlThemeInfo.setThemeName(themeName);
		htlThemeInfo.setUpdateBy(sysUser.getUserName());
		htlThemeInfoService.updateHtlThemeInfo(htlThemeInfo);
		return AjaxResult.success(htlThemeInfo);
	}

    /**
     * 删除主题图片
     */
    @ApiOperation("删除主题图片")
    @PreAuthorize("@ss.hasPermi('hotel:theme:remove')")
    @Log(title = "主题详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete")
    public AjaxResult remove(Long themeId)
    {
		HtlThemeInfo htlThemeInfo = htlThemeInfoService.selectHtlThemeInfoById(themeId);
		if (null != htlThemeInfo) {
			FileUtils.deleteFile(htlThemeInfo.getThemePicturePath());
		}
		return toAjax(htlThemeInfoService.deleteHtlThemeInfoById(themeId));
    }
    
    /**
     * 配置主题
     */
    @ApiOperation("配置主题")
    @PreAuthorize("@ss.hasPermi('hotel:theme:config')")
    @Log(title = "主题配置", businessType = BusinessType.INSERT)
	@PostMapping("/config")
    public AjaxResult config(Long themeId)
    {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlThemeConfig htlThemeConfig = htlThemeConfigService.selectHtlThemeConfigById(hotelId);
		if (null == htlThemeConfig) {
			htlThemeConfig = new HtlThemeConfig();
			htlThemeConfig.setHotelId(hotelId);
			htlThemeConfig.setThemeId(themeId);
			htlThemeConfig.setCreateBy(sysUser.getUserName());
			htlThemeConfigService.insertHtlThemeConfig(htlThemeConfig);
		} else {
			htlThemeConfig.setHotelId(hotelId);
			htlThemeConfig.setThemeId(themeId);
			htlThemeConfig.setUpdateBy(sysUser.getUserName());
			htlThemeConfigService.updateHtlThemeConfig(htlThemeConfig);
		}
		return AjaxResult.success(htlThemeConfig);
    }
    
    @ApiOperation("获取当前酒店的主题配置")
	@GetMapping("/query")
    public AjaxResult query()
    {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlThemeConfig htlThemeConfig = htlThemeConfigService.selectHtlThemeConfigById(hotelId);
		return AjaxResult.success(htlThemeConfig);
    }
}
