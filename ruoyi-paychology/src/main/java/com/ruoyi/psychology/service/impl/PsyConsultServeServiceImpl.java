package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.psychology.domain.PsyConsultServe;
import com.ruoyi.psychology.mapper.PsyConsultServeMapper;
import com.ruoyi.psychology.service.IPsyConsultServeService;
import com.ruoyi.psychology.vo.PsyConsultServeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PsyConsultServeServiceImpl extends ServiceImpl<PsyConsultServeMapper, PsyConsultServe> implements IPsyConsultServeService {

    @Resource
    private PsyConsultServeMapper psyConsultServeMapper;

    @Override
    public PsyConsultServeVO getOne(Long id) {
        return BeanUtil.toBean(psyConsultServeMapper.selectById(id), PsyConsultServeVO.class);
    }

    @Override
    public List<PsyConsultServe> getList(PsyConsultServeVO req) {
        req.setDelFlag("0");
        return psyConsultServeMapper.getList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(List<PsyConsultServe> entities) {
        return this.saveBatch(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatch(List<PsyConsultServe> entities) {
        return this.updateBatchById(entities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultServeVO req) {
        return psyConsultServeMapper.insert(BeanUtil.toBean(req, PsyConsultServe.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultServeVO req) {
        return psyConsultServeMapper.updateById(BeanUtil.toBean(req, PsyConsultServe.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultServeMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultServeMapper.deleteById(id);
    }
}
