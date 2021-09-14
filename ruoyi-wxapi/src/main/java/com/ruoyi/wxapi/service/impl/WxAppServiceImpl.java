package com.ruoyi.wxapi.service.impl;

import java.util.List;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wxapi.mapper.WxAppMapper;
import com.ruoyi.wxapi.domain.WxApp;
import com.ruoyi.wxapi.service.IWxAppService;

/**
 * 小程序设置Service业务层处理
 * 
 * @author tgq
 * @date 2021-09-01
 */
@Service
public class WxAppServiceImpl implements IWxAppService 
{
    @Autowired
    private WxAppMapper wxAppMapper;

    /**
     * 查询小程序设置
     * 
     * @param id 小程序设置主键
     * @return 小程序设置
     */
    @Override
    public WxApp selectWxAppById(Long id)
    {
        return wxAppMapper.selectWxAppById(id);
    }

    /**
     * 查询小程序设置列表
     * 
     * @param wxApp 小程序设置
     * @return 小程序设置
     */
    @Override
    public List<WxApp> selectWxAppList(WxApp wxApp)
    {
        return wxAppMapper.selectWxAppList(wxApp);
    }

    /**
     * 新增小程序设置
     * 
     * @param wxApp 小程序设置
     * @return 结果
     */
    @Override
    public int insertWxApp(WxApp wxApp)
    {
        wxApp.setCreateTime(DateUtils.getNowDate());
        return wxAppMapper.insertWxApp(wxApp);
    }

    /**
     * 修改小程序设置
     * 
     * @param wxApp 小程序设置
     * @return 结果
     */
    @Override
    public int updateWxApp(WxApp wxApp)
    {
        wxApp.setUpdateTime(DateUtils.getNowDate());
        return wxAppMapper.updateWxApp(wxApp);
    }

    /**
     * 批量删除小程序设置
     * 
     * @param ids 需要删除的小程序设置主键
     * @return 结果
     */
    @Override
    public int deleteWxAppByIds(Long[] ids)
    {
        return wxAppMapper.deleteWxAppByIds(ids);
    }

    /**
     * 删除小程序设置信息
     * 
     * @param id 小程序设置主键
     * @return 结果
     */
    @Override
    public int deleteWxAppById(Long id)
    {
        return wxAppMapper.deleteWxAppById(id);
    }


    /**
     * 获取微信小程序服务
     *
     * @param code 小程序code
     * @return 结果
     */
    @Override
    public WxMaService getMaService(String code) {
        WxApp wxApp = wxAppMapper.selectWxAppByCode(code);
        if (wxApp == null) {
            throw new IllegalArgumentException(String.format("未找到对应appcode=[%s]的配置，请核实！", code));
        }
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(wxApp.getAppId());
        config.setSecret(wxApp.getSecret());
//        config.setToken(null);
//        config.setAesKey(null);
//        config.setMsgDataFormat(null);

        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
