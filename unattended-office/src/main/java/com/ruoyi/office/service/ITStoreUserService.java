package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TStoreUser;
import com.ruoyi.office.domain.vo.StoreUserVo;

/**
 * 商家店铺用户Service接口
 * 
 * @author ruoyi
 * @date 2023-10-11
 */
public interface ITStoreUserService 
{
    /**
     * 查询商家店铺用户
     * 
     * @param id 商家店铺用户主键
     * @return 商家店铺用户
     */
    public TStoreUser selectTStoreUserById(Long id);

    /**
     * 查询商家店铺用户列表
     * 
     * @param tStoreUser 商家店铺用户
     * @return 商家店铺用户集合
     */
    public List<TStoreUser> selectTStoreUserList(TStoreUser tStoreUser);

    /**
     * 新增商家店铺用户
     * 
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    public int insertTStoreUser(StoreUserVo tStoreUser);

    /**
     * 修改商家店铺用户
     * 
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    public int updateTStoreUser(StoreUserVo tStoreUser);

    /**
     * 批量删除商家店铺用户
     * 
     * @param ids 需要删除的商家店铺用户主键集合
     * @return 结果
     */
    public int deleteTStoreUserByIds(Long[] ids);

    /**
     * 删除商家店铺用户信息
     * 
     * @param id 商家店铺用户主键
     * @return 结果
     */
    public int deleteTStoreUserById(Long id);

    List<StoreUserVo> selectTStoreUserH5listList(TStoreUser tStoreUser);
}
