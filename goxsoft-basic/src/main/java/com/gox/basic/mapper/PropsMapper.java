package com.gox.basic.mapper;

import com.gox.basic.domain.form.Props;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface PropsMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Props selectPropsById(Long id);

    public Props selectPropsByItemId(Long itemId);

    /**
     * @param id
     * @return
     */
    public Props selectPropsByProId(Long id);

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
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deletePropsById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePropsByIds(Long[] ids);
}
