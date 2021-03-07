package com.ruoyi.business.service;

import java.util.List;
import com.ruoyi.business.domain.BusZdjrgl;
import com.ruoyi.business.domain.BusZdjrglVO;

/**
 * 重大节日管理Service接口
 * 
 * @author yaowei
 * @date 2021-02-24
 */
public interface IBusZdjrglService {
	/**
	 * 查询重大节日管理
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 重大节日管理
	 */
	public BusZdjrglVO selectBusZdjrglById(Long id);

	/**
	 * 查询重大节日管理列表
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 重大节日管理集合
	 */
	public List<BusZdjrgl> selectBusZdjrglList(BusZdjrgl busZdjrgl);

	/**
	 * 新增重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	public BusZdjrgl insertBusZdjrgl(BusZdjrgl busZdjrgl);

	/**
	 * 修改重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	public int updateBusZdjrgl(BusZdjrgl busZdjrgl);

	/**
	 * 批量删除重大节日管理
	 * 
	 * @param ids
	 *            需要删除的重大节日管理ID
	 * @return 结果
	 */
	public int deleteBusZdjrglByIds(Long[] ids);

	/**
	 * 删除重大节日管理信息
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 结果
	 */
	public int deleteBusZdjrglById(Long id);

	/**
	 * 获取某月的节假日
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return
	 */
	List<BusZdjrgl> selectBusZdjrglByJrrq(int year, int month);
}
