package com.ruoyi.contest.service.impl;

import java.util.Arrays;
import java.util.List;

import com.ruoyi.contest.domain.AwardPerson;
import com.ruoyi.contest.mapper.AwardPersonMapper;
import com.ruoyi.contest.service.IAwardPersonService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;

/**
 * 参赛人员信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-02-14
 */
@Service
public class AwardPersonServiceImpl extends ServiceImpl<AwardPersonMapper, AwardPerson> implements IAwardPersonService
{
    /**
     * 查询参赛人员信息列表
     *
     * @param awardPerson 参赛人员信息
     * @return 参赛人员信息
     */
    @Override
    public List<AwardPerson> selectAwardPersonList(AwardPerson awardPerson)
    {
        return baseMapper.selectAwardPersonList(awardPerson);
    }

}
