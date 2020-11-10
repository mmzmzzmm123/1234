package com.ruoyi.web.controller.jxzxkhgl;

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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxdsfp;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxdsfpService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习导师分配Controller
 *
 * @author ruoyi
 * @date 2020-11-10
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxdsfp")
public class TsbzJxzxdsfpController extends BaseController {
    @Autowired
    private ITsbzJxzxdsfpService tsbzJxzxdsfpService;

    /**
     * 查询见习导师分配列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxdsfp tsbzJxzxdsfp) {
        startPage();
        List<TsbzJxzxdsfp> list = tsbzJxzxdsfpService.selectTsbzJxzxdsfpList(tsbzJxzxdsfp);
        return getDataTable(list);
    }

    /**
     * 导出见习导师分配列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:export')")
    @Log(title = "见习导师分配", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxdsfp tsbzJxzxdsfp) {
        List<TsbzJxzxdsfp> list = tsbzJxzxdsfpService.selectTsbzJxzxdsfpList(tsbzJxzxdsfp);
        ExcelUtil<TsbzJxzxdsfp> util = new ExcelUtil<TsbzJxzxdsfp>(TsbzJxzxdsfp.class);
        return util.exportExcel(list, "jxzxdsfp");
    }

    /**
     * 获取见习导师分配详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxdsfpService.selectTsbzJxzxdsfpById(id));
    }

    /**
     * 新增见习导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:add')")
    @Log(title = "见习导师分配", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxdsfp tsbzJxzxdsfp) {
        return toAjax(tsbzJxzxdsfpService.insertTsbzJxzxdsfp(tsbzJxzxdsfp));
    }

    /**
     * 修改见习导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:edit')")
    @Log(title = "见习导师分配", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxdsfp tsbzJxzxdsfp) {
        return toAjax(tsbzJxzxdsfpService.updateTsbzJxzxdsfp(tsbzJxzxdsfp));
    }

    /**
     * 删除见习导师分配
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxdsfp:remove')")
    @Log(title = "见习导师分配", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxzxdsfpService.deleteTsbzJxzxdsfpByIds(ids));
    }
}
