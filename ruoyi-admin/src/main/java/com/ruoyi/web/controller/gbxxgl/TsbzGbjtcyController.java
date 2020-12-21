package com.ruoyi.web.controller.gbxxgl;

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
import com.ruoyi.gbxxgl.domain.TsbzGbjtcy;
import com.ruoyi.gbxxgl.service.ITsbzGbjtcyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 家庭成员(干部管理-个人状况)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbjtcy")
public class TsbzGbjtcyController extends BaseController {
    @Autowired
    private ITsbzGbjtcyService tsbzGbjtcyService;

    /**
     * 查询家庭成员(干部管理-个人状况)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbjtcy tsbzGbjtcy) {
        startPage();
        List<TsbzGbjtcy> list = tsbzGbjtcyService.selectTsbzGbjtcyList(tsbzGbjtcy);
        return getDataTable(list);
    }

    /**
     * 导出家庭成员(干部管理-个人状况)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:export')")
    @Log(title = "家庭成员(干部管理-个人状况)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbjtcy tsbzGbjtcy) {
        List<TsbzGbjtcy> list = tsbzGbjtcyService.selectTsbzGbjtcyList(tsbzGbjtcy);
        ExcelUtil<TsbzGbjtcy> util = new ExcelUtil<TsbzGbjtcy>(TsbzGbjtcy.class);
        return util.exportExcel(list, "gbjtcy");
    }

    /**
     * 获取家庭成员(干部管理-个人状况)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbjtcyService.selectTsbzGbjtcyById(id));
    }

    /**
     * 新增家庭成员(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:add')")
    @Log(title = "家庭成员(干部管理-个人状况)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbjtcy tsbzGbjtcy) {
        tsbzGbjtcy.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbjtcyService.insertTsbzGbjtcy(tsbzGbjtcy));
    }

    /**
     * 修改家庭成员(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:edit')")
    @Log(title = "家庭成员(干部管理-个人状况)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbjtcy tsbzGbjtcy) {
        return toAjax(tsbzGbjtcyService.updateTsbzGbjtcy(tsbzGbjtcy));
    }

    /**
     * 删除家庭成员(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbjtcy:remove')")
    @Log(title = "家庭成员(干部管理-个人状况)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbjtcyService.deleteTsbzGbjtcyByIds(ids));
    }
}
