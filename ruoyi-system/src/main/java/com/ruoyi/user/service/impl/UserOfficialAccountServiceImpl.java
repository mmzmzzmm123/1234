package com.ruoyi.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.weixin.WxService;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.stereotype.Service;
import com.ruoyi.user.mapper.UserOfficialAccountMapper;
import com.ruoyi.user.domain.UserOfficialAccount;
import com.ruoyi.user.service.IUserOfficialAccountService;

/**
 * 微信公众号用户Service业务层处理
 *
 * @author Lam
 * @date 2023-10-14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserOfficialAccountServiceImpl implements IUserOfficialAccountService {

    private final UserOfficialAccountMapper userOfficialAccountMapper;
    private final WxMpService wxOfficialAccountService;

    /**
     * 查询微信公众号用户
     *
     * @param id 微信公众号用户主键
     * @return 微信公众号用户
     */
    @Override
    public UserOfficialAccount selectUserOfficialAccountById(Long id) {
        return userOfficialAccountMapper.selectUserOfficialAccountById(id);
    }

    /**
     * 查询微信公众号用户列表
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 微信公众号用户
     */
    @Override
    public List<UserOfficialAccount> selectUserOfficialAccountList(UserOfficialAccount userOfficialAccount) {
        return userOfficialAccountMapper.selectUserOfficialAccountList(userOfficialAccount);
    }

    /**
     * 新增微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    @Override
    public int insertUserOfficialAccount(UserOfficialAccount userOfficialAccount) {
        return userOfficialAccountMapper.insertUserOfficialAccount(userOfficialAccount);
    }

    /**
     * 拉取微信公众号用户列表
     *
     * @return 结果
     * */
    @Override
    public AjaxResult pullNewUserList() {
        log.info("拉取微信公众号用户列表：开始");
        UserOfficialAccount userOfficialAccount = userOfficialAccountMapper.selectMaxRecord();
        WxMpUserList wxMpUserList = null;
        try {
            if (ObjectUtil.isNull(userOfficialAccount)){
                wxMpUserList = wxOfficialAccountService.getUserService().userList();

                log.info("拉取微信公众号用户列表结果：{}", wxMpUserList);
            }else{
                wxMpUserList = wxOfficialAccountService.getUserService().userList(userOfficialAccount.getOpenId());
                log.info("拉取微信公众号用户列表结果：{}", wxMpUserList);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("请求微信端服务失败");
        }
        int total = 0;
        List<String> openIdList = wxMpUserList.getOpenids();
        if (ObjectUtil.isNotEmpty(openIdList)){
            total = openIdList.size();
            // 直接批量插入数据
            userOfficialAccountMapper.insertOfOpenIdList(openIdList);
        }
        log.info("拉取微信公众号用户列表：完成，成功拉取：{}", total);
        return AjaxResult.success(total+"");
    }

    /**
     * 批量更新新用户基本信息
     *
     * @return 结果
     * */
    @Override
    public AjaxResult updateNewUserInfo() {
        log.info("批量更新新用户基本信息：开始");
        List<UserOfficialAccount> userOfficialAccounts = userOfficialAccountMapper.selectOfUnionIdIsNull();
        if (ObjectUtil.isNull(userOfficialAccounts)){
            log.warn("批量更新新用户基本信息：暂无数据需要更新");
            return AjaxResult.success("0");
        }
        List<UserOfficialAccount> updateList = new ArrayList<>();
        List<WxMpUser> wxMpUserList = new ArrayList<>();
        List<String> openIdList = userOfficialAccounts.stream().map(UserOfficialAccount::getOpenId).collect(Collectors.toList());
        try {
            wxMpUserList = wxOfficialAccountService.getUserService().userInfoList(openIdList);
        }catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("更新失败，微信拉取数据异常");
        }
        if (ObjectUtil.isNotNull(wxMpUserList)){
            List<WxMpUser> finalWxMpUserList = wxMpUserList;
            userOfficialAccounts.forEach(item -> {
                WxMpUser mpUser = finalWxMpUserList.stream().filter(wxMpUser -> wxMpUser.getOpenId().equals(item.getOpenId())).findFirst().orElse(null);
                if (ObjectUtil.isNotNull(mpUser)){
                    UserOfficialAccount update = new UserOfficialAccount();
                    BeanUtils.copyBeanProp(update, mpUser);
                    update.setId(item.getId());
                    if (ObjectUtil.isNotNull(mpUser.getGroupId())){
                        update.setGroupId(mpUser.getGroupId().longValue());
                    }
                    if (ObjectUtil.isNotNull(mpUser.getSubscribeTime())){
                        update.setSubscribeTime(new Date(mpUser.getSubscribeTime()));
                    }
                    updateList.add(update);
                }
            });
            userOfficialAccountMapper.updateOfList(updateList);
        }
        log.info("批量更新新用户基本信息：完成");
        return AjaxResult.success(userOfficialAccounts.size()+"");
    }

    /**
     * 修改微信公众号用户
     *
     * @param userOfficialAccount 微信公众号用户
     * @return 结果
     */
    @Override
    public int updateUserOfficialAccount(UserOfficialAccount userOfficialAccount) {
        return userOfficialAccountMapper.updateUserOfficialAccount(userOfficialAccount);
    }

    /**
     * 批量删除微信公众号用户
     *
     * @param ids 需要删除的微信公众号用户主键
     * @return 结果
     */
    @Override
    public int deleteUserOfficialAccountByIds(Long[] ids) {
        return userOfficialAccountMapper.deleteUserOfficialAccountByIds(ids);
    }

    /**
     * 删除微信公众号用户信息
     *
     * @param id 微信公众号用户主键
     * @return 结果
     */
    @Override
    public int deleteUserOfficialAccountById(Long id) {
        return userOfficialAccountMapper.deleteUserOfficialAccountById(id);
    }
}
