package com.ruoyi.qtjs.mapper;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzQtjspxfa;

/**
 * 群体教师评选方案Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-15
 */
public interface TsbzQtjspxfaMapper {
    /**
     * 查询群体教师评选方案
     *
     * @param id 群体教师评选方案ID
     * @return 群体教师评选方案
     */
    public TsbzQtjspxfa selectTsbzQtjspxfaById(Long id);

    /**
     * 查询群体教师评选方案列表
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 群体教师评选方案集合
     */
    public List<TsbzQtjspxfa> selectTsbzQtjspxfaList(TsbzQtjspxfa tsbzQtjspxfa);

    /**
     * 新增群体教师评选方案
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 结果
     */
    public int insertTsbzQtjspxfa(TsbzQtjspxfa tsbzQtjspxfa);

    /**
     * 修改群体教师评选方案
     *
     * @param tsbzQtjspxfa 群体教师评选方案
     * @return 结果
     */
    public int updateTsbzQtjspxfa(TsbzQtjspxfa tsbzQtjspxfa);

    /**
     * 删除群体教师评选方案
     *
     * @param id 群体教师评选方案ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfaById(Long id);

    /**
     * 批量删除群体教师评选方案
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjspxfaByIds(Long[] ids);
}
