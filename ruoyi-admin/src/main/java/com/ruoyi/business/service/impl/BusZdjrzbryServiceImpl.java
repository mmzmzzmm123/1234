package com.ruoyi.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.business.mapper.BusZdjrzbryMapper;
import com.ruoyi.business.domain.BusZdjrzbry;
import com.ruoyi.business.service.IBusZdjrzbryService;

/**
 * 重大节日值班人员Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-24
 */
@Service
public class BusZdjrzbryServiceImpl implements IBusZdjrzbryService {
	@Autowired
	private BusZdjrzbryMapper busZdjrzbryMapper;

	/**
	 * 查询重大节日值班人员
	 * 
	 * @param id
	 *            重大节日值班人员ID
	 * @return 重大节日值班人员
	 */
	@Override
	public BusZdjrzbry selectBusZdjrzbryById(Long id) {
		return busZdjrzbryMapper.selectBusZdjrzbryById(id);
	}

	/**
	 * 查询重大节日值班人员列表
	 * 
	 * @param busZdjrzbry
	 *            重大节日值班人员
	 * @return 重大节日值班人员
	 */
	@Override
	public List<BusZdjrzbry> selectBusZdjrzbryList(BusZdjrzbry busZdjrzbry) {
		return busZdjrzbryMapper.selectBusZdjrzbryList(busZdjrzbry);
	}

	/**
	 * 新增重大节日值班人员
	 * 
	 * @param busZdjrzbry
	 *            重大节日值班人员
	 * @return 结果
	 */
	@Override
	public int insertBusZdjrzbry(BusZdjrzbry busZdjrzbry) {
		return busZdjrzbryMapper.insertBusZdjrzbry(busZdjrzbry);
	}

	/**
	 * 修改重大节日值班人员
	 * 
	 * @param busZdjrzbry
	 *            重大节日值班人员
	 * @return 结果
	 */
	@Override
	public int updateBusZdjrzbry(BusZdjrzbry busZdjrzbry) {
		return busZdjrzbryMapper.updateBusZdjrzbry(busZdjrzbry);
	}

	/**
	 * 批量删除重大节日值班人员
	 * 
	 * @param ids
	 *            需要删除的重大节日值班人员ID
	 * @return 结果
	 */
	@Override
	public int deleteBusZdjrzbryByIds(Long[] ids) {
		return busZdjrzbryMapper.deleteBusZdjrzbryByIds(ids);
	}

	/**
	 * 删除重大节日值班人员信息
	 * 
	 * @param id
	 *            重大节日值班人员ID
	 * @return 结果
	 */
	@Override
	public int deleteBusZdjrzbryById(Long id) {
		return busZdjrzbryMapper.deleteBusZdjrzbryById(id);
	}
}
