package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.PostOrderImages;

/**
 * 发单图片Service接口
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public interface IPostOrderImagesService 
{
    /**
     * 查询发单图片
     * 
     * @param imageId 发单图片主键
     * @return 发单图片
     */
    public PostOrderImages selectPostOrderImagesByImageId(Long imageId);

    /**
     * 查询发单图片列表
     * 
     * @param postOrderImages 发单图片
     * @return 发单图片集合
     */
    public List<PostOrderImages> selectPostOrderImagesList(PostOrderImages postOrderImages);

    /**
     * 新增发单图片
     * 
     * @param postOrderImages 发单图片
     * @return 结果
     */
    public int insertPostOrderImages(PostOrderImages postOrderImages);

    /**
     * 修改发单图片
     * 
     * @param postOrderImages 发单图片
     * @return 结果
     */
    public int updatePostOrderImages(PostOrderImages postOrderImages);

    /**
     * 批量删除发单图片
     * 
     * @param imageIds 需要删除的发单图片主键集合
     * @return 结果
     */
    public int deletePostOrderImagesByImageIds(Long[] imageIds);

    /**
     * 删除发单图片信息
     * 
     * @param imageId 发单图片主键
     * @return 结果
     */
    public int deletePostOrderImagesByImageId(Long imageId);


}
