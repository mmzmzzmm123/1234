package com.ruoyi.system.callback.dto;

import lombok.Data;

@Data
public class CalledDTO {

    /***
     * 可能是数组， 也可能是对象
     */
    private Object data;

    /***
     * 透传字段
     */
    private String extend;

    /***
     * 机器人编号
     */
    private String robotId;

    private Long type;

    /***
     * 操作码
     */
    private String optSerNo;

    private Integer resultCode;

    private String resultMsg;

    private String requestPara;

    public boolean isSuccess() {
        return  resultCode != null && 0 == resultCode;
    }

    public static boolean isSucceeded(CalledDTO callbackData) {
        return null != callbackData && callbackData.getResultCode() != null && 0 == callbackData.getResultCode();
    }

}
