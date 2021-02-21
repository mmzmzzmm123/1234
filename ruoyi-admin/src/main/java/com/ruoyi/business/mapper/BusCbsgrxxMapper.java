package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.BusCbsgrxx;

/**
 * 承包商工人信息Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface BusCbsgrxxMapper 
{
    /**
     * 查询承包商工人信息
     * 
     * @param id 承包商工人信息ID
     * @return 承包商工人信息
     */
    public BusCbsgrxx selectBusCbsgrxxById(Long id);

    /**
     * 查询承包商工人信息列表
     * 
     * @param busCbsgrxx 承包商工人信息
     * @return 承包商工人信息集合
     */
    public List<BusCbsgrxx> selectBusCbsgrxxList(BusCbsgrxx busCbsgrxx);

    /**
     * 新增承包商工人信息
     * 
     * @param busCbsgrxx 承包商工人信息
     * @return 结果
     */
    public int insertBusCbsgrxx(BusCbsgrxx busCbsgrxx);

    /**
     * 修改承包商工人信息
     * 
     * @param busCbsgrxx 承包商工人信息
     * @return 结果
     */
    public int updateBusCbsgrxx(BusCbsgrxx busCbsgrxx);

    /**
     * 删除承包商工人信息
     * 
     * @param id 承包商工人信息ID
     * @return 结果
     */
    public int deleteBusCbsgrxxById(Long id);

    /**
     * 批量删除承包商工人信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusCbsgrxxByIds(Long[] ids);

	public List<BusCbsgrxx> selectByCbsId(Long cbsId);
}
