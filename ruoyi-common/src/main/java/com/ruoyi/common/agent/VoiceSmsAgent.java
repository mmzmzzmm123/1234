package com.ruoyi.common.agent;

import com.google.gson.Gson;
import com.ruoyi.common.utils.http.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/***
 * https://mysubmail.com/
 * ch2012@163.com
 * knight2012
 */
@Slf4j
public class VoiceSmsAgent {
    private static Gson GSON = new Gson();

    public static void send(String phone, String name, String code) {
        send(phone, name, code, 10);
    }

    public static void send(String phone, String name, String code, Integer retry) {
        Map body = new HashMap();
        body.put("appid", "21460");
        body.put("to", phone);
        body.put("project", "vxB3H2");
        body.put("signature", "d1f431d1adc5719d5ebb7c953d7bc94e");

        Notification noti = new Notification();
        noti.setName(name);
        noti.setReson(code);
        body.put("vars", GSON.toJson(noti));

        String resultStr = null;
        try {
            resultStr = HttpUtil.doPost("https://api.mysubmail.com/voice/xsend.json", null, null, body, null, true);

            NotiResult result = GSON.fromJson(resultStr, NotiResult.class);
            if(!"success".equals(result.status) && retry > 0){
                log.warn("通知发送失败，稍后再试 {}", resultStr);
                Thread.sleep(20000);
                send(phone, name, code, retry - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
//        VoiceSmsAgent.send("15828325276", "121212");
//        Map n = new HashMap<>();
//        n.put("version","4");
//        HttpUtil.doPost("http://47.89.36.172:7700/send/all/000771",null,null,n,null,true);

        Map body = new HashMap();
        body.put("appid", "21460");
        body.put("to", "18502887156");
        body.put("project", "vxB3H2");
        body.put("signature", "d1f431d1adc5719d5ebb7c953d7bc94e");

        Notification noti = new Notification();
        noti.setName("定制");
        noti.setReson("111111");
        body.put("vars", new Gson().toJson(noti));

        String result = HttpUtil.doPost("https://api.mysubmail.com/voice/xsend.json", null, null, body, null, true);
        System.out.println(result);

    }

    @Data
    public static class Notification {
        private String name;
        private String reson;
    }

    @Data
    public static class NotiResult {
        private String status;
        private String code;
        private String msg;
    }


}
