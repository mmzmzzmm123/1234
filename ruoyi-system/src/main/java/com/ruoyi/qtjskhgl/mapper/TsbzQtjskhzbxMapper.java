package com.ruoyi.qtjskhgl.mapper;

import java.util.List;
import com.ruoyi.qtjskhgl.domain.TsbzQtjskhzbx;

/**
 * 群体教师考核指标项Mapper接口
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public interface TsbzQtjskhzbxMapper 
{
    /**
     * 查询群体教师考核指标项
     * 
     * @param id 群体教师考核指标项ID
     * @return 群体教师考核指标项
     */
    public TsbzQtjskhzbx selectTsbzQtjskhzbxById(Long id);

    /**
     * 查询群体教师考核指标项列表
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 群体教师考核指标项集合
     */
    public List<TsbzQtjskhzbx> selectTsbzQtjskhzbxList(TsbzQtjskhzbx tsbzQtjskhzbx);

    /**
     * 新增群体教师考核指标项
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 结果
     */
    public int insertTsbzQtjskhzbx(TsbzQtjskhzbx tsbzQtjskhzbx);

    /**
     * 修改群体教师考核指标项
     * 
     * @param tsbzQtjskhzbx 群体教师考核指标项
     * @return 结果
     */
    public int updateTsbzQtjskhzbx(TsbzQtjskhzbx tsbzQtjskhzbx);

    /**
     * 删除群体教师考核指标项
     * 
     * @param id 群体教师考核指标项ID
     * @return 结果
     */
    public int deleteTsbzQtjskhzbxById(Long id);

    /**
     * 批量删除群体教师考核指标项
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjskhzbxByIds(Long[] ids);
}
