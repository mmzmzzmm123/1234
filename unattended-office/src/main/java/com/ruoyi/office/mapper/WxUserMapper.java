package com.ruoyi.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.WxUser;

/**
 * 微信用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public interface WxUserMapper  extends BaseMapper<WxUser>
{
    /**
     * 查询微信用户信息
     * 
     * @param id 微信用户信息主键
     * @return 微信用户信息
     */
    public WxUser selectWxUserById(Long id);

    /**
     * 查询微信用户信息列表
     * 
     * @param wxUser 微信用户信息
     * @return 微信用户信息集合
     */
    public List<WxUser> selectWxUserList(WxUser wxUser);

    /**
     * 新增微信用户信息
     * 
     * @param wxUser 微信用户信息
     * @return 结果
     */
    public int insertWxUser(WxUser wxUser);

    /**
     * 修改微信用户信息
     * 
     * @param wxUser 微信用户信息
     * @return 结果
     */
    public int updateWxUser(WxUser wxUser);

    /**
     * 删除微信用户信息
     * 
     * @param id 微信用户信息主键
     * @return 结果
     */
    public int deleteWxUserById(Long id);

    /**
     * 批量删除微信用户信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWxUserByIds(Long[] ids);

    WxUser selectUserByOpenId(String openid);
}
