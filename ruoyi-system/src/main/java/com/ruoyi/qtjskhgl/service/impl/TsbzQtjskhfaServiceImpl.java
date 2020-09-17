package com.ruoyi.qtjskhgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.qtjskhgl.mapper.TsbzQtjskhfaMapper;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhfa;
import com.ruoyi.qtjskhgl.service.ITsbzQtjskhfaService;

/**
 * 群体教师考核方案Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
@Service
public class TsbzQtjskhfaServiceImpl implements ITsbzQtjskhfaService 
{
    @Autowired
    private TsbzQtjskhfaMapper tsbzQtjskhfaMapper;

    /**
     * 查询群体教师考核方案
     * 
     * @param id 群体教师考核方案ID
     * @return 群体教师考核方案
     */
    @Override
    public TsbzQtjskhfa selectTsbzQtjskhfaById(Long id)
    {
        return tsbzQtjskhfaMapper.selectTsbzQtjskhfaById(id);
    }

    /**
     * 查询群体教师考核方案列表
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 群体教师考核方案
     */
    @Override
    public List<TsbzQtjskhfa> selectTsbzQtjskhfaList(TsbzQtjskhfa tsbzQtjskhfa)
    {
        return tsbzQtjskhfaMapper.selectTsbzQtjskhfaList(tsbzQtjskhfa);
    }

    /**
     * 新增群体教师考核方案
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 结果
     */
    @Override
    public int insertTsbzQtjskhfa(TsbzQtjskhfa tsbzQtjskhfa)
    {
        tsbzQtjskhfa.setCreateTime(DateUtils.getNowDate());
        return tsbzQtjskhfaMapper.insertTsbzQtjskhfa(tsbzQtjskhfa);
    }

    /**
     * 修改群体教师考核方案
     * 
     * @param tsbzQtjskhfa 群体教师考核方案
     * @return 结果
     */
    @Override
    public int updateTsbzQtjskhfa(TsbzQtjskhfa tsbzQtjskhfa)
    {
        return tsbzQtjskhfaMapper.updateTsbzQtjskhfa(tsbzQtjskhfa);
    }

    /**
     * 批量删除群体教师考核方案
     * 
     * @param ids 需要删除的群体教师考核方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhfaByIds(Long[] ids)
    {
        return tsbzQtjskhfaMapper.deleteTsbzQtjskhfaByIds(ids);
    }

    /**
     * 删除群体教师考核方案信息
     * 
     * @param id 群体教师考核方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzQtjskhfaById(Long id)
    {
        return tsbzQtjskhfaMapper.deleteTsbzQtjskhfaById(id);
    }
}
