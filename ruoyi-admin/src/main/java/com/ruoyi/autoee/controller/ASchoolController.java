package com.ruoyi.autoee.controller;

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
import com.ruoyi.autoee.domain.ASchool;
import com.ruoyi.autoee.service.IASchoolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学校管理Controller
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
@RestController
@RequestMapping("/autoee/school")
public class ASchoolController extends BaseController
{
    @Autowired
    private IASchoolService aSchoolService;

    /**
     * 查询学校管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:list')")
    @GetMapping("/list")
    public TableDataInfo list(ASchool aSchool)
    {
        startPage();
        List<ASchool> list = aSchoolService.selectASchoolList(aSchool);
        return getDataTable(list);
    }

    /**
     * 导出学校管理列表
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:export')")
    @Log(title = "学校管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ASchool aSchool)
    {
        List<ASchool> list = aSchoolService.selectASchoolList(aSchool);
        ExcelUtil<ASchool> util = new ExcelUtil<ASchool>(ASchool.class);
        util.exportExcel(response, list, "学校管理数据");
    }

    /**
     * 获取学校管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(aSchoolService.selectASchoolById(id));
    }

    /**
     * 新增学校管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:add')")
    @Log(title = "学校管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ASchool aSchool)
    {
        return toAjax(aSchoolService.insertASchool(aSchool));
    }

    /**
     * 修改学校管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:edit')")
    @Log(title = "学校管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ASchool aSchool)
    {
        return toAjax(aSchoolService.updateASchool(aSchool));
    }

    /**
     * 删除学校管理
     */
    @PreAuthorize("@ss.hasPermi('autoee:school:remove')")
    @Log(title = "学校管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(aSchoolService.deleteASchoolByIds(ids));
    }
}
