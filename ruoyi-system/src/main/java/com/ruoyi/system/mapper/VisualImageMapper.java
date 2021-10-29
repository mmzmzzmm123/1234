package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.VisualImage;

/**
 * 图片管理。管理上传的图片Mapper接口
 * 
 * @author yangsj
 * @date 2021-10-28
 */
public interface VisualImageMapper 
{
    /**
     * 查询图片管理。管理上传的图片
     * 
     * @param id 图片管理。管理上传的图片主键
     * @return 图片管理。管理上传的图片
     */
    public VisualImage selectVisualImageById(Long id);

    /**
     * 查询图片管理。管理上传的图片列表
     * 
     * @param visualImage 图片管理。管理上传的图片
     * @return 图片管理。管理上传的图片集合
     */
    public List<VisualImage> selectVisualImageList(VisualImage visualImage);

    /**
     * 新增图片管理。管理上传的图片
     * 
     * @param visualImage 图片管理。管理上传的图片
     * @return 结果
     */
    public int insertVisualImage(VisualImage visualImage);

    /**
     * 修改图片管理。管理上传的图片
     * 
     * @param visualImage 图片管理。管理上传的图片
     * @return 结果
     */
    public int updateVisualImage(VisualImage visualImage);

    /**
     * 删除图片管理。管理上传的图片
     * 
     * @param id 图片管理。管理上传的图片主键
     * @return 结果
     */
    public int deleteVisualImageById(Long id);

    /**
     * 批量删除图片管理。管理上传的图片
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVisualImageByIds(Long[] ids);
}
