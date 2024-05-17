package com.onethinker.mapper;

import java.util.List;

import com.onethinker.domain.FileRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    public FileRelation selectFileRelationById(String id);

    /**
     * 查询文件关联信息列表
     *
     * @param fileRelation 文件关联信息
     * @return 文件关联信息集合
     */
    public List<FileRelation> selectFileRelationList(FileRelation fileRelation);

    /**
     * 新增文件关联信息
     *
     * @param fileRelation 文件关联信息
     * @return 结果
     */
    public int insertFileRelation(FileRelation fileRelation);

    /**
     * 修改文件关联信息
     *
     * @param fileRelation 文件关联信息
     * @return 结果
     */
    public int updateFileRelation(FileRelation fileRelation);

    /**
     * 删除文件关联信息
     *
     * @param id 文件关联信息主键
     * @return 结果
     */
    public int deleteFileRelationById(String id);

    /**
     * 批量删除文件关联信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFileRelationByIds(String[] ids);
}
