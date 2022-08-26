package com.ruoyi.ibaiqi.wxUser.mapper;

import java.util.List;

import com.ruoyi.ibaiqi.wxUser.domain.IbWxUser;

/**
 * 微信用户Mapper接口
 *
 * @author zhangxuDev
 * @date 2022-08-17
 */
public interface IbWxUserMapper {
    /**
     * 查询微信用户
     *
     * @param id 微信用户主键
     * @return 微信用户
     */
    public IbWxUser selectIbWxUserById(Long id);

    /**
     * 查询微信用户列表
     *
     * @param ibWxUser 微信用户
     * @return 微信用户集合
     */
    public List<IbWxUser> selectIbWxUserList(IbWxUser ibWxUser);

    /**
     * 新增微信用户
     *
     * @param ibWxUser 微信用户
     * @return 结果
     */
    public int insertIbWxUser(IbWxUser ibWxUser);

    /**
     * 修改微信用户
     *
     * @param ibWxUser 微信用户
     * @return 结果
     */
    public int updateIbWxUser(IbWxUser ibWxUser);

    /**
     * 删除微信用户
     *
     * @param id 微信用户主键
     * @return 结果
     */
    public int deleteIbWxUserById(Long id);

    /**
     * 批量删除微信用户
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteIbWxUserByIds(Long[] ids);

    /**
     * 通过OpenID查询微信用户信息
     *
     * @param openid
     * @return
     */
    public IbWxUser selectIbWxUserOpenID(String openid);
}
