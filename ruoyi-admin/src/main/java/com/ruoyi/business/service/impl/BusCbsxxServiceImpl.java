package com.ruoyi.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.business.domain.BusCbsxx;
import com.ruoyi.business.mapper.BusCbsxxMapper;
import com.ruoyi.business.service.IBusCbsxxService;
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

	/**
	 * 查询承包商信息
	 * 
	 * @param id
	 *            承包商信息ID
	 * @return 承包商信息
	 */
	@Override
	public BusCbsxx selectBusCbsxxById(Long id) {
		return busCbsxxMapper.selectBusCbsxxById(id);
	}

	/**
	 * 查询承包商信息列表
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 承包商信息
	 */
	@Override
	public List<BusCbsxx> selectBusCbsxxList(BusCbsxx busCbsxx) {
		return busCbsxxMapper.selectBusCbsxxList(busCbsxx);
	}

	/**
	 * 新增承包商信息
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 结果
	 */
	@Transactional
	@Override
	public int insertBusCbsxx(BusCbsxx busCbsxx) {
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
		
		if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
			throw new CustomException("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
        	throw new CustomException("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
        	throw new CustomException("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        userService.insertUser(user);
        
        // 注册承包商
		return busCbsxxMapper.insertBusCbsxx(busCbsxx);
	}

	/**
	 * 修改承包商信息
	 * 
	 * @param busCbsxx
	 *            承包商信息
	 * @return 结果
	 */
	@Override
	public int updateBusCbsxx(BusCbsxx busCbsxx) {
		return busCbsxxMapper.updateBusCbsxx(busCbsxx);
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

}
