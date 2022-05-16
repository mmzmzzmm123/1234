package work.cib.zpyz;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    public static JSONObject sendSoap(String url, String soap) throws Exception {
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            //服务的地址
            URL wsUrl = new URL(url);

            conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);

            os = conn.getOutputStream();
            // 请求体
            //soap = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><GetGzzhxxSq xmlns=\"http://tempuri.org/\"><bankCode>3090000</bankCode><khsqlsh>121</khsqlsh><cityCode>321200</cityCode><passWord>32422012026559c0ba33691347fca870</passWord></GetGzzhxxSq></soap:Body></soap:Envelope>";
            log.info("发送第三发请求报文:" + soap);
            os.write(soap.getBytes());
//--------------------------------------------------------
            is = conn.getInputStream();
            int count = is.available();
            log.info("第三方返回报文长度:" + count);
            byte[] restByte = new byte[count];
            int len = 0;
            String result = "";
            while ((len = is.read(restByte)) != -1) {
                String str = new String(restByte, 0, len, "GBK");
                result += str;
            }
            log.info("接收第三方返回报文(未转换):" + result);
            result = decode(result);
            log.info("接收第三方返回报文(转换后):" + result);
//	        JSONObject json = JSONObject.fromObject(result);
            JSONObject json = JSONObject.parseObject(result);
            return json;
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * base64解码
     */
    public static String decode(String base64) {
        String str = "";
        Base64 base = new Base64();
        try {
            str = new String(Base64.decodeBase64(base64), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("base64解码失败", e);
        }
        return str;
    }

}
