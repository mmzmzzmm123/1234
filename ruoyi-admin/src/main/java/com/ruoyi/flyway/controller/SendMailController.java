package com.ruoyi.flyway.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.flyway.domain.MailRequest;
import com.ruoyi.flyway.service.SendMailService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/sendMail")
public class SendMailController  extends BaseController {
    @Resource
    private SendMailService service;
    @PostMapping("/send")
    @PreAuthorize("@ss.hasPermi('system:sendMail:send')")
    public void list(@RequestBody MailRequest mailRequest)
    {
        service.sendSimpleMail(mailRequest);
    }


}
