package com.ruoyi.mail.data;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class MailInData {

    /**
     * 接收者邮箱
     */
    @Email
    private String receiveUserMail;

    /**
     * 主题
     */
    @NotBlank
    private String subject;

    /**
     * 邮件内容
     */
    @NotBlank
    private String content;

    /**
     * 附件
     */
    private String attachments;
}
