package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DataWechatUser;

/**
 * 微信用户信息Mapper接口
 * 
 * @author genius
 * @date 2023-04-06
 */
public interface DataWechatUserMapper 
{
    /**
     * 查询微信用户信息
     * 
     * @param userId 微信用户信息ID
     * @return 微信用户信息
     */
    public DataWechatUser selectDataWechatUserById(Long userId);

    /**
     * 查询微信用户信息列表
     * 
     * @param dataWechatUser 微信用户信息
     * @return 微信用户信息集合
     */
    public List<DataWechatUser> selectDataWechatUserList(DataWechatUser dataWechatUser);

    /**
     * 新增微信用户信息
     * 
     * @param dataWechatUser 微信用户信息
     * @return 结果
     */
    public int insertDataWechatUser(DataWechatUser dataWechatUser);

    /**
     * 修改微信用户信息
     * 
     * @param dataWechatUser 微信用户信息
     * @return 结果
     */
    public int updateDataWechatUser(DataWechatUser dataWechatUser);

    /**
     * 删除微信用户信息
     * 
     * @param userId 微信用户信息ID
     * @return 结果
     */
    public int deleteDataWechatUserById(Long userId);

    /**
     * 批量删除微信用户信息
     * 
     * @param userIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteDataWechatUserByIds(Long[] userIds);

    DataWechatUser selectUserByOpenId(String openId);
}
