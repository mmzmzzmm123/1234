package com.ruoyi.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/13/013 16:08
 * @Description :
 */
public class PlayConstants {

    public static String serviceName = "ruoyi-admin";

    // 切割字符
    public static String splitRegex = "\\[-#@-\\]";
    // 拼接字符
    public static String joiningRegex = "[-#@-]";

    // 消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件 2013 小程序 2016 音乐
    public static List<Integer> confoundMomentTypeIds = Arrays.asList(2001, 2002, 2005);

    // 海外 用户配置
    public static String unioUserNo = "2024010515523101";

    // 域名分组传10
    public static Integer domainGroupTypeId = 10;

    // 10：跳转落地URL 20：直接跳转原始URL
    public static Integer redirectWa = 20;

    public static String obs_ak = "LO33WIGK66SC20E05PYW";

    public static String obs_sk = "nwulsZhcz04AFgzcwydI4RkpHBpP7HyNK0Z0qm44";

    public static String obs_bulkName = "linkmaster-obs";

    public static String obs_endPoint = "obs.ap-southeast-2.myhuaweicloud.com";

    public static String obs_downloadUrl = "https://linkmaster-obs.utchats.com/";

}
