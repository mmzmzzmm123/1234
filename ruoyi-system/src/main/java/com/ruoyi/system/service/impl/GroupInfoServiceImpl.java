package com.ruoyi.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.dto.GroupPageQueryExportDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.openapi.model.output.ExtTgSelectGroupVO;
import com.ruoyi.system.service.GroupInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        if (CollUtil.isEmpty(resourceList)) {
            return new ArrayList<>();
        }
        List<String> groupSerialNos = existGroup(resourceList.stream().map(GroupResourceVO::getGroupSerialNo)
                .collect(Collectors.toList()));
        if (CollUtil.isNotEmpty(groupSerialNos)) {
            resourceList = resourceList.stream().filter(p -> !groupSerialNos.contains(p.getGroupSerialNo())).collect(Collectors.toList());
            if (CollUtil.isEmpty(resourceList)) {
                return new ArrayList<>();
            }
        }
        List<GroupInfo> result = new ArrayList<>();
        List<String> newGroupSerialNos = new ArrayList<>();
        for (GroupResourceVO groupResourceVO : resourceList) {
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setGroupId(IdWorker.getIdStr());
            groupInfo.setGroupSerialNo(groupResourceVO.getGroupSerialNo());
            groupInfo.setCreateType(10);
            groupInfo.setRegistrationTime(groupResourceVO.getRegistrationTime());
            groupInfo.setCreateTime(LocalDateTime.now());
            groupInfo.setUpdateTime(LocalDateTime.now());
            //防止导入的群数据重复
            if (newGroupSerialNos.contains(groupResourceVO.getGroupSerialNo())) {
                continue;
            }
            newGroupSerialNos.add(groupResourceVO.getGroupSerialNo());
            result.add(groupInfo);
        }
        saveBatch(result);
        return result;
    }

    @Override
    public List<GroupInfoVO> selectGroup(Integer registrationDay, Integer groupNum, List<String> countryCode,List<String> excludeGroupId) {
        return baseMapper.selectGroup(registrationDay == null ? null :
                LocalDateTime.now().plusDays(-registrationDay), groupNum, countryCode,excludeGroupId);
    }

    @Override
    public void syncGroupInfo(List<GroupInfo> groupInfoList, List<ExtTgSelectGroupVO> utInfos) {
        if(CollUtil.isEmpty(utInfos)){
            return;
        }
        Map<String,ExtTgSelectGroupVO> utInfoMap =
                utInfos.stream().collect(Collectors.toMap(ExtTgSelectGroupVO::getChatroomSerialNo, p -> p));
        groupInfoList.stream().filter(p -> utInfoMap.containsKey(p.getGroupSerialNo()))
                .map(p->{
                    ExtTgSelectGroupVO vo = utInfoMap.get(p.getGroupSerialNo());
                    GroupInfo update = new GroupInfo();
                    update.setGroupId(p.getGroupId());
                    update.setGroupInviteLink(vo.getInviteLink());
                    update.setUpdateTime(LocalDateTime.now());
                    return update;
                }).collect(Collectors.toList());

    }

    @Override
    public GroupInfo saveExternalGroup(String groupSerialNo,String groupName) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setGroupId(IdWorker.getIdStr());
        groupInfo.setGroupSerialNo(groupSerialNo);
        groupInfo.setGroupName(groupName);
        groupInfo.setCreateType(20);
        groupInfo.setCreateTime(LocalDateTime.now());
        return groupInfo;
    }

    @Override
    public GroupInfo getGroupBySerialNo(String serialNo) {
        return baseMapper.selectOne(new LambdaQueryWrapper<GroupInfo>().eq(GroupInfo::getGroupSerialNo,serialNo).last(" limit 1"));
    }
}
