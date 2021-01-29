package com.stdiet.web.controller.custom;

import com.stdiet.common.core.controller.BaseController;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.custom.domain.WxXmlData;
import com.stdiet.custom.service.ISysWxService;
import com.thoughtworks.xstream.XStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlMimeType;

@RestController
@RequestMapping("/wx")
public class CusWxController extends BaseController {

    @Autowired
    public ISysWxService sysWxService;

    @GetMapping("/checkSign")
    public String wxCheckAuth(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        return sysWxService.wxCheckAuth(signature, timestamp, nonce, echostr);
    }

    @PostMapping("/checkSign")
    public String autoResonse(HttpServletRequest request){
        return sysWxService.autoResponse(request);
    }

}
