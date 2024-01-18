package com.onethinker.bk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onethinker.bk.domain.Label;
import com.onethinker.bk.mapper.LabelMapper;
import com.onethinker.bk.service.ILabelService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 标签Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {
    @Resource
    private LabelMapper labelMapper;

    @Autowired
    private RedisCache redisCache;

    private static final String REDIS_KEY = "label";

    /**
     * 查询标签
     *
     * @param id 标签主键
     * @return 标签
     */
    @Override
    public Label selectLabelById(Long id) {
        return labelMapper.selectLabelById(id);
    }

    /**
     * 查询标签列表
     *
     * @param label 标签
     * @return 标签
     */
    @Override
    public List<Label> selectLabelList(Label label) {
        return labelMapper.selectLabelList(label);
    }

    /**
     * 新增标签
     *
     * @param label 标签
     * @return 结果
     */
    @Override
    public int insertLabel(Label label) {
        label.existsParams();
        int i = labelMapper.insertLabel(label);
        // 删除缓存
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 修改标签
     *
     * @param label 标签
     * @return 结果
     */
    @Override
    public int updateLabel(Label label) {
        int i = labelMapper.updateLabel(label);
        // 删除缓存
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    /**
     * 删除标签信息
     *
     * @param id 标签主键
     * @return 结果
     */
    @Override
    public int deleteLabelById(Long id) {
        int i = labelMapper.deleteLabelById(id);
        // 删除缓存
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public List<Label> getLabelInfo() {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY;
        if (redisCache.hasKey(redisKey)) {
            return redisCache.getCacheList(redisKey);
        }
        List<Label> labels = labelMapper.selectLabelList(new Label());
        if (labels.isEmpty()) {
            return Lists.newArrayList();
        }
        redisCache.setCacheList(redisKey, labels);
        redisCache.expire(redisKey, 1, TimeUnit.DAYS);
        return labels;
    }
}
