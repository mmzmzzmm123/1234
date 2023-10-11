package com.ruoyi.office.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStoreUserMapper;
import com.ruoyi.office.domain.TStoreUser;
import com.ruoyi.office.service.ITStoreUserService;

/**
 * 商家店铺用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-11
 */
@Service
public class TStoreUserServiceImpl extends ServiceImpl<TStoreUserMapper, TStoreUser> implements ITStoreUserService
{
    @Autowired
    private TStoreUserMapper tStoreUserMapper;

    /**
     * 查询商家店铺用户
     * 
     * @param id 商家店铺用户主键
     * @return 商家店铺用户
     */
    @Override
    public TStoreUser selectTStoreUserById(Long id)
    {
        return tStoreUserMapper.selectTStoreUserById(id);
    }

    /**
     * 查询商家店铺用户列表
     * 
     * @param tStoreUser 商家店铺用户
     * @return 商家店铺用户
     */
    @Override
    public List<TStoreUser> selectTStoreUserList(TStoreUser tStoreUser)
    {
        return tStoreUserMapper.selectTStoreUserList(tStoreUser);
    }

    /**
     * 新增商家店铺用户
     * 
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    @Override
    public int insertTStoreUser(TStoreUser tStoreUser)
    {
        tStoreUser.setCreateTime(DateUtils.getNowDate());
        return tStoreUserMapper.insertTStoreUser(tStoreUser);
    }

    /**
     * 修改商家店铺用户
     * 
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    @Override
    public int updateTStoreUser(TStoreUser tStoreUser)
    {
        tStoreUser.setUpdateTime(DateUtils.getNowDate());
        return tStoreUserMapper.updateTStoreUser(tStoreUser);
    }

    /**
     * 批量删除商家店铺用户
     * 
     * @param ids 需要删除的商家店铺用户主键
     * @return 结果
     */
    @Override
    public int deleteTStoreUserByIds(Long[] ids)
    {
        return tStoreUserMapper.deleteTStoreUserByIds(ids);
    }

    /**
     * 删除商家店铺用户信息
     * 
     * @param id 商家店铺用户主键
     * @return 结果
     */
    @Override
    public int deleteTStoreUserById(Long id)
    {
        return tStoreUserMapper.deleteTStoreUserById(id);
    }
}
