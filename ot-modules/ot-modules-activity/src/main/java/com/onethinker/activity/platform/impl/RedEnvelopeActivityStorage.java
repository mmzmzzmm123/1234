package com.onethinker.activity.platform.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onethinker.activity.domain.RedEnvelopeCtrl;
import com.onethinker.activity.dto.ActivityReqDTO;
import com.onethinker.activity.dto.ActivityResDTO;
import com.onethinker.activity.dto.RedEnvelopeCtrlDTO;
import com.onethinker.activity.mapper.RedEnvelopeCtrlMapper;
import com.onethinker.activity.mapper.RedEnvelopeDtlMapper;
import com.onethinker.activity.platform.ActivityStorage;
import com.onethinker.common.enums.ActivityTypeEnum;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangyouqi
 * @date 2024/5/31
 * 扫码红包
 */
@Data
@Log4j2
public class RedEnvelopeActivityStorage implements ActivityStorage {

    private final ActivityTypeEnum activityType = ActivityTypeEnum.RED_ENVELOPE;

    private RedEnvelopeCtrlMapper redEnvelopeCtrlMapper;

    private RedEnvelopeDtlMapper redEnvelopeDtlMapper;

    public RedEnvelopeActivityStorage(ApplicationContext applicationContext) {
        redEnvelopeCtrlMapper = applicationContext.getBean(RedEnvelopeCtrlMapper.class);
        redEnvelopeDtlMapper = applicationContext.getBean(RedEnvelopeDtlMapper.class);
    }

    @Override
    public void saveActivitCtr(ActivityReqDTO activityReqDTO) {
        // 入批控表
        RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = activityReqDTO.getRedEnvelopeCtrlDTO();
        // 校验入参明细是否有效
        redEnvelopeCtrlDTO.existsReqParams();

        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(redEnvelopeCtrlDTO, redEnvelopeCtrl);
        redEnvelopeCtrl.setCreateTime(new Date());
        redEnvelopeCtrl.setWeight(System.currentTimeMillis());
        redEnvelopeCtrlMapper.insertRedEnvelopeCtrl(redEnvelopeCtrl);
    }

    @Override
    public void updateEntry(ActivityReqDTO activity) {
        RedEnvelopeCtrlDTO redEnvelopeCtrl = (RedEnvelopeCtrlDTO) activity.getRedEnvelopeCtrlDTO();
        Assert.notNull(redEnvelopeCtrl, "redEnvelopeCtrl is null");
        redEnvelopeCtrlMapper.updateRedEnvelopeCtrlAndStatus(redEnvelopeCtrl);
    }

    @Override
    public void updateEntry(Object reqDTO, Integer status) {
        RedEnvelopeCtrlDTO redEnvelopeCtrl = (RedEnvelopeCtrlDTO) reqDTO;
        redEnvelopeCtrl.setOrgStatus(redEnvelopeCtrl.getStatus());
        redEnvelopeCtrl.setStatus(status);
        int i = redEnvelopeCtrlMapper.updateRedEnvelopeCtrlAndStatus(redEnvelopeCtrl);
        Assert.isTrue(i != 0,"update error");
    }

    @Override
    public void getActivityInfo(ActivityResDTO reqDTO) {
        LambdaQueryWrapper<RedEnvelopeCtrl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RedEnvelopeCtrl::getBatchNo, reqDTO.getBatchNo());
        List<RedEnvelopeCtrl> redEnvelopes = redEnvelopeCtrlMapper.selectList(queryWrapper);
        if (ObjectUtils.isEmpty(redEnvelopes) || redEnvelopes.isEmpty()) {
            reqDTO.setRedEnvelopes(Lists.newArrayList());
        } else {
            reqDTO.setRedEnvelopes(redEnvelopes.parallelStream().map(e -> {
                RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = new RedEnvelopeCtrlDTO();
                BeanUtils.copyProperties(e, redEnvelopeCtrlDTO);
                return redEnvelopeCtrlDTO;
            }).collect(Collectors.toList()));
        }
    }

    @Override
    public List<Map> queryCtrlEntryByParams(Object reqDTO) {
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(reqDTO, redEnvelopeCtrl);
        List<RedEnvelopeCtrl> redEnvelopeCtrlList = redEnvelopeCtrlMapper.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
        if (ObjectUtils.isEmpty(redEnvelopeCtrlList) || redEnvelopeCtrlList.isEmpty()) {
            return Lists.newArrayList();
        }
        return redEnvelopeCtrlList.stream().map(e -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(e,Map.class);
        }).collect(Collectors.toList());
    }
}
