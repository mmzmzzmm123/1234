package com.ruoyi.gbxxgl.mapper;

import java.util.List;

import com.ruoyi.gbxxgl.domain.TsbzGbryjl;

/**
 * 荣誉奖励(干部管理)Mapper接口
 *
 * @author tsbz
 * @date 2020-12-21
 */
public interface TsbzGbryjlMapper {
    /**
     * 查询荣誉奖励(干部管理)
     *
     * @param id 荣誉奖励(干部管理)ID
     * @return 荣誉奖励(干部管理)
     */
    public TsbzGbryjl selectTsbzGbryjlById(Long id);

    /**
     * 查询荣誉奖励(干部管理)列表
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 荣誉奖励(干部管理)集合
     */
    public List<TsbzGbryjl> selectTsbzGbryjlList(TsbzGbryjl tsbzGbryjl);

    /**
     * 新增荣誉奖励(干部管理)
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 结果
     */
    public int insertTsbzGbryjl(TsbzGbryjl tsbzGbryjl);

    /**
     * 修改荣誉奖励(干部管理)
     *
     * @param tsbzGbryjl 荣誉奖励(干部管理)
     * @return 结果
     */
    public int updateTsbzGbryjl(TsbzGbryjl tsbzGbryjl);

    /**
     * 删除荣誉奖励(干部管理)
     *
     * @param id 荣誉奖励(干部管理)ID
     * @return 结果
     */
    public int deleteTsbzGbryjlById(Long id);

    /**
     * 批量删除荣誉奖励(干部管理)
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzGbryjlByIds(Long[] ids);
}
