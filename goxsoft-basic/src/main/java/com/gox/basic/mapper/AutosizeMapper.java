package com.gox.basic.mapper;

import com.gox.basic.domain.form.Autosize;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface AutosizeMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public Autosize selectAutosizeById(Long id);

    public Autosize selectAutosizeByItemId(Long itemId);

    /**
     * 查询  列表
     *
     * @param autosize
     * @return   集合
     */
    public List<Autosize> selectAutosizeList(Autosize autosize);

    /**
     * 新增
     *
     * @param autosize
     * @return 结果
     */
    public int insertAutosize(Autosize autosize);

    /**
     * 修改
     *
     * @param autosize
     * @return 结果
     */
    public int updateAutosize(Autosize autosize);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteAutosizeById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAutosizeByIds(Long[] ids);
}
