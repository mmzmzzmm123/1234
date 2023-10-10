package com.ruoyi.office.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.binarywang.wxpay.bean.notify.OriginNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.MarketingFavorService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.service.*;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.system.service.ISysNoticeService;
import com.ruoyi.tuangou.service.TuangouService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.ServerError;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ruoyi.common.utils.PageUtils.startPage;

@RestController
@RequestMapping("/office/api")
public class ApiController extends BaseController {
    protected org.apache.commons.logging.Log log = LogFactory.getLog(getClass());

    @Autowired
    ITRoomOrderService roomOrderService;

    @Autowired
    WxPayService wxPayService;

    @Autowired
    ITWxUserPackageService userPackageService;

    @Autowired
    TuangouService tuangouService;

    @Resource(name = "customerWxMaService")
    WxMaService customerWxMaService;

    @Autowired
    ITStorePromotionService tStorePromotionService;

    @Autowired
    ITJoinUsService itJoinUsService;

    @Autowired
    ITWxUserCouponService itWxUserCouponService;

    /**
     * 预定成功 api 回调
     */
    @ApiOperation("支付成功 api 回调")
    @Log(title = "支付成功 api 回调")
    @PostMapping("/wxnotify")
    public JSONObject wxnotify(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) throws WxPayException {
        JSONObject wxPayResult = new JSONObject();
        Lock lock = new ReentrantLock();
        if (lock.tryLock()) {
            logger.info("微信支付回调 /wxnotify: " + jsonData);
            // 支付成功结果通知
            OriginNotifyResponse notifyResponse = JSON.parseObject(jsonData, OriginNotifyResponse.class);
            WxPayOrderNotifyV3Result v3Result = null;
            try {
                v3Result = wxPayService.parseOrderNotifyV3Result(jsonStrSort(notifyResponse), getRequestHeader(request));
                logger.info("回调结果解析" + v3Result.toString());
                //解密后的数据
                WxPayOrderNotifyV3Result.DecryptNotifyResult result = v3Result.getResult();

                logger.info("微信支付通知：" + result.toString());

                if (result.getTradeState().equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
                    if (result.getAttach().equalsIgnoreCase(OfficeEnum.WxTradeType.ROOM_ORDER.getCode()))
                        roomOrderService.wxnotify(result.getOutTradeNo(), result.getPayer().getOpenid(), result.getAmount().getTotal(), result.toString());
                    if (result.getAttach().equalsIgnoreCase(OfficeEnum.WxTradeType.PACK.getCode()))
                        userPackageService.wxNotify(result.getOutTradeNo(), result.getPayer().getOpenid(), result.getAmount().getTotal(), result.toString());
                }

                //通知应答：接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
                response.setStatus(HttpServletResponse.SC_OK);
                return null;
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("支付回调失败：" + e.getLocalizedMessage());
                // 通知应答：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                wxPayResult.fluentPut("code", "FAIL");
                wxPayResult.fluentPut("message", e.getMessage());
                return wxPayResult;
            } finally {
                lock.unlock();
            }
        }
        // 通知应答码：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        wxPayResult.fluentPut("code", "FAIL");
        wxPayResult.fluentPut("message", "失败");
        return wxPayResult;
    }

    @Autowired
    ITRoomOrderCancelAuditService cancelAuditService;

    /**
     * 预定成功 api 回调
     */
    @ApiOperation("退款申请 api 回调")
    @Log(title = "退款申请 api 回调")
    @PostMapping("/wxnotify/refund")
    public Object wxRefundNotify(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) throws WxPayException {
        JSONObject wxPayResult = new JSONObject();
        Lock lock = new ReentrantLock();
        if (lock.tryLock()) {
            // 支付成功结果通知
            OriginNotifyResponse notifyResponse = JSON.parseObject(jsonData, OriginNotifyResponse.class);
            WxPayRefundNotifyV3Result v3Result = null;
            try {
                v3Result = wxPayService.parseRefundNotifyV3Result(jsonStrSort(notifyResponse), getRequestHeader(request));

                //解密后的数据
                WxPayRefundNotifyV3Result.DecryptNotifyResult result = v3Result.getResult();

                if (result.getRefundStatus().equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
                    cancelAuditService.refundApproveNotify(result);
                }

                //通知应答：接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
                response.setStatus(HttpServletResponse.SC_OK);
                return null;
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("支付回调失败：" + e.getLocalizedMessage());
                // 通知应答：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                wxPayResult.fluentPut("code", "FAIL");
                wxPayResult.fluentPut("message", e.getMessage());
                return wxPayResult;
            } finally {
                lock.unlock();
            }
        }
        // 通知应答码：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        wxPayResult.fluentPut("code", "FAIL");
        wxPayResult.fluentPut("message", "失败");
        return wxPayResult;

    }

