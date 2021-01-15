package com.ruoyi.jyykhgl.mapper;

import java.util.List;

import com.ruoyi.jyykhgl.domain.TsbzJyyyjhd;

/**
 * 研究活动（教研员）Mapper接口
 *
 * @author tsbz
 * @date 2021-01-15
 */
public interface TsbzJyyyjhdMapper {
    /**
     * 查询研究活动（教研员）
     *
     * @param id 研究活动（教研员）ID
     * @return 研究活动（教研员）
     */
    public TsbzJyyyjhd selectTsbzJyyyjhdById(Long id);

    /**
     * 查询研究活动（教研员）列表
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 研究活动（教研员）集合
     */
    public List<TsbzJyyyjhd> selectTsbzJyyyjhdList(TsbzJyyyjhd tsbzJyyyjhd);

    /**
     * 新增研究活动（教研员）
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 结果
     */
    public int insertTsbzJyyyjhd(TsbzJyyyjhd tsbzJyyyjhd);

    /**
     * 修改研究活动（教研员）
     *
     * @param tsbzJyyyjhd 研究活动（教研员）
     * @return 结果
     */
    public int updateTsbzJyyyjhd(TsbzJyyyjhd tsbzJyyyjhd);

    /**
     * 删除研究活动（教研员）
     *
     * @param id 研究活动（教研员）ID
     * @return 结果
     */
    public int deleteTsbzJyyyjhdById(Long id);

    /**
     * 批量删除研究活动（教研员）
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJyyyjhdByIds(Long[] ids);
}
