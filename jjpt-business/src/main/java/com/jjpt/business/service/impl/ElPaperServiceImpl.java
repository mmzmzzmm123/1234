package com.jjpt.business.service.impl;

import java.awt.print.Paper;
import java.util.*;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jjpt.business.domain.*;
import com.jjpt.business.domain.dto.ElExamRepoExtDto;
import com.jjpt.business.domain.dto.ExamDetailRespDTO;
import com.jjpt.business.domain.dto.ExamSaveReqDto;
import com.jjpt.business.emums.ExamState;
import com.jjpt.business.emums.JobPrefix;
import com.jjpt.business.emums.PaperState;
import com.jjpt.business.emums.QuType;
import com.jjpt.business.job.BreakExamJob;
import com.jjpt.business.service.*;
import com.jjpt.business.utils.BeanMapper;
import com.jjpt.business.utils.CronUtils;
import com.ruoyi.common.core.controller.ApiError;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;

import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElPaperMapper;

/**
 * 考试记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-21
 */
@Service
public class ElPaperServiceImpl implements IElPaperService {
    @Autowired
    private ElPaperMapper elPaperMapper;
    @Autowired
    private IElExamService elExamService;
    @Autowired
    private IElExamRepoService  elExamRepoService;
    @Autowired
    private IElQuService  elQuService;
    @Autowired
    private IElQuAnswerService quAnswerService;
    @Autowired
    private IElPaperQuService  paperQuService;
    @Autowired
    private IElPaperQuAnswerService paperQuAnswerService;
    @Autowired
    private JobService jobService;
    /**
     * 展示的选项，ABC这样
     */
    private static List<String> ABC = Arrays.asList(new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K","L","M","N","O","P","Q","R","S","T","U","V","W","X"
            ,"Y","Z"
    });
    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    @Override
    public ExamDetailRespDTO selectElPaperById(String id) {
        ExamDetailRespDTO respDTO = new ExamDetailRespDTO();
        ElPaper paper = elPaperMapper.selectElPaperById(id);
        BeanMapper.copy(paper,respDTO);
        ElPaperQu elPaperQu = new ElPaperQu();
        elPaperQu.setPaperId(id);
        List<ElPaperQu> list = paperQuService.selectElPaperQuList(elPaperQu);
        List<ElPaperQu> radioList = new ArrayList<>();
        List<ElPaperQu> multiList = new ArrayList<>();
        List<ElPaperQu> judgeList = new ArrayList<>();
        for(ElPaperQu item: list){
            if(QuType.RADIO.equals(item.getQuType())){
                radioList.add(item);
            }
            if(QuType.MULTI.equals(item.getQuType())){
                multiList.add(item);
            }
            if(QuType.JUDGE.equals(item.getQuType())){
                judgeList.add(item);
            }
        }
        respDTO.setRadioList(radioList);
        respDTO.setMultiList(multiList);
        respDTO.setJudgeList(judgeList);
        return respDTO;
    }

    /**
     * 查询考试记录列表
     * 
     * @param elPaper 考试记录
     * @return 考试记录
     */
    @Override
    public List<ElPaper> selectElPaperList(ElPaper elPaper)
    {
        return elPaperMapper.selectElPaperList(elPaper);
    }

    /**
     * 新增考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    @Override
    public String insertElPaper(ElPaper elPaper) {
        Long userId = SecurityUtils.getUserId();
        //校验
        ElPaper checkElPaper = new ElPaper();
        checkElPaper.setState(PaperState.ING);
        checkElPaper.setUserId(userId);
        List<ElPaper> elPapers = elPaperMapper.selectElPaperList(checkElPaper);
        if(elPapers!=null&&elPapers.size()>0){
            throw new ServiceException("您有正在进行的考试！");
        }
        ExamSaveReqDto exam = elExamService.selectElExamById(elPaper.getExamId());
        if(exam == null){
            throw new ServiceException("考试不存在！");
        }
        if(!ExamState.ENABLE.equals(exam.getState())){
            throw new ServiceException("考试状态不正确！");
        }
        List<ElPaperQu> elPaperQus = generateByRepo(elPaper.getExamId());
        if(CollUtil.isEmpty(elPaperQus)){

            throw new ServiceException( "规则不正确，无对应的考题！");
        }

        ElPaper paper = this.savePaper(userId, exam, elPaperQus);
        String jobName = JobPrefix.BREAK_EXAM + paper.getId();
        jobService.addCronJob(BreakExamJob.class,jobName, CronUtils.dateToCron(paper.getLimitTime()),paper.getId());
        elPaper.setCreateTime(DateUtils.getNowDate());
        return paper.getId();
    }

    private ElPaper savePaper(Long userId, ExamSaveReqDto exam, List<ElPaperQu> quList) {
        Long deptId = SecurityUtils.getDeptId();
        //保存试卷基本信息
        ElPaper paper = new ElPaper();
        paper.setId(IdWorker.getIdStr());
        paper.setDeptId(deptId);
        paper.setExamId(exam.getId());
        paper.setTitle(exam.getTitle());
        paper.setTotalScore(exam.getTotalScore());
        paper.setTotalTime(exam.getTotalTime());
        paper.setUserScore(0);
        paper.setUserId(userId);
        paper.setCreateTime(new Date());
        paper.setUpdateTime(new Date());
        paper.setQualifyScore(exam.getQualifyScore());
        paper.setState(PaperState.ING);
        paper.setHasSaq(false);
        // 截止时间
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(System.currentTimeMillis());
        cl.add(Calendar.MINUTE, exam.getTotalTime());
        paper.setLimitTime(cl.getTime());
        elPaperMapper.insertElPaper(paper);
        if(CollUtil.isNotEmpty(quList)){
            savePaperQu(paper.getId(), quList);
        }
        return paper;
    }

    private void savePaperQu(String paperId, List<ElPaperQu> quList) {
        int sort = 0;
        for(ElPaperQu item:quList){
            item.setPaperId(paperId);
            item.setSort(sort);
            item.setId(IdWorker.getIdStr());
            List<ElQuAnswer> answerList = quAnswerService.listAnswerByRandom(item.getQuId());
            if (!CollUtil.isEmpty(answerList)) {
                int ii = 0;
                for (ElQuAnswer answer : answerList) {
                    ElPaperQuAnswer paperQuAnswer = new ElPaperQuAnswer();
                    paperQuAnswer.setId(UUID.randomUUID().toString());
                    paperQuAnswer.setPaperId(paperId);
                    paperQuAnswer.setQuId(answer.getQuId());
                    paperQuAnswer.setAnswerId(answer.getId());
                    paperQuAnswer.setChecked(false);
                    paperQuAnswer.setSort(ii);
                    paperQuAnswer.setAbc(ABC.get(ii));
                    paperQuAnswer.setIsRight(answer.getIsRight());
                    ii++;
                    paperQuAnswerService.insertElPaperQuAnswer(paperQuAnswer);
                }

            }
            sort++;
            paperQuService.insertElPaperQu(item);

        }
    }



    private List<ElPaperQu> generateByRepo(String examId){
        List<ElExamRepoExtDto> list =elExamRepoService.selectElExamRepoExtDtoList(examId);
        //最终的题目列表
        List<ElPaperQu> quList = new ArrayList<>();
        List<String> excludes = new ArrayList<>();
        excludes.add("none");
        if(!CollUtil.isEmpty(list)){
            for(ElExamRepoExtDto item:list){
                // 单选题
                if(item.getRadioCount()>0){
                    List<ElQu> elQus = elQuService.listByRamdom(item.getRepoId(), QuType.RADIO, excludes, item.getRadioCount());
                    for(ElQu elQu:elQus){
                        ElPaperQu elPaperQu = this.processPaperQu(item, elQu);
                        quList.add(elPaperQu);
                        excludes.add(elQu.getId());
                    }
                }

                //多选题
                if(item.getMultiCount() > 0) {
                    List<ElQu> multiList = elQuService.listByRamdom(item.getRepoId(), QuType.MULTI, excludes, item.getMultiCount());
                    for (ElQu qu : multiList) {
                        ElPaperQu paperQu = this.processPaperQu(item, qu);
                        quList.add(paperQu);
                        excludes.add(qu.getId());
                    }
                }

                // 判断题
                if(item.getJudgeCount() > 0) {
                    List<ElQu> judgeList = elQuService.listByRamdom(item.getRepoId(), QuType.JUDGE, excludes,
                            item.getJudgeCount());
                    for (ElQu qu : judgeList) {
                        ElPaperQu paperQu = this.processPaperQu(item, qu);
                        quList.add(paperQu);
                        excludes.add(qu.getId());
                    }
                }

            }
        }

        return quList;
    }

    private ElPaperQu processPaperQu(ElExamRepoExtDto item, ElQu qu){
        ElPaperQu paperQu = new ElPaperQu();
        paperQu.setQuId(qu.getId());
        paperQu.setAnswered(false);
        paperQu.setQuType(qu.getQuType());
        if (QuType.RADIO.equals(qu.getQuType())) {
            paperQu.setScore(item.getRadioScore());
            paperQu.setActualScore(item.getRadioScore());
        }
        if (QuType.MULTI.equals(qu.getQuType())) {
            paperQu.setScore(item.getMultiScore());
            paperQu.setActualScore(item.getMultiScore());
        }
        if (QuType.JUDGE.equals(qu.getQuType())) {
            paperQu.setScore(item.getJudgeScore());
            paperQu.setActualScore(item.getJudgeScore());
        }

        return paperQu;
    }
    /**
     * 修改考试记录
     * 
     * @param elPaper 考试记录
     * @return 结果
     */
    @Override
    public int updateElPaper(ElPaper elPaper)
    {
        elPaper.setUpdateTime(DateUtils.getNowDate());
        return elPaperMapper.updateElPaper(elPaper);
    }

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElPaperByIds(String[] ids)
    {
        return elPaperMapper.deleteElPaperByIds(ids);
    }

    /**
     * 删除考试记录信息
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElPaperById(String id)
    {
        return elPaperMapper.deleteElPaperById(id);
    }

    @Override
    public void handExam(String paperId) {
        //获取试卷信息
        ElPaper paper = elPaperMapper.selectElPaperById(paperId);
        //如果不是正常的，抛出异常
        if(!PaperState.ING.equals(paper.getState())){
            throw new ServiceException("试卷状态不正确！");
        }

        // 客观分
        int objScore = paperQuService.sumObjective(paperId);




    }
}
