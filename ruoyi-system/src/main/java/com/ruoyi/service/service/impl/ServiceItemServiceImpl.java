package com.ruoyi.service.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.service.domain.ServiceItemPrice;
import com.ruoyi.service.mapper.ServiceItemMapper;
import com.ruoyi.service.domain.ServiceItem;
import com.ruoyi.service.service.IServiceItemService;

/**
 * 服务子项目Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class ServiceItemServiceImpl implements IServiceItemService {

    private final ServiceItemMapper serviceItemMapper;

    /**
     * 查询服务子项目
     *
     * @param id 服务子项目主键
     * @return 服务子项目
     */
    @Override
    public ServiceItem selectServiceItemById(Long id) {
        return serviceItemMapper.selectServiceItemById(id);
    }

    /**
     * 查询服务子项目列表
     *
     * @param serviceItem 服务子项目
     * @return 服务子项目
     */
    @Override
    public List<ServiceItem> selectServiceItemList(ServiceItem serviceItem) {
        return serviceItemMapper.selectServiceItemList(serviceItem);
    }

    /**
     * 级联查询服务信息
     *
     * @param serviceItem 服务子项目
     * @return 结果
     * */
    @Override
    public List<ServiceItem> selectJoinServiceInfo(ServiceItem serviceItem) {
        return serviceItemMapper.selectJoinServiceInfo(serviceItem);
    }

    /**
     * 新增服务子项目
     *
     * @param serviceItem 服务子项目
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertServiceItem(ServiceItem serviceItem) {
        serviceItem.setCreateTime(DateUtils.getNowDate());
        int rows = serviceItemMapper.insertServiceItem(serviceItem);
        insertServiceItemPrice(serviceItem);
        return rows;
    }

    /**
     * 修改服务子项目
     *
     * @param serviceItem 服务子项目
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateServiceItem(ServiceItem serviceItem) {
        serviceItem.setUpdateBy(SecurityUtils.getUsername());
        serviceItem.setUpdateTime(DateUtils.getNowDate());
        serviceItemMapper.deleteServiceItemPriceByServiceItemId(serviceItem.getId());
        insertServiceItemPrice(serviceItem);
        return serviceItemMapper.updateServiceItem(serviceItem);
    }

    /**
     * 批量删除服务子项目
     *
     * @param ids 需要删除的服务子项目主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteServiceItemByIds(Long[] ids) {
        serviceItemMapper.deleteServiceItemPriceByServiceItemIds(ids);
        return serviceItemMapper.deleteServiceItemByIds(ids);
    }

    /**
     * 删除服务子项目信息
     *
     * @param id 服务子项目主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteServiceItemById(Long id) {
        serviceItemMapper.deleteServiceItemPriceByServiceItemId(id);
        return serviceItemMapper.deleteServiceItemById(id);
    }

    /**
     * 新增服务子项目价格信息
     *
     * @param serviceItem 服务子项目对象
     */
    public void insertServiceItemPrice(ServiceItem serviceItem) {
        List<ServiceItemPrice> serviceItemPriceList = serviceItem.getServiceItemPriceList();
        Long id = serviceItem.getId();
        if (StringUtils.isNotNull(serviceItemPriceList)) {
            List<ServiceItemPrice> list = new ArrayList<ServiceItemPrice>();
            for (ServiceItemPrice serviceItemPrice : serviceItemPriceList) {
                serviceItemPrice.setServiceItemId(id);
                list.add(serviceItemPrice);
            }
            if (list.size() > 0) {
                serviceItemMapper.batchServiceItemPrice(list);
            }
        }
    }
}
