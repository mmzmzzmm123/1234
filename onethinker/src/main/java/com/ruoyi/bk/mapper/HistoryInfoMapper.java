package com.ruoyi.bk.mapper;

import java.util.List;
import com.ruoyi.bk.domain.HistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 历史信息Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface HistoryInfoMapper extends BaseMapper<HistoryInfo>{
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
     * 删除历史信息
     *
     * @param id 历史信息主键
     * @return 结果
     */
    public int deleteHistoryInfoById(Long id);

    /**
     * 批量删除历史信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteHistoryInfoByIds(Long[] ids);
}
