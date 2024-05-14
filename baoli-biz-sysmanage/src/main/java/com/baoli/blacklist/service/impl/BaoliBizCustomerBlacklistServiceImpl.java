package com.baoli.blacklist.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.blacklist.mapper.BaoliBizCustomerBlacklistMapper;
import com.baoli.blacklist.domain.BaoliBizCustomerBlacklist;
import com.baoli.blacklist.service.IBaoliBizCustomerBlacklistService;

/**
 * 客户黑名单Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
@Service
public class BaoliBizCustomerBlacklistServiceImpl implements IBaoliBizCustomerBlacklistService 
{
    @Autowired
    private BaoliBizCustomerBlacklistMapper baoliBizCustomerBlacklistMapper;

    /**
     * 查询客户黑名单
     * 
     * @param id 客户黑名单主键
     * @return 客户黑名单
     */
    @Override
    public BaoliBizCustomerBlacklist selectBaoliBizCustomerBlacklistById(Long id)
    {
        return baoliBizCustomerBlacklistMapper.selectBaoliBizCustomerBlacklistById(id);
    }

    /**
     * 查询客户黑名单列表
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 客户黑名单
     */
    @Override
    public List<BaoliBizCustomerBlacklist> selectBaoliBizCustomerBlacklistList(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        return baoliBizCustomerBlacklistMapper.selectBaoliBizCustomerBlacklistList(baoliBizCustomerBlacklist);
    }

    /**
     * 新增客户黑名单
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 结果
     */
    @Override
    public int insertBaoliBizCustomerBlacklist(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        baoliBizCustomerBlacklist.setCreateTime(DateUtils.getNowDate());
        return baoliBizCustomerBlacklistMapper.insertBaoliBizCustomerBlacklist(baoliBizCustomerBlacklist);
    }

    /**
     * 修改客户黑名单
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 结果
     */
    @Override
    public int updateBaoliBizCustomerBlacklist(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist)
    {
        baoliBizCustomerBlacklist.setUpdateTime(DateUtils.getNowDate());
        return baoliBizCustomerBlacklistMapper.updateBaoliBizCustomerBlacklist(baoliBizCustomerBlacklist);
    }

    /**
     * 批量删除客户黑名单
     * 
     * @param ids 需要删除的客户黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCustomerBlacklistByIds(Long[] ids)
    {
        return baoliBizCustomerBlacklistMapper.deleteBaoliBizCustomerBlacklistByIds(ids);
    }

    /**
     * 删除客户黑名单信息
     * 
     * @param id 客户黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCustomerBlacklistById(Long id)
    {
        return baoliBizCustomerBlacklistMapper.deleteBaoliBizCustomerBlacklistById(id);
    }
}
