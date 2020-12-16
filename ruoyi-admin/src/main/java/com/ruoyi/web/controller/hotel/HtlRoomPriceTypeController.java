package com.ruoyi.web.controller.hotel;

import java.util.List;
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
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hotel.domain.HtlRoomPriceType;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlRoomPriceTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
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
public class HtlRoomPriceTypeController extends BaseController
{
    @Autowired
    private IHtlRoomPriceTypeService htlRoomPriceTypeService;

    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;
    
    /**
     * 查询房价类型列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:list')")
    @GetMapping("/list")
    public TableDataInfo list(HtlRoomPriceType htlRoomPriceType)
    {
        startPage();
        List<HtlRoomPriceType> list = htlRoomPriceTypeService.selectHtlRoomPriceTypeList(htlRoomPriceType);
        return getDataTable(list);
    }

    /**
     * 导出房价类型列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:export')")
    @Log(title = "房价类型", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlRoomPriceType htlRoomPriceType)
    {
        List<HtlRoomPriceType> list = htlRoomPriceTypeService.selectHtlRoomPriceTypeList(htlRoomPriceType);
        ExcelUtil<HtlRoomPriceType> util = new ExcelUtil<HtlRoomPriceType>(HtlRoomPriceType.class);
        return util.exportExcel(list, "priceType");
    }

    /**
     * 获取房价类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:query')")
    @GetMapping(value = "/{hotelId}")
    public AjaxResult getInfo(@PathVariable("hotelId") Long hotelId)
    {
        return AjaxResult.success(htlRoomPriceTypeService.selectHtlRoomPriceTypeById(hotelId));
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
		return AjaxResult.success(htlRoomPriceTypeService.selectHtlRoomPriceTypeById(hotelId));
    }
    
    /**
     * 保存房价类型
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:save')")
    @Log(title = "房价类型", businessType = BusinessType.INSERT)
    @ApiOperation("保存房价类型")
    @PostMapping("/save")
	public AjaxResult save(RoomPriceTypeEntity priceTypeEntity) {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		HtlRoomPriceType htlRoomPriceType = htlRoomPriceTypeService.selectHtlRoomPriceTypeById(hotelId);
		if (null == htlRoomPriceType) {
			htlRoomPriceType = new HtlRoomPriceType();
			BeanUtils.copyBeanProp(htlRoomPriceType, priceTypeEntity);
			htlRoomPriceType.setHotelId(hotelId);
			htlRoomPriceType.setCreateBy(sysUser.getUserName());
			htlRoomPriceType.setCreateTime(DateUtils.getNowDate());
			htlRoomPriceTypeService.insertHtlRoomPriceType(htlRoomPriceType);
			return AjaxResult.success(htlRoomPriceType);
		} else {
			BeanUtils.copyBeanProp(htlRoomPriceType, priceTypeEntity);
			htlRoomPriceType.setUpdateBy(sysUser.getUserName());
			htlRoomPriceType.setUpdateTime(DateUtils.getNowDate());
			htlRoomPriceTypeService.updateHtlRoomPriceType(htlRoomPriceType);
			return AjaxResult.success(htlRoomPriceType);
		}
	}

    /**
     * 修改房价类型
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:edit')")
    @Log(title = "房价类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HtlRoomPriceType htlRoomPriceType)
    {
        return toAjax(htlRoomPriceTypeService.updateHtlRoomPriceType(htlRoomPriceType));
    }

    /**
     * 删除房价类型
     */
    @PreAuthorize("@ss.hasPermi('hotel:priceType:remove')")
    @Log(title = "房价类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hotelIds}")
    public AjaxResult remove(@PathVariable Long[] hotelIds)
    {
        return toAjax(htlRoomPriceTypeService.deleteHtlRoomPriceTypeByIds(hotelIds));
    }
}

@ApiModel("房价类型实体")
class RoomPriceTypeEntity
{
	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public int getType1Visible() {
		return type1Visible;
	}

	public void setType1Visible(int type1Visible) {
		this.type1Visible = type1Visible;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public int getType2Visible() {
		return type2Visible;
	}

	public void setType2Visible(int type2Visible) {
		this.type2Visible = type2Visible;
	}

	public String getType3() {
		return type3;
	}

	public void setType3(String type3) {
		this.type3 = type3;
	}

	public int getType3Visible() {
		return type3Visible;
	}

	public void setType3Visible(int type3Visible) {
		this.type3Visible = type3Visible;
	}

	public String getType4() {
		return type4;
	}

	public void setType4(String type4) {
		this.type4 = type4;
	}

	public int getType4Visible() {
		return type4Visible;
	}

	public void setType4Visible(int type4Visible) {
		this.type4Visible = type4Visible;
	}

	@ApiModelProperty("房价类别1")
	private String type1;

	@ApiModelProperty(value = "是否展示类别1", allowableValues = "0, 1")
	private int type1Visible;

	@ApiModelProperty("房价类别2")
	private String type2;

	@ApiModelProperty(value = "是否展示类别2", allowableValues = "0, 1")
	private int type2Visible;
	
	@ApiModelProperty("房价类别3")
	private String type3;

	@ApiModelProperty(value = "是否展示类别3", allowableValues = "0, 1")
	private int type3Visible;
	
	@ApiModelProperty("房价类别4")
	private String type4;

	@ApiModelProperty(value = "是否展示类别4", allowableValues = "0, 1")
	private int type4Visible;
	
    public RoomPriceTypeEntity()
    {

    }
}
