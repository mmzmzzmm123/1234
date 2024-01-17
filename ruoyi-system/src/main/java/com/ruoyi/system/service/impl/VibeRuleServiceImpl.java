package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.mapper.VibeRuleMapper;
import com.ruoyi.system.service.IVibeRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VibeRuleServiceImpl implements IVibeRuleService {
    @Resource
    private VibeRuleMapper vibeRuleMapper;

    @Override
    public R<String> create(VibeRuleDTO dto) {
        VibeRule vibeRule = new VibeRule();
        BeanUtils.copyProperties(dto, vibeRule);
        vibeRule.setTargetParams(JSON.toJSONString(dto.getTargetParams()));
        vibeRuleMapper.insert(vibeRule);
        return R.ok();
    }

    @Override
    public R<String> update(VibeRuleDTO dto) {
        //todo 验证权限?

        VibeRule vibeRule = vibeRuleMapper.selectById(dto.getId());
        if (null == vibeRule) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "配置不存在"));
        }

        BeanUtils.copyProperties(dto, vibeRule);
        vibeRule.setTargetParams(JSON.toJSONString(dto.getTargetParams()));
        vibeRuleMapper.updateById(vibeRule);
        return R.ok();
    }

    @Override
    public VibeRuleDTO getOne() {
        VibeRule vibeRule = vibeRuleMapper.selectOne(new QueryWrapper<VibeRule>().lambda()
                        .eq(VibeRule::getStatus, 1).orderByDesc(VibeRule::getId).last(" limit  1 "));
        if (null == vibeRule) {
            return null;
        }

        VibeRuleDTO ret = new VibeRuleDTO();
        BeanUtils.copyProperties(vibeRule, ret);
        ret.setTargetParams(vibeRule.covertTargetParams());
        return ret;
    }
}
