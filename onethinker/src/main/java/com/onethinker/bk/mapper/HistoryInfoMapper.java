package com.onethinker.bk.mapper;

import java.util.List;
import java.util.Map;

import com.onethinker.bk.domain.HistoryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 访问IP最多的10个省
     */
    @Select("select nation, province, count(distinct ip) as num" +
            " from bk_history_info" +
            " where nation is not null and province is not null" +
            " group by nation, province" +
            " order by num desc" +
            " limit 10")
    List<Map<String, Object>> getHistoryByProvince();

    /**
     * 访问次数最多的10个IP
     */
    @Select("select ip, count(*) as num" +
            " from bk_history_info" +
            " group by ip" +
            " order by num desc" +
            " limit 10")
    List<Map<String, Object>> getHistoryByIp();

    /**
     * 访问24小时内的数据
     */
    @Select("select ip, user_id, nation, province" +
            " from bk_history_info" +
            " where create_time >= (now() - interval 24 hour)")
    List<Map<String, Object>> getHistoryBy24Hour();

    /**
     * 总访问量
     */
    @Select("select count(*) from bk_history_info")
    Long getHistoryCount();
}
