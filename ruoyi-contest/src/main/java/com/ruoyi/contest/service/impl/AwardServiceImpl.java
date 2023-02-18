package com.ruoyi.contest.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.contest.domain.Award;
import com.ruoyi.contest.domain.AwardPerson;
import com.ruoyi.contest.domain.SubContest;
import com.ruoyi.contest.domain.Teacher;
import com.ruoyi.contest.domain.vo.SaveAwardVo;
import com.ruoyi.contest.mapper.AwardMapper;
import com.ruoyi.contest.mapper.AwardPersonMapper;
import com.ruoyi.contest.mapper.SubContestMapper;
import com.ruoyi.contest.mapper.TeacherMapper;
import com.ruoyi.contest.service.IAwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    private final SubContestMapper subContestMapper;
    private final TeacherMapper teacherMapper;

    private final RestTemplate restTemplate;

    private final String BASE_URL = "http://140.210.209.124:8080/stuManage/stu/";

    /**
     * 查询获奖登记列表
     *
     * @param award 获奖登记
     * @return 获奖登记
     */
    @Override
    public List<Award> selectAwardList(Award award) {
        List<Award> list = baseMapper.selectAwardList(award);
        if (list != null) {
            for (Award a : list) {
                Map<String, Object> params = new HashMap<>();
                String subContestName = "";
                SubContest subContest = subContestMapper.selectById(a.getSubContestId());
                String guideTeacherNames = getGuideTeacherNames(a.getAwardId());
                String contestStuNames = getContestStuNames(a.getAwardId());
                if (subContest != null) {
                    subContestName = subContest.getName();
                }
                params.put("subContestName", subContestName);
                params.put("guideTeachers", guideTeacherNames);
                params.put("contestStuNames", contestStuNames);
                a.setParams(params);
            }
        }

        return list;
    }

    private String getGuideTeacherNames(Long awardId) {
        StringBuilder guideTeacherNames = new StringBuilder();
        List<AwardPerson> guideTeachers = awardPersonMapper.selectList(new QueryWrapper<AwardPerson>().eq("award_id", awardId).eq("person_type", 0));
        if (guideTeachers != null) {
            for (AwardPerson guideTeacher : guideTeachers) {
                Teacher teacher = teacherMapper.selectById(guideTeacher.getPersonId());
                if (teacher != null) {
                    guideTeacherNames.append(teacher.getName()).append(",");
                }
            }
        }
        String res = guideTeacherNames.toString();
        if (res.length() == 0) {
            return "";
        }
        return res.substring(0, res.length() - 1);
    }

    private String getContestStuNames(Long awardId) {
        StringBuilder contestStuNames = new StringBuilder();
        List<AwardPerson> contestStus = awardPersonMapper.selectList(new QueryWrapper<AwardPerson>().eq("award_id", awardId).eq("person_type", 1));
        if (contestStus != null) {
            for (AwardPerson contestStu : contestStus) {
                // todo 调整
                JSONObject jsonObject = restTemplate.getForObject(BASE_URL + contestStu.getPersonId(), JSONObject.class);
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) jsonObject.get("data");
                String name = (String) map.get("name");
                contestStuNames.append(name).append(",");
            }
        }
        String res = contestStuNames.toString();
        if (res.length() == 0) {
            return "";
        }
        return res.substring(0, res.length() - 1);
    }

    @Transactional
    @Override
    public int saveAward(SaveAwardVo vo) {
        Award award = new Award();
        BeanUtils.copyProperties(vo, award);
        award.setAttachmentUrl(vo.getAttachmentUrl());
        award.setCreateBy(SecurityUtils.getUsername());
        award.setCreateTime(new Date());
//        award.setUpdateBy(SecurityUtils.getUsername());
//        award.setUpdateTime(new Date());
        baseMapper.insert(award);
        List<AwardPerson> guideTeacherList = vo.getGuideTeacherList();
        if (guideTeacherList != null) {
            for (AwardPerson awardPerson : guideTeacherList) {
                awardPerson.setAwardId(award.getAwardId());
                awardPerson.setPersonType("0");
                awardPerson.setCreateBy(SecurityUtils.getUsername());
                awardPerson.setCreateTime(new Date());
//                awardPerson.setUpdateBy(SecurityUtils.getUsername());
//                awardPerson.setUpdateTime(new Date());
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
//                awardPerson.setUpdateBy(SecurityUtils.getUsername());
//                awardPerson.setUpdateTime(new Date());
                awardPersonMapper.insert(awardPerson);
            }
        }
        return 1;
    }

}
