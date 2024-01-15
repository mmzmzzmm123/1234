package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.dto.GroupPageQueryExportDTO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.service.GroupInfoService;
import org.springframework.stereotype.Service;

/**
 * 群基础信息(GroupInfo)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:48
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupInfoService {

    @Override
    public Page<GroupPageInfoVO> groupPage(GroupPageQueryDTO dto) {
        Page page = new Page<>(dto.getPage(), dto.getLimit());
        if (dto instanceof GroupPageQueryExportDTO) {
            page.setSearchCount(false);
        }
        return baseMapper.page(page, dto);
    }
}
