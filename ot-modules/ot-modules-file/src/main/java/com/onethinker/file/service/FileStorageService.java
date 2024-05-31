package com.onethinker.file.service;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.onethinker.common.config.OnethinkerConfig;
import com.onethinker.common.core.redis.RedisCache;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.enums.FileRealtionStatusEnum;
import com.onethinker.common.enums.FileRelationTypeEnum;
import com.onethinker.common.utils.DateUtils;
import com.onethinker.common.utils.Tools;
import com.onethinker.common.utils.bean.BeanUtils;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.domain.FileRelation;
import com.onethinker.file.dto.FileInfoDTO;
import com.onethinker.file.mapper.FileMapper;
import com.onethinker.file.mapper.FileRelationMapper;
import com.onethinker.file.platform.FileStorage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yangyouqi
 * @date 2024/5/14
 * 用来处理文件存储，对接多个平台
 */
@Slf4j
public class FileStorageService {

    @Setter
    private CopyOnWriteArrayList<FileStorage> fileStorageList;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileRelationMapper relationMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private OnethinkerConfig config;

    private final String redisKey = CacheEnum.QUERY_FILE_INFO_KEY.getCode();

    /**
     * 获取默认的存储平台
     */
    public <T extends FileStorage> T getFileStorage() {
        return this.getFileStorage(config.fileStorage.defaultPlatform);
    }

    /**
     * 获取对应的存储平台
     */
    private  <T extends FileStorage> T getFileStorage(String platform) {
        for (FileStorage fileStorage : fileStorageList) {
            if (fileStorage.getPlatform().equals(platform)) {
                return Tools.cast(fileStorage);
            }
        }
        return null;
    }

    /**
     * 获取对应的存储平台，如果存储平台不存在则抛出异常
     */
    public <T extends FileStorage> T getFileStorageVerify(String platform) {
        T fileStorage = this.getFileStorage(platform);
        if (fileStorage == null) {
            throw new RuntimeException(StrUtil.format("没有找到对应的存储平台！platform:{}", platform));
        }
        return fileStorage;
    }

    /**
     * 保存实体
     *
     * @param fileRelation 文件关联信息对象
     * @param fileInfo 文件内容
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveFileInfo(FileRelation fileRelation, FileInfo fileInfo) {
        fileRelation.setCreateTime(DateUtils.getNowDate());
        fileRelation.setStatus(FileRealtionStatusEnum.INIT.getCode());
        fileRelation.setCreateUserId(fileInfo.getCreateUserId());
        fileInfo.setCreateTime(DateUtils.getNowDate());
        fileMapper.insert(fileInfo);
        relationMapper.insert(fileRelation);
    }
    /**
     * 更新文件状态
     */
    public void updateFileRegionStatusByFileId(String id, FileRealtionStatusEnum orgStatus, FileRealtionStatusEnum updateStatus) {
        relationMapper.updateFileRegionStatusByFileId(id,orgStatus.getCode(),updateStatus.getCode());
    }

    /**
     * 根据文件类型获取有效文件数据
     */
    public List<FileInfoDTO> selectFileList(FileRelationTypeEnum typeEnum,FileRealtionStatusEnum statusEnum) {
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
        fileInfoDTO.setRelationType(typeEnum);
        fileInfoDTO.setRelationStatus(statusEnum);
        return this.selectFileList(fileInfoDTO);
    }

    /**
     * 获取 文件集合信息
     * @param reqDTO fileInfo
     * @return 返回文件信息
     */
    public List<FileInfoDTO> selectFileList(FileInfoDTO reqDTO) {
        if (Objects.nonNull(reqDTO.getActivityId()) || Objects.nonNull(reqDTO.getRelationType())) {
            LambdaQueryWrapper<FileRelation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Objects.nonNull(reqDTO.getRelationType()),FileRelation::getRelationType, reqDTO.getRelationType())
                    .isNotNull(Objects.nonNull(reqDTO.getActivityId()),FileRelation::getAttr)
                    .eq(Objects.nonNull(reqDTO.getId()),FileRelation::getFileId, reqDTO.getId());
            List<FileRelation> fileRelations = relationMapper.selectList(queryWrapper);
            if (fileRelations.isEmpty()) {
                return Lists.newArrayList();
            }
            // 进行过滤数据
            List<String> fileIds = fileRelations.parallelStream().filter(e -> {
                if (Objects.nonNull(reqDTO.getActivityId())) {
                    JSONObject attrObj = JSONObject.parseObject(e.getAttr());
                    return Objects.equals(reqDTO.getActivityId(), attrObj.get("activityId"));
                }
                return true;
            }).map(FileRelation::getFileId).collect(Collectors.toList());

            if (fileIds.isEmpty()) {
                return Lists.newArrayList();
            }
            reqDTO.setIds(fileIds);
        }
        List<FileInfo> fileInfos = fileMapper.selectFileList(reqDTO);

        return fileInfos.parallelStream().map(e -> queryFileByFileId(e.getId(),e)).collect(Collectors.toList());
    }

    /**
     * 查询文件类型
     * @param fileId 文件id
     */
    public FileInfoDTO queryFileByFileId(String fileId,FileInfo... queryFileInfo) {
        Assert.notNull(fileId,"fileId is null");
        String key = redisKey + fileId;
        if (redisCache.hasKey(key)) {
            return redisCache.getCacheObject(key);
        }

        FileInfo fileInfo;
        if (queryFileInfo == null || queryFileInfo.length == 0) {
            fileInfo = fileMapper.selectById(fileId);
            Assert.notNull(fileInfo,"fileInfo is null");
        } else {
            fileInfo = queryFileInfo[0];
        }
        FileInfoDTO result = new FileInfoDTO();
        // 获取文件类型
        BeanUtils.copyProperties(fileInfo,result);
        // 判断来源，取对于的访问地址
        result.setDomain(this.getFileStorageVerify(result.getPlatform()).getDomain());
        // 获取文件分类信息
        LambdaQueryWrapper<FileRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileRelation::getFileId,result.getId());
        FileRelation fileRelation = relationMapper.selectOne(queryWrapper);
        if (Objects.nonNull(fileRelation)) {
            result.setTenantId(fileRelation.getTenantId());
            result.setRelationType(FileRelationTypeEnum.valueOf(fileRelation.getRelationType()));
            // 判断扩展字段是否存在活动id
            if (Objects.nonNull(fileRelation.getAttr())) {
                JSONObject attrObj = JSONObject.parseObject(fileRelation.getAttr());
                result.setActivityId(attrObj.getString("activityId"));
            }
        }
        // 文件一旦上传就不会变更，这里时间可以久点
        redisCache.setCacheObject(key, result,180, TimeUnit.DAYS);
        return result;
    }

    /**
     * 查询文件信息
     * @param fileIds 文件ids
     * @return 返回文件信息
     */
    public Map<String, FileInfoDTO> queryFileByFileIds(String fileIds) {
        Assert.notNull(fileIds,"fileIds is null");
        return Arrays.stream(fileIds.split(",")).collect(Collectors.toMap(Function.identity(),this::queryFileByFileId));
    }
}
