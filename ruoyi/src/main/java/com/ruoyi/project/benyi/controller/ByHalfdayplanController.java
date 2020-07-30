package com.ruoyi.project.benyi.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.project.benyi.domain.ByHalfdayplan;
import com.ruoyi.project.benyi.service.IByHalfdayplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 入园半日体验计划Controller
 *
 * @author tsbz
 * @date 2020-07-29
 */
@RestController
@RequestMapping("/benyi/halfdayplan")
public class ByHalfdayplanController extends BaseController {
    @Autowired
    private IByHalfdayplanService byHalfdayplanService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询入园半日体验计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByHalfdayplan byHalfdayplan) {
        byHalfdayplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        startPage();
        List<ByHalfdayplan> list = byHalfdayplanService.selectByHalfdayplanList(byHalfdayplan);
        return getDataTable(list);
    }

    /**
     * 导出入园半日体验计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:export')")
    @Log(title = "入园半日体验计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByHalfdayplan byHalfdayplan) {
        List<ByHalfdayplan> list = byHalfdayplanService.selectByHalfdayplanList(byHalfdayplan);
        ExcelUtil<ByHalfdayplan> util = new ExcelUtil<ByHalfdayplan>(ByHalfdayplan.class);
        return util.exportExcel(list, "halfdayplan");
    }

    /**
     * 获取入园半日体验计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(byHalfdayplanService.selectByHalfdayplanById(id));
    }

    //@PreAuthorize("@ss.hasPermi('benyi:halfdayplan:query')")
    @GetMapping(value = "/getInfo/{id}")
    public AjaxResult getTyInfo(@PathVariable("id") String id) {
        return AjaxResult.success(byHalfdayplanService.selectByHalfdayplanById(id));
    }

    /**
     * 新增入园半日体验计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:add')")
    @Log(title = "入园半日体验计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByHalfdayplan byHalfdayplan) {
        byHalfdayplan.setId(schoolCommon.getUuid());
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool()) {
            byHalfdayplan.setType("2");
            byHalfdayplan.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        } else {
            byHalfdayplan.setType("1");
        }
        return toAjax(byHalfdayplanService.insertByHalfdayplan(byHalfdayplan));
    }

    /**
     * 修改入园半日体验计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:edit')")
    @Log(title = "入园半日体验计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByHalfdayplan byHalfdayplan) {
        return toAjax(byHalfdayplanService.updateByHalfdayplan(byHalfdayplan));
    }

    /**
     * 复制入园通知书
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:edit')")
    @Log(title = "入园半日体验计划", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{id}")
    public AjaxResult copy(@PathVariable String id) {
        System.out.println("id===" + id);
        ByHalfdayplan byHalfdayplan = byHalfdayplanService.selectByHalfdayplanById(id);
        ByHalfdayplan byHalfdayplanCopy = new ByHalfdayplan();
        byHalfdayplanCopy.setId(schoolCommon.getUuid());
        byHalfdayplanCopy.setType("2");
        byHalfdayplanCopy.setSchoolid(SecurityUtils.getLoginUser().getUser().getDept().getDeptId());
        byHalfdayplanCopy.setContent(byHalfdayplan.getContent());
        byHalfdayplanCopy.setTitle(byHalfdayplan.getTitle());

        return toAjax(byHalfdayplanService.insertByHalfdayplan(byHalfdayplanCopy));
    }

    /**
     * 删除入园半日体验计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:halfdayplan:remove')")
    @Log(title = "入园半日体验计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        //判断id是否为系统定义
        for (int i = 0; i < ids.length; i++) {
            String id = ids[i];
            ByHalfdayplan byHalfdayplan = byHalfdayplanService.selectByHalfdayplanById(id);
            if (byHalfdayplan.getType() == "1") {
                return AjaxResult.error("包含系统半日体验计划，无法删除");
            }
        }
        return toAjax(byHalfdayplanService.deleteByHalfdayplanByIds(ids));
    }
}
