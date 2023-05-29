package com.ruoyi.office.service;

import java.util.List;

import com.ruoyi.office.domain.WxUserStore;

/**
 * 微信用户余额Service接口
 * 
 * @author ruoyi
 * @date 2023-05-27
 */
public interface IWxUserStoreService 
{
    /**
     * 查询微信用户余额
     * 
     * @param id 微信用户余额主键
     * @return 微信用户余额
     */
    public WxUserStore selectWxUserStoreById(Long id);

    /**
     * 查询微信用户余额列表
     * 
     * @param wxUserStore 微信用户余额
     * @return 微信用户余额集合
     */
    public List<WxUserStore> selectWxUserStoreList(WxUserStore wxUserStore);

    /**
     * 新增微信用户余额
     * 
     * @param wxUserStore 微信用户余额
     * @return 结果
     */
    public int insertWxUserStore(WxUserStore wxUserStore);

    /**
     * 修改微信用户余额
     * 
     * @param wxUserStore 微信用户余额
     * @return 结果
     */
    public int updateWxUserStore(WxUserStore wxUserStore);

    /**
     * 批量删除微信用户余额
     * 
     * @param ids 需要删除的微信用户余额主键集合
     * @return 结果
     */
    public int deleteWxUserStoreByIds(Long[] ids);

    /**
     * 删除微信用户余额信息
     * 
     * @param id 微信用户余额主键
     * @return 结果
     */
    public int deleteWxUserStoreById(Long id);
}
