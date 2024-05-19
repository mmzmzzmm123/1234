//package com.onethinker.mail.utils;
//
//import com.alibaba.fastjson2.JSONObject;
//import com.onethinker.user.domain.PlatformUserDetail;
//import com.onethinker.common.core.redis.RedisCache;
//import com.onethinker.common.enums.CacheEnum;
//import com.onethinker.common.enums.SysConfigKeyEnum;
//import com.onethinker.common.utils.SecurityUtils;
//import com.onethinker.system.service.ISysConfigService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import javax.annotation.PostConstruct;
//import javax.mail.MessagingException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class MailUtil {
//    private static final Logger log = LoggerFactory.getLogger(MailUtil.class);
//
//    /**
//     * 1. 来源人名
//     * 2. 来源内容
//     */
//    public static final String originalText = "<hr style=\"border: 1px dashed #ef859d2e;margin: 20px 0\">\n" +
//            "            <div>\n" +
//            "                <div style=\"font-size: 18px;font-weight: bold;color: #C5343E\">\n" +
//            "                    %s\n" +
//            "                </div>\n" +
//            "                <div style=\"margin-top: 6px;font-size: 16px;color: #000000\">\n" +
//            "                    <p>\n" +
//            "                        %s\n" +
//            "                    </p>\n" +
//            "                </div>\n" +
//            "            </div>";
//
//    /**
//     * 发件人
//     */
//    public static final String replyMail = "你之前的评论收到来自 %s 的回复";
//    public static final String commentMail = "你的文章 %s 收到来自 %s 的评论";
//    public static final String messageMail = "你收到来自 %s 的留言";
//    public static final String loveMail = "你收到来自 %s 的祝福";
//    public static final String imMail = "你收到来自 %s 的消息";
//    public static final String notificationMail = "你收到来自 %s 的订阅";
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private ISysConfigService sysConfigService;
//
//    @Value("${spring.mail.username}")
//    private String sendMailer;
//
//    @Autowired
//    private RedisCache redisCache;
//
//    /**
//     * 1. 网站名称
//     * 2. 邮件类型
//     * 3. 发件人
//     * 4. 发件内容
//     * 5. originalText
//     * 6. 网站名称
//     */
//    private String mailText;
//
//    @PostConstruct
//    public void init() {
//        this.mailText = "<div style=\"font-family: serif;line-height: 22px;padding: 30px\">\n" +
//                "    <div style=\"display: flex;justify-content: center;width: 100%%;max-width: 900px;background-image: url('https://s1.ax1x.com/2022/12/04/zsKgDs.jpg');background-size: cover;border-radius: 10px\"></div>\n" +
//                "    <div style=\"margin-top: 20px;display: flex;flex-direction: column;align-items: center\">\n" +
//                "        <div style=\"margin: 10px auto 20px;text-align: center\">\n" +
//                "            <div style=\"line-height: 32px;font-size: 26px;font-weight: bold;color: #000000\">\n" +
//                "                嘿！你在 %s 中收到一条新消息。\n" +
//                "            </div>\n" +
//                "            <div style=\"font-size: 16px;font-weight: bold;color: rgba(0, 0, 0, 0.19);margin-top: 21px\">\n" +
//                "                %s\n" +
//                "            </div>\n" +
//                "        </div>\n" +
//                "        <div style=\"min-width: 250px;max-width: 800px;min-height: 128px;background: #F7F7F7;border-radius: 10px;padding: 32px\">\n" +
//                "            <div>\n" +
//                "                <div style=\"font-size: 18px;font-weight: bold;color: #C5343E\">\n" +
//                "                    %s\n" +
//                "                </div>\n" +
//                "                <div style=\"margin-top: 6px;font-size: 16px;color: #000000\">\n" +
//                "                    <p>\n" +
//                "                        %s\n" +
//                "                    </p>\n" +
//                "                </div>\n" +
//                "            </div>\n" +
//                "            %s\n" +
//                "            <a style=\"width: 150px;height: 38px;background: #ef859d38;border-radius: 32px;display: flex;align-items: center;justify-content: center;text-decoration: none;margin: 40px auto 0\"\n" +
//                "               href=\"http://114.132.69.190:8888\" target=\"_blank\">\n" +
//                "                <span style=\"color: #DB214B\">有朋自远方来</span>\n" +
//                "            </a>\n" +
//                "        </div>\n" +
//                "        <div style=\"margin-top: 20px;font-size: 12px;color: #00000045\">\n" +
//                "            此邮件由 %s 自动发出，直接回复无效（一天最多发送 " + 10 + " 条通知邮件和 " + 10 + " 条验证码邮件），退订请联系站长。\n" +
//                "        </div>\n" +
//                "    </div>\n" +
//                "</div>";
//    }
//
//    public String getMailText() {
//        return mailText;
//    }
//
//    @Async
//    public void sendMailMessage(List<String> to, String subject, String text) {
//        log.info("发送邮件===================");
//        log.info("to：{}", JSONObject.toJSONString(to));
//        log.info("subject：{}", subject);
////        log.info("text：{}", text);
//        try {
//            //true代表支持复杂的类型
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
//            //邮件发信人
//            mimeMessageHelper.setFrom(sendMailer);
//            //邮件收信人1或多个
//            mimeMessageHelper.setTo(to.toArray(new String[0]));
//            //邮件主题
//            mimeMessageHelper.setSubject(subject);
//            //邮件内容
//            mimeMessageHelper.setText(text, true);
//            //邮件发送时间
//            mimeMessageHelper.setSentDate(new Date());
//
//            //发送邮件
//            mailSender.send(mimeMessageHelper.getMimeMessage());
//
//            log.info("发送成功==================");
//        } catch (MessagingException e) {
//            log.info("发送失败==================");
//            log.error(e.getMessage());
//        }
//    }
//
//    public void sendCommentMail(CommentVO commentVO, Article article, ICommentService commentService) {
//        List<String> mail = new ArrayList<>();
//        String toName = "";
//        if (commentVO.getParentUserId() != null) {
//            PlatformUserDetail user = platformUserDetailService.getPlatFormUserDetailByUserId(commentVO.getParentUserId());
//            if (user != null && !user.getId().equals(SecurityUtils.getUserId()) && StringUtils.hasText(user.getDataId())) {
//                toName = user.getUsername();
//                mail.add(user.getDataId());
//            }
//        } else {
//            if (CommentTypeEnum.COMMENT_TYPE_MESSAGE.getCode().equals(commentVO.getType()) || CommentTypeEnum.COMMENT_TYPE_LOVE.getCode().equals(commentVO.getType())) {
//                // 树洞和表白墙
//
//            } else if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
//                // 文章评论
//                PlatformUserDetail user = platformUserDetailService.getPlatFormUserDetailByUserId(article.getUserId());
//                if (user != null && StringUtils.hasText(user.getDataId()) && !user.getId().equals(SecurityUtils.getUserId())) {
//                    mail.add(user.getDataId());
//                }
//            }
//        }
//
//        if (!CollectionUtils.isEmpty(mail)) {
//            String sourceName = "";
//            if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentVO.getType())) {
//                sourceName = article.getArticleTitle();
//            }
//            String commentMail = getCommentMail(commentVO.getType(), sourceName,
//                    SecurityUtils.getUsername(),
//                    commentVO.getCommentContent(),
//                    toName,
//                    commentVO.getParentCommentId(), commentService);
//
//            Integer count = redisCache.getCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0));
//            if (count == null || count < Integer.parseInt(sysConfigService.selectConfigByKey(SysConfigKeyEnum.CODE_MAIL_COUNT) + "")) {
//                WebInfo webInfo = webInfoService.getWebInfo();
//                sendMailMessage(mail, "您有一封来自" + webInfo.getWebName() + "的回执！", commentMail);
//                if (count == null) {
//                    redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0), 1, 1, TimeUnit.DAYS);
//                } else {
//                    redisCache.setCacheObject(CacheEnum.CAPTCHA_CODE_KEY.getCode() + mail.get(0), count++, 1, TimeUnit.DAYS);
//                }
//            }
//        }
//    }
//
//    /**
//     * source：0留言 其他是文章标题
//     * fromName：评论人
//     * toName：被评论人
//     */
//    private String getCommentMail(String commentType, String source, String fromName, String fromContent, String toName, Long toCommentId, ICommentService commentService) {
//        WebInfo webInfo = webInfoService.getWebInfo();
//
//        String mailType = "";
//        String toMail = "";
//        if (StringUtils.hasText(toName)) {
//            mailType = String.format(MailUtil.replyMail, fromName);
//            Comment toComment = commentService.selectCommentById(toCommentId);
//            if (toComment != null) {
//                toMail = String.format(MailUtil.originalText, toName, toComment.getCommentContent());
//            }
//        } else {
//            if (CommentTypeEnum.COMMENT_TYPE_MESSAGE.getCode().equals(commentType)) {
//                mailType = String.format(MailUtil.messageMail, fromName);
//            } else if (CommentTypeEnum.COMMENT_TYPE_ARTICLE.getCode().equals(commentType)) {
//                mailType = String.format(MailUtil.commentMail, source, fromName);
//            } else if (CommentTypeEnum.COMMENT_TYPE_LOVE.getCode().equals(commentType)) {
//                mailType = String.format(MailUtil.loveMail, fromName);
//            }
//        }
//
//        return String.format(mailText,
//                webInfo.getWebName(),
//                mailType,
//                fromName,
//                fromContent,
//                toMail,
//                webInfo.getWebName());
//    }
//
//}
