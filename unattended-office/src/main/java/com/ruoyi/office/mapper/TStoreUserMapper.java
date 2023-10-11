package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ruoyi.office.domain.TStoreUser;
import com.ruoyi.office.domain.vo.StoreUserVo;

/**
 * 商家店铺用户Mapper接口
 *
 * @author ruoyi
 * @date 2023-10-11
 */
public interface TStoreUserMapper extends BaseMapper<TStoreUser> {
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
    public int insertTStoreUser(TStoreUser tStoreUser);

    /**
     * 修改商家店铺用户
     *
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    public int updateTStoreUser(TStoreUser tStoreUser);

    /**
     * 删除商家店铺用户
     *
     * @param id 商家店铺用户主键
     * @return 结果
     */
    public int deleteTStoreUserById(Long id);

    /**
     * 批量删除商家店铺用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStoreUserByIds(Long[] ids);

    List<StoreUserVo> selectTStoreUserH5listList(TStoreUser tStoreUser);
}
