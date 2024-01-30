package com.ruoyi.system.callback.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Sharly
 */
@Data
public class Called1100910027DTO {
    // 好友发消息过来的 好友  或者 （群编号）
    String to_serial_no;

    // 发送时间
    String date_time;

    // 发送者机器人ID
    String from_serial_no;

    // 消息类型
    String msg_type;

    // 消息id
    String msg_id;

    // 消息内容
    ContentData content;

    @Data
    public static class ContentData {
        String message;
        // 如果是图片的话 ，就是图片的下载序列号
        List<String> download_serial_no_list;
        //媒体文件的时长
        int media_duration;
        //文件名字
        String file_name;
        // 标题
        String title;
        // 说明描述
        String description ;
        List<UrlDTO> url_list;

        String emoticon_base64;
    }

    @Data
    public static class UrlDTO {
        String url;
    }
}
