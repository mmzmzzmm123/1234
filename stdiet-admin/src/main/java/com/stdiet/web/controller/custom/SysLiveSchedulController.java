package com.stdiet.web.controller.custom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.NumberUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysLiveSchedulFanRecord;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.custom.domain.SysLiveSchedul;
import com.stdiet.custom.service.ISysLiveSchedulService;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.common.core.page.TableDataInfo;

/**
 * 直播排班Controller
 *
 * @author xzj
 * @date 2021-05-12
 */
@RestController
@RequestMapping("/custom/liveSchedul")
public class SysLiveSchedulController extends BaseController
{
    @Autowired
    private ISysLiveSchedulService sysLiveSchedulService;

    /**
     * 查询直播排班列表
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:list')")
    @GetMapping("/list")
    public AjaxResult list(SysLiveSchedul sysLiveSchedul)
    {
        startPage();
        List<SysLiveSchedul> list = sysLiveSchedulService.selectSysLiveSchedulList(sysLiveSchedul);

        int totalImportNum = 0;
        int totalAddFanNum = 0;
        double totalLiveTime = 0;
        if(list != null && list.size() > 0){
            //查询总共导粉量
            totalImportNum = sysLiveSchedulService.getTotalImportFanNumByLiveSchedul(sysLiveSchedul);
            //查询总共进粉量
            totalAddFanNum = sysLiveSchedulService.getTotalAddFanNumByLiveSchedul(sysLiveSchedul);
            //查询总直播时长(小时)
            totalLiveTime = NumberUtils.getNumberByRoundHalfUp(sysLiveSchedulService.getLiveTotalTimeByLiveSchedul(sysLiveSchedul)/60.0,2).doubleValue();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("tableDataInfo", getDataTable(list));
        result.put("totalImportNum", totalImportNum);
        result.put("totalAddFanNum", totalAddFanNum);
        result.put("totalLiveTime", totalLiveTime);

        return AjaxResult.success(result);
    }

    /**
     * 导出直播排班列表
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:export')")
    @Log(title = "直播排班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysLiveSchedul sysLiveSchedul)
    {
        List<SysLiveSchedul> list = sysLiveSchedulService.selectSysLiveSchedulList(sysLiveSchedul);
        for (SysLiveSchedul live : list) {
            live.setImportFanSize(0);
            if(live.getWxAccountList() != null && live.getWxAccountList().size() > 0){
                for (Map<String,Object> map : live.getWxAccountList()
                     ) {
                   live.setImportFanSize(live.getImportFanSize() + (map.containsKey("fanNum") ? ((BigDecimal) map.get("fanNum")).intValue() : 0));
                }
            }
            live.setAddFanSize(live.getAddFanList() != null ? live.getAddFanList().size() : 0);
            String liveTimeStr = "";
            if(live.getLiveStartTime() != null){
                liveTimeStr = DateUtils.getHourMinuteByDate(live.getLiveStartTime());
            }
            if(live.getLiveEndTime() != null){
                liveTimeStr += "-" + DateUtils.getHourMinuteByDate(live.getLiveEndTime());
            }
            live.setLiveStartTimeString(liveTimeStr);
            //计算直播时长
            if(live.getLiveStartTime() != null && live.getLiveEndTime() != null){
                double t = (live.getLiveEndTime().getTime() - live.getLiveStartTime().getTime())/1000.0/60/60;
                live.setLiveTotalHour(t > 0 ? NumberUtils.getNumberByRoundHalfUp(t,2) : new BigDecimal(0));
            }
        }
        ExcelUtil<SysLiveSchedul> util = new ExcelUtil<SysLiveSchedul>(SysLiveSchedul.class);
        return util.exportExcel(list, "liveSchedul");
    }

    /**
     * 获取直播排班详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(sysLiveSchedulService.selectSysLiveSchedulById(id));
    }

    /**
     * 新增直播排班
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:add')")
    @Log(title = "直播排班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysLiveSchedul sysLiveSchedul)
    {
        SysLiveSchedul oldLiveSchedul = sysLiveSchedulService.getLiveSchedulByLiveTime(sysLiveSchedul);
        if(oldLiveSchedul != null){
            return AjaxResult.error("直播时间冲突，无法添加");
        }
        //下播,补全下播时间
        if (sysLiveSchedul.getLiveStatus() != null && sysLiveSchedul.getLiveStatus().longValue() == 2
                && sysLiveSchedul.getLiveEndTime() == null){
            Date now = new Date();
            if(!DateUtils.isSameDay(now,sysLiveSchedul.getLiveStartTime())){
                sysLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(sysLiveSchedul.getLiveStartTime()));
            }else{
                sysLiveSchedul.setLiveEndTime(now);
            }
        }
        int row = sysLiveSchedulService.insertSysLiveSchedul(sysLiveSchedul);
        if(row > 0){
            if(sysLiveSchedul.getLiveStatus() != null){
                //开播
                if(sysLiveSchedul.getLiveStatus().longValue() == 1){
                    SysLiveSchedul lastLiveSchedul = sysLiveSchedulService.getLastLiveSchedulById(sysLiveSchedul);
                    //上一个直播还在直播，需要关闭上一个直播
                    if(lastLiveSchedul != null && lastLiveSchedul.getLiveStatus().longValue() == 1){
                        lastLiveSchedul.setLiveStatus(2L);
                        if(lastLiveSchedul.getLiveEndTime() == null){
                            if(!DateUtils.isSameDay(lastLiveSchedul.getLiveStartTime(),sysLiveSchedul.getLiveStartTime())){
                                lastLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(lastLiveSchedul.getLiveStartTime()));
                            }else{
                                lastLiveSchedul.setLiveEndTime(sysLiveSchedul.getLiveStartTime());
                            }
                        }
                        row = sysLiveSchedulService.updateSysLiveSchedul(lastLiveSchedul);
                    }
                }
            }
        }
        return toAjax(row);
    }

    /**
     * 修改直播排班
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:edit')")
    @Log(title = "直播排班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysLiveSchedul sysLiveSchedul)
    {
        SysLiveSchedul oldLiveSchedul = sysLiveSchedulService.getLiveSchedulByLiveTime(sysLiveSchedul);
        if(oldLiveSchedul != null && oldLiveSchedul.getId().longValue() != sysLiveSchedul.getId().longValue()){
            return AjaxResult.error("直播时间冲突，无法修改");
        }
        //下播,补全下播时间
        if (sysLiveSchedul.getLiveStatus() != null && sysLiveSchedul.getLiveStatus().longValue() == 2
                && sysLiveSchedul.getLiveEndTime() == null){
            Date now = new Date();
            if(!DateUtils.isSameDay(now,sysLiveSchedul.getLiveStartTime())){
                sysLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(sysLiveSchedul.getLiveStartTime()));
            }else{
                sysLiveSchedul.setLiveEndTime(now);
            }
        }
        int row = sysLiveSchedulService.updateSysLiveSchedulById(sysLiveSchedul);
        if(row > 0){
            if(sysLiveSchedul.getLiveStatus() != null){
                //开播
                if(sysLiveSchedul.getLiveStatus().longValue() == 1){
                    SysLiveSchedul lastLiveSchedul = sysLiveSchedulService.getLastLiveSchedulById(sysLiveSchedul);
                    //上一个直播还在直播，需要关闭上一个直播
                    if(lastLiveSchedul != null && lastLiveSchedul.getLiveStatus().longValue() == 1){
                        lastLiveSchedul.setLiveStatus(2L);
                        if(lastLiveSchedul.getLiveEndTime() == null){
                            if(!DateUtils.isSameDay(lastLiveSchedul.getLiveStartTime(),sysLiveSchedul.getLiveStartTime())){
                                lastLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(lastLiveSchedul.getLiveStartTime()));
                            }else{
                                lastLiveSchedul.setLiveEndTime(sysLiveSchedul.getLiveStartTime());
                            }
                        }
                        row = sysLiveSchedulService.updateSysLiveSchedul(lastLiveSchedul);
                    }
                }
            }
        }
        return toAjax(row);
    }

    /**
     * 删除直播排班
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:remove')")
    @Log(title = "直播排班", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysLiveSchedulService.deleteSysLiveSchedulByIds(ids));
    }

    /**
     * 开播、下播
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:edit')")
    @Log(title = "开播下播", businessType = BusinessType.UPDATE)
    @GetMapping(value = "/updateLiveStatus")
    public AjaxResult updateLiveStatus(SysLiveSchedul sysLiveSchedul)
    {
        if(sysLiveSchedul.getId() == null || sysLiveSchedul.getLiveStatus() == null){
            return AjaxResult.error("操作失败");
        }
        int row = 0;
        if(sysLiveSchedul.getLiveStatus().longValue() == 1){
            row = sysLiveSchedulService.updateSysLiveSchedul(sysLiveSchedul);
            if(row > 0){
                sysLiveSchedul = sysLiveSchedulService.selectSysLiveSchedulById(sysLiveSchedul.getId());
                if(sysLiveSchedul != null && sysLiveSchedul.getLiveStatus() != null){
                    //开播
                    if(sysLiveSchedul.getLiveStatus().longValue() == 1){
                        SysLiveSchedul lastLiveSchedul = sysLiveSchedulService.getLastLiveSchedulById(sysLiveSchedul);
                        //上一个直播还在直播，需要关闭上一个直播
                        if(lastLiveSchedul != null && lastLiveSchedul.getLiveStatus().longValue() == 1){
                            lastLiveSchedul.setLiveStatus(2L);
                            if(lastLiveSchedul.getLiveEndTime() == null){
                                if(!DateUtils.isSameDay(lastLiveSchedul.getLiveStartTime(),sysLiveSchedul.getLiveStartTime())){
                                    lastLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(lastLiveSchedul.getLiveStartTime()));
                                }else{
                                    lastLiveSchedul.setLiveEndTime(sysLiveSchedul.getLiveStartTime());
                                }
                            }
                            row = sysLiveSchedulService.updateSysLiveSchedul(lastLiveSchedul);
                        }
                    }
                }
            }
        }else{
            SysLiveSchedul newLiveSchedul = sysLiveSchedulService.selectSysLiveSchedulById(sysLiveSchedul.getId());
            if(newLiveSchedul != null && newLiveSchedul.getLiveEndTime() == null){
                Date now = new Date();
                if(!DateUtils.isSameDay(now,newLiveSchedul.getLiveStartTime())){
                    sysLiveSchedul.setLiveEndTime(DateUtils.getLastMinuteDate(newLiveSchedul.getLiveStartTime()));
                }else{
                    sysLiveSchedul.setLiveEndTime(now);
                }
            }
            row = sysLiveSchedulService.updateSysLiveSchedul(sysLiveSchedul);
        }
        return toAjax(row);
    }

    /**
     * 复制上次直播记录
     */
    @PreAuthorize("@ss.hasPermi('custom:liveSchedul:add')")
    @Log(title = "复制上次直播记录", businessType = BusinessType.INSERT)
    @GetMapping(value = "/copyLastTimeLiveSchedul")
    public AjaxResult copyLastTimeLiveSchedul(){
        return sysLiveSchedulService.copyLastTimeLiveSchedul();
    }

