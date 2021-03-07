package com.ruoyi.business.mapper;

import java.util.List;

import com.ruoyi.business.domain.BusCbsxx;

/**
 * 承包商信息Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface BusCbsxxMapper {
	/**
	 * 查询承包商信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 承包商信息
	 */
	public BusCbsxx selectBusCbsxxById(Long id);

	/**
	 * 查询承包商信息列表
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 承包商信息集合
	 */
	public List<BusCbsxx> selectBusCbsxxList(BusCbsxx busCbsxx);

	/**
	 * 新增承包商信息
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 结果
	 */
	public int insertBusCbsxx(BusCbsxx busCbsxx);

	/**
	 * 修改承包商信息
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 结果
	 */
	public int updateBusCbsxx(BusCbsxx busCbsxx);

	/**
	 * 删除承包商信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 结果
	 */
	public int deleteBusCbsxxById(Long id);

	/**
	 * 批量删除承包商信息
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteBusCbsxxByIds(Long[] ids);

	/**
	 * 校验承包商名称是否唯一
	 *
	 * @param cbsName 承包商名称
	 * @return 结果
	 */
	public BusCbsxx checkNameUnique(String cbsName);
	
	/**
	 * 校验手机号码是否唯一
	 *
	 * @param phoneNumber
	 *            手机号码
	 * @return 结果
	 */
	public BusCbsxx checkPhoneNumberUnique(String phoneNumber);
}
