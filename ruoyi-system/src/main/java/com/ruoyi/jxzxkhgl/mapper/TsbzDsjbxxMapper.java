package com.ruoyi.jxzxkhgl.mapper;

import java.util.List;
import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;

/**
 * 导师基本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
public interface TsbzDsjbxxMapper 
{
    /**
     * 查询导师基本信息
     * 
     * @param id 导师基本信息ID
     * @return 导师基本信息
     */
    public TsbzDsjbxx selectTsbzDsjbxxById(Long id);

    /**
     * 查询导师基本信息列表
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 导师基本信息集合
     */
    public List<TsbzDsjbxx> selectTsbzDsjbxxList(TsbzDsjbxx tsbzDsjbxx);

    /**
     * 新增导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    public int insertTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx);

    /**
     * 修改导师基本信息
     * 
     * @param tsbzDsjbxx 导师基本信息
     * @return 结果
     */
    public int updateTsbzDsjbxx(TsbzDsjbxx tsbzDsjbxx);

    /**
     * 删除导师基本信息
     * 
     * @param id 导师基本信息ID
     * @return 结果
     */
    public int deleteTsbzDsjbxxById(Long id);

    /**
     * 批量删除导师基本信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzDsjbxxByIds(Long[] ids);
}
