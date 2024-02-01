package com.ruoyi.web.controller.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.*;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.dto.AutoReplayLogDTO;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.ForceFinishGroupDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.dto.play.*;
import com.ruoyi.system.domain.mongdb.MonitorLog;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.domain.vo.AutoReplayLogVO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;
import com.ruoyi.system.domain.vo.play.*;
import com.ruoyi.system.service.*;
import com.ruoyi.system.service.business.MonitorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : zengyi
 */
@Api(tags = "炒群任务")
@Slf4j
@RestController
@RequestMapping("/play")
public class PlayController extends BaseController {
    @Resource
    private IPlayService playService;

    @Resource
    private PlayExecutionLogService playExecutionLogService;

    @Resource
    private MonitorLogService monitorLogService;

    @Resource
    private PlayMessageConfoundLogService playMessageConfoundLogService;

    @Resource
    private PlayMessagePushService playMessagePushService;

    @Autowired
    private PlayGroupInfoService playGroupInfoService;

    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation("创建炒群任务")
    @PostMapping(value = "/create")
    public R<String> create(@RequestBody PlayDTO dto) {
        R<String> checkPlayParamsRet = checkAddPlayParams(dto);
        if (checkPlayParamsRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayParamsRet;
        }

        R<String> checkPlayCommonRet = checkPlayCommonRet(dto);
        if (checkPlayCommonRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayCommonRet;
        }

        try {
            return playService.create(dto);
        } catch (Exception e) {
            log.error("play_create_err:{}", e.getMessage());
            return R.fail(10000, ErrInfoConfig.getDynmic(10000, e.getMessage()));
        }
    }

