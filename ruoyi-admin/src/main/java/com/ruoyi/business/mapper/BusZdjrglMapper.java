package com.ruoyi.business.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.business.domain.BusZdjrgl;

/**
 * 重大节日管理Mapper接口
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public interface BusZdjrglMapper {
	/**
	 * 查询重大节日管理
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 重大节日管理
	 */
	public BusZdjrgl selectBusZdjrglById(Long id);

	/**
	 * 查询重大节日管理列表
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 重大节日管理集合
	 */
	public List<BusZdjrgl> selectBusZdjrglList(BusZdjrgl busZdjrgl);

	/**
	 * 查询重大节日管理列表
	 * 
	 * @param ksrq
	 *            开始日期
	 * @param jsrq
	 *            结束日期
	 * @return
	 */
	public List<BusZdjrgl> selectBusZdjrglByJrrq(@Param("ksrq") Date ksrq, @Param("jsrq") Date jsrq);

	/**
	 * 新增重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	public int insertBusZdjrgl(BusZdjrgl busZdjrgl);

	/**
	 * 修改重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	public int updateBusZdjrgl(BusZdjrgl busZdjrgl);

	/**
	 * 删除重大节日管理
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 结果
	 */
	public int deleteBusZdjrglById(Long id);

	/**
	 * 批量删除重大节日管理
	 * 
	 * @param ids
	 *            需要删除的数据ID
	 * @return 结果
	 */
	public int deleteBusZdjrglByIds(Long[] ids);
}
