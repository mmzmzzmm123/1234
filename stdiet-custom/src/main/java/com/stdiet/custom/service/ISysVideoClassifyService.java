package com.stdiet.custom.service;

import java.util.List;
import com.stdiet.custom.domain.SysVideoClassify;
import com.stdiet.custom.dto.response.VideoClassifyResponse;

/**
 * 视频分类Service接口
 *
 * @author xzj
 * @date 2021-05-05
 */
public interface ISysVideoClassifyService
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
     * 批量删除视频分类
     *
     * @param ids 需要删除的视频分类ID
     * @return 结果
     */
    public int deleteSysVideoClassifyByIds(Long[] ids);

    /**
     * 删除视频分类信息
     *
     * @param id 视频分类ID
     * @return 结果
     */
    public int deleteSysVideoClassifyById(Long id);

    /**
     * 获取所有类别
     * @return
     */
    public List<SysVideoClassify> getAllClassify(SysVideoClassify sysVideoClassify);

    /**
     * 按分类层级分类，返回视频目录列表
     *
     * @return 视频分类集合
     */
    public List<SysVideoClassify> getAllClassifyAndVideo(Long parentId);
}