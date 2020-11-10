package com.ruoyi.jxzxkhgl.mapper;

import java.util.List;

import com.ruoyi.jxzxkhgl.domain.TsbzJxzxdsfp;

/**
 * 见习导师分配Mapper接口
 *
 * @author ruoyi
 * @date 2020-11-10
 */
public interface TsbzJxzxdsfpMapper {
    /**
     * 查询见习导师分配
     *
     * @param id 见习导师分配ID
     * @return 见习导师分配
     */
    public TsbzJxzxdsfp selectTsbzJxzxdsfpById(Long id);

    /**
     * 查询见习导师分配列表
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 见习导师分配集合
     */
    public List<TsbzJxzxdsfp> selectTsbzJxzxdsfpList(TsbzJxzxdsfp tsbzJxzxdsfp);

    /**
     * 新增见习导师分配
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 结果
     */
    public int insertTsbzJxzxdsfp(TsbzJxzxdsfp tsbzJxzxdsfp);

    /**
     * 修改见习导师分配
     *
     * @param tsbzJxzxdsfp 见习导师分配
     * @return 结果
     */
    public int updateTsbzJxzxdsfp(TsbzJxzxdsfp tsbzJxzxdsfp);

    /**
     * 删除见习导师分配
     *
     * @param id 见习导师分配ID
     * @return 结果
     */
    public int deleteTsbzJxzxdsfpById(Long id);

    /**
     * 批量删除见习导师分配
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxzxdsfpByIds(Long[] ids);
}
