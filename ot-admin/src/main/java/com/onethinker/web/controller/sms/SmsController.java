package com.onethinker.web.controller.sms;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author : yangyouqi
 * @date : 2024/2/23 11:40
 */
public class SmsController {
    public static void main(String[] args) throws IOException {
        // 指定目标网页URL
        String url = "https://yunjisms.xyz/messages/13833018873";

        // 使用jsoup连接到URL并获取整个HTML文档
        org.jsoup.nodes.Document document = Jsoup.connect(url).get();
        // 使用jsoup选择器获取特定ID为"targetDiv"的div元素
        Elements targetDiv = document.select(".container-fluid").select(".row").select(".panel-body");

        // 检查是否找到了目标div
        if (targetDiv != null) {
            // 获取目标div的文本内容
            for (Element element : targetDiv) {
                String span = element.select("span").text();
                // 可以调整为接收指定平台验证码
                if (span.contains("【")) {
                    System.out.println("短信内容：" + span);
                }
            }
        } else {
            System.out.println("未找到目标div");
        }


    }
}
