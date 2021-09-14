package com.ruoyi.wxapi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.wxapi.mapper.WxUserMapper;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.wxapi.service.IWxUserService;

/**
 * 小程序用户Service业务层处理
 * 
 * @author tgq
 * @date 2021-09-01
 */
@Service
public class WxUserServiceImpl implements IWxUserService 
{
    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 查询小程序用户
     * 
     * @param id 小程序用户主键
     * @return 小程序用户
     */
    @Override
    public WxUser selectWxUserById(Long id)
    {
        return wxUserMapper.selectWxUserById(id);
    }

    /**
     * 查询小程序用户
     *
     * @param openId 小程序用户openId
     * @return 小程序用户
     */
    @Override
    public WxUser selectWxUserByOpenId(String openId) {
        return wxUserMapper.selectWxUserByOpenId(openId);
    }

    /**
     * 查询小程序用户列表
     * 
     * @param wxUser 小程序用户
     * @return 小程序用户
     */
    @Override
    public List<WxUser> selectWxUserList(WxUser wxUser)
    {
        return wxUserMapper.selectWxUserList(wxUser);
    }

    /**
     * 新增小程序用户
     * 
     * @param wxUser 小程序用户
     * @return 结果
     */
    @Override
    public int insertWxUser(WxUser wxUser)
    {
        wxUser.setCreateTime(DateUtils.getNowDate());
        return wxUserMapper.insertWxUser(wxUser);
    }

    /**
     * 修改小程序用户
     * 
     * @param wxUser 小程序用户
     * @return 结果
     */
    @Override
    public int updateWxUser(WxUser wxUser)
    {
        wxUser.setUpdateTime(DateUtils.getNowDate());
        return wxUserMapper.updateWxUser(wxUser);
    }

    /**
     * 批量删除小程序用户
     * 
     * @param ids 需要删除的小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUserByIds(Long[] ids)
    {
        return wxUserMapper.deleteWxUserByIds(ids);
    }

    /**
     * 删除小程序用户信息
     * 
     * @param id 小程序用户主键
     * @return 结果
     */
    @Override
    public int deleteWxUserById(Long id)
    {
        return wxUserMapper.deleteWxUserById(id);
    }
}
