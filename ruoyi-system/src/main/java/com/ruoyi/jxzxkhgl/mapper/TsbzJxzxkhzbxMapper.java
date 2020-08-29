package com.ruoyi.jxzxkhgl.mapper;

import java.util.List;
import com.ruoyi.jxzxkhgl.domain.TsbzJxzxkhzbx;

/**
 * 考核指标项Mapper接口
 * 
 * @author ruoyi
 * @date 2020-08-29
 */
public interface TsbzJxzxkhzbxMapper 
{
    /**
     * 查询考核指标项
     * 
     * @param id 考核指标项ID
     * @return 考核指标项
     */
    public TsbzJxzxkhzbx selectTsbzJxzxkhzbxById(Long id);

    /**
     * 查询考核指标项列表
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 考核指标项集合
     */
    public List<TsbzJxzxkhzbx> selectTsbzJxzxkhzbxList(TsbzJxzxkhzbx tsbzJxzxkhzbx);

    /**
     * 新增考核指标项
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 结果
     */
    public int insertTsbzJxzxkhzbx(TsbzJxzxkhzbx tsbzJxzxkhzbx);

    /**
     * 修改考核指标项
     * 
     * @param tsbzJxzxkhzbx 考核指标项
     * @return 结果
     */
    public int updateTsbzJxzxkhzbx(TsbzJxzxkhzbx tsbzJxzxkhzbx);

    /**
     * 删除考核指标项
     * 
     * @param id 考核指标项ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhzbxById(Long id);

    /**
     * 批量删除考核指标项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxkhzbxByIds(Long[] ids);
}
