package com.ruoyi.jxzxkhgl.service;

import java.util.List;
import com.ruoyi.jxzxkhgl.domain.TsbzDsjbxx;

/**
 * 导师基本信息Service接口
 * 
 * @author ruoyi
 * @date 2020-11-10
 */
public interface ITsbzDsjbxxService 
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
     * 批量删除导师基本信息
     * 
     * @param ids 需要删除的导师基本信息ID
     * @return 结果
     */
    public int deleteTsbzDsjbxxByIds(Long[] ids);

    /**
     * 删除导师基本信息信息
     * 
     * @param id 导师基本信息ID
     * @return 结果
     */
    public int deleteTsbzDsjbxxById(Long id);

    /**
     * 导入用户数据
     *
     * @param dsjbxxList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importDsjbxx(List<TsbzDsjbxx> dsjbxxList, Boolean isUpdateSupport, String operName);
}
