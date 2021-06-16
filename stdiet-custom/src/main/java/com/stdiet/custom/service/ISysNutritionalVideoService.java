package com.stdiet.custom.service;

import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysNutritionalVideo;
import com.stdiet.custom.domain.SysVideoClassify;
import org.apache.ibatis.annotations.Param;

/**
 * 营养视频Service接口
 *
 * @author xzj
 * @date 2021-04-29
 */
public interface ISysNutritionalVideoService
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
    public List<SysNutritionalVideo> selectSysNutritionalVideoList(SysNutritionalVideo sysNutritionalVideo, boolean flag);

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
     * 批量删除营养视频
     *
     * @param ids 需要删除的营养视频ID
     * @return 结果
     */
    public int deleteSysNutritionalVideoByIds(Long[] ids);

    /**
     * 删除营养视频信息
     *
     * @param id 营养视频ID
     * @return 结果
     */
    public int deleteSysNutritionalVideoById(Long id);

    /**
     * 获取视频
     * @param videoId
     * @return
     */
    public SysNutritionalVideo selectSysNutritionalVideByVideoId(String videoId);

    /**
     * 阿里云视频查询检索
     * @return
     */
    public Map<String,Object> searchVideo(String key, Integer status, Integer pageNo, Integer pageSize, String scrollToken);

    /**
     * 更新微信展示状态
     * @param wxShow
     * @param ids
     * @return
     */
    public int updateWxshowByIds(Integer wxShow, Long[] ids);

    /**
     * 更新视频播放量
     * @return
     */
    public int updateVideoPlayNum(String videoId);
}