package com.ruoyi.web.controller.onethinker;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.service.IRedEnvelopeCtrlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 红包批控制Controller
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/onethinker/ctrl")
public class RedEnvelopeCtrlController extends BaseController {
    @Autowired
    private IRedEnvelopeCtrlService redEnvelopeCtrlService;

    /**
     * 查询红包批控制列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedEnvelopeCtrl redEnvelopeCtrl) {
        startPage();
        List<RedEnvelopeCtrl> list = redEnvelopeCtrlService.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
        return getDataTable(list);
    }

    /**
     * 导出红包批控制列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:export')")
    @Log(title = "红包批控制", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedEnvelopeCtrl redEnvelopeCtrl) {
        List<RedEnvelopeCtrl> list = redEnvelopeCtrlService.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
        ExcelUtil<RedEnvelopeCtrl> util = new ExcelUtil<RedEnvelopeCtrl>(RedEnvelopeCtrl. class);
        util.exportExcel(response, list, "红包批控制数据");
    }

    /**
     * 获取红包批控制详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(redEnvelopeCtrlService.selectRedEnvelopeCtrlById(id));
    }

    /**
     * 新增红包批控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:add')")
    @Log(title = "红包批控制", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RedEnvelopeCtrl redEnvelopeCtrl) {
        return toAjax(redEnvelopeCtrlService.insertRedEnvelopeCtrl(redEnvelopeCtrl));
    }

    /**
     * 修改红包批控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:edit')")
    @Log(title = "红包批控制", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RedEnvelopeCtrl redEnvelopeCtrl) {
        return toAjax(redEnvelopeCtrlService.updateRedEnvelopeCtrl(redEnvelopeCtrl));
    }

    /**
     * 删除红包批控制
     */
    @PreAuthorize("@ss.hasPermi('onethinker:ctrl:remove')")
    @Log(title = "红包批控制", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(redEnvelopeCtrlService.deleteRedEnvelopeCtrlByIds(ids));
    }
}
