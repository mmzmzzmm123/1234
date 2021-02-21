package com.ruoyi.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.domain.BusCbszzxx;
import com.ruoyi.business.domain.vo.BusCbsxxSaveVO;
import com.ruoyi.business.domain.vo.BusCbsxxVO;
import com.ruoyi.business.mapper.BusCbsxxMapper;
import com.ruoyi.business.service.IBusCbsxxService;
import com.ruoyi.business.service.IBusCbszzxxService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * 承包商信息Service业务层处理
 * 
 * @author yaowei
 * @date 2021-02-21
 */
@Service
public class BusCbsxxServiceImpl implements IBusCbsxxService {

	@Autowired
	private ISysUserService userService;

	@Autowired
	private BusCbsxxMapper busCbsxxMapper;

	@Autowired
	private IBusCbszzxxService busCbszzxxService;

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
		String cbsName = busCbsxx.getCbsName();
		BusCbsxx cbsxx = busCbsxxMapper.checkNameUnique(cbsName);
		if (cbsxx != null) {
			throw new CustomException("承包商名称已存在！");
		}
		String lxfs = busCbsxx.getLxfs();
		cbsxx = busCbsxxMapper.checkPhoneNumberUnique(lxfs);
		if (cbsxx != null) {
			throw new CustomException("承包商负责人联系方式已存在！");
		}

		String createBy = SecurityUtils.getUsername();

		// 注册添加用户
		SysUser user = new SysUser();
		user.setNickName(busCbsxx.getFzr());
		user.setUserName(lxfs);
		user.setPassword(lxfs);
		user.setRemark(cbsName);
		user.setPhonenumber(lxfs);
		user.setStatus("0");
		user.setSex("0");
		user.setRoleIds(new Long[] { 3L });
		user.setDeptId(102L);

		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
			throw new CustomException("新增承包商'" + user.getUserName() + "'失败，登录账号已存在");
		} else if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			throw new CustomException("新增承包商'" + user.getUserName() + "'失败，手机号码已存在");
		}

		user.setCreateBy(createBy);
		user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
		userService.insertUser(user);

		// 设置用户ID为承包商ID
		Long userId = user.getUserId();
		busCbsxx.setId(userId);

		int insertBusCbsxx = busCbsxxMapper.insertBusCbsxx(busCbsxx);

		// 保存自增信息
		List<String> zzxxFileUrls = busCbsxxSaveVO.getZzxxFileUrls();
		if (zzxxFileUrls != null && !zzxxFileUrls.isEmpty()) {
			// 删除所有资质
			busCbszzxxService.deleteByCbsId(userId);
			// 保存新的资质
			BusCbszzxx busCbszzxx = new BusCbszzxx();
			busCbszzxx.setCbsId(userId);
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
		List<BusCbszzxx> cbszzxxList = busCbszzxxService.selectByCbsId(cbsId);
		List<String> zzxxList = cbszzxxList.stream().map(BusCbszzxx::getZztp).collect(Collectors.toList());
		BusCbsxxVO busCbsxxVO = new BusCbsxxVO();
		busCbsxxVO.setCbsxx(busCbsxx);
		busCbsxxVO.setZzxxList(zzxxList);
		return busCbsxxVO;
	}
}
