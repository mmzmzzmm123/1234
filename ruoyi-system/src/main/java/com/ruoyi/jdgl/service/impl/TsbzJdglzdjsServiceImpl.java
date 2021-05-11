package com.ruoyi.jdgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jdgl.mapper.TsbzJdglzdjsMapper;
import com.ruoyi.jdgl.domain.TsbzJdglzdjs;
import com.ruoyi.jdgl.service.ITsbzJdglzdjsService;

/**
 * 基地管理制度建设Service业务层处理
 * 
 * @author tsbz
 * @date 2021-05-11
 */
@Service
public class TsbzJdglzdjsServiceImpl implements ITsbzJdglzdjsService 
{
    @Autowired
    private TsbzJdglzdjsMapper tsbzJdglzdjsMapper;

    /**
     * 查询基地管理制度建设
     * 
     * @param id 基地管理制度建设ID
     * @return 基地管理制度建设
     */
    @Override
    public TsbzJdglzdjs selectTsbzJdglzdjsById(Long id)
    {
        return tsbzJdglzdjsMapper.selectTsbzJdglzdjsById(id);
    }

    /**
     * 查询基地管理制度建设列表
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 基地管理制度建设
     */
    @Override
    public List<TsbzJdglzdjs> selectTsbzJdglzdjsList(TsbzJdglzdjs tsbzJdglzdjs)
    {
        return tsbzJdglzdjsMapper.selectTsbzJdglzdjsList(tsbzJdglzdjs);
    }

    /**
     * 新增基地管理制度建设
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 结果
     */
    @Override
    public int insertTsbzJdglzdjs(TsbzJdglzdjs tsbzJdglzdjs)
    {
        tsbzJdglzdjs.setCreateTime(DateUtils.getNowDate());
        return tsbzJdglzdjsMapper.insertTsbzJdglzdjs(tsbzJdglzdjs);
    }

    /**
     * 修改基地管理制度建设
     * 
     * @param tsbzJdglzdjs 基地管理制度建设
     * @return 结果
     */
    @Override
    public int updateTsbzJdglzdjs(TsbzJdglzdjs tsbzJdglzdjs)
    {
        return tsbzJdglzdjsMapper.updateTsbzJdglzdjs(tsbzJdglzdjs);
    }

    /**
     * 批量删除基地管理制度建设
     * 
     * @param ids 需要删除的基地管理制度建设ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglzdjsByIds(Long[] ids)
    {
        return tsbzJdglzdjsMapper.deleteTsbzJdglzdjsByIds(ids);
    }

    /**
     * 删除基地管理制度建设信息
     * 
     * @param id 基地管理制度建设ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglzdjsById(Long id)
    {
        return tsbzJdglzdjsMapper.deleteTsbzJdglzdjsById(id);
    }
}