    /**
     * 请求报文：按官方接口示例键值 --- 排序(必须)
     * 官方文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_5.shtml，
     * https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
     * 《微信支付API v3签名验证》 中注意：应答主体（response Body），需要按照接口返回的顺序进行验签，错误的顺序将导致验签失败。
     *
     * @param originNotifyResponse OriginNotifyResponse
     * @return String
     */
    private String jsonStrSort(OriginNotifyResponse originNotifyResponse) {

        Map<String, Object> jsonSort = new LinkedHashMap<>();
        jsonSort.put("id", originNotifyResponse.getId());
        jsonSort.put("create_time", originNotifyResponse.getCreateTime());
        jsonSort.put("resource_type", originNotifyResponse.getResourceType());
        jsonSort.put("event_type", originNotifyResponse.getEventType());
        jsonSort.put("summary", originNotifyResponse.getSummary());
        Map<String, Object> resource = new LinkedHashMap();
        resource.put("original_type", originNotifyResponse.getResource().getOriginalType());
        resource.put("algorithm", originNotifyResponse.getResource().getAlgorithm());
        resource.put("ciphertext", originNotifyResponse.getResource().getCiphertext());
        resource.put("associated_data", originNotifyResponse.getResource().getAssociatedData());
        resource.put("nonce", originNotifyResponse.getResource().getNonce());
        jsonSort.put("resource", resource);
        return JSON.toJSONString(jsonSort);
    }


    /**
     * 获取回调请求头：签名相关
     *
     * @param request HttpServletRequest
     * @return SignatureHeader
     */
    public SignatureHeader getRequestHeader(HttpServletRequest request) {
        // 获取通知签名
        String signature = request.getHeader("wechatpay-signature");
        String nonce = request.getHeader("wechatpay-nonce");
        String serial = request.getHeader("wechatpay-serial");
        String timestamp = request.getHeader("wechatpay-timestamp");

        SignatureHeader signatureHeader = new SignatureHeader();
        signatureHeader.setSignature(signature);
        signatureHeader.setNonce(nonce);
        signatureHeader.setSerial(serial);
        signatureHeader.setTimeStamp(timestamp);
        return signatureHeader;
    }

    /**
     * 获取分享码
     *
     * @return
     */
    @ApiOperation("获取分享码")
    @GetMapping("/share")
    public AjaxResult getShareCode() {
        String unionId = SecurityUtils.getLoginUser().getWxUser().getUnionId();

        String url = null;

        url = "https://www.btjingling.com/#/pages/login/signup/signup?data=" + java.net.URLEncoder.encode(unionId);// new String(account.getBytes("UTF-8"), "ISO-8859-1");
//        result.setReCode(unionId);
//        result.setUrl(url);
////        result.setQRCode(QrCodeUtil.getQRCodePicBase64(url));
//        result.setQRCode(new String(QrCodeUtil.generatePng(url, new QrConfig())));

        return AjaxResult.success(new String(QrCodeUtil.generatePng(url, new QrConfig())));
    }

    public static void main(String[] args) {
        System.out.println(230 % 100);
        System.out.println(230 / 100 * 100);
    }

    @Autowired
    ITWxUserPromotionService userPromotionService;

    @Autowired
    ITStorePromotionService storePromotionService;

