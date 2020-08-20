package com.ruoyi.jxjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxzxpxfaMapper;
import com.ruoyi.jxjs.domain.TsbzJxzxpxfa;
import com.ruoyi.jxjs.service.ITsbzJxzxpxfaService;

/**
 * 见习之星评选方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
@Service
public class TsbzJxzxpxfaServiceImpl implements ITsbzJxzxpxfaService 
{
    @Autowired
    private TsbzJxzxpxfaMapper tsbzJxzxpxfaMapper;

    /**
     * 查询见习之星评选方案
     * 
     * @param id 见习之星评选方案ID
     * @return 见习之星评选方案
     */
    @Override
    public TsbzJxzxpxfa selectTsbzJxzxpxfaById(Long id)
    {
        return tsbzJxzxpxfaMapper.selectTsbzJxzxpxfaById(id);
    }

    /**
     * 查询见习之星评选方案列表
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 见习之星评选方案
     */
    @Override
    public List<TsbzJxzxpxfa> selectTsbzJxzxpxfaList(TsbzJxzxpxfa tsbzJxzxpxfa)
    {
        return tsbzJxzxpxfaMapper.selectTsbzJxzxpxfaList(tsbzJxzxpxfa);
    }

    /**
     * 新增见习之星评选方案
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxpxfa(TsbzJxzxpxfa tsbzJxzxpxfa)
    {
        tsbzJxzxpxfa.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxpxfaMapper.insertTsbzJxzxpxfa(tsbzJxzxpxfa);
    }

    /**
     * 修改见习之星评选方案
     * 
     * @param tsbzJxzxpxfa 见习之星评选方案
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxpxfa(TsbzJxzxpxfa tsbzJxzxpxfa)
    {
        return tsbzJxzxpxfaMapper.updateTsbzJxzxpxfa(tsbzJxzxpxfa);
    }

    /**
     * 批量删除见习之星评选方案
     * 
     * @param ids 需要删除的见习之星评选方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxpxfaByIds(Long[] ids)
    {
        return tsbzJxzxpxfaMapper.deleteTsbzJxzxpxfaByIds(ids);
    }

    /**
     * 删除见习之星评选方案信息
     * 
     * @param id 见习之星评选方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxpxfaById(Long id)
    {
        return tsbzJxzxpxfaMapper.deleteTsbzJxzxpxfaById(id);
    }
}
