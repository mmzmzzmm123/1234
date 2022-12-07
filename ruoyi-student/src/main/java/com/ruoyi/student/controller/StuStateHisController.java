package com.ruoyi.student.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.student.domain.StuInfo;
import com.ruoyi.student.service.IStuInfoService;
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
import com.ruoyi.student.domain.StuStateHis;
import com.ruoyi.student.service.IStuStateHisService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生历史状态Controller
 * 
 * @author wangzq
 * @date 2022-12-05
 */
@RestController
@RequestMapping("/student/his")
public class StuStateHisController extends BaseController
{
    @Autowired
    private IStuStateHisService stuStateHisService;

    @Autowired
    private IStuInfoService<StuStateHis> stuInfoService;

    /**
     * 查询学生历史状态列表
     */
    @PreAuthorize("@ss.hasPermi('student:his:list')")
    @GetMapping("/list")
    public TableDataInfo list(StuStateHis stuStateHis)
    {
        startPage();
        List<StuStateHis> list = stuStateHisService.selectStuStateHisList(stuStateHis);
        return getDataTable(list);
    }

    /**
     * 导出学生历史状态列表
     */
    @PreAuthorize("@ss.hasPermi('student:his:export')")
    @Log(title = "学生历史状态", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StuStateHis stuStateHis)
    {
        List<StuStateHis> list = stuStateHisService.selectStuStateHisList(stuStateHis);
        stuInfoService.matchDict(list);
        for (StuStateHis stuInfo : list) {
            String hisStateName = DictUtils.getDictLabel("student_tag", stuInfo.getHisState());
            stuInfo.setHisStateName(hisStateName);
        }
        ExcelUtil<StuStateHis> util = new ExcelUtil<StuStateHis>(StuStateHis.class);
        util.exportExcel(response, list, "学生历史状态数据");
    }

    /**
     * 获取学生历史状态详细信息
     */
    @PreAuthorize("@ss.hasPermi('student:his:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(stuStateHisService.selectStuStateHisById(id));
    }

    /**
     * 新增学生历史状态
     */
    @PreAuthorize("@ss.hasPermi('student:his:add')")
    @Log(title = "学生历史状态", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StuStateHis stuStateHis)
    {
        return toAjax(stuStateHisService.insertStuStateHis(stuStateHis));
    }

    /**
     * 修改学生历史状态
     */
    @PreAuthorize("@ss.hasPermi('student:his:edit')")
    @Log(title = "学生历史状态", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StuStateHis stuStateHis)
    {
        return toAjax(stuStateHisService.updateStuStateHis(stuStateHis));
    }

    /**
     * 删除学生历史状态
     */
    @PreAuthorize("@ss.hasPermi('student:his:remove')")
    @Log(title = "学生历史状态", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(stuStateHisService.deleteStuStateHisByIds(ids));
    }
}
