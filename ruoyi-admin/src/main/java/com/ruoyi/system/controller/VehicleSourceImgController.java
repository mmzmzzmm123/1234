package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.VehicleSourceImg;
import com.ruoyi.system.service.IVehicleSourceImgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 车源图片管理Controller
 * 
 * @author carol
 * @date 2024-02-29
 */
@RestController
@RequestMapping("/system/img")
public class VehicleSourceImgController extends BaseController
{
    @Autowired
    private IVehicleSourceImgService vehicleSourceImgService;

    /**
     * 查询车源图片管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:img:list')")
    @GetMapping("/list")
    public TableDataInfo list(VehicleSourceImg vehicleSourceImg)
    {
        startPage();
        List<VehicleSourceImg> list = vehicleSourceImgService.selectVehicleSourceImgList(vehicleSourceImg);
        return getDataTable(list);
    }

    /**
     * 导出车源图片管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:img:export')")
    @Log(title = "车源图片管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VehicleSourceImg vehicleSourceImg)
    {
        List<VehicleSourceImg> list = vehicleSourceImgService.selectVehicleSourceImgList(vehicleSourceImg);
        ExcelUtil<VehicleSourceImg> util = new ExcelUtil<VehicleSourceImg>(VehicleSourceImg.class);
        util.exportExcel(response, list, "车源图片管理数据");
    }

    /**
     * 获取车源图片管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:img:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(vehicleSourceImgService.selectVehicleSourceImgById(id));
    }

    /**
     * 新增车源图片管理
     */
    @PreAuthorize("@ss.hasPermi('system:img:add')")
    @Log(title = "车源图片管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VehicleSourceImg vehicleSourceImg)
    {
        return toAjax(vehicleSourceImgService.insertVehicleSourceImg(vehicleSourceImg));
    }

    /**
     * 修改车源图片管理
     */
    @PreAuthorize("@ss.hasPermi('system:img:edit')")
    @Log(title = "车源图片管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VehicleSourceImg vehicleSourceImg)
    {
        return toAjax(vehicleSourceImgService.updateVehicleSourceImg(vehicleSourceImg));
    }

    /**
     * 删除车源图片管理
     */
    @PreAuthorize("@ss.hasPermi('system:img:remove')")
    @Log(title = "车源图片管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(vehicleSourceImgService.deleteVehicleSourceImgByIds(ids));
    }
}
