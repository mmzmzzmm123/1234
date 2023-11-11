package com.ruoyi.common.weixin.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author LAM
 * @date 2023/6/11 23:11
 */
public class WxUtils {

    /**
     * 处理请求头信息
     *
     * @param request request
     * @return 结果
     * @throws IOException io异常
     */
    public static String parseWxNotify(HttpServletRequest request) throws IOException {
        String resultXml;
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        resultXml = outSteam.toString("utf-8");
        outSteam.close();
        inStream.close();
        return resultXml;
    }

    /**
     * 返回微信服务
     * @param returnCode 状态码
     * @param returnMsg 状态信息
     * @return 数据
     * */
    public static String setXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>";
    }

}
