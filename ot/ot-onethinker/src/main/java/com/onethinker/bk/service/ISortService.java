package com.onethinker.bk.service;

import com.onethinker.bk.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * 分类Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ISortService {

    /**
     * 查询分类列表
     *
     * @param sort 分类
     * @return 分类集合
     */
    List<Sort> selectSortList(Sort sort);

    /**
     * 新增分类
     *
     * @param sort 分类
     * @return 结果
     */
    int insertSort(Sort sort);

    /**
     * 修改分类
     *
     * @param sort 分类
     * @return 结果
     */
    int updateSort(Sort sort);

    /**
     * 删除分类信息
     *
     * @param id 分类主键
     * @return 结果
     */
    int deleteSortById(Long id);

    /**
     * 获取分类标签信息
     * @return
     */
    List<Sort> getSortInfo();

    /**
     * 获取分类表情信息
     *
     * @return
     */
    Map<String, Object> listSortAndLabel();
}
