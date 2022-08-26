package com.ruoyi.ibaiqi.wxUser.service;

import java.util.List;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxAppconfig;

/**
 * 微信小程序配置Service接口
 * 
 * @author zhangxuDev
 * @date 2022-08-17
 */
public interface IIbWxAppconfigService 
{
    /**
     * 查询微信小程序配置
     * 
     * @param id 微信小程序配置主键
     * @return 微信小程序配置
     */
    public IbWxAppconfig selectIbWxAppconfigById(Long id);

    /**
     * 查询微信小程序配置列表
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 微信小程序配置集合
     */
    public List<IbWxAppconfig> selectIbWxAppconfigList(IbWxAppconfig ibWxAppconfig);

    /**
     * 新增微信小程序配置
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 结果
     */
    public int insertIbWxAppconfig(IbWxAppconfig ibWxAppconfig);

    /**
     * 修改微信小程序配置
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 结果
     */
    public int updateIbWxAppconfig(IbWxAppconfig ibWxAppconfig);

    /**
     * 批量删除微信小程序配置
     * 
     * @param ids 需要删除的微信小程序配置主键集合
     * @return 结果
     */
    public int deleteIbWxAppconfigByIds(Long[] ids);

    /**
     * 删除微信小程序配置信息
     * 
     * @param id 微信小程序配置主键
     * @return 结果
     */
    public int deleteIbWxAppconfigById(Long id);

    /**
     * 查询开启的小程序配置信息
     * @return
     */
    public IbWxAppconfig selectIbWxappConfig();

}
