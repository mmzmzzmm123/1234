package com.ruoyi.bk.mapper;

import java.util.List;
import com.ruoyi.bk.domain.BkResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 资源信息Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ResourceMapper extends BaseMapper<BkResource>{
    /**
     * 查询资源信息
     *
     * @param id 资源信息主键
     * @return 资源信息
     */
    public BkResource selectResourceById(Long id);

    /**
     * 查询资源信息列表
     *
     * @param bkResource 资源信息
     * @return 资源信息集合
     */
    public List<BkResource> selectResourceList(BkResource bkResource);

    /**
     * 新增资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    public int insertResource(BkResource bkResource);

    /**
     * 修改资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    public int updateResource(BkResource bkResource);

    /**
     * 删除资源信息
     *
     * @param id 资源信息主键
     * @return 结果
     */
    public int deleteResourceById(Long id);

    /**
     * 批量删除资源信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResourceByIds(Long[] ids);
}
