package com.ruoyi.service.service;

import java.util.List;
import com.ruoyi.service.domain.ServiceInfo;

/**
 * 服务信息Service接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface IServiceInfoService {

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
     * 批量删除服务信息
     *
     * @param ids 需要删除的服务信息主键集合
     * @return 结果
     */
    public int deleteServiceInfoByIds(Long[] ids);

    /**
     * 删除服务信息信息
     *
     * @param id 服务信息主键
     * @return 结果
     */
    public int deleteServiceInfoById(Long id);
}
