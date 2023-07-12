package com.ruoyi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.IntegralRecordConstants;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import com.ruoyi.user.mapper.PsyUserIntegralRecordMapper;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 用户积分记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-10
 */
@Service
public class PsyUserIntegralRecordServiceImpl extends ServiceImpl<PsyUserIntegralRecordMapper, PsyUserIntegralRecord> implements IPsyUserIntegralRecordService
{

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private PsyUserIntegralRecordMapper psyUserIntegralRecordMapper;

    @Override
    public int getIntegral(BigDecimal amount, Integer type) {
        int integral = 0;
        String give = configService.selectConfigByKey("integral.give");
        // 未开启功能,返回0
        if (StringUtils.isEmpty(give) || "2".equals(give)) {
            return integral;
        }

        // 注册送积分
        if (IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_REGISTER.equals(type)) {
            String register = configService.selectConfigByKey("integral.give.register");
            integral = Math.min(Integer.parseInt(register), 500);
            return integral;
        }

        // 支付送积分
        String order = configService.selectConfigByKey("integral.give.order");
        if (StringUtils.isEmpty(order)) {
            return integral;
        }

        BigDecimal config = new BigDecimal(order);
        BigDecimal rate = new BigDecimal(Constants.INTEGRAL_RATE_ORDER_GIVE);
        rate = config.compareTo(rate) >= 0 ? rate : config;

        integral = rate.multiply(amount).setScale(0, BigDecimal.ROUND_DOWN).intValue();
        return integral;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(PsyUserIntegralRecord data) {
        System.out.println("PsyUserIntegralRecord");
        if (data.getIntegral() > 0 ) {
            PsyUser psyUser = psyUserService.selectPsyUserById(data.getUid());
            Integer integral = psyUser.getIntegral();
            integral = IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD.equals(data.getType()) ? integral + data.getIntegral() : integral - data.getIntegral();
            integral = Math.max(integral, 0);
            psyUser.setIntegral(integral);

            psyUserService.updatePsyUser(psyUser);
            psyUserIntegralRecordMapper.insert(data);
        }
    }











}
