package com.ruoyi.bk.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.bk.mapper.ResourceMapper;
import com.ruoyi.bk.domain.BkResource;
import com.ruoyi.bk.service.IResourceService;
import lombok.extern.log4j.Log4j2;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * 资源信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, BkResource> implements IResourceService {
    @Resource
    private ResourceMapper resourceMapper;

    /**
     * 查询资源信息
     *
     * @param id 资源信息主键
     * @return 资源信息
     */
    @Override
    public BkResource selectResourceById(Long id) {
        return resourceMapper.selectResourceById(id);
    }

    /**
     * 查询资源信息列表
     *
     * @param bkResource 资源信息
     * @return 资源信息
     */
    @Override
    public List<BkResource> selectResourceList(BkResource bkResource) {
        return resourceMapper.selectResourceList(bkResource);
    }

    /**
     * 新增资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    @Override
    public int insertResource(BkResource bkResource) {
                bkResource.setCreateTime(DateUtils.getNowDate());
            return resourceMapper.insertResource(bkResource);
    }

    /**
     * 修改资源信息
     *
     * @param bkResource 资源信息
     * @return 结果
     */
    @Override
    public int updateResource(BkResource bkResource) {
        return resourceMapper.updateResource(bkResource);
    }

    /**
     * 批量删除资源信息
     *
     * @param ids 需要删除的资源信息主键
     * @return 结果
     */
    @Override
    public int deleteResourceByIds(Long[] ids) {
        return resourceMapper.deleteResourceByIds(ids);
    }

    /**
     * 删除资源信息信息
     *
     * @param id 资源信息主键
     * @return 结果
     */
    @Override
    public int deleteResourceById(Long id) {
        return resourceMapper.deleteResourceById(id);
    }
}
