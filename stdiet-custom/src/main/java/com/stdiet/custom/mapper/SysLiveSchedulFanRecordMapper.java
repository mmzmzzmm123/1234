package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysLiveSchedulFanRecord;

/**
 * 直播记录中每场直播对应进粉记录Mapper接口
 *
 * @author xzj
 * @date 2021-08-19
 */
public interface SysLiveSchedulFanRecordMapper
{
    /**
     * 查询直播记录中每场直播对应进粉记录
     *
     * @param id 直播记录中每场直播对应进粉记录ID
     * @return 直播记录中每场直播对应进粉记录
     */
    public SysLiveSchedulFanRecord selectSysLiveSchedulFanRecordById(Long id);

    /**
     * 查询直播记录中每场直播对应进粉记录列表
     *
     * @param sysLiveSchedulFanRecord 直播记录中每场直播对应进粉记录
     * @return 直播记录中每场直播对应进粉记录集合
     */
    public List<SysLiveSchedulFanRecord> selectSysLiveSchedulFanRecordList(SysLiveSchedulFanRecord sysLiveSchedulFanRecord);

    /**
     * 新增直播记录中每场直播对应进粉记录
     *
     * @param sysLiveSchedulFanRecord 直播记录中每场直播对应进粉记录
     * @return 结果
     */
    public int insertSysLiveSchedulFanRecord(SysLiveSchedulFanRecord sysLiveSchedulFanRecord);

    /**
     * 修改直播记录中每场直播对应进粉记录
     *
     * @param sysLiveSchedulFanRecord 直播记录中每场直播对应进粉记录
     * @return 结果
     */
    public int updateSysLiveSchedulFanRecord(SysLiveSchedulFanRecord sysLiveSchedulFanRecord);

    /**
     * 删除直播记录中每场直播对应进粉记录
     *
     * @param id 直播记录中每场直播对应进粉记录ID
     * @return 结果
     */
    public int deleteSysLiveSchedulFanRecordById(Long id);

    /**
     * 批量删除直播记录中每场直播对应进粉记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysLiveSchedulFanRecordByIds(Long[] ids);
}