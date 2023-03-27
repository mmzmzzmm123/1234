package com.ruoyi.app.controller.wechat;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.app.controller.wechat.constant.WechatConstants;
import com.ruoyi.app.controller.wechat.constant.WechatUrlConstants;
import com.ruoyi.app.controller.wechat.utils.WechatPayV3Utils;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.gauge.domain.PsyOrderPay;
import com.ruoyi.gauge.service.IPsyOrderPayService;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 微信支付相关接口demo v3版本
 * 文档地址 https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_5_1.shtml
 */
@RestController
@RequestMapping("/app/api/wechatPay/v3")
public class WechatPayV3ApiController extends BaseController {
 
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
 
    @Autowired
    public WechatPayV3Utils wechatPayV3Utils;

    @Autowired
    private IPsyUserService psyUserService;

    @Autowired
    private ICourOrderService courOrderService;

    @Autowired
    private IPsyOrderPayService orderPayService;

 
    /**
     * 发起微信小程序支付
     *
     * 用于换取openid 正式使用时openid可以直接从用户信息中获取 不需要在此接口中获取
     * @return 小程序支付所需参数
     */
    @PostMapping("/wechatPay")
    public AjaxResult wechatPay(@RequestBody Map<String, String> map) {

        //@TODO demo中先写死的一些参数
        Long userId = Long.parseLong(map.get("userId")); //用户id
        Integer courseId = Integer.parseInt(map.get("courseId")); //课程ID
        String out_trade_no = createOrderNo("DJ", userId); //创建商户订单号
        BigDecimal amount = new BigDecimal("0.01"); //先写死一个金额 单位：元

        // TODO: 内部生成订单
        CourOrder courOrder = new CourOrder();
        courOrder.setOrderId(out_trade_no);
        courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_CREATED);
        courOrder.setAmount(amount);
        courOrder.setUserId(Integer.parseInt(map.get("userId")));
        courOrder.setCourseId(courseId);
        CourOrder newCourOrder = courOrderService.generateCourOrder(courOrder);

        // TODO: 内部生成支付对象
        PsyOrderPay orderPay = new PsyOrderPay();
        orderPay.setOrderId(Long.parseLong(newCourOrder.getId().toString()));
        orderPay.setPayType(CourConstant.PAY_WAY_WEIXIN); // 微信
        orderPay.setPayStatus(CourConstant.PAY_STATUE_PENDING);
        orderPay.setAmount(amount);
        orderPay.setPayId(UUID.randomUUID().toString()); // 当前使用随机生成的支付ID，后续使用第三方支付平台返回的
        orderPayService.insertPsyOrderPay(orderPay);

        // 根据用户ID从用户表中查询openid
        PsyUser user = psyUserService.selectPsyUserById(userId + "");
        String openid = user.getWxOpenid();