    private R<String> checkPlayCommonRet(PlayDTO dto) {
        dto.setLoginUser(getLoginUser());
        if (ObjectUtils.isEmpty(dto.getLoginUser().getMerchantInfo()) || dto.getLoginUser().getMerchantInfo().getMerchantType() != 0) {
            return R.fail(11011, ErrInfoConfig.getDynmic(11011));
        }
        if (StringUtils.isEmpty(dto.getName())) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "任务名称不能为空"));
        }
        if (dto.getName().length() > 64) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "任务名称不能超过64字"));
        }

        if (null == dto.getPlayMessageList() || dto.getPlayMessageList().isEmpty()) {
            return R.fail(11016, ErrInfoConfig.getDynmic(11016));
        }
        for (PlayMessageDTO playMessageDTO : dto.getPlayMessageList()) {
            if (StringUtils.isEmpty(playMessageDTO.getRobotNickname())) {
                return R.fail(11017, ErrInfoConfig.getDynmic(11017));
            }
            List<ContentJson> contents = playMessageDTO.getMessageContent();
            for (ContentJson content : contents) {
                if (null == content.getMomentTypeId()) {
                    return R.fail(11018, ErrInfoConfig.getDynmic(11018));
                }
                if (content.getMomentTypeId() == 2017) {
                    if (null == dto.getUrlPool() || dto.getUrlPool().size() < 1) {
                        return R.fail(11019, ErrInfoConfig.getDynmic(11019));
                    }
                } else if (content.getMomentTypeId() == 2018) {
                    if (StringUtils.isEmpty(content.getSMateContent())) {
                        return R.fail(11020, ErrInfoConfig.getDynmic(11020));
                    }
                }
            }
        }

        if (null == dto.getSendMechanism()) {
            return R.fail(11021, ErrInfoConfig.getDynmic(11021));
        }
        if (null == dto.getSendMechanism().getMsgSepStart() || null == dto.getSendMechanism().getMsgSepEnd()) {
            return R.fail(11022, ErrInfoConfig.getDynmic(11022));
        }
        if (dto.getSendMechanism().getMsgSepStart() >= dto.getSendMechanism().getMsgSepEnd()) {
            return R.fail(11023, ErrInfoConfig.getDynmic(11023));
        }
        if (null == dto.getSendMechanism().getPerformerSepStart() || null == dto.getSendMechanism().getPerformerSepEnd()) {
            return R.fail(11024, ErrInfoConfig.getDynmic(11024));
        }
        if (dto.getSendMechanism().getPerformerSepStart() >= dto.getSendMechanism().getPerformerSepEnd()) {
            return R.fail(11025, ErrInfoConfig.getDynmic(11025));
        }
        if (CollectionUtils.isNotEmpty(dto.getPlayRobotMessageList())) {
            for (PlayRobotMessageDTO playRobotMessageDTO : dto.getPlayRobotMessageList()) {
                List<ContentJson> contents = playRobotMessageDTO.getMessageContent();
                for (ContentJson content : contents) {
                    if (null == content.getMomentTypeId()) {
                        return R.fail(11018, ErrInfoConfig.getDynmic(11018));
                    }
                    if (content.getMomentTypeId() == 2017) {
                        if (null == dto.getUrlPool() || dto.getUrlPool().size() < 1) {
                            return R.fail(11019, ErrInfoConfig.getDynmic(11019));
                        }
                    }
                }
            }
        }

        return R.ok();
    }

    private R<String> checkAddPlayParams(PlayDTO dto) {
        if (null == dto.getGroupSource()) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "请选择群来源"));
        }
        if (dto.getGroupSource() == 0 && dto.getGroupNum() == null) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "请配置群需求数量"));
        }
        if (dto.getGroupSource() == 1) {
            if (null == dto.getGroupUrls()) {
                return R.fail(11000, ErrInfoConfig.getDynmic(11000, "外部群链接不能为空"));
            }
            dto.setGroupUrls(handleUrlList(dto.getGroupUrls()));
            if (dto.getGroupUrls().isEmpty()) {
                return R.fail(11000, ErrInfoConfig.getDynmic(11000, "外部群链接不能为空"));
            }
        }
        if (null == dto.getRobotNum() || dto.getRobotNum() < 1) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "请配置每个群演员数"));
        }

        dto.setUrlPool(handleUrlList(dto.getUrlPool()));
        if (dto.getGroupCondition() == 0) {
            if (dto.getUserNum() == null || dto.getUserNum() < 1) {
                return R.fail(11000, ErrInfoConfig.getDynmic(11000, "请配置群人数"));
            }
        }
        if (dto.getGroupCondition() == 1) {
            if (dto.getStartType() == 1 && null == dto.getStartGroupDate()) {
                return R.fail(11000, ErrInfoConfig.getDynmic(11000, "请配置定时开始炒群时间"));
            }
        }
        return R.ok();
    }

    //群链接去除空格
    private List<String> handleUrlList(List<String> urls) {
        return urls.stream()
                .map(org.springframework.util.StringUtils::trimAllWhitespace)
                .collect(Collectors.toList());
    }

    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation("修改炒群任务")
    @PostMapping(value = "/update")
    public R<String> update(@RequestBody PlayDTO dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        R<String> checkPlayCommonRet = checkPlayCommonRet(dto);
        if (checkPlayCommonRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayCommonRet;
        }

        return playService.updatePlay(dto);
    }

    @ApiOperation("获取炒群任务详情")
    @GetMapping(value = "/{playId}")
    public R<PlayVO> info(@PathVariable String playId) {
        return playService.info(playId);
    }

    @ApiOperation("获取广告监控配置")
    @GetMapping(value = "/{playId}/ad")
    public R<AdMonitor> adInfo(@PathVariable String playId) {
        return R.ok(playService.adInfo(playId));
    }

    @ApiOperation("修改广告监控配置")
    @PostMapping(value = "/{playId}/ad")
    public R<String> updateAdInfo(@PathVariable String playId, @RequestBody AdMonitor dto) {
        return playService.updateAdInfo(playId, dto);
    }

    @ApiOperation("设置炒群速度")
    @PostMapping("setSpeed")
    public R<Void> setSpeed(@Validated @RequestBody SetSpeedDTO dto) {
        playService.setSpeed(dto);
        return R.ok();
    }

    @ApiOperation("炒群任务列表")
    @PostMapping("page")
    public R<Page<QueryPlayVO>> page(@RequestBody QueryPlayDTO dto) {
        MerchantInfo merchantInfo = getMerchantInfo();
        if (merchantInfo != null) {
            if (!merchantInfo.getMerchantType().equals(2)) {
                dto.setMerchantId(merchantInfo.getMerchantId());
            }
        }
        return R.ok(playService.page(dto));
    }

    @ApiOperation("任务进度")
    @PostMapping("taskProgress")
    public R<List<PlayTaskProgressVO>> taskProgress(@RequestBody QueryTaskProgressDTO dto) {
        return R.ok(playService.taskProgress(dto));
    }

    @ApiOperation("炒群进度")
    @PostMapping("groupProgress/{playId}")
    public R<List<PlayGroupProgressVO>> groupProgress(@PathVariable String playId) {
        return R.ok(playService.groupProgress(playId));
    }

    @ApiOperation("剧本执行日志")
    @PostMapping("executionLogList/{playId}")
    public R<List<PlayExecutionLog>> executionLogList(@PathVariable String playId) {
        return R.ok(playExecutionLogService.listByPlayId(playId));
    }

    @ApiOperation("剧本执行日志-分页")
    @PostMapping("executionLogPage")
    public R<Page<PlayExecutionLog>> executionLogPage(@Validated @RequestBody QueryExecutionLogDTO dto) {
        return R.ok(playExecutionLogService.logPage(dto));
    }


    @ApiOperation("监控触发日志-分页")
    @PostMapping("monitorLogPage")
    public R<Page<MonitorLog>> executionLogPage(@Validated @RequestBody QueryMonitorLogDTO dto) {
        return R.ok(monitorLogService.logPage(dto));
    }

    @ApiOperation("监控触发日志-导出")
    @PostMapping("monitorLogExport")
    public void executionLogExport(@Validated @RequestBody QueryMonitorLogDTO dto, HttpServletResponse response) {
        List<MonitorLog> list = new ArrayList<>();
        int page = 1;
        int limit = 500;
        while (true) {
            try {
                dto.setPage(page++);
                dto.setLimit(limit);
                List<MonitorLog> records = monitorLogService.logPage(dto).getRecords();
                list.addAll(records);
                if (records.size() < limit) {
                    break;
                }
            } catch (Exception e) {
                break;
            }
        }
        ExcelUtil<MonitorLog> util = new ExcelUtil<>(MonitorLog.class);
        util.exportExcel(response, list, "监控触发日志");
    }


    @ApiOperation("任务推送明细查询")
    @PostMapping("pushDetailPage")
    public R<Page<QueryPushDetailVO>> pushDetailPage(@Validated @RequestBody QueryPushDetailDTO dto) {
        return R.ok(playService.pushDetailPage(dto));
    }


    @ApiOperation("混淆日志查询")
    @PostMapping("confoundLogPage")
    public R<Page<QueryConfoundLogVO>> confoundLogPage(@Validated @RequestBody QueryConfoundLogDTO dto) {
        return R.ok(playMessageConfoundLogService.page(dto));
    }

    @ApiOperation("混淆重试")
    @PostMapping("confoundRetry")
    public R<Void> confoundRetry(@RequestBody ConfoundRetryDTO dto) {
        playMessageConfoundLogService.retry(dto);
        return R.ok();
    }

    @ApiOperation("账号明细统计")
    @PostMapping("robotStatistics/{playId}")
    public R<RobotStatisticsVO> robotStatistics(@PathVariable String playId) {
        return R.ok(playService.robotStatistics(playId));
    }

    @ApiOperation("账号明细列表-国家维度")
    @PostMapping("robotDetails")
    public R<List<QueryRobotDetailVO>> robotDetails(@RequestBody QueryRobotDetailDTO dto) {
        return R.ok(playMessagePushService.robotDetails(dto));
    }

    @ApiOperation("账号明细列表-账号维度")
    @PostMapping("robotAccountDetails")
    public R<Page<QueryRobotAccountDetailVO>> robotAccountDetails(@RequestBody QueryRobotAccountDetailDTO dto) {
        return R.ok(playMessagePushService.robotAccountDetails(dto));
    }
    @ApiOperation(value = "导出账号明细列表-账号维度")
    @PostMapping("/exportRobotAccountDetails")
    public R<Page<QueryRobotAccountDetailVO>> exportRobotAccountDetails(@RequestBody QueryRobotAccountDetailDTO dto, HttpServletResponse response){
        ExcelUtil<QueryRobotAccountDetailVO> excelUtil = new ExcelUtil<>(QueryRobotAccountDetailVO.class);
        List<QueryRobotAccountDetailVO> records = new ArrayList<>();
        Page<QueryRobotAccountDetailVO> page = null;
        int current = 1;
        do {
            dto.setPage(current);
            dto.setLimit(10000);
            page = playMessagePushService.robotAccountDetails(dto);
            records.addAll(page.getRecords());
            ++current;
        } while (page.hasNext());
        excelUtil.exportExcel(response,records,"账号明细列表");
        return R.ok();
    }

    @ApiOperation("炒群状态操作")
    @PostMapping("pushOperation")
    public R<Void> pushOperation(@Validated @RequestBody PushOperationDTO dto) {
        playMessagePushService.operation(dto);
        return R.ok();
    }

    @ApiOperation("重复炒群")
    @PostMapping("repeat")
    public R<String> repeatPlay(@RequestBody PlayDTO dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        LoginUser loginUser = getLoginUser();
        if (ObjectUtils.isEmpty(loginUser.getMerchantInfo()) || loginUser.getMerchantInfo().getMerchantType() != 0) {
            return R.fail(11011, ErrInfoConfig.getDynmic(11011));
        }

        dto.setLoginUser(loginUser);
        try {
            return playService.repeatPlay(dto);
        } catch (Exception e) {
            log.error("repeatPlay:params:{},msg:{}", JSONObject.toJSONString(dto), e.getMessage());
            return R.fail(10000, ErrInfoConfig.getDynmic(10000, e.getMessage()));
        }
    }

    @ApiOperation("修改炒群任务状态")
    @PostMapping("state")
    public R<Boolean> updateState(@Validated @RequestBody PlayStateDTO dto) {
        return R.ok(playService.updateState(dto));
    }

    @ApiOperation("释放水军")
    @PostMapping("/{playId}/releaseRobot")
    public R<String> releaseRobot(@PathVariable String playId) {
        return playService.handleReleaseRobot(playId);
    }

    @ApiOperation("job释放锁定水军")
    @PostMapping("jobReleaseRobot")
    public void jobReleaseRobot() {
        playService.jobReleaseRobot();
    }


    @ApiOperation("强制结束入群")
    @PostMapping("forceFinishGroup")
    public R<String> forceFinishGroup(@RequestBody ForceFinishGroupDTO forceFinishGroupDTO){
        return playGroupInfoService.forceFinishGroup(forceFinishGroupDTO.getGroupId(),forceFinishGroupDTO.getPlayId());
    }

    @ApiOperation("获取自动回复日志")
    @PostMapping("/autoReplayLog")
    public R<PageInfo<AutoReplayLogVO>> autoReplayLog(@RequestBody AutoReplayLogDTO dto){
        if (StringUtils.isEmpty(dto.getPlayId())) {
            return R.fail(11000, ErrInfoConfig.getDynmic(11000, "任务id不能为空"));
        }
        return R.ok(playService.autoReplayLog(dto));
    }

    @ApiOperation(value = "导出自动回复日志")
    @PostMapping("/export/autoReplayLog")
    public R<PageInfo<AutoReplayLogVO>> export(@RequestBody AutoReplayLogDTO dto, HttpServletResponse response) {
        if (StrUtil.isBlank(dto.getPlayId())) {
            return R.ok();
        }
        PageInfo<AutoReplayLogVO> result = playService.autoReplayLog(dto);
        ExcelUtil<AutoReplayLogVO> excelUtil = new ExcelUtil<>(AutoReplayLogVO.class);
        excelUtil.exportExcel(response, result.getList(), "任务id" + dto.getPlayId() + "自动回复日志");
        return R.ok();
    }
}
