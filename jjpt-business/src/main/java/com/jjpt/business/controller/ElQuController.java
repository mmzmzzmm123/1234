package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jjpt.business.domain.ElQu;
import com.jjpt.business.domain.dto.ElQuDto;
import com.jjpt.business.service.IElQuService;
import com.ruoyi.common.utils.poi.ExcelUtil;
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

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 试题管理Controller
 * 
 * @author 卢亚峰
 * @date 2024-05-17
 */
@RestController
@RequestMapping("/exammanage/qu")
public class ElQuController extends BaseController
{
    @Autowired
    private IElQuService elQuService;

    /**
     * 查询试题管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ElQu elQu)
    {
        startPage();
        List<ElQu> list = elQuService.selectElQuList(elQu);
        return getDataTable(list);
    }

    /**
     * 导出试题管理列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:export')")
    @Log(title = "试题管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ElQu elQu)
    {
        List<ElQu> list = elQuService.selectElQuList(elQu);
        ExcelUtil<ElQu> util = new ExcelUtil<ElQu>(ElQu.class);
        util.exportExcel(response, list, "试题管理数据");
    }

    /**
     * 获取试题管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elQuService.selectElQuById(id));
    }

    /**
     * 新增试题管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:add')")
    @Log(title = "试题管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElQuDto elQu)
    {
        return toAjax(elQuService.insertElQu(elQu));
    }

    /**
     * 修改试题管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:edit')")
    @Log(title = "试题管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElQuDto elQu)
    {
        return toAjax(elQuService.insertElQu(elQu));
    }

    /**
     * 删除试题管理
     */
    @PreAuthorize("@ss.hasPermi('exammanage:qu:remove')")
    @Log(title = "试题管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elQuService.deleteElQuByIds(ids));
    }
}
