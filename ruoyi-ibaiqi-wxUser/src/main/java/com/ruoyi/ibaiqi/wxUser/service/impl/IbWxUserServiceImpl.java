package com.ruoyi.ibaiqi.wxUser.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ibaiqi.wxUser.mapper.IbWxUserMapper;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxUser;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxUserService;

/**
 * 微信用户Service业务层处理
 * 
 * @author zhangxuDev
 * @date 2022-08-17
 */
@Service
public class IbWxUserServiceImpl implements IIbWxUserService 
{
    @Autowired
    private IbWxUserMapper ibWxUserMapper;

    /**
     * 查询微信用户
     * 
     * @param id 微信用户主键
     * @return 微信用户
     */
    @Override
    public IbWxUser selectIbWxUserById(Long id)
    {
        return ibWxUserMapper.selectIbWxUserById(id);
    }

    /**
     * 查询微信用户列表
     * 
     * @param ibWxUser 微信用户
     * @return 微信用户
     */
    @Override
    public List<IbWxUser> selectIbWxUserList(IbWxUser ibWxUser)
    {
        return ibWxUserMapper.selectIbWxUserList(ibWxUser);
    }

    /**
     * 新增微信用户
     * 
     * @param ibWxUser 微信用户
     * @return 结果
     */
    @Override
    public int insertIbWxUser(IbWxUser ibWxUser)
    {
        ibWxUser.setCreateTime(DateUtils.getNowDate());
        return ibWxUserMapper.insertIbWxUser(ibWxUser);
    }

    /**
     * 修改微信用户
     * 
     * @param ibWxUser 微信用户
     * @return 结果
     */
    @Override
    public int updateIbWxUser(IbWxUser ibWxUser)
    {
        ibWxUser.setUpdateTime(DateUtils.getNowDate());
        return ibWxUserMapper.updateIbWxUser(ibWxUser);
    }

    /**
     * 批量删除微信用户
     * 
     * @param ids 需要删除的微信用户主键
     * @return 结果
     */
    @Override
    public int deleteIbWxUserByIds(Long[] ids)
    {
        return ibWxUserMapper.deleteIbWxUserByIds(ids);
    }

    /**
     * 删除微信用户信息
     * 
     * @param id 微信用户主键
     * @return 结果
     */
    @Override
    public int deleteIbWxUserById(Long id)
    {
        return ibWxUserMapper.deleteIbWxUserById(id);
    }

    @Override
    public IbWxUser selectIbWxUserOpenID(String openid) {
        return ibWxUserMapper.selectIbWxUserOpenID(openid);
    }


}
