package com.ruoyi.service.mapper;

import java.util.List;
import com.ruoyi.service.domain.ServiceItem;
import com.ruoyi.service.domain.ServiceItemPrice;

/**
 * 服务子项目Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface ServiceItemMapper {

    /**
     * 查询服务子项目
     *
     * @param id 服务子项目主键
     * @return 服务子项目
     */
    public ServiceItem selectServiceItemById(Long id);

    /**
     * 根据服务标识查询子项目
     * @return 结果
     * */
    public List<ServiceItem> selectJoinPriceByServiceId(Long serviceId);

    /**
     * 查询服务子项目列表
     *
     * @param serviceItem 服务子项目
     * @return 服务子项目集合
     */
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem);

    /**
     * 级联查询服务信息
     *
     * @param serviceItem 服务子项目
     * @return 结果
     * */
    public List<ServiceItem> selectJoinServiceInfo(ServiceItem serviceItem);

    /**
     * 新增服务子项目
     *
     * @param serviceItem 服务子项目
     * @return 结果
     */
    public int insertServiceItem(ServiceItem serviceItem);

    /**
     * 修改服务子项目
     *
     * @param serviceItem 服务子项目
     * @return 结果
     */
    public int updateServiceItem(ServiceItem serviceItem);

    /**
     * 删除服务子项目
     *
     * @param id 服务子项目主键
     * @return 结果
     */
    public int deleteServiceItemById(Long id);

    /**
     * 批量删除服务子项目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServiceItemByIds(Long[] ids);

    /**
     * 批量删除服务子项目价格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServiceItemPriceByServiceItemIds(Long[] ids);

    /**
     * 批量新增服务子项目价格
     *
     * @param serviceItemPriceList 服务子项目价格列表
     * @return 结果
     */
    public int batchServiceItemPrice(List<ServiceItemPrice> serviceItemPriceList);


    /**
     * 通过服务子项目主键删除服务子项目价格信息
     *
     * @param id 服务子项目ID
     * @return 结果
     */
    public int deleteServiceItemPriceByServiceItemId(Long id);
}