    /**
     * 获取可用优惠券
     */
    @ApiOperation("获取可领取优惠券 不传店铺id所有，传店铺id查看店铺")
    @GetMapping("/promotion")
    public AjaxResult availableGetPromotion(TStorePromotion store) {
        long userId = SecurityUtils.getLoginUser().getWxUser().getId();

        List<TStorePromotion> res = new ArrayList<>();

        List<TStorePromotion> storePromotions = storePromotionService.selectTStorePromotionList(store);
        List<TWxUserPromotion> userPromotions = userPromotionService.selectTWxUserPromotionList(new TWxUserPromotion());
        for (TStorePromotion storePromotion : storePromotions) {
            boolean ex = false;
            for (TWxUserPromotion userPromotion : userPromotions) {
                if (storePromotion.getId() == userPromotion.getCouponId()) {
                    ex = true;
                    break;
                }
            }
            if (!ex)
                res.add(storePromotion);
        }

        return AjaxResult.success(res);
    }

    /**
     * 领取优惠券
     *
     * @return
     */
    @ApiOperation("领取优惠券")
    @PostMapping("/promotion")
    public AjaxResult promotion(@RequestBody TStorePromotion tStorePromotion) {
        long userId = SecurityUtils.getLoginUser().getWxUser().getId();
        long promotionId = tStorePromotion.getId();

        userPromotionService.receivePromotion(userId, promotionId);

        return AjaxResult.success();
    }

    /**
     * 支付可用优惠券列表
     */
    @ApiOperation("支付可用优惠券列表")
    @GetMapping("/promotion/list")
    public TableDataInfo payAvailable(TWxUserPromotion tWxUserPromotion) {
        TWxUserPromotion promotion = new TWxUserPromotion();
        promotion.setWxId(SecurityUtils.getUserId());
        promotion.setStatus("未使用");
        startPage();
        List<TWxUserPromotion> list = userPromotionService.selectPayAvailableList(promotion);
        return getDataTable(list);
    }

    /**
     * topic 模式
     *
     * @param msg 消息
     * @return
     */
    @PostMapping("/topic/{msg}")
    public String sendTopic(@PathVariable("msg") String msg) {
        MqttSendClient sendClient = new MqttSendClient();
        sendClient.publish("test_queue", msg);
        return "success";
    }

    @Autowired
    ITStoreService storeService;

    /**
     * wx店铺列表
     */
    @ApiOperation("wx店铺列表")
    @GetMapping("/store/list")
    public TableDataInfo wxStoreList(WxStoreListQryVo wxStoreListQryVo) {
        startPage();
        List<WxStoreListRspVo> list = storeService.selectWxStoreList(wxStoreListQryVo, SecurityUtils.getLoginUser().getWxUser().getId());
        return getDataTable(list);
    }

    @Autowired
    ITEquipmentService equipmentService;

    /**
     * 开门禁
     *
     * @param id
     * @return
     */
    @ApiOperation("开门禁")
    @PostMapping("/store/{id}")
    public AjaxResult openStore(@PathVariable("id") Long id) {

        try {
            storeService.openStore(id);
        } catch (Exception e) {
            throw new ServiceException("操作异常，请联系管理员");
        }

        return AjaxResult.success();
    }

    /**
     * 开门禁
     *
     * @param id
     * @return
     */
    @ApiOperation("开门禁")
    @PostMapping("/openStore/{id}")
    public AjaxResult openStoreByStoreId(@PathVariable("id") Long id) {
        try {
            storeService.openStoreByStoreId(id);
        } catch (Exception e) {
            throw new ServiceException("操作异常，请联系管理员");
        }

        return AjaxResult.success();
    }

    @Autowired
    ITRoomService roomService;

