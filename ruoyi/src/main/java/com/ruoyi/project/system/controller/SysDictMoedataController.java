package com.ruoyi.project.system.controller;

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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.system.domain.SysDictMoedata;
import com.ruoyi.project.system.service.ISysDictMoedataService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 多级字典管理Controller
 *
 * @author tsbz
 * @date 2020-05-28
 */
@RestController
@RequestMapping("/system/moedata")
public class SysDictMoedataController extends BaseController {
    @Autowired
    private ISysDictMoedataService sysDictMoedataService;

    /**
     * 查询多级字典管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:moedata:list')"+ "||@ss.hasPermi('benyi:video:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDictMoedata sysDictMoedata) {

        List<SysDictMoedata> list = sysDictMoedataService.selectSysDictMoedataList(sysDictMoedata);
        return AjaxResult.success(list);
    }

    /**
     * 获取多级字典管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:moedata:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(sysDictMoedataService.selectSysDictMoedataById(id));
    }

    /**
     * 新增多级字典管理
     */
    @PreAuthorize("@ss.hasPermi('system:moedata:add')")
    @Log(title = "多级字典管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDictMoedata sysDictMoedata) {
        sysDictMoedata.setIsdel("0");
        if (sysDictMoedata.getPid() == null) {
            sysDictMoedata.setPid((long) 0);
            sysDictMoedata.setAncestors("0");
        } else {
            sysDictMoedata.setAncestors("0," + sysDictMoedata.getPid());
        }
        return toAjax(sysDictMoedataService.insertSysDictMoedata(sysDictMoedata));
    }

    /**
     * 修改多级字典管理
     */
    @PreAuthorize("@ss.hasPermi('system:moedata:edit')")
    @Log(title = "多级字典管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysDictMoedata sysDictMoedata) {
        return toAjax(sysDictMoedataService.updateSysDictMoedata(sysDictMoedata));
    }

    /**
     * 删除多级字典管理
     */
    @PreAuthorize("@ss.hasPermi('system:moedata:remove')")
    @Log(title = "多级字典管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long  id) {
        return toAjax(sysDictMoedataService.deleteSysDictMoedataById(id));
    }
}
