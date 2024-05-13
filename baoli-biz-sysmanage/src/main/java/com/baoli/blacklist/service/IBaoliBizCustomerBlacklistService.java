package com.baoli.blacklist.service;

import java.util.List;
import com.baoli.blacklist.domain.BaoliBizCustomerBlacklist;

/**
 * 客户黑名单Service接口
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
public interface IBaoliBizCustomerBlacklistService 
{
    /**
     * 查询客户黑名单
     * 
     * @param id 客户黑名单主键
     * @return 客户黑名单
     */
    public BaoliBizCustomerBlacklist selectBaoliBizCustomerBlacklistById(Long id);

    /**
     * 查询客户黑名单列表
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 客户黑名单集合
     */
    public List<BaoliBizCustomerBlacklist> selectBaoliBizCustomerBlacklistList(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist);

    /**
     * 新增客户黑名单
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 结果
     */
    public int insertBaoliBizCustomerBlacklist(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist);

    /**
     * 修改客户黑名单
     * 
     * @param baoliBizCustomerBlacklist 客户黑名单
     * @return 结果
     */
    public int updateBaoliBizCustomerBlacklist(BaoliBizCustomerBlacklist baoliBizCustomerBlacklist);

    /**
     * 批量删除客户黑名单
     * 
     * @param ids 需要删除的客户黑名单主键集合
     * @return 结果
     */
    public int deleteBaoliBizCustomerBlacklistByIds(Long[] ids);

    /**
     * 删除客户黑名单信息
     * 
     * @param id 客户黑名单主键
     * @return 结果
     */
    public int deleteBaoliBizCustomerBlacklistById(Long id);
}
