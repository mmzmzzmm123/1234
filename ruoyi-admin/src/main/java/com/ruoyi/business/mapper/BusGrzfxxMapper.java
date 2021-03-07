package com.ruoyi.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.business.domain.BusGrzfxx;

/**
 * 工人证书信息Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-21
 */
public interface BusGrzfxxMapper {
	/**
	 * 查询工人证书信息
	 * 
	 * @param id
	 *            工人证书信息ID
	 * @return 工人证书信息
	 */
	public BusGrzfxx selectBusGrzfxxById(Long id);

	/**
	 * 查询工人证书信息列表
	 * 
	 * @param busGrzfxx
	 *            工人证书信息
	 * @return 工人证书信息集合
	 */
	public List<BusGrzfxx> selectBusGrzfxxList(BusGrzfxx busGrzfxx);

	/**
	 * 新增工人证书信息
	 * 
	 * @param busGrzfxx
	 *            工人证书信息
	 * @return 结果
	 */
	public int insertBusGrzfxx(BusGrzfxx busGrzfxx);

	/**
	 * 修改工人证书信息
	 * 
	 * @param busGrzfxx
	 *            工人证书信息
	 * @return 结果
	 */
	public int updateBusGrzfxx(BusGrzfxx busGrzfxx);

	/**
	 * 删除工人证书信息
	 * 
	 * @param id
	 *            工人证书信息ID
	 * @return 结果
	 */
	public int deleteBusGrzfxxById(Long id);

	/**
	 * 批量删除工人证书信息
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteBusGrzfxxByIds(Long[] ids);

	/**
	 * 查询工人证书
	 * 
	 * @param cbsId
	 *            承包商Id
	 * @param grId
	 *            工人ID
	 * @return 结果
	 */
	public List<BusGrzfxx> selectByCbsIdAndGrId(@Param("cbsId") Long cbsId, @Param("grId") Long grId);

}
