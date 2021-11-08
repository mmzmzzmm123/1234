package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.EmrInfoOdsCopy;

/**
 * emr原始数据Service接口
 * 
 * @author ruoyi
 * @date 2021-11-04
 */
public interface IEmrInfoOdsCopyService 
{
    /**
     * 查询emr原始数据
     * 
     * @param id emr原始数据主键
     * @return emr原始数据
     */
    public EmrInfoOdsCopy selectEmrInfoOdsCopyById(Long id);

    /**
     * 查询emr原始数据列表
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return emr原始数据集合
     */
    public List<EmrInfoOdsCopy> selectEmrInfoOdsCopyList(EmrInfoOdsCopy emrInfoOdsCopy);

    /**
     * 新增emr原始数据
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return 结果
     */
    public int insertEmrInfoOdsCopy(EmrInfoOdsCopy emrInfoOdsCopy);

    /**
     * 修改emr原始数据
     * 
     * @param emrInfoOdsCopy emr原始数据
     * @return 结果
     */
    public int updateEmrInfoOdsCopy(EmrInfoOdsCopy emrInfoOdsCopy);

    /**
     * 批量删除emr原始数据
     * 
     * @param ids 需要删除的emr原始数据主键集合
     * @return 结果
     */
    public int deleteEmrInfoOdsCopyByIds(Long[] ids);

    /**
     * 删除emr原始数据信息
     * 
     * @param id emr原始数据主键
     * @return 结果
     */
    public int deleteEmrInfoOdsCopyById(Long id);
}
