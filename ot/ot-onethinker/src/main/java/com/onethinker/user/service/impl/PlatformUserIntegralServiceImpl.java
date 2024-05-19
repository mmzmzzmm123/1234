package com.onethinker.user.service.impl;

import com.google.common.collect.Maps;
import com.onethinker.user.domain.PlatformUser;
import com.onethinker.user.domain.PlatformUserDetail;
import com.onethinker.user.domain.PlatformUserIntegral;
import com.onethinker.user.domain.PlatformUserIntegralHistory;
import com.onethinker.user.dto.PlatformUserIntegralReqDTO;
import com.onethinker.user.dto.PlatformUserIntegralResDTO;
import com.onethinker.user.mapper.PlatformUserIntegralMapper;
import com.onethinker.user.service.IPlatformUserDetailService;
import com.onethinker.user.service.IPlatformUserIntegralHistoryService;
import com.onethinker.user.service.IPlatformUserIntegralService;
import com.onethinker.common.enums.IntegralTypeEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 平台用户积分Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Service
@Log4j2
public class PlatformUserIntegralServiceImpl implements IPlatformUserIntegralService {
    @Resource
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
    public List<PlatformUserIntegralResDTO> selectPlatformUserIntegralList(PlatformUserIntegral platformUserIntegral) {
        List<PlatformUserIntegral> platformUserIntegrals = platformUserIntegralMapper.selectPlatformUserIntegralList(platformUserIntegral);
        if (ObjectUtils.isEmpty(platformUserIntegrals) || platformUserIntegrals.isEmpty()) {
            return Lists.newArrayList();
        }
        // 获取用户信息
        List<Long> puUserIds = platformUserIntegrals.stream().map(PlatformUserIntegral::getPuUserId).distinct().collect(Collectors.toList());
        Map<String, String> puUserMap = platformUserDetailService.selectUserPhoneByUserIds(puUserIds);
        // 获取活动内容
        List<String> batchNoList = platformUserIntegrals.stream().map(PlatformUserIntegral::getBatchNo).distinct().collect(Collectors.toList());
        Map<String, String> activityNameMap = queryActivityName(batchNoList);
        return platformUserIntegrals.stream().map(integral -> {
            PlatformUserIntegralResDTO resDTO = new PlatformUserIntegralResDTO();
            BeanUtils.copyProperties(integral, resDTO);
            resDTO.setPhone(puUserMap.getOrDefault(integral.getPuUserId().toString(), ""));
            resDTO.setActivityName(activityNameMap.getOrDefault(integral.getBatchNo(), ""));
            return resDTO;
        }).collect(Collectors.toList());
    }

    private Map<String, String> queryActivityName(List<String> batchNoList) {
        //目前只有默认
        Map<String, String> result = Maps.newHashMap();
        for (String s : batchNoList) {
            result.put(s, "平台" + s);
        }
        return result;
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
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPhone()), "用户信息为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getIntegral()), "添加积分为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getBatchNo()), "添加批次号不能为空");
        // 查询用户信息
        PlatformUserDetail platformUser = platformUserDetailService.selectPlatformUserDetailByDataId(reqDTO.getPhone());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUser), "用户不存在");
        // 查看活动批次号是否存在
        // 保存明细数据
        PlatformUserIntegralHistory platformUserIntegralHistory = new PlatformUserIntegralHistory();
        platformUserIntegralHistory.setIntegral(reqDTO.getIntegral());
        platformUserIntegralHistory.setPuUserId(platformUser.getId());
        platformUserIntegralHistory.setType(IntegralTypeEnum.RECHARGE.getCode());
        platformUserIntegralHistory.setBatchNo(reqDTO.getBatchNo());
        platformUserIntegralHistory.setRemark(reqDTO.getRemark());
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
        Assert.isTrue(result > 0, "添加用户积分失败");
        return result;
    }

    @Override
    public List<PlatformUserIntegralHistory> queryIntegralDetail(PlatformUserIntegralReqDTO reqDTO) {
        PlatformUserIntegralHistory platformUserIntegralHistory = new PlatformUserIntegralHistory();
        platformUserIntegralHistory.setPuUserId(reqDTO.getPuUserId());
        return platformUserIntegralHistoryService.selectPlatformUserIntegralHistoryList(platformUserIntegralHistory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int withdrawalIntegral(PlatformUserIntegralReqDTO reqDTO) {
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getPhone()), "用户信息为空，不允许发起提现申请");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getIntegral()), "提现积分不能为空");
        Assert.isTrue(!ObjectUtils.isEmpty(reqDTO.getBatchNo()), "体现平台不允许为空");
        Assert.isTrue(reqDTO.getIntegral() % 100 == 0, "积分提现需100整数");
        // 查询用户信息
        PlatformUserDetail platformUser = platformUserDetailService.selectPlatformUserDetailByDataId(reqDTO.getPhone());
        Assert.isTrue(!ObjectUtils.isEmpty(platformUser), "非法请求");
        // 查询用户是否有积分提现
        PlatformUserIntegral params = new PlatformUserIntegral();
        params.setBatchNo(reqDTO.getBatchNo());
        params.setPuUserId(platformUser.getId());
        List<PlatformUserIntegral> platformUserIntegrals = platformUserIntegralMapper.selectPlatformUserIntegralList(params);
        Assert.isTrue(!ObjectUtils.isEmpty(platformUserIntegrals) && !platformUserIntegrals.isEmpty(), "积分不足，不允许提现");
        PlatformUserIntegral platformUserIntegral = platformUserIntegrals.get(0);
        Assert.isTrue(platformUserIntegral.getResidualIntegral() - reqDTO.getIntegral() > 0, "积分不足，不允许提现");
        // 扣减用户积分处理
        platformUserIntegral.setOrgResidualIntegral(platformUserIntegral.getResidualIntegral());
        platformUserIntegral.setResidualIntegral(platformUserIntegral.getResidualIntegral() - reqDTO.getIntegral());
        int size = platformUserIntegralMapper.updateIntegralByResidualIntegralAndId(platformUserIntegral);
        int result = 0;
        if (size > 0) {
            // 插入明细记录
            PlatformUserIntegralHistory platformUserIntegralHistory = new PlatformUserIntegralHistory();
            platformUserIntegralHistory.setIntegral(reqDTO.getIntegral());
            platformUserIntegralHistory.setPuUserId(platformUser.getId());
            platformUserIntegralHistory.setType(IntegralTypeEnum.CASH_WITHDRAWAL.getCode());
            platformUserIntegralHistory.setBatchNo(reqDTO.getBatchNo());
            platformUserIntegralHistory.setRemark(reqDTO.getRemark());
            platformUserIntegralHistory.setEnabled(PlatformUserIntegralHistory.STATE_TYPE_DISABLE);
            result = platformUserIntegralHistoryService.insertPlatformUserIntegralHistory(platformUserIntegralHistory);
        }
        Assert.isTrue(result > 0, "积分提现失败，请刷新页面重新申请");
        return result;
    }
}
