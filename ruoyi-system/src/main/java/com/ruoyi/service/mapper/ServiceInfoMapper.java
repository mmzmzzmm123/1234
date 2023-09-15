package com.ruoyi.service.mapper;

import java.util.List;
import com.ruoyi.service.domain.ServiceInfo;
import com.ruoyi.service.domain.ServiceItem;

/**
 * 服务信息Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface ServiceInfoMapper {

    /**
     * 查询服务信息
     *
     * @param id 服务信息主键
     * @return 服务信息
     */
    public ServiceInfo selectServiceInfoById(Long id);

    /**
     * 查询服务信息列表
     *
     * @param serviceInfo 服务信息
     * @return 服务信息集合
     */
    public List<ServiceInfo> selectServiceInfoList(ServiceInfo serviceInfo);

    /**
     * 新增服务信息
     *
     * @param serviceInfo 服务信息
     * @return 结果
     */
    public int insertServiceInfo(ServiceInfo serviceInfo);

    /**
     * 修改服务信息
     *
     * @param serviceInfo 服务信息
     * @return 结果
     */
    public int updateServiceInfo(ServiceInfo serviceInfo);

    /**
     * 删除服务信息
     *
     * @param id 服务信息主键
     * @return 结果
     */
    public int deleteServiceInfoById(Long id);

    /**
     * 批量删除服务信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServiceInfoByIds(Long[] ids);

    /**
     * 批量删除服务子项目
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteServiceItemByServiceIds(Long[] ids);

    /**
     * 批量新增服务子项目
     *
     * @param serviceItemList 服务子项目列表
     * @return 结果
     */
    public int batchServiceItem(List<ServiceItem> serviceItemList);


    /**
     * 通过服务信息主键删除服务子项目信息
     *
     * @param id 服务信息ID
     * @return 结果
     */
    public int deleteServiceItemByServiceId(Long id);
}