    /**
     * 根据日期查询直播记录
     */
    @GetMapping(value = "/getAllLiveSchedulByDate")
    public AjaxResult getAllLiveSchedulByDate(SysLiveSchedul sysLiveSchedul){
        List<SysLiveSchedul> list = sysLiveSchedulService.selectSysLiveSchedulList(sysLiveSchedul);
        return AjaxResult.success(list);
    }

    /**
     * 根据日期查询直播记录
     */
    @GetMapping(value = "/getLiveSchedulByTime")
    public AjaxResult getLiveSchedulByTime(SysLiveSchedul sysLiveSchedul){
        SysLiveSchedul live = null;
        try {
            if(sysLiveSchedul.getFanChannel() != null && StringUtils.isNotEmpty(sysLiveSchedul.getLiveStartTimeString())){
                sysLiveSchedul.setLiveStartTimeString(java.net.URLDecoder.decode(sysLiveSchedul.getLiveStartTimeString(), "UTF-8"));
                sysLiveSchedul.setLiveStartTime(DateUtils.parseDate(sysLiveSchedul.getLiveStartTimeString()));
                live = sysLiveSchedulService.getLiveSchedulByTime(sysLiveSchedul);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return AjaxResult.success(live);
    }

    /**
     * 给对应直播添加、更新进粉记录
     */
    @PostMapping(value = "/addOrEditSysLiveSchedulFanRecord")
    public AjaxResult addOrEditSysLiveSchedulFanRecord(@RequestBody  SysLiveSchedulFanRecord sysLiveSchedulFanRecord){
        int row = sysLiveSchedulFanRecord.getId() == null ? sysLiveSchedulService.addLiveSchedulFanRecord(sysLiveSchedulFanRecord) : sysLiveSchedulService.updateLiveSchedulFanRecord(sysLiveSchedulFanRecord);
        return toAjax(row);
    }

    /**
     * 给对应直播删除进粉记录
     */
    @GetMapping(value = "/delSysLiveSchedulFanRecord")
    public AjaxResult delSysLiveSchedulFanRecord(@RequestParam("id")Long id){
        return toAjax(sysLiveSchedulService.delLiveSchedulFanRecord(id));
    }
}