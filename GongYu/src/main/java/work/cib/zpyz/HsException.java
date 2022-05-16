package work.cib.zpyz;
/**
 * 系统名称: 管理平台
 * 模块名称: 普通异常处理类
 * 类 名 称: HsException
 * 软件版权: 兴业银行
 * 开发时间: 2007.11.19
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期 修改人员 修改说明
 */

import java.lang.Exception;
import java.lang.StackTraceElement;

public class HsException extends Exception {
    protected String errCode = HsErrorMsg.ERR_DEFAULT;
    protected String errMsg = HsErrorMsg.getMessage(HsErrorMsg.ERR_DEFAULT);


    /**
     * 默认构造函数。
     */
    public HsException() {
    }

    /**
     * 指定错误代码和错误信息的构造函数。
     *
     * @param errCode 错误代码
     * @param errMsg  错误信息
     */
    public HsException(String errCode, String errMsg) {
        super(errCode);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public HsException(String errCode, String errMsg, Exception ex) {
        super(errCode);
        this.errCode = errCode;
        this.errMsg = errMsg;
        StackTraceElement[] exEl = ex.getStackTrace();
        this.setStackTrace(exEl);
    }


    public HsException(String errCode) {
        super(errCode);
        this.errCode = errCode;
        syncErrMsg();
    }

    /**
     * 设置错误代码。
     *
     * @param errCode 错误代码
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * 设置错误信息。
     *
     * @param errMessage 错误信息
     */
    public void setErrMsg(String errMessage) {
        this.errMsg = errMessage;
    }

    /**
     * 获取错误代码。
     *
     * @return String 错误代码。
     */
    public String getErrCode() {
        return this.errCode;
    }

    public String getMessage() {
        return this.errMsg;
    }

    /**
     * 获取错误信息。
     *
     * @return String 错误信息。
     */
    public String getErrMsg() {
        return this.errMsg;
    }

    public void syncErrMsg() {
        if (this.errCode != null)
            this.errMsg = HsErrorMsg.getMessage(this.errCode);

    }

    /**
     * 得到错误提示信息
     *
     * @return String
     */
    public String toString() {
        if (errCode != null)
            return errCode + ":" + errMsg;
        else
            return errMsg;
    }

}
