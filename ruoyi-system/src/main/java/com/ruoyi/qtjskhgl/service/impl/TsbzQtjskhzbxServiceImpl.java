package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhzbxMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhzbx;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhzbxService;

/**
 * 群体教师考核指标项Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjskhzbxServiceImpl implements ITsbzQtjskhzbxService 
{
    @Autowired
    private TsbzQtjskhzbxMapper tsbzQtjskhzbxMapper;

    /**
     * 查询群体教师考核指标项
     * 
     * @param id 群体教师考核指标项ID
     * @return 群体教师考核指标项
     */
    @Override
    public TsbzQtjskhzbx selectTsbzQtjskhzbxById(Long id)
    {
        return tsbzQtjskhzbxMapper.selectTsbzQtjskhzbxById(id);
    }

    /**
     * 查询群体教师考核指标项列表
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 群体教师考核指标项
     */
    @Override
    public List<TsbzQtjskhzbx> selectTsbzQtjskhzbxList(TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        return tsbzQtjskhzbxMapper.selectTsbzQtjskhzbxList(tsbzQtjskhzbx);
    }

    /**
     * 新增群体教师考核指标项
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhzbx(TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        tsbzQtjskhzbx.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhzbxMapper.insertTsbzQtjskhzbx(tsbzQtjskhzbx);
    }

    /**
     * 修改群体教师考核指标项
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhzbx(TsbzQtjskhzbx tsbzQtjskhzbx)
    {
        return tsbzQtjskhzbxMapper.updateTsbzQtjskhzbx(tsbzQtjskhzbx);
    }

    /**
     * 批量删除群体教师考核指标项
     * 
     * @param ids 需要删除的群体教师考核指标项ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhzbxByIds(Long[] ids)
    {
        return tsbzQtjskhzbxMapper.deleteTsbzQtjskhzbxByIds(ids);
    }

    /**
     * 删除群体教师考核指标项信息
     * 
     * @param id 群体教师考核指标项ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhzbxById(Long id)
    {
        return tsbzQtjskhzbxMapper.deleteTsbzQtjskhzbxById(id);
    }
}
