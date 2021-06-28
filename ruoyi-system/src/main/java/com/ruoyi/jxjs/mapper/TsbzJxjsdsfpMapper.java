package com.ruoyi.jxjs.mapper;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxjsdsfp;

/**
 * 见习教师导师分配Mapper接口
 *
 * @author tsbz
 * @date 2021-06-28
 */
public interface TsbzJxjsdsfpMapper {
    /**
     * 查询见习教师导师分配
     *
     * @param id 见习教师导师分配ID
     * @return 见习教师导师分配
     */
    public TsbzJxjsdsfp selectTsbzJxjsdsfpById(Long id);

    /**
     * 查询见习教师导师分配列表
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 见习教师导师分配集合
     */
    public List<TsbzJxjsdsfp> selectTsbzJxjsdsfpList(TsbzJxjsdsfp tsbzJxjsdsfp);

    /**
     * 新增见习教师导师分配
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 结果
     */
    public int insertTsbzJxjsdsfp(TsbzJxjsdsfp tsbzJxjsdsfp);

    /**
     * 修改见习教师导师分配
     *
     * @param tsbzJxjsdsfp 见习教师导师分配
     * @return 结果
     */
    public int updateTsbzJxjsdsfp(TsbzJxjsdsfp tsbzJxjsdsfp);

    /**
     * 删除见习教师导师分配
     *
     * @param id 见习教师导师分配ID
     * @return 结果
     */
    public int deleteTsbzJxjsdsfpById(Long id);

    /**
     * 批量删除见习教师导师分配
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxjsdsfpByIds(Long[] ids);
}
