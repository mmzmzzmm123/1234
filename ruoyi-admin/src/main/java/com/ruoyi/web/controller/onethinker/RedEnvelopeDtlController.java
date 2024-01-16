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

import com.onethinker.onethinker.domain.RedEnvelopeDtl;
import com.onethinker.onethinker.service.IRedEnvelopeDtlService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 红包明细Controller
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@RestController
@RequestMapping("/onethinker/dtl")
public class RedEnvelopeDtlController extends BaseController {
    @Autowired
    private IRedEnvelopeDtlService redEnvelopeDtlService;

    /**
     * 查询红包明细列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:list')")
    @GetMapping("/list")
    public TableDataInfo list(RedEnvelopeDtl redEnvelopeDtl) {
        startPage();
        List<RedEnvelopeDtl> list = redEnvelopeDtlService.selectRedEnvelopeDtlList(redEnvelopeDtl);
        return getDataTable(list);
    }

    /**
     * 导出红包明细列表
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:export')")
    @Log(title = "红包明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RedEnvelopeDtl redEnvelopeDtl) {
        List<RedEnvelopeDtl> list = redEnvelopeDtlService.selectRedEnvelopeDtlList(redEnvelopeDtl);
        ExcelUtil<RedEnvelopeDtl> util = new ExcelUtil<RedEnvelopeDtl>(RedEnvelopeDtl. class);
        util.exportExcel(response, list, "红包明细数据");
    }

    /**
     * 获取红包明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(redEnvelopeDtlService.selectRedEnvelopeDtlById(id));
    }

    /**
     * 删除红包明细
     */
    @PreAuthorize("@ss.hasPermi('onethinker:dtl:remove')")
    @Log(title = "红包明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(redEnvelopeDtlService.deleteRedEnvelopeDtlByIds(ids));
    }
}
