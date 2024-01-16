package com.ruoyi.system.service.impl;
import java.time.LocalDateTime;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.dto.GroupPageQueryExportDTO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.service.GroupInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 群基础信息(GroupInfo)表服务实现类
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:48
 */
@Service("groupInfoService")
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupInfoService {

    @Override
    public List<String> existGroup(List<String> groupSerialNos) {
        return (ArrayList) baseMapper.selectObjs(
                new LambdaQueryWrapper<GroupInfo>()
                        .eq(GroupInfo::getGroupSerialNo, groupSerialNos)
                        .select(GroupInfo::getGroupSerialNo));
    }

    @Override
    public Page<GroupPageInfoVO> groupPage(GroupPageQueryDTO dto) {
        Page page = new Page<>(dto.getPage(), dto.getLimit());
        if (dto instanceof GroupPageQueryExportDTO) {
            page.setSearchCount(false);
        }
        return baseMapper.page(page, dto);
    }

    @Override
    public List<GroupInfo> saveImportGroup(List<GroupResourceVO> resourceList) {
        if(CollUtil.isEmpty(resourceList)){
            return new ArrayList<>();
        }
        List<String> groupSerialNos = existGroup(resourceList.stream().map(GroupResourceVO::getGroupSerialNo).collect(Collectors.toList()));
        if (CollUtil.isNotEmpty(groupSerialNos)) {
            resourceList = resourceList.stream().filter(p -> !groupSerialNos.contains(p)).collect(Collectors.toList());
            if(CollUtil.isEmpty(resourceList)){
                return new ArrayList<>();
            }
        }
        List<GroupInfo> result =
                resourceList.stream().map(p->{
                    GroupInfo groupInfo = new GroupInfo();
                    groupInfo.setGroupId(IdWorker.getIdStr());
                    groupInfo.setGroupSerialNo(p.getGroupSerialNo());
                    groupInfo.setCreateType(10);
                    groupInfo.setRegistrationTime(p.getRegistrationTime());
                    groupInfo.setCreateTime(LocalDateTime.now());
                    groupInfo.setUpdateTime(LocalDateTime.now());
                    return groupInfo;
                }).collect(Collectors.toList());
        saveBatch(result);
        return result;
    }
}
