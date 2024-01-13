package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.VibeRuleDTO;
import com.ruoyi.system.service.IVibeRuleService;
import org.springframework.stereotype.Service;

@Service
public class VibeRuleServiceImpl implements IVibeRuleService {
    @Override
    public R<String> create(VibeRuleDTO dto) {
        return R.ok();
    }

    @Override
    public R<String> update(VibeRuleDTO dto) {
        return R.ok();
    }

    @Override
    public R<VibeRuleDTO> info(Integer id) {
        VibeRuleDTO ret = new VibeRuleDTO();
        ret.setId(1);
        ret.setGroupNum(1);
        return R.ok(ret);
    }
}
