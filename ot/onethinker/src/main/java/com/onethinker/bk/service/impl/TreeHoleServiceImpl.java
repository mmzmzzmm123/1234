package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.TreeHole;
import com.onethinker.bk.mapper.TreeHoleMapper;
import com.onethinker.bk.service.ITreeHoleService;
import com.onethinker.bk.service.IWebInfoService;
import com.ruoyi.common.constant.BkConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 树洞Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class TreeHoleServiceImpl extends ServiceImpl<TreeHoleMapper, TreeHole> implements ITreeHoleService {
    @Resource
    private TreeHoleMapper treeHoleMapper;

    @Autowired
    private IWebInfoService webInfoService;

    @Autowired
    private RedisCache redisCache;

    private static final String REDIS_KEY = "treeHole";

    /**
     * 新增树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    @Override
    public int insertTreeHole(TreeHole treeHole, String ipAddr) {
        treeHole.existsParams();
        if (!StringUtils.hasText(treeHole.getAvatar())) {
            treeHole.setAvatar(webInfoService.getRandomAvatar(null, ipAddr));
        }
        treeHole.setCreateTime(DateUtils.getNowDate());
        int i = treeHoleMapper.insertTreeHole(treeHole);
        // 删除缓存
        redisCache.deleteObject(CacheEnum.WEB_INFO.getCode() + REDIS_KEY);
        return i;
    }

    /**
     * 修改树洞
     *
     * @param treeHole 树洞
     * @return 结果
     */
    @Override
    public int updateTreeHole(TreeHole treeHole) {
        int i = treeHoleMapper.updateTreeHole(treeHole);
        redisCache.deleteObject(CacheEnum.WEB_INFO.getCode() + REDIS_KEY);
        return i;
    }

    /**
     * 删除树洞信息
     *
     * @param id 树洞主键
     * @return 结果
     */
    @Override
    public int deleteTreeHoleById(Long id) {
        int i = treeHoleMapper.deleteTreeHoleById(id);
        redisCache.deleteObject(CacheEnum.WEB_INFO.getCode() + REDIS_KEY);
        return i;
    }

    @Override
    public List<TreeHole> listTreeHole() {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        List<TreeHole> treeHoles;

        /**
         * 少于200条直接展示，大于200条的随机抽取从第N条开始往后200条
         */
        Integer count = new LambdaQueryChainWrapper<>(treeHoleMapper).count();
        if (count > BkConstants.TREE_HOLE_COUNT) {
            int i = new Random().nextInt(count + 1 - BkConstants.TREE_HOLE_COUNT);
            treeHoles = treeHoleMapper.queryAllByLimit(i, BkConstants.TREE_HOLE_COUNT);
        } else {
            treeHoles = new LambdaQueryChainWrapper<>(treeHoleMapper).list();
        }

        List<TreeHole> result = treeHoles.stream().map(treeHole -> {
            if (!StringUtils.hasText(treeHole.getAvatar())) {
                treeHole.setAvatar(webInfoService.getRandomAvatar(treeHole.getId().toString(), ""));
            }
            return treeHole;
        }).collect(Collectors.toList());

        redisCache.setCacheList(redisKey, result);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return result;
    }
}
