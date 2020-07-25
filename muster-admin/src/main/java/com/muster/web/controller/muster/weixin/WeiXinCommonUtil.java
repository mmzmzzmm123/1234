package com.muster.web.controller.muster.weixin;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.muster.common.utils.StringUtils;
import com.muster.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WeiXinCommonUtil
 * @Description TOOD 微信通用工具类
 * @Author guoconglin
 * @DATE 2020/4/10 10:38
 * @Version 1.0
 **/


public class WeiXinCommonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(WeiXinCommonUtil.class);


    private static Map<String, JsApiTicket> weixinCache = new HashMap<String, JsApiTicket>();

    private static Map<String, Token> tokenCache = new HashMap<String, Token>();

    // 临时二维码
    private static final  String QR_SCENE = "QR_SCENE";
    // 永久二维码
    private static final  String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    // 永久二维码(字符串)
    private static  final  String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";
    // 创建二维码
    private static final String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s";
    // 通过ticket换取二维码
    private static final String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";

    // 凭证获取（GET）
    public static final  String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 长链接转短链接接口
     */
    public static final String SHORTURL_API_URL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=%s";
    /**
     * 获取access_token
     */
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 获得jsapi_ticket
     */
    public static final String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";

    /**
     * 通过access token获得jsapi_ticket
     */
    public static final String TOKEN_GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    /**
     * 用code换取oauth2的access token
     */
    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 刷新oauth2的access token
     */
    public static final String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    /**
     * 用oauth2获取用户信息
     */
    public static final String OAUTH2_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=%s";
    /**
     * 验证oauth2的access token是否有效
     */
    public static final String OAUTH2_VALIDATE_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
    /**
     * 获取微信服务器IP地址
     */
    public static final String GET_CALLBACK_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip";
    /**
     * 第三方使用网站应用授权登录的url
     */
    public static final String QRCONNECT_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * oauth2授权的url连接
     */
    public static final String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    /**
     * 获取公众号的自动回复规则
     */
    public static final String GET_CURRENT_AUTOREPLY_INFO_URL = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info";

    /**
     * 公众号调用或第三方平台帮公众号调用对公众号的所有api调用（包括第三方帮其调用）次数进行清零
     */
    public static final String CLEAR_QUOTA_URL = "https://api.weixin.qq.com/cgi-bin/clear_quota";

    /**
     * 通过openId 获取公众号用户详细信息
     */
    public static final String OAUTH2_USERINFO_URLS = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=%s";

    /**
     * 公众号发送模板消息链接
     */
    public static final String SEND_MP_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";




    /**
     *  获取接口访问凭证
     * @author guoconglin
     * @date 2020/4/10 10:44
     * @param: [appId 微信平台开账号的appId, appSecret 微信开放平台开账号后的appSecret]
     * @return: com.sanqi.common.utils.weixin.Token
     */
    public static Token getToken(String appId,String appSecret) {
        Token token = null;
        String requestUrl = String.format(TOKEN_URL,appId,appSecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        LOG.info("获取token jsonObject jsonObject:{}", jsonObject);
        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getIntValue("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                LOG.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return token;
    }

    /**
     * 刷新token
     * @param appId 微信平台开的唯一标识
     * @param accessToken token
     * @return
     */
    public static String getRefreshToken(String appId,String accessToken){
        String refreshToken = String.format(OAUTH2_REFRESH_TOKEN_URL,appId,accessToken);
        return refreshToken;
    }

    /**
     * 验证token是否有效
     * @param refreshToken 刷新获取到的新token
     * @param appId 微信平台开的唯一标识
     * @return
     */
    public static boolean validateToken(String refreshToken,String appId){
        String refreshTokenUrl = String.format(OAUTH2_VALIDATE_TOKEN_URL,refreshToken,appId);
        JSONObject jsonObject = httpsRequest(refreshTokenUrl, "GET", null);
        if(jsonObject != null){
            //TODO需要判断
            int errcode = jsonObject.getIntValue("errcode");
            if(errcode == 0){ //则证明有效
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    /**
     * 获取微信授权页面链接
     * @param appId 微信平台开的唯一标识
     * @param redirectURI 回调地址
     * @param scope
     * @param state
     * @return
     */
    public static String getAuthorizeUrl(String appId,String redirectURI, String scope, String state){
        String authorizeUrl = String.format(QRCONNECT_URL,appId,URIUtil.encodeURIComponent(redirectURI),scope, StringUtils.trimToEmpty(state));
        return authorizeUrl;
    }

   /**
    * 用code换取oauth2的access token
    * @author guoconglin
    * @date 2020/4/10 12:55
    * @param: [code, appId 微信平台唯一识别appId, appSecret 微信平台唯一识别appSecret]
    * @return: com.sanqi.common.utils.weixin.WeiXinOauth2Token
    */
    public static WeiXinOauth2Token getOauth2AccessToken(String code,String appId,String appSecret){
        WeiXinOauth2Token weixinOauth2Token = new WeiXinOauth2Token();
        String getTokenUrl = String.format(OAUTH2_ACCESS_TOKEN_URL, appId, appSecret, code);
        JSONObject jsonObject = httpsRequest(getTokenUrl, "GET", null);
        LOG.info("获取网页授权凭证 jsonObject:{}",jsonObject);
        if (null != jsonObject) {
            if(jsonObject.getIntValue("errcode") == 0){
                weixinOauth2Token.setAccessToken(jsonObject.getString("access_token"));
                weixinOauth2Token.setExpiresIn(jsonObject.getIntValue("expires_in"));
                weixinOauth2Token.setRefreshToken(jsonObject.getString("refresh_token"));
                weixinOauth2Token.setOpenId(jsonObject.getString("openid"));
                weixinOauth2Token.setScope(jsonObject.getString("scope"));
                weixinOauth2Token.setErrcode(jsonObject.getIntValue("errcode"));
                weixinOauth2Token.setErrmsg(jsonObject.getString("errmsg"));
                weixinOauth2Token.setUnionId(jsonObject.containsKey("unionid")?jsonObject.getString("unionid"):null);
                LOG.info("获取网页授权凭证成功 weixinOauth2Token:{}",weixinOauth2Token);
            }else {
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                weixinOauth2Token.setErrcode(errorCode);
                weixinOauth2Token.setErrmsg(errorMsg);
                LOG.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return weixinOauth2Token;
    }


    /**
     * 通过网页授权获取用户信息
     * @author guoconglin
     * @date 2020/4/10 13:00
     * @param: [accessToken 获取的token, openId 用户的openId]
     * @return: com.sanqi.common.utils.weixin.WeiXinUserInfo
     */
    public static WeiXinUserInfo getUserInfo(String accessToken, String openId){
        WeiXinUserInfo weixinUserInfo = new WeiXinUserInfo();
        String userInfoUrl = String.format(OAUTH2_USERINFO_URL, accessToken, openId, "zh_CN");
        JSONObject jsonObject = httpsRequest(userInfoUrl, "GET", null);
        LOG.info("jsonObject:{}",jsonObject);
        LOG.info("jsonObject:{}",jsonObject.getIntValue("subscribe"));
        if (null != jsonObject) {
            try {
                if(jsonObject.getIntValue("errcode") == 0){
                    // 用户的标识
                    if(jsonObject.containsKey("openid")){
                        weixinUserInfo.setWxOpenid(jsonObject.getString("openid"));
                    }
                    if(jsonObject.containsKey("subscribe")){
                        // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                        int subscribe = jsonObject.getIntValue("subscribe");
                        weixinUserInfo.setSubscribe(subscribe);
                    }
                    // 用户关注时间
                    if(jsonObject.containsKey("subscribe_time")){
                        weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
                    }
                    // 昵称
                    if(jsonObject.containsKey("nickname")){
                        weixinUserInfo.setNickName(jsonObject.getString("nickname"));
                    }
                    // 用户的性别（1是男性，2是女性，0是未知）
                    if(jsonObject.containsKey("sex")){
                        weixinUserInfo.setSex(jsonObject.getIntValue("sex"));
                    }
                    // 用户所在国家
                    if(jsonObject.containsKey("country")){
                        weixinUserInfo.setCountry(jsonObject.getString("country"));
                    }
                    // 用户所在省份
                    if(jsonObject.containsKey("province")){
                        weixinUserInfo.setProvince(jsonObject.getString("province"));
                    }
                    // 用户所在城市
                    if(jsonObject.containsKey("city")){
                        weixinUserInfo.setCity(jsonObject.getString("city"));
                    }
                    // 用户的语言，简体中文为zh_CN
                    if(jsonObject.containsKey("language")){
                        weixinUserInfo.setLanguage(jsonObject.getString("language"));
                    }
                    // 用户头像
                    if(jsonObject.containsKey("headimgurl")){
                        weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                    }
                    //unionid
                    if(jsonObject.containsKey("unionid")){
                        weixinUserInfo.setUnionId(jsonObject.getString("unionid"));
                    }
                    int errorCode = jsonObject.getIntValue("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
                    weixinUserInfo.setErrcode(errorCode);
                    weixinUserInfo.setErrmsg(errorMsg);
                    LOG.info("获取用户信息成功 weixinUserInfo:{}", weixinUserInfo);
                }

            } catch (Exception e) {
                int errorCode = jsonObject.getIntValue("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                weixinUserInfo.setErrcode(errorCode);
                weixinUserInfo.setErrmsg(errorMsg);
                LOG.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return weixinUserInfo;
    }

    /**
     * 创建调用jsapi时所需要的签名
     * @author guoconglin
     * @date 2020/4/10 13:19
     * @param: [url, mpAppId, mpSecret]
     * @return: com.sanqi.common.utils.weixin.WxJsapiSignature
     */
    public static WxJsapiSignature createJsapiSignature(String url,String mpAppId,String mpSecret) throws ParseException {
        LOG.info("创建调用jsapi时所需要的签名 url:{} ",url);
        String token_str = null;
        Token token = tokenCache.get("accessToken");
        if(StringUtils.isNotNull(token)){
            int expiresIn = token.getExpiresIn();
            Calendar ever = Calendar.getInstance();
            Date date = new Date(expiresIn*1000L);
            ever.setTime(date);
            Calendar current = Calendar.getInstance();
            if(ever.after(current)){
                token_str = token.getAccessToken();
                LOG.info("获取的accessToken:{}",token_str);
            }else {
                token_str = getWxJsapiTokenStr(mpAppId, mpSecret);
            }
        }else{
            token_str = getWxJsapiTokenStr(mpAppId, mpSecret);
        }
        WxJsapiSignature wxJsapiSignature = null;
        String ticket_str = null;
        LOG.info("weixinCache:{}",weixinCache);
        JsApiTicket jsApiTicket = weixinCache.get("ticket");
        LOG.info("获取jsApiTicket成功 jsApiTicket:{}", jsApiTicket);
        if(jsApiTicket != null){
            int expiresIn = jsApiTicket.getExpiresIn();
            Calendar ever = Calendar.getInstance();
            Date date = new Date(expiresIn*1000L);
            ever.setTime(date);
            Calendar current = Calendar.getInstance();
            if(ever.after(current)) {
                ticket_str = jsApiTicket.getTicket();
                LOG.info("获取jsApiTicket成功 ticket_str:{}", ticket_str);
            }else{
                String ticketUrl = String.format(TOKEN_GET_JSAPI_TICKET_URL,token_str);
                JSONObject jsonObject = httpsRequest(ticketUrl, "GET", null);
                if(jsonObject != null){
                    try {
                        ticket_str = jsonObject.getString("ticket");
                        JsApiTicket jsApiTicket1 = new JsApiTicket();
                        weixinCache.remove("ticket");
                        jsApiTicket1.setTicket(jsonObject.getString("ticket"));
                        LOG.info("获取jticket的值为: ticket:{}",jsonObject.getString("ticket"));
                        jsApiTicket1.setExpiresIn(jsonObject.getIntValue("expires_in"));
                        weixinCache.put("ticket", jsApiTicket1);
                    } catch (JSONException e) {
                        token = null;
                        // 获取jsApiTicket失败
                        LOG.error("获取jsApiTicket失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
                    }
                }
            }
            Map<String, String> sign = SignUtil.sign(ticket_str, url);
            wxJsapiSignature = new WxJsapiSignature();
            wxJsapiSignature.setAppId(mpAppId);
            wxJsapiSignature.setNonceStr(sign.get("nonceStr"));
            wxJsapiSignature.setTimestamp(Long.parseLong(sign.get("timestamp")));
            wxJsapiSignature.setSignature(sign.get("signature"));
            LOG.info("获取jsApiTicket成功 wxJsapiSignature:{}", wxJsapiSignature);
        }else{
            String ticketUrl = String.format(TOKEN_GET_JSAPI_TICKET_URL,token_str);
            JSONObject jsonObject = httpsRequest(ticketUrl, "GET", null);
            LOG.info("jsonObject 请求jsonObject:{}",jsonObject);
            if(jsonObject != null){
                try {
                    JsApiTicket jsApiTicket1 = new JsApiTicket();
                    weixinCache.remove("ticket");
                    jsApiTicket1.setTicket(jsonObject.getString("ticket"));
                    jsApiTicket1.setExpiresIn(jsonObject.getIntValue("expires_in"));
                    weixinCache.put("ticket", jsApiTicket1);
                    LOG.info("获取ticket成功 ========= ticket:{}", jsApiTicket1);
                    Map<String, String> sign = SignUtil.sign(jsApiTicket1.getTicket(), url);
                    wxJsapiSignature = new WxJsapiSignature();
                    wxJsapiSignature.setAppId(mpAppId);
                    wxJsapiSignature.setNonceStr(sign.get("nonceStr"));
                    wxJsapiSignature.setTimestamp(Long.parseLong(sign.get("timestamp")));
                    wxJsapiSignature.setSignature(sign.get("signature"));
                    LOG.info("获取jsApiTicket成功 ========= wxJsapiSignature:{}", wxJsapiSignature);
                } catch (JSONException e) {
                    token = null;
                    // 获取jsApiTicket失败
                    LOG.error("获取jsApiTicket失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
                }
            }
        }
        return wxJsapiSignature;
    }

    private static String getWxJsapiTokenStr(String mpAppId,String mpSecret){
        String token_str = null;
        Token token = getToken(mpAppId, mpSecret);
        LOG.info("获取的token1:{}",token);
        if(StringUtils.isNotNull(token)){
            token_str = token.getAccessToken();
            tokenCache.remove("accessToken");
            tokenCache.put("accessToken",token);
        }else{
            LOG.info("失败{} {}");
        }
        LOG.info("获取的第二次accessToken:{}",token_str);
        return token_str;
    }

    /**
     * 创建临时带参数二维码
     * @param accessToken
     * @expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
     * @param sceneId 场景Id
     * @return
     */
    public static String createTempTicket(String accessToken, String expireSeconds, int sceneId) {
        WechatQRCode wxQRCode = null;
        Map<String,Integer> intMap = new HashMap<String,Integer>();
        intMap.put("scene_id",sceneId);
        Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();
        mapMap.put("scene", intMap);
        //
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("expire_seconds", expireSeconds);
        paramsMap.put("action_name", QR_SCENE);
        paramsMap.put("action_info", mapMap);
        String data = new Gson().toJson(paramsMap);
        System.out.println("创建临时带参数二维码参数"+data);
        String requestUrl = String.format(create_ticket_path,accessToken);
        data = HttpUtils.sendPost( requestUrl, data);
        System.out.println("创建临时带参数二维码返回结果"+data);
        try {
            wxQRCode = new Gson().fromJson(data, WechatQRCode.class);
        } catch (JsonSyntaxException e) {
            wxQRCode = null;
            e.printStackTrace();
        }
        System.out.println("创建临时带参数二维码返回转换对象"+wxQRCode);
        return wxQRCode==null?null:wxQRCode.getUrl();

    }

    /**
     * 创建永久二维码(数字)
     * @param accessToken
     * @param sceneId 场景Id
     * @return
     */
    public static String createForeverTicket(String accessToken, int sceneId) {

        //output data
        Map<String,Integer> intMap = new HashMap<String,Integer>();
        intMap.put("scene_id",sceneId);
        Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();
        mapMap.put("scene", intMap);
        //
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("action_name", QR_LIMIT_SCENE);
        paramsMap.put("action_info", mapMap);
        String data = new Gson().toJson(paramsMap);
        System.out.println("创建临时带参数二维码参数"+data);
        String requestUrl = String.format(create_ticket_path,accessToken);
        data = HttpUtils.sendPost( requestUrl, data);
        System.out.println("创建临时带参数二维码返回结果"+data);
        WechatQRCode wxQRCode = null;
        try {
            wxQRCode = new Gson().fromJson(data, WechatQRCode.class);
        } catch (JsonSyntaxException e) {
            wxQRCode = null;
            e.printStackTrace();
        }
        return wxQRCode==null?null:wxQRCode.getTicket();
    }

    /**
     * 创建永久二维码(字符串)
     *
     * @param accessToken
     * @param sceneStr 场景str
     * @return
     */
    public static String createForeverStrTicket(String accessToken, String sceneStr){
        //output data
        Map<String,String> intMap = new HashMap<String,String>();
        intMap.put("scene_str",sceneStr);
        Map<String,Map<String,String>> mapMap = new HashMap<String,Map<String,String>>();
        mapMap.put("scene", intMap);

        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("action_name", QR_LIMIT_STR_SCENE);
        paramsMap.put("action_info", mapMap);
        String data = new Gson().toJson(paramsMap);
        System.out.println("创建临时带参数二维码参数"+data);
        String requestUrl = String.format(create_ticket_path,accessToken);
        data = HttpUtils.sendPost( requestUrl, data);
        System.out.println("创建临时带参数二维码返回结果"+data);
        WechatQRCode wxQRCode = null;
        try {
            wxQRCode = new Gson().fromJson(data, WechatQRCode.class);
        } catch (JsonSyntaxException e) {
            wxQRCode = null;
        }
        return wxQRCode==null?null:wxQRCode.getTicket();
    }


    /**
     * 获取二维码ticket后，通过ticket换取二维码图片展示
     * @param ticket
     * @return
     */
    public static String showQrcode(String ticket){
        System.out.println("通过ticket换取二维码图片展示参数==="+ticket);
        String s = null;
        try {
            s =  HttpUtils.sendGet(String.format(showqrcode_path,urlEncodeUTF8(ticket)),"");
            System.out.println("通过ticket换取二维码图片展示参数==="+s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 长链接生成短链接
     * @param longUrl 长链接地址
     * @param accessToken token令牌
     * @return
     */
    public static String Long2Short(String longUrl,String accessToken){
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("action", "long2short");
        paramsMap.put("long_url", longUrl);
        String data = new Gson().toJson(paramsMap);
        String requestUrl = String.format(SHORTURL_API_URL,accessToken);
        data = HttpUtils.sendPost(requestUrl,data);
        JSONObject jsonObject = JSONObject.parseObject(data);
        return jsonObject.getString("short_url");
    }







    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            LOG.error("连接超时：{}", ce);
        } catch (Exception e) {
            LOG.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

}
