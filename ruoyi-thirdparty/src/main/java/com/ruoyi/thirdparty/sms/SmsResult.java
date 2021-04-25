package com.ruoyi.thirdparty.sms;

/**
 * @author : yelifeng
 * @date : 2020/10/17 0017 14:57 xuc5566@gmail.com
 */
public class SmsResult {
    private String outId;
    private boolean result;
    private String errorCode;
    private String errorMsg;
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public String getOutId() {
        return outId;
    }
    public void setOutId(String outId) {
        this.outId = outId;
    }
}
