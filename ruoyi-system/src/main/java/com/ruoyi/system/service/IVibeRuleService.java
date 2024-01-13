package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.VibeRuleDTO;

public interface IVibeRuleService {
    R<String> create(VibeRuleDTO dto);

    R<String> update(VibeRuleDTO dto);

    R<VibeRuleDTO> info(Integer id);
}
