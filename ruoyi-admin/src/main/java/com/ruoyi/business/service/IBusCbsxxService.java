package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.domain.vo.BusCbsxxSaveVO;
import com.ruoyi.business.domain.vo.BusCbsxxVO;

/**
 * 承包商信息Service接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface IBusCbsxxService {
	/**
	 * 查询承包商信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 承包商信息
	 */
	public BusCbsxxVO selectBusCbsxxById(Long id);

	/**
	 * 查询承包商信息列表
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 承包商信息集合
	 */
	public List<BusCbsxxVO> selectBusCbsxxList(BusCbsxx busCbsxx);

	/**
	 * 新增承包商信息
	 * 
	 * @param busCbsxxSaveVO
	 *            承包商信息
	 * @return 结果
	 */
	public int insertBusCbsxx(BusCbsxxSaveVO busCbsxxSaveVO);

	/**
	 * 修改承包商信息
	 * 
	 * @param busCbsxxSaveVO
	 *            承包商信息
	 * @return 结果
	 */
	public int updateBusCbsxx(BusCbsxxSaveVO busCbsxxSaveVO);

	/**
	 * 批量删除承包商信息
	 * 
	 * @param ids
	 *            需要删除的承包商信息ID
	 * @return 结果
	 */
	public int deleteBusCbsxxByIds(Long[] ids);

	/**
	 * 删除承包商信息信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 结果
	 */
	public int deleteBusCbsxxById(Long id);

	BusCbsxx selectById(Long id);
}
