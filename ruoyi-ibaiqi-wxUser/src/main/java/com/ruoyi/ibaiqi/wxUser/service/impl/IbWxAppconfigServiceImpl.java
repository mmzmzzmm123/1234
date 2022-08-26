package com.ruoyi.ibaiqi.wxUser.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.ibaiqi.wxUser.mapper.IbWxAppconfigMapper;
import com.ruoyi.ibaiqi.wxUser.domain.IbWxAppconfig;
import com.ruoyi.ibaiqi.wxUser.service.IIbWxAppconfigService;

/**
 * 微信小程序配置Service业务层处理
 * 
 * @author zhangxuDev
 * @date 2022-08-17
 */
@Service
public class IbWxAppconfigServiceImpl implements IIbWxAppconfigService 
{
    @Autowired
    private IbWxAppconfigMapper ibWxAppconfigMapper;

    /**
     * 查询微信小程序配置
     * 
     * @param id 微信小程序配置主键
     * @return 微信小程序配置
     */
    @Override
    public IbWxAppconfig selectIbWxAppconfigById(Long id)
    {
        return ibWxAppconfigMapper.selectIbWxAppconfigById(id);
    }

    /**
     * 查询微信小程序配置列表
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 微信小程序配置
     */
    @Override
    public List<IbWxAppconfig> selectIbWxAppconfigList(IbWxAppconfig ibWxAppconfig)
    {
        return ibWxAppconfigMapper.selectIbWxAppconfigList(ibWxAppconfig);
    }

    /**
     * 新增微信小程序配置
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 结果
     */
    @Override
    public int insertIbWxAppconfig(IbWxAppconfig ibWxAppconfig)
    {
        ibWxAppconfig.setCreateTime(DateUtils.getNowDate());
        return ibWxAppconfigMapper.insertIbWxAppconfig(ibWxAppconfig);
    }

    /**
     * 修改微信小程序配置
     * 
     * @param ibWxAppconfig 微信小程序配置
     * @return 结果
     */
    @Override
    public int updateIbWxAppconfig(IbWxAppconfig ibWxAppconfig)
    {
        ibWxAppconfig.setUpdateTime(DateUtils.getNowDate());
        return ibWxAppconfigMapper.updateIbWxAppconfig(ibWxAppconfig);
    }

    /**
     * 批量删除微信小程序配置
     * 
     * @param ids 需要删除的微信小程序配置主键
     * @return 结果
     */
    @Override
    public int deleteIbWxAppconfigByIds(Long[] ids)
    {
        return ibWxAppconfigMapper.deleteIbWxAppconfigByIds(ids);
    }

    /**
     * 删除微信小程序配置信息
     * 
     * @param id 微信小程序配置主键
     * @return 结果
     */
    @Override
    public int deleteIbWxAppconfigById(Long id)
    {
        return ibWxAppconfigMapper.deleteIbWxAppconfigById(id);
    }

    @Override
    public IbWxAppconfig selectIbWxappConfig() {
        return ibWxAppconfigMapper.selectIbWxappConfig();
    }

}
