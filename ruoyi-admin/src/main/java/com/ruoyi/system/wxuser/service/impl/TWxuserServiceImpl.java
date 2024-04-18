package com.ruoyi.system.wxuser.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.wxuser.mapper.TWxuserMapper;
import com.ruoyi.system.wxuser.domain.TWxuser;
import com.ruoyi.system.wxuser.service.ITWxuserService;

/**
 * 微信用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-04-18
 */
@Service
public class TWxuserServiceImpl implements ITWxuserService 
{
    @Autowired
    private TWxuserMapper tWxuserMapper;

    /**
     * 查询微信用户
     * 
     * @param id 微信用户主键
     * @return 微信用户
     */
    @Override
    public TWxuser selectTWxuserById(Long id)
    {
        return tWxuserMapper.selectTWxuserById(id);
    }

    /**
     * 查询微信用户列表
     * 
     * @param tWxuser 微信用户
     * @return 微信用户
     */
    @Override
    public List<TWxuser> selectTWxuserList(TWxuser tWxuser)
    {
        return tWxuserMapper.selectTWxuserList(tWxuser);
    }

    /**
     * 新增微信用户
     * 
     * @param tWxuser 微信用户
     * @return 结果
     */
    @Override
    public int insertTWxuser(TWxuser tWxuser)
    {
        tWxuser.setCreateTime(DateUtils.getNowDate());
        return tWxuserMapper.insertTWxuser(tWxuser);
    }

    /**
     * 修改微信用户
     * 
     * @param tWxuser 微信用户
     * @return 结果
     */
    @Override
    public int updateTWxuser(TWxuser tWxuser)
    {
        tWxuser.setUpdateTime(DateUtils.getNowDate());
        return tWxuserMapper.updateTWxuser(tWxuser);
    }

    /**
     * 批量删除微信用户
     * 
     * @param ids 需要删除的微信用户主键
     * @return 结果
     */
    @Override
    public int deleteTWxuserByIds(Long[] ids)
    {
        return tWxuserMapper.deleteTWxuserByIds(ids);
    }

    /**
     * 删除微信用户信息
     * 
     * @param id 微信用户主键
     * @return 结果
     */
    @Override
    public int deleteTWxuserById(Long id)
    {
        return tWxuserMapper.deleteTWxuserById(id);
    }
}
