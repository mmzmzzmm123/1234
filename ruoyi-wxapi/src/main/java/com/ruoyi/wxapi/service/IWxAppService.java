package com.ruoyi.wxapi.service;

import java.util.List;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.ruoyi.wxapi.domain.WxApp;

/**
 * 小程序设置Service接口
 * 
 * @author tgq
 * @date 2021-09-01
 */
public interface IWxAppService 
{
    /**
     * 查询小程序设置
     * 
     * @param id 小程序设置主键
     * @return 小程序设置
     */
    public WxApp selectWxAppById(Long id);

    /**
     * 查询小程序设置列表
     * 
     * @param wxApp 小程序设置
     * @return 小程序设置集合
     */
    public List<WxApp> selectWxAppList(WxApp wxApp);

    /**
     * 新增小程序设置
     * 
     * @param wxApp 小程序设置
     * @return 结果
     */
    public int insertWxApp(WxApp wxApp);

    /**
     * 修改小程序设置
     * 
     * @param wxApp 小程序设置
     * @return 结果
     */
    public int updateWxApp(WxApp wxApp);

    /**
     * 批量删除小程序设置
     * 
     * @param ids 需要删除的小程序设置主键集合
     * @return 结果
     */
    public int deleteWxAppByIds(Long[] ids);

    /**
     * 删除小程序设置信息
     * 
     * @param id 小程序设置主键
     * @return 结果
     */
    public int deleteWxAppById(Long id);

    /**
     * 获取微信小程序服务
     *
     * @param code 小程序code
     * @return 结果
     */
    public WxMaService getMaService(String code);
}
