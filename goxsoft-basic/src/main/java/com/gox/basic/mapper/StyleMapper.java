package com.gox.basic.mapper;

import com.gox.basic.domain.form.Style;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface StyleMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Style selectStyleById(Long id);

    public Style selectStyleByItemId(Long itemId);

    /**
     * 查询  列表
     *
     * @param style
     * @return   集合
     */
    public List<Style> selectStyleList(Style style);

    /**
     * 新增
     *
     * @param style
     * @return 结果
     */
    public int insertStyle(Style style);

    /**
     * 修改
     *
     * @param style
     * @return 结果
     */
    public int updateStyle(Style style);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteStyleById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStyleByIds(Long[] ids);
}
