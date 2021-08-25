package com.stdiet.web.controller.custom;

import com.itextpdf.io.util.DateTimeUtil;
import com.stdiet.common.annotation.Log;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.common.utils.poi.ExcelUtil;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysWxUserInfo;
import com.stdiet.custom.domain.SysWxUserLog;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.*;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

/**
 * 微信用户记录Controller
 *
 * @author wonder
 * @date 2020-11-28
 */
@RestController
@RequestMapping("/custom/wxUserLog")
public class SysWxUserLogController extends BaseController {

    @Autowired
    private ISysWxUserLogService sysWxUserLogService;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private ISysMessageNoticeService sysMessageNoticeService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    /**
     * 查询微信用户记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysWxUserLog sysWxUserLog) {
        startPage();
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);

        for (SysWxUserLog userLog : list) {
            if (StringUtils.isNotEmpty(userLog.getPhone())) {
                userLog.setPhone(StringUtils.hiddenPhoneNumber(userLog.getPhone()));
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出微信用户记录列表
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:export')")
    @Log(title = "微信用户记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SysWxUserLog sysWxUserLog) {
        List<SysWxUserLog> list = sysWxUserLogService.selectSysWxUserLogList(sysWxUserLog);
        for (SysWxUserLog userLog : list) {
            if (StringUtils.isNotEmpty(userLog.getPhone())) {
                userLog.setPhone(StringUtils.hiddenPhoneNumber(userLog.getPhone()));
            }
        }
        ExcelUtil<SysWxUserLog> util = new ExcelUtil<SysWxUserLog>(SysWxUserLog.class);
        return util.exportExcel(list, "wxUserLog");
    }

    /**
     * 获取微信用户记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        SysWxUserLog sysWxUserLog = sysWxUserLogService.selectSysWxUserLogById(id);
        return AjaxResult.success(sysWxUserLog);
    }

    /**
     * 新增微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:add')")
    @Log(title = "微信用户记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysWxUserLog sysWxUserLog) {
        //暂时不开放后台增加打卡，因为无法获取到openid
        //return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
        return AjaxResult.error("新增打卡功能暂时关闭");
    }

    /**
     * 修改微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:edit')")
    @Log(title = "微信用户记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysWxUserLog sysWxUserLog) {
        if( sysWxUserLog != null && StringUtils.isNotEmpty(sysWxUserLog.getOpenid())){
            SysWxUserLog dateLog = sysWxUserLogService.selectSysWxUserLogByDateAndOpenId(sysWxUserLog);
            if(dateLog != null && dateLog.getId().intValue() != sysWxUserLog.getId().intValue()){
                return AjaxResult.error("该用户在"+ DateUtils.dateTime(sysWxUserLog.getLogTime())+"已打卡，无法重复打卡");
            }
            return toAjax(sysWxUserLogService.updateSysWxUserLog(sysWxUserLog));
        }else{
            return AjaxResult.error("修改失败");
        }
    }

    /**
     * 删除微信用户记录
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:remove')")
    @Log(title = "微信用户记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(sysWxUserLogService.deleteSysWxUserLogByIds(ids));
    }

    @GetMapping(value = "/wx/logs/list")
    public AjaxResult getLogs(SysWxUserLog sysWxUserLog) {
        List<WxLogInfo> list = sysWxUserLogService.selectWxLogInfoList(sysWxUserLog);
        return AjaxResult.success(list);
    }

    @PostMapping(value = "/wx/logs/add")
    public AjaxResult addLog(@RequestBody SysWxUserLog sysWxUserLog) {
        // 查询微信用户
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserLog.getOpenid());
        if (StringUtils.isNull(userInfo)) {
            return AjaxResult.error(5003, "没有用户信息");
        }
        // 提取有用值
        sysWxUserLog.setAvatarUrl(userInfo.getAvatarUrl());
        sysWxUserLog.setPhone(userInfo.getPhone());
        sysWxUserLog.setLogTime(DateTimeUtil.getCurrentTimeDate());
        return add(sysWxUserLog);
    }

    @GetMapping(value = "/wx/logs/check/{openid}")
    public AjaxResult checkLog(@PathVariable String openid) {
        SysWxUserLog sysWxUserLog = new SysWxUserLog();
        sysWxUserLog.setLogTime(new Date());
        sysWxUserLog.setOpenid(openid);
        int count = sysWxUserLogService.checkWxLogInfoCount(sysWxUserLog);
        return AjaxResult.success(count);
    }

    /**
     * 获取微信用户记录详细信息
     * @param id 打卡记录ID
     * @param nextFlag 0当前  -1上一条  1下一条
     * @return
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:query')")
    @GetMapping(value = "/getPunchLogDetail/{id}")
    public AjaxResult getPunchLogDetail(@PathVariable("id") String id,
                                        @RequestParam(value = "cusId", required = false)Long cusId,
                                        @RequestParam(value = "nextFlag", required = false, defaultValue = "0")int nextFlag) {
        SysWxUserLog sysWxUserLog = null;
        //根据ID查询
        SysWxUserLog param = new SysWxUserLog();
        param.setId(Long.parseLong(id));
        param.setNextFlag(nextFlag);
        param.setCustomerId(cusId);
        sysWxUserLog = sysWxUserLogService.getPunchLogDetail(param);

        if(sysWxUserLog != null){
            Map<String, List<String>> imageUrlMap = new HashMap<>();
            List<String> breakfastImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getBreakfastImages()) ? Arrays.asList(sysWxUserLog.getBreakfastImages().split("\\|")) : new ArrayList<>();
            imageUrlMap.put("breakfastImages", breakfastImagesUrlList);

            List<String> lunchImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getLunchImages()) ? Arrays.asList(sysWxUserLog.getLunchImages().split("\\|")) : new ArrayList<>();
            imageUrlMap.put("lunchImages", lunchImagesUrlList);

            List<String> dinnerImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getDinnerImages()) ? Arrays.asList(sysWxUserLog.getDinnerImages().split("\\|")) : new ArrayList<>();
            imageUrlMap.put("dinnerImages", dinnerImagesUrlList);

            List<String> extraMealImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getExtraMealImages()) ? Arrays.asList(sysWxUserLog.getExtraMealImages().split("\\|")) : new ArrayList<>();
            imageUrlMap.put("extraMealImages", extraMealImagesUrlList);

            List<String> bodyImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getBodyImages()) ? Arrays.asList(sysWxUserLog.getBodyImages().split("\\|")) : new ArrayList<>();
            imageUrlMap.put("bodyImages", bodyImagesUrlList );

            //生成预览链接
            Map<String,List<String>> downUrlList = AliyunOSSUtils.generatePresignedUrl(imageUrlMap);
            sysWxUserLog.setImagesUrl(downUrlList);

            //查询对应对应客户性别
            if(sysWxUserLog.getCustomerId() != null){
                sysWxUserLog.setSex( sysCustomerService.getCustomerSex(sysWxUserLog.getCustomerId()));
            }
        }

        return AjaxResult.success(sysWxUserLog);
    }

    /**
     * 根据客户ID查询对应打卡体重数据
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:list')")
    @GetMapping("/getAllPunchLogByCustomerId")
    public AjaxResult getAllPunchLogByCustomerId(SysWxUserLog sysWxUserLog) {
        List<SysWxUserLog> list = sysWxUserLogService.getWxUserLogListByCustomerId(sysWxUserLog);
        return AjaxResult.success(list);
    }

    /**
     * 点评客户打卡内容
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:query')")
    @PostMapping("/commentPunchContent")
    public AjaxResult commentPunchContent(@RequestBody SysWxUserLog sysWxUserLog) {
        int row = sysWxUserLogService.updateSysWxUserLog(sysWxUserLog);
        if(row > 0){
            SysWxUserLog log = sysWxUserLogService.selectSysWxUserLogById(sysWxUserLog.getId()+"");
            sysMessageNoticeService.sendPunchCommentMessage(log);
        }
        return toAjax(row);
    }

    /**
     * 查询指定日期未打卡用户
     */
    @PreAuthorize("@ss.hasPermi('custom:wxUserLog:list')")
    @GetMapping("/selectNotPunchCustomerByDate")
    public TableDataInfo selectNotPunchCustomerByDate(SysCustomer sysCustomer) {
        startPage();
        List<SysCustomer> list = sysWxUserLogService.selectNotPunchCustomerByDate(sysCustomer);
        return getDataTable(list);
    }

}