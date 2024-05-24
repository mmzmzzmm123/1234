package com.jjpt.business.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jjpt.business.domain.dto.UserExamRespDTO;
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
import com.jjpt.business.domain.ElUserExam;
import com.jjpt.business.service.IElUserExamService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 考试记录Controller
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
@RestController
@RequestMapping("/exammanage/userexam")
public class ElUserExamController extends BaseController
{
    @Autowired
    private IElUserExamService elUserExamService;

    /**
     * 查询考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserExamRespDTO elUserExam)
    {
        startPage();
        List<UserExamRespDTO> list = elUserExamService.selectElUserExamList(elUserExam);
        return getDataTable(list);
    }




    /**
     * 导出考试记录列表
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:export')")
    @Log(title = "考试记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserExamRespDTO elUserExam)
    {
        List<UserExamRespDTO> list = elUserExamService.selectElUserExamList(elUserExam);
        ExcelUtil<UserExamRespDTO> util = new ExcelUtil<>(UserExamRespDTO.class);
        util.exportExcel(response, list, "考试记录数据");
    }

    /**
     * 获取考试记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(elUserExamService.selectElUserExamById(id));
    }

    /**
     * 新增考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:add')")
    @Log(title = "考试记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ElUserExam elUserExam)
    {
        return toAjax(elUserExamService.insertElUserExam(elUserExam));
    }

    /**
     * 修改考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:edit')")
    @Log(title = "考试记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ElUserExam elUserExam)
    {
        return toAjax(elUserExamService.updateElUserExam(elUserExam));
    }

    /**
     * 删除考试记录
     */
    @PreAuthorize("@ss.hasPermi('exammanage:userexam:remove')")
    @Log(title = "考试记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(elUserExamService.deleteElUserExamByIds(ids));
    }
}
