package com.ruoyi.qtjs.service;

import java.util.List;

import com.ruoyi.qtjs.domain.TsbzXxjbxx;

/**
 * 学校信息Service接口
 *
 * @author ruoyi
 * @date 2020-09-14
 */
public interface ITsbzXxjbxxService {
    /**
     * 查询学校信息
     *
     * @param id 学校信息ID
     * @return 学校信息
     */
    public TsbzXxjbxx selectTsbzXxjbxxById(String id);

    /**
     * 查询学校信息列表
     *
     * @param tsbzXxjbxx 学校信息
     * @return 学校信息集合
     */
    public List<TsbzXxjbxx> selectTsbzXxjbxxList(TsbzXxjbxx tsbzXxjbxx);

    /**
     * 新增学校信息
     *
     * @param tsbzXxjbxx 学校信息
     * @return 结果
     */
    public int insertTsbzXxjbxx(TsbzXxjbxx tsbzXxjbxx);

    /**
     * 修改学校信息
     *
     * @param tsbzXxjbxx 学校信息
     * @return 结果
     */
    public int updateTsbzXxjbxx(TsbzXxjbxx tsbzXxjbxx);

    /**
     * 批量删除学校信息
     *
     * @param ids 需要删除的学校信息ID
     * @return 结果
     */
    public int deleteTsbzXxjbxxByIds(String[] ids);

    /**
     * 删除学校信息信息
     *
     * @param id 学校信息ID
     * @return 结果
     */
    public int deleteTsbzXxjbxxById(String id);
}
