package com.ruoyi.productionManager.mapper;

import java.util.List;
import com.ruoyi.productionManager.domain.StandardInfoDetails;

/**
 * 标准信息文件Mapper接口
 * 
 * @author ruoyi
 * @date 2021-10-18
 */
public interface StandardInfoDetailsMapper 
{
    /**
     * 查询标准信息文件
     * 
     * @param detailsId 标准信息文件主键
     * @return 标准信息文件
     */
    public StandardInfoDetails selectStandardInfoDetailsByDetailsId(Long detailsId);

    /**
     * 查询标准信息文件列表
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 标准信息文件集合
     */
    public List<StandardInfoDetails> selectStandardInfoDetailsList(StandardInfoDetails standardInfoDetails);

    /**
     * 新增标准信息文件
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 结果
     */
    public int insertStandardInfoDetails(StandardInfoDetails standardInfoDetails);

    /**
     * 修改标准信息文件
     * 
     * @param standardInfoDetails 标准信息文件
     * @return 结果
     */
    public int updateStandardInfoDetails(StandardInfoDetails standardInfoDetails);

    /**
     * 删除标准信息文件
     * 
     * @param detailsId 标准信息文件主键
     * @return 结果
     */
    public int deleteStandardInfoDetailsByDetailsId(Long detailsId);

    /**
     * 批量删除标准信息文件
     * 
     * @param detailsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStandardInfoDetailsByDetailsIds(Long[] detailsIds);
}
