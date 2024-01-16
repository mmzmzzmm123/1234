package com.ruoyi.web.controller.bk;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.onethinker.bk.domain.WebInfo;
import com.onethinker.bk.service.IWebInfoService;
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

import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 网站信息Controller
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@RestController
@RequestMapping("/bk/weiInfo")
public class WebInfoController extends BaseController {
    @Autowired
    private IWebInfoService webInfoService;

    /**
     * 查询网站信息列表
     */
    @GetMapping("/getWebInfo")
    public AjaxResult getWebInfo() {
        return AjaxResult.success(webInfoService.getWebInfo());
    }

    /**
     * 导出网站信息列表
     */
    @PreAuthorize("@ss.hasPermi('bk:info:export')")
    @Log(title = "网站信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WebInfo webInfo) {
        List<WebInfo> list = webInfoService.selectWebInfoList(webInfo);
        ExcelUtil<WebInfo> util = new ExcelUtil<WebInfo>(WebInfo. class);
        util.exportExcel(response, list, "网站信息数据");
    }

    /**
     * 获取网站信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('bk:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(webInfoService.selectWebInfoById(id));
    }

    /**
     * 新增网站信息
     */
    @PreAuthorize("@ss.hasPermi('bk:info:add')")
    @Log(title = "网站信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WebInfo webInfo) {
        return toAjax(webInfoService.insertWebInfo(webInfo));
    }

    /**
     * 修改网站信息
     */
    @PreAuthorize("@ss.hasPermi('bk:info:edit')")
    @Log(title = "网站信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WebInfo webInfo) {
        return toAjax(webInfoService.updateWebInfo(webInfo));
    }

    /**
     * 删除网站信息
     */
    @PreAuthorize("@ss.hasPermi('bk:info:remove')")
    @Log(title = "网站信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(webInfoService.deleteWebInfoByIds(ids));
    }
}
