package com.ruoyi.app.controller.login;


import com.qcloud.cos.model.UploadResult;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.QrCodeUtil;
import com.ruoyi.common.utils.cos.COSClientFactory;
import com.ruoyi.common.utils.file.FileUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/app/gauge/qr")
@Api(value = "登录模块", tags = {"二维码工具"})
public class QrCode {

    // 生成二维码
    @PostMapping("/getCode")
    public AjaxResult getCode(@RequestBody Map<String, Object> map) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String qrCodeParam = map.get("qrCodeParam").toString();
        Map<String , String> res = new HashMap<>();
        String qrCodePath = new QrCodeUtil().getCode(qrCodeParam);
        File file  = new File(qrCodePath);
        InputStream in = new FileInputStream(file);
        UploadResult upload = COSClientFactory.upload(in, uuid+".png", "");
        String key = upload.getKey();
        String url = COSClientFactory.getObjUrl(key, "");
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", url);
//        res.put("images_url", "http://wx.ssgpsy.com"+"/prod-api" + qrCodePath);
        res.put("images_url", url);
        return AjaxResult.success(res);
    }



}
