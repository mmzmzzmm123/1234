package com.ruoyi.qtjs.service;

import java.util.List;
import com.ruoyi.qtjs.domain.TsbzQtjspxfazbxsj;

/**
 * 群体教师评选指标数据Service接口
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public interface ITsbzQtjspxfazbxsjService 
{
    /**
     * 查询群体教师评选指标数据
     * 
     * @param id 群体教师评选指标数据ID
     * @return 群体教师评选指标数据
     */
    public TsbzQtjspxfazbxsj selectTsbzQtjspxfazbxsjById(Long id);

    /**
     * 查询群体教师评选指标数据列表
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 群体教师评选指标数据集合
     */
    public List<TsbzQtjspxfazbxsj> selectTsbzQtjspxfazbxsjList(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj);

    /**
     * 新增群体教师评选指标数据
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    public int insertTsbzQtjspxfazbxsj(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj);

    /**
     * 修改群体教师评选指标数据
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    public int updateTsbzQtjspxfazbxsj(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj);

    /**
     * 批量删除群体教师评选指标数据
     * 
     * @param ids 需要删除的群体教师评选指标数据ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfazbxsjByIds(Long[] ids);

    /**
     * 删除群体教师评选指标数据信息
     * 
     * @param id 群体教师评选指标数据ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfazbxsjById(Long id);
}
