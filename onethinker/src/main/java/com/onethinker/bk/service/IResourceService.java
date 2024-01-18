package com.onethinker.bk.service;

import com.onethinker.bk.domain.BkResource;

import java.util.List;

/**
 * 资源信息Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IResourceService {
    /**
     * 查询资源信息
     *
     * @param id 资源信息主键
     * @return 资源信息
     */
    BkResource selectResourceById(Long id);

    /**
     * 查询资源信息列表
     *
     * @param bkResource 资源信息
     * @return 资源信息集合
     */
    List<BkResource> selectResourceList(BkResource bkResource);

    /**
     * 新增资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    int insertResource(BkResource bkResource);

    /**
     * 修改资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    int updateResource(BkResource bkResource);

    /**
     * 批量删除资源信息
     *
     * @param ids 需要删除的资源信息主键集合
     * @return 结果
     */
    int deleteResourceByIds(Long[] ids);

    /**
     * 删除资源信息信息
     *
     * @param id 资源信息主键
     * @return 结果
     */
    int deleteResourceById(Long id);
}
