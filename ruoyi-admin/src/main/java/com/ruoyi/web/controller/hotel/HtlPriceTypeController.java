package com.ruoyi.web.controller.hotel;

import java.io.IOException;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hotel.domain.HtlPriceType;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlPriceTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房价类型Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Api(value = "房价类型管理")
@RestController
@RequestMapping("/hotel/priceType")
public class HtlPriceTypeController extends BaseController
{
    @Autowired
    private IHtlPriceTypeService htlPriceTypeService;

    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;
    
    /**
     * 查询房价类型列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:list')")
    @GetMapping("/list")
    public TableDataInfo list(HtlPriceType htlPriceType)
    {
        startPage();
        List<HtlPriceType> list = htlPriceTypeService.selectHtlPriceTypeList(htlPriceType);
        return getDataTable(list);
    }

    /**
     * 导出房价类型列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:export')")
    @Log(title = "房价类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlPriceType htlPriceType)
    {
        List<HtlPriceType> list = htlPriceTypeService.selectHtlPriceTypeList(htlPriceType);
        ExcelUtil<HtlPriceType> util = new ExcelUtil<HtlPriceType>(HtlPriceType.class);
        return util.exportExcel(list, "priceType");
    }

    /**
     * 获取房价类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:query')")
    @GetMapping(value = "/{hotelId}")
    public AjaxResult getInfo(@PathVariable("hotelId") Long hotelId)
    {
        return AjaxResult.success(htlPriceTypeService.selectHtlPriceTypeById(hotelId));
    }

    /**
     * 获取当前用户关联的酒店房价类型
     */
    @ApiOperation("获取当前用户关联的酒店房价类型")
    @GetMapping(value = "/query")
    public AjaxResult query()
    {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		HtlPriceType htlPriceType = new HtlPriceType();
		htlPriceType.setHotelId(hotelId);
		return AjaxResult.success(htlPriceTypeService.selectHtlPriceTypeList(htlPriceType));
    }
    
    /**
     * 新增房价类型
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:save')")
    @Log(title = "房价类型", businessType = BusinessType.INSERT)
	@ApiOperation("新增房价类型")
    @PostMapping("/add")
	@ApiImplicitParam(name = "priceType", value = "房价类别", required = true, dataType = "String")
	public AjaxResult add(String priceType) {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		HtlPriceType htlPriceType = new HtlPriceType();
		htlPriceType.setHotelId(hotelId);
		htlPriceType.setPriceType(priceType);
		htlPriceType.setIsVisible(0);
		htlPriceType.setCreateBy(sysUser.getUserName());
		htlPriceTypeService.insertHtlPriceType(htlPriceType);
		return AjaxResult.success(htlPriceType);
	}

    /**
     * 修改房价类型
     */
    @ApiOperation("修改房价类型")
    @PreAuthorize("@ss.hasPermi('hotel:priceType:edit')")
    @Log(title = "房价类型", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "priceTypeId", value = "房价类别ID", required = true, dataType = "long"),
		@ApiImplicitParam(name = "priceType", value = "房价类别", required = true, dataType = "String") 
	})
	public AjaxResult edit(Long priceTypeId, String priceType) {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlPriceType htlPriceType = htlPriceTypeService.selectHtlPriceTypeById(priceTypeId);
		if (null == htlPriceType || hotelId != htlPriceType.getHotelId()) {
			return AjaxResult.error("传递参数不正确");
		}
		htlPriceType.setPriceType(priceType);
		htlPriceType.setUpdateBy(sysUser.getUserName());
		htlPriceTypeService.updateHtlPriceType(htlPriceType);
		return AjaxResult.success(htlPriceType);
	}

    /**
     * 删除房价类型
     */
    @ApiOperation("删除房价类型")
    @ApiImplicitParam(name = "priceTypeId", value = "房价类别ID", required = true, dataType = "long")
    @PreAuthorize("@ss.hasPermi('hotel:priceType:remove')")
    @Log(title = "房价类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/delete")
    public AjaxResult remove(Long priceTypeId)
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlPriceType htlPriceType = htlPriceTypeService.selectHtlPriceTypeById(priceTypeId);
		if (null == htlPriceType || hotelId != htlPriceType.getHotelId()) {
			return AjaxResult.error("传递参数不正确");
		}
        return toAjax(htlPriceTypeService.deleteHtlPriceTypeById(priceTypeId));
    }
    
    @ApiOperation("显示或隐藏房价类型")
    @PostMapping(value = "/switched")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "priceTypeId", value = "房价类别ID", required = true, dataType = "long"),
		@ApiImplicitParam(name = "isVisible", value = "是否展示类别（0：展示；1：隐藏）", required = true, dataType = "int") 
	})
	public AjaxResult switched(Long priceTypeId, Integer isVisible) throws IOException
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlPriceType htlPriceType = htlPriceTypeService.selectHtlPriceTypeById(priceTypeId);
		if (null == htlPriceType || hotelId != htlPriceType.getHotelId()) {
			return AjaxResult.error("传递参数不正确");
		}
		htlPriceType.setIsVisible(isVisible);
		htlPriceType.setUpdateBy(sysUser.getUserName());
		htlPriceTypeService.updateHtlPriceType(htlPriceType);
		return AjaxResult.success(htlPriceType);
    }
}
