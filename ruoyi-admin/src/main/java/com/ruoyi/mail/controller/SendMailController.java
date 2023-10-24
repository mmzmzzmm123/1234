package com.ruoyi.mail.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.mail.data.MailInData;
import com.ruoyi.mail.service.IMailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ruoyi.common.core.domain.AjaxResult.success;


@Api("发送邮件")
@CrossOrigin
@RestController
@RequestMapping("/mail/sendmail")
public class SendMailController {

    @Autowired
    private IMailService mailService;

    @ApiOperation("发送简单邮件")
    @PostMapping("/sendSimpleMail")
    public AjaxResult sendSimpleMail(@Validated @RequestBody MailInData mailInData){
        mailService.sendSimpleMail(mailInData.getReceiveUserMail(),mailInData.getSubject(),mailInData.getContent());
        return success();
    }
}
