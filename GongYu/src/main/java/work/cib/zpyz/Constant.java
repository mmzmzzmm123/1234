package work.cib.zpyz;

public class Constant {

    //api-参数源发起系统编号
    public static final String SRCCNSMRSYSID = "F02008";
    //短信发送状态-初始状态-1.待发送
    public static final String DX_JLZT_1 = "1";

    /**
     * 发票验真状态-记录状态 1.待处理 2.部分成功 3.全部成功 4.全部失败
     */
    public class UPL_JLZT {
        public static final String UPL_JLZT_1 = "1";
        public static final String UPL_JLZT_2 = "2";
        public static final String UPL_JLZT_3 = "3";
        public static final String UPL_JLZT_4 = "4";
    }

    //是否需要验真截图-初始状态-0需要 1.不需要
    public static final String UPL_YZJT_0 = "0";
    public static final String UPL_YZJT_1 = "1";
    //发票状态	1.待验证 2.无效 3.有效  4.重复发票  6.申请注销
    public static final String INVOICE_STATE_1 = "1";
    public static final String INVOICE_STATE_2 = "2";
    public static final String INVOICE_STATE_3 = "3";
    public static final String INVOICE_STATE_4 = "4";
    public static final String INVOICE_STATE_6 = "6";
    //OCR识别状态   0.待识别 1.识别成功 2.识别失败 3.无需识别（excel）
    public static final String INVOICE_OCRFLAG_0 = "0";
    public static final String INVOICE_OCRFLAG_1 = "1";
    public static final String INVOICE_OCRFLAG_2 = "2";
    public static final String INVOICE_OCRFLAG_3 = "3";

    //首次验真状态  0.待查验  1.查验成功  2.查验无效
    public static final String INVOICE_FIRST_0 = "0";
    public static final String INVOICE_FIRST_1 = "1";
    public static final String INVOICE_FIRST_2 = "2";

    //官网验真状态  0.待查验 1.查验成功 2.查验无效 3.无需查验
    public static final String INVOICE_YZZT_0 = "0";
    public static final String INVOICE_YZZT_1 = "1";
    public static final String INVOICE_YZZT_2 = "2";
    public static final String INVOICE_YZZT_3 = "3";

    //二次验真状态  0.待查验  1.查验成功  2.查验无效  3.无需查验
    public static final String INVOICE_SECOND_0 = "0";
    public static final String INVOICE_SECOND_1 = "1";
    public static final String INVOICE_SECOND_2 = "2";
    public static final String INVOICE_SECOND_3 = "3";

    //删除原始数据状态   0 删除  1 不删除
    public static final String INVOICE_DELFLAG_0 = "0";
    public static final String INVOICE_DELFLAG_1 = "1";
    //日期格式
    public static final String SIMPLEFORMAT_19 = "yyyy-MM-dd HH:mm:ss";
    public static final String SIMPLEFORMAT_14 = "yyyyMMddHHmmss";
    public static final String SIMPLEFORMAT_8 = "yyyyMMdd";
    //验真查验的类型   为0时则为接口验真     为1时则为官网验真      为2时则为单独OCR识别（八月迭代新增功能）
    public static final String VERIFYTYPE_0 = "0";
    public static final String VERIFYTYPE_1 = "1";
    public static final String VERIFYTYPE_2 = "2";
    //数据的类型 		为0时为发票图像 	为1时为发票四要素
    public static final String DATATYPE_0 = "0";
    public static final String DATATYPE_1 = "1";
    //API返回码     0 成功    1失败
    public static final String API_CODE_SUCCESS = "200";
    //业务管理员id R99999998
    public static final String BUSIADMIN_ID = "R99999998";
    //业务管理员NOTESID 999998
    public static final String BUSIADMIN_NOTESID = "999998";
    //系统管理员NOTESID 999999
    public static final String SYSADMIN_NOTESID = "999999";
    //成功返回码
    public static final String SUCCESS_CODE = "0000";
    //成功返回信息
    public static final String SUCCESS_MSG = "success";
    //logger_level
    public static final int LOG_LEVEL_1 = 1;
    public static final int LOG_LEVEL_2 = 2;
    public static final int LOG_LEVEL_3 = 3;

    public static final String UP_USER = "sysadmin";

    /**
     * @return F02008
     */
    public static String getSrccnsmrsysid() {
        return SRCCNSMRSYSID;
    }

    /**
     * @return 短信发送状态-初始状态-1.待发送
     */
    public static String getDxJlzt1() {
        return DX_JLZT_1;
    }

    /**
     * @return 发票验真状态-初始状态-1.待处理
     */
    public static String getUplJlzt1() {
        return UPL_JLZT.UPL_JLZT_1;
    }

    /**
     * @return 发票验真状态-初始状态-3-全部成功
     */
    public static String getUplJlzt2() {
        return UPL_JLZT.UPL_JLZT_2;
    }

    /**
     * @return 发票验真状态-初始状态-3-全部成功
     */
    public static String getUplJlzt3() {
        return UPL_JLZT.UPL_JLZT_3;
    }

    /**
     * @return 是否需要验真截图-初始状态-1.不需要
     */
    public static String getUplYzjt1() {
        return UPL_YZJT_1;
    }

    /**
     * @return 是否需要验真截图-初始状态-0需要
     */
    public static String getUplYzjt0() {
        return UPL_YZJT_0;
    }

}
