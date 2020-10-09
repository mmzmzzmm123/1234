package com.ruoyi.project.benyi.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.benyi.mapper.ByAssessmentcontentMapper;
import com.ruoyi.project.benyi.domain.ByAssessmentcontent;
import com.ruoyi.project.benyi.service.IByAssessmentcontentService;

/**
 * 评估内容Service业务层处理
 *
 * @author tsbz
 * @date 2020-10-09
 */
@Service
public class ByAssessmentcontentServiceImpl implements IByAssessmentcontentService {
    @Autowired
    private ByAssessmentcontentMapper byAssessmentcontentMapper;

    /**
     * 查询评估内容
     *
     * @param id 评估内容ID
     * @return 评估内容
     */
    @Override
    public ByAssessmentcontent selectByAssessmentcontentById(Long id) {
        return byAssessmentcontentMapper.selectByAssessmentcontentById(id);
    }

    /**
     * 查询评估内容列表
     *
     * @param byAssessmentcontent 评估内容
     * @return 评估内容
     */
    @Override
    public List<ByAssessmentcontent> selectByAssessmentcontentList(ByAssessmentcontent byAssessmentcontent) {
        return byAssessmentcontentMapper.selectByAssessmentcontentList(byAssessmentcontent);
    }

    /**
     * 新增评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    @Override
    public int insertByAssessmentcontent(ByAssessmentcontent byAssessmentcontent) {
        byAssessmentcontent.setCreateTime(DateUtils.getNowDate());
        return byAssessmentcontentMapper.insertByAssessmentcontent(byAssessmentcontent);
    }

    /**
     * 修改评估内容
     *
     * @param byAssessmentcontent 评估内容
     * @return 结果
     */
    @Override
    public int updateByAssessmentcontent(ByAssessmentcontent byAssessmentcontent) {
        return byAssessmentcontentMapper.updateByAssessmentcontent(byAssessmentcontent);
    }

    /**
     * 批量删除评估内容
     *
     * @param ids 需要删除的评估内容ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentcontentByIds(Long[] ids) {
        return byAssessmentcontentMapper.deleteByAssessmentcontentByIds(ids);
    }

    /**
     * 删除评估内容信息
     *
     * @param id 评估内容ID
     * @return 结果
     */
    @Override
    public int deleteByAssessmentcontentById(Long id) {
        return byAssessmentcontentMapper.deleteByAssessmentcontentById(id);
    }
}
