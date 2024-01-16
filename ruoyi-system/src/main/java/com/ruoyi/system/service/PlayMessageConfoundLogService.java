package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;

import java.util.List;

/**
 *
 */
public interface PlayMessageConfoundLogService extends IService<PlayMessageConfoundLog> {

    void removeByConfoundIds(List<Integer> confoundIds);

    void createConfoundLog(List<PlayMessageConfound> confoundList);

    void handleConfoundText(CalledDTO inputDTO);

    void handleConfoundImg(CalledDTO inputDTO);

    void retryingConfusion(String lockKey);

    Page<QueryConfoundLogVO> page(QueryConfoundLogDTO dto);

    void retry(ConfoundRetryDTO dto);

}
