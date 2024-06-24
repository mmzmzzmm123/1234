package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.OrderAssignmentImagesMapper;
import com.ruoyi.system.domain.OrderAssignmentImages;
import com.ruoyi.system.service.IOrderAssignmentImagesService;

/**
 * 接单者图片存储Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
@Service
public class OrderAssignmentImagesServiceImpl implements IOrderAssignmentImagesService 
{
    @Autowired
    private OrderAssignmentImagesMapper orderAssignmentImagesMapper;

    /**
     * 查询接单者图片存储
     * 
     * @param imageId 接单者图片存储主键
     * @return 接单者图片存储
     */
    @Override
    public OrderAssignmentImages selectOrderAssignmentImagesByImageId(Long imageId)
    {
        return orderAssignmentImagesMapper.selectOrderAssignmentImagesByImageId(imageId);
    }

    /**
     * 查询接单者图片存储列表
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 接单者图片存储
     */
    @Override
    public List<OrderAssignmentImages> selectOrderAssignmentImagesList(OrderAssignmentImages orderAssignmentImages)
    {
        return orderAssignmentImagesMapper.selectOrderAssignmentImagesList(orderAssignmentImages);
    }

    /**
     * 新增接单者图片存储
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 结果
     */
    @Override
    public int insertOrderAssignmentImages(OrderAssignmentImages orderAssignmentImages)
    {
        return orderAssignmentImagesMapper.insertOrderAssignmentImages(orderAssignmentImages);
    }

    /**
     * 修改接单者图片存储
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 结果
     */
    @Override
    public int updateOrderAssignmentImages(OrderAssignmentImages orderAssignmentImages)
    {
        return orderAssignmentImagesMapper.updateOrderAssignmentImages(orderAssignmentImages);
    }

    /**
     * 批量删除接单者图片存储
     * 
     * @param imageIds 需要删除的接单者图片存储主键
     * @return 结果
     */
    @Override
    public int deleteOrderAssignmentImagesByImageIds(Long[] imageIds)
    {
        return orderAssignmentImagesMapper.deleteOrderAssignmentImagesByImageIds(imageIds);
    }

    /**
     * 删除接单者图片存储信息
     * 
     * @param imageId 接单者图片存储主键
     * @return 结果
     */
    @Override
    public int deleteOrderAssignmentImagesByImageId(Long imageId)
    {
        return orderAssignmentImagesMapper.deleteOrderAssignmentImagesByImageId(imageId);
    }
}
