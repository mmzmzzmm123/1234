package com.ruoyi.web.controller.hotel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.hotel.domain.ChinaCityList;
import com.ruoyi.hotel.service.IChinaCityListService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/hotel/weather")
public class HtlWeatherController extends BaseController {

	@Autowired
	private IChinaCityListService cityListService;

	@ApiOperation("获取所有一级行政区划")
	@GetMapping(value = "/adm1")
	public AjaxResult adm1() {
		return AjaxResult.success(cityListService.getAllProvinces());
	}

	@ApiOperation("获取二级行政区划")
	@ApiImplicitParam(name = "adm1", value = "一级行政区划名称", required = true, dataType = "String")
	@GetMapping(value = "/adm2")
	public AjaxResult adm2(String adm1) {
		return AjaxResult.success(cityListService.selectAdm2ByAdm1(adm1));
	}

	@ApiOperation("获取三级行政区划")
	@ApiImplicitParams({ @ApiImplicitParam(name = "adm1", value = "一级行政区划名称", required = true, dataType = "String"),
			@ApiImplicitParam(name = "adm2", value = "二级行政区划名称", required = true, dataType = "String") })
	@GetMapping(value = "/adm3")
	public AjaxResult adm3(String adm1, String adm2) {
		ChinaCityList chinaCityList = new ChinaCityList();
		chinaCityList.setAdm1(adm1);
		chinaCityList.setAdm2(adm2);
		List<ChinaCityList> cityList = cityListService.selectChinaCityListList(chinaCityList);
		List<CityEntity> cityEntityList = new ArrayList<CityEntity>();
		for (ChinaCityList city : cityList) {
			CityEntity cityEntity = new CityEntity();
			BeanUtils.copyBeanProp(cityEntity, city);
			cityEntityList.add(cityEntity);
		}

		return AjaxResult.success(cityEntityList);
	}

	@ApiOperation("获取城市信息")
	@ApiImplicitParam(name = "locationId", value = "地区/城市ID", required = true, dataType = "long")
	@GetMapping(value = "/getCityInfo")
	public AjaxResult getCityInfo(Long locationId) {
		ChinaCityList cityInfo = cityListService.selectChinaCityListById(locationId);
		CityEntity cityEntity = new CityEntity();
		BeanUtils.copyBeanProp(cityEntity, cityInfo);

		return AjaxResult.success(cityEntity);
	}
}

@ApiModel("城市信息实体")
class CityEntity {
	@ApiModelProperty("该地区/城市所属一级行政区域名称")
	private String adm1;

	@ApiModelProperty("该地区/城市所属上级行政区域名称")
	private String adm2;

	@ApiModelProperty("地区/城市名称")
	private String locationName;

	@ApiModelProperty("地区/城市ID")
	private Long locationId;

	public CityEntity() {

	}

	public String getAdm1() {
		return adm1;
	}

	public void setAdm1(String adm1) {
		this.adm1 = adm1;
	}

	public String getAdm2() {
		return adm2;
	}

	public void setAdm2(String adm2) {
		this.adm2 = adm2;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
}
