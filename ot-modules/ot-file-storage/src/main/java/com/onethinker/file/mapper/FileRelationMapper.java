package com.onethinker.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onethinker.file.domain.FileRelation;

import java.util.List;

/**
 * 文件关联信息Mapper接口
 *
 * @author yangyouqi
 * @date 2024-05-18
 */
public interface FileRelationMapper extends BaseMapper<FileRelation> {
    /**
     * 查询文件关联信息
     *
     * @param id 文件关联信息主键
     * @return 文件关联信息
     */
    FileRelation selectFileRelationById(String id);

    /**
     * 查询文件关联信息列表
     *
     * @param fileRelation 文件关联信息
     * @return 文件关联信息集合
     */
    List<FileRelation> selectFileRelationList(FileRelation fileRelation);

    /**
     * 新增文件关联信息
     *
     * @param fileRelation 文件关联信息
     * @return 结果
     */
    int insertFileRelation(FileRelation fileRelation);

    /**
     * 修改文件关联信息
     *
     * @param fileRelation 文件关联信息
     * @return 结果
     */
    int updateFileRelation(FileRelation fileRelation);

    /**
     * 删除文件关联信息
     *
     * @param id 文件关联信息主键
     * @return 结果
     */
    int deleteFileRelationById(String id);

    /**
     * 批量删除文件关联信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteFileRelationByIds(String[] ids);


    /**
     * 更新文件关联信息状态
     *
     * @param id id
     * @param orgStatus 原状态
     * @param updateStatus 更新后的状态
     */
    void updateFileRegionStatusByFileId(String id, Integer orgStatus, Integer updateStatus);
}
