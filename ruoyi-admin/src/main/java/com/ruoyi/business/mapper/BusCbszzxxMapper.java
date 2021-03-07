package com.ruoyi.business.mapper;

import java.util.List;
import com.ruoyi.business.domain.BusCbszzxx;

/**
 * 承包商资质信息Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface BusCbszzxxMapper {
	/**
	 * 查询承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 承包商资质信息
	 */
	public BusCbszzxx selectBusCbszzxxById(Long id);

	/**
	 * 查询承包商资质信息列表
	 * 
	 * @param busCbszzxx
	 *            承包商资质信息
	 * @return 承包商资质信息集合
	 */
	public List<BusCbszzxx> selectBusCbszzxxList(BusCbszzxx busCbszzxx);

	/**
	 * 新增承包商资质信息
	 * 
	 * @param busCbszzxx
	 *            承包商资质信息
	 * @return 结果
	 */
	public int insertBusCbszzxx(BusCbszzxx busCbszzxx);

	/**
	 * 修改承包商资质信息
	 * 
	 * @param busCbszzxx
	 *            承包商资质信息
	 * @return 结果
	 */
	public int updateBusCbszzxx(BusCbszzxx busCbszzxx);

	/**
	 * 删除承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 结果
	 */
	public int deleteBusCbszzxxById(Long id);

	/**
	 * 批量删除承包商资质信息
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteBusCbszzxxByIds(Long[] ids);

	/**
	 * 根据承包商ID查找资质
	 * 
	 * @param cbsId
	 *            承包商ID
	 * @return
	 */
	public List<BusCbszzxx> selectByCbsId(Long cbsId);
}
