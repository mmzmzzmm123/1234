package com.ruoyi.onethinker.factory.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ruoyi.onethinker.domain.RedEnvelopeCtrl;
import com.ruoyi.onethinker.dto.ActivityReqDTO;
import com.ruoyi.onethinker.dto.RedEnvelopeCtrlDTO;
import com.ruoyi.onethinker.factory.service.IActivityDetailService;
import com.ruoyi.onethinker.mapper.RedEnvelopeCtrlMapper;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.log4j.Log4j2;

/**
 * 红包批控制Service业务层处理
 *
 * @author yangyouqi
 * @date 2023-10-31
 */
@Service
@Log4j2
public class RedEnvelopeCtrlServiceImpl implements IActivityDetailService {
    @Resource
    private RedEnvelopeCtrlMapper redEnvelopeCtrlMapper;

    @Override
    public int saveEntry(ActivityReqDTO activityReqDTO) {
        Assert.isTrue(ObjectUtils.isEmpty(activityReqDTO.getRedEnvelopeCtrlDTO()),"生成红包必填数据不能为空");
        RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = activityReqDTO.getRedEnvelopeCtrlDTO();
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(redEnvelopeCtrlDTO,redEnvelopeCtrl);
        int i = redEnvelopeCtrlMapper.insertRedEnvelopeCtrl(redEnvelopeCtrl);
        return i;
    }
}
