package com.ruoyi.office.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.office.domain.vo.MerchantUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserMapper;
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.service.ITWxUserService;

import javax.annotation.Resource;

/**
 * 微信用户信息 t_wx_userService业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserServiceImpl extends ServiceImpl<TWxUserMapper, TWxUser> implements ITWxUserService {
    @Autowired
    private TWxUserMapper tWxUserMapper;

    /**
     * 查询微信用户信息 t_wx_user
     *
     * @param id 微信用户信息 t_wx_user主键
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public TWxUser selectTWxUserById(Long id) {
        return tWxUserMapper.selectTWxUserById(id);
    }

    /**
     * 查询微信用户信息 t_wx_user列表
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public List<TWxUser> selectTWxUserList(TWxUser tWxUser) {
        return tWxUserMapper.selectTWxUserList(tWxUser);
    }

    /**
     * 新增微信用户信息 t_wx_user
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    @Override
    public int insertTWxUser(TWxUser tWxUser) {
        tWxUser.setCreateTime(DateUtils.getNowDate());
        return tWxUserMapper.insertTWxUser(tWxUser);
    }

    /**
     * 修改微信用户信息 t_wx_user
     *
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    @Override
    public int updateTWxUser(TWxUser tWxUser) {
        tWxUser.setUpdateTime(DateUtils.getNowDate());
        return tWxUserMapper.updateTWxUser(tWxUser);
    }

    /**
     * 批量删除微信用户信息 t_wx_user
     *
     * @param ids 需要删除的微信用户信息 t_wx_user主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserByIds(Long[] ids) {
        return tWxUserMapper.deleteTWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息 t_wx_user信息
     *
     * @param id 微信用户信息 t_wx_user主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserById(Long id) {
        return tWxUserMapper.deleteTWxUserById(id);
    }

    @Override
    public List<MerchantUserVo> listStoreWxuser(TWxUser tWxUser) {
        return baseMapper.listStoreWxuser(tWxUser);
    }

    @Override
    public void updateLogInfo(WxUser wxUser) {
        UpdateWrapper<TWxUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(TWxUser::getUnionId, wxUser.getUnionId());
        updateWrapper.lambda().set(TWxUser::getLoginIp, wxUser.getLoginIp()).set(TWxUser::getLoginDate, new Date())
                .set(TWxUser::getUpdateTime, new Date());
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void regWithShare(String unionId, String inviteCode) {
        UpdateWrapper<TWxUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(TWxUser::getUnionId, unionId);
        final TWxUser wxUser = baseMapper.selectOne(updateWrapper);
        if (wxUser != null) {
            updateWrapper.lambda().set(TWxUser::getShareCode, inviteCode)
                    .set(TWxUser::getLoginIp, IpUtils.getIpAddr())
                    .set(TWxUser::getLoginDate, DateUtils.getNowDate());
            tWxUserMapper.update(null, updateWrapper);
        } else {
            TWxUser newUser = new TWxUser();
            newUser.setUnionId(unionId);
            newUser.setShareCode(inviteCode);
            newUser.setLoginIp(IpUtils.getIpAddr());
            newUser.setLoginDate(DateUtils.getNowDate());
            tWxUserMapper.insertTWxUser(newUser);
        }
        throw new ServiceException("只更新了分享码,奖励操作暂未执行");
    }

}
