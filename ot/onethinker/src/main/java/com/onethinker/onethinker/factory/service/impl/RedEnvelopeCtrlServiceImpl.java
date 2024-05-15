package com.onethinker.onethinker.factory.service.impl;

import com.onethinker.onethinker.domain.RedEnvelopeCtrl;
import com.onethinker.onethinker.dto.ActivityReqDTO;
import com.onethinker.onethinker.dto.RedEnvelopeCtrlDTO;
import com.onethinker.onethinker.factory.service.IActivityDetailService;
import com.onethinker.onethinker.mapper.RedEnvelopeCtrlMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        // 校验入参明细是否有效
        activityReqDTO.getRedEnvelopeCtrlDTO().existsReqParams();
        // 入批控表
        RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = activityReqDTO.getRedEnvelopeCtrlDTO();
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(redEnvelopeCtrlDTO, redEnvelopeCtrl);
        redEnvelopeCtrl.setCreateTime(new Date());
        redEnvelopeCtrl.setWeight(System.currentTimeMillis());
        int i = redEnvelopeCtrlMapper.insertRedEnvelopeCtrl(redEnvelopeCtrl);
        return i;
    }

    @Override
    public List<RedEnvelopeCtrlDTO> queryRedEnvelopeCtrlByParams(Object reqDTO) {
        RedEnvelopeCtrl redEnvelopeCtrl = new RedEnvelopeCtrl();
        BeanUtils.copyProperties(reqDTO, redEnvelopeCtrl);
        List<RedEnvelopeCtrl> redEnvelopeCtrls = redEnvelopeCtrlMapper.selectRedEnvelopeCtrlList(redEnvelopeCtrl);
        if (ObjectUtils.isEmpty(redEnvelopeCtrls) || redEnvelopeCtrls.isEmpty()) {
            return Lists.newArrayList();
        }
        return redEnvelopeCtrls.stream().map(e -> {
            RedEnvelopeCtrlDTO redEnvelopeCtrlDTO = new RedEnvelopeCtrlDTO();
            BeanUtils.copyProperties(e, redEnvelopeCtrlDTO);
            return redEnvelopeCtrlDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public int updateEntry(Object doTemp, Integer createQrCodeStatus) {
        RedEnvelopeCtrlDTO redEnvelopeCtrl = (RedEnvelopeCtrlDTO) doTemp;
        redEnvelopeCtrl.setOrgStatus(redEnvelopeCtrl.getStatus());
        redEnvelopeCtrl.setStatus(createQrCodeStatus);
        return redEnvelopeCtrlMapper.updateRedEnvelopeCtrlAndStatus(redEnvelopeCtrl);
    }
}
