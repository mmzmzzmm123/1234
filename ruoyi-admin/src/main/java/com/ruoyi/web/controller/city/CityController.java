package com.ruoyi.web.controller.city;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.domain.City;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.service.ICityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 城市Controller
 *
 * @author ruoyi
 * @date 2024-01-08
 */
@RestController
@RequestMapping("/system/city")
public class CityController extends BaseController {
    @Autowired
    private ICityService cityService;

    /**
     * 查询 城市列表
     */
    @PreAuthorize("@ss.hasPermi('system:city:list')")
    @GetMapping("/list")
    public TableDataInfo list(City city) {
        startPage();
        List<City> list = cityService.selectCityList(city);
        return getDataTable(list);
    }

    /**
     * 导出 城市列表
     */
    @PreAuthorize("@ss.hasPermi('system:city:export')")
    @Log(title = " 城市", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, City city) {
        List<City> list = cityService.selectCityList(city);
        ExcelUtil<City> util = new ExcelUtil<City>(City.class);
        util.exportExcel(response, list, " 城市数据");
    }

    /**
     * 获取 城市详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:city:query')")
    @GetMapping(value = "/{code}")
    public AjaxResult getInfo(@PathVariable("code") Long code) {
        return success(cityService.selectCityByCode(code));
    }

    /**
     * 新增 城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:add')")
    @Log(title = " 城市", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody City city) {
        return toAjax(cityService.insertCity(city));
    }

    /**
     * 修改 城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:edit')")
    @Log(title = " 城市", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody City city) {
        return toAjax(cityService.updateCity(city));
    }

    /**
     * 删除 城市
     */
    @PreAuthorize("@ss.hasPermi('system:city:remove')")
    @Log(title = " 城市", businessType = BusinessType.DELETE)
    @DeleteMapping("/{codes}")
    public AjaxResult remove(@PathVariable Long[] codes) {
        return toAjax(cityService.deleteCityByCodes(codes));
    }
}
