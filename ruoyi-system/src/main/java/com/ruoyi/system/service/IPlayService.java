package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.system.domain.dto.play.QueryPlayDTO;
import com.ruoyi.system.domain.vo.play.QueryPlayVO;

public interface IPlayService {
    R<String>  create(PlayDTO dto);

    R<String> update(PlayDTO dto);

    R<PlayDTO> info(String id);

    Page<QueryPlayVO> page(QueryPlayDTO dto);

}
