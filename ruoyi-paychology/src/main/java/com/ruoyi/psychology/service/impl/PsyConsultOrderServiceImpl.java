package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.mapper.PsyConsultOrderMapper;
import com.ruoyi.psychology.service.*;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import com.ruoyi.psychology.vo.PsyConsultServeVO;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.psychology.vo.PsyConsultWorkVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询订单Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-26
 */
@Service
public class PsyConsultOrderServiceImpl implements IPsyConsultOrderService 
{
    @Resource
    private PsyConsultOrderMapper psyConsultOrderMapper;

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private IPsyConsultService psyConsultService;

    @Resource
    private IPsyConsultServeService psyConsultServeService;

    @Resource
    private IPsyConsultWorkService psyConsultWorkService;

    @Override
    public PsyConsultOrderVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultOrderMapper.selectById(id), PsyConsultOrderVO.class);
    }

    @Override
    public PsyConsultOrderVO getOneByOrderId(String orderNo) {
        LambdaQueryWrapper<PsyConsultOrder> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultOrder::getOrderNo, orderNo);
        PsyConsultOrder consultOrder = psyConsultOrderMapper.selectOne(wp);
        return BeanUtil.toBean(consultOrder, PsyConsultOrderVO.class);
    }

    @Override
    public List<PsyConsultOrder> getList(PsyConsultOrderVO req) {
        req.setDelFlag("0");
        return psyConsultOrderMapper.getList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultOrderVO req) {
        PsyConsultServeVO serve = psyConsultServeService.getOne(req.getServeId());
        PsyConsultVO consult = psyConsultService.getOne(serve.getConsultId());
        PsyUser psyUser = psyUserService.selectPsyUserById(req.getUserId().intValue());

        req.setConsultId(serve.getConsultId());
        req.setConsultName(consult.getUserName());
        req.setServeName(serve.getName());
        req.setUserName(psyUser.getName());
        req.setDelFlag("0");
        req.setNum(1);
        req.setBuyNum(0);
        req.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_CREATED);

        if (req.getWorkId() > 0) {
            PsyConsultWorkVO work = psyConsultWorkService.getOne(req.getWorkId());
            req.setType(work.getType());
            req.setDay(work.getDay());
            req.setWeek(work.getWeek());
            req.setTimeStart(work.getTimeStart());
            req.setTimeEnd(work.getTimeEnd());
            req.setTime(work.getTime());

            // 更新预约数量
            psyConsultWorkService.updateNum(req.getWorkId(), -1);
        }

        // 增加预约人数
        psyConsultService.updateNum(consult.getId(), 1);

        return psyConsultOrderMapper.insert(BeanUtil.toBean(req, PsyConsultOrder.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultOrderVO req) {
        return psyConsultOrderMapper.updateById(BeanUtil.toBean(req, PsyConsultOrder.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePsyOrder(PsyConsultOrderVO req) {
        // 咨询人数
        psyConsultService.updateNum(req.getConsultId(), -1);
        // 排班释放
        psyConsultWorkService.updateNum(req.getWorkId(), -1);
        // 订单状态变更
        update(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultOrderMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultOrderMapper.deleteById(id);
    }
}
