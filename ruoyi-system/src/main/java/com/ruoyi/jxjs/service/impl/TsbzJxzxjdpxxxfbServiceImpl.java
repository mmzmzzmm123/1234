package com.ruoyi.jxjs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxzxjdpxxxfbMapper;
import com.ruoyi.jxjs.domain.TsbzJxzxjdpxxxfb;
import com.ruoyi.jxjs.service.ITsbzJxzxjdpxxxfbService;

/**
 * 基地培训信息发布Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-11-09
 */
@Service
public class TsbzJxzxjdpxxxfbServiceImpl implements ITsbzJxzxjdpxxxfbService 
{
    @Autowired
    private TsbzJxzxjdpxxxfbMapper tsbzJxzxjdpxxxfbMapper;

    /**
     * 查询基地培训信息发布
     * 
     * @param id 基地培训信息发布ID
     * @return 基地培训信息发布
     */
    @Override
    public TsbzJxzxjdpxxxfb selectTsbzJxzxjdpxxxfbById(Long id)
    {
        return tsbzJxzxjdpxxxfbMapper.selectTsbzJxzxjdpxxxfbById(id);
    }

    /**
     * 查询基地培训信息发布列表
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 基地培训信息发布
     */
    @Override
    public List<TsbzJxzxjdpxxxfb> selectTsbzJxzxjdpxxxfbList(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        return tsbzJxzxjdpxxxfbMapper.selectTsbzJxzxjdpxxxfbList(tsbzJxzxjdpxxxfb);
    }

    /**
     * 新增基地培训信息发布
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxjdpxxxfb(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        tsbzJxzxjdpxxxfb.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxjdpxxxfbMapper.insertTsbzJxzxjdpxxxfb(tsbzJxzxjdpxxxfb);
    }

    /**
     * 修改基地培训信息发布
     * 
     * @param tsbzJxzxjdpxxxfb 基地培训信息发布
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxjdpxxxfb(TsbzJxzxjdpxxxfb tsbzJxzxjdpxxxfb)
    {
        return tsbzJxzxjdpxxxfbMapper.updateTsbzJxzxjdpxxxfb(tsbzJxzxjdpxxxfb);
    }

    /**
     * 批量删除基地培训信息发布
     * 
     * @param ids 需要删除的基地培训信息发布ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxjdpxxxfbByIds(Long[] ids)
    {
        return tsbzJxzxjdpxxxfbMapper.deleteTsbzJxzxjdpxxxfbByIds(ids);
    }

    /**
     * 删除基地培训信息发布信息
     * 
     * @param id 基地培训信息发布ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxjdpxxxfbById(Long id)
    {
        return tsbzJxzxjdpxxxfbMapper.deleteTsbzJxzxjdpxxxfbById(id);
    }
}
