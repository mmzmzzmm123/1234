package com.ruoyi.office.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.office.domain.vo.StoreUserVo;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TStoreUserMapper;
import com.ruoyi.office.domain.TStoreUser;
import com.ruoyi.office.service.ITStoreUserService;

/**
 * 商家店铺用户Service业务层处理
 *
 * @author ruoyi
 * @date 2023-10-11
 */
@Service
public class TStoreUserServiceImpl extends ServiceImpl<TStoreUserMapper, TStoreUser> implements ITStoreUserService {
    @Autowired
    private TStoreUserMapper tStoreUserMapper;

    /**
     * 查询商家店铺用户
     *
     * @param id 商家店铺用户主键
     * @return 商家店铺用户
     */
    @Override
    public TStoreUser selectTStoreUserById(Long id) {
        return tStoreUserMapper.selectTStoreUserById(id);
    }

    /**
     * 查询商家店铺用户列表
     *
     * @param tStoreUser 商家店铺用户
     * @return 商家店铺用户
     */
    @Override
    public List<TStoreUser> selectTStoreUserList(TStoreUser tStoreUser) {
        return tStoreUserMapper.selectTStoreUserList(tStoreUser);
    }

    @Autowired
    ISysUserService userService;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 新增商家店铺用户
     *
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    @Override
    public int insertTStoreUser(StoreUserVo vo) {
        final SysUser sysUsers = userService.selectUserByUserName(vo.getUserName());
        if (sysUsers != null) {
            throw new ServiceException("用户账号已存在");
        }
        SysUser user = new SysUser();
        user.setUserName(vo.getUserName());
        user.setNickName(vo.getNickName());
        user.setPhonenumber(vo.getPhonenumber());
        user.setPassword(SecurityUtils.encryptPassword(vo.getPassword()));
        userService.registerUser(user);

        if (StringUtils.isNotEmpty(vo.getRoleName())) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : vo.getRoleName().split(",")) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(Long.parseLong(roleId));
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }

        TStoreUser tStoreUser = new TStoreUser();
        tStoreUser.setCreateTime(DateUtils.getNowDate());
        tStoreUser.setStoreId(vo.getStoreId());
        tStoreUser.setUserId(user.getUserId());

        return tStoreUserMapper.insertTStoreUser(tStoreUser);
    }

    /**
     * 修改商家店铺用户
     *
     * @param tStoreUser 商家店铺用户
     * @return 结果
     */
    @Override
    public int updateTStoreUser(StoreUserVo vo) {

        TStoreUser storeUser = selectTStoreUserById(vo.getId());

        final SysUser sysUsers = userService.selectUserByUserName(vo.getUserName());
        if (sysUsers != null && sysUsers.getUserId() != storeUser.getUserId()) {
            throw new ServiceException("用户账号已存在");
        }

        SysUser user = userService.selectUserById(storeUser.getUserId());
        user.setUserName(vo.getUserName());
        user.setNickName(vo.getNickName());
        user.setPhonenumber(vo.getPhonenumber());
        if (!user.getPassword().equalsIgnoreCase(vo.getPassword())) {
            user.setPassword(SecurityUtils.encryptPassword(vo.getPassword()));
        }
        userService.updateUserProfile(user);

        userRoleMapper.deleteUserRoleByUserId(storeUser.getUserId());
        if (StringUtils.isNotEmpty(vo.getRoleName())) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (String roleId : vo.getRoleName().split(",")) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(storeUser.getUserId());
                ur.setRoleId(Long.parseLong(roleId));
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }

        storeUser.setStoreId(vo.getStoreId());
        return tStoreUserMapper.updateTStoreUser(storeUser);
    }

    /**
     * 批量删除商家店铺用户
     *
     * @param ids 需要删除的商家店铺用户主键
     * @return 结果
     */
    @Override
    public int deleteTStoreUserByIds(Long[] ids) {
        return tStoreUserMapper.deleteTStoreUserByIds(ids);
    }

    /**
     * 删除商家店铺用户信息
     *
     * @param id 商家店铺用户主键
     * @return 结果
     */
    @Override
    public int deleteTStoreUserById(Long id) {
        return tStoreUserMapper.deleteTStoreUserById(id);
    }

    @Autowired
    ISysRoleService sysRoleService;

    @Override
    public List<StoreUserVo> selectTStoreUserH5listList(TStoreUser tStoreUser) {
        List<StoreUserVo> list = tStoreUserMapper.selectTStoreUserH5listList(tStoreUser);
        for (StoreUserVo su : list) {
            List<SysRole> userRoles = sysRoleService.selectUserRolesByUserId(su.getUserId());
            final Set<String> roleSet = userRoles.stream().map(x -> x.getRoleName()).collect(Collectors.toSet());
            String roleName = "";
            for (String r : roleSet) {
                roleName += "," + r;
            }
            su.setRoleName(roleName.substring(1));
        }
        return list;
    }
}