package com.onethinker.bk.service;

import com.onethinker.bk.domain.ResourcePath;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.ResourcePathVO;

import java.util.List;
import java.util.Map;

/**
 * 资源路径Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface IResourcePathService {

    /**
     * 新增资源路径
     *
     * @param resourcePathVO 资源路径
     * @return 结果
     */
    int insertResourcePath(ResourcePathVO resourcePathVO, String resourceType);

    /**
     * 修改资源路径
     *
     * @param resourcePathVO 资源路径
     * @return 结果
     */
    int updateResourcePath(ResourcePathVO resourcePathVO);

    /**
     * 删除资源路径信息
     *
     * @param id 资源路径主键
     * @return 结果
     */
    int deleteResourcePathById(Long id);

    /**
     * 根据类型查询相关数据
     *
     * @return
     */
    Map<String, List<ResourcePathVO>> queryResourcePathByType(String resourcePathType);

    /**
     * 查询资源信息
     *
     * @param baseRequestVO
     * @return
     */
    List<ResourcePath> listResourcePath(BaseRequestVO baseRequestVO);

    /**
     * 查询相关类型数据
     *
     * @return
     */
    List<Map<String, Object>> queryClassifyByType(String type);
}
