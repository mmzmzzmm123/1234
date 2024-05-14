package com.baoli.blacklist.mapper;

import java.util.List;
import com.baoli.blacklist.domain.BaoliBizEnterpriseBlacklist;

/**
 * 企业黑名单Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-13
 */
public interface BaoliBizEnterpriseBlacklistMapper 
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
     * 删除企业黑名单
     * 
     * @param id 企业黑名单主键
     * @return 结果
     */
    public int deleteBaoliBizEnterpriseBlacklistById(Long id);

    /**
     * 批量删除企业黑名单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizEnterpriseBlacklistByIds(Long[] ids);
}
