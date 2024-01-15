package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.dto.*;
import com.ruoyi.system.domain.vo.GroupClusterCountVO;
import com.ruoyi.system.domain.vo.GroupClusterVO;
import com.ruoyi.system.domain.vo.GroupMemberInfoVO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:33
 */
@Api(tags = "群管理")
@RestController
@RequestMapping("/group")
public class GroupController {

    @ApiOperation(value = "获取群分组列表和群数统计")
    @PostMapping("/queryClusterCount")
    public R<List<GroupClusterCountVO>> queryClusterCount(@RequestBody GroupClusterNameDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "获取群分组列表")
    @PostMapping("/queryCluster")
    public R<List<GroupClusterVO>> queryCluster(@RequestBody GroupClusterNameDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "新增群分组")
    @PostMapping("/addCluster")
    public R<Void> addCluster(@RequestBody GroupClusterNameDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "修改群分组名称")
    @PostMapping("/updateCluster")
    public R<Void> updateCluster(@RequestBody GroupClusterNameUpdateDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "删除群分组")
    @PostMapping("/deleteCluster/{clusterId}")
    public R<Void> deleteCluster(@PathVariable String clusterId) {
        return R.ok();
    }


    @ApiOperation(value = "分页查询群列表信息")
    @PostMapping("/page")
    public R<Page<GroupPageInfoVO>> page(@RequestBody GroupPageQueryDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "导出群信息")
    @PostMapping("/export")
    public R<Page<GroupPageInfoVO>> export(@RequestBody GroupPageQueryDTO dto, HttpServletResponse response) {
        List<GroupPageInfoVO> list = new ArrayList<>();
        ExcelUtil<GroupPageInfoVO> util = new ExcelUtil<GroupPageInfoVO>(GroupPageInfoVO.class);
        util.exportExcel(response, list, "群列表");
        return R.ok();
    }

    @ApiOperation(value = "更新群信息")
    @PostMapping("/syncInfo")
    public R<Void> syncInfo() {
        return R.ok();
    }

    @ApiOperation(value = "导入群资源")
    @PostMapping("/importResource")
    public R<Void> importResource(@RequestParam(value = "file") @ApiParam("群资源文件") MultipartFile file,
                                  @RequestParam(value = "clusterId") @ApiParam("分组号") String clusterId) {
        return R.ok();
    }


    @ApiOperation(value = "批量修改群信息")
    @PostMapping("/updateInfo")
    public R<Void> updateInfo(@RequestBody GroupUpdateInfoDTO dto) {
        return R.ok();
    }


    @ApiOperation(value = "修改群的分组")
    @PostMapping("/changeCluster")
    public R<Void> changeCluster(@RequestBody ChangeGroupClusterDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "邀请bot进群检测")
    @PostMapping("/invitingBotJoin")
    public R<Void> invitingBotJoin(@RequestBody GroupIdsDTO dto) {
        return R.ok();
    }


    @ApiOperation(value = "标记群是否使用")
    @PostMapping("/markUsedFlag")
    public R<Void> markUsedFlag(@RequestBody GroupMarkUsedDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "设置群类型")
    @PostMapping("/setType")
    public R<Void> setType(@RequestBody GroupTypeSetDTO dto) {
        return R.ok();
    }


    @ApiOperation(value = "注销群")
    @PostMapping("/delete")
    public R<Void> delete(@RequestBody GroupIdsDTO dto) {
        return R.ok();
    }


    @ApiOperation(value = "查询群内成员")
    @PostMapping("/queryMember")
    public R<Page<GroupMemberInfoVO>> queryMember(@RequestBody GroupMemberQueryDTO dto) {
        return R.ok();
    }

    @ApiOperation(value = "同步群内成员")
    @PostMapping("/syncMember/{groupId}")
    public R<Void> syncMember(@PathVariable String groupId) {
        return R.ok();
    }

    @ApiOperation(value = "设置管理员")
    @PostMapping("/setAdmin")
    public R<Void> setAdmin(@RequestBody GroupAdminSetDTO dto) {
        return R.ok();
    }
}
