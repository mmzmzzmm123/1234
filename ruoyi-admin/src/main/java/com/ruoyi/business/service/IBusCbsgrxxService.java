package com.ruoyi.business.service;

import java.util.List;

import com.ruoyi.business.domain.BusCbsgrxx;
import com.ruoyi.business.domain.vo.BusCbsgrxxSaveVO;
import com.ruoyi.business.domain.vo.BusCbsgrxxVO;

/**
 * 承包商工人信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface IBusCbsgrxxService {
	/**
	 * 查询承包商工人信息
	 * 
	 * @param id
	 *            承包商工人信息ID
	 * @return 承包商工人信息
	 */
	public BusCbsgrxxVO selectBusCbsgrxxById(Long id);

	/**
	 * 查询承包商工人信息列表
	 * 
	 * @param busCbsgrxx
	 *            承包商工人信息
	 * @return 承包商工人信息集合
	 */
	public List<BusCbsgrxx> selectBusCbsgrxxList(BusCbsgrxx busCbsgrxx);

	/**
	 * 新增承包商工人信息
	 * 
	 * @param busCbsgrxxSaveVO
	 *            承包商工人信息
	 * @return 结果
	 */
	public int insertBusCbsgrxx(BusCbsgrxxSaveVO busCbsgrxxSaveVO);

	/**
	 * 修改承包商工人信息
	 * 
	 * @param busCbsgrxxSaveVO
	 *            承包商工人信息
	 * @return 结果
	 */
	public int updateBusCbsgrxx(BusCbsgrxxSaveVO busCbsgrxxSaveVO);

	/**
	 * 批量删除承包商工人信息
	 * 
	 * @param ids
	 *            需要删除的承包商工人信息ID
	 * @return 结果
	 */
	public int deleteBusCbsgrxxByIds(Long[] ids);

	/**
	 * 删除承包商工人信息信息
	 * 
	 * @param id
	 *            承包商工人信息ID
	 * @return 结果
	 */
	public int deleteBusCbsgrxxById(Long id);

	int deleteByCbsId(Long cbsId);

	List<BusCbsgrxxVO> selectByCbsId(Long cbsId);
}
