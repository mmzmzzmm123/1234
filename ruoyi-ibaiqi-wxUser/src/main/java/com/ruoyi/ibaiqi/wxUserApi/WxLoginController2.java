package com.ruoyi.ibaiqi.wxUserApi;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import com.ruoyi.ibaiqi.wxUser.domain.IbWxAppconfig;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxUser;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxAppconfigService;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxUserService;

/*import com.ruoyi.abuwx.domain.AbucoderWxappConfig;
import com.ruoyi.abuwx.domain.AbucoderWxuser;
import com.ruoyi.abuwx.service.IAbucoderWxappConfigService;
import com.ruoyi.abuwx.service.IAbucoderWxuserService;*/
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/wxUser/")
public class WxLoginController2 {

    /**
     * 测试接口
     *
     * @return
     */
    @GetMapping("test2")
    public AjaxResult test() {
        return AjaxResult.success("小程序api调试成功！~");
    }

    @Autowired
    private IIbWxUserService ibWxUserService;

    @Autowired
    private IIbWxAppconfigService ibWxAppconfigService;

    /**
     * 你自己的微信小程序APPID
     */

//    private final static String AppID = "你自己的微信小程序APPID";
    /**
     * 你自己的微信APP密钥
     */
//    private final static String AppSecret = "你自己的微信APP密钥";

    /**
     * 登录时获取的 code（微信官方提供的临时凭证）
     *
     * @param object
     * @return
     */
    @Anonymous
    @PostMapping("/wxlogin2")
    public AjaxResult wxLogin(@RequestBody JSONObject object) {
        //微信官方提供的微信小程序登录授权时使用的URL地址
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        System.out.println(object);
        /**
         * 拼接需要的参数
         * appid = AppID 你自己的微信小程序APPID
         * js_code = AppSecret 你自己的微信APP密钥
         * grant_type=authorization_code = code 微信官方提供的临时凭证
         */
        //todo 这些下午要慢慢对着改
        IbWxAppconfig ibWxAppconfig = ibWxAppconfigService.selectIbWxappConfig();
        String params = StrUtil.format("appid={}&secret={}&js_code={}&grant_type=authorization_code", ibWxAppconfig.getAppid(), ibWxAppconfig.getAppSecret(), object.get("code"));
        //开始发起网络请求,若依管理系统自带网络请求工具，直接使用即可
        String res = HttpUtils.sendGet(url, params);
        System.out.println("输出请求到的结果");
        System.out.println(res);
        JSONObject jsonObject = JSON.parseObject(res);
        String session_key = (String) jsonObject.get("session_key");
        String openid = (String) jsonObject.get("openid");
        if (StrUtil.isEmpty(openid)) {
            return AjaxResult.error("未获取到openid");
        }
        String token = UUID.randomUUID().toString();
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("session_key", session_key);
        data.put("openid", openid);
        data.put("state", true);
        data.put("nickName", object.get("nickName"));
        data.put("avatarUrl", object.get("avatarUrl"));
        /**先通过openid来查询是否存在*/
        IbWxUser ibWxUser = ibWxUserService.selectIbWxUserOpenID(openid);
        if (ibWxUser == null) {
            /**如果不存在就插入到我们的数据库里*/
            IbWxUser wxuser = new IbWxUser();
            wxuser.setNickname(String.valueOf(object.get("nickName")));
            wxuser.setGender((Integer) object.get("gender"));
            wxuser.setAvatar(String.valueOf(object.get("avatarUrl")));
            wxuser.setOpenid(openid);
            wxuser.setCreateTime(DateUtils.getNowDate());
            wxuser.setCreateBy("Abu");
            ibWxUserService.insertIbWxUser(wxuser);
        } else {
            /**如果存在就更新数据库里原有的数据*/
            ibWxUser.setNickname(String.valueOf(object.get("nickName")));
            ibWxUser.setGender((Integer) object.get("gender"));
            ibWxUser.setAvatar(String.valueOf(object.get("avatarUrl")));
            ibWxUser.setUpdateTime(DateUtils.getNowDate());
            ibWxUser.setUpdateBy("Abu");
            ibWxUserService.updateIbWxUser(ibWxUser);
        }
        /**返回结果集到前段*/
        return AjaxResult.success(data);
    }
}
