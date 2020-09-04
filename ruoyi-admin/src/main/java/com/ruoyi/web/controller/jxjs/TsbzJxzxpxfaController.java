package com.ruoyi.web.controller.jxjs;

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
import com.ruoyi.jxjs.domain.TsbzJxzxpxfa;
import com.ruoyi.jxjs.service.ITsbzJxzxpxfaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习之星评选方案Controller
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jxzxpxfa")
public class TsbzJxzxpxfaController extends BaseController {
    @Autowired
    private ITsbzJxzxpxfaService tsbzJxzxpxfaService;

    /**
     * 查询见习之星评选方案列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:list')"+ "||@ss.hasPermi('jxjs:jdcx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxpxfa tsbzJxzxpxfa) {
        startPage();
        List<TsbzJxzxpxfa> list = tsbzJxzxpxfaService.selectTsbzJxzxpxfaList(tsbzJxzxpxfa);
        return getDataTable(list);
    }

    /**
     * 导出见习之星评选方案列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:export')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxpxfa tsbzJxzxpxfa) {
        List<TsbzJxzxpxfa> list = tsbzJxzxpxfaService.selectTsbzJxzxpxfaList(tsbzJxzxpxfa);
        ExcelUtil<TsbzJxzxpxfa> util = new ExcelUtil<TsbzJxzxpxfa>(TsbzJxzxpxfa.class);
        return util.exportExcel(list, "jxzxpxfa");
    }

    /**
     * 获取见习之星评选方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxpxfaService.selectTsbzJxzxpxfaById(id));
    }

    /**
     * 新增见习之星评选方案
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:add')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxpxfa tsbzJxzxpxfa) {
        System.out.println("time:" + tsbzJxzxpxfa.getFayxjssj());
        //创建人id
        tsbzJxzxpxfa.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxpxfaService.insertTsbzJxzxpxfa(tsbzJxzxpxfa));
    }

    /**
     * 修改见习之星评选方案
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:edit')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxpxfa tsbzJxzxpxfa) {
        return toAjax(tsbzJxzxpxfaService.updateTsbzJxzxpxfa(tsbzJxzxpxfa));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:edit')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody TsbzJxzxpxfa tsbzJxzxpxfa) {
        return toAjax(tsbzJxzxpxfaService.updateTsbzJxzxpxfa(tsbzJxzxpxfa));
    }

    /**
     * 删除见习之星评选方案
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:remove')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJxzxpxfaService.deleteTsbzJxzxpxfaByIds(ids));
    }
    /**
     * 复制见习之星评选方案
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jxzxpxfa:add')")
    @Log(title = "见习之星评选方案", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@PathVariable Long id) {
        TsbzJxzxpxfa tsbzJx = tsbzJxzxpxfaService.selectTsbzJxzxpxfaById(id);
        return toAjax(tsbzJxzxpxfaService.insertTsbzJxzxpxfa(tsbzJx));
    }
}
