package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.tools.UrlReplaceTools;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.MD5Utils;
import com.ruoyi.system.callback.dto.Called1100850405DTO;
import com.ruoyi.system.callback.dto.Called1100850508DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.ConfoundRetryDTO;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.dto.out.InsertEventOutputDTO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;
import com.ruoyi.system.mapper.PlayMessageConfoundLogMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgAppointGradeTextListInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgDisperseImageInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgInsertEventInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.PlayMessageConfoundService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
public class PlayMessageConfoundLogServiceImpl extends ServiceImpl<PlayMessageConfoundLogMapper, PlayMessageConfoundLog>
        implements PlayMessageConfoundLogService {

    private static int IMG_CONFOUND_SIZE = 5;

    @Resource
    private PlayMessageConfoundService playMessageConfoundService;

    @Resource
    private RedisLock redisLock;


    @Override
    public void removeByConfoundIds(List<Integer> confoundIds) {
        if (CollectionUtils.isEmpty(confoundIds)) {
            return;
        }

        LambdaQueryWrapper<PlayMessageConfoundLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PlayMessageConfoundLog::getMessageConfoundId, confoundIds);
        super.remove(lambdaQueryWrapper);
    }

    @Override
    public void createConfoundLog(List<PlayMessageConfound> confoundList) {

        for (PlayMessageConfound confound : confoundList) {
            List<PlayMessageConfoundLog> logList = new ArrayList<>();
            // 图片要按群数量%10 多次获取
            if (confound.getMomentTypeId() == 2002) {
                int batchSize = confound.getGroupNum() % IMG_CONFOUND_SIZE == 0 ?
                        confound.getGroupNum() / IMG_CONFOUND_SIZE : (confound.getGroupNum() / IMG_CONFOUND_SIZE + 1);
                for (int i = 0; i < batchSize; i++) {
                    PlayMessageConfoundLog confoundLog = new PlayMessageConfoundLog();
                    confoundLog.setMessageConfoundId(confound.getId());
                    confoundLog.setState(0);

                    String optSerNo = this.disperseImage(confound.getConfoundContent());
                    confoundLog.setOptSerialNo(optSerNo);
                    if (StringUtils.isEmpty(optSerNo)) { //失败
                        confoundLog.setState(2);
                        confoundLog.setFailMessage("请求失败");
                    }
                    logList.add(confoundLog);
                }
            } else if (confound.getMomentTypeId() == 2005) {
                // 链接 按群数量循环同步调用
                for (Integer i = 0; i < confound.getGroupNum(); i++) {
                    PlayMessageConfoundLog confoundLog = new PlayMessageConfoundLog();
                    confoundLog.setMessageConfoundId(confound.getId());
                    confoundLog.setState(0);

                    TgBaseOutputDTO<InsertEventOutputDTO> outputDTO = this.insertEvent(confound.getConfoundContent());
                    String optSerNo = Optional.ofNullable(outputDTO)
                            .map(TgBaseOutputDTO::getOptSerNo)
                            .orElse("");
                    confoundLog.setOptSerialNo(optSerNo);

                    String resultContent = Optional.ofNullable(outputDTO)
                            .map(TgBaseOutputDTO::getData)
                            .map(InsertEventOutputDTO::getActivityLinkAddress)
                            .orElse("");
                    confoundLog.setResultContent(resultContent);
                    if (StringUtils.isEmpty(resultContent)) { //失败
                        confoundLog.setState(2);
                        confoundLog.setFailMessage("请求失败");
                    } else {
                        confoundLog.setState(1);
                        confoundLog.setFailMessage("");
                    }
                    logList.add(confoundLog);
                }
            } else {
                PlayMessageConfoundLog confoundLog = new PlayMessageConfoundLog();
                confoundLog.setMessageConfoundId(confound.getId());
                confoundLog.setState(0);

                // 计算文案的Md5值 先进行保存再请求
                String contentMd5 = MD5Utils.getMD5(confound.getConfoundContent(), StandardCharsets.UTF_8.displayName());
                confoundLog.setContentMd5(contentMd5);
                super.save(confoundLog);

                String optSerNo = this.getAppointGradeTextList(confound.getConfoundContent(), confound.getGroupNum());
                confoundLog.setOptSerialNo(optSerNo);
                if (StringUtils.isEmpty(optSerNo)) { //失败
                    confoundLog.setState(2);
                    confoundLog.setFailMessage("请求失败");
                }
                super.updateById(confoundLog);
            }
            super.saveBatch(logList);
        }

    }

    public PlayMessageConfoundLog getConfoundLogByOptSerNo(String optSerNo) {
        if (StringUtils.isEmpty(optSerNo)) {
            return null;
        }
        LambdaQueryWrapper<PlayMessageConfoundLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayMessageConfoundLog::getOptSerialNo, optSerNo);
        lambdaQueryWrapper.orderByDesc(PlayMessageConfoundLog::getId);
        lambdaQueryWrapper.last("limit 1");
        return super.getOne(lambdaQueryWrapper);
    }


    @Override
    public void handleConfoundText(CalledDTO inputDTO) {

        String jsonData = inputDTO.getData().toString();
        String optSerNo = inputDTO.getOptSerNo();
        if (StringUtils.isBlank(jsonData)) {
            log.info("handleConfoundText callback data is blank {}", optSerNo);
            return;
        }

        Called1100850405DTO data = JSONObject.parseObject(jsonData, Called1100850405DTO.class);
        String md5 = MD5Utils.getMD5(data.getOriginContent(), StandardCharsets.UTF_8.displayName());

        // 通过MD5 或者 操作编码查询
        PlayMessageConfoundLog confoundLog = this.queryByContentMd5OrSerialNo(md5, optSerNo);
        if (confoundLog == null) {
            log.info("handleConfoundText confoundLog is null {} {}", optSerNo, md5);
            return;
        }

        if (CollectionUtils.isEmpty(data.getDiscreteList())) {
            confoundLog.setState(2);
            confoundLog.setFailMessage("回调无结果");
        } else {
            confoundLog.setState(1);
            confoundLog.setFailMessage("");
            String joiningRegex = PlayConstants.joiningRegex;
            String resultContent = String.join(joiningRegex, data.getDiscreteList());
            confoundLog.setResultContent(resultContent);
        }
        confoundLog.setModifyTime(new Date());
        final boolean res = super.updateById(confoundLog);
        log.info("handleConfoundText-optSerNo-success-{},{},{}",optSerNo,JSON.toJSONString(confoundLog),res);
    }

    @Override
    public void handleConfoundImg(CalledDTO inputDTO) {
        PlayMessageConfoundLog confoundLog = this.getConfoundLogByOptSerNo(inputDTO.getOptSerNo());
        if (confoundLog == null) {
            log.info("handleConfoundImg confoundLog is null {}", inputDTO.getOptSerNo());
            return;
        }
        Called1100850508DTO data = JSONObject.parseObject(inputDTO.getData().toString(), Called1100850508DTO.class);
        if (data == null || CollectionUtils.isEmpty(data.getDiscreteList())) {
            confoundLog.setState(2);
            confoundLog.setFailMessage("回调无结果");
        } else {
            confoundLog.setState(1);
            confoundLog.setFailMessage("");
            String joiningRegex = PlayConstants.joiningRegex;
            List<String> contentList = new ArrayList<>();

            for (String discrete : data.getDiscreteList()) {
                String replace = "";
                int batchNum = 0;
                do {
                    batchNum++;
                    replace = UrlReplaceTools.replace(discrete);
                    log.info("handleConfoundImg replace [{}] [{}] [{}]", inputDTO.getOptSerNo(), discrete, replace);
                } while (StringUtils.isEmpty(replace) && batchNum < 3);
                if (StringUtils.isNotBlank(replace)) {
                    contentList.add(replace);
                }
            }
            String resultContent = contentList.stream()
                    .collect(Collectors.joining(joiningRegex));
            confoundLog.setResultContent(resultContent);
        }
        confoundLog.setModifyTime(new Date());
        super.updateById(confoundLog);
    }


    public String disperseImage(String imageUrl) {

        OpenApiResult<TgBaseOutputDTO> result = null;
        try {
            ThirdTgDisperseImageInputDTO dto = new ThirdTgDisperseImageInputDTO();
            dto.setImageUrl(imageUrl);
            dto.setNum(IMG_CONFOUND_SIZE);

            result = OpenApiClient.disperseImageByThirdKpTg(dto);

            log.info("PlayMessageConfoundLogServiceImpl disperseImage {} {}", JSON.toJSONString(dto), result.toString());
        } catch (Exception e) {
            log.info("PlayMessageConfoundLogServiceImpl disperseImage error", e);
        }
        return Optional.ofNullable(result)
                .map(OpenApiResult::getData)
                .map(TgBaseOutputDTO::getOptSerNo)
                .orElse("");
    }

    /**
     * 获取离散文字
     *
     * @param content
     * @param num     返回多少条
     * @return
     */
    public String getAppointGradeTextList(String content, Integer num) {
        OpenApiResult<TgBaseOutputDTO> result = null;
        try {
            ThirdTgAppointGradeTextListInputDTO dto = new ThirdTgAppointGradeTextListInputDTO();
            dto.setTextContent(content);

            // 获取条数，各离散等级条数限制 L1： 10 L2： 100 L3： 500 L4-L5： 1000
            dto.setLevel(5);
            dto.setNum(num);

            result = OpenApiClient.getAppointGradeTextListByThirdKpTg(dto);

            log.info("PlayMessageConfoundLogServiceImpl getAppointGradeTextList {} {}", JSON.toJSONString(dto), result.toString());
        } catch (Exception e) {
            log.info("PlayMessageConfoundLogServiceImpl getAppointGradeTextList error", e);
        }
        return Optional.ofNullable(result)
                .map(OpenApiResult::getData)
                .map(TgBaseOutputDTO::getOptSerNo)
                .orElse("");
    }

    public TgBaseOutputDTO<InsertEventOutputDTO> insertEvent(String activityRule) {
        TgBaseOutputDTO outputDTO = null;
        try {
            ThirdTgInsertEventInputDTO dto = new ThirdTgInsertEventInputDTO();
            dto.setActivityRule(activityRule);
            dto.setUnioUserNo(PlayConstants.unioUserNo);
            //域名分组传10
            dto.setDomainGroupTypeId(PlayConstants.domainGroupTypeId);
            //10：跳转落地URL 20：直接跳转原始URL
            dto.setRedirectWay(PlayConstants.redirectWa);

            OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.insertEventByThirdKpTg(dto);
            log.info("PlayMessageConfoundLogServiceImpl insertEvent {} {}", JSON.toJSONString(dto), result.toString());
            outputDTO = result.getData();
            Object object = Optional.ofNullable(outputDTO)
                    .map(TgBaseOutputDTO::getData)
                    .orElse(null);
            if (object != null) {
                outputDTO.setData(JSONObject.parseObject(object.toString(), InsertEventOutputDTO.class));
            }
        } catch (Exception e) {
            log.info("PlayMessageConfoundLogServiceImpl insertEvent error", e);
        }
        return outputDTO;
    }

    @Override
    public void retryingConfusion(String lockKey) {
        try {
            // 扫描失败 且 重试次数小于3得数据
            List<PlayMessageConfoundLog> logList = baseMapper.selectRetryingList();
            if (CollectionUtils.isEmpty(logList)) {
                return;
            }
            Map<Integer, PlayMessageConfound> confoundMap = new HashMap<>();
            for (PlayMessageConfoundLog confoundLog : logList) {
                if (confoundLog.getExecuteNum() >= 3){
                    confoundLog.setState(2);
                    confoundLog.setFailMessage("未收到结果回调,混淆失败");
                    if(confoundLog.getState().intValue() != 2) {
                        log.info("handleConfoundText-optSerNo-{},{}",confoundLog.getOptSerialNo(),JSON.toJSONString(confoundLog));
                        super.updateById(confoundLog);
                    }
                    continue;
                }
                PlayMessageConfound confound = confoundMap.get(confoundLog.getMessageConfoundId());
                if (confound == null) {
                    confound = playMessageConfoundService.getById(confoundLog.getMessageConfoundId());
                    if (confound == null) {
                        continue;
                    }
                    confoundMap.put(confound.getId(), confound);
                }
                this.retryingConfusion(confoundLog, confound);
            }
        } catch (Exception e) {
            log.info("retryingConfusion error", e);
        } finally {
            redisLock.unlock(lockKey);
        }
    }

    private void retryingConfusion(PlayMessageConfoundLog confoundLog, PlayMessageConfound confound) {
        if (confound.getMomentTypeId() == 2001) {
            String optSerNo = this.getAppointGradeTextList(confound.getConfoundContent(), confound.getGroupNum());
            confoundLog.setOptSerialNo(optSerNo);
            if (StringUtils.isEmpty(optSerNo)) {
                confoundLog.setState(2);
                confoundLog.setFailMessage("请求失败");
            } else {
                confoundLog.setState(3);
                confoundLog.setFailMessage("重试中");
            }
        } else if (confound.getMomentTypeId() == 2002) {
            String optSerNo = this.disperseImage(confound.getConfoundContent());
            confoundLog.setOptSerialNo(optSerNo);
            if (StringUtils.isEmpty(optSerNo)) {
                confoundLog.setState(2);
                confoundLog.setFailMessage("请求失败");
            } else {
                confoundLog.setState(3);
                confoundLog.setFailMessage("重试中");
            }
        } else if (confound.getMomentTypeId() == 2005) {
            TgBaseOutputDTO<InsertEventOutputDTO> outputDTO = this.insertEvent(confound.getConfoundContent());
            String optSerNo = Optional.ofNullable(outputDTO)
                    .map(TgBaseOutputDTO::getOptSerNo)
                    .orElse("");
            confoundLog.setOptSerialNo(optSerNo);
            String resultContent = Optional.ofNullable(outputDTO)
                    .map(TgBaseOutputDTO::getData)
                    .map(InsertEventOutputDTO::getActivityLinkAddress)
                    .orElse("");
            confoundLog.setResultContent(resultContent);
            if (StringUtils.isEmpty(resultContent)) { //失败
                confoundLog.setState(2);
                confoundLog.setFailMessage("请求失败");
            } else {
                confoundLog.setState(1);
                confoundLog.setFailMessage("");
            }
        } else {
            confoundLog.setOptSerialNo("");
            confoundLog.setState(2);
            confoundLog.setFailMessage("未支持的混淆类型");
        }
        confoundLog.setExecuteNum(confoundLog.getExecuteNum() + 1);
        confoundLog.setModifyTime(new Date());
        final int res = super.baseMapper.update(confoundLog, new UpdateWrapper<PlayMessageConfoundLog>().lambda().eq(PlayMessageConfoundLog::getId, confoundLog.getId()).ne(PlayMessageConfoundLog::getState, 1));
        log.info("handleConfoundText-optSerNo-retry-{},{},{}",confoundLog.getOptSerialNo(), JSON.toJSONString(confoundLog),res);
    }

    @Override
    public Page<QueryConfoundLogVO> page(QueryConfoundLogDTO dto) {
        Page<QueryConfoundLogVO> page = new Page<>(dto.getPage(), dto.getLimit());
        baseMapper.page(page, dto);
        if (CollectionUtils.isNotEmpty(page.getRecords())) {
            String splitRegex = PlayConstants.splitRegex;
            for (QueryConfoundLogVO record : page.getRecords()) {
                if (StringUtils.isEmpty(record.getResultContent())) {
                    continue;
                }
                List<String> resultList = ListTools.newArrayList(record.getResultContent().split(splitRegex));
                record.setResultList(resultList);
            }
        }
        return page;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void retry(ConfoundRetryDTO dto) {
        if (CollectionUtils.isEmpty(dto.getIds())) {
            return;
        }
//        List<PlayMessageConfoundLog> confoundLogs = super.listByIds(dto.getIds());
        LambdaQueryWrapper<PlayMessageConfoundLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(PlayMessageConfoundLog::getId,dto.getIds());
        // 不是成功的日志 才能重试
        lambdaQueryWrapper.ne(PlayMessageConfoundLog::getState, 1);
        List<PlayMessageConfoundLog> confoundLogs = super.list(lambdaQueryWrapper);
        Assert.isTrue(CollectionUtils.isNotEmpty(confoundLogs), "未找到可重试混淆记录");
        Map<Integer, PlayMessageConfound> confoundMap = new HashMap<>();
        for (PlayMessageConfoundLog confoundLog : confoundLogs) {
            PlayMessageConfound confound = confoundMap.get(confoundLog.getMessageConfoundId());
            if (confound == null) {
                confound = playMessageConfoundService.getById(confoundLog.getMessageConfoundId());
                if (confound == null) {
                    continue;
                }
                confoundMap.put(confound.getId(), confound);
            }
            this.retryingConfusion(confoundLog, confound);
        }
    }

    @Override
    public PlayMessageConfoundLog queryByContentMd5OrSerialNo(final String md5, final String serialNo) {
        LambdaQueryWrapper<PlayMessageConfoundLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayMessageConfoundLog::getOptSerialNo,serialNo);
        final PlayMessageConfoundLog confoundLog = this.getOne(queryWrapper);
        if(confoundLog != null){
            return confoundLog;
        }
        queryWrapper.clear();
        queryWrapper.eq(PlayMessageConfoundLog::getContentMd5, md5)
                .isNull(PlayMessageConfoundLog::getResultContent)
                .orderByDesc(PlayMessageConfoundLog::getCreateTime)
                .last("limit 1");
        return this.getOne(queryWrapper);
    }
}




