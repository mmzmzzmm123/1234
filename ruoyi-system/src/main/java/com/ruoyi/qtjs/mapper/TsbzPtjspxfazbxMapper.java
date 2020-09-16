package com.ruoyi.qtjs.mapper;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzPtjspxfazbx;

/**
 * 群体教师评选方案指标Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public interface TsbzPtjspxfazbxMapper {
    /**
     * 查询群体教师评选方案指标
     *
     * @param id 群体教师评选方案指标ID
     * @return 群体教师评选方案指标
     */
    public TsbzPtjspxfazbx selectTsbzPtjspxfazbxById(Long id);

    /**
     * 查询群体教师评选方案指标列表
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 群体教师评选方案指标集合
     */
    public List<TsbzPtjspxfazbx> selectTsbzPtjspxfazbxList(TsbzPtjspxfazbx tsbzPtjspxfazbx);

    /**
     * 新增群体教师评选方案指标
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    public int insertTsbzPtjspxfazbx(TsbzPtjspxfazbx tsbzPtjspxfazbx);

    /**
     * 修改群体教师评选方案指标
     *
     * @param tsbzPtjspxfazbx 群体教师评选方案指标
     * @return 结果
     */
    public int updateTsbzPtjspxfazbx(TsbzPtjspxfazbx tsbzPtjspxfazbx);

    /**
     * 删除群体教师评选方案指标
     *
     * @param id 群体教师评选方案指标ID
     * @return 结果
     */
    public int deleteTsbzPtjspxfazbxById(Long id);

    /**
     * 批量删除群体教师评选方案指标
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzPtjspxfazbxByIds(Long[] ids);
}
