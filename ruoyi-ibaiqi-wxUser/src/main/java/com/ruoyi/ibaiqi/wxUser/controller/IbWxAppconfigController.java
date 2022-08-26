package com.ruoyi.ibaiqi.wxUser.controller;

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
import com.ruoyi.ibaiqi.wxUser.domain.IbWxAppconfig;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxAppconfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信小程序配置Controller
 * 
 * @author zhangxuDev
 * @date 2022-08-17
 */
@RestController
@RequestMapping("/wxUser/appConfig")
public class IbWxAppconfigController extends BaseController
{
    @Autowired
    private IIbWxAppconfigService ibWxAppconfigService;

    /**
     * 查询微信小程序配置列表
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(IbWxAppconfig ibWxAppconfig)
    {
        startPage();
        List<IbWxAppconfig> list = ibWxAppconfigService.selectIbWxAppconfigList(ibWxAppconfig);
        return getDataTable(list);
    }

    /**
     * 导出微信小程序配置列表
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:export')")
    @Log(title = "微信小程序配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, IbWxAppconfig ibWxAppconfig)
    {
        List<IbWxAppconfig> list = ibWxAppconfigService.selectIbWxAppconfigList(ibWxAppconfig);
        ExcelUtil<IbWxAppconfig> util = new ExcelUtil<IbWxAppconfig>(IbWxAppconfig.class);
        util.exportExcel(response, list, "微信小程序配置数据");
    }

    /**
     * 获取微信小程序配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ibWxAppconfigService.selectIbWxAppconfigById(id));
    }

    /**
     * 新增微信小程序配置
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:add')")
    @Log(title = "微信小程序配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody IbWxAppconfig ibWxAppconfig)
    {
        return toAjax(ibWxAppconfigService.insertIbWxAppconfig(ibWxAppconfig));
    }

    /**
     * 修改微信小程序配置
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:edit')")
    @Log(title = "微信小程序配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody IbWxAppconfig ibWxAppconfig)
    {
        return toAjax(ibWxAppconfigService.updateIbWxAppconfig(ibWxAppconfig));
    }

    /**
     * 删除微信小程序配置
     */
    @PreAuthorize("@ss.hasPermi('wxUser:appConfig:remove')")
    @Log(title = "微信小程序配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ibWxAppconfigService.deleteIbWxAppconfigByIds(ids));
    }
}
