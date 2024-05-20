package com.jjpt.business.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jjpt.business.domain.ElExam;
import com.jjpt.business.domain.ElExamRepo;
import com.jjpt.business.domain.dto.ElExamRepoExtDto;
import com.jjpt.business.domain.dto.ExamSaveReqDto;
import com.jjpt.business.mapper.ElExamMapper;
import com.jjpt.business.service.IElExamRepoService;
import com.jjpt.business.service.IElExamService;
import com.jjpt.business.service.IElRepoService;
import com.jjpt.business.utils.BeanMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 考试主Service业务层处理
 *
 * @author ruoyi
 * @date 2024-05-18
 */
@Service
public class ElExamServiceImpl implements IElExamService {
    @Autowired
    private ElExamMapper elExamMapper;

    @Autowired
    private IElExamRepoService elExamRepoService;
    @Autowired
    private IElRepoService elRepoService;



    /**
     * 查询考试主
     *
     * @param id 考试主主键
     * @return 考试主
     */
    @Override
    public ExamSaveReqDto selectElExamById(String id) {
        ExamSaveReqDto respDto = new ExamSaveReqDto();
        ElExam exam = elExamMapper.selectElExamById(id);
        BeanMapper.copy(exam, respDto);
        // 题库
        ElExamRepo elExamRepo = new ElExamRepo();
        elExamRepo.setExamId(id);

        // 题库

        List<ElExamRepo> elExamRepos = elExamRepoService.selectElExamRepoList(elExamRepo);
        List<ElExamRepoExtDto> elExamRepoExtDtos = BeanMapper.mapList(elExamRepos, ElExamRepoExtDto.class);
        for(ElExamRepoExtDto item : elExamRepoExtDtos){
            Map<String, Object> stringObjectMap = elRepoService.selectTypeNumbyRepoId(item.getRepoId());
            item.setTotalRadio(Integer.valueOf(stringObjectMap.get("radioCount").toString()));
            item.setTotalMulti(Integer.valueOf(stringObjectMap.get("multiCount").toString()));
            item.setTotalJudge(Integer.valueOf(stringObjectMap.get("judgeCount").toString()));
        }
        respDto.setRepoList(elExamRepoExtDtos);
        return respDto;
    }

    /**
     * 查询考试主列表
     *
     * @param elExam 考试主
     * @return 考试主
     */
    @Override
    public List<ElExam> selectElExamList(ElExam elExam) {
        return elExamMapper.selectElExamList(elExam);
    }

    /**
     * 新增考试主
     *
     * @param elExam 考试主
     * @return 结果
     */
    @Override
    public int insertElExam(ExamSaveReqDto elExam) {
        boolean isInsert = false;
        // ID
        String id = elExam.getId();
        if (StringUtils.isBlank(id)) {
            id = IdWorker.getIdStr();
            isInsert = true;
        }
        //复制参数
        ElExam entity = new ElExam();
        this.calcScore(elExam);

        // 复制基本数据
        BeanMapper.copy(elExam, entity);
        entity.setId(id);
        if (elExam.getTimeLimit() != null && elExam.getTimeLimit() && elExam.getState() != null && elExam.getState() == 2) {
            entity.setState(0);
        } else {
            entity.setState(elExam.getState());
        }
        //题库组卷
        elExamRepoService.saveAll(id, elExam.getRepoList());
        elExam.setCreateTime(DateUtils.getNowDate());
        int res;
        if (isInsert) {
            res = elExamMapper.insertElExam(entity);
        } else {
            res = elExamMapper.updateElExam(entity);
        }
        return res;
    }

    private void calcScore(ExamSaveReqDto elExam) {
        // 主观题分数
        int objScore = 0;
        // 题库组卷
        List<ElExamRepoExtDto> repoList = elExam.getRepoList();
        for (ElExamRepoExtDto item : repoList) {
            if (item.getRadioCount() != null && item.getRadioCount() > 0 && item.getRadioScore() != null && item.getRadioScore() > 0) {
                objScore += item.getRadioCount() * item.getRadioScore();
            }
            if (item.getMultiCount() != null && item.getMultiCount() > 0 && item.getMultiScore() != null && item.getMultiScore() > 0) {
                objScore += item.getMultiCount() * item.getMultiScore();
            }
            if (item.getJudgeCount() != null && item.getJudgeCount() > 0 && item.getJudgeScore() != null && item.getJudgeScore() > 0) {
                objScore += item.getJudgeCount() * item.getJudgeScore();
            }
        }
        elExam.setTotalScore(objScore);
    }

    /**
     * 修改考试主
     *
     * @param elExam 考试主
     * @return 结果
     */
    @Override
    public int updateElExam(ElExam elExam) {
        elExam.setUpdateTime(DateUtils.getNowDate());
        return elExamMapper.updateElExam(elExam);
    }

    /**
     * 批量删除考试主
     *
     * @param ids 需要删除的考试主主键
     * @return 结果
     */
    @Override
    public int deleteElExamByIds(String[] ids) {
        return elExamMapper.deleteElExamByIds(ids);
    }

    /**
     * 删除考试主信息
     *
     * @param id 考试主主键
     * @return 结果
     */
    @Override
    public int deleteElExamById(String id) {
        return elExamMapper.deleteElExamById(id);
    }
}
