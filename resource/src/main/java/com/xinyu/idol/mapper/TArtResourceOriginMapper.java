package com.xinyu.idol.mapper;

import java.util.List;
import com.xinyu.idol.domain.TArtResourceOrigin;

/**
 * 美术资源上传原件Mapper接口
 * 
 * @author liyu
 * @date 2023-10-31
 */
public interface TArtResourceOriginMapper 
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
     * 删除美术资源上传原件
     * 
     * @param id 美术资源上传原件主键
     * @return 结果
     */
    public int deleteTArtResourceOriginById(String id);

    /**
     * 批量删除美术资源上传原件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTArtResourceOriginByIds(String[] ids);
}
