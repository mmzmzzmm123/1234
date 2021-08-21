package com.stdiet.custom.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.custom.domain.SysLiveSchedulFanRecord;
import com.stdiet.custom.mapper.SysLiveSchedulFanRecordMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysLiveSchedulMapper;
import com.stdiet.custom.domain.SysLiveSchedul;
import com.stdiet.custom.service.ISysLiveSchedulService;

/**
 * 直播排班Service业务层处理
 *
 * @author xzj
 * @date 2021-05-12
 */
@Service
public class SysLiveSchedulServiceImpl implements ISysLiveSchedulService
{
    @Autowired
    private SysLiveSchedulMapper sysLiveSchedulMapper;

    @Autowired
    private SysLiveSchedulFanRecordMapper sysLiveSchedulFanRecordMapper;

    /**
     * 查询直播排班
     *
     * @param id 直播排班ID
     * @return 直播排班
     */
    @Override
    public SysLiveSchedul selectSysLiveSchedulById(Long id)
    {
        return sysLiveSchedulMapper.selectSysLiveSchedulById(id);
    }

    /**
     * 查询直播排班列表
     *
     * @param sysLiveSchedul 直播排班
     * @return 直播排班
     */
    @Override
    public List<SysLiveSchedul> selectSysLiveSchedulList(SysLiveSchedul sysLiveSchedul)
    {
        return sysLiveSchedulMapper.selectSysLiveSchedulList(sysLiveSchedul);
    }

    /**
     * 新增直播排班
     *
     * @param sysLiveSchedul 直播排班
     * @return 结果
     */
    @Override
    public int insertSysLiveSchedul(SysLiveSchedul sysLiveSchedul)
    {
        sysLiveSchedul.setCreateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.insertSysLiveSchedul(sysLiveSchedul);
    }

    /**
     * 修改直播排班
     *
     * @param sysLiveSchedul 直播排班
     * @return 结果
     */
    @Override
    public int updateSysLiveSchedul(SysLiveSchedul sysLiveSchedul)
    {
        sysLiveSchedul.setUpdateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.updateSysLiveSchedul(sysLiveSchedul);
    }

    /**
     * 批量删除直播排班
     *
     * @param ids 需要删除的直播排班ID
     * @return 结果
     */
    @Override
    public int deleteSysLiveSchedulByIds(Long[] ids)
    {
        return sysLiveSchedulMapper.deleteSysLiveSchedulByIds(ids);
    }

    /**
     * 删除直播排班信息
     *
     * @param id 直播排班ID
     * @return 结果
     */
    @Override
    public int deleteSysLiveSchedulById(Long id)
    {
        return sysLiveSchedulMapper.deleteSysLiveSchedulById(id);
    }

    /**
     * 根据直播时间查询是否重叠
     */
    @Override
    public SysLiveSchedul getLiveSchedulByLiveTime(SysLiveSchedul sysLiveSchedul){
        return sysLiveSchedulMapper.getLiveSchedulByLiveTime(sysLiveSchedul);
    }

    /**
     * 更新部分字段
     * @param sysLiveSchedul
     * @return
     */
    @Override
    public int updateSysLiveSchedulById(SysLiveSchedul sysLiveSchedul){
        sysLiveSchedul.setUpdateTime(DateUtils.getNowDate());
        return sysLiveSchedulMapper.updateSysLiveSchedulById(sysLiveSchedul);
    }

    /**
     * 根据ID查询上一条记录
     * @param sysLiveSchedul
     * @return
     */
    @Override
    public SysLiveSchedul getLastLiveSchedulById(SysLiveSchedul sysLiveSchedul){
        return sysLiveSchedulMapper.getLastLiveSchedulById(sysLiveSchedul);
    }

    /**
     * 复制上一次直播间计划，并将上次的直播计划状态全部改为未开播
     * @return
     */
    @Override
    public AjaxResult copyLastTimeLiveSchedul(){
        AjaxResult result = AjaxResult.success();
        //获取上一次直播时间
        SysLiveSchedul lastLiveSchedul = sysLiveSchedulMapper.getLastLiveSchedul();
        if(lastLiveSchedul == null){
            return AjaxResult.error("不存在直播记录，无法复制");
        }
        Date nowDate = new Date();
        LocalDate nowLocalDate = DateUtils.dateToLocalDate(nowDate);
        if(DateUtils.isSameDay(lastLiveSchedul.getLiveSchedulDate(), nowDate)){
            return AjaxResult.error("今日已存在直播记录，无法复制");
        }
        //获取上一次所有计划
        List<SysLiveSchedul> list = sysLiveSchedulMapper.getAllLiveSchedulByDate(lastLiveSchedul);
        if(list != null && list.size() > 0){
            int row = 0;
            for (SysLiveSchedul schedul : list) {
                schedul.setLiveStatus(0L);
                schedul.setCreateTime(new Date());
                schedul.setUpdateTime(null);
                schedul.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId()+"");
                schedul.setUpdateBy(null);
                schedul.setLiveSchedulDate(DateUtils.dateToDate(nowDate));
                if(schedul.getLiveStartTime() != null) {
                    LocalDateTime liveStartTime = DateUtils.dateToLocalDateTime(schedul.getLiveStartTime());
                    schedul.setLiveStartTime(DateUtils.localDateTimeToDate(LocalDateTime.of(nowLocalDate.getYear(),nowLocalDate.getMonthValue(),nowLocalDate.getDayOfMonth(),
                            liveStartTime.getHour(),liveStartTime.getMinute())));
                    System.out.println(liveStartTime.getHour() + "-" +liveStartTime.getMinute());
                }
                if(schedul.getLiveEndTime() != null) {
                    LocalDateTime liveEndTime = DateUtils.dateToLocalDateTime(schedul.getLiveEndTime());
                    schedul.setLiveEndTime(DateUtils.localDateTimeToDate(LocalDateTime.of(nowLocalDate.getYear(),nowLocalDate.getMonthValue(),nowLocalDate.getDayOfMonth(),
                            liveEndTime.getHour(),liveEndTime.getMinute())));
                    System.out.println(liveEndTime.getHour() + "-" +liveEndTime.getMinute());
                }

                row = sysLiveSchedulMapper.insertSysLiveSchedul(schedul);
            }
        }else{
            return AjaxResult.error("不存在直播记录，无法复制");
        }
        return result;
    }

    /**
     * 根据时间确定最近的直播记录
     * @return
     */
    public SysLiveSchedul getLiveSchedulByTime(SysLiveSchedul sysLiveSchedul){
        return sysLiveSchedulMapper.getLiveSchedulByTime(sysLiveSchedul);
    }

    /**
     * 给对应直播添加进粉记录
     * @return
     */
    public int addLiveSchedulFanRecord(SysLiveSchedulFanRecord sysLiveSchedulFanRecord){
        return sysLiveSchedulFanRecordMapper.insertSysLiveSchedulFanRecord(sysLiveSchedulFanRecord);
    }

    /**
     * 给对应直播修改进粉记录
     * @return
     */
    public int updateLiveSchedulFanRecord(SysLiveSchedulFanRecord sysLiveSchedulFanRecord){
        return sysLiveSchedulFanRecordMapper.updateSysLiveSchedulFanRecord(sysLiveSchedulFanRecord);
    }

    /**
     * 给对应直播删除进粉记录
     * @param id
     * @return
     */
    public int delLiveSchedulFanRecord(Long id){
        return sysLiveSchedulFanRecordMapper.deleteSysLiveSchedulFanRecordById(id);
    }
}