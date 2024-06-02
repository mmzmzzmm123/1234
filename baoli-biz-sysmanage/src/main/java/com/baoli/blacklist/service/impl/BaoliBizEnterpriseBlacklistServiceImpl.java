package com.baoli.blacklist.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.blacklist.mapper.BaoliBizEnterpriseBlacklistMapper;
import com.baoli.blacklist.domain.BaoliBizEnterpriseBlacklist;
import com.baoli.blacklist.service.IBaoliBizEnterpriseBlacklistService;

/**
 * 企业黑名单Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
@Service
public class BaoliBizEnterpriseBlacklistServiceImpl implements IBaoliBizEnterpriseBlacklistService 
{
    @Autowired
    private BaoliBizEnterpriseBlacklistMapper baoliBizEnterpriseBlacklistMapper;

    /**
     * 查询企业黑名单
     * 
     * @param id 企业黑名单主键
     * @return 企业黑名单
     */
    @Override
    public BaoliBizEnterpriseBlacklist selectBaoliBizEnterpriseBlacklistById(Long id)
    {
        return baoliBizEnterpriseBlacklistMapper.selectBaoliBizEnterpriseBlacklistById(id);
    }

    /**
     * 查询企业黑名单列表
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 企业黑名单
     */
    @Override
    public List<BaoliBizEnterpriseBlacklist> selectBaoliBizEnterpriseBlacklistList(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        return baoliBizEnterpriseBlacklistMapper.selectBaoliBizEnterpriseBlacklistList(baoliBizEnterpriseBlacklist);
    }

    /**
     * 新增企业黑名单
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 结果
     */
    @Override
    public int insertBaoliBizEnterpriseBlacklist(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        baoliBizEnterpriseBlacklist.setCreateTime(DateUtils.getNowDate());
        return baoliBizEnterpriseBlacklistMapper.insertBaoliBizEnterpriseBlacklist(baoliBizEnterpriseBlacklist);
    }

    /**
     * 修改企业黑名单
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 结果
     */
    @Override
    public int updateBaoliBizEnterpriseBlacklist(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist)
    {
        baoliBizEnterpriseBlacklist.setUpdateTime(DateUtils.getNowDate());
        return baoliBizEnterpriseBlacklistMapper.updateBaoliBizEnterpriseBlacklist(baoliBizEnterpriseBlacklist);
    }

    /**
     * 批量删除企业黑名单
     * 
     * @param ids 需要删除的企业黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizEnterpriseBlacklistByIds(Long[] ids)
    {
        return baoliBizEnterpriseBlacklistMapper.deleteBaoliBizEnterpriseBlacklistByIds(ids);
    }

    /**
     * 删除企业黑名单信息
     * 
     * @param id 企业黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizEnterpriseBlacklistById(Long id)
    {
        return baoliBizEnterpriseBlacklistMapper.deleteBaoliBizEnterpriseBlacklistById(id);
    }
}
