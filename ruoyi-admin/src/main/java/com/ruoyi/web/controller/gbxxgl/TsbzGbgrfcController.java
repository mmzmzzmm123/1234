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
import com.ruoyi.gbxxgl.domain.TsbzGbgrfc;
import com.ruoyi.gbxxgl.service.ITsbzGbgrfcService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 个人风采(干部管理-个人状况)Controller
 *
 * @author tsbz
 * @date 2020-12-21
 */
@RestController
@RequestMapping("/gbxxgl/gbgrfc")
public class TsbzGbgrfcController extends BaseController {
    @Autowired
    private ITsbzGbgrfcService tsbzGbgrfcService;

    /**
     * 查询个人风采(干部管理-个人状况)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzGbgrfc tsbzGbgrfc) {
        startPage();
        List<TsbzGbgrfc> list = tsbzGbgrfcService.selectTsbzGbgrfcList(tsbzGbgrfc);
        return getDataTable(list);
    }

    /**
     * 导出个人风采(干部管理-个人状况)列表
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:export')")
    @Log(title = "个人风采(干部管理-个人状况)", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzGbgrfc tsbzGbgrfc) {
        List<TsbzGbgrfc> list = tsbzGbgrfcService.selectTsbzGbgrfcList(tsbzGbgrfc);
        ExcelUtil<TsbzGbgrfc> util = new ExcelUtil<TsbzGbgrfc>(TsbzGbgrfc.class);
        return util.exportExcel(list, "gbgrfc");
    }

    /**
     * 获取个人风采(干部管理-个人状况)详细信息
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzGbgrfcService.selectTsbzGbgrfcById(id));
    }

    /**
     * 新增个人风采(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:add')")
    @Log(title = "个人风采(干部管理-个人状况)", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzGbgrfc tsbzGbgrfc) {
        tsbzGbgrfc.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzGbgrfcService.insertTsbzGbgrfc(tsbzGbgrfc));
    }

    /**
     * 修改个人风采(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:edit')")
    @Log(title = "个人风采(干部管理-个人状况)", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzGbgrfc tsbzGbgrfc) {
        return toAjax(tsbzGbgrfcService.updateTsbzGbgrfc(tsbzGbgrfc));
    }

    /**
     * 删除个人风采(干部管理-个人状况)
     */
    @PreAuthorize("@ss.hasPermi('gbxxgl:gbgrfc:remove')")
    @Log(title = "个人风采(干部管理-个人状况)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzGbgrfcService.deleteTsbzGbgrfcByIds(ids));
    }
}
