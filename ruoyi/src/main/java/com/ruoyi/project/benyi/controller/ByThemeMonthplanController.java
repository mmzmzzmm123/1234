package com.ruoyi.project.benyi.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ruoyi.project.benyi.domain.ByThemeMonthplan;
import com.ruoyi.project.benyi.service.IByThemeMonthplanService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.page.TableDataInfo;

/**
 * 主题整合月计划Controller
 *
 * @author tsbz
 * @date 2020-08-17
 */
@RestController
@RequestMapping("/benyi/thememonthplan")
public class ByThemeMonthplanController extends BaseController {
    @Autowired
    private IByThemeMonthplanService byThemeMonthplanService;
    @Autowired
    private SchoolCommon schoolCommon;

    /**
     * 查询主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ByThemeMonthplan byThemeMonthplan) {
        startPage();
        List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        return getDataTable(list);
    }

    /**
     * 导出主题整合月计划列表
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:export')")
    @Log(title = "主题整合月计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ByThemeMonthplan byThemeMonthplan) {
        List<ByThemeMonthplan> list = byThemeMonthplanService.selectByThemeMonthplanList(byThemeMonthplan);
        ExcelUtil<ByThemeMonthplan> util = new ExcelUtil<ByThemeMonthplan>(ByThemeMonthplan.class);
        return util.exportExcel(list, "thememonthplan");
    }

    /**
     * 获取主题整合月计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(byThemeMonthplanService.selectByThemeMonthplanById(id));
    }

    /**
     * 新增主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:add')")
    @Log(title = "主题整合月计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        //首先判断当前账户是否为幼儿园账号
        if (schoolCommon.isSchool()) {

            byThemeMonthplan.setCreateuserid(SecurityUtils.getLoginUser().getUser().getUserId());

            String dateTime = byThemeMonthplan.getMonth();
            dateTime = dateTime .replace("Z", " UTC"); //2019-06-27T16:00:00.000 UTC
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//转换时区格式
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
            Date date = null;//将Z时间格式转换成Date类型格式或换成毫秒
            try {
                date = format1.parse(dateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String time= format2.format(date);//2019-06
            System.out.println(time);
            byThemeMonthplan.setMonth(time);
            byThemeMonthplan.setName(byThemeMonthplan.getName() + "-主题整合月计划（" + byThemeMonthplan.getMonth() + "）");
            return toAjax(byThemeMonthplanService.insertByThemeMonthplan(byThemeMonthplan));
        } else {
            return AjaxResult.error("当前用户非幼儿园，无法添加幼儿");
        }
    }

    /**
     * 修改主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:edit')")
    @Log(title = "主题整合月计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ByThemeMonthplan byThemeMonthplan) {
        return toAjax(byThemeMonthplanService.updateByThemeMonthplan(byThemeMonthplan));
    }

    /**
     * 删除主题整合月计划
     */
    @PreAuthorize("@ss.hasPermi('benyi:thememonthplan:remove')")
    @Log(title = "主题整合月计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(byThemeMonthplanService.deleteByThemeMonthplanByIds(ids));
    }
}
