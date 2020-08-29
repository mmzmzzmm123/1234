package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzJxzxkhzbxMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhzbx;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhzbxService;

/**
 * 考核指标项Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJxzxkhzbxServiceImpl implements ITsbzJxzxkhzbxService 
{
    @Autowired
    private TsbzJxzxkhzbxMapper tsbzJxzxkhzbxMapper;

    /**
     * 查询考核指标项
     * 
     * @param id 考核指标项ID
     * @return 考核指标项
     */
    @Override
    public TsbzJxzxkhzbx selectTsbzJxzxkhzbxById(Long id)
    {
        return tsbzJxzxkhzbxMapper.selectTsbzJxzxkhzbxById(id);
    }

    /**
     * 查询考核指标项列表
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 考核指标项
     */
    @Override
    public List<TsbzJxzxkhzbx> selectTsbzJxzxkhzbxList(TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        return tsbzJxzxkhzbxMapper.selectTsbzJxzxkhzbxList(tsbzJxzxkhzbx);
    }

    /**
     * 新增考核指标项
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxkhzbx(TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        tsbzJxzxkhzbx.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhzbxMapper.insertTsbzJxzxkhzbx(tsbzJxzxkhzbx);
    }

    /**
     * 修改考核指标项
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxkhzbx(TsbzJxzxkhzbx tsbzJxzxkhzbx)
    {
        return tsbzJxzxkhzbxMapper.updateTsbzJxzxkhzbx(tsbzJxzxkhzbx);
    }

    /**
     * 批量删除考核指标项
     * 
     * @param ids 需要删除的考核指标项ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhzbxByIds(Long[] ids)
    {
        return tsbzJxzxkhzbxMapper.deleteTsbzJxzxkhzbxByIds(ids);
    }

    /**
     * 删除考核指标项信息
     * 
     * @param id 考核指标项ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhzbxById(Long id)
    {
        return tsbzJxzxkhzbxMapper.deleteTsbzJxzxkhzbxById(id);
    }
}
