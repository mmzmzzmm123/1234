package com.ruoyi.psychology.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyUserMapper;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;

/**
 * 用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-26
 */
@Service
public class PsyUserServiceImpl implements IPsyUserService 
{
    @Autowired
    private PsyUserMapper psyUserMapper;

    /**
     * 查询用户
     * 
     * @param id 用户主键
     * @return 用户
     */
    @Override
    public PsyUser selectPsyUserById(String id)
    {
        return psyUserMapper.selectPsyUserById(id);
    }

    /**
     * 查询用户列表
     * 
     * @param psyUser 用户
     * @return 用户
     */
    @Override
    public List<PsyUser> selectPsyUserList(PsyUser psyUser)
    {
        return psyUserMapper.selectPsyUserList(psyUser);
    }

    /**
     * 新增用户
     * 
     * @param psyUser 用户
     * @return 结果
     */
    @Override
    public int insertPsyUser(PsyUser psyUser)
    {
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
    public int updatePsyUser(PsyUser psyUser)
    {
        return psyUserMapper.updatePsyUser(psyUser);
    }

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的用户主键
     * @return 结果
     */
    @Override
    public int deletePsyUserByIds(String[] ids)
    {
        return psyUserMapper.deletePsyUserByIds(ids);
    }

    /**
     * 删除用户信息
     * 
     * @param id 用户主键
     * @return 结果
     */
    @Override
    public int deletePsyUserById(String id)
    {
        return psyUserMapper.deletePsyUserById(id);
    }
}
