package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusCbszzxx;

/**
 * 承包商资质信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface IBusCbszzxxService 
{
    /**
     * 查询承包商资质信息
     * 
     * @param id 承包商资质信息ID
     * @return 承包商资质信息
     */
    public BusCbszzxx selectBusCbszzxxById(Long id);

    /**
     * 查询承包商资质信息列表
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 承包商资质信息集合
     */
    public List<BusCbszzxx> selectBusCbszzxxList(BusCbszzxx busCbszzxx);

    /**
     * 新增承包商资质信息
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 结果
     */
    public int insertBusCbszzxx(BusCbszzxx busCbszzxx);

    /**
     * 修改承包商资质信息
     * 
     * @param busCbszzxx 承包商资质信息
     * @return 结果
     */
    public int updateBusCbszzxx(BusCbszzxx busCbszzxx);

    /**
     * 批量删除承包商资质信息
     * 
     * @param ids 需要删除的承包商资质信息ID
     * @return 结果
     */
    public int deleteBusCbszzxxByIds(Long[] ids);

    /**
     * 删除承包商资质信息信息
     * 
     * @param id 承包商资质信息ID
     * @return 结果
     */
    public int deleteBusCbszzxxById(Long id);

	List<BusCbszzxx> selectByCbsId(Long cbsId);

	int deleteByCbsId(Long cbsId);
}