        String content = "支付demo-课程金"; //先写死一个商品描述
        String attach = "我是附加数据"; //先写死一个附加数据 这是可选的 可以用来判断支付内容做支付成功后的处理
 
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 15);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
 
        JSONObject params = new JSONObject();
        params.put("appid", WechatConstants.WECHAT_MP_APPID); //小程序appid
        params.put("mchid", WechatConstants.WECHAT_MCH_ID); //商户号
        params.put("description", content); //商品描述
        params.put("out_trade_no", out_trade_no); //商户订单号
        params.put("time_expire", sdf.format(calendar.getTime())); //交易结束时间 选填 时间到了之后将不能再支付 遵循rfc3339标准格式
        params.put("attach", attach); //附加数据 选填 在查询API和支付通知中原样返回 可作为自定义参数使用
        params.put("notify_url", WechatUrlConstants.PAY_V3_NOTIFY); //支付结果异步通知接口
        JSONObject amount_json = new JSONObject();
        amount_json.put("total", Integer.parseInt(amount_fee(amount))); //支付金额 单位：分
        params.put("amount", amount_json); //订单金额信息
        JSONObject payer = new JSONObject();
        payer.put("openid", openid); //用户在小程序侧的openid
        params.put("payer", payer); //支付者信息
        JSONObject res = wechatPayV3Utils.sendPost(WechatUrlConstants.PAY_V3_JSAPI, params); //发起请求
        if (res == null || StringUtils.isEmpty(res.getString("prepay_id"))) {
            //@TODO 支付发起失败可以将订单数据回滚
            return error("支付发起失败");
        }
        StringBuilder sb = new StringBuilder();
        //返回给小程序拉起微信支付的参数
        Map<String, String> result = new HashMap<>();
        result.put("appId", WechatConstants.WECHAT_MP_APPID); //小程序appid
        sb.append(result.get("appId")).append("\n");
        result.put("timeStamp", (new Date().getTime() / 1000) + ""); //时间戳
        sb.append(result.get("timeStamp")).append("\n");
        result.put("nonceStr", RandomStringUtils.randomAlphanumeric(32)); //32位随机字符串
        sb.append(result.get("nonceStr")).append("\n");
        result.put("package", "prepay_id=" + res.getString("prepay_id")); //预支付id 格式为 prepay_id=xxx
        sb.append(result.get("package")).append("\n");
        result.put("paySign", wechatPayV3Utils.signRSA(sb.toString())); //签名
        result.put("signType", "RSA"); //加密方式 固定RSA
        result.put("out_trade_no", out_trade_no); //商户订单号 此参数不是小程序拉起支付所需的参数 因此不参与签名
        return AjaxResult.success(RespMessageConstants.OPERATION_SUCCESS ,result);
    }
 
    /**
     * 支付成功后查询订单状态
     *
     * @param out_trade_no 商户订单号
     * @return null代表查询失败 SUCCESS-成功 USERPAYING（用户支付中）和ACCEPT（已接收，等待扣款）为中间态 需要重新查询 其他为支付失败
     */
    @PostMapping("/checkPay")
    public Map<String, Object> checkPay(String out_trade_no) {
        //@TODO 先查询自己数据库中的订单状态是否支付成功 若成功 则直接返回SUCCESS 若未成功 则需调用查询支付接口
        String status = orderQueryByOutTradeNo(out_trade_no);
        return AjaxResult.success("请求成功", status);
    }
 
    /**
     * 申请微信退款
     * 交易时间超过一年的订单无法提交退款
     * 微信支付退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。
     * 申请退款总金额不能超过订单金额。
     * 一笔退款失败后重新提交，请不要更换退款单号，请使用原商户退款单号
     * 每个支付订单的部分退款次数不能超过50次
     * 同一笔订单多次退款的请求需相隔1分钟
     *
     * @param transaction_id 微信支付订单号
     * @return
     */
    @PostMapping("/wechatRefund")
    public Map<String, Object> wechatRefund(String transaction_id) {
        //@TODO demo中先写死的一些参数
        Long userId = 1L; //先写死一个用户id
        BigDecimal amount = new BigDecimal("0.01"); //先写死一个退款金额 单位：元
        String reason = "支付demo-退还订金"; //先写死一个退款原因 这是可选的
 
        //@TODO 先查询订单是否可退款 将订单修改为退款中等业务处理
 
        String out_refund_no = createOrderNo("TK", userId); //创建商户退款单号
        JSONObject params = new JSONObject();
        params.put("transaction_id", transaction_id); //微信支付订单号 也可以传out_trade_no 即发起支付时创建的商户订单号 二选一 transaction_id>out_trade_no
        params.put("out_refund_no", out_refund_no); //商户退款单号
        params.put("reason", reason); //退款原因 选填 若填写 会在退款消息中显示给用户
        params.put("notify_url", WechatUrlConstants.PAY_V3_REFUND_NOTIFY); //退款结果异步通知接口
        JSONObject amountJson = new JSONObject();
        amountJson.put("refund", Integer.parseInt(amount_fee(amount))); //退款金额 单位：分
        amountJson.put("total", Integer.parseInt(amount_fee(amount))); //原订单金额 单位：分
        amountJson.put("currency", "CNY"); //退款币种
        params.put("amount", amountJson); //订单金额信息
        JSONObject res = wechatPayV3Utils.sendPost(WechatUrlConstants.PAY_V3_REFUND, params); //发起请求
        if (res == null) {
            //@TODO 退款失败时回滚订单状态
            return error("退款申请失败");
        }
        logger.info("微信退款单号：" + res.getString("refund_id"));
        //@TODO 可以在此更新订单微信退款单号等信息
        return success();
    }
 
    /**
     * 微信支付异步通知
     *
     * @param request
     * @return
     */
    @PostMapping("/wechatPayNotify")
    public Map<String, String> wechatPayNotify(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(2);
        JSONObject res = wechatPayV3Utils.getCallbackData(request);

        if (res == null) {
            result.put("code", "FAIL");
            result.put("message", "失败");
            return result;
        }
        logger.info("最终拿到的微信支付通知数据：" + res);
        //@TODO 处理支付成功后的业务 例如 将订单状态修改为已支付 具体参数键值可参考文档 注意！！！ 微信可能会多次发送重复的通知 因此要判断业务是否已经处理过了 避免重复处理
        // TODO: 修改订单状态为已完成
        String out_trade_no = res.getString("out_trade_no");
        CourOrder courOrder = courOrderService.selectCourOrderByOrderId(out_trade_no);
        courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_FINISHED);
        courOrderService.updateCourOrder(courOrder);

        // TODO: 修改支付对象状态为已支付
        String payId = res.getString("transaction_id"); // 微信支付系统生成的订单号
        PsyOrderPay orderPay = new PsyOrderPay();
        orderPay.setOrderId(Long.parseLong(courOrder.getId().toString())); // 订单ID
        orderPay.setPayStatus(CourConstant.PAY_STATUE_PAID);
        orderPay.setPayId(payId);
        orderPayService.updatePsyOrderPayByOrderId(orderPay);

        result.put("code", "SUCCESS");
        result.put("message", "OK");
        return result;
    }
 
    /**
     * 微信退款异步通知
     *
     * @param request
     * @return
     */
    @PostMapping("/wechatRefundNotify")
    public Map<String, String> wechatRefundNotify(HttpServletRequest request) {
        Map<String, String> result = new HashMap<>(2);
        JSONObject res = wechatPayV3Utils.getCallbackData(request);
        if (res == null) {
            result.put("code", "FAIL");
            result.put("message", "失败");
            return result;
        }
        logger.info("最终拿到的微信退款通知数据：" + res);
        //@TODO 处理退款成功后的业务 例如 将订单状态修改位已退款 具体参数键值可参考文档 注意！！！ 微信可能会多次发送重复的通知 因此要判断业务是否已经处理过了 避免重复处理
        result.put("code", "SUCCESS");
        result.put("message", "OK");
        return result;
    }
 
    /**
     * 通过商户订单号查询订单在微信侧支付状态
     *
     * @param out_trade_no 发起支付时创建的商户订单号
     * @return null代表查询失败 SUCCESS-成功 USERPAYING和ACCEPT为中间态 其他为支付失败
     */
    public String orderQueryByOutTradeNo(String out_trade_no) {
        JSONObject res = wechatPayV3Utils.sendGet(String.format(WechatUrlConstants.PAY_V3_QUERY_OUT, out_trade_no, WechatConstants.WECHAT_MCH_ID));
        return res == null ? null : res.getString("trade_state");
    }
 
    /**
     * 查询单笔退款
     *
     * @param out_refund_no 申请退款时创建的商户退款单号
     * @return
     */
    public JSONObject refundQuery(String out_refund_no) {
        return wechatPayV3Utils.sendGet(String.format(WechatUrlConstants.PAY_V3_QUERY_REFUND, out_refund_no));
    }
 
     /**
     * 创建商户订单号
     * 要求 32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
     * 组成 两位前缀 + 17位时间戳 + 9位id补零 + 4位随机数 合计32位
     *
     * @param head 例如 商品-SP 订金-DJ 退款-TK 等等
     * @param id   用户id
     * @return
     */
    public String createOrderNo(String head, Long id) {
        StringBuilder uid = new StringBuilder(id.toString());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        int length = uid.length();
        for (int i = 0; i < 9 - length; i++) {
            uid.insert(0, "0");
        }
        return head + sdf.format(date) + uid + (int) ((Math.random() * 9 + 1) * 1000);
    }
 
    /**
     * 金额元转分字符串
     *
     * @param cny 元
     * @return
     */
    public String amount_fee(BigDecimal cny) {
        BigDecimal b2 = new BigDecimal(100);
        return cny.multiply(b2).setScale(0, RoundingMode.DOWN).toString();
    }
 
}