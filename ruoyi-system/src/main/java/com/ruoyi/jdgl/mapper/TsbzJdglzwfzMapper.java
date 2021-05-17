package com.ruoyi.jdgl.mapper;

import java.util.List;
import com.ruoyi.jdgl.domain.TsbzJdglzwfz;

/**
 * 基地管理自我发展Mapper接口
 * 
 * @author tsbz
 * @date 2021-05-17
 */
public interface TsbzJdglzwfzMapper 
{
    /**
     * 查询基地管理自我发展
     * 
     * @param id 基地管理自我发展ID
     * @return 基地管理自我发展
     */
    public TsbzJdglzwfz selectTsbzJdglzwfzById(Long id);

    /**
     * 查询基地管理自我发展列表
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 基地管理自我发展集合
     */
    public List<TsbzJdglzwfz> selectTsbzJdglzwfzList(TsbzJdglzwfz tsbzJdglzwfz);

    /**
     * 新增基地管理自我发展
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 结果
     */
    public int insertTsbzJdglzwfz(TsbzJdglzwfz tsbzJdglzwfz);

    /**
     * 修改基地管理自我发展
     * 
     * @param tsbzJdglzwfz 基地管理自我发展
     * @return 结果
     */
    public int updateTsbzJdglzwfz(TsbzJdglzwfz tsbzJdglzwfz);

    /**
     * 删除基地管理自我发展
     * 
     * @param id 基地管理自我发展ID
     * @return 结果
     */
    public int deleteTsbzJdglzwfzById(Long id);

    /**
     * 批量删除基地管理自我发展
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJdglzwfzByIds(Long[] ids);
}
