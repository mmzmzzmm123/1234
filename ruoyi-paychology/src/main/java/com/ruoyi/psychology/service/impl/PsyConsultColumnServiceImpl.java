package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.psychology.domain.PsyConsultColumn;
import com.ruoyi.psychology.mapper.PsyConsultColumnMapper;
import com.ruoyi.psychology.service.IPsyConsultColumnService;
import com.ruoyi.psychology.vo.PsyConsultColumnVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询专栏Service业务层处理
 *
 * @author ruoyi
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
