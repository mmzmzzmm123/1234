package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TWxUser;
import com.ruoyi.office.domain.vo.MerchantUserVo;

/**
 * 微信用户信息 t_wx_userService接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface ITWxUserService 
{
    /**
     * 查询微信用户信息 t_wx_user
     * 
     * @param id 微信用户信息 t_wx_user主键
     * @return 微信用户信息 t_wx_user
     */
    public TWxUser selectTWxUserById(Long id);

    /**
     * 查询微信用户信息 t_wx_user列表
     * 
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 微信用户信息 t_wx_user集合
     */
    public List<TWxUser> selectTWxUserList(TWxUser tWxUser);

    /**
     * 新增微信用户信息 t_wx_user
     * 
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    public int insertTWxUser(TWxUser tWxUser);

    /**
     * 修改微信用户信息 t_wx_user
     * 
     * @param tWxUser 微信用户信息 t_wx_user
     * @return 结果
     */
    public int updateTWxUser(TWxUser tWxUser);

    /**
     * 批量删除微信用户信息 t_wx_user
     * 
     * @param ids 需要删除的微信用户信息 t_wx_user主键集合
     * @return 结果
     */
    public int deleteTWxUserByIds(Long[] ids);

    /**
     * 删除微信用户信息 t_wx_user信息
     * 
     * @param id 微信用户信息 t_wx_user主键
     * @return 结果
     */
    public int deleteTWxUserById(Long id);

    List<MerchantUserVo> listStoreWxuser(TWxUser tWxUser);
}
