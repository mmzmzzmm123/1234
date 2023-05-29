package com.ruoyi.office.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.domain.WxUserStore;
import com.ruoyi.office.service.IWxUserStoreService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.office.mapper.WxUserStoreMapper;

/**
 * 微信用户余额Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-05-27
 */
@Service
public class WxUserStoreServiceImpl extends ServiceImpl<WxUserStoreMapper, WxUserStore>  implements IWxUserStoreService
{
    @Autowired
    private WxUserStoreMapper wxUserStoreMapper;

    /**
     * 查询微信用户余额
     * 
     * @param id 微信用户余额主键
     * @return 微信用户余额
     */
    @Override
    public WxUserStore selectWxUserStoreById(Long id)
    {
        return wxUserStoreMapper.selectWxUserStoreById(id);
    }

    /**
     * 查询微信用户余额列表
     * 
     * @param wxUserStore 微信用户余额
     * @return 微信用户余额
     */
    @Override
    public List<WxUserStore> selectWxUserStoreList(WxUserStore wxUserStore)
    {
//        QueryWrapper<WxUserStore> queryWrapper = new QueryWrapper();
//        queryWrapper.lambda().gt(WxUserStore::getId,1);
//        return wxUserStoreMapper.selectList(queryWrapper);
        return wxUserStoreMapper.selectWxUserStoreList(wxUserStore);
    }

    /**
     * 新增微信用户余额
     * 
     * @param wxUserStore 微信用户余额
     * @return 结果
     */
    @Override
    public int insertWxUserStore(WxUserStore wxUserStore)
    {
        wxUserStore.setCreateTime(DateUtils.getNowDate());
        return wxUserStoreMapper.insertWxUserStore(wxUserStore);
    }

    /**
     * 修改微信用户余额
     * 
     * @param wxUserStore 微信用户余额
     * @return 结果
     */
    @Override
    public int updateWxUserStore(WxUserStore wxUserStore)
    {
        wxUserStore.setUpdateTime(DateUtils.getNowDate());
        return wxUserStoreMapper.updateWxUserStore(wxUserStore);
    }

    /**
     * 批量删除微信用户余额
     * 
     * @param ids 需要删除的微信用户余额主键
     * @return 结果
     */
    @Override
    public int deleteWxUserStoreByIds(Long[] ids)
    {
        return wxUserStoreMapper.deleteWxUserStoreByIds(ids);
    }

    /**
     * 删除微信用户余额信息
     * 
     * @param id 微信用户余额主键
     * @return 结果
     */
    @Override
    public int deleteWxUserStoreById(Long id)
    {
        return wxUserStoreMapper.deleteWxUserStoreById(id);
    }
}
