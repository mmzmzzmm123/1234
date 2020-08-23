package com.ruoyi.web.controller.jxjs;

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
import com.ruoyi.jxjs.domain.TsbzJdcx;
import com.ruoyi.jxjs.service.ITsbzJdcxService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 基地区级审核Controller
 *
 * @author ruoyi
 * @date 2020-08-20
 */
@RestController
@RequestMapping("/jxjs/jdcx")
public class TsbzJdcxController extends BaseController {
    @Autowired
    private ITsbzJdcxService tsbzJdcxService;
    @Autowired
    private SchoolCommonController schoolCommonController;

    /**
     * 查询基地区级审核列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:list')")
    @GetMapping("/list")
    public TableDataInfo list(TsbzJdcx tsbzJdcx) {
        startPage();
        List<TsbzJdcx> list = tsbzJdcxService.selectTsbzJdcxList(tsbzJdcx);
        return getDataTable(list);
    }

    /**
     * 导出基地区级审核列表
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:export')")
    @Log(title = "基地区级审核", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TsbzJdcx tsbzJdcx) {
//        List<TsbzJdcx> list = tsbzJdcxService.selectTsbzJdcxList(tsbzJdcx);
        List<TsbzJdcx> list = tsbzJdcxService.selectTsbzJdcxExport(tsbzJdcx);
        ExcelUtil<TsbzJdcx> util = new ExcelUtil<TsbzJdcx>(TsbzJdcx.class);
        return util.exportExcel(list, "笔试名单");
    }

    /**
     * 获取基地区级审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tsbzJdcxService.selectTsbzJdcxById(id));
    }

    /**
     * 新增基地区级初级审核
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:add')")
    @Log(title = "基地区级审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TsbzJdcx tsbzJdcx) {
        int iCount = 0;
        //暂用当前状态 传 过来jsid数组
        String jsIds = tsbzJdcx.getDqzt();
        if (!schoolCommonController.isStringEmpty(jsIds)) {
            String[] arrJsId = jsIds.split(",");
            for (int i = 0; i < arrJsId.length; i++) {
                tsbzJdcx.setJsid(Long.valueOf(arrJsId[i]));
                tsbzJdcx.setDqzt("1");
                tsbzJdcx.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());
                iCount = iCount + tsbzJdcxService.insertTsbzJdcx(tsbzJdcx);
            }
        } else {
            return AjaxResult.error("请选择教师");
        }
        return toAjax(iCount);
    }

    /**
     * 修改基地区级审核
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:edit')")
    @Log(title = "基地区级审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TsbzJdcx tsbzJdcx) {
        return toAjax(tsbzJdcxService.updateTsbzJdcx(tsbzJdcx));
    }

    /**
     * 提交基地校初级审核
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:edit')")
    @Log(title = "提交基地校初级审核", businessType = BusinessType.UPDATE)
    @PostMapping("/check/{ids}")
    public AjaxResult check(@PathVariable Long[] ids) {
        int iCount = 0;
        TsbzJdcx tsbzJdcx = null;
        for (int i = 0; i < ids.length; i++) {
            tsbzJdcx = new TsbzJdcx();
            tsbzJdcx.setId(ids[i]);
            tsbzJdcx.setDqzt("2");
            tsbzJdcx.setJdxshzt("1");
            tsbzJdcx.setJdxshr(SecurityUtils.getLoginUser().getUser().getUserId());

            iCount = iCount + tsbzJdcxService.updateTsbzJdcx(tsbzJdcx);
        }
        return toAjax(iCount);
    }

    /**
     * 删除基地区级审核
     */
    @PreAuthorize("@ss.hasPermi('jxjs:jdcx:remove')")
    @Log(title = "基地区级审核", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tsbzJdcxService.deleteTsbzJdcxByIds(ids));
    }
}
