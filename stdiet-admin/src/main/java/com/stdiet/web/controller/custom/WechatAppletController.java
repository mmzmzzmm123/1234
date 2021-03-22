package com.stdiet.web.controller.custom;

import com.itextpdf.io.util.DateTimeUtil;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.response.CustomerCaseResponse;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.ISysCustomerCaseService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysWxUserInfoService;
import com.stdiet.custom.service.ISysWxUserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信小程序统一Controller
 */
@RestController
@RequestMapping("/wx/applet")
public class WechatAppletController extends BaseController {


    @Autowired
    private ISysCustomerCaseService sysCustomerCaseService;

    @Autowired
    private ISysWxUserLogService sysWxUserLogService;

    @Autowired
    private ISysWxUserInfoService sysWxUserInfoService;

    @Autowired
    private ISysOrderService sysOrderService;

    /**
     * 查询微信小程序中展示的客户案例
     */
    @GetMapping("/caseList")
    public TableDataInfo caseList(SysCustomerCase sysCustomerCase)
    {
        startPage();
        sysCustomerCase.setKeywordArray(StringUtils.isNotEmpty(sysCustomerCase.getKeyword()) ? sysCustomerCase.getKeyword().split(",") : null);
        List<CustomerCaseResponse> list = sysCustomerCaseService.getWxCustomerCaseList(sysCustomerCase);
        //处理ID加密
        dealIdEnc(list);
        return getDataTable(list);
    }

    /**
     * 查询客户案例文件列表
     */
    @GetMapping("/getFileByCaseId")
    public AjaxResult getFileByCaseId(@RequestParam("caseId")String caseId)
    {
        Long id = StringUtils.isNotEmpty(caseId) ? Long.parseLong(AesUtils.decrypt(caseId, null)) : null;
        CustomerCaseResponse customerCaseResponse = new CustomerCaseResponse();
        customerCaseResponse.setId(caseId);
        if(id != null){
            List<SysCustomerCaseFile> list = sysCustomerCaseService.getFileListByCaseId(id);
            List<String> fileUrl = new ArrayList<>();
            for (SysCustomerCaseFile caseFile : list) {
                fileUrl.add(caseFile.getFileUrl());
            }
            List<String> downUrlList = AliyunOSSUtils.generatePresignedUrl(fileUrl);
            customerCaseResponse.setFileList(downUrlList);
        }else{
            customerCaseResponse.setFileList(new ArrayList<>());
        }
        return AjaxResult.success(customerCaseResponse);
    }

    /**
     * 同步客户信息，返回订单数量
     * @param sysWxUserInfo
     * @return
     */
    @PostMapping("/synchroCustomerInfo")
    public AjaxResult synchroCustomerInfo(@RequestBody SysWxUserInfo sysWxUserInfo) {
        if(StringUtils.isEmpty(sysWxUserInfo.getOpenid()) || StringUtils.isEmpty(sysWxUserInfo.getPhone())){
            return AjaxResult.error("手机号为空");
        }
        // 查询微信用户
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserInfo.getOpenid());
        if(userInfo != null){
            //更新数据
            sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo);
        }else{
            sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo);
        }
        //查询是否下单
        SysCustomer param = new SysCustomer();
        param.setPhone(sysWxUserInfo.getPhone());
        int orderCount = sysOrderService.getOrderCountByCustomer(param);
        //返回订单数量
        return AjaxResult.success(orderCount);
    }

    /**
     * 微信小程序获取客户打卡记录
     * @param sysWxUserLog
     * @return
     */
    @GetMapping(value = "/getPunchLogs")
    public TableDataInfo getPunchLogs(SysWxUserLog sysWxUserLog) {
        startPage();
        List<WxLogInfo> list = sysWxUserLogService.selectWxLogInfoList(sysWxUserLog);
        return getDataTable(list);
    }

    /**
     * 今日是否打卡
     * @param openid
     * @return
     */
    @GetMapping(value = "/checkNowPunchLog/{openid}")
    public AjaxResult checkNowPunchLog(@PathVariable String openid) {
        int count = StringUtils.isEmpty(openid) ? 0 : sysWxUserLogService.checkWxLogInfoCount(openid);
        return AjaxResult.success(count);
    }

    /**
     * 微信小程序打卡
     * @param sysWxUserLog
     * @return
     */
    @PostMapping(value = "/addPunchLog")
    public AjaxResult addPunchLog(@RequestBody SysWxUserLog sysWxUserLog) {
        // 查询微信用户
        SysWxUserInfo userInfo = StringUtils.isEmpty(sysWxUserLog.getOpenid()) ? null : sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserLog.getOpenid());
        if (userInfo == null) {
            return AjaxResult.error("不存在客户");
        }
        //查询今日是否已打卡
        int count = sysWxUserLogService.checkWxLogInfoCount(sysWxUserLog.getOpenid());
        if(count > 0){
            return AjaxResult.error("今日已打卡，不可重复打卡");
        }
        sysWxUserLog.setLogTime(DateTimeUtil.getCurrentTimeDate());
        return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
    }

    /**
     * 处理返回值的ID加密
     * @param list
     * @return
     */
    private void dealIdEnc(List<CustomerCaseResponse> list){
        for (CustomerCaseResponse cus : list) {
            cus.setId(AesUtils.encrypt(cus.getId()+"", null));
        }
    }
}
