package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.vo.ReactionVo;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.IReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Author: WangJinpeng
 * Date: 2022/8/2
 * Time: 21:29
 */
@Service
public class ReactionServiceImpl implements IReactionService {

    @Autowired
    private WorkReactionMapper workReactionMapper;
    @Autowired
    private WorkReactionDetailMapper workReactionDetailMapper;
    @Autowired
    private WorkFeedingMapper workFeedingMapper;
    @Autowired
    private WorkHeatingMapper workHeatingMapper;
    @Autowired
    private WorkConcentrateMapper workConcentrateMapper;
    @Autowired
    private WorkGelMapper workGelMapper;
    @Autowired
    private WorkExtractionMapper workExtractionMapper;

    @Override
    @Transactional
    public void addAndUpdateReaction(ReactionVo reactionVo) {
        //id为空代表新增，不为空代表编辑
        if (StringUtils.isNull(reactionVo.getReactionId())){
            String reactionCode = IdUtils.fastSimpleUUID();
            //反应列表
            WorkReaction workReaction = new WorkReaction();
            BeanUtils.copyBeanProp(workReaction, reactionVo);
            workReaction.setReactionCode(reactionCode);
            //反应详情列表
            List<WorkReactionDetail> workReactionDetails = reactionVo.getWorkReactionDetails().stream()
                    .map(x -> {
                        WorkReactionDetail workReactionDetail = new WorkReactionDetail();
                        BeanUtils.copyBeanProp(workReactionDetail, x);
                        workReactionDetail.setReactionCode(reactionCode);
                        return workReactionDetail;
                    }).collect(Collectors.toList());

            WorkHeating workHeating = reactionVo.getWorkHeating();
            workHeating.setReactionCode(reactionCode);
            WorkFeeding workFeeding = reactionVo.getWorkFeeding();
            workFeeding.setReactionCode(reactionCode);
            WorkExtraction workExtraction = reactionVo.getWorkExtraction();
            workExtraction.setReactionCode(reactionCode);
            WorkConcentrate workConcentrate = reactionVo.getWorkConcentrate();
            workConcentrate.setReactionCode(reactionCode);
            WorkGel workGel = reactionVo.getWorkGel();
            workGel.setReactionCode(reactionCode);

            workReactionMapper.insertWorkReaction(workReaction);
            workFeedingMapper.insertWorkFeeding(workFeeding);
            workHeatingMapper.insertWorkHeating(workHeating);
            workConcentrateMapper.insertWorkConcentrate(workConcentrate);
            workExtractionMapper.insertWorkExtraction(workExtraction);
            workGelMapper.insertWorkGel(workGel);
            workReactionDetails.forEach(k -> {
                workReactionDetailMapper.insertWorkReactionDetail(k);
            });
        } else {
            WorkReaction workReaction = new WorkReaction();
            BeanUtils.copyBeanProp(workReaction, reactionVo);
            workReactionMapper.updateWorkReaction(workReaction);
            workFeedingMapper.updateWorkFeeding(reactionVo.getWorkFeeding());
            workHeatingMapper.updateWorkHeating(reactionVo.getWorkHeating());
            workConcentrateMapper.updateWorkConcentrate(reactionVo.getWorkConcentrate());
            workExtractionMapper.updateWorkExtraction(reactionVo.getWorkExtraction());
            workGelMapper.updateWorkGel(reactionVo.getWorkGel());
            reactionVo.getWorkReactionDetails().forEach(k -> {
                workReactionDetailMapper.updateWorkReactionDetail(k);
            });
        }
    }

    @Override
    public List<ReactionVo> getReactionList() {
        List<ReactionVo> reactionVos = new ArrayList<>();
        //反应列表条数少暂时这样，之后可优化
        WorkReaction workReactionQuery = new WorkReaction();
        List<WorkReaction> workReactions = workReactionMapper.selectWorkReactionList(workReactionQuery);
        if (!CollectionUtils.isEmpty(workReactions)) {
            List<WorkReactionDetail> workReactionDetails = workReactionDetailMapper.selectWorkReactionDetailList(new WorkReactionDetail());
            List<WorkFeeding> workFeedings = workFeedingMapper.selectWorkFeedingList(new WorkFeeding());
            List<WorkHeating> workHeatings = workHeatingMapper.selectWorkHeatingList(new WorkHeating());
            List<WorkConcentrate> workConcentrates = workConcentrateMapper.selectWorkConcentrateList(new WorkConcentrate());
            List<WorkExtraction> workExtractions = workExtractionMapper.selectWorkExtractionList(new WorkExtraction());
            List<WorkGel> workGels = workGelMapper.selectWorkGelList(new WorkGel());

            for (WorkReaction workReaction : workReactions){
                ReactionVo reactionVo = new ReactionVo();
                BeanUtils.copyBeanProp(reactionVo, workReaction);

                List<WorkReactionDetail> reactionDetails = workReactionDetails.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                reactionVo.setWorkReactionDetails(reactionDetails);

                List<WorkFeeding> feedings = workFeedings.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(feedings)){
                    reactionVo.setWorkFeeding(feedings.get(0));
                }
                List<WorkHeating> heatings = workHeatings.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(heatings)){
                    reactionVo.setWorkHeating(heatings.get(0));
                }
                List<WorkConcentrate> concentrates = workConcentrates.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(concentrates)){
                    reactionVo.setWorkConcentrate(concentrates.get(0));
                }
                List<WorkExtraction> extractions = workExtractions.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(extractions)){
                    reactionVo.setWorkExtraction(extractions.get(0));
                }
                List<WorkGel> gels = workGels.stream().filter(x -> x.getReactionCode().equals(workReaction.getReactionCode())).collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(gels)){
                    reactionVo.setWorkGel(gels.get(0));
                }
                reactionVos.add(reactionVo);
            }
        }
        return reactionVos;
    }
}
