package com.ruoyi.contest.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.ContestInfo;

/**
 * 竞赛基础信息Mapper接口
 *
 * @author ruoyi
 * @date 2023-02-14
 */
public interface ContestInfoMapper extends BaseMapper<ContestInfo>
{

    /**
     * 查询竞赛基础信息列表
     *
     * @param contestInfo 竞赛基础信息
     * @return 竞赛基础信息集合
     */
    public List<ContestInfo> selectContestInfoList(ContestInfo contestInfo);

}
