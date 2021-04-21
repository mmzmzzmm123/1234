package com.ruoyi.zcrpsgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.zcrpsgl.mapper.TsbzZcrzjzmdMapper;
import com.ruoyi.zcrpsgl.domain.TsbzZcrzjzmd;
import com.ruoyi.zcrpsgl.service.ITsbzZcrzjzmdService;

/**
 * 主持人报名专家组名单Service业务层处理
 * 
 * @author tsbz
 * @date 2021-04-21
 */
@Service
public class TsbzZcrzjzmdServiceImpl implements ITsbzZcrzjzmdService 
{
    @Autowired
    private TsbzZcrzjzmdMapper tsbzZcrzjzmdMapper;

    /**
     * 查询主持人报名专家组名单
     * 
     * @param id 主持人报名专家组名单ID
     * @return 主持人报名专家组名单
     */
    @Override
    public TsbzZcrzjzmd selectTsbzZcrzjzmdById(Long id)
    {
        return tsbzZcrzjzmdMapper.selectTsbzZcrzjzmdById(id);
    }

    /**
     * 查询主持人报名专家组名单列表
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 主持人报名专家组名单
     */
    @Override
    public List<TsbzZcrzjzmd> selectTsbzZcrzjzmdList(TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        return tsbzZcrzjzmdMapper.selectTsbzZcrzjzmdList(tsbzZcrzjzmd);
    }

    /**
     * 新增主持人报名专家组名单
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 结果
     */
    @Override
    public int insertTsbzZcrzjzmd(TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        tsbzZcrzjzmd.setCreateTime(DateUtils.getNowDate());
        return tsbzZcrzjzmdMapper.insertTsbzZcrzjzmd(tsbzZcrzjzmd);
    }

    /**
     * 修改主持人报名专家组名单
     * 
     * @param tsbzZcrzjzmd 主持人报名专家组名单
     * @return 结果
     */
    @Override
    public int updateTsbzZcrzjzmd(TsbzZcrzjzmd tsbzZcrzjzmd)
    {
        return tsbzZcrzjzmdMapper.updateTsbzZcrzjzmd(tsbzZcrzjzmd);
    }

    /**
     * 批量删除主持人报名专家组名单
     * 
     * @param ids 需要删除的主持人报名专家组名单ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrzjzmdByIds(Long[] ids)
    {
        return tsbzZcrzjzmdMapper.deleteTsbzZcrzjzmdByIds(ids);
    }

    /**
     * 删除主持人报名专家组名单信息
     * 
     * @param id 主持人报名专家组名单ID
     * @return 结果
     */
    @Override
    public int deleteTsbzZcrzjzmdById(Long id)
    {
        return tsbzZcrzjzmdMapper.deleteTsbzZcrzjzmdById(id);
    }
}
