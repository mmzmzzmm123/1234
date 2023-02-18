package com.ruoyi.contest.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.Award;

/**
 * 获奖登记Mapper接口
 *
 * @author lsyonlygoddes
 * @date 2023-02-14
 */
public interface AwardMapper extends BaseMapper<Award>
{

    /**
     * 查询获奖登记列表
     *
     * @param award 获奖登记
     * @return 获奖登记集合
     */
    public List<Award> selectAwardList(Award award);

}
