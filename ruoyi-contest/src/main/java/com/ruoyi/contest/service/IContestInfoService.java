package com.ruoyi.contest.service;

import java.util.List;

import com.ruoyi.contest.domain.ContestInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 竞赛基础信息Service接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface IContestInfoService extends IService<ContestInfo>
{
    /**
     * 查询竞赛基础信息列表
     *
     * @param contestInfo 竞赛基础信息
     * @return 竞赛基础信息集合
     */
    public List<ContestInfo> selectContestInfoList(ContestInfo contestInfo);
}
