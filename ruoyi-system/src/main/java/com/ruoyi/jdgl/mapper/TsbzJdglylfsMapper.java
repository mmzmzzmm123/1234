package com.ruoyi.jdgl.mapper;

import java.util.List;
import com.ruoyi.jdgl.domain.TsbzJdglylfs;

/**
 * 基地管理引领辐射Mapper接口
 * 
 * @author tsbz
 * @date 2021-05-17
 */
public interface TsbzJdglylfsMapper 
{
    /**
     * 查询基地管理引领辐射
     * 
     * @param id 基地管理引领辐射ID
     * @return 基地管理引领辐射
     */
    public TsbzJdglylfs selectTsbzJdglylfsById(Long id);

    /**
     * 查询基地管理引领辐射列表
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 基地管理引领辐射集合
     */
    public List<TsbzJdglylfs> selectTsbzJdglylfsList(TsbzJdglylfs tsbzJdglylfs);

    /**
     * 新增基地管理引领辐射
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 结果
     */
    public int insertTsbzJdglylfs(TsbzJdglylfs tsbzJdglylfs);

    /**
     * 修改基地管理引领辐射
     * 
     * @param tsbzJdglylfs 基地管理引领辐射
     * @return 结果
     */
    public int updateTsbzJdglylfs(TsbzJdglylfs tsbzJdglylfs);

    /**
     * 删除基地管理引领辐射
     * 
     * @param id 基地管理引领辐射ID
     * @return 结果
     */
    public int deleteTsbzJdglylfsById(Long id);

    /**
     * 批量删除基地管理引领辐射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJdglylfsByIds(Long[] ids);
}
