package com.ruoyi.project.benyi.controller;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.project.common.SchoolCommon;
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
public class ByAssessmentcontentController extends BaseController {
    @Autowired
    private IByAssessmentcontentService byAssessmentcontentService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询评估内容列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByAssessmentcontent byAssessmentcontent) {
        startPage();
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
        return getDataTable(list);
    }

    /**
     * 查询幼儿未评估内容列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:list')")
    @GetMapping("/noassessment/list/{child}")
    public TableDataInfo listno(@PathVariable("child") Long child) {
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectNoByAssessmentcontentListByChild(child);
        return getDataTable(list);
    }

    /**
     * 查询评估内容列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:list')")
    @Log(title = "评估学习", businessType = BusinessType.QUERY)
    @GetMapping("/treeselectstudy")
    public AjaxResult treeselectstudy(ByAssessmentcontent byAssessmentcontent) {
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentstudyList(byAssessmentcontent);
        return AjaxResult.success(byAssessmentcontentService.buildByAssessmentcontentTreeSelect(list));
    }

    /**
     * 查询评估内容列表 根据id
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:list')")
    @GetMapping("/childnote/{id}")
    public AjaxResult childnote(@PathVariable("id") Long id) {
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectChildNodeByParentid(id);
        return AjaxResult.success(list);
    }

    /**
     * 获取评估内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:query')")
    @GetMapping(value = "/byparentid/{id}")
    public AjaxResult getInfobyparentId(@PathVariable("id") Long id) {
        System.out.println("start:" + id);
        ByAssessmentcontent byAssessmentcontent = new ByAssessmentcontent();
        byAssessmentcontent.setParentId(id);
        return AjaxResult.success(byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent));
    }

//    /**
//     * 获取部门下拉树列表
//     */
//    @GetMapping("/treeselect")
//    public AjaxResult treeselect(ByDayFlowDetail byDayFlowDetail) {
//        List<ByDayFlowDetail> byDayFlowDetails = byDayFlowDetailService.selectByDayFlowDetailListTree(byDayFlowDetail);
//        return AjaxResult.success(byDayFlowDetailService.buildDayFlowDetailTreeSelect(byDayFlowDetails));
//    }

    /**
     * 导出评估内容列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:export')")
    @Log(title = "评估内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentcontent byAssessmentcontent) {
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
        ExcelUtil<ByAssessmentcontent> util = new ExcelUtil<ByAssessmentcontent>(ByAssessmentcontent.class);
        return util.exportExcel(list, "assessmentcontent");
    }

    /**
     * 获取评估内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
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
    public AjaxResult add(@RequestBody ByAssessmentcontent byAssessmentcontent) {
        return toAjax(byAssessmentcontentService.insertByAssessmentcontent(byAssessmentcontent));
    }

    /**
     * 修改评估内容
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:edit')")
    @Log(title = "评估内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentcontent byAssessmentcontent) {
        return toAjax(byAssessmentcontentService.updateByAssessmentcontent(byAssessmentcontent));
    }

    /**
     * 删除评估内容
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentcontent:remove')")
    @Log(title = "评估内容", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        if (byAssessmentcontentService.hasChildByAssessmentcontentId(id)) {
            return AjaxResult.error("存在下级内容,不允许删除");
        }
        return toAjax(byAssessmentcontentService.deleteByAssessmentcontentById(id));
    }

    @GetMapping(value = "/getassessmentstatistics/{childid}/{scope}")
    public AjaxResult getAssessmentStatistics(@PathVariable("childid") Long childid, @PathVariable("scope") String scope) {
        AjaxResult ajaxResult = AjaxResult.success();
        String[] strArr = new String[]{"健康", "语言", "社会", "科学", "艺术"};
        List<String> douArr = new ArrayList<String>();
        ByAssessmentcontent byAssessmentcontent = null;
        for (int i = 0; i < strArr.length; i++) {
            byAssessmentcontent = new ByAssessmentcontent();
            byAssessmentcontent.setScope(scope);
            byAssessmentcontent.setName(strArr[i]);
            //获取{"健康", "语言", "社会", "科学", "艺术"} 对应的值
            List<ByAssessmentcontent> list = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent);
            if (list != null && list.size() > 0) {
                Long id = list.get(0).getId();
                byAssessmentcontent.setId(id);
                byAssessmentcontent.setSort(childid);
                int count = byAssessmentcontentService.selectCountElement(byAssessmentcontent);
                int countChild = byAssessmentcontentService.selectCountElementByChild(byAssessmentcontent);
                if (scope.equals("1")) {
                    douArr.add((schoolCommon.format1((double) 48 * countChild / count)));
                } else if (scope.equals("2")) {
                    douArr.add((schoolCommon.format1(((double) 60 * countChild / count))));
                } else if (scope.equals("3")) {
                    douArr.add((schoolCommon.format1(((double) 72 * countChild / count))));
                }
            }
        }
        ajaxResult.put("statistics", douArr);
        return ajaxResult;
    }

    ///根据id获取领域目标
    @GetMapping(value = "/getassessmentstatisticsmb/{childid}/{scope}/{type}")
    public AjaxResult getAssessmentStatisticsmb(@PathVariable("childid") Long childid, @PathVariable("scope") String scope, @PathVariable("type") String type) {
        AjaxResult ajaxResult = AjaxResult.success();
        String[] strArr = new String[]{"健康", "语言", "社会", "科学", "艺术"};
        ByAssessmentcontent byAssessmentcontent = new ByAssessmentcontent();
        byAssessmentcontent.setName(strArr[Integer.parseInt(type)]);
        byAssessmentcontent.setScope(scope);

        long a = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent).get(0).getId();
        List<ByAssessmentcontent> list = byAssessmentcontentService.selectNodeByid(a);
        ajaxResult.put("mb", list);
        return ajaxResult;
    }

    ///统计结果
    @GetMapping(value = "/getassessmentstatisticsbymb/{childid}/{scope}/{type}")
    public AjaxResult getAssessmentStatisticsbymb(@PathVariable("childid") Long childid, @PathVariable("scope") String scope, @PathVariable("type") String type) {
        AjaxResult ajaxResult = AjaxResult.success();
        String[] strArr = new String[]{"健康", "语言", "社会", "科学", "艺术"};
        ByAssessmentcontent byAssessmentcontent0 = new ByAssessmentcontent();
        byAssessmentcontent0.setName(strArr[Integer.parseInt(type)]);
        byAssessmentcontent0.setScope(scope);
        long a = byAssessmentcontentService.selectByAssessmentcontentList(byAssessmentcontent0).get(0).getId();
        List<ByAssessmentcontent> list0 = byAssessmentcontentService.selectNodeByid(a);

        List<Double> douArr = new ArrayList<Double>();
        ByAssessmentcontent byAssessmentcontent = null;
        for (int i = 0; i < list0.size(); i++) {
            byAssessmentcontent = new ByAssessmentcontent();
            byAssessmentcontent.setScope(scope);
            byAssessmentcontent.setId(list0.get(i).getId());
            byAssessmentcontent.setSort(childid);
            int count = byAssessmentcontentService.selectCountElement(byAssessmentcontent);
            int countChild = byAssessmentcontentService.selectCountElementByChild(byAssessmentcontent);
            if (scope.equals("1")) {
                douArr.add((((double) 48 / count) * countChild));
                System.out.println("48:" + count + "-" + countChild);
            } else if (scope.equals("2")) {
                douArr.add((((double) 60 / count) * countChild));
                System.out.println("60:" + count + "-" + countChild);
            } else if (scope.equals("3")) {
                douArr.add((((double) 72 / count) * countChild));
                System.out.println("72:" + count + "-" + countChild);
            }
        }
        ajaxResult.put("statistics", douArr);
        return ajaxResult;
    }

}
