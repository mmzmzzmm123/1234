package com.onethinker.web.controller.weatherinfo;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.activity.domain.WeatherInfo;
import com.onethinker.activity.service.IWeatherInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.onethinker.common.annotation.Log;
import com.onethinker.common.core.controller.BaseController;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.enums.BusinessType;

import com.onethinker.common.utils.poi.ExcelUtil;
import com.onethinker.common.core.page.TableDataInfo;

/**
 * 天气预报最新结果
 *
 * @author yangyouqi
 * @date 2024-03-08
 */
@RestController
@RequestMapping("/onethinker/weatherInfo")
public class WeatherInfoController extends BaseController {
    @Autowired
    private IWeatherInfoService weatherInfoService;

    /**
     * 查询天气预报最新结果列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(WeatherInfo weatherInfo) {
        startPage();
        List<WeatherInfo> list = weatherInfoService.selectWeatherInfoList(weatherInfo);
        return getDataTable(list);
    }

    /**
     * 导出天气预报最新结果列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:export')")
    @Log(title = "天气预报最新结果", businessType = BusinessType.EXPORT)
    @PostMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/export")
    public void export(HttpServletResponse response, WeatherInfo weatherInfo) {
        List<WeatherInfo> list = weatherInfoService.selectWeatherInfoList(weatherInfo);
        ExcelUtil<WeatherInfo> util = new ExcelUtil<>(WeatherInfo.class);
        util.exportExcel(response, list, "天气预报最新结果数据");
    }

    /**
     * 获取天气预报最新结果详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:query')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(weatherInfoService.selectWeatherInfoById(id));
    }

    /**
     * 新增天气预报最新结果
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:add')")
    @Log(title = "天气预报最新结果", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WeatherInfo weatherInfo) {
        return toAjax(weatherInfoService.insertWeatherInfo(weatherInfo));
    }

    /**
     * 修改天气预报最新结果
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:edit')")
    @Log(title = "天气预报最新结果", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WeatherInfo weatherInfo) {
        return toAjax(weatherInfoService.updateWeatherInfo(weatherInfo));
    }

    /**
     * 删除天气预报最新结果
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:remove')")
    @Log(title = "天气预报最新结果", businessType = BusinessType.DELETE)
    @DeleteMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(weatherInfoService.deleteWeatherInfoByIds(ids));
    }


    /**
     * 获取某个城市最新天气
     */
    @PreAuthorize("@ss.hasPermi('onethinker:weatherInfo:query')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/city")
    public AjaxResult getInfo(@RequestParam("city") String city) {
        return success(weatherInfoService.selectNewWeatherInfoByCity(city));
    }
}
