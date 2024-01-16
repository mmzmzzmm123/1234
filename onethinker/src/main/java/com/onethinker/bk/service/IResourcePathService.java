package com.onethinker.bk.service;

import java.util.List;
import com.onethinker.bk.domain.ResourcePath;

/**
 * 资源路径Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IResourcePathService {
    /**
     * 查询资源路径
     *
     * @param id 资源路径主键
     * @return 资源路径
     */
    public ResourcePath selectResourcePathById(Long id);

    /**
     * 查询资源路径列表
     *
     * @param resourcePath 资源路径
     * @return 资源路径集合
     */
    public List<ResourcePath> selectResourcePathList(ResourcePath resourcePath);

    /**
     * 新增资源路径
     *
     * @param resourcePath 资源路径
     * @return 结果
     */
    public int insertResourcePath(ResourcePath resourcePath);

    /**
     * 修改资源路径
     *
     * @param resourcePath 资源路径
     * @return 结果
     */
    public int updateResourcePath(ResourcePath resourcePath);

    /**
     * 批量删除资源路径
     *
     * @param ids 需要删除的资源路径主键集合
     * @return 结果
     */
    public int deleteResourcePathByIds(Long[] ids);

    /**
     * 删除资源路径信息
     *
     * @param id 资源路径主键
     * @return 结果
     */
    public int deleteResourcePathById(Long id);
}
