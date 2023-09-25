package com.ruoyi.api.server.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.service.domain.ServiceInfo;
import com.ruoyi.service.mapper.ServiceInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LAM
 * @date 2023/9/22 18:43
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServerService {

    private final ServiceInfoMapper serviceInfoMapper;

    /**
     * 查询服务全数据
     *
     * @return 结果
     * */
    public List<ServiceInfo> select() {
        log.info("查询服务全数据：开始");
        List<ServiceInfo> serviceInfos = serviceInfoMapper.selectJoinAll(new ServiceInfo());
        if (ObjectUtil.isEmpty(serviceInfos)) {
            serviceInfos = new ArrayList<>();
        }
        log.info("查询服务全数据：完成，返回数据：{}", serviceInfos);
        return serviceInfos;
    }
}