    /**
     * 开门禁
     *
     * @param orderId
     * @return
     */
    @ApiOperation("开房间设备")
    @PostMapping("/room/{orderId}")
    public AjaxResult openRoom(@PathVariable("orderId") Long orderId) {

        try {
            // 判断是否到达开门允许的事件范围
            SysDictData dictData = new SysDictData();
            dictData.setDictLabel("open_room");
            final List<SysDictData> sysDictData = dictDataService.selectDictDataList(dictData);
            int minutes = 15;
            if (sysDictData.size() > 0) {
                minutes = Integer.parseInt(sysDictData.get(0).getDictValue());
            }
            TRoomOrder roomOrder = roomOrderService.selectTRoomOrderById(orderId);
            long diff = (roomOrder.getStartTime().getTime() - new Date().getTime()) / 60000;
            if (diff > minutes)
                return AjaxResult.error("订单开始前" + minutes + "分钟才可以开门");

            roomService.openRoom(roomOrder.getRoomId());
            TRoomOrder upOrder = new TRoomOrder();
            upOrder.setId(orderId);
            upOrder.setStatus(3);// 使用中
            roomOrderService.updateTRoomOrder(upOrder);
            TRoom room = new TRoom();
            room.setId(roomOrder.getRoomId());
            room.setStatus("3");// 使用中
            roomService.updateTRoom(room);
        } catch (Exception e) {
            return AjaxResult.error("操作异常，请联系管理员：" + e.getMessage());
        }

        return AjaxResult.success();
    }

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @ApiOperation("wx订单记录")
    @GetMapping("/room/order")
    public TableDataInfo order(RoomOrderWxReqVo tRoomOrder) {
        tRoomOrder.setCreateBy(SecurityUtils.getLoginUser().getWxUser().getId() + "");
        startPage();
        List<RoomOrderWxVo> list = roomOrderService.getWxRoomOrder(tRoomOrder);
        return getDataTable(list);
    }

    @Autowired
    private ITRoomService tRoomService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ITRoomPackageService tRoomPackageService;

