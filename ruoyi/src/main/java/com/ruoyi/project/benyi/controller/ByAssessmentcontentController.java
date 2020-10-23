package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
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
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByAssessmentcontent;
import com.ruoyi.project.benyi.service.IByAssessmentcontentService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 评估内容Controller
 *
 * @author tsbz
 * @date 2020-10-09
 */
@RestController
@RequestMapping("/benyi/assessmentcontent")
public class ByAssessmentcontentController extends BaseController
{
    @Autowired
    private IByAssessmentcontentService byAssessmentcontentService;
    @Autowired
    private TokenService tokenService;

/**
 * 查询评估内容列表
 */
@PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:list')")
@GetMapping("/list")
        public TableDataInfo list(ByAssessmentcontent byAssessmentcontent)
    {
        startPage();
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
        return getDataTable(list);
    }
    
    /**
     * 导出评估内容列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:export')")
    @Log(title = "评估内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentcontent byAssessmentcontent)
    {
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
        ExcelUtil<ByAssessmentcontent> util = new ExcelUtil<ByAssessmentcontent>(ByAssessmentcontent.class);
        return util.exportExcel(list, "assessmentcontent");
    }

    /**
     * 获取评估内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(byAssessmentcontentService.selectByAssessmentcontentById(id));
    }



    /**
     * 获取部门下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(ByAssessmentcontent byAssessmentcontent) {
        List<ByAssessmentcontent> byAssessmentcontents = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
        return AjaxResult.success(byAssessmentcontentService.buildByAssessmentcontentTreeSelect(byAssessmentcontents));
    }


    /**
     * 新增评估内容
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:add')")
    @Log(title = "评估内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAssessmentcontent byAssessmentcontent)
    {
        return toAjax(byAssessmentcontentService.insertByAssessmentcontent(byAssessmentcontent));
    }

    /**
     * 修改评估内容
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:edit')")
    @Log(title = "评估内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentcontent byAssessmentcontent)
    {
        return toAjax(byAssessmentcontentService.updateByAssessmentcontent(byAssessmentcontent));
    }

    /**
     * 删除评估内容
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:remove')")
    @Log(title = "评估内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        if (byAssessmentcontentService.hasChildByAssessmentcontentId(id)) {
            return AjaxResult.error("存在下级内容,不允许删除");
        }
        return toAjax(byAssessmentcontentService.deleteByAssessmentcontentById(id));
    }
}
