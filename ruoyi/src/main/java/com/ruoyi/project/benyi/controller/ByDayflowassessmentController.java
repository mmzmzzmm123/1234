package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.benyi.domain.ByDayflowassessmentplan;
import com.ruoyi.project.benyi.service.IByDayFlowStandardService;
import com.ruoyi.project.benyi.service.IByDayflowassessmentplanService;
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
    private IByDayFlowStandardService byDayFlowStandardService;
    @Autowired
    private IByDayflowassessmentplanService byDayflowassessmentplanService;

    /**
     * 查询幼儿园一日流程评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByDayflowassessment byDayflowassessment) {
        byDayflowassessment.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
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
        byDayflowassessment.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        byDayflowassessment.setCreateUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(byDayflowassessmentService.insertByDayflowassessment(byDayflowassessment));
    }

    /**
     * 修改幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:edit')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByDayflowassessment byDayflowassessment) {
        AjaxResult ajax = AjaxResult.success();
        if (byDayflowassessment.getItems() != null) {
            // 取出标准id并根据id查询此标准的分数
            String[] arr = byDayflowassessment.getItems().split(",");
            // 勾选的总分数sum
            Double sum = 0.0;
            for (int i = 0; i < arr.length; i++) {
                sum += byDayFlowStandardService.selectByDayFlowStandardById(Long.parseLong(arr[i])).getScore();
            }
            // 标准满分
            Double bzmfAll = byDayflowassessment.getBzmf();
            // 总扣分值
            Double sumKfz = 0.0;
            sumKfz = bzmfAll - sum;
            ByDayflowassessmentplan byDayflowassessmentplan = new ByDayflowassessmentplan();
            // 如果评估对象是主班教师 直接更新主板分数
            if (byDayflowassessment.getPgdx().equals(byDayflowassessment.getBzbh())) {
                // 主班教师扣分值
                Double zbjsKfz = byDayflowassessment.getKfz() + sumKfz;
                byDayflowassessment.setKfz(zbjsKfz);
                // 判断扣分值是否超过满分
                if (zbjsKfz.compareTo(bzmfAll) < 0) {
                    byDayflowassessment.setZzdf(bzmfAll - zbjsKfz);
                } else {
                    byDayflowassessment.setZzdf(0.0);
                }
                byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment);
                // 设置班级平均分
                ByDayflowassessment byDayflowassessment2 = new ByDayflowassessment();
                byDayflowassessment2.setClassid(byDayflowassessment.getClassid());
                byDayflowassessment2.setPlanid(byDayflowassessment.getPlanid());
                List<ByDayflowassessment> list2 = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment2);
                if (list2 != null && list2.size() > 0) {
                    Double total = 0.0;
                    Double avg = 0.0;
                    for (int i = 0; i < list2.size(); i++) {
                        total += list2.get(i).getZzdf();
                    }
                    avg = total / list2.size();

                    for (int j = 0; j < list2.size(); j++) {
                        byDayflowassessment2.setId(list2.get(j).getId());
                        byDayflowassessment2.setClassdf(avg);
                        byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment2);
                    }
                    // 将计划中的平均分同步更新
                    byDayflowassessmentplan.setId(byDayflowassessment.getPlanid());
                    byDayflowassessmentplan.setClassavg(avg);
                    byDayflowassessmentplanService.updateByDayflowassessmentplan(byDayflowassessmentplan);
                }
                return ajax;
            } else {
                //如果是配班或者助理 先更新配班或者助理分数  再更新主班教师分数
                byDayflowassessment.setKfz(sumKfz);
                byDayflowassessment.setZzdf(sum);
                byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment);
                // 继续扣除主班教师分数
                ByDayflowassessment byDayflowassessment1 = new ByDayflowassessment();
                // 其他老师扣分项
                byDayflowassessment1.setPgdx(byDayflowassessment.getBzbh());
                byDayflowassessment1.setPlanid(byDayflowassessment.getPlanid());
                List<ByDayflowassessment> list = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment1);
                if (list != null && list.size() > 0) {
                    // 主班教师当前扣分值
                    Double zbjsDqkfz = list.get(0).getKfz() + sumKfz;
                    byDayflowassessment1.setKfz(zbjsDqkfz);
                    // 主班教师当前最终得分
                    byDayflowassessment1.setId(list.get(0).getId());
                    // 如果扣分值超出满分 则主班分数等于0
                    if (zbjsDqkfz.compareTo(bzmfAll) < 0) {
                        byDayflowassessment1.setZzdf(bzmfAll - zbjsDqkfz);
                    } else {
                        byDayflowassessment1.setZzdf(0.0);
                    }
                    byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment1);
                    // 设置班级平均分
                    ByDayflowassessment byDayflowassessment3 = new ByDayflowassessment();
                    byDayflowassessment3.setClassid(byDayflowassessment.getClassid());
                    byDayflowassessment3.setPlanid(byDayflowassessment.getPlanid());
                    List<ByDayflowassessment> list3 = byDayflowassessmentService.selectByDayflowassessmentList(byDayflowassessment3);
                    if (list3 != null && list3.size() > 0) {
                        Double total2 = 0.0;
                        Double avg2 = 0.0;
                        for (int i = 0; i < list3.size(); i++) {
                            total2 += list3.get(i).getZzdf();
                        }
                        avg2 = total2 / list3.size();
                        for (int j = 0; j < list3.size(); j++) {
                            byDayflowassessment3.setId(list3.get(j).getId());
                            byDayflowassessment3.setClassdf(avg2);
                            byDayflowassessmentService.updateByDayflowassessment(byDayflowassessment3);
                        }
                        // 将计划中的平均分同步更新
                        byDayflowassessmentplan.setId(byDayflowassessment.getPlanid());
                        byDayflowassessmentplan.setClassavg(avg2);
                        byDayflowassessmentplanService.updateByDayflowassessmentplan(byDayflowassessmentplan);
                    }
                }
                return ajax;
            }
        } else {
            return AjaxResult.error("未勾选评估选项");
        }
    }

    /**
     * 删除幼儿园一日流程评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:dayflowassessment:remove')")
    @Log(title = "幼儿园一日流程评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byDayflowassessmentService.deleteByDayflowassessmentByIds(ids));
    }
}
