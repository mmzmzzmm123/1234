package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.domain.dto.play.PlayDTO;

public interface IPlayService {
    R<String>  create(PlayDTO dto);

    R<String> update(PlayDTO dto);

    R<PlayDTO> info(String id);
}
