package com.ruoyi.api.user.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.user.model.dto.ApiUserDto;
import com.ruoyi.api.user.model.vo.ApiUserLevelVo;
import com.ruoyi.api.user.model.vo.ApiUserLikeDataVo;
import com.ruoyi.api.user.model.vo.ApiUserVo;
import com.ruoyi.api.user.model.vo.ApiUserWalletVo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.LongUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.domain.UserLevel;
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/19 17:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserService {

    private final UserInfoMapper userInfoMapper;
    private final UserLevelMapper userLevelMapper;
    private final UserWalletMapper userWalletMapper;
    private final UserLikeStaffRecordMapper userLikeStaffRecordMapper;
    private final UserLikeStaffTrendsRecordMapper userLikeStaffTrendsRecordMapper;
    private final WxService wxService;

    /**
     * 小程序用户登录
     *
     * @param code 登录code
     * @return 结果
     */
    public R<ApiUserVo> wxMiniLogin(String code) {
        log.info("小程序用户登录：开始，参数：{}", code);
        if (StringUtils.isBlank(code)) {
            log.info("小程序用户登录：失败，参数为空");
            return R.fail();
        }
        ApiUserVo vo = new ApiUserVo();
        //请求微信获取对应sessionInfo
        WxMaJscode2SessionResult wxMaJscode2SessionResult = null;
        try {
            wxMaJscode2SessionResult = wxService.getWxMiniSessionInfo(code);
            if (ObjectUtil.isNull(wxMaJscode2SessionResult)) {
                return R.fail("小程序用户登录：失败，请求微信的数据对象空了");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
        UserInfo userInfo = userInfoMapper.selectByOpenId(wxMaJscode2SessionResult.getOpenid());
        userInfo = userInfo == null ? new UserInfo() : userInfo;
        userInfo.setOpenId(wxMaJscode2SessionResult.getOpenid())
                .setSessionKey(wxMaJscode2SessionResult.getSessionKey())
                .setUnionId(wxMaJscode2SessionResult.getUnionid())
                .setLastLoginTime(DateUtils.getNowDate());
        //如果微信用户不存在，表明该用户是首次进入小程序，需要进行首次登记
        if (ObjectUtil.isNull(userInfo.getId())) {
            userInfo.setNickName(StringUtils.generateUniqueNameWithAnimeElement())
                    .setCreateTime(userInfo.getLastLoginTime());
            userInfoMapper.insertUserInfo(userInfo);
        } else {
            userInfoMapper.updateUserInfo(userInfo);
        }
        BeanUtils.copyBeanProp(vo, userInfo);
        // 查询用户等级数据
        UserLevel userLevel = userLevelMapper.selectByUserId(userInfo.getId());
        ApiUserLevelVo levelVo = new ApiUserLevelVo();
        BeanUtils.copyBeanProp(levelVo, userLevel);
        vo.setUserLevelVo(levelVo);
        // 查询用户钱包数据
        UserWallet userWallet = userWalletMapper.selectByUserId(userInfo.getId());
        ApiUserWalletVo walletVo = new ApiUserWalletVo();
        BeanUtils.copyBeanProp(walletVo, userWallet);
        vo.setUserWalletVo(walletVo);
        log.info("小程序用户登录：完成，返回数据：{}", vo);
        return R.ok(vo);
    }

    /**
     * 用户信息更新
     *
     * @param dto 数据
     * @return 结果
     */
    public Boolean update(ApiUserDto dto) {
        log.info("用户信息更新：开始，参数：{}", dto);
        UserInfo updateInfo = new UserInfo();
        BeanUtils.copyBeanProp(updateInfo, dto);
        userInfoMapper.updateUserInfo(updateInfo);
        log.info("用户信息更新：完成");
        return Boolean.TRUE;
    }

    /**
     * 生成用户推荐码
     *
     * @param userId 用户标识
     * @return 结果
     */
    public String generateReferralCode(Long userId) {
        log.info("生成用户推荐码：开始，参数：{}", userId);
        Long code = LongUtils.generateReferralCode();
        code = recursionGenerateCode(code);
        UserInfo update = new UserInfo();
        update.setId(userId)
                .setReferralCode(code);
        userInfoMapper.updateUserInfo(update);
        log.info("生成用户推荐码：完成，返回参数：{}", code);
        return code.toString();
    }

    /**
     * 递归构成唯一性的推荐码
     *
     * @param code 初始化code
     * @return 唯一性code
     */
    private Long recursionGenerateCode(Long code) {
        UserInfo select = new UserInfo();
        select.setReferralCode(code);
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoList(select);
        if (ObjectUtil.isNotEmpty(userInfos)) {
            return this.recursionGenerateCode(LongUtils.generateReferralCode());
        }
        return code;
    }

    /**
     * 获取用户点赞收藏记录数据
     *
     * @param userId 用户标识
     * @return 结果
     */
    public ApiUserLikeDataVo selectUserLikeData(Long userId) {
        log.info("获取用户点赞收藏记录数据：开始，参数：{}", userId);
        ApiUserLikeDataVo vo = new ApiUserLikeDataVo();
        List<Long> likeStaffUserIdList = new ArrayList<>();
        List<Long> likeStaffTrendsIdList = new ArrayList<>();
        // 开始查询
        if (ObjectUtil.isNotNull(userId)) {
            // 查询收藏员工记录
            List<Long> tempStaffUserId = userLikeStaffRecordMapper.selectStaffUserIdByUserId(userId);
            if (ObjectUtil.isNotEmpty(tempStaffUserId)) {
                likeStaffUserIdList = tempStaffUserId;
            }
            // 查询点赞动态记录
            List<Long> tempStaffTrendsIdList = userLikeStaffTrendsRecordMapper.selectTrendsIdByUserId(userId);
            if (ObjectUtil.isNotEmpty(tempStaffTrendsIdList)){
                likeStaffTrendsIdList = tempStaffTrendsIdList;
            }
        }
        vo.setLikeStaffUserIdList(likeStaffUserIdList)
                .setLikeStaffTrendsIdList(likeStaffTrendsIdList);
        log.info("获取用户点赞收藏记录数据：完成，返回数据：{}", vo);
        return vo;
    }
}
