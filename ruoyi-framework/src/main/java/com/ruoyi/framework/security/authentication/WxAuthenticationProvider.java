package com.ruoyi.framework.security.authentication;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.config.WxMaServiceUtil;
import com.ruoyi.framework.web.service.UserDetailsServiceImpl;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.office.mapper.WxUserMapper;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class WxAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private UserDetailsServiceImpl userDetailsService;

    private WxUserMapper userMapper;

    @Autowired WxMaServiceUtil wxMaServiceUtil;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WxLoginBody loginBody = (WxLoginBody) authentication.getCredentials();
        try {
            String openId = null, unionId = null, mpOpenId = null;
            if(wxMpService.getWxMpConfigStorage().getAppId().equals(loginBody.getAppId())){
                WxOAuth2AccessToken token = wxMpService.getOAuth2Service().getAccessToken(loginBody.getCode());
                mpOpenId = token.getOpenId();
                unionId = token.getUnionId();
            }else{
                WxMaService wxMaService = StringUtils.isEmpty(loginBody.getAppId()) ?
                        wxMaServiceUtil.getMaServiceByName(loginBody.getAppName()) : wxMaServiceUtil.getMaServiceById(loginBody.getAppId());
                WxMaJscode2SessionResult result = wxMaService.jsCode2SessionInfo(loginBody.getCode());
                openId = result.getOpenid();
                unionId = result.getUnionid();
            }

            WxUser wxUser = userMapper.selectUserByUnionId(unionId);
            if (wxUser == null) {
                wxUser = new WxUser();
                wxUser.setNickName("微信用户");
                if(openId != null){
                    wxUser.setOpenId(openId);
                }
                if(mpOpenId != null){
                    wxUser.setMpOpenId(mpOpenId);
                }
                wxUser.setUnionId(unionId);
                wxUser.setCreateBy("admin");
                wxUser.setCreateTime(new Date());
                wxUser.setLoginDate(DateUtils.getNowDate());
                wxUser.setLoginIp(IpUtils.getIpAddr());
                if (StringUtils.isNotEmpty(loginBody.getInviteCode())) {
                    wxUser.setShareCode(loginBody.getInviteCode());
                    // 新用户奖励 待补充

                }
                userMapper.insertWxUser(wxUser);
            } else {
                if (UserStatus.DISABLE.getCode().equals(wxUser.getStatus())) {
                    log.info("登录用户：{} 已被停用.", wxUser.getUnionId());
                    throw new ServiceException("对不起，您的账号：" + wxUser.getUnionId() + " 已停用");
                }
                if(openId != null){
                    wxUser.setOpenId(openId);
                }
                if(mpOpenId != null){
                    wxUser.setMpOpenId(mpOpenId);
                }
                wxUser.setUpdateTime(DateUtils.getNowDate());
                wxUser.setLoginDate(DateUtils.getNowDate());
                userMapper.updateWxUser(wxUser);
            }
            UserDetails userDetails = userDetailsService.createWxLoginUser(wxUser);
            WxAuthenticationToken authenticationToken = new WxAuthenticationToken(userDetails, loginBody, userDetails.getAuthorities());
            return authenticationToken;
        } catch (WxErrorException e) {
            throw new ServiceException("微信小程序登录出错");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WxAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUserMapper(WxUserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
