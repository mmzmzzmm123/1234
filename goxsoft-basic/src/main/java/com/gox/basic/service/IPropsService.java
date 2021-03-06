package com.gox.basic.service;

import com.gox.basic.domain.form.Props;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IPropsService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Props selectPropsById(Long id);

    /**
     * 查询  列表
     *
     * @param props
     * @return   集合
     */
    public List<Props> selectPropsList(Props props);

    /**
     * 新增
     *
     * @param props
     * @return 结果
     */
    public int insertProps(Props props);

    /**
     * 修改
     *
     * @param props
     * @return 结果
     */
    public int updateProps(Props props);

    /**
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deletePropsByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deletePropsById(Long id);
}
