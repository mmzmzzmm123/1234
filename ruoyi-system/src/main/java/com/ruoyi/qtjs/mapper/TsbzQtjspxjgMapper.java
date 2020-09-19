package com.ruoyi.qtjs.mapper;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzQtjspxjg;

/**
 * 群体教师评选结果Mapper接口
 *
 * @author ruoyi
 * @date 2020-09-16
 */
public interface TsbzQtjspxjgMapper {
    /**
     * 查询群体教师评选结果
     *
     * @param id 群体教师评选结果ID
     * @return 群体教师评选结果
     */
    public TsbzQtjspxjg selectTsbzQtjspxjgById(Long id);

    /**
     * 查询群体教师评选结果列表
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 群体教师评选结果集合
     */
    public List<TsbzQtjspxjg> selectTsbzQtjspxjgList(TsbzQtjspxjg tsbzQtjspxjg);

    /**
     * 新增群体教师评选结果
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 结果
     */
    public int insertTsbzQtjspxjg(TsbzQtjspxjg tsbzQtjspxjg);

    /**
     * 修改群体教师评选结果
     *
     * @param tsbzQtjspxjg 群体教师评选结果
     * @return 结果
     */
    public int updateTsbzQtjspxjg(TsbzQtjspxjg tsbzQtjspxjg);

    /**
     * 删除群体教师评选结果
     *
     * @param id 群体教师评选结果ID
     * @return 结果
     */
    public int deleteTsbzQtjspxjgById(Long id);

    /**
     * 批量删除群体教师评选结果
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzQtjspxjgByIds(Long[] ids);

    /**
     * 查询群体教师考核名单列表
     *
     * @param tsbzQtjspxjg 见习之星名单
     * @return 见习之星名单集合
     */
    public List<TsbzQtjspxjg> selectTsbzQtjspxjgKhjdList(TsbzQtjspxjg tsbzQtjspxjg);
}
