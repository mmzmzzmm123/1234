package com.ruoyi.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.business.domain.BusCbszdxx;

/**
 * 承包商站点信息Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-22
 */
public interface BusCbszdxxMapper {
	/**
	 * 查询承包商站点信息
	 * 
	 * @param id
	 *            承包商站点信息ID
	 * @return 承包商站点信息
	 */
	public BusCbszdxx selectBusCbszdxxById(Long id);

	/**
	 * 查询承包商站点信息列表
	 * 
	 * @param busCbszdxx
	 *            承包商站点信息
	 * @return 承包商站点信息集合
	 */
	public List<BusCbszdxx> selectBusCbszdxxList(BusCbszdxx busCbszdxx);

	/**
	 * 新增承包商站点信息
	 * 
	 * @param busCbszdxx
	 *            承包商站点信息
	 * @return 结果
	 */
	public int insertBusCbszdxx(BusCbszdxx busCbszdxx);

	/**
	 * 修改承包商站点信息
	 * 
	 * @param busCbszdxx
	 *            承包商站点信息
	 * @return 结果
	 */
	public int updateBusCbszdxx(BusCbszdxx busCbszdxx);

	/**
	 * 删除承包商站点信息
	 * 
	 * @param id
	 *            承包商站点信息ID
	 * @return 结果
	 */
	public int deleteBusCbszdxxById(Long id);

	/**
	 * 批量删除承包商站点信息
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteBusCbszdxxByIds(Long[] ids);

	/**
	 * 根据条件删除已承包的站点
	 * 
	 * @param cbsId
	 *            承包商ID
	 * @param year
	 *            承包年份
	 * @param fwxm
	 *            服务项目
	 * @return
	 */
	public int deleteSelectedCbszdxx(@Param("cbsId") Long cbsId, @Param("year") String year,
			@Param("fwxm") String fwxm);
}
