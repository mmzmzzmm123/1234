package com.ruoyi.web.controller.qtjs;

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
import com.ruoyi.qtjs.domain.TsbzQtjspxfazbxsj;
import com.ruoyi.qtjs.service.ITsbzQtjspxfazbxsjService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 群体教师评选指标数据Controller
 *
 * @author ruoyi
 * @date 2020-09-17
 */
@RestController
@RequestMapping("/qtjs/qtjspxfazbxsj")
public class TsbzQtjspxfazbxsjController extends BaseController {
    @Autowired
    private ITsbzQtjspxfazbxsjService tsbzQtjspxfazbxsjService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询群体教师评选指标数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj) {
        startPage();
        tsbzQtjspxfazbxsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        List<TsbzQtjspxfazbxsj> list = tsbzQtjspxfazbxsjService.selectTsbzQtjspxfazbxsjList(tsbzQtjspxfazbxsj);
        return getDataTable(list);
    }

    /**
     * 导出群体教师评选指标数据列表
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:export')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj) {
        List<TsbzQtjspxfazbxsj> list = tsbzQtjspxfazbxsjService.selectTsbzQtjspxfazbxsjList(tsbzQtjspxfazbxsj);
        ExcelUtil<TsbzQtjspxfazbxsj> util = new ExcelUtil<TsbzQtjspxfazbxsj>(TsbzQtjspxfazbxsj.class);
        return util.exportExcel(list, "qtjspxfazbxsj");
    }

    /**
     * 获取群体教师评选指标数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj=new TsbzQtjspxfazbxsj();
        tsbzQtjspxfazbxsj.setId(id);
        tsbzQtjspxfazbxsj.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return AjaxResult.success(tsbzQtjspxfazbxsjService.selectTsbzQtjspxfazbxsjById(tsbzQtjspxfazbxsj));
    }

    /**
     * 新增群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:edit')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj) {
        tsbzQtjspxfazbxsj.setJsid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        tsbzQtjspxfazbxsj.setCreateuserid(schoolCommonController.userIdToxxjsId(SecurityUtils.getLoginUser().getUser().getUserId()));
        return toAjax(tsbzQtjspxfazbxsjService.insertTsbzQtjspxfazbxsj(tsbzQtjspxfazbxsj));
    }

    /**
     * 修改群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:edit')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj) {
        return toAjax(tsbzQtjspxfazbxsjService.updateTsbzQtjspxfazbxsj(tsbzQtjspxfazbxsj));
    }

    /**
     * 删除群体教师评选指标数据
     */
    @PreAuthorize("@ss.hasPermi('qtjs:qtjspxfazbxsj:remove')")
    @Log(title = "群体教师评选指标数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzQtjspxfazbxsjService.deleteTsbzQtjspxfazbxsjByIds(ids));
    }
}
