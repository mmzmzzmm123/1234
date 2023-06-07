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

    public enum RoomOrderStatus {
        IDEAL(0, "空闲"), TO_PAY(1, "待支付"), ORDERED(2, "已预约"), USING(3, "使用中"),
        OVER_TIME(4, "超时未使用"),USED(5, "已完成"),CANCEL(9, "取消");

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

}
