package com.jjpt.business.service.impl;

import java.security.Security;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jjpt.business.domain.dto.UserExamRespDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jjpt.business.mapper.ElUserExamMapper;
import com.jjpt.business.domain.ElUserExam;
import com.jjpt.business.service.IElUserExamService;

/**
 * 考试记录Service业务层处理
 * 
 * @author 卢亚峰
 * @date 2024-05-22
 */
@Service
public class ElUserExamServiceImpl implements IElUserExamService 
{
    @Autowired
    private ElUserExamMapper elUserExamMapper;

    /**
     * 查询考试记录
     * 
     * @param id 考试记录主键
     * @return 考试记录
     */
    @Override
    public ElUserExam selectElUserExamById(String id)
    {
        return elUserExamMapper.selectElUserExamById(id);
    }

    /**
     * 查询考试记录列表
     * 
     * @param elUserExam 考试记录
     * @return 考试记录
     */
    @Override
    public List<UserExamRespDTO> selectElUserExamList(UserExamRespDTO query) {
        query.setUserId(SecurityUtils.getUserId());

        return elUserExamMapper.selectElUserExamList(query);
    }

    /**
     * 新增考试记录
     * 
     * @param elUserExam 考试记录
     * @return 结果
     */
    @Override
    public int insertElUserExam(ElUserExam elUserExam)
    {
        elUserExam.setCreateTime(DateUtils.getNowDate());
        return elUserExamMapper.insertElUserExam(elUserExam);
    }

    /**
     * 修改考试记录
     * 
     * @param elUserExam 考试记录
     * @return 结果
     */
    @Override
    public int updateElUserExam(ElUserExam elUserExam)
    {
        elUserExam.setUpdateTime(DateUtils.getNowDate());
        return elUserExamMapper.updateElUserExam(elUserExam);
    }

    /**
     * 批量删除考试记录
     * 
     * @param ids 需要删除的考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElUserExamByIds(String[] ids)
    {
        return elUserExamMapper.deleteElUserExamByIds(ids);
    }

    /**
     * 删除考试记录信息
     * 
     * @param id 考试记录主键
     * @return 结果
     */
    @Override
    public int deleteElUserExamById(String id)
    {
        return elUserExamMapper.deleteElUserExamById(id);
    }

    @Override
    public void joinResult(Long userId, String examId, int objScore, boolean b) {
        ElUserExam record = new ElUserExam();
        record.setUserId(userId);
        record.setExamId(examId);
        record = elUserExamMapper.selectElUserExamByKey(record);
        if(record == null){
            record = new ElUserExam();
            record.setCreateTime(new Date());
            record.setUpdateTime(new Date());
            record.setUserId(userId);
            record.setExamId(examId);
            record.setMaxScore(objScore);
            record.setPassed(b);
            record.setId(IdWorker.getIdStr());
            elUserExamMapper.insertElUserExam(record);
            return;
        }
        record.setTryCount(record.getTryCount()+1);
        record.setUpdateTime(new Date());
        if(record.getMaxScore() < objScore){
            record.setMaxScore(objScore);
            record.setPassed(b);
        }
        elUserExamMapper.updateElUserExam(record);
    }
}
