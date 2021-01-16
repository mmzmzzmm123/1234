package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyyfwjc;

/**
 * 服务基层（教研员）Mapper接口
 *
 * @author tsbz
 * @date 2021-01-16
 */
public interface TsbzJyyfwjcMapper {
    /**
     * 查询服务基层（教研员）
     *
     * @param id 服务基层（教研员）ID
     * @return 服务基层（教研员）
     */
    public TsbzJyyfwjc selectTsbzJyyfwjcById(Long id);

    /**
     * 查询服务基层（教研员）列表
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 服务基层（教研员）集合
     */
    public List<TsbzJyyfwjc> selectTsbzJyyfwjcList(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 新增服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    public int insertTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 修改服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    public int updateTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc);

    /**
     * 删除服务基层（教研员）
     *
     * @param id 服务基层（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyfwjcById(Long id);

    /**
     * 批量删除服务基层（教研员）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyyfwjcByIds(Long[] ids);
}
