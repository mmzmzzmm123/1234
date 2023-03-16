package com.ruoyi.app.controller.login;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.nio.file.Paths;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;
import com.ruoyi.common.utils.QrCodeUtil;

@RestController
@RequestMapping("/app/login")
@Api(value = "登录模块", tags = {"二维码工具"})
public class QrCode {

    // 生成二维码
    @RequestMapping("/getCode")
    public  Map<String , String> getCode( @RequestParam(value = "codeparam") String qrCodeParam , HttpServletResponse response){
        Map<String , String> map = new HashMap<>();
        String qrCodePath = new QrCodeUtil().getCode(qrCodeParam);
        map.put("images_url", qrCodePath);
        return map;
    }



}
