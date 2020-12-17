package com.ruoyi.web.controller.hotel;

import java.math.BigDecimal;
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
import com.ruoyi.hotel.domain.HtlRoomPrice;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlRoomPriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 房间价格Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Api("房间信息管理")
@RestController
@RequestMapping("/hotel/roomPrice")
public class HtlRoomPriceController extends BaseController
{
    @Autowired
    private IHtlRoomPriceService htlRoomPriceService;
    
    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;

    /**
     * 查询房间价格列表
     */
    @ApiOperation("查询房间价格列表")
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:list')")
    @GetMapping("/list")
    public TableDataInfo list()
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
    	long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
		htlRoomPrice.setHotelId(hotelId);
        List<HtlRoomPrice> list = htlRoomPriceService.selectHtlRoomPriceList(htlRoomPrice);
        return getDataTable(list);
    }

    /**
     * 导出房间价格列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:export')")
    @Log(title = "房间价格", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlRoomPrice htlRoomPrice)
    {
        List<HtlRoomPrice> list = htlRoomPriceService.selectHtlRoomPriceList(htlRoomPrice);
        ExcelUtil<HtlRoomPrice> util = new ExcelUtil<HtlRoomPrice>(HtlRoomPrice.class);
        return util.exportExcel(list, "roomPrice");
    }

    /**
     * 获取房间价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:query')")
    @GetMapping(value = "/{roomRateId}")
    public AjaxResult getInfo(@PathVariable("roomRateId") Long roomRateId)
    {
        return AjaxResult.success(htlRoomPriceService.selectHtlRoomPriceById(roomRateId));
    }

    /**
     * 新增房间价格
     */
    @ApiOperation("新增房间价格")
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:add')")
    @Log(title = "房间价格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(RoomPriceEntity roomPriceEntity)
	{
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
		BeanUtils.copyBeanProp(htlRoomPrice, roomPriceEntity);
		htlRoomPrice.setHotelId(hotelId);
		htlRoomPrice.setCreateBy(sysUser.getUserName());
		htlRoomPrice.setCreateTime(DateUtils.getNowDate());
		htlRoomPriceService.insertHtlRoomPrice(htlRoomPrice);
		return AjaxResult.success(htlRoomPrice);
	}

    /**
     * 修改房间价格
     */
    @ApiOperation("修改房间价格")
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:edit')")
    @Log(title = "房间价格", businessType = BusinessType.UPDATE)
    @ApiImplicitParam(name = "roomRateId", value = "房价ID", required = true, dataType = "long")
    @PutMapping("/edit")
    public AjaxResult edit(Long roomRateId, RoomPriceEntity roomPriceEntity)
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
    	HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
		BeanUtils.copyBeanProp(htlRoomPrice, roomPriceEntity);
		htlRoomPrice.setRoomRateId(roomRateId);
		htlRoomPrice.setUpdateBy(sysUser.getUserName());
		htlRoomPrice.setUpdateTime(DateUtils.getNowDate());
        htlRoomPriceService.updateHtlRoomPrice(htlRoomPrice);
        return AjaxResult.success(htlRoomPrice);
    }

    /**
     * 删除房间价格
     */
    @ApiOperation("删除房间价格")
    @PreAuthorize("@ss.hasPermi('hotel:roomPrice:remove')")
    @Log(title = "房间价格", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "roomRateId", value = "房价ID", required = true, dataType = "long")
	@DeleteMapping("/delete")
	public AjaxResult remove(Long roomRateId)
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlRoomPrice roomPrice = htlRoomPriceService.selectHtlRoomPriceById(roomRateId);
		if (null != roomPrice && roomPrice.getHotelId() == hotelId) {
			return toAjax(htlRoomPriceService.deleteHtlRoomPriceById(roomRateId));
		} else {
			return AjaxResult.error("禁止删除不属于当前用户的房价信息");
		}
    }
}

@ApiModel("房价实体")
class RoomPriceEntity
{
	public BigDecimal getRoomPrice1() {
		return roomPrice1;
	}

	public void setRoomPrice1(BigDecimal roomPrice1) {
		this.roomPrice1 = roomPrice1;
	}

	public BigDecimal getRoomPrice2() {
		return roomPrice2;
	}

	public void setRoomPrice2(BigDecimal roomPrice2) {
		this.roomPrice2 = roomPrice2;
	}

	public BigDecimal getRoomPrice3() {
		return roomPrice3;
	}

	public void setRoomPrice3(BigDecimal roomPrice3) {
		this.roomPrice3 = roomPrice3;
	}

	public BigDecimal getRoomPrice4() {
		return roomPrice4;
	}

	public void setRoomPrice4(BigDecimal roomPrice4) {
		this.roomPrice4 = roomPrice4;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@ApiModelProperty("房价1")
	private BigDecimal roomPrice1;

	@ApiModelProperty("房价2")
	private BigDecimal roomPrice2;

	@ApiModelProperty("房价3")
	private BigDecimal roomPrice3;
	
	@ApiModelProperty("房价4")
	private BigDecimal roomPrice4;

	@ApiModelProperty("房间类型")
	private String roomType;
	
	@ApiModelProperty(value = "显示顺序", allowableValues = "range[1,10]")
	private int orderNum;
	
    public RoomPriceEntity()
    {

    }
}
