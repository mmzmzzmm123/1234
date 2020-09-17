package com.ruoyi.qtjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzQtjspxfazbxsjMapper;
import com.ruoyi.qtjs.domain.TsbzQtjspxfazbxsj;
import com.ruoyi.qtjs.service.ITsbzQtjspxfazbxsjService;

/**
 * 群体教师评选指标数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjspxfazbxsjServiceImpl implements ITsbzQtjspxfazbxsjService 
{
    @Autowired
    private TsbzQtjspxfazbxsjMapper tsbzQtjspxfazbxsjMapper;

    /**
     * 查询群体教师评选指标数据
     * 
     * @param id 群体教师评选指标数据ID
     * @return 群体教师评选指标数据
     */
    @Override
    public TsbzQtjspxfazbxsj selectTsbzQtjspxfazbxsjById(Long id)
    {
        return tsbzQtjspxfazbxsjMapper.selectTsbzQtjspxfazbxsjById(id);
    }

    /**
     * 查询群体教师评选指标数据列表
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 群体教师评选指标数据
     */
    @Override
    public List<TsbzQtjspxfazbxsj> selectTsbzQtjspxfazbxsjList(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj)
    {
        return tsbzQtjspxfazbxsjMapper.selectTsbzQtjspxfazbxsjList(tsbzQtjspxfazbxsj);
    }

    /**
     * 新增群体教师评选指标数据
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    @Override
    public int insertTsbzQtjspxfazbxsj(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj)
    {
        tsbzQtjspxfazbxsj.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjspxfazbxsjMapper.insertTsbzQtjspxfazbxsj(tsbzQtjspxfazbxsj);
    }

    /**
     * 修改群体教师评选指标数据
     * 
     * @param tsbzQtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    @Override
    public int updateTsbzQtjspxfazbxsj(TsbzQtjspxfazbxsj tsbzQtjspxfazbxsj)
    {
        return tsbzQtjspxfazbxsjMapper.updateTsbzQtjspxfazbxsj(tsbzQtjspxfazbxsj);
    }

    /**
     * 批量删除群体教师评选指标数据
     * 
     * @param ids 需要删除的群体教师评选指标数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfazbxsjByIds(Long[] ids)
    {
        return tsbzQtjspxfazbxsjMapper.deleteTsbzQtjspxfazbxsjByIds(ids);
    }

    /**
     * 删除群体教师评选指标数据信息
     * 
     * @param id 群体教师评选指标数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfazbxsjById(Long id)
    {
        return tsbzQtjspxfazbxsjMapper.deleteTsbzQtjspxfazbxsjById(id);
    }
}
