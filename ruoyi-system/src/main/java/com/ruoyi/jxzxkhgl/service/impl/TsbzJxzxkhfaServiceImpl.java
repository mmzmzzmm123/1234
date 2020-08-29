package com.ruoyi.jxzxkhgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxzxkhgl.mapper.TsbzJxzxkhfaMapper;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhfa;
import com.ruoyi.jxzxkhgl.service.ITsbzJxzxkhfaService;

/**
 * 见习之星考核方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
@Service
public class TsbzJxzxkhfaServiceImpl implements ITsbzJxzxkhfaService 
{
    @Autowired
    private TsbzJxzxkhfaMapper tsbzJxzxkhfaMapper;

    /**
     * 查询见习之星考核方案
     * 
     * @param id 见习之星考核方案ID
     * @return 见习之星考核方案
     */
    @Override
    public TsbzJxzxkhfa selectTsbzJxzxkhfaById(Long id)
    {
        return tsbzJxzxkhfaMapper.selectTsbzJxzxkhfaById(id);
    }

    /**
     * 查询见习之星考核方案列表
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 见习之星考核方案
     */
    @Override
    public List<TsbzJxzxkhfa> selectTsbzJxzxkhfaList(TsbzJxzxkhfa tsbzJxzxkhfa)
    {
        return tsbzJxzxkhfaMapper.selectTsbzJxzxkhfaList(tsbzJxzxkhfa);
    }

    /**
     * 新增见习之星考核方案
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxkhfa(TsbzJxzxkhfa tsbzJxzxkhfa)
    {
        tsbzJxzxkhfa.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxkhfaMapper.insertTsbzJxzxkhfa(tsbzJxzxkhfa);
    }

    /**
     * 修改见习之星考核方案
     * 
     * @param tsbzJxzxkhfa 见习之星考核方案
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxkhfa(TsbzJxzxkhfa tsbzJxzxkhfa)
    {
        return tsbzJxzxkhfaMapper.updateTsbzJxzxkhfa(tsbzJxzxkhfa);
    }

    /**
     * 批量删除见习之星考核方案
     * 
     * @param ids 需要删除的见习之星考核方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhfaByIds(Long[] ids)
    {
        return tsbzJxzxkhfaMapper.deleteTsbzJxzxkhfaByIds(ids);
    }

    /**
     * 删除见习之星考核方案信息
     * 
     * @param id 见习之星考核方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxkhfaById(Long id)
    {
        return tsbzJxzxkhfaMapper.deleteTsbzJxzxkhfaById(id);
    }
}