    /**
     * 查询店铺房间列表
     */
    @ApiOperation("wx房间列表")
    @GetMapping("/room/list")
    public TableDataInfo room(TRoom tRoom) {
        List<RoomWxVo> res = new ArrayList<>();
        startPage();
        List<TRoom> list = tRoomService.selectTRoomList(tRoom);

        SysDictData dict = new SysDictData();
        dict.setDictType("room_mark");
        Map<String, String> dictMap = dictDataService.selectDictDataList(dict).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));

        SysDictData dict1 = new SysDictData();
        dict1.setDictType("room_status");
        Map<String, String> roomStatusMap = dictDataService.selectDictDataList(dict1).stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));


        final Map<Long, List<TRoomPrice>> roomPriceGroup = tRoomPriceService.selectTRoomPriceList(new TRoomPrice()).stream().collect(Collectors.groupingBy(TRoomPrice::getRoomId));

        final Map<Long, List<TRoomPackage>> roomPackageGroup = tRoomPackageService.selectTRoomPackageList(new TRoomPackage()).stream().collect(Collectors.groupingBy(TRoomPackage::getRoomId));

        for (TRoom room : list) {
            RoomWxVo vo = new RoomWxVo();
            BeanUtils.copyProperties(room, vo);

            vo.setStatus(roomStatusMap.get(vo.getStatus()));

            String roomMark = "";
            if (StringUtils.isEmpty(room.getRemark()))
                roomMark = ", ";
            for (String mark : room.getRemark().split(",")) {
                roomMark += "," + dictMap.get(mark);
            }

            vo.setRemark(roomMark.substring(1));
            vo.setPriceList(roomPriceGroup.get(room.getId()));
            vo.setPackageList(roomPackageGroup.get(room.getId()));

            RoomAvailablePeriod qry = new RoomAvailablePeriod();
            qry.setRoomId(room.getId());
            qry.setDate(DateUtils.parseDate(DateUtils.getDate()));
            final RoomAvailablePeriod availablePeriod = roomOrderService.getAvailablePeriod(qry);

            vo.setPeriod(availablePeriod);
            res.add(vo);
        }
        return getDataTable(res);
    }


    @Autowired
    private ITRoomPriceService tRoomPriceService;

    /**
     * 查询房间价格列表
     */
    @ApiOperation("wx房间价格列表")
    @GetMapping("/price/list")
    public TableDataInfo list(TRoomPrice tRoomPrice) {
        if (tRoomPrice.getRoomId() == null || tRoomPrice.getRoomId() == 0) {
            throw new ServiceException("请补充房间编号");
        }
        startPage();
        List<TRoomPrice> list = tRoomPriceService.selectTRoomPriceList(tRoomPrice);
        return getDataTable(list);
    }

    /**
     * 获取房间时间段价格
     */
    @ApiOperation(value = "获取房间已占用时间段")
    @GetMapping(value = "/room/available")
    public AjaxResult getAvailablePeriod(RoomAvailablePeriod vo) {
        final RoomAvailablePeriod availablePeriod = roomOrderService.getAvailablePeriod(vo);
        return success(availablePeriod);
    }

    /**
     * @param code
     * @return
     */
    @ApiOperation("美团验券")
    @GetMapping("/meiTuanConsume/{code}")
    public Boolean MeituanConsume(@PathVariable("code") String code) throws ServiceException {
        Long userId = SecurityUtils.getLoginUser().getWxUser().getId();
        tuangouService.test(code, userId);
        return true;
    }


    @Autowired
    private ITWxUserAmountService tWxUserAmountService;

    /**
     * 查询微信用户余额
     */
    @ApiOperation("微信用户余额,merchant=0时，用户所有商户的余额列表")
    @GetMapping("/wxuser/{merchant}")
    public TableDataInfo wxuser(@PathVariable("merchant") long merchant) {
        TWxUserAmount wxUserAmount = new TWxUserAmount();
        wxUserAmount.setWxUserId(SecurityUtils.getLoginUser().getWxUser().getId());
        if (merchant != 0) {
            final TStore store = storeService.selectTStoreById(merchant);
            wxUserAmount.setUserId(Long.parseLong(store.getCreateBy()));
        }
        startPage();
        List<TWxUserAmount> userAmount = tWxUserAmountService.selectTWxUserAmountList(wxUserAmount);
        return getDataTable(userAmount);
    }

    /**
     * 查询用户余额
     */
    @ApiOperation("查询用户余额")
    @GetMapping("/amount")
    public AjaxResult getTotalAmount() {
        long wxid = SecurityUtils.getLoginUser().getWxUser().getId();
        BigDecimal total = new BigDecimal(0);
        // 获取所有商户的余额进行汇总；
        TWxUserAmount qry = new TWxUserAmount();
        qry.setWxUserId(wxid);
        final List<TWxUserAmount> wxUserAmounts = tWxUserAmountService.selectTWxUserAmountList(qry);
        for (TWxUserAmount userAmount : wxUserAmounts) {
            total = total.add(userAmount.getAmount());
        }

        return AjaxResult.success(total);
    }

    /**
     * 根据门店id查询用户在该商户余额
     */
    @ApiOperation("根据门店id查询用户在该商户余额")
    @GetMapping("/amount/{storeId}")
    public AjaxResult getStoreAmount(@PathVariable("storeId") Long storeId) {
        BigDecimal total = new BigDecimal(0);
        // 根据storeId获取对应商户，查询商户余额
        final TStore store = storeService.selectTStoreById(storeId);

        TWxUserAmount qry = new TWxUserAmount();
        qry.setUserId(Long.parseLong(store.getCreateBy()));
        qry.setWxUserId(SecurityUtils.getLoginUser().getWxUser().getId());
//        qry.setWxUserId(9l);
        final List<TWxUserAmount> wxUserAmounts = tWxUserAmountService.selectTWxUserAmountList(qry);

        if (wxUserAmounts.size() == 0)
            return AjaxResult.success(0);
        return AjaxResult.success(wxUserAmounts.get(0).getAmount());
    }

    @Autowired
    ITStorePackageService storePackageService;

    /**
     * 地址、门店名称查询
     */
    @ApiOperation("地址、门店名称查询: 商户余额、店铺列表、充值套餐列表")
    @GetMapping("/store/{name}")
    public TableDataInfo getStoreAmount(@PathVariable("name") String name) {
        List<WxUserStoreInfo> userStoreInfo = new ArrayList<>();
        long wxuserid = SecurityUtils.getLoginUser().getWxUser().getId();

        startPage();

        TStore storeQry = new TStore();
        storeQry.setName(name);
        final List<TStore> storeList = storeService.selectTStoreList(storeQry);

        for (TStore store : storeList) {
            WxUserStoreInfo wxUserStoreInfo = new WxUserStoreInfo();
            BeanUtils.copyProperties(store, wxUserStoreInfo);

            long userId = Long.parseLong(store.getCreateBy());
            TStorePackage qry = new TStorePackage();
            qry.setCreateBy(store.getCreateBy());
            final List<TStorePackage> tStorePackages = storePackageService.selectTStorePackageList(qry);
            wxUserStoreInfo.setPackageList(tStorePackages);

            TWxUserAmount userAmountQry = new TWxUserAmount();
            userAmountQry.setWxUserId(wxuserid);
            userAmountQry.setUserId(userId);
            final List<TWxUserAmount> wxUserAmounts = tWxUserAmountService.selectTWxUserAmountList(userAmountQry);
            if (wxUserAmounts.size() > 0) {
                wxUserStoreInfo.setAmount(wxUserAmounts.get(0).getAmount());
            }

            userStoreInfo.add(wxUserStoreInfo);
        }

        return getDataTable(userStoreInfo);
    }

    @Autowired
    private ITWxUserPackageService tWxUserPackageService;

    /**
     * 用户充值记录
     */
    @ApiOperation("用户充值记录")
    @GetMapping("/user/package/list")
    public TableDataInfo userPackageList(TWxUserPackage tWxUserPackage) {
        tWxUserPackage.setCreateBy(SecurityUtils.getLoginUser().getWxUser().getId() + "");
        startPage();
        List<TWxUserPackage> list = tWxUserPackageService.selectTWxUserPackageList(tWxUserPackage);
        return getDataTable(list);
    }

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @ApiOperation("微信用户订单列表：payType： WX_PAY(1, \"微信支付\"), CARD_BALANCE_PAY(2, \"储值卡余额支付\"), COUPON_PAY(4, \"优惠券支付\")" +
            "status：状态： IDEAL(0, \"空闲\"), TO_PAY(1, \"待支付\"), ORDERED(2, \"已预约\"), USING(3, \"使用中\"),\n" +
            "        OVER_TIME(4, \"超时未使用\"), USED(5, \"已完成\"), CANCEL(9, \"取消\");")
    @GetMapping("/order/list")
    public TableDataInfo orderList(TRoomOrder tRoomOrder) {
        tRoomOrder.setCreateBy(SecurityUtils.getLoginUser().getWxUser().getId() + "");
        startPage();
        List<TRoomOrder> list = roomOrderService.selectTRoomOrderList(tRoomOrder);
        return getDataTable(list);
    }

    @ApiOperation("待支付订单支付")
    @Log(title = "待支付订单支付", businessType = BusinessType.INSERT)
    @PostMapping("/reorder")
    public AjaxResult reorder(@RequestBody ReorderPayReq prepayReq) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long wxUserId = 9l;
        try {
            final PrepayResp prepay = roomOrderService.reOrder(prepayReq, wxUserId);
            return AjaxResult.success(prepay);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("查询可以开门的订单,status不用")
    @GetMapping("/order/open")
    public TableDataInfo orderCanOpen(OrderCanOpenReq tRoomOrder) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
        tRoomOrder.setCreateBy(wxUserId + "");
        startPage();
        List<RoomOrderWxVo> res = roomOrderService.getOrderCanOpen(tRoomOrder);
        return getDataTable(res);
    }

    @ApiOperation("房间按时间段计算价格支付")
    @Log(title = "房间按时间段计算价格支付", businessType = BusinessType.INSERT)
    @PostMapping("/order")
    public AjaxResult order(@RequestBody PrepayReq order) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long wxUserId = 9l;
        order.setUserId(wxUserId);
        try {
            logger.info("/order:" + order.toString());
            final PrepayResp prepay = roomOrderService.prepay(order, wxUserId);
//            logger.info("/order: return:" + prepay.getOrderId() + prepay.getJsapiResult().toString());
            return AjaxResult.success(prepay);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("购买房间套餐")
    @Log(title = "购买房间套餐", businessType = BusinessType.INSERT)
    @PostMapping("/pack/order")
    public AjaxResult packOrder(@RequestBody PackPrepayReq order) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long wxUserId = 9l;
        order.setUserId(wxUserId);
        try {
            logger.info("/order:" + order.toString());
            final PrepayResp prepay = roomOrderService.packPrepay(order, wxUserId);
//            logger.info("/order: return:" + prepay.getOrderId() + prepay.getJsapiResult().toString());
            return AjaxResult.success(prepay);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @ApiOperation("优惠券预定房间")
    @Log(title = "优惠券预定房间", businessType = BusinessType.INSERT)
    @PostMapping("/promotion/order")
    public AjaxResult promotionOrder(@RequestBody PrepayReq order) {
        long wxUserId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long wxUserId = 9l;
        order.setUserId(wxUserId);
        try {
//            logger.info("/promotion/order:" + order.toString());
            final PrepayResp prepay = roomOrderService.promotionPrepay(order, wxUserId);
//            logger.info("/order: return:" + prepay.getOrderId() + prepay.getJsapiResult().toString());
            return AjaxResult.success(prepay);
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    @Autowired
    private ISysNoticeService noticeService;

    /**
     * 获取banner通知公告列表
     */
    @GetMapping("/notice/list")
    public TableDataInfo list(SysNotice notice) {
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    @Autowired
    private ITWxUserCouponService wxUserCouponService;

    /**
     * 查询用户优惠券列表
     */
    @GetMapping("/user/coupon/list")
    public TableDataInfo list(TWxUserCoupon tWxUserCoupon) {
        startPage();
        List<TWxUserCoupon> list = wxUserCouponService.selectTWxUserCouponList(tWxUserCoupon);
        return getDataTable(list);
    }


    /**
     * 查询可用优惠券
     */
    @ApiOperation("可用优惠券")
    @GetMapping("/validlist")
    public TableDataInfo validlist(WxUserCouponReq req) {
        startPage();
        List<WxUserCouponResp> list = wxUserCouponService.validlist(req, SecurityUtils.getLoginUser().getWxUser().getId());
        return getDataTable(list);
    }

    /**
     * 查询可用优惠券
     */
    @ApiOperation("可用优惠券数量")
    @GetMapping("/validCount")
    public AjaxResult validCount(WxUserCouponReq req) {
        List<WxUserCouponResp> list = wxUserCouponService.validlist(req, SecurityUtils.getLoginUser().getWxUser().getId());
        return AjaxResult.success(list.size());
    }

    /**
     * 查询可用优惠券
     */
    @ApiOperation("已使用优惠券")
    @GetMapping("/usedlist")
    public TableDataInfo usedlist(WxUserCouponReq req) {
        startPage();
        List<WxUserCouponResp> list = wxUserCouponService.usedlist(req, SecurityUtils.getLoginUser().getWxUser().getId());
        return getDataTable(list);
    }

    /**
     * 查询可用优惠券
     */
    @ApiOperation("不可用优惠券")
    @GetMapping("/invalid")
    public TableDataInfo invalid(WxUserCouponReq req) {
        startPage();
        List<WxUserCouponResp> list = wxUserCouponService.invalid(req, SecurityUtils.getLoginUser().getWxUser().getId());
        return getDataTable(list);
    }

    /**
     * 新增用户套餐购买记录
     */
    @Log(title = "用户套餐购买", businessType = BusinessType.INSERT)
    @PostMapping("/package/buy")
    public AjaxResult buy(@RequestBody BuyStorePackReq tWxUserPackage) {
        long userWxId = SecurityUtils.getLoginUser().getWxUser().getId();
//        long userWxId = 9l;
        final PrepayResp pre = tWxUserPackageService.buy(tWxUserPackage, userWxId);
        return AjaxResult.success(pre);
    }

    /**
     * 小程序使用微信使用prepayId支付成功后，回调通知
     */
    @ApiOperation("套餐支付查询")
    @Log(title = "套餐支付查询", businessType = BusinessType.INSERT)
    @PostMapping("/package/payquery")
    public AjaxResult wxPayQuery(@RequestBody PrepayResp vo) {
        try {
            logger.info("/payquery:" + vo.toString());
            WxPayOrderQueryV3Result v3Result = tWxUserPackageService.finish(vo, SecurityUtils.getLoginUser().getWxUser().getId());
            if (v3Result == null)
                logger.info("支付成功,微信已经回调");
            else
                logger.info("套餐购买查询 /package/payquery:" + v3Result.toString());
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

  /*  @ApiOperation("生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", value = "类型", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "parameterValue", value = "参数值", dataType = "String", required = true, paramType = "query")
    })
    @GetMapping(value = "/createQrCode/{roomId}")
    public String createQrCode(@RequestParam("codeType") String codeType,
                               @RequestParam("parameterValue") String parameterValue) {*/

    @ApiOperation("生成二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeType", value = "类型", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "parameterValue", value = "参数值", dataType = "String", required = true, paramType = "query")
    })
    @GetMapping(value = "/createQrCode/{roomId}")
    public String createQrCode(@PathVariable("roomId") Long roomId) {
        final WxPayConfig config = wxPayService.getConfig();

        TRoom room = roomService.selectTRoomById(roomId);
        String codeType = "scene";
        String parameterValue = "" + roomId;

        // 设置小程序二维码线条颜色为黑色
        WxMaCodeLineColor lineColor = new WxMaCodeLineColor("0", "0", "0");
        byte[] qrCodeBytes = null;
        try {
            //其中codeType以及parameterValue为前端页面所需要接收的参数。
            qrCodeBytes = customerWxMaService.getQrcodeService().createWxaCodeUnlimitBytes(roomId + "", "pages/order/add/index", false, "release", 30, false, lineColor, false);
        } catch (WxErrorException e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
            throw new ServiceException("打印二维码错误" + e.getMessage());
        }
        String qrCodeStr = Base64.getEncoder().encodeToString(qrCodeBytes);//.encodeBase64String(qrCodeBytes);
        return qrCodeStr;
    }


    @ApiOperation("绑定手机号")
    @PostMapping(value = "/binding")
    public AjaxResult binding(@RequestBody BindingReq bindingReq) {
        // 调用微信 API 获取用户的 openid 和 session_key
        WxMaJscode2SessionResult session = null;
        try {
            // 调用微信 API 获取用户的手机号
            WxMaPhoneNumberInfo phoneInfo = customerWxMaService.getUserService().getNewPhoneNoInfo(bindingReq.getCode());
            String phoneNumber = phoneInfo.getPhoneNumber();

            long userWxId = SecurityUtils.getLoginUser().getWxUser().getId();
            TWxUser wxUser = new TWxUser();
            wxUser.setId(userWxId);
            wxUser.setPhone(phoneNumber);
            wxUserService.updateTWxUser(wxUser);

        } catch (WxErrorException e) {
            AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    @Autowired
    ITWxUserService wxUserService;

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/userInfo")
    public AjaxResult userInfo() {
        long userWxId = SecurityUtils.getLoginUser().getWxUser().getId();
        TWxUser wxUser = wxUserService.selectTWxUserById(userWxId);
        return AjaxResult.success(wxUser);
    }

    @ApiOperation("获取所有门店充值套餐列表")
    @GetMapping(value = "/store/pack")
    public TableDataInfo storePack(WxStoreListQryVo wxStoreListQryVo) {
        TStorePackage qry = new TStorePackage();
        startPage();
        List<TStorePackage> list = storePackageService.selectTStorePackageList(qry);
        return getDataTable(list);
    }

    @ApiOperation("获取所有门店福利")
    @GetMapping(value = "/promotionList")
    public TableDataInfo list(TStorePromotion tStorePromotion) {
        startPage();
        List<TStorePromotion> list = tStorePromotionService.selectTStorePromotionList(tStorePromotion);
        return getDataTable(list);
    }

    @ApiOperation("加盟信息")
    @PostMapping(value = "/joinUs")
    public AjaxResult joinUs(@RequestBody TJoinUs tJoinUs) {
        int ret = itJoinUsService.insertTJoinUs(tJoinUs);
        return AjaxResult.success(ret);
    }


}
