package com.renxin.psychology.service;

import java.util.List;
import com.renxin.psychology.domain.PsyConsultantTeamSupervision;

/**
 * 咨询师团体Service接口
 * 
 * @author renxin
 * @date 2024-06-26
 */
public interface IPsyConsultantTeamSupervisionService 
{
    /**
     * 查询咨询师团体
     * 
     * @param id 咨询师团体主键
     * @return 咨询师团体
     */
    public PsyConsultantTeamSupervision selectPsyConsultantTeamSupervisionById(Long id);

    /**
     * 查询咨询师团体列表
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 咨询师团体集合
     */
    public List<PsyConsultantTeamSupervision> selectPsyConsultantTeamSupervisionList(PsyConsultantTeamSupervision psyConsultantTeamSupervision);

    /**
     * 新增咨询师团体
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 结果
     */
    public int insertPsyConsultantTeamSupervision(PsyConsultantTeamSupervision psyConsultantTeamSupervision);

    /**
     * 修改咨询师团体
     * 
     * @param psyConsultantTeamSupervision 咨询师团体
     * @return 结果
     */
    public int updatePsyConsultantTeamSupervision(PsyConsultantTeamSupervision psyConsultantTeamSupervision);

    /**
     * 批量删除咨询师团体
     * 
     * @param ids 需要删除的咨询师团体主键集合
     * @return 结果
     */
    public int deletePsyConsultantTeamSupervisionByIds(Long[] ids);

    /**
     * 删除咨询师团体信息
     * 
     * @param id 咨询师团体主键
     * @return 结果
     */
    public int deletePsyConsultantTeamSupervisionById(Long id);
}
