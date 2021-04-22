package com.ruoyi.xypsgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xypsgl.mapper.TsbzXypsfaMapper;
import com.ruoyi.xypsgl.domain.TsbzXypsfa;
import com.ruoyi.xypsgl.service.ITsbzXypsfaService;

/**
 * 学员评审方案Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-22
 */
@Service
public class TsbzXypsfaServiceImpl implements ITsbzXypsfaService 
{
    @Autowired
    private TsbzXypsfaMapper tsbzXypsfaMapper;

    /**
     * 查询学员评审方案
     * 
     * @param id 学员评审方案ID
     * @return 学员评审方案
     */
    @Override
    public TsbzXypsfa selectTsbzXypsfaById(Long id)
    {
        return tsbzXypsfaMapper.selectTsbzXypsfaById(id);
    }

    /**
     * 查询学员评审方案列表
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 学员评审方案
     */
    @Override
    public List<TsbzXypsfa> selectTsbzXypsfaList(TsbzXypsfa tsbzXypsfa)
    {
        return tsbzXypsfaMapper.selectTsbzXypsfaList(tsbzXypsfa);
    }

    /**
     * 新增学员评审方案
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 结果
     */
    @Override
    public int insertTsbzXypsfa(TsbzXypsfa tsbzXypsfa)
    {
        tsbzXypsfa.setCreateTime(DateUtils.getNowDate());
        return tsbzXypsfaMapper.insertTsbzXypsfa(tsbzXypsfa);
    }

    /**
     * 修改学员评审方案
     * 
     * @param tsbzXypsfa 学员评审方案
     * @return 结果
     */
    @Override
    public int updateTsbzXypsfa(TsbzXypsfa tsbzXypsfa)
    {
        return tsbzXypsfaMapper.updateTsbzXypsfa(tsbzXypsfa);
    }

    /**
     * 批量删除学员评审方案
     * 
     * @param ids 需要删除的学员评审方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXypsfaByIds(Long[] ids)
    {
        return tsbzXypsfaMapper.deleteTsbzXypsfaByIds(ids);
    }

    /**
     * 删除学员评审方案信息
     * 
     * @param id 学员评审方案ID
     * @return 结果
     */
    @Override
    public int deleteTsbzXypsfaById(Long id)
    {
        return tsbzXypsfaMapper.deleteTsbzXypsfaById(id);
    }
}
