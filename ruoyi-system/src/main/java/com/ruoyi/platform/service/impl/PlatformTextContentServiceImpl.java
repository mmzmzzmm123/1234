package com.ruoyi.platform.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.platform.mapper.PlatformTextContentMapper;
import com.ruoyi.platform.domain.PlatformTextContent;
import com.ruoyi.platform.service.IPlatformTextContentService;

/**
 * 文本内容管理Service业务层处理
 *
 * @author Lam
 * @date 2023-09-13
 */
@Service
@RequiredArgsConstructor
public class PlatformTextContentServiceImpl implements IPlatformTextContentService {

    private final PlatformTextContentMapper platformTextContentMapper;
    private final RedisCache redisCache;

    /**
     * 查询文本内容管理
     *
     * @param id 文本内容管理主键
     * @return 文本内容管理
     */
    @Override
    public PlatformTextContent selectPlatformTextContentById(Long id) {
        return platformTextContentMapper.selectPlatformTextContentById(id);
    }

    /**
     * 查询文本内容管理列表
     *
     * @param platformTextContent 文本内容管理
     * @return 文本内容管理
     */
    @Override
    public List<PlatformTextContent> selectPlatformTextContentList(PlatformTextContent platformTextContent) {
        return platformTextContentMapper.selectPlatformTextContentList(platformTextContent);
    }

    /**
     * 新增文本内容管理
     *
     * @param platformTextContent 文本内容管理
     * @return 结果
     */
    @Override
    public int insertPlatformTextContent(PlatformTextContent platformTextContent) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_TEXT_CONTENT+platformTextContent.getType());
        String loginUser = SecurityUtils.getUsername();
        Date now = DateUtils.getNowDate();
        platformTextContent.setCreateTime(now)
                .setUpdateTime(now)
                .setUpdateBy(loginUser)
                .setCreateBy(loginUser);
        return platformTextContentMapper.insertPlatformTextContent(platformTextContent);
    }

    /**
     * 修改文本内容管理
     *
     * @param platformTextContent 文本内容管理
     * @return 结果
     */
    @Override
    public int updatePlatformTextContent(PlatformTextContent platformTextContent) {
        redisCache.deleteObject(RedisKeyConstants.PLATFORM_TEXT_CONTENT+platformTextContent.getType());
        platformTextContent.setUpdateBy(SecurityUtils.getUsername());
        platformTextContent.setUpdateTime(DateUtils.getNowDate());
        return platformTextContentMapper.updatePlatformTextContent(platformTextContent);
    }

    /**
     * 批量删除文本内容管理
     *
     * @param ids 需要删除的文本内容管理主键
     * @return 结果
     */
    @Override
    public int deletePlatformTextContentByIds(Long[] ids) {
        return platformTextContentMapper.deletePlatformTextContentByIds(ids);
    }

    /**
     * 删除文本内容管理信息
     *
     * @param id 文本内容管理主键
     * @return 结果
     */
    @Override
    public int deletePlatformTextContentById(Long id) {
        return platformTextContentMapper.deletePlatformTextContentById(id);
    }
}
