package com.ruoyi.carpool.service;

import java.util.List;
import com.ruoyi.carpool.domain.PBlacklist;

/**
 * 黑名单Service接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface IPBlacklistService 
{
    /**
     * 查询黑名单
     * 
     * @param id 黑名单主键
     * @return 黑名单
     */
    public PBlacklist selectPBlacklistById(Long id);

    /**
     * 查询黑名单列表
     * 
     * @param pBlacklist 黑名单
     * @return 黑名单集合
     */
    public List<PBlacklist> selectPBlacklistList(PBlacklist pBlacklist);

    /**
     * 新增黑名单
     * 
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int insertPBlacklist(PBlacklist pBlacklist);

    /**
     * 修改黑名单
     * 
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int updatePBlacklist(PBlacklist pBlacklist);

    /**
     * 批量删除黑名单
     * 
     * @param ids 需要删除的黑名单主键集合
     * @return 结果
     */
    public int deletePBlacklistByIds(String[] ids);

    /**
     * 删除黑名单信息
     * 
     * @param id 黑名单主键
     * @return 结果
     */
    public int deletePBlacklistById(Long id);
}
