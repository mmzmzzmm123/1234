package com.ruoyi.service.service;

import java.util.List;
import com.ruoyi.service.domain.ServiceItem;

/**
 * 服务子项目Service接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface IServiceItemService {

    /**
     * 查询服务子项目
     *
     * @param id 服务子项目主键
     * @return 服务子项目
     */
    public ServiceItem selectServiceItemById(Long id);

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
     * 批量删除服务子项目
     *
     * @param ids 需要删除的服务子项目主键集合
     * @return 结果
     */
    public int deleteServiceItemByIds(Long[] ids);

    /**
     * 删除服务子项目信息
     *
     * @param id 服务子项目主键
     * @return 结果
     */
    public int deleteServiceItemById(Long id);
}
