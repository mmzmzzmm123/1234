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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hotel.domain.HtlHotelInfo;
import com.ruoyi.hotel.service.IHtlHotelInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 酒店信息Controller
 * 
 * @author sucheng
 * @date 2020-11-22
 */
@Api("酒店信息管理")
@RestController
@RequestMapping("/hotel/hotelInfo")
public class HtlHotelInfoController extends BaseController
{
    @Autowired
    private IHtlHotelInfoService htlHotelInfoService;

    /**
     * 查询酒店信息列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(HtlHotelInfo htlHotelInfo)
    {
        startPage();
        List<HtlHotelInfo> list = htlHotelInfoService.selectHtlHotelInfoList(htlHotelInfo);
        return getDataTable(list);
    }

    /**
     * 导出酒店信息列表
     */
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:export')")
    @Log(title = "酒店信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(HtlHotelInfo htlHotelInfo)
    {
        List<HtlHotelInfo> list = htlHotelInfoService.selectHtlHotelInfoList(htlHotelInfo);
        ExcelUtil<HtlHotelInfo> util = new ExcelUtil<HtlHotelInfo>(HtlHotelInfo.class);
        return util.exportExcel(list, "hotelInfo");
    }

    /**
     * 获取酒店信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:query')")
    @GetMapping(value = "/{hotelId}")
    public AjaxResult getInfo(@PathVariable("hotelId") Long hotelId)
    {
        return AjaxResult.success(htlHotelInfoService.selectHtlHotelInfoById(hotelId));
    }
    
    /**
     * 获取当前用户关联的酒店信息
     */
    @ApiOperation("获取当前用户关联的酒店信息")
    @GetMapping(value = "/query")
    public AjaxResult query()
    {
    	SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        return AjaxResult.success(htlHotelInfoService.selectHtlHotelInfoByUserId(sysUser.getUserId()));
    }

    /**
     * 保存酒店信息
     */
    @ApiOperation("保存酒店信息")
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:save')")
    @Log(title = "酒店信息", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult save(HotelInfoEntity hotelInfoEntity)
	{
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		HtlHotelInfo htlHotelInfo = htlHotelInfoService.selectHtlHotelInfoByUserId(sysUser.getUserId());
		if (null == htlHotelInfo) {
			htlHotelInfo = new HtlHotelInfo();
			BeanUtils.copyBeanProp(htlHotelInfo, hotelInfoEntity);
			htlHotelInfo.setUserId(sysUser.getUserId());
			htlHotelInfo.setCreateBy(sysUser.getUserName());
			htlHotelInfo.setCreateTime(DateUtils.getNowDate());
			htlHotelInfoService.insertHtlHotelInfo(htlHotelInfo);
			return AjaxResult.success(htlHotelInfo);
		} else {
			BeanUtils.copyBeanProp(htlHotelInfo, hotelInfoEntity);
			htlHotelInfo.setUpdateBy(sysUser.getUserName());
			htlHotelInfo.setUpdateTime(DateUtils.getNowDate());
			htlHotelInfoService.updateHtlHotelInfo(htlHotelInfo);
			return AjaxResult.success(htlHotelInfo);
		}
	}

    /**
     * 修改酒店信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:edit')")
    @Log(title = "酒店信息", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(HotelInfoEntity hotelInfoEntity)
    {
    	HtlHotelInfo htlHotelInfo = new HtlHotelInfo();//
		BeanUtils.copyBeanProp(htlHotelInfo, hotelInfoEntity);
		
		SysUser sysUser = SecurityUtils.getLoginUser().getUser();
		
		htlHotelInfo.setUpdateBy(sysUser.getUserName());
		htlHotelInfo.setUpdateTime(DateUtils.getNowDate());
        return toAjax(htlHotelInfoService.updateHtlHotelInfo(htlHotelInfo));
    }

    /**
     * 删除酒店信息
     */
    @PreAuthorize("@ss.hasPermi('hotel:hotelInfo:remove')")
    @Log(title = "酒店信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{hotelIds}")
    public AjaxResult remove(@PathVariable Long[] hotelIds)
    {
        return toAjax(htlHotelInfoService.deleteHtlHotelInfoByIds(hotelIds));
    }
    
	/**
	 * LOGO上传
	 */
	@ApiOperation("上传酒店LOGO")
	@Log(title = "酒店LOGO", businessType = BusinessType.UPDATE)
	@PostMapping("/logo")
    public AjaxResult uploadLogo(@RequestParam("logofile") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String logo = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
			HtlHotelInfo hotelInfo = htlHotelInfoService.selectHtlHotelInfoByUserId(sysUser.getUserId());
			if (null != hotelInfo) {
				hotelInfo.setLogoPath(logo);
				hotelInfo.setUpdateBy(sysUser.getUserName());
				hotelInfo.setUpdateTime(DateUtils.getNowDate());
				htlHotelInfoService.updateHtlHotelInfo(hotelInfo);
			} else {
				hotelInfo = new HtlHotelInfo();
				hotelInfo.setLogoPath(logo);
			}
			return AjaxResult.success(hotelInfo);
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
	
	/**
	 * 上传二维码
	 */
	@ApiOperation("上传二维码")
	@Log(title = "二维码", businessType = BusinessType.UPDATE)
	@PostMapping("/qrCode")
    public AjaxResult uploadQrCode(@RequestParam("qrCode") MultipartFile file) throws IOException
    {
        if (!file.isEmpty())
        {
            String qrCodePath = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
            SysUser sysUser = SecurityUtils.getLoginUser().getUser();
			HtlHotelInfo hotelInfo = htlHotelInfoService.selectHtlHotelInfoByUserId(sysUser.getUserId());
			if (null != hotelInfo) {
				hotelInfo.setQrCodePath(qrCodePath);
				hotelInfo.setUpdateBy(sysUser.getUserName());
				hotelInfo.setUpdateTime(DateUtils.getNowDate());
				htlHotelInfoService.updateHtlHotelInfo(hotelInfo);
			} else {
				hotelInfo = new HtlHotelInfo();
				hotelInfo.setQrCodePath(qrCodePath);
			}
			return AjaxResult.success(hotelInfo);
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}

@ApiModel("酒店信息实体")
class HotelInfoEntity
{
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getQrCodePath() {
		return qrCodePath;
	}

	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getReservationPhone() {
		return reservationPhone;
	}

	public void setReservationPhone(String reservationPhone) {
		this.reservationPhone = reservationPhone;
	}

	public String getServiceItems() {
		return serviceItems;
	}

	public void setServiceItems(String serviceItems) {
		this.serviceItems = serviceItems;
	}

	@ApiModelProperty("酒店名称")
	private String hotelName;

	@ApiModelProperty("LOGO文件路径")
	private String logoPath;

	@ApiModelProperty("城市编码")
	private int cityId;

	@ApiModelProperty("二维码文件路径")
	private String qrCodePath;

	@ApiModelProperty("公告信息")
	private String notice;

	@ApiModelProperty("预定电话")
	private String reservationPhone;

	@ApiModelProperty("服务项目")
	private String serviceItems;
	
    public HotelInfoEntity()
    {

    }
}
