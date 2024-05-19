package com.onethinker.bk.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.Sort;
import com.onethinker.bk.mapper.SortMapper;
import com.onethinker.bk.service.ILabelService;
import com.onethinker.bk.service.ISortService;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 分类Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements ISortService {
    @Resource
    private SortMapper sortMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ILabelService labelService;

    private static final String REDIS_KEY = "sort";

    /**
     * 查询分类列表
     *
     * @param sort 分类
     * @return 分类
     */
    @Override
    public List<Sort> selectSortList(Sort sort) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + JSON.toJSONString(sort);
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        List<Sort> sorts = sortMapper.selectSortList(sort);
        if (sorts.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey, sorts);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return sorts;
    }

    /**
     * 新增分类
     *
     * @param sort 分类
     * @return 结果
     */
    @Override
    public int insertSort(Sort sort) {
        sort.existsParams();
        int i = sortMapper.insertSort(sort);
        // 清除缓存信息
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 修改分类
     *
     * @param sort 分类
     * @return 结果
     */
    @Override
    public int updateSort(Sort sort) {
        int i = sortMapper.updateSort(sort);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 删除分类信息
     *
     * @param id 分类主键
     * @return 结果
     */
    @Override
    public int deleteSortById(Long id) {
        int i = sortMapper.deleteSortById(id);
        // 清除缓存
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public List<Sort> getSortInfo() {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        List<Sort> sorts = sortMapper.selectSortList(new Sort());
        if (sorts.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey, sorts);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return sorts;
    }

    @Override
    public Map<String, Object> listSortAndLabel() {
        Map<String, Object> map = new HashMap<>();
        map.put("sorts", getSortInfo());
        map.put("labels", labelService.getLabelInfo());
        return map;
    }
}
