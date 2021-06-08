package com.stdiet.web.controller.custom;

import java.math.BigDecimal;
import java.util.*;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysWxDistribution;
import com.stdiet.custom.dto.request.FanStatisticsRequest;
import com.stdiet.custom.dto.response.ExportFanStatisticsResponse;
import com.stdiet.custom.service.ISysImportFanRecordService;
import com.stdiet.custom.service.ISysWxDistributionService;
import com.stdiet.framework.web.domain.server.Sys;
import io.swagger.models.auth.In;
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

    @Autowired
    private ISysImportFanRecordService sysImportFanRecordService;

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

    /**
     * 获取导粉通过率
     */
    @RequestMapping("/getImportFanPassRate")
    public AjaxResult getImportFanPassRate(SysWxFanStatistics sysWxFanStatistics){
        List<Map<String,Object>> passList = sysWxFanStatisticsService.getTotalFanNumGroupByChannel(sysWxFanStatistics);
        List<Map<String,Object>> importList = sysImportFanRecordService.getTotalFanNumGroupByChannel(sysWxFanStatistics);
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Integer> channelIdList = new ArrayList<>();
        for (Map<String,Object> map : passList) {
            Integer channelId = (Integer)map.get("channelId");
            if(channelId != null && !channelIdList.contains(channelId)){
                channelIdList.add(channelId);
            }
        }
        for (Map<String,Object> map : importList) {
            Integer channelId = (Integer)map.get("channelId");
            if(channelId != null && !channelIdList.contains(channelId)){
                channelIdList.add(channelId);
            }
        }
        Double allTotalImportFanNum = 0.0;
        Double allTotalPassFanNum = 0.0;
        for (Integer channelId : channelIdList) {
            //根据渠道ID查询导粉记录
            Map<String,Object> importMap = getMap(importList, channelId);
            Map<String,Object> passMap = getMap(passList, channelId);
            if(importMap != null || passMap != null){
                BigDecimal totalImportFanNum = new BigDecimal(0);
                BigDecimal totalPassFanNum = new BigDecimal(0);
                String channelName = "";
                if(importMap != null){
                    totalImportFanNum = importMap.get("totalFanNum") != null ? (BigDecimal) importMap.get("totalFanNum") : new BigDecimal(0);
                    channelName = (String)importMap.get("channelName");
                }
                if(passMap != null){
                    totalPassFanNum = passMap.get("totalFanNum") != null ? (BigDecimal) passMap.get("totalFanNum") : new BigDecimal(0);
                    channelName = (String)passMap.get("channelName");
                }
                if(totalImportFanNum.doubleValue() == 0 && totalPassFanNum.doubleValue() == 0){
                    continue;
                }
                allTotalImportFanNum += totalImportFanNum.doubleValue();
                allTotalPassFanNum += totalPassFanNum.doubleValue();
                Map<String, Object> rateMap = new HashMap<>();
                rateMap.put("channelName", channelName);
                rateMap.put("totalImportFanNum", totalImportFanNum);
                rateMap.put("totalPassFanNum", totalPassFanNum);
                BigDecimal passRate = totalImportFanNum.doubleValue() > 0 ? new BigDecimal(totalPassFanNum.doubleValue()*100/totalImportFanNum.doubleValue()).setScale(1, BigDecimal.ROUND_HALF_UP) : new BigDecimal(100);
                rateMap.put("passRate", passRate.doubleValue() > 100 ? 100 : passRate);
                resultList.add(rateMap);
            }
        }
        if(resultList.size() > 0){
            Map<String,Object> total = new HashMap<>();
            total.put("channelName", "总计");
            total.put("totalImportFanNum", allTotalImportFanNum);
            total.put("totalPassFanNum", allTotalPassFanNum);
            BigDecimal totalPassRate = allTotalImportFanNum > 0 ? new BigDecimal(allTotalPassFanNum*100/allTotalImportFanNum).setScale(1, BigDecimal.ROUND_HALF_UP) : new BigDecimal(100);
            total.put("passRate", totalPassRate.doubleValue() > 100 ? 100 : totalPassRate);
            resultList.add(total);
        }
        return AjaxResult.success(resultList);
    }

    private Map<String,Object> getMap(List<Map<String,Object>> list, Integer channelId){
        for (Map<String,Object> map : list) {
            if(map.get("channelId") != null && ((Integer) map.get("channelId")).intValue() == channelId.intValue()){
                return map;
            }
        }
        return null;
    }
}
