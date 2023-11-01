package com.xinyu.idol.service;

import java.util.List;
import com.xinyu.idol.domain.TArtResourceOrigin;

/**
 * 美术资源上传原件Service接口
 * 
 * @author liyu
 * @date 2023-10-31
 */
public interface ITArtResourceOriginService 
{
    /**
     * 查询美术资源上传原件
     * 
     * @param id 美术资源上传原件主键
     * @return 美术资源上传原件
     */
    public TArtResourceOrigin selectTArtResourceOriginById(String id);

    /**
     * 查询美术资源上传原件列表
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 美术资源上传原件集合
     */
    public List<TArtResourceOrigin> selectTArtResourceOriginList(TArtResourceOrigin tArtResourceOrigin);

    /**
     * 新增美术资源上传原件
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 结果
     */
    public int insertTArtResourceOrigin(TArtResourceOrigin tArtResourceOrigin);

    /**
     * 修改美术资源上传原件
     * 
     * @param tArtResourceOrigin 美术资源上传原件
     * @return 结果
     */
    public int updateTArtResourceOrigin(TArtResourceOrigin tArtResourceOrigin);

    /**
     * 批量删除美术资源上传原件
     * 
     * @param ids 需要删除的美术资源上传原件主键集合
     * @return 结果
     */
    public int deleteTArtResourceOriginByIds(String[] ids);

    /**
     * 删除美术资源上传原件信息
     * 
     * @param id 美术资源上传原件主键
     * @return 结果
     */
    public int deleteTArtResourceOriginById(String id);
}
