package com.onethinker.bk.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.onethinker.bk.domain.Family;
import com.onethinker.bk.mapper.FamilyMapper;
import com.onethinker.bk.service.IFamilyService;
import com.onethinker.bk.vo.FamilyVO;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 家庭信息Service业务层处理
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
@Service
@Log4j2
public class FamilyServiceImpl extends ServiceImpl<FamilyMapper, Family> implements IFamilyService {
    @Resource
    private FamilyMapper familyMapper;

    @Autowired
    private RedisCache redisCache;

    private static final String REDIS_KEY = "family";

    /**
     * 查询家庭信息
     *
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    @Override
    public Family selectFamilyById(Long id) {
        return familyMapper.selectFamilyById(id);
    }

    /**
     * 查询家庭信息列表
     *
     * @param family 家庭信息
     * @return 家庭信息
     */
    @Override
    public List<Family> selectFamilyList(Family family) {
        return familyMapper.selectFamilyList(family);
    }

    /**
     * 新增家庭信息
     *
     * @param familyVO 家庭信息
     * @return 结果
     */
    @Override
    public int insertFamily(FamilyVO familyVO) {
        Long userId = SecurityUtils.getUserId();
        familyVO.setUserId(userId);

        Family oldFamily = selectFamilyByUserId(userId);
        Family family = new Family();
        BeanUtils.copyProperties(familyVO, family);
        family.setStatus(Boolean.FALSE);
        if (oldFamily != null) {
            family.setId(oldFamily.getId());
            updateById(family);
        } else {
            family.setId(null);
            save(family);
        }
        family.setCreateTime(DateUtils.getNowDate());
        int i = familyMapper.insertFamily(family);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return i;
    }

    @Override
    public Family selectFamilyByUserId(Long userId) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + userId;
        if (!redisCache.hasKey(redisKey)) {
            LambdaQueryWrapper<Family> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Family::getUserId,userId);
            Family family = familyMapper.selectOne(queryWrapper);
            redisCache.setCacheObject(redisKey,family,1, TimeUnit.DAYS);
        }
        return JSON.parseObject(redisCache.getCacheObject(redisKey).toString(),Family.class);
    }

    @Override
    public List<Family> listRandomFamily(Integer size) {
        String redisKey = CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "LIST";
        if (!redisCache.hasKey(redisKey)) {
            List<Family> families = familyMapper.selectFamilyList(new Family());
            if (Objects.isNull(families)) {
                return Lists.newArrayList();
            }
            redisCache.setCacheList(redisKey,families);
            redisCache.expire(redisKey,1,TimeUnit.DAYS);
        }
        List<Family> familyList = redisCache.getCacheList(redisKey);
        if (familyList.size() > size) {
            Collections.shuffle(familyList);
            familyList = familyList.subList(0, size);
        }
        return familyList;
    }

    /**
     * 修改家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    @Override
    public int updateFamily(Family family) {
        family.setUpdateTime(DateUtils.getNowDate());
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return familyMapper.updateFamily(family);
    }

    /**
     * 批量删除家庭信息
     *
     * @param ids 需要删除的家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyByIds(Long[] ids) {
        return familyMapper.deleteFamilyByIds(ids);
    }

    /**
     * 删除家庭信息信息
     *
     * @param id 家庭信息主键
     * @return 结果
     */
    @Override
    public int deleteFamilyById(Long id) {
        Family family = familyMapper.selectFamilyById(id);
        Collection<String> keys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + REDIS_KEY + "*");
        redisCache.deleteObject(keys);
        return familyMapper.deleteFamilyById(id);
    }
}
