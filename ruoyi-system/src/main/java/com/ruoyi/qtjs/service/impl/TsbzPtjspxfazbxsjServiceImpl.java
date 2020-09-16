package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzPtjspxfazbxsjMapper;
import com.ruoyi.qtjs.domain.TsbzPtjspxfazbxsj;
import com.ruoyi.qtjs.service.ITsbzPtjspxfazbxsjService;

/**
 * 群体教师评选指标数据Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@Service
public class TsbzPtjspxfazbxsjServiceImpl implements ITsbzPtjspxfazbxsjService {
    @Autowired
    private TsbzPtjspxfazbxsjMapper tsbzPtjspxfazbxsjMapper;

    /**
     * 查询群体教师评选指标数据
     *
     * @param id 群体教师评选指标数据ID
     * @return 群体教师评选指标数据
     */
    @Override
    public TsbzPtjspxfazbxsj selectTsbzPtjspxfazbxsjById(Long id) {
        return tsbzPtjspxfazbxsjMapper.selectTsbzPtjspxfazbxsjById(id);
    }

    /**
     * 查询群体教师评选指标数据列表
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 群体教师评选指标数据
     */
    @Override
    public List<TsbzPtjspxfazbxsj> selectTsbzPtjspxfazbxsjList(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj) {
        return tsbzPtjspxfazbxsjMapper.selectTsbzPtjspxfazbxsjList(tsbzPtjspxfazbxsj);
    }

    /**
     * 新增群体教师评选指标数据
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    @Override
    public int insertTsbzPtjspxfazbxsj(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj) {
        tsbzPtjspxfazbxsj.setCreateTime(DateUtils.getNowDate());
        return tsbzPtjspxfazbxsjMapper.insertTsbzPtjspxfazbxsj(tsbzPtjspxfazbxsj);
    }

    /**
     * 修改群体教师评选指标数据
     *
     * @param tsbzPtjspxfazbxsj 群体教师评选指标数据
     * @return 结果
     */
    @Override
    public int updateTsbzPtjspxfazbxsj(TsbzPtjspxfazbxsj tsbzPtjspxfazbxsj) {
        return tsbzPtjspxfazbxsjMapper.updateTsbzPtjspxfazbxsj(tsbzPtjspxfazbxsj);
    }

    /**
     * 批量删除群体教师评选指标数据
     *
     * @param ids 需要删除的群体教师评选指标数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzPtjspxfazbxsjByIds(Long[] ids) {
        return tsbzPtjspxfazbxsjMapper.deleteTsbzPtjspxfazbxsjByIds(ids);
    }

    /**
     * 删除群体教师评选指标数据信息
     *
     * @param id 群体教师评选指标数据ID
     * @return 结果
     */
    @Override
    public int deleteTsbzPtjspxfazbxsjById(Long id) {
        return tsbzPtjspxfazbxsjMapper.deleteTsbzPtjspxfazbxsjById(id);
    }
}
