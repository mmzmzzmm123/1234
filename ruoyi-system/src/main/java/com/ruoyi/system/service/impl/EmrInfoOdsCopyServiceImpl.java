package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.EmrInfoOdsCopyMapper;
import com.ruoyi.system.domain.EmrInfoOdsCopy;
import com.ruoyi.system.service.IEmrInfoOdsCopyService;

/**
 * emr原始数据Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-11-04
 */
@Service
public class EmrInfoOdsCopyServiceImpl implements IEmrInfoOdsCopyService 
{
    @Autowired
    private EmrInfoOdsCopyMapper emrInfoOdsCopyMapper;

    /**
     * 查询emr原始数据
     * 
     * @param id emr原始数据主键
     * @return emr原始数据
     */
    @Override
    public EmrInfoOdsCopy selectEmrInfoOdsCopyById(Long id)
    {
        return emrInfoOdsCopyMapper.selectEmrInfoOdsCopyById(id);
    }

    /**
     * 查询emr原始数据列表
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return emr原始数据
     */
    @Override
    public List<EmrInfoOdsCopy> selectEmrInfoOdsCopyList(EmrInfoOdsCopy emrInfoOdsCopy)
    {
        return emrInfoOdsCopyMapper.selectEmrInfoOdsCopyList(emrInfoOdsCopy);
    }

    /**
     * 新增emr原始数据
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return 结果
     */
    @Override
    public int insertEmrInfoOdsCopy(EmrInfoOdsCopy emrInfoOdsCopy)
    {
        return emrInfoOdsCopyMapper.insertEmrInfoOdsCopy(emrInfoOdsCopy);
    }

    /**
     * 修改emr原始数据
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return 结果
     */
    @Override
    public int updateEmrInfoOdsCopy(EmrInfoOdsCopy emrInfoOdsCopy)
    {
        return emrInfoOdsCopyMapper.updateEmrInfoOdsCopy(emrInfoOdsCopy);
    }

    /**
     * 批量删除emr原始数据
     * 
     * @param ids 需要删除的emr原始数据主键
     * @return 结果
     */
    @Override
    public int deleteEmrInfoOdsCopyByIds(Long[] ids)
    {
        return emrInfoOdsCopyMapper.deleteEmrInfoOdsCopyByIds(ids);
    }

    /**
     * 删除emr原始数据信息
     * 
     * @param id emr原始数据主键
     * @return 结果
     */
    @Override
    public int deleteEmrInfoOdsCopyById(Long id)
    {
        return emrInfoOdsCopyMapper.deleteEmrInfoOdsCopyById(id);
    }
}
