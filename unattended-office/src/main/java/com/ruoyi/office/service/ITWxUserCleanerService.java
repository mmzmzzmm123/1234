package com.ruoyi.office.service;

import java.util.List;
import com.ruoyi.office.domain.TWxUserCleaner;

/**
 * 保洁员信息Service接口
 * 
 * @author ruoyi
 * @date 2023-09-20
 */
public interface ITWxUserCleanerService 
{
    /**
     * 查询保洁员信息
     * 
     * @param id 保洁员信息主键
     * @return 保洁员信息
     */
    public TWxUserCleaner selectTWxUserCleanerById(Long id);

    /**
     * 查询保洁员信息列表
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 保洁员信息集合
     */
    public List<TWxUserCleaner> selectTWxUserCleanerList(TWxUserCleaner tWxUserCleaner);

    /**
     * 新增保洁员信息
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    public int insertTWxUserCleaner(TWxUserCleaner tWxUserCleaner);

    /**
     * 修改保洁员信息
     * 
     * @param tWxUserCleaner 保洁员信息
     * @return 结果
     */
    public int updateTWxUserCleaner(TWxUserCleaner tWxUserCleaner);

    /**
     * 批量删除保洁员信息
     * 
     * @param ids 需要删除的保洁员信息主键集合
     * @return 结果
     */
    public int deleteTWxUserCleanerByIds(Long[] ids);

    /**
     * 删除保洁员信息信息
     * 
     * @param id 保洁员信息主键
     * @return 结果
     */
    public int deleteTWxUserCleanerById(Long id);
}
