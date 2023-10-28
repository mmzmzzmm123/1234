package com.ruoyi.onethinker.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.ruoyi.common.enums.IntegralTypeEnum;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.onethinker.domain.PlatformUser;
import com.ruoyi.onethinker.domain.PlatformUserIntegralHistory;
import com.ruoyi.onethinker.dto.PlatformUserIntegralReqDTO;
import com.ruoyi.onethinker.mapper.PlatformUserIntegralMapper;
import com.ruoyi.onethinker.domain.PlatformUserIntegral;
import com.ruoyi.onethinker.service.IPlatformUserDetailService;
import com.ruoyi.onethinker.service.IPlatformUserIntegralHistoryService;
import com.ruoyi.onethinker.service.IPlatformUserIntegralService;
import com.ruoyi.onethinker.service.IPlatformUserService;
import lombok.extern.log4j.Log4j2;

/**
 * 平台用户积分Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralServiceImpl implements IPlatformUserIntegralService {
    @Autowired
    private PlatformUserIntegralMapper platformUserIntegralMapper;
    @Autowired
    private IPlatformUserDetailService platformUserDetailService;
    @Autowired
    private IPlatformUserIntegralHistoryService platformUserIntegralHistoryService;

    /**
     * 查询平台用户积分
     *
     * @param id 平台用户积分主键
     * @return 平台用户积分
     */
    @Override
    public PlatformUserIntegral selectPlatformUserIntegralById(Long id) {
        return platformUserIntegralMapper.selectPlatformUserIntegralById(id);
    }

    /**
     * 查询平台用户积分列表
     *
     * @param platformUserIntegral 平台用户积分
     * @return 平台用户积分
     */
    @Override
    public List<PlatformUserIntegral> selectPlatformUserIntegralList(PlatformUserIntegral platformUserIntegral) {
        return platformUserIntegralMapper.selectPlatformUserIntegralList(platformUserIntegral);
    }

    /**
     * 新增平台用户积分
     *
     * @param reqDTO 平台用户积分
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertPlatformUserIntegral(PlatformUserIntegralReqDTO reqDTO) {
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPhone()),"用户信息为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getIntegral()),"添加积分为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getBatchNo()),"添加批次号不能为空");
        // 查询用户信息
        PlatformUser platformUser = platformUserDetailService.queryUserByPhone(reqDTO.getPhone());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUser),"平台用户有误");
        // 查看活动批次号是否存在
        // 保存明细数据
        PlatformUserIntegralHistory platformUserIntegralHistory = new PlatformUserIntegralHistory();
        platformUserIntegralHistory.setIntegral(reqDTO.getIntegral());
        platformUserIntegralHistory.setPuUserId(platformUser.getId());
        platformUserIntegralHistory.setType(IntegralTypeEnum.RECHARGE.getCode());
        platformUserIntegralHistory.setBatchNo(reqDTO.getBatchNo());
        int size = platformUserIntegralHistoryService.insertPlatformUserIntegralHistory(platformUserIntegralHistory);
        int result = 0;
        if (size > 0) {
            // 更新平台用户信息
            PlatformUserIntegral params = new PlatformUserIntegral();
            params.setBatchNo(reqDTO.getBatchNo());
            params.setPuUserId(platformUser.getId());
            List<PlatformUserIntegral> platformUserIntegrals = platformUserIntegralMapper.selectPlatformUserIntegralList(params);
            if (ObjectUtils.isEmpty(platformUserIntegrals) || platformUserIntegrals.isEmpty()) {
                params.setCreateTime(new Date());
                params.setResidualIntegral(reqDTO.getIntegral());
                params.setTotalIntegral(reqDTO.getIntegral());
                params.setEnabled(PlatformUserIntegral.STATE_TYPE_ENABLED);
                result = platformUserIntegralMapper.insertPlatformUserIntegral(params);

            } else {
                params = platformUserIntegrals.get(0);
                params.setOrgResidualIntegral(params.getResidualIntegral());
                params.setTotalIntegral(params.getTotalIntegral() + reqDTO.getIntegral());
                params.setResidualIntegral(params.getResidualIntegral() + reqDTO.getIntegral());
                result = platformUserIntegralMapper.updateIntegralByResidualIntegralAndId(params);
            }
        }
        Assert.isTrue(result > 0,"添加用户积分失败");
        return result;
    }

    /**
     * 修改平台用户积分
     *
     * @param platformUserIntegral 平台用户积分
     * @return 结果
     */
    @Override
    public int updatePlatformUserIntegral(PlatformUserIntegral platformUserIntegral) {
                platformUserIntegral.setUpdateTime(DateUtils.getNowDate());
        return platformUserIntegralMapper.updatePlatformUserIntegral(platformUserIntegral);
    }

    /**
     * 批量删除平台用户积分
     *
     * @param ids 需要删除的平台用户积分主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserIntegralByIds(Long[] ids) {
        return platformUserIntegralMapper.deletePlatformUserIntegralByIds(ids);
    }

    /**
     * 删除平台用户积分信息
     *
     * @param id 平台用户积分主键
     * @return 结果
     */
    @Override
    public int deletePlatformUserIntegralById(Long id) {
        return platformUserIntegralMapper.deletePlatformUserIntegralById(id);
    }
}
