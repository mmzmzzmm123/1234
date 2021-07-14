package com.stdiet.web.controller.custom;

import com.aliyun.vod20170321.models.GetPlayInfoResponseBody;
import com.stdiet.common.constant.HttpStatus;
import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.core.domain.entity.SysDictData;
import com.stdiet.common.core.page.TableDataInfo;
import com.stdiet.common.exception.file.FileNameLengthLimitExceededException;
import com.stdiet.common.utils.AliyunVideoUtils;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.file.FileUploadUtils;
import com.stdiet.common.utils.file.MimeTypeUtils;
import com.stdiet.common.utils.oss.AliyunOSSUtils;
import com.stdiet.common.utils.sign.AesUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.response.*;
import com.stdiet.custom.page.WxLogInfo;
import com.stdiet.custom.service.*;
import com.stdiet.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 微信小程序统一Controller
 */
@RestController
@RequestMapping("/wx/applet")
public class WechatAppletController extends BaseController {


    public static final String[] imageName = {"breakfastImages", "lunchImages", "dinnerImages", "extraMealImages", "bodyImages"};
    @Autowired
    ISysRecipesService iSysRecipesService;
    @Autowired
    ISysNutritionalVideoService iSysNutritionalVideoService;
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
    private ISysCustomerService iSysCustomerService;
    @Autowired
    private ISysMessageNoticeService sysMessageNoticeService;
    @Autowired
    private ISysCustomerService sysCustomerService;
    @Autowired
    private ISysNutritionalVideoService sysNutritionalVideoService;
    @Autowired
    private ISysRecipesPlanService sysRecipesPlanService;
    @Autowired
    private ISysOrderPauseService sysOrderPauseService;
    @Autowired
    private ISysDictTypeService iSysDictTypeService;
    @Autowired
    private IWechatAppletService iWechatAppletService;
    @Autowired
    private ISysServicesTopicService iSysServicesTopicService;
    @Autowired
    private ISysVideoClassifyService sysVideoClassifyService;

    @Autowired
    private ISysWxBannerImageService sysWxBannerImageService;

    /**
     * 查询微信小程序中展示的客户案例
     */
    @GetMapping("/caseList")
    public TableDataInfo caseList(SysCustomerCase sysCustomerCase, @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                  @RequestParam(value = "randomFlag", required = false, defaultValue = "false") boolean randomFlag) {
        if (randomFlag) {
            List<CustomerCaseResponse> reponseList = sysCustomerCaseService.getWxCustomerCaseByRandom(pageSize);
            //处理ID加密
            dealIdEnc(reponseList);
            TableDataInfo rspData = new TableDataInfo();
            rspData.setCode(HttpStatus.SUCCESS);
            rspData.setMsg("查询成功");
            rspData.setRows(reponseList);
            rspData.setTotal(pageSize);
            return rspData;
        } else {
            startPage();
            sysCustomerCase.setKeywordArray(StringUtils.isNotEmpty(sysCustomerCase.getKeyword()) ? sysCustomerCase.getKeyword().split(",") : null);
            List<CustomerCaseResponse> list = sysCustomerCaseService.getWxCustomerCaseList(sysCustomerCase);
            //处理ID加密
            dealIdEnc(list);
            return getDataTable(list);
        }
    }

    /**
     * 查询客户案例文件列表
     */
    @GetMapping("/getFileByCaseId")
    public AjaxResult getFileByCaseId(@RequestParam("caseId") String caseId) {
        Long id = StringUtils.isNotEmpty(caseId) ? Long.parseLong(AesUtils.decrypt(caseId, null)) : -1L;
        CustomerCaseResponse customerCaseResponse = new CustomerCaseResponse();
        customerCaseResponse.setId(caseId);
        if (id != -1) {
            List<SysCustomerCaseFile> list = sysCustomerCaseService.getFileListByCaseId(id);
            List<String> fileUrl = new ArrayList<>();
            for (SysCustomerCaseFile caseFile : list) {
                fileUrl.add(caseFile.getFileUrl());
            }
            List<String> downUrlList = AliyunOSSUtils.generatePresignedUrl(fileUrl);
            customerCaseResponse.setFileList(downUrlList);
        } else {
            customerCaseResponse.setFileList(new ArrayList<>());
        }
        return AjaxResult.success(customerCaseResponse);
    }

