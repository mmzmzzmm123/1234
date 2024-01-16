package com.onethinker.bk.service;

import java.util.List;
import com.onethinker.bk.domain.HistoryInfo;

/**
 * 历史信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IHistoryInfoService {
    /**
     * 查询历史信息
     *
     * @param id 历史信息主键
     * @return 历史信息
     */
    public HistoryInfo selectHistoryInfoById(Long id);

    /**
     * 查询历史信息列表
     *
     * @param historyInfo 历史信息
     * @return 历史信息集合
     */
    public List<HistoryInfo> selectHistoryInfoList(HistoryInfo historyInfo);

    /**
     * 新增历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    public int insertHistoryInfo(HistoryInfo historyInfo);

    /**
     * 修改历史信息
     *
     * @param historyInfo 历史信息
     * @return 结果
     */
    public int updateHistoryInfo(HistoryInfo historyInfo);

    /**
     * 批量删除历史信息
     *
     * @param ids 需要删除的历史信息主键集合
     * @return 结果
     */
    public int deleteHistoryInfoByIds(Long[] ids);

    /**
     * 删除历史信息信息
     *
     * @param id 历史信息主键
     * @return 结果
     */
    public int deleteHistoryInfoById(Long id);
}
