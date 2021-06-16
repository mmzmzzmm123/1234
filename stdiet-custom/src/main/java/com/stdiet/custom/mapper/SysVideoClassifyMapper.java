package com.stdiet.custom.mapper;

import java.util.List;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.dto.response.VideoClassifyResponse;

/**
 * 视频分类Mapper接口
 *
 * @author xzj
 * @date 2021-05-05
 */
public interface SysVideoClassifyMapper
{
    /**
     * 查询视频分类
     *
     * @param id 视频分类ID
     * @return 视频分类
     */
    public SysVideoClassify selectSysVideoClassifyById(Long id);

    /**
     * 查询视频分类列表
     *
     * @param sysVideoClassify 视频分类
     * @return 视频分类集合
     */
    public List<SysVideoClassify> selectSysVideoClassifyList(SysVideoClassify sysVideoClassify);

    /**
     * 新增视频分类
     *
     * @param sysVideoClassify 视频分类
     * @return 结果
     */
    public int insertSysVideoClassify(SysVideoClassify sysVideoClassify);

    /**
     * 修改视频分类
     *
     * @param sysVideoClassify 视频分类
     * @return 结果
     */
    public int updateSysVideoClassify(SysVideoClassify sysVideoClassify);

    /**
     * 删除视频分类
     *
     * @param id 视频分类ID
     * @return 结果
     */
    public int deleteSysVideoClassifyById(Long id);

    /**
     * 批量删除视频分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysVideoClassifyByIds(Long[] ids);

    /**
     * 获取所有类别
     * @return
     */
    public List<SysVideoClassify> getAllClassify(SysVideoClassify sysVideoClassify);

    /**
     * 根据父级ID获取下面子分类以及视频
     * @param parentId
     * @return
     */
    public List<VideoClassifyResponse>  getOneAllClassifyAndVideo(SysVideoClassify sysVideoClassify);
}