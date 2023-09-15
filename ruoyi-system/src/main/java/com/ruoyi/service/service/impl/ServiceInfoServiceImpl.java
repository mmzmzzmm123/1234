package com.ruoyi.service.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.service.domain.ServiceItem;
import com.ruoyi.service.mapper.ServiceInfoMapper;
import com.ruoyi.service.domain.ServiceInfo;
import com.ruoyi.service.service.IServiceInfoService;

/**
 * 服务信息Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class ServiceInfoServiceImpl implements IServiceInfoService {

    private final ServiceInfoMapper serviceInfoMapper;

    /**
     * 查询服务信息
     *
     * @param id 服务信息主键
     * @return 服务信息
     */
    @Override
    public ServiceInfo selectServiceInfoById(Long id) {
        return serviceInfoMapper.selectServiceInfoById(id);
    }

    /**
     * 查询服务信息列表
     *
     * @param serviceInfo 服务信息
     * @return 服务信息
     */
    @Override
    public List<ServiceInfo> selectServiceInfoList(ServiceInfo serviceInfo) {
        return serviceInfoMapper.selectServiceInfoList(serviceInfo);
    }

    /**
     * 新增服务信息
     *
     * @param serviceInfo 服务信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertServiceInfo(ServiceInfo serviceInfo) {
        Date now = DateUtils.getNowDate();
        String loginName = SecurityUtils.getUsername();
        serviceInfo.setCreateTime(now)
                .setCreateBy(loginName)
                .setUpdateBy(loginName)
                .setUpdateTime(now);
        int rows = serviceInfoMapper.insertServiceInfo(serviceInfo);
        insertServiceItem(serviceInfo);
        return rows;
    }

    /**
     * 修改服务信息
     *
     * @param serviceInfo 服务信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateServiceInfo(ServiceInfo serviceInfo) {
        serviceInfo.setUpdateBy(SecurityUtils.getUsername());
        serviceInfo.setUpdateTime(DateUtils.getNowDate());
        serviceInfoMapper.deleteServiceItemByServiceId(serviceInfo.getId());
        insertServiceItem(serviceInfo);
        return serviceInfoMapper.updateServiceInfo(serviceInfo);
    }

    /**
     * 批量删除服务信息
     *
     * @param ids 需要删除的服务信息主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteServiceInfoByIds(Long[] ids) {
        serviceInfoMapper.deleteServiceItemByServiceIds(ids);
        return serviceInfoMapper.deleteServiceInfoByIds(ids);
    }

    /**
     * 删除服务信息信息
     *
     * @param id 服务信息主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteServiceInfoById(Long id) {
        serviceInfoMapper.deleteServiceItemByServiceId(id);
        return serviceInfoMapper.deleteServiceInfoById(id);
    }

    /**
     * 新增服务子项目信息
     *
     * @param serviceInfo 服务信息对象
     */
    public void insertServiceItem(ServiceInfo serviceInfo) {
        List<ServiceItem> serviceItemList = serviceInfo.getServiceItemList();
        Long id = serviceInfo.getId();
        if (StringUtils.isNotNull(serviceItemList)) {
            Date now = DateUtils.getNowDate();
            String loginName = SecurityUtils.getUsername();
            List<ServiceItem> list = new ArrayList<ServiceItem>();
            for (ServiceItem serviceItem : serviceItemList) {
                serviceItem.setServiceId(id)
                        .setCreateBy(loginName)
                        .setCreateTime(now)
                        .setUpdateBy(loginName)
                        .setUpdateTime(now);
                list.add(serviceItem);
            }
            if (list.size() > 0) {
                serviceInfoMapper.batchServiceItem(list);
            }
        }
    }
}
