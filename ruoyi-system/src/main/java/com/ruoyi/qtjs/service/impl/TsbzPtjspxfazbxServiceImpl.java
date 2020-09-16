package com.ruoyi.qtjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzPtjspxfazbxMapper;
import com.ruoyi.qtjs.domain.TsbzPtjspxfazbx;
import com.ruoyi.qtjs.service.ITsbzPtjspxfazbxService;

/**
 * 群体教师评选方案指标Service业务层处理
 *
 * @author ruoyi
 * @date 2020-09-16
 */
@Service
public class TsbzPtjspxfazbxServiceImpl implements ITsbzPtjspxfazbxService {
    @Autowired
    private TsbzPtjspxfazbxMapper tsbzPtjspxfazbxMapper;

    /**
     * 查询群体教师评选方案指标
     *
     * @param id 群体教师评选方案指标ID
     * @return 群体教师评选方案指标
     */
    @Override
    public TsbzPtjspxfazbx selectTsbzPtjspxfazbxById(Long id) {
        return tsbzPtjspxfazbxMapper.selectTsbzPtjspxfazbxById(id);
    }

    /**
     * 查询群体教师评选方案指标列表
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 群体教师评选方案指标
     */
    @Override
    public List<TsbzPtjspxfazbx> selectTsbzPtjspxfazbxList(TsbzPtjspxfazbx tsbzPtjspxfazbx) {
        return tsbzPtjspxfazbxMapper.selectTsbzPtjspxfazbxList(tsbzPtjspxfazbx);
    }

    /**
     * 新增群体教师评选方案指标
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    @Override
    public int insertTsbzPtjspxfazbx(TsbzPtjspxfazbx tsbzPtjspxfazbx) {
        tsbzPtjspxfazbx.setCreateTime(DateUtils.getNowDate());
        return tsbzPtjspxfazbxMapper.insertTsbzPtjspxfazbx(tsbzPtjspxfazbx);
    }

    /**
     * 修改群体教师评选方案指标
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    @Override
    public int updateTsbzPtjspxfazbx(TsbzPtjspxfazbx tsbzPtjspxfazbx) {
        return tsbzPtjspxfazbxMapper.updateTsbzPtjspxfazbx(tsbzPtjspxfazbx);
    }

    /**
     * 批量删除群体教师评选方案指标
     *
     * @param ids 需要删除的群体教师评选方案指标ID
     * @return 结果
     */
    @Override
    public int deleteTsbzPtjspxfazbxByIds(Long[] ids) {
        return tsbzPtjspxfazbxMapper.deleteTsbzPtjspxfazbxByIds(ids);
    }

    /**
     * 删除群体教师评选方案指标信息
     *
     * @param id 群体教师评选方案指标ID
     * @return 结果
     */
    @Override
    public int deleteTsbzPtjspxfazbxById(Long id) {
        return tsbzPtjspxfazbxMapper.deleteTsbzPtjspxfazbxById(id);
    }
}
