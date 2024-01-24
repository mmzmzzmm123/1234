package com.ruoyi.common.enums;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/24/024 16:17
 * @Description :
 */
public enum BotDealFunctionsEnum {

    KICK_OUT("KICK_OUT","踢人")
    ,KICK_REPEAL("KICK_REPEAL","踢人并删除历史消息(近1天内的)")
    ,RESTRICT("RESTRICT","禁言")
    ,DELETE_MESSAGE("DELETE_MESSAGE","删除消息")
    ,UNKNOWN("UNKNOWN","未知")
    ;

    String key;
    String value;

    BotDealFunctionsEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static BotDealFunctionsEnum findByKey(String key){
        for (BotDealFunctionsEnum value : BotDealFunctionsEnum.values()) {
            if (value.getKey().equals(key)){
                return value;
            }
        }
        return BotDealFunctionsEnum.UNKNOWN;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
