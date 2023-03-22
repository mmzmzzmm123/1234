package com.ruoyi.psychology.service;

import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.vo.LoginVO;
import com.ruoyi.psychology.domain.PsyUser;

/**
 * 用户Service接口
 *
 * @author ruoyi
 * @date 2022-08-26
 */
public interface IPsyUserService {
    /**
     * 查询用户
     *
     * @param id 用户主键
     * @return 用户
     */
    public PsyUser selectPsyUserById(String id);

    /**
     * 查询用户列表
     *
     * @param psyUser 用户
     * @return 用户集合
     */
    public List<PsyUser> selectPsyUserList(PsyUser psyUser);

    /**
     * 新增用户
     *
     * @param psyUser 用户
     * @return 结果
     */
    public int insertPsyUser(PsyUser psyUser);

    /**
     * 修改用户
     *
     * @param psyUser 用户
     * @return 结果
     */
    public int updatePsyUser(PsyUser psyUser);

    /**
     * 批量删除用户
     *
     * @param ids 需要删除的用户主键集合
     * @return 结果
     */
    public int deletePsyUserByIds(String[] ids);

    /**
     * 删除用户信息
     *
     * @param id 用户主键
     * @return 结果
     */
    public int deletePsyUserById(String id);

    PsyUser queryUserByAccount(String account);

    LoginVO checkPsyUser(String openId, JSONObject userInfo);

    void bindPhone(LoginDTO loginDTO);
}
