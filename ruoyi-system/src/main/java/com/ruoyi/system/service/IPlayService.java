package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.system.domain.dto.play.QueryPlayDTO;
import com.ruoyi.system.domain.vo.play.PlayGroupProgressVO;
import com.ruoyi.system.domain.vo.play.PlayTaskProgressVO;
import com.ruoyi.system.domain.vo.play.QueryPlayVO;

public interface IPlayService {
    R<String>  create(PlayDTO dto);

    R<String> updatePlay(PlayDTO dto);

    R<PlayDTO> info(String id);

    /**
     * 炒群任务分页列表
     * @param dto
     * @return
     */
    Page<QueryPlayVO> page(QueryPlayDTO dto);

    /**
     * 任务进度
     * @param playId
     * @return
     */
    PlayTaskProgressVO taskProgress(String playId);

    /**
     * 炒群进度
     * @param playId
     * @return
     */
    PlayGroupProgressVO groupProgress(String playId);

}
