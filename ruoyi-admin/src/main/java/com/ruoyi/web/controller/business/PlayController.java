package com.ruoyi.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.AdMonitor;
import com.ruoyi.common.core.domain.dto.play.PlayDTO;
import com.ruoyi.common.core.domain.dto.play.PlayMessageDTO;
import com.ruoyi.common.core.domain.entity.MerchantInfo;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.dto.play.*;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;
import com.ruoyi.system.domain.vo.play.*;
import com.ruoyi.system.service.IPlayService;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.PlayMessagePushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    private PlayMessageConfoundLogService playMessageConfoundLogService;

    @Resource
    private PlayMessagePushService playMessagePushService;


    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation("创建炒群任务")
    @PostMapping(value = "/create")
    public R<String> create(@RequestBody PlayDTO dto) {
        dto.setLoginUser(getLoginUser());
        R<String> checkPlayParamsRet = checkAddPlayParams(dto);
        if (checkPlayParamsRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayParamsRet;
        }

        R<String> checkPlayMessageListRet = checkPlayMessageList(dto.getPlayMessageList());
        if (checkPlayMessageListRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayMessageListRet;
        }

        try {
            return playService.create(dto);
        } catch (Exception e) {
            log.error("play_create_err:{}", e.getMessage());
        }
        return R.fail();
    }

    private R<String> checkPlayMessageList(List<PlayMessageDTO> playMessageDTOList) {
        if (null == playMessageDTOList || playMessageDTOList.isEmpty()) {
            return R.fail(ErrInfoConfig.getDynmic(11016));
        }
        for (PlayMessageDTO playMessageDTO : playMessageDTOList) {
            if (StringUtils.isEmpty(playMessageDTO.getRobotNickname())) {
                return R.fail(ErrInfoConfig.getDynmic(11017));
            }
        }
        return R.ok();
    }

    private R<String> checkAddPlayParams(PlayDTO dto) {
        if (ObjectUtils.isEmpty(dto.getLoginUser().getMerchantInfo()) || dto.getLoginUser().getMerchantInfo().getMerchantType() != 0) {
            return R.fail(ErrInfoConfig.getDynmic(11011));
        }
        if (StringUtils.isEmpty(dto.getName())) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "任务名称不能为空"));
        }
        if (dto.getName().length() > 64) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "任务名称不能超过64字"));
        }
        if (null == dto.getGroupSource()) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "请选择群来源"));
        }
        if (dto.getGroupSource() == 0 && dto.getGroupNum() == null) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "请配置群需求数量"));
        }
        if (dto.getGroupSource() == 1) {
            if (null == dto.getGroupUrls()) {
                return R.fail(ErrInfoConfig.getDynmic(11000, "外部群链接不能为空"));
            }
            dto.setGroupUrls(handleUrlList(dto.getGroupUrls()));
            if (dto.getGroupUrls().isEmpty()) {
                return R.fail(ErrInfoConfig.getDynmic(11000, "外部群链接不能为空"));
            }
        }
        if (null == dto.getRobotNum()) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "请配置每个群演员数"));
        }

        dto.setUrlPool(handleUrlList(dto.getUrlPool()));
        if (dto.getGroupCondition() == 0) {
            if (dto.getUserNum() == null || dto.getUserNum() < 1) {
                return R.fail(ErrInfoConfig.getDynmic(11000, "请配置群人数"));
            }
        }
        if (dto.getGroupCondition() == 1) {
            if (dto.getStartType() == 1 && null == dto.getStartGroupDate()) {
                return R.fail(ErrInfoConfig.getDynmic(11000, "请配置定时开始炒群时间"));
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
        if (StringUtils.isEmpty(dto.getName())) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "任务名称不能为空"));
        }
        if (dto.getName().length() > 64) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "任务名称不能超过64字"));
        }
        dto.setUrlPool(handleUrlList(dto.getUrlPool()));
        R<String> checkPlayMessageListRet = checkPlayMessageList(dto.getPlayMessageList());
        if (checkPlayMessageListRet.getCode() != HttpStatus.SUCCESS) {
            return checkPlayMessageListRet;
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

    @ApiOperation("账号明细列表")
    @PostMapping("robotDetails")
    public R<List<QueryRobotDetailVO>> robotDetails(@RequestBody QueryRobotDetailDTO dto) {
        return R.ok(playMessagePushService.robotDetails(dto));
    }

    @ApiOperation("炒群状态操作")
    @PostMapping("pushOperation")
    public R<Void> pushOperation(@Validated @RequestBody PushOperationDTO dto) {
        playMessagePushService.operation(dto);
        return R.ok();
    }

    @ApiOperation("重复炒群")
    @PostMapping("repeat/{playId}")
    public R<String> repeatPlay(@PathVariable String playId) {
        LoginUser loginUser = getLoginUser();
        if (ObjectUtils.isEmpty(loginUser.getMerchantInfo()) || loginUser.getMerchantInfo().getMerchantType() != 0) {
            //return R.fail(ErrInfoConfig.getDynmic(11011));
        }
        return playService.repeatPlay(playId, loginUser);
    }

    @ApiOperation("修改炒群任务状态")
    @PostMapping("state")
    public R<Boolean> updateState(@Validated @RequestBody PlayStateDTO dto) {
        return R.ok(playService.updateState(dto));
    }

    @ApiOperation("释放水军")
    @PostMapping("/{playId}/releaseRobot")
    public R<String> releaseUser(@PathVariable String playId) {
        return playService.releaseUser(playId);
    }

    @ApiOperation("job执行释放水军")
    @PostMapping("jobReleaseRobot")
    public void jobReleaseRobot() {
        playService.jobReleaseRobot();
    }
}
