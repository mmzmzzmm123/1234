package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysNutritionalVideo;
import org.apache.ibatis.annotations.Param;

/**
 * 营养视频Mapper接口
 *
 * @author xzj
 * @date 2021-04-29
 */
public interface SysNutritionalVideoMapper
{
    /**
     * 查询营养视频
     *
     * @param id 营养视频ID
     * @return 营养视频
     */
    public SysNutritionalVideo selectSysNutritionalVideoById(Long id);

    /**
     * 查询营养视频列表
     *
     * @param sysNutritionalVideo 营养视频
     * @return 营养视频集合
     */
    public List<SysNutritionalVideo> selectSysNutritionalVideoList(SysNutritionalVideo sysNutritionalVideo);

    /**
     * 新增营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    public int insertSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo);

    /**
     * 修改营养视频
     *
     * @param sysNutritionalVideo 营养视频
     * @return 结果
     */
    public int updateSysNutritionalVideo(SysNutritionalVideo sysNutritionalVideo);

    /**
     * 删除营养视频
     *
     * @param id 营养视频ID
     * @return 结果
     */
    public int deleteSysNutritionalVideoById(Long id);

    /**
     * 批量删除营养视频
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysNutritionalVideoByIds(Long[] ids);

    /**
     * 获取视频
     * @param videoId
     * @return
     */
    public SysNutritionalVideo selectSysNutritionalVideByVideoId(@Param("videoId")String videoId);
}