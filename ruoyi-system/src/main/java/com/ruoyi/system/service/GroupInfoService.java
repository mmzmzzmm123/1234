package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.system.callback.dto.Called1100910039DTO;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.openapi.model.output.ExtTgSelectGroupVO;

import java.util.List;

/**
 * 群基础信息(GroupInfo)表服务接口
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:48
 */
public interface GroupInfoService extends IService<GroupInfo> {

    List<String> existGroup(List<String> groupSerialNos);

    Page<GroupPageInfoVO> groupPage(GroupPageQueryDTO dto);

    List<GroupInfo> saveImportGroup(List<GroupResourceVO> resourceList);


    List<GroupInfoVO> selectGroup(Integer registrationDay, Integer groupNum, List<String> countryCode, List<String> excludeGroupId);

    void syncGroupInfo(List<GroupInfo> groupInfoList, List<ExtTgSelectGroupVO> utInfos);

    GroupInfo  saveExternalGroup(Called1100910039DTO dto);

    GroupInfo getGroupBySerialNo(String groupId);
}
