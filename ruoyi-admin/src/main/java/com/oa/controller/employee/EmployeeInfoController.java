package com.oa.controller.employee;

import com.oa.system.employee.domain.EmployeeInfo;
import com.oa.system.employee.service.IEmployeeInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
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
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 员工信息Controller
 * 
 * @author ruoyi
 * @date 2022-03-17
 */
@RestController
@RequestMapping("/oa/employee")
public class EmployeeInfoController extends BaseController
{
    private final IEmployeeInfoService employeeInfoService;

    @Autowired
    public EmployeeInfoController(IEmployeeInfoService employeeInfoService) {
        this.employeeInfoService = employeeInfoService;
    }

    /**
     * 查询员工信息列表
     */
    @PreAuthorize("@ss.hasPermi('oa:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(EmployeeInfo employeeInfo)
    {
        startPage();
        List<EmployeeInfo> list = employeeInfoService.selectEmployeeInfoList(employeeInfo);
        return getDataTable(list);
    }

    /**
     * 导出员工信息列表
     */
    @PreAuthorize("@ss.hasPermi('oa:employee:export')")
    @Log(title = "员工信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EmployeeInfo employeeInfo)
    {
        List<EmployeeInfo> list = employeeInfoService.selectEmployeeInfoList(employeeInfo);
        ExcelUtil<EmployeeInfo> util = new ExcelUtil<>(EmployeeInfo.class);
        util.exportExcel(response, list, "员工信息数据");
    }

    /**
     * 获取员工信息详细信息
     */
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(employeeInfoService.selectEmployeeInfoByUserId(userId));
    }

    /**
     * 新增员工信息
     */
    @PreAuthorize("@ss.hasPermi('oa:employee:add')")
    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EmployeeInfo employeeInfo)
    {
        return toAjax(employeeInfoService.insertEmployeeInfo(employeeInfo));
    }

    /**
     * 修改员工信息
     */
    @PreAuthorize("@ss.hasPermi('oa:employee:edit')")
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EmployeeInfo employeeInfo)
    {
        return toAjax(employeeInfoService.updateEmployeeInfo(employeeInfo));
    }

    /**
     * 删除员工信息
     */
    @PreAuthorize("@ss.hasPermi('oa:employee:remove')")
    @Log(title = "员工信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(employeeInfoService.deleteEmployeeInfoByUserIds(userIds));
    }
}
