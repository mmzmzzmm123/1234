package com.onethinker.bk.service;

import com.onethinker.bk.domain.HistoryInfo;

import java.util.List;
import java.util.Map;

/**
 * 历史信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IHistoryInfoService {

    /**
     * 新增历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    int insertHistoryInfo(HistoryInfo historyInfo);

    /**
     * 修改历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    int updateHistoryInfo(HistoryInfo historyInfo);

    /**
     * 获取统计记录
     *
     * @return
     */
    Map<String, Object> getHistoryInfo();
}
