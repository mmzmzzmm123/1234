package com.ruoyi.jyykhgl.service;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyydwjs;

/**
 * 队伍建设（教研员）Service接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface ITsbzJyydwjsService {
    /**
     * 查询队伍建设（教研员）
     *
     * @param id 队伍建设（教研员）ID
     * @return 队伍建设（教研员）
     */
    public TsbzJyydwjs selectTsbzJyydwjsById(Long id);

    /**
     * 查询队伍建设（教研员）列表
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 队伍建设（教研员）集合
     */
    public List<TsbzJyydwjs> selectTsbzJyydwjsList(TsbzJyydwjs tsbzJyydwjs);

    /**
     * 新增队伍建设（教研员）
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 结果
     */
    public int insertTsbzJyydwjs(TsbzJyydwjs tsbzJyydwjs);

    /**
     * 修改队伍建设（教研员）
     *
     * @param tsbzJyydwjs 队伍建设（教研员）
     * @return 结果
     */
    public int updateTsbzJyydwjs(TsbzJyydwjs tsbzJyydwjs);

    /**
     * 批量删除队伍建设（教研员）
     *
     * @param ids 需要删除的队伍建设（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyydwjsByIds(Long[] ids);

    /**
     * 删除队伍建设（教研员）信息
     *
     * @param id 队伍建设（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyydwjsById(Long id);
}
