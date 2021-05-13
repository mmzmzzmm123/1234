package com.ruoyi.jdgl.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jdgl.mapper.TsbzJdglhdglMapper;
import com.ruoyi.jdgl.domain.TsbzJdglhdgl;
import com.ruoyi.jdgl.service.ITsbzJdglhdglService;

/**
 * 基地管理活动管理Service业务层处理
 * 
 * @author tsbz
 * @date 2021-05-13
 */
@Service
public class TsbzJdglhdglServiceImpl implements ITsbzJdglhdglService 
{
    @Autowired
    private TsbzJdglhdglMapper tsbzJdglhdglMapper;

    /**
     * 查询基地管理活动管理
     * 
     * @param id 基地管理活动管理ID
     * @return 基地管理活动管理
     */
    @Override
    public TsbzJdglhdgl selectTsbzJdglhdglById(Long id)
    {
        return tsbzJdglhdglMapper.selectTsbzJdglhdglById(id);
    }

    /**
     * 查询基地管理活动管理列表
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 基地管理活动管理
     */
    @Override
    public List<TsbzJdglhdgl> selectTsbzJdglhdglList(TsbzJdglhdgl tsbzJdglhdgl)
    {
        return tsbzJdglhdglMapper.selectTsbzJdglhdglList(tsbzJdglhdgl);
    }

    /**
     * 新增基地管理活动管理
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 结果
     */
    @Override
    public int insertTsbzJdglhdgl(TsbzJdglhdgl tsbzJdglhdgl)
    {
        tsbzJdglhdgl.setCreateTime(DateUtils.getNowDate());
        return tsbzJdglhdglMapper.insertTsbzJdglhdgl(tsbzJdglhdgl);
    }

    /**
     * 修改基地管理活动管理
     * 
     * @param tsbzJdglhdgl 基地管理活动管理
     * @return 结果
     */
    @Override
    public int updateTsbzJdglhdgl(TsbzJdglhdgl tsbzJdglhdgl)
    {
        return tsbzJdglhdglMapper.updateTsbzJdglhdgl(tsbzJdglhdgl);
    }

    /**
     * 批量删除基地管理活动管理
     * 
     * @param ids 需要删除的基地管理活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglhdglByIds(Long[] ids)
    {
        return tsbzJdglhdglMapper.deleteTsbzJdglhdglByIds(ids);
    }

    /**
     * 删除基地管理活动管理信息
     * 
     * @param id 基地管理活动管理ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJdglhdglById(Long id)
    {
        return tsbzJdglhdglMapper.deleteTsbzJdglhdglById(id);
    }
}
