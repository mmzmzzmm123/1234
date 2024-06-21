package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.OrderAssignmentImages;

/**
 * 接单者图片存储Service接口
 * 
 * @author ruoyi
 * @date 2024-06-21
 */
public interface IOrderAssignmentImagesService 
{
    /**
     * 查询接单者图片存储
     * 
     * @param imageId 接单者图片存储主键
     * @return 接单者图片存储
     */
    public OrderAssignmentImages selectOrderAssignmentImagesByImageId(Long imageId);

    /**
     * 查询接单者图片存储列表
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 接单者图片存储集合
     */
    public List<OrderAssignmentImages> selectOrderAssignmentImagesList(OrderAssignmentImages orderAssignmentImages);

    /**
     * 新增接单者图片存储
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 结果
     */
    public int insertOrderAssignmentImages(OrderAssignmentImages orderAssignmentImages);

    /**
     * 修改接单者图片存储
     * 
     * @param orderAssignmentImages 接单者图片存储
     * @return 结果
     */
    public int updateOrderAssignmentImages(OrderAssignmentImages orderAssignmentImages);

    /**
     * 批量删除接单者图片存储
     * 
     * @param imageIds 需要删除的接单者图片存储主键集合
     * @return 结果
     */
    public int deleteOrderAssignmentImagesByImageIds(Long[] imageIds);

    /**
     * 删除接单者图片存储信息
     * 
     * @param imageId 接单者图片存储主键
     * @return 结果
     */
    public int deleteOrderAssignmentImagesByImageId(Long imageId);
}
