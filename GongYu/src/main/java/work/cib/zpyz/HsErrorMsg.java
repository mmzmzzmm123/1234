package work.cib.zpyz;
/**
 * 系统名称: 管理平台
 * 模块名称: 公用错误信息声明
 * 类 名 称: HsException
 * 软件版权: 兴业银行
 * 开发时间: 2007.11.19
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期 修改人员 修改说明
 */

import java.util.HashMap;

public class HsErrorMsg {

    protected static HashMap messages = new HashMap();
    ;

    /* 成功返回码的宏定义 */
    public static final String ERR_SUCCESS = "00000"; // 成功
    public static final String ERR_DEFAULT = "00001"; // 缺省错误
    public static final String ERR_UNKNOW = "00002"; // 未知错误

    public static final String ERR_DB_ERROR = "00003";
    public static final String ERR_QUERY_ERROR = "00004";
    public static final String TCP_ERROR = "00005";
    public static final String FILE_ERROR = "00006";

    public static final String ERR_DATATIME = "00010"; //时间格式错误

    static {

        //成功返回码的宏定义
        messages.put(ERR_SUCCESS, "成功");
        messages.put(ERR_DEFAULT, "处理失败");
        messages.put(ERR_UNKNOW, "未知错误");

        messages.put(ERR_DB_ERROR, "数据库错误");
        messages.put(ERR_QUERY_ERROR, "查询失败");
        messages.put(TCP_ERROR, "通讯错误");
        messages.put(FILE_ERROR, "文件错误");
        messages.put(ERR_DATATIME, "时间格式错误");


    }

    public static String getMessage(String key) {

        String message = (String) messages.get(key);        //是否需要默认""?
        return message != null ? message : "未知错误";
    }

}
