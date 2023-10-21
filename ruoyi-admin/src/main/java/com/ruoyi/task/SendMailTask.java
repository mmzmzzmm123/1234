package com.ruoyi.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.mail.domain.MailContent;
import com.ruoyi.mail.domain.MailUser;
import com.ruoyi.mail.mapper.MailContentMapper;
import com.ruoyi.mail.mapper.MailUserMapper;
import com.ruoyi.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("sendMail")
public class SendMailTask
{
    @Autowired
    private MailContentMapper mailContentMapper;

    @Autowired
    private IMailService iMailService;

    @Autowired
    private MailUserMapper mailUserMapper;

    private void configureTasks() {
        Map<String, Object> map = new HashMap<>();
        MailContent mailContent = new MailContent();
        mailContent.setIsDelete(0L);
        //查询所有晚安语
        List<MailContent> contents = mailContentMapper.selectMailContentList(mailContent);

        MailUser mailUser = new MailUser();
        mailUser.setIsDelete(0L);
        List<MailUser> users = mailUserMapper.selectMailUserList(mailUser);
        //取其中一个
        int size = contents.size()-1;
        //(数据类型)(最小值+Math.random()*(最大值-最小值+1))
        int index = (int)(Math.random()*(size-0+1)+0);

        String message = contents.get(index).getContent();
        //String message = contents.stream().map(content::getContent).collect(Collectors.toList()).get(0);
        Map<String, String> collect = users.stream().filter(user -> user.getName() != null).collect(Collectors.toMap(MailUser::getName, MailUser::getMailAccount));

        Set<Map.Entry<String, String>> entries = collect.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            iMailService.sendSimpleMail(entry.getValue(),"晚安",entry.getKey()+"： "+message);
        }
        //发完邮件之后把这个晚安语设置为删除
        MailContent mailContent1 = new MailContent();
        mailContent1.setId(contents.get(index).getId());
        mailContent1.setIsDelete(1L);
        mailContentMapper.updateMailContent(mailContent1);

    }
}
