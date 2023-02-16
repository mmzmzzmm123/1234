package com.ruoyi.contest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.contest.domain.Award;
import com.ruoyi.contest.domain.AwardPerson;
import com.ruoyi.contest.domain.vo.SaveAwardVo;
import com.ruoyi.contest.mapper.AwardMapper;
import com.ruoyi.contest.mapper.AwardPersonMapper;
import com.ruoyi.contest.service.IAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 获奖登记Service业务层处理
 *
 * @author lsyonlygoddes
 * @date 2023-02-14
 */
@Service
@RequiredArgsConstructor
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements IAwardService {

    private final AwardPersonMapper awardPersonMapper;

    /**
     * 查询获奖登记列表
     *
     * @param award 获奖登记
     * @return 获奖登记
     */
    @Override
    public List<Award> selectAwardList(Award award) {
        return baseMapper.selectAwardList(award);
    }

    /**
     * {
     * *             "id": 17116,
     * *             "name": "史启蒙",
     * *             "orderNum": 0,
     * *             "conDegree": 100,
     * *             "workContent": ""
     * *         },
     *
     * @param vo
     * @return
     */

    @Transactional
    @Override
    public int saveAward(SaveAwardVo vo) {
        Award award = new Award();
        BeanUtils.copyProperties(vo, award);
        award.setAttachmentUrl(vo.getAttachmentUrl());
        award.setCreateBy(SecurityUtils.getUsername());
        award.setCreateTime(new Date());
        award.setUpdateBy(SecurityUtils.getUsername());
        award.setUpdateTime(new Date());
        baseMapper.insert(award);
        List<AwardPerson> guideTeacherList = vo.getGuideTeacherList();
        if (guideTeacherList != null) {
            for (AwardPerson awardPerson : guideTeacherList) {
                awardPerson.setAwardId(award.getAwardId());
                awardPerson.setPersonType("0");
                awardPerson.setCreateBy(SecurityUtils.getUsername());
                awardPerson.setCreateTime(new Date());
                awardPerson.setUpdateBy(SecurityUtils.getUsername());
                awardPerson.setUpdateTime(new Date());
                awardPersonMapper.insert(awardPerson);
            }
        }
        List<AwardPerson> contestStuList = vo.getContestStuList();
        if (contestStuList != null) {
            for (AwardPerson awardPerson : contestStuList) {
                awardPerson.setAwardId(award.getAwardId());
                awardPerson.setPersonType("1");
                awardPerson.setCreateBy(SecurityUtils.getUsername());
                awardPerson.setCreateTime(new Date());
                awardPerson.setUpdateBy(SecurityUtils.getUsername());
                awardPerson.setUpdateTime(new Date());
                awardPersonMapper.insert(awardPerson);
            }
        }
        return 1;
    }

}
