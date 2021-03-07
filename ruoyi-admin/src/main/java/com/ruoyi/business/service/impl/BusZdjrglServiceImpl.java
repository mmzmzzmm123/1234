package com.ruoyi.business.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.ruoyi.business.domain.BusCbsgrxx;
import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.domain.BusZdjrCbszbVO;
import com.ruoyi.business.domain.BusZdjrgl;
import com.ruoyi.business.domain.BusZdjrglVO;
import com.ruoyi.business.domain.BusZdjrzbry;
import com.ruoyi.business.mapper.BusCbsgrxxMapper;
import com.ruoyi.business.mapper.BusCbsxxMapper;
import com.ruoyi.business.mapper.BusZdjrglMapper;
import com.ruoyi.business.mapper.BusZdjrzbryMapper;
import com.ruoyi.business.service.IBusZdjrglService;
import com.ruoyi.common.utils.DateUtils;

/**
 * 重大节日管理Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-24
 */
@Service
public class BusZdjrglServiceImpl implements IBusZdjrglService {
	@Autowired
	private BusZdjrglMapper busZdjrglMapper;

	@Autowired
	private BusCbsxxMapper busCbsxxMapper;

	@Autowired
	private BusZdjrzbryMapper busZdjrzbryMapper;

	@Autowired
	private BusCbsgrxxMapper busCbsgrxxMapper;

	/**
	 * 查询重大节日管理
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 重大节日管理
	 */
	@Override
	public BusZdjrglVO selectBusZdjrglById(Long id) {
		BusZdjrgl busZdjrgl = busZdjrglMapper.selectBusZdjrglById(id);
		BusZdjrglVO busZdjrglVO = new BusZdjrglVO();
		BeanUtils.copyProperties(busZdjrgl, busZdjrglVO);
		List<BusCbsxx> busCbsxxList = busCbsxxMapper.selectBusCbsxxList(new BusCbsxx());
		if (busCbsxxList.isEmpty()) {
			return busZdjrglVO;
		}
		// 设置承包商设置的值班人员
		List<BusZdjrzbry> busZdjrzbryList = selectZdjrzbryByZdjrId(id);
		List<BusZdjrCbszbVO> zdjrCbszbVOs = new ArrayList<>(busZdjrzbryList.size());
		for (BusCbsxx e : busCbsxxList) {
			BusZdjrCbszbVO busZdjrCbszbVO = new BusZdjrCbszbVO();
			BeanUtils.copyProperties(e, busZdjrCbszbVO);
			// 设置值班人员
			List<BusZdjrzbry> zbryList = busZdjrzbryList.stream()
					.filter(zbry -> Objects.equal(busZdjrCbszbVO.getId(), zbry.getCbsId())).peek(zbry -> {
						BusCbsgrxx busCbsgrxx = busCbsgrxxMapper.selectBusCbsgrxxById(zbry.getGrId());
						if (busCbsgrxx != null) {
							BeanUtils.copyProperties(busCbsgrxx, zbry);
						}
					}).collect(Collectors.toList());
			busZdjrCbszbVO.setZbryList(zbryList);
			zdjrCbszbVOs.add(busZdjrCbszbVO);
		}
		busZdjrglVO.setCbszbList(zdjrCbszbVOs);
		return busZdjrglVO;
	}

	/**
	 * 根据假日ID获取所有值班人员
	 * 
	 * @param id
	 *            节假日ID
	 * @return
	 */
	private List<BusZdjrzbry> selectZdjrzbryByZdjrId(Long id) {
		BusZdjrzbry busZdjrzbry = new BusZdjrzbry();
		busZdjrzbry.setZdjrId(id);
		return busZdjrzbryMapper.selectBusZdjrzbryList(busZdjrzbry);
	}

	/**
	 * 查询重大节日管理列表
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 重大节日管理
	 */
	@Override
	public List<BusZdjrgl> selectBusZdjrglList(BusZdjrgl busZdjrgl) {
		return busZdjrglMapper.selectBusZdjrglList(busZdjrgl);
	}

	/**
	 * 获取某月的节假日
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return
	 */
	@Override
	public List<BusZdjrgl> selectBusZdjrglByJrrq(int year, int month) {
		Date ksrq = DateUtils.getMinDate(year, month);
		Date jsrq = DateUtils.getMaxDate(year, month);
		return busZdjrglMapper.selectBusZdjrglByJrrq(ksrq, jsrq);
	}

	/**
	 * 新增重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	@Transactional
	@Override
	public BusZdjrgl insertBusZdjrgl(BusZdjrgl busZdjrgl) {
		busZdjrglMapper.deleteByJrrq(busZdjrgl.getJrrq());
		busZdjrglMapper.insertBusZdjrgl(busZdjrgl);
		return busZdjrgl;
	}

	/**
	 * 修改重大节日管理
	 * 
	 * @param busZdjrgl
	 *            重大节日管理
	 * @return 结果
	 */
	@Override
	public int updateBusZdjrgl(BusZdjrgl busZdjrgl) {
		return busZdjrglMapper.updateBusZdjrgl(busZdjrgl);
	}

	/**
	 * 批量删除重大节日管理
	 * 
	 * @param ids
	 *            需要删除的重大节日管理ID
	 * @return 结果
	 */
	@Override
	public int deleteBusZdjrglByIds(Long[] ids) {
		return busZdjrglMapper.deleteBusZdjrglByIds(ids);
	}

	/**
	 * 删除重大节日管理信息
	 * 
	 * @param id
	 *            重大节日管理ID
	 * @return 结果
	 */
	@Override
	public int deleteBusZdjrglById(Long id) {
		return busZdjrglMapper.deleteBusZdjrglById(id);
	}
}
