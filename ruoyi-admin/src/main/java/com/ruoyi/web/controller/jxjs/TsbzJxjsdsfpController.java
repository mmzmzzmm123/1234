package com.ruoyi.web.controller.jxjs;

import java.util.List;

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
import com.ruoyi.jxjs.domain.TsbzJxjsdsfp;
import com.ruoyi.jxjs.service.ITsbzJxjsdsfpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习教师导师分配Controller
 *
 * @author tsbz
 * @date 2021-06-28
 */
@RestController
@RequestMapping("/jxjs/jxjsdsfp")
public class TsbzJxjsdsfpController extends BaseController {
    @Autowired
    private ITsbzJxjsdsfpService tsbzJxjsdsfpService;

    /**
     * 查询见习教师导师分配列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxjsdsfp tsbzJxjsdsfp) {
        startPage();
        List<TsbzJxjsdsfp> list = tsbzJxjsdsfpService.selectTsbzJxjsdsfpList(tsbzJxjsdsfp);
        return getDataTable(list);
    }

    /**
     * 导出见习教师导师分配列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:export')")
    @Log(title = "见习教师导师分配", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxjsdsfp tsbzJxjsdsfp) {
        List<TsbzJxjsdsfp> list = tsbzJxjsdsfpService.selectTsbzJxjsdsfpListExport(tsbzJxjsdsfp);
        ExcelUtil<TsbzJxjsdsfp> util = new ExcelUtil<TsbzJxjsdsfp>(TsbzJxjsdsfp.class);
        return util.exportExcel(list, "jxjsdsfp");
    }

    /**
     * 获取见习教师导师分配详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxjsdsfpService.selectTsbzJxjsdsfpById(id));
    }

    /**
     * 新增见习教师导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:add')")
    @Log(title = "见习教师导师分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxjsdsfp tsbzJxjsdsfp) {
        return toAjax(tsbzJxjsdsfpService.insertTsbzJxjsdsfp(tsbzJxjsdsfp));
    }

    /**
     * 修改见习教师导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:edit')")
    @Log(title = "见习教师导师分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxjsdsfp tsbzJxjsdsfp) {
        return toAjax(tsbzJxjsdsfpService.updateTsbzJxjsdsfp(tsbzJxjsdsfp));
    }

    /**
     * 删除见习教师导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxjsdsfp:remove')")
    @Log(title = "见习教师导师分配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxjsdsfpService.deleteTsbzJxjsdsfpByIds(ids));
    }
}
