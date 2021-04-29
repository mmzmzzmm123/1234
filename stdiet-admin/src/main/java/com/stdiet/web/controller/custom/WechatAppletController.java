package com.stdiet.web.controller.custom;

import com.aliyun.vod20170321.models.GetPlayInfoResponseBody;
import com.aliyun.vod20170321.models.GetVideoInfoResponseBody;
import com.aliyun.vod20170321.models.GetVideoListResponseBody;
import com.itextpdf.io.util.DateTimeUtil;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.enums.BusinessType;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.response.CustomerCaseResponse;
import com.stdiet.custom.dto.response.MessageNoticeResponse;
import com.stdiet.custom.dto.response.NutritionalVideoResponse;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.*;
import org.aspectj.weaver.loadtime.Aj;
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

    @Autowired
    private ISysAskNutritionQuestionService sysAskNutritionQuestionService;

    @Autowired
    private ISysMessageNoticeService sysMessageNoticeService;

    @Autowired
    private ISysCustomerService sysCustomerService;

    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;

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
     * 同步客户信息
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
        Map<String,Object> result = new HashMap<>();
        //根据手机号查询返回用户加密ID
        SysCustomer customer = sysCustomerService.getCustomerByPhone(sysWxUserInfo.getPhone());
        result.put("customerId", customer != null ? AesUtils.encrypt(customer.getId()+"", null) : null);
        //查询未读消息数量
        SysMessageNotice messageParam = new SysMessageNotice();
        messageParam.setReadType(0);
        messageParam.setMessageCustomer(customer != null ? customer.getId() : 0);
        int unReadNoticeTotal = sysMessageNoticeService.getCustomerMessageCount(messageParam);
        result.put("unReadNoticeTotal", unReadNoticeTotal);
        return AjaxResult.success(result);
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

            /*for (WxLogInfo log : list) {
                log.setId(AesUtils.encrypt(log.getId()+"", null));
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
        SysWxUserLog sysWxUserLog = new SysWxUserLog();
        sysWxUserLog.setOpenid(openid);
        sysWxUserLog.setLogTime(new Date());
        int count = StringUtils.isEmpty(openid) ? 0 : sysWxUserLogService.checkWxLogInfoCount(sysWxUserLog);
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
            return AjaxResult.error("打卡失败");
        }
        if(sysWxUserLog.getId() == null){
            //查询今日是否已打卡
            if(sysWxUserLog.getLogTime() == null){
                sysWxUserLog.setLogTime(new Date());
            }
            int count = sysWxUserLogService.checkWxLogInfoCount(sysWxUserLog);
            if(count > 0){
                return AjaxResult.error("今日已打卡，不可重复打卡");
            }
            return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
        }else{
            return toAjax(sysWxUserLogService.updateSysWxUserLog(sysWxUserLog));
        }

    }

    public static final String[] imageName = {"breakfastImages", "lunchImages", "dinnerImages", "extraMealImages", "bodyImages"};

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
        imageUrlMap.put(imageName[0], breakfastImagesUrlList);

        List<String> lunchImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getLunchImages()) ? Arrays.asList(sysWxUserLog.getLunchImages().split("\\|")) : new ArrayList<>();
        imageUrlMap.put(imageName[1], lunchImagesUrlList);

        List<String> dinnerImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getDinnerImages()) ? Arrays.asList(sysWxUserLog.getDinnerImages().split("\\|")) : new ArrayList<>();
        imageUrlMap.put(imageName[2], dinnerImagesUrlList);

        List<String> extraMealImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getExtraMealImages()) ? Arrays.asList(sysWxUserLog.getExtraMealImages().split("\\|")) : new ArrayList<>();
        imageUrlMap.put(imageName[3], extraMealImagesUrlList);

        List<String> bodyImagesUrlList = StringUtils.isNotEmpty(sysWxUserLog.getBodyImages()) ? Arrays.asList(sysWxUserLog.getBodyImages().split("\\|")) : new ArrayList<>();
        imageUrlMap.put(imageName[4], bodyImagesUrlList );

        //生成预览链接
        Map<String,List<String>> downUrlList = AliyunOSSUtils.generatePresignedUrl(imageUrlMap);

        sysWxUserLog.setBreakfastImagesUrl(downUrlList.get(imageName[0]));
        sysWxUserLog.setLunchImagesUrl(downUrlList.get(imageName[1]));
        sysWxUserLog.setDinnerImagesUrl(downUrlList.get(imageName[2]));
        sysWxUserLog.setExtraMealImagesUrl(downUrlList.get(imageName[3]));
        sysWxUserLog.setBodyImagesUrl(downUrlList.get(imageName[4]));

        List<String> allImagesList = new ArrayList<>();
        List<String> allUrlList = new ArrayList<>();

        for (String key : imageName) {
            if(!"bodyImages".equals(key)){
                allUrlList.addAll(downUrlList.get(key));
                allImagesList.addAll(imageUrlMap.get(key));
            }
        }
        sysWxUserLog.setAllImagesUrl(allUrlList);
        sysWxUserLog.setAllImages(StringUtils.join(allImagesList, "|"));

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
     * 获取小程序展示的营养小知识列表
     */
    @GetMapping(value = "/getNutritionQuestionList")
    public AjaxResult getNutritionQuestionList(SysNutritionQuestion sysNutritionQuestion, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,  @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        sysNutritionQuestion.setShowFlag(1);
        Map<String,Object> result = sysNutritionQuestionService.getNutritionQuestionListByKey(sysNutritionQuestion, pageNum, pageSize);
        return AjaxResult.success(result);
    }

    /**
     * 新增营养小知识提问
     */
    @PostMapping("/addAskNutritionQuestion")
    public AjaxResult addAskNutritionQuestion(@RequestBody SysAskNutritionQuestion sysAskNutritionQuestion)
    {
        if(StringUtils.isEmpty(sysAskNutritionQuestion.getOpenid()) || StringUtils.isEmpty(sysAskNutritionQuestion.getQuestion())){
            return AjaxResult.error("添加失败");
        }
        return toAjax(sysAskNutritionQuestionService.insertSysAskNutritionQuestion(sysAskNutritionQuestion));
    }

    /**
     * 获取用户通知消息
     */
    @GetMapping(value = "/getCustomerMessage")
    public TableDataInfo getCustomerMessage(SysMessageNotice sysMessageNotice) {
        startPage();
        if(StringUtils.isNotEmpty(sysMessageNotice.getCustomerId())){
            sysMessageNotice.setMessageCustomer(Long.parseLong(AesUtils.decrypt(sysMessageNotice.getCustomerId(), null)));
        }else{
            sysMessageNotice.setMessageCustomer(0L);
        }
        List<MessageNoticeResponse> list = sysMessageNoticeService.getCustomerMessage(sysMessageNotice);
        return getDataTable(list);
    }

    /**
     * 更新用户通知消息已读状态
     */
    @GetMapping(value = "/updateMessageReadStatus")
    public AjaxResult updateMessageReadStatus(@RequestParam("id")Long id) {
        SysMessageNotice sysMessageNotice = new SysMessageNotice();
        sysMessageNotice.setReadType(1);
        sysMessageNotice.setId(id);
        return toAjax(sysMessageNoticeService.updateSysMessageNotice(sysMessageNotice));
    }

    /**
     * 更新用户通知消息已读状态
     */
    @GetMapping(value = "/getVideoList")
    public TableDataInfo getVideoList(SysNutritionalVideo sysNutritionalVideo) {
        AjaxResult result = AjaxResult.success();
        startPage();
        //int total = 0;
        //List<NutritionalVideoResponse> nutritionalVideoList = new ArrayList<>();
        try{
            /**GetVideoListResponseBody videoListResponseBody = AliyunVideoUtils.getVideoListByPage(null, "Normal", 1, 10);
            if(videoListResponseBody != null){
                total = videoListResponseBody.total;
                for (GetVideoListResponseBody.GetVideoListResponseBodyVideoListVideo video : videoListResponseBody.videoList.video) {
                    NutritionalVideoResponse nutritionalVideoResponse = new NutritionalVideoResponse();
                    nutritionalVideoResponse.setCoverURL(video.getCoverURL());
                    nutritionalVideoResponse.setTitle(video.getTitle());
                    nutritionalVideoResponse.setVideoId(video.getVideoId());
                    nutritionalVideoResponse.setDescription(video.getDescription());
                    nutritionalVideoResponse.setTags(video.getTags());
                    nutritionalVideoList.add(nutritionalVideoResponse);
                    System.out.println(video.getVideoId());
                    System.out.println(video.getCoverURL());
                    System.out.println(video.getTitle());
                    System.out.println(video.getDescription());
                }
            }
            System.out.println();**/
            sysNutritionalVideo.setShowFlag(1);
            List<SysNutritionalVideo> list = sysNutritionalVideoService.selectSysNutritionalVideoList(sysNutritionalVideo);
            return getDataTable(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据视频id获取播放链接
     */
    @GetMapping(value = "/getVideoDetailById")
    public AjaxResult getVideoDetailById(@RequestParam(value = "videoId") String videoId) {
        AjaxResult result = AjaxResult.success();
        NutritionalVideoResponse nutritionalVideoResponse = new NutritionalVideoResponse();
        try{
            SysNutritionalVideo sysNutritionalVideo = sysNutritionalVideoService.selectSysNutritionalVideByVideoId(videoId);
            if(sysNutritionalVideo != null){
                GetPlayInfoResponseBody playInfoResponseBody = AliyunVideoUtils.getVideoVisitDetail(videoId);
                //GetVideoInfoResponseBody videoInfoResponseBody = AliyunVideoUtils.getVideoById(videoId);
                List<GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo> playList = playInfoResponseBody.playInfoList.playInfo;
                if(playList != null && playList.size() > 0){
                    nutritionalVideoResponse.setPlayUrl(playList.get(0).getPlayURL());
                }
                nutritionalVideoResponse.setDescription(sysNutritionalVideo.getDescription());
                nutritionalVideoResponse.setTags(sysNutritionalVideo.getTags());
                nutritionalVideoResponse.setTitle(sysNutritionalVideo.getTitle());
                nutritionalVideoResponse.setCreateTime(DateUtils.dateTime(sysNutritionalVideo.getCreateTime()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        result.put("videoDetail", nutritionalVideoResponse);
        return result;
    }
}
