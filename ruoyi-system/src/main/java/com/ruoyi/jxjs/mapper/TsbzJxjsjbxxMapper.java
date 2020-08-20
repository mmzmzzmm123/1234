package com.ruoyi.jxjs.mapper;

import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;

import java.util.List;

/**
 * 见习教师基本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public interface TsbzJxjsjbxxMapper 
{
    /**
     * 查询见习教师基本信息
     * 
     * @param id 见习教师基本信息ID
     * @return 见习教师基本信息
     */
    public TsbzJxjsjbxx selectTsbzJxjsjbxxById(Long id);

    /**
     * 查询见习教师基本信息列表
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息集合
     */
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxList(TsbzJxjsjbxx tsbzJxjsjbxx);

    /**
     * 查询见习教师基本信息列表
     *
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 见习教师基本信息集合
     */
    public List<TsbzJxjsjbxx> selectTsbzJxjsjbxxListnotjdx(TsbzJxjsjbxx tsbzJxjsjbxx);

    /**
     * 新增见习教师基本信息
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    public int insertTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx);

    /**
     * 修改见习教师基本信息
     * 
     * @param tsbzJxjsjbxx 见习教师基本信息
     * @return 结果
     */
    public int updateTsbzJxjsjbxx(TsbzJxjsjbxx tsbzJxjsjbxx);

    /**
     * 删除见习教师基本信息
     * 
     * @param id 见习教师基本信息ID
     * @return 结果
     */
    public int deleteTsbzJxjsjbxxById(Long id);

    /**
     * 批量删除见习教师基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxjsjbxxByIds(Long[] ids);
}
