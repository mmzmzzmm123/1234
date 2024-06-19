package com.renxin.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.renxin.psychology.domain.PsyConsultColumn;
import com.renxin.psychology.mapper.PsyConsultColumnMapper;
import com.renxin.psychology.service.IPsyConsultColumnService;
import com.renxin.psychology.vo.PsyConsultColumnVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询专栏Service业务层处理
 *
 * @author renxin
 * @date 2023-09-04
 */
@Service
public class PsyConsultColumnServiceImpl implements IPsyConsultColumnService
{

    @Resource
    private PsyConsultColumnMapper mapper;


    @Override
    public PsyConsultColumnVO getOne(Long id) {
        return mapper.getOne(id);
    }

    @Override
    public List<PsyConsultColumnVO> getList(PsyConsultColumnVO req) {
        return mapper.getList(req);
    }

    @Override
    public int add(PsyConsultColumnVO req) {
        return mapper.insert(BeanUtil.toBean(req, PsyConsultColumn.class));
    }

    @Override
    public int update(PsyConsultColumnVO req) {
        return mapper.updateById(BeanUtil.toBean(req, PsyConsultColumn.class));
    }

    @Override
    public int delete(Long id) {
        return mapper.deleteById(id);
    }
}
