package com.ruoyi.psychology.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultOrderServe;
import com.ruoyi.psychology.mapper.PsyConsultOrderServeMapper;
import com.ruoyi.psychology.service.IPsyConsultOrderServeService;
import com.ruoyi.psychology.vo.PsyConsultServeConfigVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 咨询订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Service
public class PsyConsultOrderServeServiceImpl implements IPsyConsultOrderServeService
{

    @Resource
    private PsyConsultOrderServeMapper psyConsultOrderServeMapper;

    @Override
    public void add(PsyConsultServeConfigVO serve, Long orderId) {
        PsyConsultOrderServe orderServe = new PsyConsultOrderServe();
        orderServe.setOrderId(orderId);
        orderServe.setServeId(serve.getId());
        orderServe.setBound(serve.getBound());
        orderServe.setBoundName(ConsultConstant.CONSULT_NO_LIMIT.equals(serve.getBound()) ? "不限时" : "限时");
        orderServe.setMode(serve.getMode());
        orderServe.setModeName(ConsultConstant.CONSULT_MODE_SOUND.equals(serve.getMode()) ? "语音咨询"  : ConsultConstant.CONSULT_MODE_VOICE.equals(serve.getMode()) ? "视频咨询" : "面对面咨询");
        orderServe.setType(serve.getType());
        orderServe.setTypeName(ConsultConstant.CONSULT_TYPE_ONCE.equals(serve.getType()) ? "单次咨询" : "套餐咨询");
        orderServe.setTime(serve.getTime());
        orderServe.setName(serve.getName());
        orderServe.setInfo(serve.getInfo());
        orderServe.setPrice(serve.getPrice());
        orderServe.setNum(serve.getNum());

        if (ConsultConstant.CONSULT_LIMIT.equals(serve.getBound()) && serve.getEnd() > 0) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, serve.getEnd());
            orderServe.setEnd(calendar.getTime());
        }

        psyConsultOrderServeMapper.insert(orderServe);
    }

    @Override
    public PsyConsultOrderServe getOneByOrder(Long orderId, Long serveId) {
        LambdaQueryWrapper<PsyConsultOrderServe> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultOrderServe::getOrderId, orderId);
        wp.eq(PsyConsultOrderServe::getServeId, serveId);
        return psyConsultOrderServeMapper.selectOne(wp);
    }

}
