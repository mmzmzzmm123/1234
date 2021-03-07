package com.ruoyi.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.ruoyi.business.domain.BusCbsgrxx;
import com.ruoyi.business.domain.BusGrzfxx;
import com.ruoyi.business.domain.BusZdjrzbry;
import com.ruoyi.business.domain.vo.BusCbsgrxxSaveVO;
import com.ruoyi.business.domain.vo.BusCbsgrxxVO;
import com.ruoyi.business.mapper.BusCbsgrxxMapper;
import com.ruoyi.business.mapper.BusZdjrzbryMapper;
import com.ruoyi.business.service.IBusCbsgrxxService;
import com.ruoyi.business.service.IBusGrzfxxService;
import com.ruoyi.common.utils.StringUtils;

/**
 * 承包商工人信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Service
public class BusCbsgrxxServiceImpl implements IBusCbsgrxxService {

	@Autowired
	private IBusGrzfxxService busGrzfxxService;

	@Autowired
	private BusCbsgrxxMapper busCbsgrxxMapper;

	@Autowired
	private BusZdjrzbryMapper busZdjrzbryMapper;

	/**
	 * 查询承包商工人信息
	 * 
	 * @param id
	 *            承包商工人信息ID
	 * @return 承包商工人信息
	 */
	@Override
	public BusCbsgrxxVO selectBusCbsgrxxById(Long id) {
		BusCbsgrxx busCbsgrxx = busCbsgrxxMapper.selectBusCbsgrxxById(id);
		return selectByCbsgrxx(busCbsgrxx);
	}

	/**
	 * 查询承包商工人信息列表
	 * 
	 * @param busCbsgrxx
	 *            承包商工人信息
	 * @return 承包商工人信息
	 */
	@Override
	public List<BusCbsgrxx> selectBusCbsgrxxList(BusCbsgrxx busCbsgrxx) {
		return busCbsgrxxMapper.selectBusCbsgrxxList(busCbsgrxx);
	}

	/**
	 * 新增承包商工人信息
	 * 
	 * @param busCbsgrxx
	 *            承包商工人信息
	 * @return 结果
	 */
	@Override
	public int insertBusCbsgrxx(BusCbsgrxxSaveVO busCbsgrxxSaveVO) {
		BusCbsgrxx busCbsgrxx = new BusCbsgrxx();
		BeanUtils.copyProperties(busCbsgrxxSaveVO, busCbsgrxx);
		int insertBusCbsgrxx = busCbsgrxxMapper.insertBusCbsgrxx(busCbsgrxx);

		// 保存自增信息
		List<String> zzxxFileUrls = busCbsgrxxSaveVO.getZzxxFileUrls();
		if (zzxxFileUrls != null && !zzxxFileUrls.isEmpty()) {
			// 保存新的资质
			BusGrzfxx busGrzfxx = new BusGrzfxx();
			busGrzfxx.setCbsId(busCbsgrxx.getCbsId());
			busGrzfxx.setGrId(busCbsgrxx.getId());
			busGrzfxx.setZztp(StringUtils.join(zzxxFileUrls, ","));
			busGrzfxxService.insertBusGrzfxx(busGrzfxx);
		}
		return insertBusCbsgrxx;
	}

	/**
	 * 修改承包商工人信息
	 * 
	 * @param busCbsgrxxSaveVO
	 *            承包商工人信息
	 * @return 结果
	 */
	@Override
	public int updateBusCbsgrxx(BusCbsgrxxSaveVO busCbsgrxxSaveVO) {
		Long grId = busCbsgrxxSaveVO.getId();
		Long cbsId = busCbsgrxxSaveVO.getCbsId();
		BusCbsgrxx busCbsgrxx = busCbsgrxxMapper.selectBusCbsgrxxById(grId);
		BeanUtils.copyProperties(busCbsgrxxSaveVO, busCbsgrxx);
		// 保存资质信息 先删除再新增
		busGrzfxxService.deleteByCbsIdAndGrId(cbsId, grId);
		List<String> zzxxFileUrls = busCbsgrxxSaveVO.getZzxxFileUrls();
		if (zzxxFileUrls != null && !zzxxFileUrls.isEmpty()) {
			// 保存新的资质
			BusGrzfxx busGrzfxx = new BusGrzfxx();
			busGrzfxx.setCbsId(busCbsgrxx.getCbsId());
			busGrzfxx.setGrId(busCbsgrxx.getId());
			busGrzfxx.setZztp(StringUtils.join(zzxxFileUrls, ","));
			busGrzfxxService.insertBusGrzfxx(busGrzfxx);
		}
		return busCbsgrxxMapper.updateBusCbsgrxx(busCbsgrxx);
	}

	/**
	 * 批量删除承包商工人信息
	 * 
	 * @param ids
	 *            需要删除的承包商工人信息ID
	 * @return 结果
	 */
	@Override
	public int deleteBusCbsgrxxByIds(Long[] ids) {
		return busCbsgrxxMapper.deleteBusCbsgrxxByIds(ids);
	}

	/**
	 * 删除承包商工人信息信息
	 * 
	 * @param id
	 *            承包商工人信息ID
	 * @return 结果
	 */
	@Override
	public int deleteBusCbsgrxxById(Long id) {
		return busCbsgrxxMapper.deleteBusCbsgrxxById(id);
	}

	/**
	 * 查询承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 承包商资质信息
	 */
	@Override
	public List<BusCbsgrxxVO> selectByCbsId(Long cbsId) {
		List<BusCbsgrxx> busCbsgrxxList = busCbsgrxxMapper.selectByCbsId(cbsId);
		List<BusCbsgrxxVO> busCbsgrxxVOList = new ArrayList<>(busCbsgrxxList.size());
		for (BusCbsgrxx busCbsgrxx : busCbsgrxxList) {
			BusCbsgrxxVO busCbsgrxxVO = selectByCbsgrxx(busCbsgrxx);
			busCbsgrxxVOList.add(busCbsgrxxVO);
		}
		return busCbsgrxxVOList;
	}

	/**
	 * 查询承包商资质信息
	 * 
	 * @param id
	 *            承包商资质信息ID
	 * @return 承包商资质信息
	 */
	@Transactional
	@Override
	public int deleteByCbsId(Long cbsId) {
		List<BusCbsgrxxVO> busCbszzxxList = selectByCbsId(cbsId);
		if (busCbszzxxList.isEmpty()) {
			return 0;
		}
		List<Long> ids = busCbszzxxList.stream().map(BusCbsgrxxVO::getId).collect(Collectors.toList());
		return deleteBusCbsgrxxByIds(ids.toArray(new Long[ids.size()]));
	}

	private BusCbsgrxxVO selectByCbsgrxx(BusCbsgrxx busCbsgrxx) {
		Long grId = busCbsgrxx.getId();
		Long cbsId = busCbsgrxx.getCbsId();
		List<BusGrzfxx> busGrzfxxList = busGrzfxxService.selectByCbsIdAndGrId(cbsId, grId);
		List<String> zzxxFileUrls = busGrzfxxList.stream().filter(e -> e.getZztp() != null)
				.map(e -> e.getZztp().split(",")).flatMap(Arrays::stream).collect(Collectors.toList());
		BusCbsgrxxVO busCbsgrxxVO = new BusCbsgrxxVO();
		BeanUtils.copyProperties(busCbsgrxx, busCbsgrxxVO);
		busCbsgrxxVO.setZzxxFileUrls(zzxxFileUrls);
		return busCbsgrxxVO;
	}

	@Override
	public List<BusCbsgrxxVO> zbryList(Long zdjrId, Long cbsId) {
		List<BusCbsgrxx> busCbsgrxxList = busCbsgrxxMapper.selectByCbsId(cbsId);
		List<BusZdjrzbry> zbryList = busZdjrzbryMapper.selectBusZdjrzbryByZdjrIdAndCbsId(zdjrId, cbsId);
		return busCbsgrxxList.stream().map(e -> {
			BusCbsgrxxVO busCbsgrxxVO = new BusCbsgrxxVO();
			BeanUtils.copyProperties(e, busCbsgrxxVO);
			boolean selected = zbryList.stream().anyMatch(zbry -> Objects.equal(zbry.getGrId(), e.getId()));
			busCbsgrxxVO.setSelected(selected);
			return busCbsgrxxVO;
		}).collect(Collectors.toList());
	}
}
