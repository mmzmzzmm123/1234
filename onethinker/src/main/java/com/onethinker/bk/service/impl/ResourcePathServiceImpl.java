package com.onethinker.bk.service.impl;

import java.util.List;
        import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onethinker.bk.mapper.ResourcePathMapper;
import com.onethinker.bk.domain.ResourcePath;
import com.onethinker.bk.service.IResourcePathService;
import lombok.extern.log4j.Log4j2;
import javax.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
/**
 * 资源路径Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ResourcePathServiceImpl extends ServiceImpl<ResourcePathMapper,ResourcePath> implements IResourcePathService {
    @Resource
    private ResourcePathMapper resourcePathMapper;

    /**
     * 查询资源路径
     *
     * @param id 资源路径主键
     * @return 资源路径
     */
    @Override
    public ResourcePath selectResourcePathById(Long id) {
        return resourcePathMapper.selectResourcePathById(id);
    }

    /**
     * 查询资源路径列表
     *
     * @param resourcePath 资源路径
     * @return 资源路径
     */
    @Override
    public List<ResourcePath> selectResourcePathList(ResourcePath resourcePath) {
        return resourcePathMapper.selectResourcePathList(resourcePath);
    }

    /**
     * 新增资源路径
     *
     * @param resourcePath 资源路径
     * @return 结果
     */
    @Override
    public int insertResourcePath(ResourcePath resourcePath) {
                resourcePath.setCreateTime(DateUtils.getNowDate());
            return resourcePathMapper.insertResourcePath(resourcePath);
    }

    /**
     * 修改资源路径
     *
     * @param resourcePath 资源路径
     * @return 结果
     */
    @Override
    public int updateResourcePath(ResourcePath resourcePath) {
        return resourcePathMapper.updateResourcePath(resourcePath);
    }

    /**
     * 批量删除资源路径
     *
     * @param ids 需要删除的资源路径主键
     * @return 结果
     */
    @Override
    public int deleteResourcePathByIds(Long[] ids) {
        return resourcePathMapper.deleteResourcePathByIds(ids);
    }

    /**
     * 删除资源路径信息
     *
     * @param id 资源路径主键
     * @return 结果
     */
    @Override
    public int deleteResourcePathById(Long id) {
        return resourcePathMapper.deleteResourcePathById(id);
    }
}
