package com.ruoyi.psychology.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.vo.LoginVO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.mapper.PsyUserMapper;
import com.ruoyi.psychology.service.IPsyUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户Service业务层处理
 *
 * @author ruoyi
 * @date 2022-08-26
 */
@Service
public class PsyUserServiceImpl implements IPsyUserService {
    @Resource
    private PsyUserMapper psyUserMapper;

    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public PsyUser selectPsyUserById(String id) {
        return psyUserMapper.selectPsyUserById(id);
    }

    /**
     * 查询用户列表
     *
     * @param psyUser 用户
     * @return 用户
     */
    @Override
    public List<PsyUser> selectPsyUserList(PsyUser psyUser) {
        return psyUserMapper.selectPsyUserList(psyUser);
    }

    /**
     * 新增用户
     *
     * @param psyUser 用户
     * @return 结果
     */
    @Override
    public int insertPsyUser(PsyUser psyUser) {
        psyUser.setCreateTime(DateUtils.getNowDate());
        return psyUserMapper.insertPsyUser(psyUser);
    }

    /**
     * 修改用户
     *
     * @param psyUser 用户
     * @return 结果
     */
    @Override
    public int updatePsyUser(PsyUser psyUser) {
        return psyUserMapper.updatePsyUser(psyUser);
    }

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deletePsyUserByIds(String[] ids) {
        return psyUserMapper.deletePsyUserByIds(ids);
    }

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deletePsyUserById(String id) {
        return psyUserMapper.deletePsyUserById(id);
    }

    @Override
    public PsyUser queryUserByAccount(String account) {
        return psyUserMapper.queryUserByAccount(account);
    }

    @Override
    public LoginVO checkPsyUser(String openId, JSONObject userInfo) {
        //去数据库查询此微信是否绑定过手机
        PsyUser user = psyUserMapper.queryUserByAccount(openId);

        String nickname = userInfo.getString("nickname");
        String headImgUrl = userInfo.getString("headimgurl");

        //用户信息为空，则插入一条数据
        Integer id = null;
        if (user == null) {
            id = psyUserMapper.insertPsyUser(PsyUser.builder().wxOpenid(openId).name(nickname).avatar(headImgUrl).build());
        } else {
            id = Integer.valueOf(user.getId());
        }
        return LoginVO.builder().userId(id).name(nickname).avatar(headImgUrl).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindPhone(LoginDTO loginDTO) {
        PsyUser user = psyUserMapper.queryUserByAccount(loginDTO.getWxOpenId());
        PsyUser phoneUser = psyUserMapper.queryUserByAccount(loginDTO.getPhone());
        //若该手机号已经存在用户，直接将微信信息更新至该用户,并删除此微信用户
        if (phoneUser != null) {
            psyUserMapper.updatePsyUser(PsyUser.builder().id(phoneUser.getId()).wxOpenid(user.getWxOpenid()).avatar(user.getAvatar()).name(user.getName()).build());
            psyUserMapper.deletePsyUserById(user.getId());
            loginDTO.setUserId(phoneUser.getId());
        } else {
            psyUserMapper.updatePsyUser(PsyUser.builder().id(user.getId()).phone(loginDTO.getPhone()).build());
            loginDTO.setUserId(user.getId());
        }
    }
}
