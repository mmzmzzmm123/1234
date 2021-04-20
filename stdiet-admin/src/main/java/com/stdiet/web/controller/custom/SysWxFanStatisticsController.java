package com.stdiet.web.controller.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysWxDistribution;
import com.stdiet.custom.dto.request.FanStatisticsRequest;
import com.stdiet.custom.dto.response.ExportFanStatisticsResponse;
import com.stdiet.custom.service.ISysWxDistributionService;
import com.stdiet.framework.web.domain.server.Sys;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysWxFanStatistics;
import com.stdiet.custom.service.ISysWxFanStatisticsService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 进粉统计Controller
 *
 * @author xiezhijun
 * @date 2021-02-03
 */
@RestController
@RequestMapping("/custom/fanStatistics")
public class SysWxFanStatisticsController extends BaseController
{
    @Autowired
    private ISysWxFanStatisticsService sysWxFanStatisticsService;

    @Autowired
    private ISysWxDistributionService sysWxDistributionService;

    /**
     * 查询进粉统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxFanStatistics sysWxFanStatistics)
    {
        startPage();
        List<SysWxFanStatistics> list = sysWxFanStatisticsService.selectSysWxFanStatisticsList(sysWxFanStatistics);
        if(list != null && list.size() > 0){
            int totalFanNum = sysWxFanStatisticsService.selectFanNumCount(sysWxFanStatistics);
            list.get(0).setTotalFanNum(totalFanNum);
        }
        return getDataTable(list);
    }

    /**
     * 导出进粉统计列表
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:export')")
    @Log(title = "进粉统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxFanStatistics sysWxFanStatistics)
    {
        sysWxFanStatistics.setSortFlag(1);
        List<SysWxFanStatistics> list = sysWxFanStatisticsService.exportStatisticsList(sysWxFanStatistics);
        List<ExportFanStatisticsResponse> responsesList = new ArrayList<>();
        ExportFanStatisticsResponse exportFanStatisticsResponse = null;
        int groupId = 0;
        int index = 0;
        int totalFanNum = 0;
        for(SysWxFanStatistics sysWxFan : list){
            int wxGroupId = sysWxFan.getSaleGroupId() != null ? sysWxFan.getSaleGroupId().intValue() : 0;
            if(wxGroupId != groupId && groupId != 0){
                responsesList.add(new ExportFanStatisticsResponse());
                index = 0;
            }
            groupId = wxGroupId;
            exportFanStatisticsResponse = new ExportFanStatisticsResponse();
            exportFanStatisticsResponse.setId(++index);
            exportFanStatisticsResponse.setAccount(sysWxFan.getAccount());
            exportFanStatisticsResponse.setSaleName(sysWxFan.getUserName());
            exportFanStatisticsResponse.setWxAccount(sysWxFan.getWxAccount());
            exportFanStatisticsResponse.setWxPhone(sysWxFan.getWxPhone());
            exportFanStatisticsResponse.setFanNum(sysWxFan.getFanNum());
            responsesList.add(exportFanStatisticsResponse);
            totalFanNum += exportFanStatisticsResponse.getFanNum().intValue();
        }
        responsesList.add(new ExportFanStatisticsResponse());
        ExportFanStatisticsResponse total = new ExportFanStatisticsResponse();
        total.setWxAccount("总计进粉量");
        total.setFanNum(totalFanNum);
        responsesList.add(total);
        ExcelUtil<ExportFanStatisticsResponse> util = new ExcelUtil<ExportFanStatisticsResponse>(ExportFanStatisticsResponse.class);
        return util.exportExcel(responsesList, DateUtils.getDate());
    }

    /**
     * 获取进粉统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysWxFanStatisticsService.selectSysWxFanStatisticsById(id));
    }

    /**
     * 新增进粉统计
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:add')")
    @Log(title = "进粉统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FanStatisticsRequest fanStatisticsRequest)
    {
        return sysWxFanStatisticsService.addWxFanNum(fanStatisticsRequest);
    }

    /**
     * 修改进粉统计
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:edit')")
    @Log(title = "进粉统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxFanStatistics sysWxFanStatistics)
    {
        List<SysWxFanStatistics> wxIdFanStatistics = sysWxFanStatisticsService.getWxFanStatisticsByUserIdAndFanTime(sysWxFanStatistics);
        System.out.println(wxIdFanStatistics.size());
        if(wxIdFanStatistics != null && wxIdFanStatistics.size() > 0 && sysWxFanStatistics.getId().intValue() != wxIdFanStatistics.get(0).getId().intValue()){
            return AjaxResult.error("当前日期、微信号下已存在进粉记录");
        }
        return toAjax(sysWxFanStatisticsService.updateSysWxFanStatistics(sysWxFanStatistics));
    }

    /**
     * 删除进粉统计
     */
    @PreAuthorize("@ss.hasPermi('custom:fanStatistics:remove')")
    @Log(title = "进粉统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysWxFanStatisticsService.deleteSysWxFanStatisticsByIds(ids));
    }

    /**
     * 获取当前用户被分配的微信号
     */
    @RequestMapping("/getWxByUserId")
    public AjaxResult getWxByUserId(@RequestParam("userId")Long userId){
        List<SysWxDistribution> list = sysWxDistributionService.selectDistributionWxByUserId(userId);
        return AjaxResult.success(list);
    }
}
