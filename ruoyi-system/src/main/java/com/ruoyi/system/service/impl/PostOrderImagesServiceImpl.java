package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PostOrderImagesMapper;
import com.ruoyi.system.domain.PostOrderImages;
import com.ruoyi.system.service.IPostOrderImagesService;

/**
 * 发单图片Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
@Service
public class PostOrderImagesServiceImpl implements IPostOrderImagesService 
{
    @Autowired
    private PostOrderImagesMapper postOrderImagesMapper;

    /**
     * 查询发单图片
     * 
     * @param imageId 发单图片主键
     * @return 发单图片
     */
    @Override
    public PostOrderImages selectPostOrderImagesByImageId(Long imageId)
    {
        return postOrderImagesMapper.selectPostOrderImagesByImageId(imageId);
    }

    /**
     * 查询发单图片列表
     * 
     * @param postOrderImages 发单图片
     * @return 发单图片
     */
    @Override
    public List<PostOrderImages> selectPostOrderImagesList(PostOrderImages postOrderImages)
    {
        return postOrderImagesMapper.selectPostOrderImagesList(postOrderImages);
    }

    /**
     * 新增发单图片
     * 
     * @param postOrderImages 发单图片
     * @return 结果
     */
    @Override
    public int insertPostOrderImages(PostOrderImages postOrderImages)
    {
        return postOrderImagesMapper.insertPostOrderImages(postOrderImages);
    }

    /**
     * 修改发单图片
     * 
     * @param postOrderImages 发单图片
     * @return 结果
     */
    @Override
    public int updatePostOrderImages(PostOrderImages postOrderImages)
    {
        return postOrderImagesMapper.updatePostOrderImages(postOrderImages);
    }

    /**
     * 批量删除发单图片
     * 
     * @param imageIds 需要删除的发单图片主键
     * @return 结果
     */
    @Override
    public int deletePostOrderImagesByImageIds(Long[] imageIds)
    {
        return postOrderImagesMapper.deletePostOrderImagesByImageIds(imageIds);
    }

    /**
     * 删除发单图片信息
     * 
     * @param imageId 发单图片主键
     * @return 结果
     */
    @Override
    public int deletePostOrderImagesByImageId(Long imageId)
    {
        return postOrderImagesMapper.deletePostOrderImagesByImageId(imageId);
    }
}
