package com.ruoyi.bk.mapper;

import java.util.List;
import com.ruoyi.bk.domain.ResourcePath;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 资源路径Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ResourcePathMapper extends BaseMapper<ResourcePath>{
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
     * 删除资源路径
     *
     * @param id 资源路径主键
     * @return 结果
     */
    public int deleteResourcePathById(Long id);

    /**
     * 批量删除资源路径
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourcePathByIds(Long[] ids);
}
