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


    /**
     * 更新微信展示状态
     * @param wxShow
     * @param ids
     * @return
     */
    public int updateWxshowByIds(@Param("wxShow")Integer wxShow, @Param("array") Long[] ids);

    /**
     * 查询阿里云的视频ID，包含已删除的
     * @param ids
     * @return
     */
    public List<String> getVideoIdByIds(@Param("array") Long[] ids);

    /**
     * 更新视频播放量
     * @return
     */
    public int updateVideoPlayNum(@Param("videoId")String videoId);

    /**
     * 根据视频分类ID查询该分类下存在视频数量
     * @param cateId
     * @return
     */
    public int getVideoCountByCateId(@Param("cateId")Long cateId);
}