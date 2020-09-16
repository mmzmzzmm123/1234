package com.ruoyi.qtjs.service;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzPtjspxfazbxsj;

/**
 * 群体教师评选指标数据Service接口
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public interface ITsbzPtjspxfazbxsjService {
    /**
     * 查询群体教师评选指标数据
     *
     * @param id 群体教师评选指标数据ID
     * @return 群体教师评选指标数据
     */
    public TsbzPtjspxfazbxsj selectTsbzPtjspxfazbxsjById(Long id);

    /**
     * 查询群体教师评选指标数据列表
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 群体教师评选指标数据集合
     */
    public List<TsbzPtjspxfazbxsj> selectTsbzPtjspxfazbxsjList(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj);

    /**
     * 新增群体教师评选指标数据
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    public int insertTsbzPtjspxfazbxsj(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj);

    /**
     * 修改群体教师评选指标数据
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    public int updateTsbzPtjspxfazbxsj(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj);

    /**
     * 批量删除群体教师评选指标数据
     *
     * @param ids 需要删除的群体教师评选指标数据ID
     * @return 结果
     */
    public int deleteTsbzPtjspxfazbxsjByIds(Long[] ids);

    /**
     * 删除群体教师评选指标数据信息
     *
     * @param id 群体教师评选指标数据ID
     * @return 结果
     */
    public int deleteTsbzPtjspxfazbxsjById(Long id);
}
