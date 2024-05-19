package com.onethinker.bk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.bk.domain.Sort;

import java.util.List;

/**
 * 分类Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface SortMapper extends BaseMapper<Sort> {
    /**
     * 查询分类
     *
     * @param id 分类主键
     * @return 分类
     */
    public Sort selectSortById(Long id);

    /**
     * 查询分类列表
     *
     * @param sort 分类
     * @return 分类集合
     */
    public List<Sort> selectSortList(Sort sort);

    /**
     * 新增分类
     *
     * @param sort 分类
     * @return 结果
     */
    public int insertSort(Sort sort);

    /**
     * 修改分类
     *
     * @param sort 分类
     * @return 结果
     */
    public int updateSort(Sort sort);

    /**
     * 删除分类
     *
     * @param id 分类主键
     * @return 结果
     */
    public int deleteSortById(Long id);

    /**
     * 批量删除分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSortByIds(Long[] ids);
}
