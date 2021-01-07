package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.project.common.SchoolCommon;
import com.ruoyi.project.system.domain.SysDictData;
import com.ruoyi.project.system.service.ISysDictDataService;
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
import com.ruoyi.project.benyi.domain.ByAssessmentchild;
import com.ruoyi.project.benyi.service.IByAssessmentchildService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 幼儿评估Controller
 *
 * @author tsbz
 * @date 2020-10-27
 */
@RestController
@RequestMapping("/benyi/assessmentchild")
public class ByAssessmentchildController extends BaseController {
    @Autowired
    private IByAssessmentchildService byAssessmentchildService;
    @Autowired
    private SchoolCommon schoolCommon;
    @Autowired
    private ISysDictDataService dictDataService;

    /**
     * 查询幼儿评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByAssessmentchild byAssessmentchild) {
        startPage();
        List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchild);
        return getDataTable(list);
    }

    @GetMapping(value = "/getdicdata/{id}")
    public AjaxResult getListDicData(@PathVariable("id") Long id) {
        AjaxResult ajaxResult = AjaxResult.success();
        List<SysDictData> list = dictDataService.selectDictDataByChildId(id);
        ajaxResult.put("dictdata", list);
        return ajaxResult;
    }

    /**
     * 导出幼儿评估列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:export')")
    @Log(title = "幼儿评估", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByAssessmentchild byAssessmentchild) {
        List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchild);
        ExcelUtil<ByAssessmentchild> util = new ExcelUtil<ByAssessmentchild>(ByAssessmentchild.class);
        return util.exportExcel(list, "assessmentchild");
    }

    /**
     * 获取幼儿评估详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byAssessmentchildService.selectByAssessmentchildById(id));
    }

    /**
     * 新增幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:add')")
    @Log(title = "幼儿评估", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByAssessmentchild byAssessmentchild) {
        int iCount = 0;
        byAssessmentchild.setUserid(SecurityUtils.getLoginUser().getUser().getUserId());
        String strItems = byAssessmentchild.getItems();
        //System.out.println("执行嘛？"+strItems);
        //判断选中的checkbox是否为空
        if (schoolCommon.isStringEmpty(strItems)) {
            return AjaxResult.error("请至少选择一个评估项");
        } else {
            String[] strArr = strItems.split(",");
            for (int i = 0; i < strArr.length; i++) {
                //System.out.println("contentid:"+strArr[i]);
                byAssessmentchild.setContentid(Long.valueOf(strArr[i]));
                //先判断该项指标是否已添加，添加后不可继续维护
                ByAssessmentchild byAssessmentchildNew = new ByAssessmentchild();
                byAssessmentchildNew.setContentid(Long.valueOf(strArr[i]));
                byAssessmentchildNew.setChildid(byAssessmentchild.getChildid());
                List<ByAssessmentchild> list = byAssessmentchildService.selectByAssessmentchildList(byAssessmentchildNew);
                if (list != null && list.size() > 0) {
                } else {
                    iCount = byAssessmentchildService.insertByAssessmentchild(byAssessmentchild);
                }
            }
        }
        if (iCount > 0) {
            return toAjax(iCount);
        } else {
            return AjaxResult.error("选择的评估数据无变化");
        }

    }

    /**
     * 修改幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:edit')")
    @Log(title = "幼儿评估", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByAssessmentchild byAssessmentchild) {
        return toAjax(byAssessmentchildService.updateByAssessmentchild(byAssessmentchild));
    }

    /**
     * 删除幼儿评估
     */
    @PreAuthorize("@ss.hasPermi('benyi:assessmentchild:remove')")
    @Log(title = "幼儿评估", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byAssessmentchildService.deleteByAssessmentchildByIds(ids));
    }
}
