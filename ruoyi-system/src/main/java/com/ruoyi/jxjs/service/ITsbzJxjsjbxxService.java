package com.ruoyi.jxjs.service;

import java.util.List;
import com.ruoyi.jxjs.domain.TsbzJxjsjbxx;

/**
 * 见习教师基本信息Service接口
 * 
 * @author ruoyi
 * @date 2020-08-20
 */
public interface ITsbzJxjsjbxxService 
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
     * 批量删除见习教师基本信息
     * 
     * @param ids 需要删除的见习教师基本信息ID
     * @return 结果
     */
    public int deleteTsbzJxjsjbxxByIds(Long[] ids);

    /**
     * 删除见习教师基本信息信息
     * 
     * @param id 见习教师基本信息ID
     * @return 结果
     */
    public int deleteTsbzJxjsjbxxById(Long id);
}
