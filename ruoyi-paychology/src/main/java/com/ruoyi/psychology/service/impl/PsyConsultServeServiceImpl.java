package com.ruoyi.psychology.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.mapper.PsyConsultServeMapper;
import com.ruoyi.psychology.request.PsyRefConsultServeReq;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.vo.PsyConsultServeVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PsyConsultServeServiceImpl implements IPsyConsultServeService {

    @Resource
    private PsyConsultServeMapper psyConsultServeMapper;

    @Override
    public List<PsyConsultServe> getList(PsyConsultServe req) {
        LambdaQueryWrapper<PsyConsultServe> wp = new LambdaQueryWrapper<>();
        wp.eq((req.getConsultId() != null && req.getConsultId() > 0), PsyConsultServe::getConsultId, req.getConsultId());
        wp.eq((req.getServeId() != null && req.getServeId() > 0), PsyConsultServe::getServeId, req.getServeId());
        wp.eq(PsyConsultServe::getDelFlag, "0");
        wp.eq(PsyConsultServe::getStatus, "0");
        return psyConsultServeMapper.selectList(wp);
    }

    @Override
    public PsyConsultServe getOne(PsyConsultServe req) {
        List<PsyConsultServe> serves = getList(req);
        return CollectionUtils.isNotEmpty(serves) ? serves.get(0) : null;
    }

    @Override
    public int getRefCountByConsultId(Long id){
        PsyConsultServe req = new PsyConsultServe();
        req.setConsultId(id);
        List<PsyConsultServe> serves = getList(req);
        return serves.size();
    }

    @Override
    public List<PsyConsultServeVO> getServeRef(PsyConsultServe req) {
        req.setDelFlag("0");
        req.setStatus("0");
        return psyConsultServeMapper.getServeRef(req);
    }

    @Override
    public List<PsyConsultServeVO> getConsultServeRef(PsyConsultServe req) {
        // 咨询师过滤
        req.setStatus("0");
        req.setDelFlag("0");
        return psyConsultServeMapper.getConsultServeRef(req);
    }

    @Override
    public int batchServeRef(PsyRefConsultServeReq req) {
        List<PsyConsultServe> refs = new ArrayList<>();
        req.getIds().forEach(a -> {
            PsyConsultServe ref = new PsyConsultServe();
            ref.setConsultId(req.getConsultId());
            ref.setServeId(a);
            ref.setNum(0);
            ref.setDelFlag("0");
            ref.setStatus("0");
            refs.add(ref);
        });

        return psyConsultServeMapper.batchServeRef(refs);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultServeMapper.tombstonedByIds(ids);
    }
}
