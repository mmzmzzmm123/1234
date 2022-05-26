package com.moon.home.controller;

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
import com.moon.home.domain.HomeDaily;
import com.moon.home.service.IHomeDailyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 家居Controller
 * 
 * @author fo
 * @date 2022-05-23
 */
@RestController
@RequestMapping("/home/daily")
public class HomeDailyController extends BaseController
{
    @Autowired
    private IHomeDailyService homeDailyService;

    /**
     * 查询家居列表
     */
    @PreAuthorize("@ss.hasPermi('home:daily:list')")
    @GetMapping("/list")
    public TableDataInfo list(HomeDaily homeDaily)
    {
        startPage();
        List<HomeDaily> list = homeDailyService.selectHomeDailyList(homeDaily);
        return getDataTable(list);
    }

    /**
     * 导出家居列表
     */
    @PreAuthorize("@ss.hasPermi('home:daily:export')")
    @Log(title = "家居", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomeDaily homeDaily)
    {
        List<HomeDaily> list = homeDailyService.selectHomeDailyList(homeDaily);
        ExcelUtil<HomeDaily> util = new ExcelUtil<HomeDaily>(HomeDaily.class);
        util.exportExcel(response, list, "家居数据");
    }

    /**
     * 获取家居详细信息
     */
    @PreAuthorize("@ss.hasPermi('home:daily:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(homeDailyService.selectHomeDailyById(id));
    }

    /**
     * 新增家居
     */
    @PreAuthorize("@ss.hasPermi('home:daily:add')")
    @Log(title = "家居", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeDaily homeDaily)
    {
        return toAjax(homeDailyService.insertHomeDaily(homeDaily));
    }

    /**
     * 修改家居
     */
    @PreAuthorize("@ss.hasPermi('home:daily:edit')")
    @Log(title = "家居", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeDaily homeDaily)
    {
        return toAjax(homeDailyService.updateHomeDaily(homeDaily));
    }

    /**
     * 删除家居
     */
    @PreAuthorize("@ss.hasPermi('home:daily:remove')")
    @Log(title = "家居", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(homeDailyService.deleteHomeDailyByIds(ids));
    }
}
