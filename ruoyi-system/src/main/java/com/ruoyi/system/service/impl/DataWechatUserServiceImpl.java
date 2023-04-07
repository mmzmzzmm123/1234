package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.system.biz.token.LoginUser;
import com.ruoyi.system.biz.token.TokenServiceImpl;
import com.ruoyi.system.domain.dto.WxLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DataWechatUserMapper;
import com.ruoyi.system.domain.DataWechatUser;
import com.ruoyi.system.service.IDataWechatUserService;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 微信用户信息Service业务层处理
 * 
 * @author genius
 * @date 2023-04-06
 */
@Service
public class DataWechatUserServiceImpl implements IDataWechatUserService
{

    /**
     * 微信 appid
     */
    @Value("${wechat.appId}")
    private String appId;

    /**
     * 微信appSecret
     */
    @Value("${wechat.appSecret}")
    private String appSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private DataWechatUserMapper dataWechatUserMapper;

    /**
     * 查询微信用户信息
     *
     * @param userId 微信用户信息ID
     * @return 微信用户信息
     */
    @Override
    public DataWechatUser selectDataWechatUserById(Long userId)
    {
        return dataWechatUserMapper.selectDataWechatUserById(userId);
    }

    /**
     * 查询微信用户信息列表
     *
     * @param dataWechatUser 微信用户信息
     * @return 微信用户信息
     */
    @Override
    public List<DataWechatUser> selectDataWechatUserList(DataWechatUser dataWechatUser)
    {
        return dataWechatUserMapper.selectDataWechatUserList(dataWechatUser);
    }

    /**
     * 新增微信用户信息
     *
     * @param dataWechatUser 微信用户信息
     * @return 结果
     */
    @Override
    public int insertDataWechatUser(DataWechatUser dataWechatUser)
    {
        return dataWechatUserMapper.insertDataWechatUser(dataWechatUser);
    }

    /**
     * 修改微信用户信息
     *
     * @param dataWechatUser 微信用户信息
     * @return 结果
     */
    @Override
    public int updateDataWechatUser(DataWechatUser dataWechatUser)
    {
        return dataWechatUserMapper.updateDataWechatUser(dataWechatUser);
    }

    /**
     * 批量删除微信用户信息
     *
     * @param userIds 需要删除的微信用户信息ID
     * @return 结果
     */
    @Override
    public int deleteDataWechatUserByIds(Long[] userIds)
    {
        return dataWechatUserMapper.deleteDataWechatUserByIds(userIds);
    }

    /**
     * 删除微信用户信息信息
     *
     * @param userId 微信用户信息ID
     * @return 结果
     */
    @Override
    public int deleteDataWechatUserById(Long userId)
    {
        return dataWechatUserMapper.deleteDataWechatUserById(userId);
    }

    public LoginUser login(String code) {

        WxLoginResponse result = wxLogin(code);
        LoginUser response ;

        if (WxLoginResponse.SUCCESS_CODE == result.getErrcode()) {

            response = new LoginUser();

            //用户存在则返回用户信息(和token)，不存在则创建新用户
            String openId = result.getOpenid();
            //根据openId获取用户对象
            DataWechatUser user = this.selectUserByOpenId(openId);

            if (user != null) { //数据库存在用户
                String token = getToken(user);
                response.setUser(user);
                response.setToken(token);
                response.setSessionKey(result.getSessionKey());
            }else{// 未注册用户没有 token 值
                DataWechatUser userNobody =new DataWechatUser();
                userNobody.setOpenId(openId);
                response.setUser(userNobody);
                response.setSessionKey(result.getSessionKey());
            }

        } else {
            throw new UserException(result.getErrmsg());
        }
        return response;
    }

    private String getToken(DataWechatUser user) {
        LoginUser pbaLoginUser = new LoginUser();
        pbaLoginUser.setUser(user);
        return tokenService.createToken(pbaLoginUser);
    }


    @Override
    public LoginUser register(DataWechatUser user) {
        LoginUser response = new LoginUser();
        this.insertDataWechatUser(user);
        String token = getToken(user);
        response.setUser(user);
        response.setToken(token);
        return response;
    }

    @Override
    public void loginOut(String token) {
        tokenService.delLoginUser(token);
    }

    @Override
    public WxLoginResponse wxLogin(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?";
        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(url);
        urlBuilder.queryParam("appid", appId);
        urlBuilder.queryParam("secret", appSecret);
        urlBuilder.queryParam("js_code", code);
        urlBuilder.queryParam("grant_type", "authorization_code");
        return restTemplate.getForEntity(urlBuilder.build().toUri(), WxLoginResponse.class).getBody();
    }

    @Override
    public DataWechatUser selectUserByOpenId(String openId) {
        return dataWechatUserMapper.selectUserByOpenId(openId);
    }
}
