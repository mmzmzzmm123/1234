package com.ruoyi.web.controller.hotel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.ruoyi.hotel.domain.HtlRoomPicture;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlRoomPictureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间图片Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Api("房间图片管理")
@RestController
@RequestMapping("/hotel/roomPicture")
public class HtlRoomPictureController extends BaseController
{
    @Autowired
    private IHtlRoomPictureService htlRoomPictureService;

    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;
    
    /**
     * 查询房间图片列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:list')")
    @GetMapping("/list")
    public TableDataInfo list(HtlRoomPicture htlRoomPicture)
    {
        startPage();
        List<HtlRoomPicture> list = htlRoomPictureService.selectHtlRoomPictureList(htlRoomPicture);
        return getDataTable(list);
    }

    /**
     * 导出房间图片列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:export')")
    @Log(title = "房间图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlRoomPicture htlRoomPicture)
    {
        List<HtlRoomPicture> list = htlRoomPictureService.selectHtlRoomPictureList(htlRoomPicture);
        ExcelUtil<HtlRoomPicture> util = new ExcelUtil<HtlRoomPicture>(HtlRoomPicture.class);
        return util.exportExcel(list, "roomPicture");
    }

    /**
     * 获取房间图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:query')")
    @GetMapping(value = "/{pictureId}")
    public AjaxResult getInfo(@PathVariable("pictureId") Long pictureId)
    {
        return AjaxResult.success(htlRoomPictureService.selectHtlRoomPictureById(pictureId));
    }
    
    /**
     * 获取当前用户关联的房间图片详细信息
     */
    @ApiOperation("获取当前用户关联的房间图片详细信息")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:query')")
    @GetMapping(value = "/query")
    public AjaxResult query()
    {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlRoomPicture htlRoomPicture = new HtlRoomPicture();
		htlRoomPicture.setHotelId(hotelId);
		return AjaxResult.success(htlRoomPictureService.selectHtlRoomPictureList(htlRoomPicture));
    }

    /**
     * 上传房间图片
     * @throws IOException 
     */
    @ApiOperation("上传房间图片")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:add')")
    @Log(title = "房间图片", businessType = BusinessType.INSERT)
	@PostMapping(value = "/upload")
    public AjaxResult upload(@RequestParam("roomPicture") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String pic = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
    		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
    		HtlRoomPicture htlRoomPicture = new HtlRoomPicture();
    		htlRoomPicture.setPictureName(file.getOriginalFilename());
    		htlRoomPicture.setHotelId(hotelId);
    		htlRoomPicture.setPicturePath(pic);
			htlRoomPicture.setOrderNum(99);// 默认最后一个
			htlRoomPicture.setStatus(1);// 停用
    		htlRoomPicture.setCreateBy(sysUser.getUserName());
    		htlRoomPicture.setCreateTime(DateUtils.getNowDate());
    		htlRoomPictureService.insertHtlRoomPicture(htlRoomPicture);
			return AjaxResult.success(htlRoomPicture);
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 替换房间图片
     */
    @ApiOperation("替换房间图片")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:edit')")
    @Log(title = "房间图片", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/replace")
	public AjaxResult replace(Long pictureId, @RequestParam("roomPicture") MultipartFile file) throws IOException
    {
    	if (!file.isEmpty())
        {
            String pic = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
            HtlRoomPicture htlRoomPicture =  htlRoomPictureService.selectHtlRoomPictureById(pictureId);
	    	FileUtils.deleteFile(htlRoomPicture.getPicturePath());

    		htlRoomPicture.setPicturePath(pic);
    		htlRoomPicture.setUpdateBy(sysUser.getUserName());
    		htlRoomPicture.setUpdateTime(DateUtils.getNowDate());
    		htlRoomPictureService.updateHtlRoomPicture(htlRoomPicture);
    		
			return AjaxResult.success(htlRoomPicture);
        }
    	 return AjaxResult.error("上传图片异常，请联系管理员");
    }
    
    /**
     * 切换图片状态
     */
    @ApiOperation("切换图片状态（启用或停用）")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:edit')")
    @Log(title = "房间图片", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/switched")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pictureId", value = "图片ID", required = true, dataType = "long"),
		@ApiImplicitParam(name = "status", value = "状态（0：启用；1：停用）", required = true, dataType = "int") 
	})
	public AjaxResult switched(Long pictureId, Integer status) throws IOException
    {
    	 HtlRoomPicture htlRoomPicture =  htlRoomPictureService.selectHtlRoomPictureById(pictureId);
		if (null == htlRoomPicture) {
			return AjaxResult.error("未找到照片信息，请检查传入参数");
		}
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		htlRoomPicture.setStatus(status);
		htlRoomPicture.setUpdateBy(sysUser.getUserName());
		htlRoomPicture.setUpdateTime(DateUtils.getNowDate());
		htlRoomPictureService.updateHtlRoomPicture(htlRoomPicture);
		return AjaxResult.success(htlRoomPicture);
    }
    
