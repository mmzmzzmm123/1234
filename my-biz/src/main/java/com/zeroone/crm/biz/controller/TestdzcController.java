package com.zeroone.crm.biz.controller;

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
import com.zeroone.crm.biz.domain.Testdzc;
import com.zeroone.crm.biz.service.ITestdzcService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 测试使用Controller
 * 
 * @author ruoyi
 * @date 2022-08-01
 */
@RestController
@RequestMapping("/mybiz/testdzc")
public class TestdzcController extends BaseController
{
    @Autowired
    private ITestdzcService testdzcService;

    /**
     * 查询测试使用列表
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:list')")
    @GetMapping("/list")
    public TableDataInfo list(Testdzc testdzc)
    {
        startPage();
        List<Testdzc> list = testdzcService.selectTestdzcList(testdzc);
        return getDataTable(list);
    }

    /**
     * 导出测试使用列表
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:export')")
    @Log(title = "测试使用", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Testdzc testdzc)
    {
        List<Testdzc> list = testdzcService.selectTestdzcList(testdzc);
        ExcelUtil<Testdzc> util = new ExcelUtil<Testdzc>(Testdzc.class);
        util.exportExcel(response, list, "测试使用数据");
    }

    /**
     * 获取测试使用详细信息
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(testdzcService.selectTestdzcById(id));
    }

    /**
     * 新增测试使用
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:add')")
    @Log(title = "测试使用", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Testdzc testdzc)
    {
        return toAjax(testdzcService.insertTestdzc(testdzc));
    }

    /**
     * 修改测试使用
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:edit')")
    @Log(title = "测试使用", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Testdzc testdzc)
    {
        return toAjax(testdzcService.updateTestdzc(testdzc));
    }

    /**
     * 删除测试使用
     */
    @PreAuthorize("@ss.hasPermi('mybiz:testdzc:remove')")
    @Log(title = "测试使用", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(testdzcService.deleteTestdzcByIds(ids));
    }
}
