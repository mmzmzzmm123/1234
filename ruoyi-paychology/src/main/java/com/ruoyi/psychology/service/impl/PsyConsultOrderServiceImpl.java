package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.psychology.domain.PsyConsultOrder;
import com.ruoyi.psychology.mapper.PsyConsultOrderMapper;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
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

    @Override
    public PsyConsultOrderVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultOrderMapper.selectById(id), PsyConsultOrderVO.class);
    }

    @Override
    public List<PsyConsultOrder> getList(PsyConsultOrderVO req) {
        req.setDelFlag("0");
        return psyConsultOrderMapper.getList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultOrderVO req) {
        return psyConsultOrderMapper.insert(BeanUtil.toBean(req, PsyConsultOrder.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultOrderVO req) {
        return psyConsultOrderMapper.updateById(BeanUtil.toBean(req, PsyConsultOrder.class));
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
