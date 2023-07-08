package com.ruoyi.office.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.binarywang.wxpay.bean.notify.OriginNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.vo.RoomAvailablePeriod;
import com.ruoyi.office.domain.vo.RoomWxVo;
import com.ruoyi.office.mqtt.MqttSendClient;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.WxStoreListQryVo;
import com.ruoyi.office.domain.vo.WxStoreListRspVo;
import com.ruoyi.office.service.*;
import com.ruoyi.system.service.ISysDictDataService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
            // 支付成功结果通知
            OriginNotifyResponse notifyResponse = JSON.parseObject(jsonData, OriginNotifyResponse.class);
            WxPayOrderNotifyV3Result v3Result = null;
            try {
                v3Result = wxPayService.parseOrderNotifyV3Result(jsonStrSort(notifyResponse), getRequestHeader(request));

                //解密后的数据
                WxPayOrderNotifyV3Result.DecryptNotifyResult result = v3Result.getResult();

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
        promotion.setStatus(0l);
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
        List<WxStoreListRspVo> list = storeService.selectWxStoreList(wxStoreListQryVo);
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
        TStore store = storeService.selectTStoreById(id);
        TEquipment equipment = equipmentService.selectTEquipmentById(store.getEquipId());
        MqttSendClient sendClient = new MqttSendClient();
        sendClient.publish(equipment.getEquipControl(), "a1");
        equipment.setOnOff("Y");
        equipmentService.updateTEquipment(equipment);
        return AjaxResult.success();
    }

    @Autowired
    ITRoomService roomService;

    /**
     * 开门禁
     *
     * @param id
     * @return
     */
    @ApiOperation("开房间设备")
    @PostMapping("/room/{id}")
    public AjaxResult openRoom(@PathVariable("id") Long id) {
        TRoom room = roomService.selectTRoomById(id);
        List<TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment());
        MqttSendClient sendClient = new MqttSendClient();
        String equips = room.getTableCode();
        for (String equip : equips.split(",")) {
            for (TEquipment e : equipments) {
                if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(e.getEquipType()))
                    continue;

                if (e.getId() == Long.parseLong(equip)) {

                    sendClient.publish(e.getEquipControl(), "a1");
                    e.setOnOff("Y");
                    equipmentService.updateTEquipment(e);
                    break;
                }
            }
        }

        return AjaxResult.success();
    }

    @Autowired
    private ITRoomOrderService tRoomOrderService;

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     */
    @ApiOperation("wx订单记录")
    @GetMapping("/room/order")
    public TableDataInfo order(TRoomOrder tRoomOrder) {
        tRoomOrder.setCreateBy(SecurityUtils.getUserId() + "");
        startPage();
        List<TRoomOrder> list = tRoomOrderService.selectTRoomOrderList(tRoomOrder);
        return getDataTable(list);
    }

    @Autowired
    private ITRoomService tRoomService;

    @Autowired
    private ISysDictDataService dictDataService;

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

        final Map<Long, List<TRoomPrice>> roomPriceGroup = tRoomPriceService.selectTRoomPriceList(new TRoomPrice()).stream().collect(Collectors.groupingBy(TRoomPrice::getRoomId));

        for (TRoom room : list) {
            RoomWxVo vo = new RoomWxVo();
            BeanUtils.copyProperties(room, vo);
            String roomMark = "";
            if (StringUtils.isEmpty(room.getRemark()))
                continue;
            for (String mark : room.getRemark().split(",")) {
                roomMark += "," + dictMap.get(mark);
            }

            vo.setRemark(roomMark.substring(1));
            vo.setPriceList(roomPriceGroup.get(room.getId()));

            RoomAvailablePeriod qry = new RoomAvailablePeriod();
            qry.setRoomId(room.getId());
            qry.setDate(DateUtils.parseDate(DateUtils.getDate()));
            final RoomAvailablePeriod availablePeriod = tRoomOrderService.getAvailablePeriod(qry);

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
        final RoomAvailablePeriod availablePeriod = tRoomOrderService.getAvailablePeriod(vo);
        return success(availablePeriod);
    }

}