    /**
     * 同步客户信息
     *
     * @param sysWxUserInfo
     * @return
     */
    @PostMapping("/synchroCustomerInfo")
    public AjaxResult synchroCustomerInfo(@RequestBody SysWxUserInfo sysWxUserInfo) {
        if (StringUtils.isEmpty(sysWxUserInfo.getOpenid()) || StringUtils.isEmpty(sysWxUserInfo.getPhone())) {
            return AjaxResult.error("手机号为空");
        }
        //根据手机号查询返回用户加密ID
        SysCustomer customer = sysCustomerService.getCustomerByPhone(sysWxUserInfo.getPhone());
        //加密ID
        String customerEncId = null;
        if (customer != null) {
            sysWxUserInfo.setCusId(customer.getId());
            customerEncId = AesUtils.encrypt(customer.getId() + "", null);
        }
        // 查询微信用户
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserInfo.getOpenid());
        if (userInfo != null) {
            //更新数据
            sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo);
        } else {
            sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("customerId", customerEncId);
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
     *
     * @param sysWxUserLog
     * @return
     */
    @GetMapping(value = "/getPunchLogs")
    public AjaxResult getPunchLogs(SysWxUserLog sysWxUserLog) {
        if (StringUtils.isEmpty(sysWxUserLog.getPhone()) && StringUtils.isEmpty(sysWxUserLog.getOpenid())) {
            return AjaxResult.error(5001, "缺少参数");
        }
        //查询是否下单
        SysCustomer param = new SysCustomer();
        param.setPhone(sysWxUserLog.getPhone());
        int orderCount = sysOrderService.getOrderCountByCustomer(param);
        if (orderCount > 0) {
            Map<String, Object> result = new HashMap<>();
            //今日是否已打卡
            boolean isPunch = false;
            startPage();
            List<WxLogInfo> list = sysWxUserLogService.getWxLogInfoList(sysWxUserLog);
            if (list.size() > 0) {
                WxLogInfo lastLog = list.get(0);
                if (lastLog.getDate() != null && ChronoUnit.DAYS.between(DateUtils.stringToLocalDate(lastLog.getDate(), "yyyy-MM-dd"), LocalDate.now()) == 0) {
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
        } else {
            return AjaxResult.error(5002, "未查询到相关订单信息");
        }
    }

    /**
     * 今日是否打卡
     *
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
     *
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
        if (sysWxUserLog.getId() == null) {
            //查询今日是否已打卡
            if (sysWxUserLog.getLogTime() == null) {
                sysWxUserLog.setLogTime(new Date());
            }
            int count = sysWxUserLogService.checkWxLogInfoCount(sysWxUserLog);
            if (count > 0) {
                return AjaxResult.error("今日已打卡，不可重复打卡");
            }
            return toAjax(sysWxUserLogService.insertSysWxUserLog(sysWxUserLog));
        } else {
            return toAjax(sysWxUserLogService.updateSysWxUserLog(sysWxUserLog));
        }

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
        if (sysWxUserLog == null) {
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
        imageUrlMap.put(imageName[4], bodyImagesUrlList);

        //生成预览链接
        Map<String, List<String>> downUrlList = AliyunOSSUtils.generatePresignedUrl(imageUrlMap);

        sysWxUserLog.setBreakfastImagesUrl(downUrlList.get(imageName[0]));
        sysWxUserLog.setLunchImagesUrl(downUrlList.get(imageName[1]));
        sysWxUserLog.setDinnerImagesUrl(downUrlList.get(imageName[2]));
        sysWxUserLog.setExtraMealImagesUrl(downUrlList.get(imageName[3]));
        sysWxUserLog.setBodyImagesUrl(downUrlList.get(imageName[4]));

        List<String> allImagesList = new ArrayList<>();
        List<String> allUrlList = new ArrayList<>();

        for (String key : imageName) {
            if (!"bodyImages".equals(key)) {
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
     *
     * @param list
     * @return
     */
    private void dealIdEnc(List<CustomerCaseResponse> list) {
        if (list != null) {
            for (CustomerCaseResponse cus : list) {
                cus.setId(AesUtils.encrypt(cus.getId() + "", null));
            }
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
     *
     */
    /**
     * 获取小程序展示的营养小知识列表
     *
     * @param sysNutritionQuestion 查询参数对象
     * @param pageNum              分页当前页码
     * @param pageSize             分页每页数量
     * @param randomFlag           随机标识  true 随机获取指定pageSize数量的营养小知识，不支持分页、模糊查询  false 则正常查询
     * @return
     */
    @GetMapping(value = "/getNutritionQuestionList")
    public AjaxResult getNutritionQuestionList(SysNutritionQuestion sysNutritionQuestion, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                               @RequestParam(value = "randomFlag", required = false, defaultValue = "false") boolean randomFlag) {
        sysNutritionQuestion.setShowFlag(1);
        if (randomFlag) {
            //随机获取指定条数
            sysNutritionQuestion.setKey(null);
            List<NutritionQuestionResponse> list = sysNutritionQuestionService.getNutritionQuestionByRandom(sysNutritionQuestion, pageSize);
            return AjaxResult.success(list);
        } else {
            Map<String, Object> result = sysNutritionQuestionService.getNutritionQuestionListByKey(sysNutritionQuestion, pageNum, pageSize);
            return AjaxResult.success(result);
        }
    }

    /**
     * 新增营养小知识提问
     */
    @PostMapping("/addAskNutritionQuestion")
    public AjaxResult addAskNutritionQuestion(@RequestBody SysAskNutritionQuestion sysAskNutritionQuestion) {
        if (StringUtils.isEmpty(sysAskNutritionQuestion.getOpenid()) || StringUtils.isEmpty(sysAskNutritionQuestion.getQuestion())) {
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
        Long cusId = StringUtils.isNotEmpty(sysMessageNotice.getCustomerId()) ? Long.parseLong(AesUtils.decrypt(sysMessageNotice.getCustomerId())) : 0L;

        sysMessageNotice.setMessageCustomer(cusId);
        List<MessageNoticeResponse> list = sysMessageNoticeService.getCustomerMessage(sysMessageNotice);
        return getDataTable(list);
    }

    /**
     * 更新用户通知消息已读状态
     */
    @GetMapping(value = "/updateMessageReadStatus")
    public AjaxResult updateMessageReadStatus(@RequestParam("id") Long id) {
        SysMessageNotice sysMessageNotice = new SysMessageNotice();
        sysMessageNotice.setReadType(1);
        sysMessageNotice.setId(id);
        return toAjax(sysMessageNoticeService.updateSysMessageNotice(sysMessageNotice));
    }

    /**
     * 获取视频列表
     */
    @GetMapping(value = "/getVideoList")
    public TableDataInfo getVideoList(SysNutritionalVideo sysNutritionalVideo) {
        sysNutritionalVideo.setShowFlag(1);
        sysNutritionalVideo.setSortType(2);
        //普通用户
        sysNutritionalVideo.setUserType(0);
        if (StringUtils.isNotEmpty(sysNutritionalVideo.getOpenId())) {
            //查询是否为客户，存在订单就视为客户
            int orderNum = sysOrderService.getOrderCountByOpenId(sysNutritionalVideo.getOpenId());
            if (orderNum > 0) {
                sysNutritionalVideo.setUserType(1);
            }
        }
        if (sysNutritionalVideo.getCateId() != null && sysNutritionalVideo.getCateId().longValue() == 0) {
            sysNutritionalVideo.setCateId(null);
        }
        startPage();
        List<SysNutritionalVideo> list = sysNutritionalVideoService.selectSysNutritionalVideoList(sysNutritionalVideo, true);
        return getDataTable(list);
    }


    /**
     * 根据视频id获取播放链接
     */
    @GetMapping(value = "/getVideoDetailById")
    public AjaxResult getVideoDetailById(@RequestParam(value = "videoId") String videoId) {
        NutritionalVideoResponse nutritionalVideoResponse = new NutritionalVideoResponse();
        try {
            SysNutritionalVideo sysNutritionalVideo = sysNutritionalVideoService.selectSysNutritionalVideByVideoId(videoId);
            if (sysNutritionalVideo != null) {
                GetPlayInfoResponseBody playInfoResponseBody = AliyunVideoUtils.getVideoVisitDetail(videoId);
                List<GetPlayInfoResponseBody.GetPlayInfoResponseBodyPlayInfoListPlayInfo> playList = playInfoResponseBody.playInfoList.playInfo;
                if (playList != null && playList.size() > 0) {
                    nutritionalVideoResponse.setPlayUrl(playList.get(0).getPlayURL());
                }
                if (StringUtils.isNotEmpty(sysNutritionalVideo.getCoverUrl())) {
                    nutritionalVideoResponse.setCoverUrl(AliyunOSSUtils.generatePresignedUrl(sysNutritionalVideo.getCoverUrl()));
                } else {
                    nutritionalVideoResponse.setCoverUrl(AliyunVideoUtils.getVideoCoverUrl(videoId));
                }
                nutritionalVideoResponse.setDescription(sysNutritionalVideo.getDescription());
                nutritionalVideoResponse.setTags(sysNutritionalVideo.getTags());
                nutritionalVideoResponse.setTitle(sysNutritionalVideo.getTitle());
                nutritionalVideoResponse.setPlayNum(sysNutritionalVideo.getPlayNum());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(nutritionalVideoResponse);
    }

    /**
     * 更新播放次数
     */
    @GetMapping(value = "/updateVideoPlayNum")
    public AjaxResult updateVideoPlayNum(@RequestParam(value = "videoId") String videoId) {
        AjaxResult result = AjaxResult.error();
        if (sysNutritionalVideoService.updateVideoPlayNum(videoId) > 0) {
            result = AjaxResult.success();
        }
        return result;
    }

    @PostMapping("/login")
    public AjaxResult login(@RequestBody SysWxUserInfo sysWxUserInfo) {
        if (StringUtils.isEmpty(sysWxUserInfo.getOpenid())) {
            return AjaxResult.error("缺少参数：openid");
        }
        SysWxUserInfo curWxUserInfo = sysWxUserInfoService.selectSysWxUserInfoById(sysWxUserInfo.getOpenid());

        // 第一次尝试匹配，搜索sys_wx_user_info表，尝试查找cus_id，此时不需要手机号
        if (StringUtils.isEmpty(sysWxUserInfo.getPhone()) && (StringUtils.isNull(curWxUserInfo) || StringUtils.isNull(curWxUserInfo.getCusId()))) {
            // 如果没找到任何信息，此时为未登录过的新用户 || 没有cusId，没有创建用户信息
            return AjaxResult.error(5000, "需要手机号进一步匹配");
        }

        // 第二次尝试匹配，带上手机号，先去sys_customer查找，找到就更新sys_wx_user_info表，并返回一系列登录后的数据
        if (StringUtils.isNotEmpty(sysWxUserInfo.getPhone())) {
            SysCustomer sysCustomer = iSysCustomerService.getCustomerByPhone(sysWxUserInfo.getPhone());
            if (StringUtils.isNull(sysCustomer)) {
//                return AjaxResult.error(5003, "未查到用户信息，请联系销售顾问");
                // 创建新客户
                sysCustomer = new SysCustomer();
                sysCustomer.setName(sysWxUserInfo.getNickName());
                sysCustomer.setPhone(sysWxUserInfo.getPhone());
                sysCustomer.setUpdateTime(DateUtils.getNowDate());
                sysCustomer.setCreateTime(DateUtils.getNowDate());
                iSysCustomerService.insertSysCustomer(sysCustomer);
            }

            sysWxUserInfo.setCusId(sysCustomer.getId());
            sysWxUserInfo.setUpdateTime(DateUtils.getNowDate());
            if (StringUtils.isNull(curWxUserInfo)) {
                // 新增sys_wx_user_info
                sysWxUserInfo.setCreateTime(DateUtils.getNowDate());
                sysWxUserInfoService.insertSysWxUserInfo(sysWxUserInfo);
            } else {
                // 更新sys_wx_user_info数据，
                sysWxUserInfoService.updateSysWxUserInfo(sysWxUserInfo);
            }
            // 更新对象
            curWxUserInfo = sysWxUserInfo;
        }

        // 更新时间超过7天，重新登录获取最新信息
        if (StringUtils.isEmpty(curWxUserInfo.getAvatarUrl()) || ChronoUnit.DAYS.between(DateUtils.dateToLocalDate(curWxUserInfo.getUpdateTime()), LocalDate.now()) >= 7) {
            return AjaxResult.error(5001, "信息缺失或者过期需要重新登录");
        }

        curWxUserInfo.setCustomerId(AesUtils.encrypt(curWxUserInfo.getCusId().toString()));
        curWxUserInfo.setCusId(null);

        // 并返回一系列登录后的数据
        return AjaxResult.success(curWxUserInfo);
    }

    @GetMapping("/getRecipesPlans")
    public AjaxResult getRecipesPlans(@RequestParam String customerId, @RequestParam String openid) {
        Long cusId = 0L;
        if (StringUtils.isNull(customerId)) {
            SysWxUserInfo wxUserInfo = sysWxUserInfoService.selectSysWxUserInfoById(openid);
            cusId = StringUtils.isNotNull(wxUserInfo) ? wxUserInfo.getCusId() : 0L;
        } else {
            cusId = StringUtils.isNotEmpty(customerId) ? Long.parseLong(AesUtils.decrypt(customerId)) : 0L;
        }
        if (cusId == 0L) {
            return AjaxResult.error(5000, "需要手机号进一步匹配");
        }

        List<SysRecipesPlanListInfo> plans = sysRecipesPlanService.selectRecipesPlanListInfoByCusId(cusId);

        SysRecipesPlanListInfo tmpPlan;
        for (int i = 0; i < plans.size(); i++) {
            tmpPlan = plans.get(i);
            tmpPlan.setStatus(1);
            if (StringUtils.isNull(tmpPlan.getSendFlag()) || tmpPlan.getSendFlag() == 0) {
                tmpPlan.setSendFlag(0);
                tmpPlan.setMenus(new ArrayList<>());
            }
            // 从excel转到线上的客户，之前的食谱无效
            if (i > 0 && plans.get(i - 1).getRecipesId() == null && i < plans.size() - 1 && plans.get(i + 1).getRecipesId() != null) {
                for (int j = 0; j < i; j++) {
                    plans.get(j).setStatus(0);
                }
            }
        }

        SysOrderPause orderPause = new SysOrderPause();
        orderPause.setCusId(cusId);
        List<SysOrderPause> pauses = sysOrderPauseService.selectSysOrderPauseList(orderPause);

        Map<String, Object> result = new HashMap<>();
        result.put("plans", plans);
        result.put("pauses", pauses);

        return AjaxResult.success(result);
    }

    @GetMapping("/getDicts")
    public AjaxResult getDicts() {

        List<SysDictData> unitDict = iSysDictTypeService.selectDictDataByType("cus_cus_unit");
        List<SysDictData> weightDict = iSysDictTypeService.selectDictDataByType("cus_cus_weight");
        List<SysDictData> menuTypeDict = iSysDictTypeService.selectDictDataByType("cus_dishes_type");
        List<SysDictData> igdUnitDict = iSysDictTypeService.selectDictDataByType("sys_ingredient_unit");
        List<SysDictData> idgTypeDict = iSysDictTypeService.selectDictDataByType("cus_ing_type");

        Map<String, Object> result = new HashMap<>();
        result.put("unitDict", unitDict);
        result.put("weightDict", weightDict);
        result.put("menuTypeDict", menuTypeDict);
        result.put("igdUnitDict", igdUnitDict);
        result.put("idgTypeDict", idgTypeDict);

        return AjaxResult.success(result);
    }

    @GetMapping("/getRecipesDetail")
    public AjaxResult getRecipesDetail(@RequestParam Long menuId) {
        return AjaxResult.success(iSysRecipesService.selectDishesByMenuIdShow(menuId));
    }

    @GetMapping("/getPlanDetail")
    public AjaxResult getPlanDetail(@RequestParam Long planId) {
        return AjaxResult.success(iSysRecipesService.selectSysRecipesByRecipesIdShow(planId));
    }

    @GetMapping("/authServer")
    public String authServer(HttpServletRequest request) {
//        String token = "shengtang";
//        String encodingAESKey = "i483gAY6KM0Ty5ij245U08jLhROxateWvg0LPjYLprS";
        return request.getParameter("echostr");
    }

//    @GetMapping("/getToken")
//    public String getToken(@RequestParam String appId) {
//        return iWechatAppletService.getAccessToken(appId);
//    }

    @GetMapping("/trans")
    public AjaxResult transId(@RequestParam String customerId, @RequestParam Long cusId) {
        if (StringUtils.isNotEmpty(customerId)) {
            return AjaxResult.success(AesUtils.decrypt(customerId));
        }

        return AjaxResult.success(AesUtils.encrypt(String.valueOf(cusId)));
    }

    @GetMapping("/subscribe")
    public AjaxResult subscribe(@RequestParam String customerId, @RequestParam Long planId, @RequestParam Integer subscribed) {
//        Long cusId = StringUtils.isNotEmpty(customerId) ? Long.parseLong(AesUtils.decrypt(customerId)) : 0L;

        SysRecipesPlan info = new SysRecipesPlan();
        info.setId(planId);
        info.setSubscribed(subscribed);

        return AjaxResult.success(sysRecipesPlanService.updateSysRecipesPlan(info));
    }


    /**
     * 主题列表
     *
     * @param customerId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/services/topic/list")
    public TableDataInfo fetchServiceQuestion(@RequestParam String customerId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        startPage();

        String cusId = StringUtils.isNotEmpty(customerId) ? AesUtils.decrypt(customerId) : "0";

        SysServicesTopic topic = new SysServicesTopic();
        topic.setRole("customer");
        topic.setUid(cusId);

        List<SysServicesTopic> list = iSysServicesTopicService.selectSysServicesTopicByUserIdAndRole(topic);
        if (StringUtils.isNotNull(list)) {
            for (SysServicesTopic tTopic : list) {
                tTopic.setUid(AesUtils.encrypt(tTopic.getUid()));
            }
        }

        return getDataTable(list);
    }

    /**
     * 客户添加问题
     *
     * @param topic
     * @param customerId
     * @return
     */
    @PostMapping("/services/topic/post")
    public AjaxResult postServiceTopic(@RequestBody SysServicesTopic topic, @RequestParam String customerId) {
        String cusId = StringUtils.isNotEmpty(customerId) ? AesUtils.decrypt(customerId) : "0";
        if (StringUtils.isEmpty(cusId)) {
            return toAjax(0);
        }
        topic.setUid(cusId);

        return AjaxResult.success(iSysServicesTopicService.insertSysServicesTopic(topic));
    }


    /**
     * 回答问题
     *
     * @param topic
     * @return
     */
    @PostMapping("/services/topic/reply")
    public AjaxResult replyServiceTopic(@RequestBody SysServicesTopic topic, @RequestParam Integer type) {

        topic.setFromUid(AesUtils.decrypt(topic.getFromUid()));
        topic.setToUid(AesUtils.decrypt(topic.getToUid()));

        SysServicesTopic servicesTopic = type == 0 ? iSysServicesTopicService.inserSysServicesTopicComment(topic) : iSysServicesTopicService.inserSysServicesTopicReply(topic);

        return AjaxResult.success(servicesTopic);
    }

    /**
     * 获取问题详情
     *
     * @param id
     * @return
     */
    @GetMapping("/services/topic/detail")
    public AjaxResult serviceTopicDetail(@RequestParam String topicId, @RequestParam String id) {
        List<SysServicesTopic> topics = iSysServicesTopicService.selectSysServicesTopicSessionByTopicId(topicId);
        if (StringUtils.isNotNull(topics)) {
            SysServicesTopic status = new SysServicesTopic();
            status.setId(id);
            status.setRead(1);
            iSysServicesTopicService.updateSysServicesTopicStatus(status);

            for (SysServicesTopic topic : topics) {
                topic.setUid(AesUtils.encrypt(topic.getUid()));
                if (StringUtils.isNotNull(topic.getComments())) {
                    for (SysServicesTopic cTopic : topic.getComments()) {
                        cTopic.setFromUid(AesUtils.encrypt(cTopic.getFromUid()));
                        cTopic.setToUid(AesUtils.encrypt(cTopic.getToUid()));
                        if (StringUtils.isNotNull(cTopic.getReplys())) {
                            for (SysServicesTopic rTopic : cTopic.getReplys()) {
                                rTopic.setFromUid(AesUtils.encrypt(rTopic.getFromUid()));
                                rTopic.setToUid(AesUtils.encrypt(rTopic.getToUid()));
                            }
                        }
                    }
                }
            }
        }
        return AjaxResult.success(topics);
    }


//    @GetMapping("/services/post/update")
//    public AjaxResult updateServiceQuestion(@RequestParam Long id) {
//        SysServicesQuestion servicesQuestion = new SysServicesQuestion();
//        servicesQuestion.setRead(1);
//        servicesQuestion.setId(id);
//
//        return toAjax(iSysServicesQuestionService.updateSysServicesQuestionStatus(servicesQuestion));
//    }


    /**
     * 获取视频分类
     *
     * @param openid
     * @return
     */
    @GetMapping("/getVideoClassify")
    public AjaxResult getVideoClassify(@RequestParam("openid") String openid) {
        SysVideoClassify param = new SysVideoClassify();
        param.setParentId(0L);
        List<SysVideoClassify> list = sysVideoClassifyService.getAllClassify(param);
        List<List<Map<String, Object>>> result = new ArrayList<>();
        int groupCount = 3;
        SysVideoClassify all = new SysVideoClassify();
        all.setId(0L);
        all.setCateName("全部内容");
        list.add(0, all);
        if (list != null && list.size() > 0) {
            //分成三组
            int groupNum = list.size() / groupCount + (list.size() % groupCount > 0 ? 1 : 0);
            for (int i = 0; i < groupNum; i++) {
                List<Map<String, Object>> groupList = new ArrayList<>();
                for (int j = i * groupCount; j < (i + 1) * groupCount; j++) {
                    if (j < list.size()) {
                        Map<String, Object> videoClassifyMap = new HashMap<>();
                        SysVideoClassify sysVideoClassify = list.get(j);
                        videoClassifyMap.put("cateName", sysVideoClassify.getCateName());
                        videoClassifyMap.put("cateId", sysVideoClassify.getId());
                        groupList.add(videoClassifyMap);
                    } else {
                        break;
                    }
                }
                result.add(groupList);
            }
        }
        return AjaxResult.success(result);
    }


    /**
     * 查询打卡社区
     *
     * @return
     */
    @GetMapping("/getCommunityPunch")
    public TableDataInfo getCommunityPunch() {
        startPage();
        List<CommunityPunchReponse> list = sysWxUserLogService.getCommunityPunch(new SysWxUserLog());
        return getDataTable(list);
    }

    /**
     * 修改健康减脂宣言
     *
     * @return
     */
    @PostMapping("/updateHealthManifesto")
    public AjaxResult updateHealthManifesto(@RequestParam("cusId") String cusId, @RequestParam("healthManifesto") String healthManifesto) {
        if (StringUtils.isEmpty(healthManifesto, cusId)) {
            return AjaxResult.error("缺少必要参数");
        }
        cusId = AesUtils.decrypt(cusId);
        if (cusId == null) {
            return AjaxResult.error("参数不合法");
        }
        if (healthManifesto.length() > 200) {
            return AjaxResult.error("健康宣言字数过长");
        }
        SysWxUserInfo sysWxUserInfo = new SysWxUserInfo();
        sysWxUserInfo.setCusId(Long.parseLong(cusId));
        sysWxUserInfo.setHealthManifesto(healthManifesto);
        return toAjax(sysWxUserInfoService.updateHealthManifestoByCusId(sysWxUserInfo));
    }

    /**
     * 获取个人中心数据（健康宣言、消息条数、打卡社区总打卡次数、打卡次数）
     *
     * @return
     */
    @GetMapping("/getCustomerCenterInfo")
    public AjaxResult getCustomerCenterInfo(@RequestParam("cusId") String cusId) {
        if (StringUtils.isEmpty(cusId)) {
            return AjaxResult.error("缺少必要参数");
        }
        cusId = AesUtils.decrypt(cusId);
        if (cusId == null) {
            return AjaxResult.error("参数不合法");
        }
        AjaxResult result = AjaxResult.success();
        //获取健康宣言
        SysWxUserInfo sysWxUserInfo = sysWxUserInfoService.selectSysWxUserInfoByCusId(Long.parseLong(cusId));
        result.put("healthManifesto", sysWxUserInfo.getHealthManifesto() == null ? "" : sysWxUserInfo.getHealthManifesto());
        //获取未读消息条数
        SysMessageNotice messageParam = new SysMessageNotice();
        messageParam.setReadType(0);
        messageParam.setMessageCustomer(Long.parseLong(cusId));
        int unReadNoticeTotal = sysMessageNoticeService.getCustomerMessageCount(messageParam);
        result.put("unReadNoticeTotal", unReadNoticeTotal);
        //获取已打卡几次
        SysWxUserLog sysWxUserLog = new SysWxUserLog();
        sysWxUserLog.setCustomerId(Long.parseLong(cusId));
        int punchNum = sysWxUserLogService.getPunchTotalNum(sysWxUserLog);
        result.put("customerPunchNum", unReadNoticeTotal);
        //查询社区打卡客户数量
        int punchCustomerNum = sysWxUserLogService.getPunchCustomerTotalNum();
        result.put("punchCustomerNum", punchCustomerNum);
        return result;
    }

    /**
     * 获取需要在小程序展示的Banner图
     *
     * @return
     */
    @GetMapping("/getShowBannerImage")
    public AjaxResult getShowBannerImage() {
        SysWxBannerImage sysWxBannerImage = new SysWxBannerImage();
        //获取可以显示的Banner
        sysWxBannerImage.setShowFlag(1L);
        List<BannerResponse> list = sysWxBannerImageService.getBannerListOrderByOrderNum(sysWxBannerImage);
        return AjaxResult.success(list);
    }

    @GetMapping("/studyVideoClassify")
    public AjaxResult fetchStudyVideoClassify(@RequestParam String openid) {
        int payed = 0;
        SysWxUserInfo userInfo = sysWxUserInfoService.selectSysWxUserInfoById(openid);
        if (StringUtils.isNotNull(userInfo)) {
            List<SysOrder> orders = sysOrderService.getAllOrderByCusId(userInfo.getCusId());
            if (StringUtils.isNotEmpty(orders)) {
                payed = 1;
            }
        }

        SysVideoClassify sysVideoClassify = new SysVideoClassify();
        //父级分类ID 0表示主分类
        sysVideoClassify.setParentId(4l);
        //客户类型 0未付费客户-playLevel根据上传的标记  1已付费客户-playLevel全为0
        sysVideoClassify.setUserType(payed);
        List<VideoClassifyResponse> list = sysVideoClassifyService.getAllClassifyAndVideo(sysVideoClassify);

        String imgUrl = "https://stdiet.oss-cn-shenzhen.aliyuncs.com/videoCover/2021-07-12/";
        if (StringUtils.isNotNull(list)) {
            for (VideoClassifyResponse res : list) {
                List<VideoClassifyResponse> classifies = res.getChildrenClassify();
                if (StringUtils.isEmpty(classifies)) {
                    res.setImgUrl(AliyunOSSUtils.generatePresignedUrl(imgUrl + res.getId() + ".png"));
                } else {
                    for (VideoClassifyResponse twoRes : classifies) {
                        twoRes.setImgUrl(AliyunOSSUtils.generatePresignedUrl(imgUrl + twoRes.getId() + ".png"));
                    }
                }
            }
        }

        return AjaxResult.success(list);
    }

    @GetMapping("/studyVideoComments")
    public AjaxResult fetchStudyVideoComments(@RequestParam String openid, @RequestParam String topicId) {
        SysVideoComment videoComment = new SysVideoComment();
        videoComment.setTopicId(topicId);
        List<SysVideoComment> comments = iSysNutritionalVideoService.selectVideoCommentByTopicId(videoComment, openid);
        return AjaxResult.success(comments);
    }

    @PostMapping("/video/post/comment")
    public AjaxResult videoPostComment(@RequestBody SysVideoComment videoComment, @RequestParam String openid) {
        SysVideoComment comment = iSysNutritionalVideoService.insertVideoComment(videoComment);
        if (StringUtils.isNull(comment)) {
            return AjaxResult.error();
        }

        return AjaxResult.success(comment);
    }

    @PostMapping(value = "/video/post/reply")
    public AjaxResult videoPostReply(@RequestBody SysVideoComment videoComment, @RequestParam String openid) {
        SysVideoComment reply = iSysNutritionalVideoService.insertVideoCommentReply(videoComment);

        if (StringUtils.isNull(reply)) {
            return AjaxResult.error();
        }

        return AjaxResult.success(reply);
    }
}


