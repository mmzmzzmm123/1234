package com.ruoyi.web.controller.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.GroupCluster;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.GroupClusterCountVO;
import com.ruoyi.system.domain.vo.GroupClusterVO;
import com.ruoyi.system.domain.vo.GroupMemberInfoVO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import com.ruoyi.system.service.GroupClusterRefService;
import com.ruoyi.system.service.GroupClusterService;
import com.ruoyi.system.service.GroupInfoService;
import com.ruoyi.system.service.GroupStateService;
import com.ruoyi.system.service.business.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:33
 */
@Api(tags = "群管理")
@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

    @Resource
    private GroupClusterService groupClusterService;

    @Resource
    private GroupClusterRefService groupClusterRefService;

    @Resource
    private GroupStateService groupStateService;

    @Resource
    private GroupService groupService;

    @Resource
    private GroupInfoService groupInfoService;

    @ApiOperation(value = "获取群分组列表和群数统计")
    @PostMapping("/queryClusterCount")
    public R<List<GroupClusterCountVO>> queryClusterCount(@RequestBody GroupClusterNameDTO dto) {
        try {
            return R.ok(groupClusterRefService.fillCount(groupClusterService.queryRobotRadio(dto)));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "获取群分组列表")
    @PostMapping("/queryCluster")
    public R<List<GroupClusterVO>> queryCluster(@RequestBody GroupClusterNameDTO dto) {
        try {
            List<GroupCluster> groupClusters = groupClusterService.queryRobotRadio(dto);
            if (CollUtil.isEmpty(groupClusters)) {
                return R.ok(new ArrayList<>());
            }
            return R.ok(groupClusters.stream().map(p -> BeanUtil.copyProperties(p, GroupClusterVO.class)).collect(Collectors.toList()));
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "新增群分组")
    @PostMapping("/addCluster")
    public R<Void> addCluster(@RequestBody GroupClusterNameDTO dto) {
        try {
            groupClusterService.add(dto);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "修改群分组名称")
    @PostMapping("/updateCluster")
    public R<Void> updateCluster(@RequestBody GroupClusterNameUpdateDTO dto) {
        try {
            groupClusterService.update(dto);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "删除群分组")
    @PostMapping("/deleteCluster/{clusterId}")
    public R<Void> deleteCluster(@PathVariable String clusterId) {
        try {
            //删除分组
            groupClusterService.delete(clusterId);
            //分组上的群移至默认分组
            groupClusterRefService.moving(clusterId, groupClusterService.getClusterDefault(""));
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, clusterId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "分页查询群列表信息")
    @PostMapping("/page")
    public R<Page<GroupPageInfoVO>> page(@RequestBody GroupPageQueryDTO dto) {
        try {
            return R.ok(groupInfoService.groupPage(dto));
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "导出群信息")
    @PostMapping("/export")
    public R<Page<GroupPageInfoVO>> export(@RequestBody GroupPageQueryExportDTO dto, HttpServletResponse response) {
        List<GroupPageInfoVO> list = new ArrayList<>();
        int page = 1;
        int limit = 500;
        while (true) {
            try {
                dto.setPage(page++);
                dto.setLimit(limit);
                List<GroupPageInfoVO> records = groupInfoService.groupPage(dto).getRecords();
                list.addAll(records);
                if (records.size() < limit) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        ExcelUtil<GroupPageInfoVO> util = new ExcelUtil<>(GroupPageInfoVO.class);
        util.exportExcel(response, list, "群列表");
        return R.ok();
    }


    @ApiOperation(value = "更新群信息")
    @PostMapping("/syncInfo")
    public R<Void> syncInfo(@RequestBody GroupIdsDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "群不能为空");
            groupService.syncInfo(groupInfoService.listByIds(dto.getGroupIds()), null);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "导入群资源")
    @PostMapping("/importResource")
    public R<Void> importResource(@RequestParam(value = "file") @ApiParam("群资源文件") MultipartFile file,
                                  @RequestParam(value = "clusterId") @ApiParam("分组号") String clusterId) {
        try {
            groupService.importResource(groupService.analysisExcelInfo(file), clusterId);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(clusterId), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "批量修改群信息")
    @PostMapping("/updateInfo")
    public R<Void> updateInfo(@RequestBody GroupUpdateInfoDTO dto) {
        try {
            groupService.updateInfo(dto);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "修改群的分组")
    @PostMapping("/changeCluster")
    public R<Void> changeCluster(@RequestBody ChangeGroupClusterDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "修改分组的群不能为空");
            Assert.notEmpty(dto.getClusterId(), "新的分组不能为空");
            groupClusterRefService.moving(dto.getGroupIds(), dto.getClusterId());
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "邀请bot进群检测")
    @PostMapping("/invitingBotJoin")
    public R<Void> invitingBotJoin(@RequestBody GroupIdsDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "群不能为空");
            int count = groupService.invitingBotJoin(dto.getGroupIds());
            Assert.isTrue(count > 0, "无满足条件的群");
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "标记群是否使用")
    @PostMapping("/markUsedFlag")
    public R<Void> markUsedFlag(@RequestBody GroupMarkUsedDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "标记的群不能为空");
            groupStateService.markUsed(dto.getGroupIds(), dto.getGroupUsed());
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "设置群类型")
    @PostMapping("/setType")
    public R<Void> setType(@RequestBody GroupTypeSetDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "设置群类型的群不能为空");
            groupService.setType(dto);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "注销群")
    @PostMapping("/delete")
    public R<Void> delete(@RequestBody GroupIdsDTO dto) {
        try {
            Assert.notEmpty(dto.getGroupIds(), "注销的群不能为空");
            groupClusterRefService.deleteGroup(dto.getGroupIds());
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }


    @ApiOperation(value = "查询群内成员")
    @PostMapping("/queryMember")
    public R<Page<GroupMemberInfoVO>> queryMember(@RequestBody GroupMemberQueryDTO dto) {
        try {
            return R.ok(groupService.queryMember(dto));
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "同步群内成员")
    @PostMapping("/syncMember/{groupId}")
    public R<Void> syncMember(@PathVariable String groupId) {
        try {
            groupService.syncMember(groupId);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, groupId, e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }

    @ApiOperation(value = "设置管理员")
    @PostMapping("/setAdmin")
    public R<Void> setAdmin(@RequestBody GroupAdminSetDTO dto) {
        try {
            groupService.setAdmin(dto);
            return R.ok();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        } catch (Exception e) {
            String idWork = IdWorker.getIdStr();
            log.error("未知异常={},{} ", idWork, JSONObject.toJSONString(dto), e);
            return R.fail("未知异常！ trace:" + idWork);
        }
    }
}
