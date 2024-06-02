package com.baoli.blacklist.service;

import java.util.List;
import com.baoli.blacklist.domain.BaoliBizEnterpriseBlacklist;

/**
 * 企业黑名单Service接口
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
public interface IBaoliBizEnterpriseBlacklistService 
{
    /**
     * 查询企业黑名单
     * 
     * @param id 企业黑名单主键
     * @return 企业黑名单
     */
    public BaoliBizEnterpriseBlacklist selectBaoliBizEnterpriseBlacklistById(Long id);

    /**
     * 查询企业黑名单列表
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 企业黑名单集合
     */
    public List<BaoliBizEnterpriseBlacklist> selectBaoliBizEnterpriseBlacklistList(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist);

    /**
     * 新增企业黑名单
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 结果
     */
    public int insertBaoliBizEnterpriseBlacklist(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist);

    /**
     * 修改企业黑名单
     * 
     * @param baoliBizEnterpriseBlacklist 企业黑名单
     * @return 结果
     */
    public int updateBaoliBizEnterpriseBlacklist(BaoliBizEnterpriseBlacklist baoliBizEnterpriseBlacklist);

    /**
     * 批量删除企业黑名单
     * 
     * @param ids 需要删除的企业黑名单主键集合
     * @return 结果
     */
    public int deleteBaoliBizEnterpriseBlacklistByIds(Long[] ids);

    /**
     * 删除企业黑名单信息
     * 
     * @param id 企业黑名单主键
     * @return 结果
     */
    public int deleteBaoliBizEnterpriseBlacklistById(Long id);
}
