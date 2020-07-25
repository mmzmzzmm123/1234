package com.muster.web.controller.muster.weixin;


import cn.hutool.extra.qrcode.QrCodeUtil;
import com.muster.common.utils.sign.Base64;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * @ClassName QrCodeUtils
 * @Description TOOD 生成二维码图片
 * @Author guoconglin
 * @DATE 2020/4/10 13:57
 * @Version 1.0
 **/


public class QrCodeUtils {

    /**
     * 生成二维码方法返回到流
     * @author guoconglin
     * @date 2020/4/10 13:44
     * @param: [url 访问链接, width 图片宽, height 图片高, mimeType 类型比图png\jpg, resp]
     * @return: void
     */
    public static void getQrcode(String url,int width,int height,String mimeType, HttpServletResponse resp) throws Exception {
        QrCodeUtil.generate(url,width,height, mimeType,resp.getOutputStream());
    }

    /**
     * 生成base64图片
     * @author guoconglin
     * @date 2020/4/10 13:49
     * @param: [url 访问链接, width 图片宽, height 图片高, mimeType 类型比图png\jpg]
     * @return: java.lang.String
     */
    public static String getQrCodeBasePicture(String url,int width,int height,String mimeType){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        QrCodeUtil.generate(url,width,height,mimeType,stream);
        String base64EnCode = Base64.encode(stream.toByteArray());
        return base64EnCode;
    }

}
