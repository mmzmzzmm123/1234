package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TWxUserMapper;
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.service.ITWxUserService;

/**
 * 微信用户信息 t_wx_userService业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class TWxUserServiceImpl extends ServiceImpl<TWxUserMapper, TWxUser> implements ITWxUserService
{
    @Autowired
    private TWxUserMapper tWxUserMapper;

    /**
     * 查询微信用户信息 t_wx_user
     * 
     * @param id 微信用户信息 t_wx_user主键
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public TWxUser selectTWxUserById(Long id)
    {
        return tWxUserMapper.selectTWxUserById(id);
    }

    /**
     * 查询微信用户信息 t_wx_user列表
     * 
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 微信用户信息 t_wx_user
     */
    @Override
    public List<TWxUser> selectTWxUserList(TWxUser tWxUser)
    {
        return tWxUserMapper.selectTWxUserList(tWxUser);
    }

    /**
     * 新增微信用户信息 t_wx_user
     * 
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    @Override
    public int insertTWxUser(TWxUser tWxUser)
    {
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
    public int updateTWxUser(TWxUser tWxUser)
    {
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
    public int deleteTWxUserByIds(Long[] ids)
    {
        return tWxUserMapper.deleteTWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息 t_wx_user信息
     * 
     * @param id 微信用户信息 t_wx_user主键
     * @return 结果
     */
    @Override
    public int deleteTWxUserById(Long id)
    {
        return tWxUserMapper.deleteTWxUserById(id);
    }
}
