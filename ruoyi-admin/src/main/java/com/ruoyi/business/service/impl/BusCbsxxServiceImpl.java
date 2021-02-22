package com.ruoyi.business.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.domain.BusCbszzxx;
import com.ruoyi.business.domain.vo.BusCbsgrxxVO;
import com.ruoyi.business.domain.vo.BusCbsxxSaveVO;
import com.ruoyi.business.domain.vo.BusCbsxxVO;
import com.ruoyi.business.mapper.BusCbsxxMapper;
import com.ruoyi.business.service.IBusCbsgrxxService;
import com.ruoyi.business.service.IBusCbsxxService;
import com.ruoyi.business.service.IBusCbszzxxService;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * 承包商信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Service
public class BusCbsxxServiceImpl implements IBusCbsxxService {

	@Autowired
	private BusCbsxxMapper busCbsxxMapper;

	@Autowired
	private IBusCbszzxxService busCbszzxxService;

	@Autowired
	private IBusCbsgrxxService busCbsgrxxService;

	/**
	 * 查询承包商信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 承包商信息
	 */
	@Override
	public BusCbsxxVO selectBusCbsxxById(Long id) {
		BusCbsxx busCbsxx = busCbsxxMapper.selectBusCbsxxById(id);
		return selectByCbsxx(busCbsxx);
	}

	/**
	 * 查询承包商信息列表
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 承包商信息
	 */
	@Override
	public List<BusCbsxxVO> selectBusCbsxxList(BusCbsxx busCbsxx) {
		List<BusCbsxx> busCbsxxList = busCbsxxMapper.selectBusCbsxxList(busCbsxx);
		List<BusCbsxxVO> busCbsxxListVOList = new ArrayList<>();
		for (BusCbsxx cbsxx : busCbsxxList) {
			BusCbsxxVO cbsxxVO = selectByCbsxx(cbsxx);
			busCbsxxListVOList.add(cbsxxVO);
		}
		return busCbsxxListVOList;
	}

	@Transactional
	@Override
	public int insertBusCbsxx(BusCbsxxSaveVO busCbsxxSaveVO) {
		BusCbsxx busCbsxx = new BusCbsxx();
		BeanUtils.copyProperties(busCbsxxSaveVO, busCbsxx);
		Long cbsId = busCbsxxSaveVO.getId();
		Long createUserId = SecurityUtils.getUserId();
		busCbsxx.setXrrId(createUserId);
		busCbsxx.setXrsj(new Date());
		busCbsxx.setXgrId(createUserId);
		busCbsxx.setXgsj(new Date());
		int insertBusCbsxx = busCbsxxMapper.insertBusCbsxx(busCbsxx);
		// 保存自增信息
		List<String> zzxxFileUrls = busCbsxxSaveVO.getZzxxFileUrls();
		if (zzxxFileUrls != null && !zzxxFileUrls.isEmpty()) {
			// 保存新的资质
			BusCbszzxx busCbszzxx = new BusCbszzxx();
			busCbszzxx.setCbsId(cbsId);
			busCbszzxx.setZztp(StringUtils.join(zzxxFileUrls, ","));
			busCbszzxxService.insertBusCbszzxx(busCbszzxx);
		}
		return insertBusCbsxx;
	}

	/**
	 * 修改承包商信息
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 结果
	 */
	@Transactional
	@Override
	public int updateBusCbsxx(BusCbsxxSaveVO busCbsxxSaveVO) {
		Long id = busCbsxxSaveVO.getId();
		BusCbsxx cbsxx = busCbsxxMapper.selectBusCbsxxById(id);
		BeanUtils.copyProperties(busCbsxxSaveVO, cbsxx);
		Long createUserId = SecurityUtils.getUserId();
		cbsxx.setXgrId(createUserId);
		cbsxx.setXgsj(new Date());
		// 保存资质信息 先删除再新增
		busCbszzxxService.deleteByCbsId(id);
		List<String> zzxxFileUrls = busCbsxxSaveVO.getZzxxFileUrls();
		if (zzxxFileUrls != null && !zzxxFileUrls.isEmpty()) {
			BusCbszzxx busCbszzxx = new BusCbszzxx();
			busCbszzxx.setCbsId(id);
			busCbszzxx.setZztp(StringUtils.join(zzxxFileUrls, ","));
			busCbszzxxService.insertBusCbszzxx(busCbszzxx);
		}
		return busCbsxxMapper.updateBusCbsxx(cbsxx);
	}

	/**
	 * 批量删除承包商信息
	 * 
	 * @param ids
	 *            需要删除的承包商信息ID
	 * @return 结果
	 */
	@Override
	public int deleteBusCbsxxByIds(Long[] ids) {
		return busCbsxxMapper.deleteBusCbsxxByIds(ids);
	}

	/**
	 * 删除承包商信息信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 结果
	 */
	@Override
	public int deleteBusCbsxxById(Long id) {
		return busCbsxxMapper.deleteBusCbsxxById(id);
	}

	private BusCbsxxVO selectByCbsxx(BusCbsxx busCbsxx) {
		Long cbsId = busCbsxx.getId();
		List<BusCbsgrxxVO> ryxxList = busCbsgrxxService.selectByCbsId(cbsId);
		List<BusCbszzxx> cbszzxxList = busCbszzxxService.selectByCbsId(cbsId);
		List<String> zzxxList = cbszzxxList.stream().filter(e -> e.getZztp() != null).map(e -> e.getZztp().split(","))
				.flatMap(Arrays::stream).collect(Collectors.toList());
		BusCbsxxVO busCbsxxVO = new BusCbsxxVO();
		busCbsxxVO.setCbsxx(busCbsxx);
		busCbsxxVO.setZzxxList(zzxxList);
		busCbsxxVO.setRyxxList(ryxxList);
		return busCbsxxVO;
	}
}
