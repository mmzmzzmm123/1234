package com.ruoyi.api.user.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.user.model.dto.ApiUserAccountFormDto;
import com.ruoyi.api.user.model.vo.ApiUserAccountVo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.SysYesNoEnums;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.user.domain.UserSocialAccounts;
import com.ruoyi.user.mapper.UserSocialAccountsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/21 20:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiUserAccountService {

    private final UserSocialAccountsMapper userSocialAccountsMapper;

    /**
     * 获取用户账号列表
     *
     * @param userId 用户标识
     * @return 结果
     */
    public List<ApiUserAccountVo> selectByUserId(Long userId) {
        log.info("获取用户账号列表：开始，参数：{}", userId);
        List<ApiUserAccountVo> voList = new ArrayList<>();
        if (ObjectUtil.isNotNull(userId)) {
            UserSocialAccounts select = new UserSocialAccounts();
            select.setUserId(userId);
            List<UserSocialAccounts> userSocialAccounts = userSocialAccountsMapper.selectUserSocialAccountsList(select);
            if (ObjectUtil.isNotEmpty(userSocialAccounts)) {
                for (UserSocialAccounts item : userSocialAccounts) {
                    ApiUserAccountVo vo = new ApiUserAccountVo();
                    BeanUtils.copyBeanProp(vo, item);
                    voList.add(vo);
                }
            }
        }
        log.info("获取用户账号列表：完成，返回数据：{}", voList);
        return voList;
    }

    /**
     * 新增用户账号
     *
     * @param dto 数据
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> add(ApiUserAccountFormDto dto) {
        log.info("新增用户账号：开始，数据：{}", dto);
        if (ObjectUtil.isNull(dto.getUserId()) || StringUtils.isBlank(dto.getAccountServiceProvider()) || StringUtils.isBlank(dto.getNum())) {
            log.warn("新增用户账号：失败，必要参数为空");
            return R.warn("亲爱的，请输入必要参数不能为空哟");
        }
        UserSocialAccounts insert = new UserSocialAccounts();
        BeanUtils.copyBeanProp(insert, dto);
        Date now = DateUtils.getNowDate();
        insert.setCreateTime(now)
                .setUpdateTime(now);
        // 判断是否默认，如果默认需要把已设置默认的数据去除
        if (StringUtils.isNotBlank(dto.getIfDefault()) && SysYesNoEnums.YES.getCode().equals(dto.getIfDefault())) {
            Long defaultId = selectUserDefaultId(dto.getUserId());
            if (ObjectUtil.isNotNull(defaultId)) {
                UserSocialAccounts update = new UserSocialAccounts();
                update.setId(defaultId)
                        .setIfDefault(SysYesNoEnums.NO.getCode())
                        .setUpdateTime(now);
                userSocialAccountsMapper.updateUserSocialAccounts(update);
            }
        }
        userSocialAccountsMapper.insertUserSocialAccounts(insert);
        log.info("新增用户账号：完成");
        return R.ok(Boolean.TRUE);
    }

    /**
     * 查询用户默认记录数据
     *
     * @param userId 用户标识
     * @return 默认记录标识
     */
    private Long selectUserDefaultId(Long userId) {
        UserSocialAccounts select = new UserSocialAccounts();
        select.setUserId(userId)
                .setIfDefault(SysYesNoEnums.YES.getCode());
        List<UserSocialAccounts> userSocialAccounts = userSocialAccountsMapper.selectUserSocialAccountsList(select);
        if (ObjectUtil.isNotEmpty(userSocialAccounts)) {
            return userSocialAccounts.get(0).getId();
        }
        return null;
    }

    /**
     * 更新用户账号
     *
     * @param dto 数据
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> update(ApiUserAccountFormDto dto) {
        log.info("更新用户账号：开始，参数：{}", dto);
        Date now = DateUtils.getNowDate();
        UserSocialAccounts userSocialAccounts = new UserSocialAccounts();
        BeanUtils.copyBeanProp(userSocialAccounts, dto);
        userSocialAccounts.setUpdateTime(now);
        // 看看是否新的默认
        if (ObjectUtil.isNotNull(dto.getIfDefault()) && SysYesNoEnums.YES.getCode().equals(dto.getIfDefault())) {
            Long defaultId = selectUserDefaultId(dto.getUserId());
            if (ObjectUtil.isNotNull(defaultId) && !defaultId.equals(dto.getId())) {
                UserSocialAccounts update = new UserSocialAccounts();
                update.setId(defaultId)
                        .setIfDefault(SysYesNoEnums.NO.getCode())
                        .setUpdateTime(now);
                userSocialAccountsMapper.updateUserSocialAccounts(update);
            }
        }
        userSocialAccountsMapper.updateUserSocialAccounts(userSocialAccounts);
        log.info("更新用户账号：完成");
        return R.ok(Boolean.TRUE);
    }

    /**
     * 删除用户账号
     *
     * @param id
     * @return 结果
     */
    public Boolean deleteById(Long id) {
        userSocialAccountsMapper.deleteUserSocialAccountsById(id);
        return Boolean.TRUE;
    }
}
