package com.ruoyi.common.utils.bookmarkhtml;

import org.springframework.stereotype.Component;

/**
 * 常量类
 */
@Component
public class Const {

    public static String BASE_PATH;

    public static String LOGIN_SESSION_KEY = "Favorites_user";

    public static String PASSWORD_KEY = "@#$%^&*()OPG#$%^&*(HG";
    public static String URL_CODE = "(?<=charset=)(.+)(?=\")";//取网址的编码

    public static String DES3_KEY = "9964DYByKL967c3308imytCB";

    public static String default_logo="img/logo.jpg";

    public static String userAgent="Mozilla";

    public static String default_Profile=BASE_PATH+"/img/logo.jpg";

    public static String LAST_REFERER = "LAST_REFERER";

    public static int COOKIE_TIMEOUT= 30*24*60*60;

    public static String MenuIocURL= "https://up.raindrop.io/collection/templates/social-media-logos-6/47social.png";
    //已修改父级id的书签
    public static String BOOKMARK_STATE_FLAG= "666";
    //导入书签html功能
    public static String IMPORTHTML= "IMPORT_HTML";

    //手机号发送验证码
    public static String PHONE_REGISTER= "phone_register";


    //最新书签
    public static String NEWEST = "newest";
    //星标
    public static String ASTERISK = "asterisk";
    //稍后看
    public static String SEEYOULATER = "seeYouLater";
    //回收站
    public static String RECYCLE = "recycle";




    //是否书签删除状态 0 未删除 1表示删除
    public static int BKDELETE = 0;

    public static int BKNOTDELETE = 1;




}
