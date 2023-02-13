package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.domain.Award;

import java.util.List;

/**
 * <p>
 * 获奖登记表 Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2023-02-13
 */
public interface AwardMapper extends BaseMapper<Award> {
    /**
     * 查询获奖登记列表
     *
     * @param award 获奖登记
     * @return 获奖登记集合
     */
    public List<Award> selectAwardList(Award award);

}
