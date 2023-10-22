package com.ruoyi.office.domain.enums;

public class OfficeEnum {

    public enum WxPayRespCode {
        TRADE_ERROR(403, "交易错误"), SYSTEM_ERROR(500, "系统错误"), SIGN_ERROR(401, "签名错误"), RULE_LIMIT(403, "业务规则限制"), PARAM_ERROR(400, "参数错误"), OUT_TRADE_NO_USED(403, "商户订单号重复"), ORDER_NOT_EXIST(404, "订单不存在");

        private final Integer code;
        private final String info;

        WxPayRespCode(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static WxPayRespCode GetValueByCode(Integer code) {
            for (WxPayRespCode e : WxPayRespCode.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum RoomOrderStatus { // 待支付	1 已预约	2 使用中	3 超时未使用	4 已完成	5 取消	9
        IDEAL(0, "空闲"), TO_PAY(1, "待支付"), ORDERED(2, "已预约"), USING(3, "使用中"),
        OVER_TIME(4, "超时未使用"), USED(5, "已完成"), CANCEL(9, "取消");

        private final Integer code;
        private final String info;

        RoomOrderStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static RoomOrderStatus GetValueByCode(Integer code) {
            for (RoomOrderStatus e : RoomOrderStatus.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    /**
     * 购买套餐订单状态
     */
    public enum PackageOrderStatus {
        TO_PAY(0, "待支付"), PAYED(1, "已支付");

        private final Integer code;
        private final String info;

        PackageOrderStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static PackageOrderStatus GetValueByCode(Integer code) {
            for (PackageOrderStatus e : PackageOrderStatus.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum CouponType {
        TIMES_CARD(1, "次数卡"), HOUR_CARD(2, "时长卡");

        private final Integer code;
        private final String info;

        CouponType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static CouponType GetValueByCode(Integer code) {
            for (CouponType e : CouponType.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum ValidType {
        FROM_BUY_DATE(1, "自购买日期起"), START_END_DATE(2, "开始结束日期");

        private final Integer code;
        private final String info;

        ValidType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static ValidType GetValueByCode(Integer code) {
            for (ValidType e : ValidType.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum PayType {
        WX_PAY(1, "微信支付"), CARD_BALANCE_PAY(2, "储值卡余额支付"), COUPON_PAY(4, "优惠券支付"), ORDER4GUEST_PAY(9, "代客预约支付");

        private final Integer code;
        private final String info;

        PayType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static PayType GetValueByCode(Integer code) {
            for (PayType e : PayType.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum WxPayState {
        SUCCESS("SUCCESS", "支付成功"), REFUND("REFUND", "转入退款"), NOTPAY("NOTPAY", "未支付"), CLOSED("CLOSED", "已关闭"), REVOKED("REVOKED", "已撤销（仅付款码支付会返回）"), USERPAYING("USERPAYING", "用户支付中（仅付款码支付会返回）"), PAYERROR("PAYERROR", "支付失败（仅付款码支付会返回）");

        private final String code;
        private final String info;

        WxPayState(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static WxPayState GetValueByCode(Integer code) {
            for (WxPayState e : WxPayState.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum WxTradeType {
        PACK("PACK", "套餐订单"), ROOM_ORDER("ROOM_ORDER", "房间订单"), ROOM_ORDER_CHARGE("ROOM_ORDER_CHARGE", "房间订单");

        private final String code;
        private final String info;

        WxTradeType(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static WxTradeType GetValueByCode(String code) {
            for (WxTradeType e : WxTradeType.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum EquipType {
        HORN("horn", "喇叭"), MACHINE("machine", "机器"), AIR_CONDITION("aircondition", "空调"), LIGHT("light", "电灯"), DOOR("door", "门禁");

        private final String code;
        private final String info;

        EquipType(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static EquipType GetValueByCode(String code) {
            for (EquipType e : EquipType.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum RoomStatus { // //0 可用 1 不可用 2 清洁中 3 使用中
        CAN_USE("0", "可用"), NO_USE("1", "不可用"), IN_CLEAN("2", "清洁中"), IN_USE("3", "使用中");

        private final String code;
        private final String info;

        RoomStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static RoomStatus GetValueByCode(String code) {
            for (RoomStatus e : RoomStatus.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum CleanRecordStatus { // //0 待打扫 1 打扫中 2 打扫完成
        TOBE_CLEAN(0, "待打扫"), CLEANING(1, "打扫中"), COMPLETE(2, "打扫完成");

        private final Integer code;
        private final String info;

        CleanRecordStatus(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static CleanRecordStatus GetValueByCode(Integer code) {
            for (CleanRecordStatus e : CleanRecordStatus.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

    public enum ChargeOrderStatus { // 待支付	1 已预约	2 使用中	3 超时未使用	4 已完成	5 取消	9
        TO_PAY("TO_PAY", "待支付"), PAYED("PAYED", "已预约"), CANCEL("CANCEL", "取消");

        private final String code;
        private final String info;

        ChargeOrderStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }

        public static ChargeOrderStatus GetValueByCode(String code) {
            for (ChargeOrderStatus e : ChargeOrderStatus.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            throw new RuntimeException("枚举值错误");
        }
    }

}
