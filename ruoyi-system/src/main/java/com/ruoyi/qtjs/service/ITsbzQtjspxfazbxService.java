package com.ruoyi.qtjs.service;

import java.util.List;
import com.ruoyi.qtjs.domain.TsbzQtjspxfazbx;

/**
 * 群体教师评选方案指标Service接口
 * 
 * @author ruoyi
 * @date 2020-09-16
 */
public interface ITsbzQtjspxfazbxService 
{
    /**
     * 查询群体教师评选方案指标
     * 
     * @param id 群体教师评选方案指标ID
     * @return 群体教师评选方案指标
     */
    public TsbzQtjspxfazbx selectTsbzQtjspxfazbxById(Long id);

    /**
     * 查询群体教师评选方案指标列表
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 群体教师评选方案指标集合
     */
    public List<TsbzQtjspxfazbx> selectTsbzQtjspxfazbxList(TsbzQtjspxfazbx tsbzQtjspxfazbx);

    /**
     * 新增群体教师评选方案指标
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    public int insertTsbzQtjspxfazbx(TsbzQtjspxfazbx tsbzQtjspxfazbx);

    /**
     * 修改群体教师评选方案指标
     * 
     * @param tsbzQtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    public int updateTsbzQtjspxfazbx(TsbzQtjspxfazbx tsbzQtjspxfazbx);

    /**
     * 批量删除群体教师评选方案指标
     * 
     * @param ids 需要删除的群体教师评选方案指标ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfazbxByIds(Long[] ids);

    /**
     * 删除群体教师评选方案指标信息
     * 
     * @param id 群体教师评选方案指标ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfazbxById(Long id);
}
