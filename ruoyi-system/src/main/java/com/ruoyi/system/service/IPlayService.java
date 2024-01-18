package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.AdMonitor;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.system.domain.dto.play.*;
import com.ruoyi.system.domain.vo.play.*;

import java.util.List;

public interface IPlayService extends IService<Play> {
    R<String> create(PlayDTO dto);

    R<String> updatePlay(PlayDTO dto);

    R<PlayVO> info(String playId);

    AdMonitor adInfo(String playId);

    R<String> updateAdInfo(String playId, AdMonitor id);

    /**
     * 炒群任务分页列表
     *
     * @param dto
     * @return
     */
    Page<QueryPlayVO> page(QueryPlayDTO dto);

    /**
     * 任务进度
     *
     * @param dto
     * @return
     */
    List<PlayTaskProgressVO> taskProgress(QueryTaskProgressDTO dto);

    /**
     * 炒群进度
     *
     * @param playId
     * @return
     */
    List<PlayGroupProgressVO> groupProgress(String playId);

    /**
     * 任务推送明细查询
     *
     * @param dto
     * @return
     */
    Page<QueryPushDetailVO> pushDetailPage(QueryPushDetailDTO dto);


    void setSpeed(SetSpeedDTO dto);

    /**
     * 账号明细统计
     * @param playId
     * @return
     */
    RobotStatisticsVO robotStatistics(String playId);

    /**
     * 设置重复炒群
     * @return
     */
    R<String> repeatPlay(String playId, LoginUser loginUser);

    /**
     * 修改炒群任务状态
     */
    Boolean updateState(PlayStateDTO dto);

    /**
     * 释放水军
     */
    R<String> releaseUser(String playId);

    void jobReleaseRobot();
}
