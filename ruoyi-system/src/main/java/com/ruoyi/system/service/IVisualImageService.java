package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.VisualImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片管理。管理上传的图片Service接口
 *
 * @author yangsj
 * @date 2021-10-28
 */
public interface IVisualImageService
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
     * 批量删除图片管理。管理上传的图片
     *
     * @param ids 需要删除的图片管理。管理上传的图片主键集合
     * @return 结果
     */
    public int deleteVisualImageByIds(Long[] ids);

    /**
     * 删除图片管理。管理上传的图片信息
     *
     * @param id 图片管理。管理上传的图片主键
     * @return 结果
     */
    public int deleteVisualImageById(Long id);

    int addImage(MultipartFile file, Integer heigth, Integer width);

    String saveImageToLocal(MultipartFile file);

    int divideImage(VisualImage visualImage);
}
