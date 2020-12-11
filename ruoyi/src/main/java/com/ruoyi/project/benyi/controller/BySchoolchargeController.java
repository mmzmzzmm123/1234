package com.ruoyi.project.benyi.controller;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.BySchoolcharge;
import com.ruoyi.project.benyi.service.IBySchoolchargeService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 园所收费标准Controller
 *
 * @author tsbz
 * @date 2020-12-10
 */
@RestController
@RequestMapping("/benyi/schoolcharge")
public class BySchoolchargeController extends BaseController {
    @Autowired
    private IBySchoolchargeService bySchoolchargeService;

    /**
     * 查询园所收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:list')")
    @GetMapping("/list")
    public TableDataInfo list(BySchoolcharge bySchoolcharge) {
        startPage();
        List<BySchoolcharge> list = bySchoolchargeService.selectBySchoolchargeList(bySchoolcharge);
        return getDataTable(list);
    }

    /**
     * 查询幼儿收费列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:list')")
    @GetMapping("/child/list")
    public TableDataInfo childlist(BySchoolcharge bySchoolcharge) {
        startPage();
        List<BySchoolcharge> list = bySchoolchargeService.selectByChildchargeList(bySchoolcharge);
        return getDataTable(list);
    }

    /**
     * 导出园所收费标准列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:export')")
    @Log(title = "园所收费标准", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BySchoolcharge bySchoolcharge) {
        List<BySchoolcharge> list = bySchoolchargeService.selectBySchoolchargeList(bySchoolcharge);
        ExcelUtil<BySchoolcharge> util = new ExcelUtil<BySchoolcharge>(BySchoolcharge.class);
        return util.exportExcel(list, "schoolcharge");
    }

    /**
     * 获取园所收费标准详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bySchoolchargeService.selectBySchoolchargeById(id));
    }

    /**
     * 新增园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:edit')")
    @Log(title = "园所收费标准", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BySchoolcharge bySchoolcharge) {
        bySchoolcharge.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        bySchoolcharge.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        return toAjax(bySchoolchargeService.insertBySchoolcharge(bySchoolcharge));
    }

    /**
     * 修改园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:edit')")
    @Log(title = "园所收费标准", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BySchoolcharge bySchoolcharge) {
        return toAjax(bySchoolchargeService.updateBySchoolcharge(bySchoolcharge));
    }

    /**
     * 删除园所收费标准
     */
    @PreAuthorize("@ss.hasPermi('benyi:schoolcharge:remove')")
    @Log(title = "园所收费标准", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bySchoolchargeService.deleteBySchoolchargeByIds(ids));
    }
}
