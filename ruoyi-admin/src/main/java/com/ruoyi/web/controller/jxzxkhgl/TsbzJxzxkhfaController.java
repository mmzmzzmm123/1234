package com.ruoyi.web.controller.jxzxkhgl;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhzbx;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhzbxService;
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
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhfa;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhfaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 见习之星考核方案Controller
 *
 * @author ruoyi
 * @date 2020-08-29
 */
@RestController
@RequestMapping("/jxzxkhgl/jxzxkhfa")
public class TsbzJxzxkhfaController extends BaseController {
    @Autowired
    private ITsbzJxzxkhfaService tsbzJxzxkhfaService;
    @Autowired
    private ITsbzJxzxkhzbxService tsbzJxzxkhzbxService;

    /**
     * 查询见习之星考核方案列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJxzxkhfa tsbzJxzxkhfa) {
        startPage();
        List<TsbzJxzxkhfa> list = tsbzJxzxkhfaService.selectTsbzJxzxkhfaList(tsbzJxzxkhfa);
        return getDataTable(list);
    }

    /**
     * 导出见习之星考核方案列表
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:export')")
    @Log(title = "见习之星考核方案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJxzxkhfa tsbzJxzxkhfa) {
        List<TsbzJxzxkhfa> list = tsbzJxzxkhfaService.selectTsbzJxzxkhfaList(tsbzJxzxkhfa);
        ExcelUtil<TsbzJxzxkhfa> util = new ExcelUtil<TsbzJxzxkhfa>(TsbzJxzxkhfa.class);
        return util.exportExcel(list, "jxzxkhfa");
    }

    /**
     * 获取见习之星考核方案详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJxzxkhfaService.selectTsbzJxzxkhfaById(id));
    }

    /**
     * 新增见习之星考核方案
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:add')")
    @Log(title = "见习之星考核方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJxzxkhfa tsbzJxzxkhfa) {
        //新增的时候 判断是否创建了当前年份方案
        TsbzJxzxkhfa tsbzJxzxkhfaNew = new TsbzJxzxkhfa();
        tsbzJxzxkhfaNew.setKhnf(tsbzJxzxkhfa.getKhnf());
        List<TsbzJxzxkhfa> list = tsbzJxzxkhfaService.selectTsbzJxzxkhfaList(tsbzJxzxkhfaNew);
        if (list != null && list.size() > 0) {
            return AjaxResult.error("当前年份的考核方案已创建，不可重复创建");
        }

        tsbzJxzxkhfa.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(tsbzJxzxkhfaService.insertTsbzJxzxkhfa(tsbzJxzxkhfa));
    }

    /**
     * 修改见习之星考核方案
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:edit')")
    @Log(title = "见习之星考核方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJxzxkhfa tsbzJxzxkhfa) {
        return toAjax(tsbzJxzxkhfaService.updateTsbzJxzxkhfa(tsbzJxzxkhfa));
    }

    /**
     * 删除见习之星考核方案
     */
    @PreAuthorize("@ss.hasPermi('jxzxkhgl:jxzxkhfa:remove')")
    @Log(title = "见习之星考核方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //判断方案下是否存在指标项
        TsbzJxzxkhzbx tsbzJxzxkhzbx = null;
        for (int i = 0; i < ids.length; i++) {
            tsbzJxzxkhzbx = new TsbzJxzxkhzbx();
            tsbzJxzxkhzbx.setFaid(ids[i]);

            List<TsbzJxzxkhzbx> list = tsbzJxzxkhzbxService.selectTsbzJxzxkhzbxList(tsbzJxzxkhzbx);
            if (list != null && list.size() > 0) {
                return AjaxResult.error("当前见习之星考核方案下存在指标项，不可删除");
            }
        }
        return toAjax(tsbzJxzxkhfaService.deleteTsbzJxzxkhfaByIds(ids));
    }
}
