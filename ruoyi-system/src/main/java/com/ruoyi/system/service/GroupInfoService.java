package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;

/**
 * 群基础信息(GroupInfo)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:48
 */
public interface GroupInfoService extends IService<GroupInfo> {

     Page<GroupPageInfoVO> groupPage(GroupPageQueryDTO dto);
}
