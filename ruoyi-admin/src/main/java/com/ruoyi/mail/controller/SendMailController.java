package com.ruoyi.mail.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mail.data.MailInData;
import com.ruoyi.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ruoyi.common.core.domain.AjaxResult.success;

@CrossOrigin
@RestController
@RequestMapping("/mail/sendmail")
public class SendMailController {

    @Autowired
    private IMailService mailService;

    @PostMapping("/sendSimpleMail")
    public AjaxResult sendSimpleMail(@Validated @RequestBody MailInData mailInData){
        mailService.sendSimpleMail(mailInData.getReceiveUserMail(),mailInData.getSubject(),mailInData.getContent());
        return success();
    }
}
