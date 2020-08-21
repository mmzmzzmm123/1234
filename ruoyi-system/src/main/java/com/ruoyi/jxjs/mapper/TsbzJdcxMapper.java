package com.ruoyi.jxjs.mapper;

import java.util.List;
import com.ruoyi.jxjs.domain.TsbzJdcx;

/**
 * 基地区级审核Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public interface TsbzJdcxMapper 
{
    /**
     * 查询基地区级审核
     * 
     * @param id 基地区级审核ID
     * @return 基地区级审核
     */
    public TsbzJdcx selectTsbzJdcxById(Long id);

    /**
     * 查询基地区级审核列表
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 基地区级审核集合
     */
    public List<TsbzJdcx> selectTsbzJdcxList(TsbzJdcx tsbzJdcx);

    /**
     * 新增基地区级审核
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 结果
     */
    public int insertTsbzJdcx(TsbzJdcx tsbzJdcx);

    /**
     * 修改基地区级审核
     * 
     * @param tsbzJdcx 基地区级审核
     * @return 结果
     */
    public int updateTsbzJdcx(TsbzJdcx tsbzJdcx);

    /**
     * 删除基地区级审核
     * 
     * @param id 基地区级审核ID
     * @return 结果
     */
    public int deleteTsbzJdcxById(Long id);

    /**
     * 批量删除基地区级审核
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJdcxByIds(Long[] ids);
}