    /**
     * 重命名图片名称
     */
    @ApiOperation("重命名图片名称")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:edit')")
    @Log(title = "房间图片", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/rename")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "pictureId", value = "图片ID", required = true, dataType = "long"),
		@ApiImplicitParam(name = "pictureName", value = "图片名称", required = true, dataType = "String")
	})
	public AjaxResult rename(Long pictureId, String pictureName) throws IOException {
		HtlRoomPicture htlRoomPicture = htlRoomPictureService.selectHtlRoomPictureById(pictureId);
		if (null == htlRoomPicture) {
			return AjaxResult.error("未找到照片信息，请检查传入参数");
		}
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		htlRoomPicture.setPictureName(pictureName);
		htlRoomPicture.setUpdateBy(sysUser.getUserName());
		htlRoomPicture.setUpdateTime(DateUtils.getNowDate());
		htlRoomPictureService.updateHtlRoomPicture(htlRoomPicture);
		return AjaxResult.success(htlRoomPicture);
	}

	/**
	 * 排列图片顺序
	 */
	@ApiOperation("排列图片顺序")
	@PreAuthorize("@ss.hasPermi('hotel:roomPicture:edit')")
	@Log(title = "房间图片", businessType = BusinessType.UPDATE)
	@PostMapping(value = "/order")
	public AjaxResult order(Long[] pictureIds) throws IOException {
		List<HtlRoomPicture> result = new ArrayList<HtlRoomPicture>();
		for (int i = 0; i < pictureIds.length; i++) {
			HtlRoomPicture htlRoomPicture = htlRoomPictureService.selectHtlRoomPictureById(pictureIds[i]);
			if (null == htlRoomPicture) {
				return AjaxResult.error("未找到照片信息，请检查传入参数");
			}
			SysUser sysUser = SecurityUtils.getLoginUser().getUser();
			htlRoomPicture.setOrderNum(i);
			htlRoomPicture.setUpdateBy(sysUser.getUserName());
			htlRoomPicture.setUpdateTime(DateUtils.getNowDate());
			htlRoomPictureService.updateHtlRoomPicture(htlRoomPicture);
			result.add(htlRoomPicture);
		}
		return AjaxResult.success(result);
	}
    
    /**
     * 删除房间图片
     */
    @ApiOperation("删除房间图片")
    @PreAuthorize("@ss.hasPermi('hotel:roomPicture:remove')")
    @Log(title = "房间图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete")
    public AjaxResult remove(Long pictureId)
    {
    	HtlRoomPicture htlRoomPicture = htlRoomPictureService.selectHtlRoomPictureById(pictureId);
		if (null != htlRoomPicture) {
	    	FileUtils.deleteFile(htlRoomPicture.getPicturePath());
		}
        return toAjax(htlRoomPictureService.deleteHtlRoomPictureById(pictureId));
    }
}
