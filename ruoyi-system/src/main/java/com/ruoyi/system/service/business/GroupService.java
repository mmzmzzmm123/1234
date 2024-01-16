package com.ruoyi.system.service.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.GroupMonitorInfo;
import com.ruoyi.system.domain.dto.GroupQueryDTO;
import com.ruoyi.system.domain.vo.GroupInfoVO;
import com.ruoyi.system.domain.vo.GroupResourceVO;
import com.ruoyi.system.service.GroupClusterRefService;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.GroupMonitorInfoService;
import com.ruoyi.system.service.GroupStateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:47
 */
@Service
@AllArgsConstructor
@Slf4j
public class GroupService {

    private final GroupInfoService groupInfoService;

    private final GroupClusterRefService groupClusterRefService;

    private final GroupStateService groupStateService;

    private final GroupMonitorInfoService groupMonitorInfoService;

    /**
     * 选群
     *
     * @param dto 选群条件
     * @return 满足条件群
     */
    public R<List<GroupInfoVO>> queryGroup(GroupQueryDTO dto) {
        return R.ok();
    }

    /**
     * 取消标记群已使用
     *
     * @param groupIds 群id集合
     */
    public void cancelGroup(List<String> groupIds) {
        groupStateService.markUsed(groupIds, 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public void importResource(List<GroupResourceVO> resourceList, String newClusterId) {
        if (CollUtil.isEmpty(resourceList)) {
            return;
        }
        //1.保存群机器信息
        List<GroupInfo> groupInfos = groupInfoService.saveImportGroup(resourceList);
        Assert.notEmpty(groupInfos, "群已存在,请勿重复添加");
        List<String> groupIds = groupInfos.stream().map(GroupInfo::getGroupId).collect(Collectors.toList());
        //2.保存群组关联
        groupClusterRefService.add(groupIds, newClusterId);
        //3.保存群状态
        groupStateService.addImportGroup(groupIds);
        //4.新增bot监控表
        groupMonitorInfoService.add(groupIds);
        //5.同步群信息
        syncInfo(groupInfos);
        //6.执行邀请bot进群
        invitingBotJoin(groupInfos, true);
    }


    public void syncInfo(List<GroupInfo> groupInfos) {
        //todo openApi接口 获取ut群信息
    }

    public void invitingBotJoin(List<GroupInfo> groupInfos, boolean newGroup) {
        for (GroupInfo groupInfo : groupInfos) {
            if (newGroup) {
                invitingBotJoin(groupInfo);
            } else {
                GroupMonitorInfo gm = groupMonitorInfoService.getById(groupInfo.getGroupId());
                if (gm != null && ObjectUtil.equal(0, gm.getBotCheck())) {
                    invitingBotJoin(groupInfo);
                }
            }
        }
    }

    public void invitingBotJoin(GroupInfo groupInfo) {
        //todo 邀请bot进群逻辑
    }

    public List<GroupResourceVO> analysisExcelInfo(MultipartFile file) {
        List<GroupResourceVO> excel = new ArrayList<>();
        try {
            ExcelUtil<GroupResourceVO> util = new ExcelUtil<GroupResourceVO>(GroupResourceVO.class);
            excel = util.importExcel(file.getInputStream());
        } catch (Exception e) {
            log.info("analysisExcelInfo.error={}", file.getOriginalFilename(), e);
        }
        Assert.notEmpty(excel, "文件解析失败");
        return excel;
    }


}
