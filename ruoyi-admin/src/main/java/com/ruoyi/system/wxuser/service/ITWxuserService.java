package com.ruoyi.system.wxuser.service;

import com.ruoyi.system.domain.TWxuser;

import java.util.List;

/**
 * 微信用户Service接口
 *
 * @author ruoyi
 * @date 2024-04-18
 */
public interface ITWxuserService
{
    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    public TWxuser selectTWxuserById(Long id);

    /**
     * 查询微信用户列表
     *
     * @param tWxuser 微信用户
     * @return 微信用户集合
     */
    public List<TWxuser> selectTWxuserList(TWxuser tWxuser);

    /**
     * 新增微信用户
     *
     * @param tWxuser 微信用户
     * @return 结果
     */
    public int insertTWxuser(TWxuser tWxuser);

    /**
     * 修改微信用户
     *
     * @param tWxuser 微信用户
     * @return 结果
     */
    public int updateTWxuser(TWxuser tWxuser);

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的微信用户主键集合
     * @return 结果
     */
    public int deleteTWxuserByIds(Long[] ids);

    /**
     * 删除微信用户信息
     *
     * @param id 微信用户主键
     * @return 结果
     */
    public int deleteTWxuserById(Long id);
}
