package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByDayFlowStandard;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentitem;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentplan;
import com.ruoyi.project.benyi.service.IByDayFlowStandardService;
import com.ruoyi.project.benyi.service.IByDayflowassessmentitemService;
import com.ruoyi.project.benyi.service.IByDayflowassessmentplanService;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.ByClass;
import com.ruoyi.project.system.service.IByClassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.benyi.domain.ByDayflowassessment;
import com.ruoyi.project.benyi.service.IByDayflowassessmentService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿园一日流程评估Controller
 *
 * @author tsbz
 * @date 2021-02-05
 */
@RestController
@RequestMapping("/benyi/dayflowassessment")
public class ByDayflowassessmentController extends BaseController {
    @Autowired
    private IByDayflowassessmentService byDayflowassessmentService;
    @Autowired
    private IByClassService byClassService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private IByDayflowassessmentitemService byDayflowassessmentitemService;

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayflowassessment byDayflowassessment) {
//        byDayflowassessment.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        return getDataTable(list);
    }

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/listpjf")
    public TableDataInfo listpjf(ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        startPage();
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentPjf(byDayflowassessment);
        return getDataTable(list);
    }

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/listteacheravg")
    public TableDataInfo listteacheravg(ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        startPage();
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentTeacherPjf(byDayflowassessment);
        return getDataTable(list);
    }

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/listmyself")
    public TableDataInfo listmyself(ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setPgdx(SecurityUtils.getLoginUser().getUser().getUserId());
        startPage();
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        return getDataTable(list);
    }

    /**
     * 导出幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:export')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByDayflowassessment byDayflowassessment) {
        List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment);
        ExcelUtil<ByDayflowassessment> util = new ExcelUtil<ByDayflowassessment>(ByDayflowassessment.class);
        return util.exportExcel(list, "dayflowassessment");
    }

    /**
     * 获取幼儿园一日流程评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byDayflowassessmentService.selectByDayflowassessmentById(id));
    }

    /**
     * 新增幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:add')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByDayflowassessment byDayflowassessment) {
        //判断当前评估对象的角色是主班 配班 还是助理教师
        Long pgdx = byDayflowassessment.getPgdx();
        //获取班级信息
        String classId = byDayflowassessment.getClassid();
        ByClass byClass = byClassService.selectByClassById(classId);
        if (byClass != null) {
            byDayflowassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
            byDayflowassessment.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
            byDayflowassessment.setXnxq(schoolCommon.getCurrentXnXq());
            //获取总得分
            byDayflowassessment.setZzdf(GetDf(byDayflowassessment.getList()));
            //如果评估对象非主班教师，那么对主班教师产生相同的扣分项
            if (byClass.getZbjs() == pgdx) {
                int iRows = addDayFlowAssessment(byDayflowassessment);
                return toAjax(iRows);
            } else {
                //评估对象为助理教师和配班教师
                int iRows = addDayFlowAssessment(byDayflowassessment);
                ByDayflowassessment byDayflowassessmentNew = byDayflowassessment;
                if (byClass.getZbjs() == null) {
                    System.out.println("未设置主班教师");
                } else {
                    byDayflowassessmentNew.setPgdx(byClass.getZbjs());//设置评估对象为主班教师
                    byDayflowassessmentNew.setPgdxxm(byClass.getZbjsxm());
                    byDayflowassessmentNew.setRemark("被评估："+byDayflowassessment.getId());

                    iRows = iRows + addDayFlowAssessment(byDayflowassessmentNew);
                }
                return toAjax(iRows);
            }
        } else {
            return AjaxResult.error("班级信息错误");
        }
    }

    //
    public Integer addDayFlowAssessment(ByDayflowassessment byDayflowassessment) {
        int iRows = byDayflowassessmentService.insertByDayflowassessment(byDayflowassessment);

        List<ByDayFlowStandard> list = byDayflowassessment.getList();

        if (list != null && list.size() > 0) {
            //将传进来的评分值存入item表
            ByDayflowassessmentitem byDayflowassessmentitem = null;
            for (int i = 0; i < list.size(); i++) {
                byDayflowassessmentitem = new ByDayflowassessmentitem();
                String mrz = list.get(i).getMrz();
                if (mrz != "0" && !schoolCommon.isStringEmpty(mrz)) {
                    Double dMrz = Double.valueOf(mrz);
                    if (dMrz != 0) {
                        byDayflowassessmentitem.setPid(byDayflowassessment.getId());
                        byDayflowassessmentitem.setItem(list.get(i).getId());
                        byDayflowassessmentitem.setValue(dMrz);
                        iRows = iRows + byDayflowassessmentitemService.insertByDayflowassessmentitem(byDayflowassessmentitem);
                    }
                }
            }
        }
        return iRows;
    }

    public Double GetDf(List<ByDayFlowStandard> list) {
        Double df = (double) 0;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                String mrz = list.get(i).getMrz();
                if (mrz != "0" && !schoolCommon.isStringEmpty(mrz)) {
                    Double dMrz = Double.valueOf(mrz);
                    if (dMrz != 0) {
                        df = df + dMrz;
                    }
                }
            }
        }
        return df;
    }

    /**
     * 修改幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:edit')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayflowassessment byDayflowassessment) {
        AjaxResult ajax = AjaxResult.success();
        return ajax;
    }

    /**
     * 删除幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:remove')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //先删除item数据
        for (int i = 0; i < ids.length; i++) {
            byDayflowassessmentitemService.deleteByDayflowassessmentitemByPid(ids[i]);
        }
        return toAjax(byDayflowassessmentService.deleteByDayflowassessmentByIds(ids));
    }
}
