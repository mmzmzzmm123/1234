package com.ruoyi.web.controller.jxzxkhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.web.controller.common.SchoolCommonController;
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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhsh;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhshService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核审核过程Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhsh")
public class TsbzJxzxkhshController extends BaseController {
    @Autowired
    private ITsbzJxzxkhshService tsbzJxzxkhshService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:list')" + "||@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhsh tsbzJxzxkhsh) {
        startPage();
        List<TsbzJxzxkhsh> list = tsbzJxzxkhshService.selectTsbzJxzxkhshList(tsbzJxzxkhsh);
        return getDataTable(list);
    }

    /**
     * 查询考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:list')" + "||@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:list')")
    @GetMapping("/listbyfaid")
    public TableDataInfo listByFaid(TsbzJxzxkhsh tsbzJxzxkhsh) {
        tsbzJxzxkhsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());
        List<TsbzJxzxkhsh> list = tsbzJxzxkhshService.selectTsbzJxzxkhshList(tsbzJxzxkhsh);
        return getDataTable(list);
    }

    /**
     * 导出考核审核过程列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:export')")
    @Log(title = "考核审核过程", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhsh tsbzJxzxkhsh) {
        List<TsbzJxzxkhsh> list = tsbzJxzxkhshService.selectTsbzJxzxkhshList(tsbzJxzxkhsh);
        ExcelUtil<TsbzJxzxkhsh> util = new ExcelUtil<TsbzJxzxkhsh>(TsbzJxzxkhsh.class);
        return util.exportExcel(list, "jxzxkhsh");
    }

    /**
     * 获取考核审核过程详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxkhshService.selectTsbzJxzxkhshById(id));
    }

    /**
     * 新增考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:add')" + "||@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhsh tsbzJxzxkhsh) {
        tsbzJxzxkhsh.setJsid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJxzxkhsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxkhshService.insertTsbzJxzxkhsh(tsbzJxzxkhsh));
    }

    /**
     * 修改考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhsh tsbzJxzxkhsh) {
        //校级
       if(!schoolCommonController.isStringEmpty(tsbzJxzxkhsh.getXjshyj())&&tsbzJxzxkhsh.getXjshyj().equals("1")) {
           tsbzJxzxkhsh.setXjshr(SecurityUtils.getLoginUser().getUser().getUserId());
           tsbzJxzxkhsh.setStatus("2");
       }

        //区级
        if(!schoolCommonController.isStringEmpty(tsbzJxzxkhsh.getQjshyj())&&tsbzJxzxkhsh.getQjshyj().equals("1")) {
            tsbzJxzxkhsh.setQjshr(SecurityUtils.getLoginUser().getUser().getUserId());
            tsbzJxzxkhsh.setStatus("9");
        }

        return toAjax(tsbzJxzxkhshService.updateTsbzJxzxkhsh(tsbzJxzxkhsh));
    }

    /**
     * 删除考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:remove')")
    @Log(title = "考核审核过程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxzxkhshService.deleteTsbzJxzxkhshByIds(ids));
    }

    /**
     * 新增考核审核过程
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhsh:add')" + "||@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:edit')")
    @Log(title = "考核审核过程", businessType = BusinessType.INSERT)
    @PostMapping("/check/{id}")
    public AjaxResult add(@PathVariable Long id) {
        TsbzJxzxkhsh tsbzJxzxkhsh = new TsbzJxzxkhsh();
        tsbzJxzxkhsh.setFaid(id);
        tsbzJxzxkhsh.setJsid(SecurityUtils.getLoginUser().getUser().getUserId());
        tsbzJxzxkhsh.setCreateuseird(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxkhshService.insertTsbzJxzxkhsh(tsbzJxzxkhsh));
    }
}
