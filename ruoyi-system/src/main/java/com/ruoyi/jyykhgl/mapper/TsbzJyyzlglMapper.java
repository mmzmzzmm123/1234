package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyyzlgl;

/**
 * 质量管理（教研员）Mapper接口
 *
 * @author tsbz
 * @date 2021-01-16
 */
public interface TsbzJyyzlglMapper {
    /**
     * 查询质量管理（教研员）
     *
     * @param id 质量管理（教研员）ID
     * @return 质量管理（教研员）
     */
    public TsbzJyyzlgl selectTsbzJyyzlglById(Long id);

    /**
     * 查询质量管理（教研员）列表
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 质量管理（教研员）集合
     */
    public List<TsbzJyyzlgl> selectTsbzJyyzlglList(TsbzJyyzlgl tsbzJyyzlgl);

    /**
     * 新增质量管理（教研员）
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 结果
     */
    public int insertTsbzJyyzlgl(TsbzJyyzlgl tsbzJyyzlgl);

    /**
     * 修改质量管理（教研员）
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 结果
     */
    public int updateTsbzJyyzlgl(TsbzJyyzlgl tsbzJyyzlgl);

    /**
     * 删除质量管理（教研员）
     *
     * @param id 质量管理（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyzlglById(Long id);

    /**
     * 批量删除质量管理（教研员）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyyzlglByIds(Long[] ids);
}
