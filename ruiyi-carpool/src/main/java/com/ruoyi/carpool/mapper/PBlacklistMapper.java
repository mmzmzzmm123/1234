package com.ruoyi.carpool.mapper;

import java.util.List;
import com.ruoyi.carpool.domain.PBlacklist;

/**
 * 黑名单Mapper接口
 * 
 * @author ruoyi
 * @date 2021-12-03
 */
public interface PBlacklistMapper 
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
     * 查询黑名单列表-乘客
     *
     * @param pBlacklist 黑名单
     * @return 黑名单集合
     */
    public List<PBlacklist> selectPBlacklistListPassenger(PBlacklist pBlacklist);

    /**
     * 查询黑名单列表-司机
     *
     * @param pBlacklist 黑名单
     * @return 黑名单集合
     */
    public List<PBlacklist> selectPBlacklistListDriver(PBlacklist pBlacklist);

    /**
     * 新增黑名单
     * 
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int insertPBlacklist(PBlacklist pBlacklist);
    /**
     * 新增黑名单
     *
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int insertPBlacklistDriver(PBlacklist pBlacklist);
    /**
     * 新增黑名单
     *
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int insertPBlacklistPassenger(PBlacklist pBlacklist);

    /**
     * 修改黑名单
     * 
     * @param pBlacklist 黑名单
     * @return 结果
     */
    public int updatePBlacklist(PBlacklist pBlacklist);

    /**
     * 删除黑名单
     * 
     * @param id 黑名单主键
     * @return 结果
     */
    public int deletePBlacklistById(Long id);

    /**
     * 批量删除黑名单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePBlacklistByIds(Long[] ids);


    /**
     * 批量删除黑名单
     *
     * @param userID 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePBlacklistByIdDriver(String userID);


    /**
     * 批量删除黑名单
     *
     * @param userID 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePBlacklistByIdPassenger(String userID);


}
