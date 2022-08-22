package com.ruoyi.ibaiqi.wxUserApi;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxAppconfig;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxUser;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxAppconfigService;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxUserService;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
/**
 * 实现微信授权登陆，并拥有ruoyi-vue的权限系统功能。
 * 通过微信openid实现登陆，登陆是通过openid表关联到用户表sys_user,
 * 再通过sys_user用中用户名和密码来登陆返回token
 * 因此实现openId使用若依前后端分离的权限系统，本质于使用諅系统完全一致
* */
@RestController
@RequestMapping("/api/v1/wxUser/")
public class WxLoginController {

    @Autowired
    private IIbWxUserService ibWxUserService;

    @Autowired
    private IIbWxAppconfigService ibWxAppconfigService;

    //为操作非微信管理用户
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysLoginService sysLoginService;

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
     * <p>
     * 设置思路：如果微信用户表中(ib_wx_user)没有存openId,先注册用户，再通过用户名和密码登陆返回token,得到userId,
     * 最后把userId和登陆获取的信息一并存到微信表中（ib_wx_user）
     *
     * @param object
     * object 包括有 wx.login登陆获取code 微信官方提供的临时凭证
     * @return
     */
    @Anonymous
    @PostMapping("/wxlogin")
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


        String nickNameStr = object.get("nickName").toString();
        //如果微信名称大于12，则取前边12字符
        if (nickNameStr.length() > 6) {
            nickNameStr = nickNameStr.substring(0, 5);
        }

        String userNameStr = nickNameStr + UUID.fastUUID().toString().substring(0, 7);
        System.out.println("注册userName的值：" + userNameStr);

        /**先通过openid来查询是否存在*/
        IbWxUser ibWxUser = ibWxUserService.selectIbWxUserOpenID(openid);

        if (ibWxUser == null) {

            /**
             * 为操作非微信openId管理用户表 ib_wx_user，即ruoyi-vue系统原管理用户sys_User，
             */
            //先注册一个随机用户
            SysUser user = new SysUser();
            user.setUserName(userNameStr); //必选
            user.setNickName(nickNameStr); //必选
            user.setPassword(SecurityUtils.encryptPassword("123456"));
            //注册用户 密码使用123456 所以如下登陆也应该使用123456
            boolean regFlag = userService.registerUser(user);
            //用户登陆，获取token
            String token2 = sysLoginService.loginByUserNamePassword(userNameStr, "123456");
            System.out.println("登陆后返回token2:" + token2);
            //todo 冒似传递到前端了
            data.put(Constants.TOKEN, token2);

            //注册过若依默认用户表，再注册微信登陆用户表 ib_wx_user
            if (regFlag) {
                /**如果不存在就插入到我们的数据库里*/

                SysUser sysUser = userService.selectUserByUserName(userNameStr);

                IbWxUser wxuser = new IbWxUser();
                wxuser.setNickname(String.valueOf(object.get("nickName")));
                wxuser.setGender((Integer) object.get("gender"));
                wxuser.setAvatar(String.valueOf(object.get("avatarUrl")));
                wxuser.setOpenid(openid);
                wxuser.setCreateTime(DateUtils.getNowDate());
                //todo userID 暂时存放于此
                wxuser.setCreateBy(sysUser.getUserId().toString());
                ibWxUserService.insertIbWxUser(wxuser);
            }


        } else {


            System.out.println("通openID查询到的用户如下,准备更新用户：");
            System.out.println(ibWxUser);
            //通过openId找到关闻userId,再查询到当前需要登陆用户信息，如用户名和密码
            SysUser userCurrent = userService.selectUserById(Long.parseLong(ibWxUser.getCreateBy()));

            String token2;
            //后边密码为初始密码，不能用存储后的加密密码。
            token2 = sysLoginService.loginByUserNamePassword(userCurrent.getUserName(), "123456");
            data.put(Constants.TOKEN, token2);


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
