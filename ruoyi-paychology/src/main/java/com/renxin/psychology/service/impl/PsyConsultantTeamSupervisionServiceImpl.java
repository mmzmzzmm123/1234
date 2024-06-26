package com.renxin.psychology.service.impl;

import java.util.List;
import com.renxin.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.renxin.psychology.mapper.PsyConsultantTeamSupervisionMapper;
import com.renxin.psychology.domain.PsyConsultantTeamSupervision;
import com.renxin.psychology.service.IPsyConsultantTeamSupervisionService;

/**
 * 咨询师团体Service业务层处理
 * 
 * @author renxin
 * @date 2024-06-26
 */
@Service
public class PsyConsultantTeamSupervisionServiceImpl implements IPsyConsultantTeamSupervisionService 
{
    @Autowired
    private PsyConsultantTeamSupervisionMapper psyConsultantTeamSupervisionMapper;

    /**
     * 查询咨询师团体
     * 
     * @param id 咨询师团体主键
     * @return 咨询师团体
     */
    @Override
    public PsyConsultantTeamSupervision selectPsyConsultantTeamSupervisionById(Long id)
    {
        return psyConsultantTeamSupervisionMapper.selectPsyConsultantTeamSupervisionById(id);
    }

    /**
     * 查询咨询师团体列表
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 咨询师团体
     */
    @Override
    public List<PsyConsultantTeamSupervision> selectPsyConsultantTeamSupervisionList(PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        return psyConsultantTeamSupervisionMapper.selectPsyConsultantTeamSupervisionList(psyConsultantTeamSupervision);
    }

    /**
     * 新增咨询师团体
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 结果
     */
    @Override
    public int insertPsyConsultantTeamSupervision(PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        psyConsultantTeamSupervision.setCreateTime(DateUtils.getNowDate());
        return psyConsultantTeamSupervisionMapper.insertPsyConsultantTeamSupervision(psyConsultantTeamSupervision);
    }

    /**
     * 修改咨询师团体
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 结果
     */
    @Override
    public int updatePsyConsultantTeamSupervision(PsyConsultantTeamSupervision psyConsultantTeamSupervision)
    {
        psyConsultantTeamSupervision.setUpdateTime(DateUtils.getNowDate());
        return psyConsultantTeamSupervisionMapper.updatePsyConsultantTeamSupervision(psyConsultantTeamSupervision);
    }

    /**
     * 批量删除咨询师团体
     * 
     * @param ids 需要删除的咨询师团体主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantTeamSupervisionByIds(Long[] ids)
    {
        return psyConsultantTeamSupervisionMapper.deletePsyConsultantTeamSupervisionByIds(ids);
    }

    /**
     * 删除咨询师团体信息
     * 
     * @param id 咨询师团体主键
     * @return 结果
     */
    @Override
    public int deletePsyConsultantTeamSupervisionById(Long id)
    {
        return psyConsultantTeamSupervisionMapper.deletePsyConsultantTeamSupervisionById(id);
    }
}
