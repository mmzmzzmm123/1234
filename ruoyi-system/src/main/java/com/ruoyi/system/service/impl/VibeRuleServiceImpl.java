package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.VibeRule;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.core.domain.dto.play.VibeRuleDTO;
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
    public R<VibeRuleDTO> info(Integer id) {
        VibeRule vibeRule = vibeRuleMapper.selectById(id);
        if (null == vibeRule) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "配置不存在"));
        }

        VibeRuleDTO ret = new VibeRuleDTO();
        BeanUtils.copyProperties(vibeRule, ret);
        ret.setTargetParams(JSONArray.parseArray(vibeRule.getTargetParams(), VibeRuleDTO.TargetParam.class));
        return R.ok(ret);
    }
}
