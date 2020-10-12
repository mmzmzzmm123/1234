package com.ruoyi.web.controller.qtjs;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.qtjs.domain.TsbzQtjspxsh;
import com.ruoyi.qtjs.service.ITsbzQtjspxshService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选审核过程Controller
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@RestController
@RequestMapping("/qtjs/qtjspxsh")
public class TsbzQtjspxshController extends BaseController {
    @Autowired
    private ITsbzQtjspxshService tsbzQtjspxshService;

    /**
     * 查询群体教师评选审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjspxsh tsbzQtjspxsh) {
        startPage();
        List<TsbzQtjspxsh> list = tsbzQtjspxshService.selectTsbzQtjspxshList(tsbzQtjspxsh);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:export')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjspxsh tsbzQtjspxsh) {
        List<TsbzQtjspxsh> list = tsbzQtjspxshService.selectTsbzQtjspxshList(tsbzQtjspxsh);
        ExcelUtil<TsbzQtjspxsh> util = new ExcelUtil<TsbzQtjspxsh>(TsbzQtjspxsh.class);
        return util.exportExcel(list, "qtjspxsh");
    }

    /**
     * 查询考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:list')" + "||@ss.hasPermi('qtjs:qtjspxfazbxsj:list')")
    @GetMapping("/listbyfaid")
    public TableDataInfo listByFaid(TsbzQtjspxsh tsbzQtjspxsh) {
        tsbzQtjspxsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());
        List<TsbzQtjspxsh> list = tsbzQtjspxshService.selectTsbzQtjspxshList(tsbzQtjspxsh);
        return getDataTable(list);
    }

    /**
     * 获取群体教师评选审核过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzQtjspxshService.selectTsbzQtjspxshById(id));
    }

    /**
     * 新增群体教师评选审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:add')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjspxsh tsbzQtjspxsh) {
        return toAjax(tsbzQtjspxshService.insertTsbzQtjspxsh(tsbzQtjspxsh));
    }

    /**
     * 修改群体教师评选审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:edit')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjspxsh tsbzQtjspxsh) {
        return toAjax(tsbzQtjspxshService.updateTsbzQtjspxsh(tsbzQtjspxsh));
    }

    /**
     * 删除群体教师评选审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:remove')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjspxshService.deleteTsbzQtjspxshByIds(ids));
    }

    /**
     * 回退考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:edit')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.UPDATE)
    @PostMapping("/back/{id}/{status}")
    public AjaxResult back(@PathVariable Long id, @PathVariable String status) {
        TsbzQtjspxsh tsbzQtjspxsh = new TsbzQtjspxsh();
        tsbzQtjspxsh.setId(id);
        tsbzQtjspxsh.setStatus(status);
        return toAjax(tsbzQtjspxshService.updateTsbzQtjspxsh(tsbzQtjspxsh));
    }

    /**
     * 提交考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxsh:add')" + "||@ss.hasPermi('qtjs:qtjspxfazbxsj:edit')")
    @Log(title = "群体教师评选审核过程", businessType = BusinessType.INSERT)
    @PostMapping("/check/{id}")
    public AjaxResult add(@PathVariable Long id) {
        TsbzQtjspxsh tsbzQtjspxsh = new TsbzQtjspxsh();
        tsbzQtjspxsh.setFaid(id);
        tsbzQtjspxsh.setStatus("1");
        tsbzQtjspxsh.setJsid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzQtjspxsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzQtjspxshService.insertTsbzQtjspxsh(tsbzQtjspxsh));
    }
}
