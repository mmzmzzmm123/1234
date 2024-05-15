package com.onethinker.bk.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.onethinker.bk.domain.ResourcePath;
import com.onethinker.bk.mapper.ResourcePathMapper;
import com.onethinker.bk.service.IResourcePathService;
import com.onethinker.bk.vo.BaseRequestVO;
import com.onethinker.bk.vo.ResourcePathVO;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 资源路径Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class ResourcePathServiceImpl extends ServiceImpl<ResourcePathMapper, ResourcePath> implements IResourcePathService {
    @Resource
    private ResourcePathMapper resourcePathMapper;

    @Autowired
    private RedisCache redisCache;

    private static final String REDIS_KEY = "resourcePath";

    /**
     * 新增资源路径
     *
     * @param resourcePathVO 资源路径
     * @return 结果
     */
    @Override
    public int insertResourcePath(ResourcePathVO resourcePathVO, String resourceType) {
        resourcePathVO.existsParams(resourceType);
        ResourcePath resourcePath = new ResourcePath();
        BeanUtils.copyProperties(resourcePathVO, resourcePath);
        resourcePath.setCreateTime(DateUtils.getNowDate());
        int i = resourcePathMapper.insertResourcePath(resourcePath);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 修改资源路径
     *
     * @param resourcePathVO 资源路径
     * @return 结果
     */
    @Override
    public int updateResourcePath(ResourcePathVO resourcePathVO) {
        resourcePathVO.existsParams(ResourcePathVO.UPDATE_RESOURCE_PATH);
        ResourcePath resourcePath = new ResourcePath();
        BeanUtils.copyProperties(resourcePathVO, resourcePath);
        int i = resourcePathMapper.updateById(resourcePath);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 删除资源路径信息
     *
     * @param id 资源路径主键
     * @return 结果
     */
    @Override
    public int deleteResourcePathById(Long id) {
        int i = resourcePathMapper.deleteResourcePathById(id);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public Map<String, List<ResourcePathVO>> queryResourcePathByType(String resourceType) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + resourceType;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheMap(redisKey);
        }
        LambdaQueryWrapper<ResourcePath> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResourcePath::getType, resourceType)
                .eq(ResourcePath::getStatus, Boolean.TRUE)
                .orderByAsc(ResourcePath::getCreateTime);
        List<ResourcePath> resourcePaths = resourcePathMapper.selectList(queryWrapper);

        Map<String, List<ResourcePathVO>> result = resourcePaths.stream().map(resourcePath -> {
            ResourcePathVO resourcePathVO = new ResourcePathVO();
            BeanUtils.copyProperties(resourcePath, resourcePathVO);
            return resourcePathVO;
        }).collect(Collectors.groupingBy(ResourcePathVO::getClassify));

        if (result.isEmpty()) {
            return Maps.newHashMap();
        }
        redisCache.setCacheMap(redisKey,result);
        redisCache.expire(redisKey,1, TimeUnit.DAYS);
        return result;
    }

    @Override
    public List<ResourcePath> listResourcePath(BaseRequestVO baseRequestVO) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + JSONObject.toJSONString(baseRequestVO);
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        LambdaQueryWrapper<ResourcePath> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(baseRequestVO.getResourceType()), ResourcePath::getType, baseRequestVO.getResourceType())
                .eq(StringUtils.hasText(baseRequestVO.getClassify()), ResourcePath::getClassify, baseRequestVO.getClassify())
                .eq(ResourcePath::getStatus, Boolean.TRUE)
                .orderByAsc(ResourcePath::getCreateTime);

        List<ResourcePath> resourcePaths = resourcePathMapper.selectList(queryWrapper);
        if (resourcePaths.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey,resourcePaths);
        redisCache.expire(redisKey,1,TimeUnit.DAYS);
        return resourcePaths;
    }

    @Override
    public List<Map<String, Object>> queryClassifyByType(String type) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "list_" + type;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        LambdaQueryWrapper<ResourcePath> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ResourcePath::getStatus, Boolean.TRUE)
                .eq(ResourcePath::getType, type);
        List<ResourcePath> resourcePaths = resourcePathMapper.selectList(queryWrapper);

        Map<String, Long> collect = resourcePaths.stream().collect(Collectors.groupingBy(ResourcePath::getClassify, Collectors.counting()));

        if (collect.isEmpty()) {
            return Lists.newArrayList();
        }

        List list = new ArrayList<>(collect.entrySet());
        redisCache.setCacheList(redisKey,list);
        redisCache.expire(redisKey,1,TimeUnit.DAYS);
        return list;
    }
}
