package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;

/**
 *
 */
public interface PlayMessageConfoundLogService extends IService<PlayMessageConfoundLog> {

    Page<QueryConfoundLogVO> page(QueryConfoundLogDTO dto);

    void retry(ConfoundRetryDTO dto);

}
