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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhgcsj;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhgcsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考核过程数据Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhgcsj")
public class TsbzJxzxkhgcsjController extends BaseController {
    @Autowired
    private ITsbzJxzxkhgcsjService tsbzJxzxkhgcsjService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        startPage();
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        List<TsbzJxzxkhgcsj> list = tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjList(tsbzJxzxkhgcsj);
        return getDataTable(list);
    }

    /**
     * 导出考核过程数据列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:export')")
    @Log(title = "考核过程数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        List<TsbzJxzxkhgcsj> list = tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjList(tsbzJxzxkhgcsj);
        ExcelUtil<TsbzJxzxkhgcsj> util = new ExcelUtil<TsbzJxzxkhgcsj>(TsbzJxzxkhgcsj.class);
        return util.exportExcel(list, "jxzxkhgcsj");
    }

    /**
     * 获取考核过程数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        TsbzJxzxkhgcsj tsbzJxzxkhgcsj = new TsbzJxzxkhgcsj();
        tsbzJxzxkhgcsj.setId(id);
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return AjaxResult.success(tsbzJxzxkhgcsjService.selectTsbzJxzxkhgcsjById(tsbzJxzxkhgcsj));
    }

    /**
     * 新增考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:add')")
    @Log(title = "考核过程数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        tsbzJxzxkhgcsj.setId(schoolCommonController.getUuid());
        tsbzJxzxkhgcsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxkhgcsjService.insertTsbzJxzxkhgcsj(tsbzJxzxkhgcsj));
    }

    /**
     * 修改考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:edit')")
    @Log(title = "考核过程数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhgcsj tsbzJxzxkhgcsj) {
        return toAjax(tsbzJxzxkhgcsjService.updateTsbzJxzxkhgcsj(tsbzJxzxkhgcsj));
    }

    /**
     * 删除考核过程数据
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhgcsj:remove')")
    @Log(title = "考核过程数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(tsbzJxzxkhgcsjService.deleteTsbzJxzxkhgcsjByIds(ids));
    }
}
