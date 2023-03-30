package com.ruoyi.app.controller.login;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.QrCodeUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/gauge/qr")
@Api(value = "登录模块", tags = {"二维码工具"})
public class QrCode {

    // 生成二维码
    @PostMapping("/getCode")
    public AjaxResult getCode(@RequestBody Map<String, Object> map){
        String qrCodeParam = map.get("qrCodeParam").toString();
        Map<String , String> res = new HashMap<>();
        String qrCodePath = new QrCodeUtil().getCode(qrCodeParam);
        res.put("images_url", "https://wx.ssgpsy.com" + qrCodePath);
        return AjaxResult.success(res);
    }



}
