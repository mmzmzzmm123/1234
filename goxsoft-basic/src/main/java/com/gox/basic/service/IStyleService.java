package com.gox.basic.service;

import com.gox.basic.domain.form.Style;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IStyleService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Style selectStyleById(Long id);

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
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteStyleByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteStyleById(Long id);
}
