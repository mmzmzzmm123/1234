package com.stdiet.web.controller.custom;

import com.itextpdf.io.util.DateTimeUtil;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.response.CustomerCaseResponse;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    @Autowired
    private ISysNutritionQuestionService sysNutritionQuestionService;

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
        return AjaxResult.success();
    }

    /**
     * 微信小程序获取客户打卡记录
     * @param sysWxUserLog
     * @return
     */
    @GetMapping(value = "/getPunchLogs")
    public AjaxResult getPunchLogs(SysWxUserLog sysWxUserLog) {
        if(StringUtils.isEmpty(sysWxUserLog.getPhone()) && StringUtils.isEmpty(sysWxUserLog.getOpenid())){
            return  AjaxResult.error(5001, "缺少参数");
        }
        //查询是否下单
        SysCustomer param = new SysCustomer();
        param.setPhone(sysWxUserLog.getPhone());
        int orderCount = sysOrderService.getOrderCountByCustomer(param);
        if(orderCount > 0){
            Map<String, Object> result = new HashMap<>();
            //今日是否已打卡
            boolean isPunch = false;
            startPage();
            List<WxLogInfo> list = sysWxUserLogService.getWxLogInfoList(sysWxUserLog);
            if(list.size() > 0){
                WxLogInfo lastLog = list.get(0);
                if(lastLog.getDate() != null && ChronoUnit.DAYS.between(DateUtils.stringToLocalDate(lastLog.getDate(), "yyyy-MM-dd"), LocalDate.now()) == 0) {
                    isPunch = true;
                }
            }

            /*for (WxLogInfo wxLogInfo : list) {
                Map<String, List<String>> imageUrlMap = new HashMap<>();

                List<String> breakfastImagesUrlList = StringUtils.isNotEmpty(wxLogInfo.getBreakfastImages()) ? Arrays.asList(wxLogInfo.getBreakfastImages().split("\\|")) : new ArrayList<>();
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

                wxLogInfo.setBreakfastImagesUrl(downUrlList.get("breakfastImages"));
                wxLogInfo.setLunchImagesUrl(downUrlList.get("lunchImages"));
                wxLogInfo.setDinnerImagesUrl(downUrlList.get("dinnerImages"));
                wxLogInfo.setExtraMealImagesUrl(downUrlList.get("extraMealImages"));
                wxLogInfo.setBodyImagesUrl(downUrlList.get("bodyImages"));
            }*/

            Collections.reverse(list);
            TableDataInfo tableDataInfo = getDataTable(list);
            result.put("isPunch", isPunch);
            result.put("tableDataInfo", tableDataInfo);
            return AjaxResult.success(result);
        }else{
            return AjaxResult.error(5002, "未查询到相关订单信息");
        }
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
        if (userInfo == null || StringUtils.isEmpty(userInfo.getPhone())) {
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
     * 获取微信用户记录详细信息
     */
    @GetMapping(value = "/getPunchLogDetail/{id}")
    public AjaxResult getPunchLogDetail(@PathVariable("id") String id) {
        WxLogInfo sysWxUserLog = null;
        //根据ID查询
        SysWxUserLog param = new SysWxUserLog();
        param.setId(Long.parseLong(id));
        sysWxUserLog = sysWxUserLogService.getWxLogInfoDetailById(param);
        if(sysWxUserLog == null){
            return AjaxResult.error("打卡记录不存在");
        }
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

        sysWxUserLog.setBreakfastImagesUrl(downUrlList.get("breakfastImages"));
        sysWxUserLog.setLunchImagesUrl(downUrlList.get("lunchImages"));
        sysWxUserLog.setDinnerImagesUrl(downUrlList.get("dinnerImages"));
        sysWxUserLog.setExtraMealImagesUrl(downUrlList.get("extraMealImages"));
        sysWxUserLog.setBodyImagesUrl(downUrlList.get("bodyImages"));

        return AjaxResult.success(sysWxUserLog);
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

    /**
     * 上传文件到OSS返回URL
     */
    @PostMapping(value = "/uploadFile/{prefix}")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String prefix) throws Exception {
        try {
            if (file == null) {
                return AjaxResult.error("文件不存在");
            }
            int fileNameLength = file.getOriginalFilename().length();
            if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
                throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
            }
            FileUploadUtils.assertAllowed(file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);

            String fileUrl = AliyunOSSUtils.uploadFileInputSteam(prefix + '/', DateUtils.getDate() + "/" + file.getOriginalFilename(), file);

            AjaxResult ajax = null;
            if (StringUtils.isNotEmpty(fileUrl)) {
                ajax = AjaxResult.success();
                ajax.put("fileUrl", fileUrl);
            } else {
                ajax = AjaxResult.error("文件上传失败");
            }
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error("文件上传失败");
        }
    }

    /**
     * 获取微信用户记录详细信息
     */
    @GetMapping(value = "/getNutritionQuestionList")
    public AjaxResult getNutritionQuestionList(SysNutritionQuestion sysNutritionQuestion, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,  @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        Map<String,Object> result = sysNutritionQuestionService.getNutritionQuestionListByKey(sysNutritionQuestion, pageNum, pageSize);
        return AjaxResult.success(result);
    }
}
