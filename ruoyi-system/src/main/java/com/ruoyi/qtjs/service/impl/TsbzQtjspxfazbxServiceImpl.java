package com.ruoyi.qtjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjs.mapper.TsbzQtjspxfazbxMapper;
import com.ruoyi.qtjs.domain.TsbzQtjspxfazbx;
import com.ruoyi.qtjs.service.ITsbzQtjspxfazbxService;

/**
 * 群体教师评选方案指标Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
@Service
public class TsbzQtjspxfazbxServiceImpl implements ITsbzQtjspxfazbxService 
{
    @Autowired
    private TsbzQtjspxfazbxMapper tsbzQtjspxfazbxMapper;

    /**
     * 查询群体教师评选方案指标
     * 
     * @param id 群体教师评选方案指标ID
     * @return 群体教师评选方案指标
     */
    @Override
    public TsbzQtjspxfazbx selectTsbzQtjspxfazbxById(Long id)
    {
        return tsbzQtjspxfazbxMapper.selectTsbzQtjspxfazbxById(id);
    }

    /**
     * 查询群体教师评选方案指标列表
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 群体教师评选方案指标
     */
    @Override
    public List<TsbzQtjspxfazbx> selectTsbzQtjspxfazbxList(TsbzQtjspxfazbx tsbzQtjspxfazbx)
    {
        return tsbzQtjspxfazbxMapper.selectTsbzQtjspxfazbxList(tsbzQtjspxfazbx);
    }

    /**
     * 新增群体教师评选方案指标
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    @Override
    public int insertTsbzQtjspxfazbx(TsbzQtjspxfazbx tsbzQtjspxfazbx)
    {
        tsbzQtjspxfazbx.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjspxfazbxMapper.insertTsbzQtjspxfazbx(tsbzQtjspxfazbx);
    }

    /**
     * 修改群体教师评选方案指标
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    @Override
    public int updateTsbzQtjspxfazbx(TsbzQtjspxfazbx tsbzQtjspxfazbx)
    {
        return tsbzQtjspxfazbxMapper.updateTsbzQtjspxfazbx(tsbzQtjspxfazbx);
    }

    /**
     * 批量删除群体教师评选方案指标
     * 
     * @param ids 需要删除的群体教师评选方案指标ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfazbxByIds(Long[] ids)
    {
        return tsbzQtjspxfazbxMapper.deleteTsbzQtjspxfazbxByIds(ids);
    }

    /**
     * 删除群体教师评选方案指标信息
     * 
     * @param id 群体教师评选方案指标ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjspxfazbxById(Long id)
    {
        return tsbzQtjspxfazbxMapper.deleteTsbzQtjspxfazbxById(id);
    }
}
