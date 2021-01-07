package com.ruoyi.web.controller.hotel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hotel.domain.HtlPriceType;
import com.ruoyi.hotel.domain.HtlRoomPrice;
import com.ruoyi.hotel.domain.HtlRoomType;
import com.ruoyi.hotel.service.IHtlHotelInfoService;
import com.ruoyi.hotel.service.IHtlPriceTypeService;
import com.ruoyi.hotel.service.IHtlRoomPriceService;
import com.ruoyi.hotel.service.IHtlRoomTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;

/**
 * 房间价格Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Api("房间价格管理")
@RestController
@RequestMapping("/hotel/roomPrice")
public class HtlRoomPriceController extends BaseController
{
	@Autowired
	private IHtlRoomPriceService htlRoomPriceService;

	@Autowired
	private IHtlRoomTypeService htlRoomTypeService;

	@Autowired
	private IHtlPriceTypeService htlPriceTypeService;
    
    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;


    /**
     * 获取房间价格详细信息
     */
    @ApiOperation("查询房间价格列表")
    @GetMapping("/query")
    public AjaxResult query()
    {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		List<RoomTypeEntity> result = new ArrayList<RoomTypeEntity>();

		HtlRoomType htlRoomType = new HtlRoomType();
		htlRoomType.setHotelId(hotelId);
		List<HtlRoomType> roomTypeList = htlRoomTypeService.selectHtlRoomTypeList(htlRoomType);
		for (HtlRoomType roomType : roomTypeList) {
			RoomTypeEntity typeEntity = new RoomTypeEntity();
			BeanUtils.copyBeanProp(typeEntity, roomType);

			HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
			htlRoomPrice.setRoomTypeId(roomType.getRoomTypeId());
			htlRoomPrice.setHotelId(hotelId);
			List<HtlRoomPrice> roomPriceList = htlRoomPriceService.selectHtlRoomPriceList(htlRoomPrice);
			for (HtlRoomPrice roomPrice : roomPriceList) {
				RoomPriceEntity priceEntity = new RoomPriceEntity();
				BeanUtils.copyBeanProp(priceEntity, roomPrice);

				HtlPriceType htlPriceType = htlPriceTypeService.selectHtlPriceTypeById(roomPrice.getPriceTypeId());
				if (null != htlPriceType) {
					priceEntity.setPriceType(htlPriceType.getPriceType());
				}

				typeEntity.addPrice(priceEntity);
			}
			result.add(typeEntity);
		}
		return AjaxResult.success(result);
    }

    /**
     * 新增房间价格
     */
    @ApiOperation("新增房间价格")
    @Log(title = "房间价格", businessType = BusinessType.INSERT)
    @PostMapping("/add")
	public AjaxResult add(@RequestBody RoomTypeEntity roomTypeEntity) {
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		if (hotelId < 0) {
			return AjaxResult.error("请配置酒店信息");
		}
		HtlRoomType htlRoomType = new HtlRoomType();
		htlRoomType.setHotelId(hotelId);
		htlRoomType.setOrderNum(roomTypeEntity.getOrderNum());
		htlRoomType.setRoomType(roomTypeEntity.getRoomType());
		htlRoomType.setCreateBy(sysUser.getUserName());
		htlRoomTypeService.insertHtlRoomType(htlRoomType);
		
		BeanUtils.copyBeanProp(roomTypeEntity, htlRoomType);

		List<RoomPriceEntity> priceList = roomTypeEntity.getPriceList();
		for (RoomPriceEntity price : priceList) {
			HtlPriceType htlPriceType = htlPriceTypeService.selectHtlPriceTypeById(price.getPriceTypeId());
			if (null == htlPriceType) {
				return AjaxResult.error("参数【房价类型ID】传递不正确");
			}
			HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
			htlRoomPrice.setRoomTypeId(htlRoomType.getRoomTypeId());
			htlRoomPrice.setPriceTypeId(price.getPriceTypeId());
			htlRoomPrice.setRoomPrice(price.getRoomPrice());
			htlRoomPrice.setHotelId(hotelId);
			htlRoomPrice.setCreateBy(sysUser.getUserName());
			htlRoomPriceService.insertHtlRoomPrice(htlRoomPrice);
			price.setPriceType(htlPriceType.getPriceType());
		}
		return AjaxResult.success(roomTypeEntity);
	}

    /**
     * 修改房间价格
     */
    @ApiOperation("修改房间价格")
    @Log(title = "房间价格", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@RequestBody RoomTypeEntity roomTypeEntity)
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		long hotelId = htlHotelInfoService.getHotelIdByUserId(sysUser.getUserId());
		HtlRoomType htlRoomType = htlRoomTypeService.selectHtlRoomTypeById(roomTypeEntity.getRoomTypeId());
		if (null == htlRoomType || hotelId != htlRoomType.getHotelId()) {
			return AjaxResult.error("传递参数不正确");
		}
		htlRoomType.setOrderNum(roomTypeEntity.getOrderNum());
		htlRoomType.setRoomType(roomTypeEntity.getRoomType());
		htlRoomType.setUpdateBy(sysUser.getUserName());
		htlRoomTypeService.updateHtlRoomType(htlRoomType);
		
		BeanUtils.copyBeanProp(roomTypeEntity, htlRoomType);

		List<RoomPriceEntity> priceList = roomTypeEntity.getPriceList();
		for (RoomPriceEntity price : priceList) {
	    	HtlRoomPrice htlRoomPrice = new HtlRoomPrice();
	    	htlRoomPrice.setHotelId(hotelId);
	    	htlRoomPrice.setPriceTypeId(price.getPriceTypeId());
	    	htlRoomPrice.setRoomTypeId(htlRoomType.getRoomTypeId());
	    	
			List<HtlRoomPrice> list = htlRoomPriceService.selectHtlRoomPriceList(htlRoomPrice);
			if (null == list || list.size() == 0) {
		    	htlRoomPrice.setRoomPrice(price.getRoomPrice());
		    	htlRoomPrice.setCreateBy(sysUser.getUserName());
				htlRoomPriceService.insertHtlRoomPrice(htlRoomPrice);
			} else {
				htlRoomPrice.setRoomPrice(price.getRoomPrice());
				htlRoomPrice.setUpdateBy(sysUser.getUserName());
		        htlRoomPriceService.updateHtlRoomPrice(htlRoomPrice);
			}
		}

        return AjaxResult.success(roomTypeEntity);
    }

    /**
     * 删除房间价格
     */
    @ApiOperation("删除房间价格")
    @Log(title = "房间价格", businessType = BusinessType.DELETE)
    @ApiImplicitParam(name = "roomTypeId", value = "房型ID", required = true, dataType = "long")
	@DeleteMapping("/delete")
	public AjaxResult remove(Long roomTypeId)
    {
		htlRoomTypeService.deleteHtlRoomTypeById(roomTypeId);
		htlRoomPriceService.deleteHtlRoomPriceById(roomTypeId);
		return AjaxResult.success();
    }
}

@ApiModel("房型实体")
class RoomTypeEntity
{
	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public Long getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public List<RoomPriceEntity> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<RoomPriceEntity> priceList) {
		this.priceList = priceList;
	}
	
	public void addPrice(RoomPriceEntity price) {
		if (null == this.priceList) {
			this.priceList = new ArrayList<RoomPriceEntity>();
		}
		this.priceList.add(price);
	}

	@ApiModelProperty("房间类型")
	private String roomType;
	
	@ApiModelProperty("房型ID")
    private Long roomTypeId;

	@ApiModelProperty("酒店ID")
    private Long hotelId;

	@ApiModelProperty(value = "显示顺序", allowableValues = "range[1,10]")
    private Integer orderNum;
	
	@ApiModelProperty("房价")
	private List<RoomPriceEntity> priceList;
    
    public RoomTypeEntity()
    {

    }
}

@ApiModel(value = "RoomPriceEntity", description = "房价实体")
class RoomPriceEntity
{
	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	
	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	@ApiModelProperty("房价类别ID")
	private Long priceTypeId;
	
	@ApiModelProperty("房价类别")
	private String priceType;

	@ApiModelProperty("房价")
	private BigDecimal roomPrice;
	
    public RoomPriceEntity()
    {

    }
}
