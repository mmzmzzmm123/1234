package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.constant.PlayStatusConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.app.OrderProduceRequest;
import com.ruoyi.common.core.domain.dto.play.*;
import com.ruoyi.common.core.domain.entity.Product;
import com.ruoyi.common.core.domain.entity.ProductSku;
import com.ruoyi.common.core.domain.entity.play.*;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.ProductCategoryType;
import com.ruoyi.common.enums.play.PushStateEnum;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.ProductTools;
import com.ruoyi.system.domain.dto.play.*;
import com.ruoyi.system.domain.vo.play.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.*;
import com.ruoyi.system.service.business.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ruoyi.common.core.domain.R.SUCCESS;

@Slf4j
@Service
public class PlayServiceImpl extends ServiceImpl<PlayMapper, Play> implements IPlayService {
    @Resource
    private PlayGroupPackMapper playGroupPackMapper;

    @Resource
    private PlayRobotPackMapper playRobotPackMapper;

    @Resource
    private PlayMessageMapper playMessageMapper;

    @Resource
    private PlayRobotPackService playRobotPackService;

    @Resource
    private PlayMessageService playMessageService;

    @Resource
    private PlayMessagePushService playMessagePushService;

    @Resource
    private PlayMessagePushDetailService playMessagePushDetailService;

    @Resource
    private IRobotService robotService;

    @Resource
    private PlayFileMapper playFileMapper;

    @Resource
    private PlayRepeatLogMapper playRepeatLogMapper;

    @Resource
    private PlayGroupInfoMapper playGroupInfoMapper;

    @Resource
    private PlayRobotGroupRelationMapper playRobotGroupRelationMapper;

    @Override
    @Transactional(rollbackFor=Exception.class)
    public R<String> create(PlayDTO dto) {
        //设置订单数据
        Long productId = dto.getProductId();
        if (null == productId || productId < 0) {
            Product product = SpringUtils.getBean(ProductMapper.class).selectOne(new LambdaQueryWrapper<Product>().select(Product::getProductId)
                    .eq(Product::getCategoryId, ProductCategoryType.PLAY.getId())
                    .last(" limit 1 "));
            if (null == product) {
                return R.fail(ErrInfoConfig.getDynmic(11001, "未获取到商品信息"));
            }
            productId = product.getProductId();
        }
        AjaxResult result = ProductTools.checkNormal(productId);
        if (result.isError()) {
            return R.fail(ErrInfoConfig.getDynmic(11001, "未获取到商品信息"));
        }
        handleOrder(productId, (Product) result.data(), dto.getName(), dto.getLoginUser());

        //todo 混淆处理?

        String playId = IdWorker.getIdStr();
        savePlay(dto, playId, productId);

        //外部群不能设置群包装
        if (dto.getGroupSource() == 0) {
            saveGroupPack(playId, dto.getGroupPack());
        }
        saveRobotPack(playId, dto.getPerformerList());
        //插入剧本消息表
        savePlayMessage(playId, dto.getPlayMessageList(), dto.getSendMechanism());

        //删除上传word文件
        if (StringUtils.isNotEmpty(dto.getFileId())) {
            playFileMapper.delete(new LambdaQueryWrapper<PlayFile>().eq(PlayFile::getFileId, dto.getFileId()));
        }

        return R.ok(playId);
    }

    //创建订单记录
    private void handleOrder(Long productId, Product product, String name, LoginUser loginUser) {
        long price = 0;
        Set<String> groupSet = new HashSet<>();

        List<ProductSku> skuList = new ArrayList<>();
        ProductSku productSku = SpringUtils.getBean(ProductServiceImpl.class).getOneSkuByProductId(productId);
        skuList.add(productSku);

        OrderProduceRequest request = new OrderProduceRequest();
        request.setTaskName(name);
        request.setProductId(productId);
        OrderProduceRequest.Params params = new OrderProduceRequest.Params();
        request.setParams(params);
        request.setLoginUser(loginUser);

        SpringUtils.getBean(OrderServiceImpl.class).orderStorage(request, product, skuList, groupSet, price, 2);
    }

    private void savePlayMessage(String playId, List<PlayMessageDTO> playMessageList, SendMechanism sendMechanism) {
        if (playMessageList.isEmpty()) {
            return;
        }

        List<PlayMessage> saveData = new ArrayList<>();
        String nick = null;
        for (PlayMessageDTO messageDTO : playMessageList) {
            PlayMessage playMessage = new PlayMessage();

            if (nick == null) {
                nick = messageDTO.getRobotNickname();
                playMessage.setIntervalTime(0);
            } else {
                if (nick.equals(messageDTO.getRobotNickname())) {
                    // 同一个发言人
                    playMessage.setIntervalTime(generateRandomNumber(sendMechanism.getMsgSepStart(), sendMechanism.getMsgSepEnd()));
                } else {
                    // 不同的发言人
                    playMessage.setIntervalTime(generateRandomNumber(sendMechanism.getPerformerSepStart(), sendMechanism.getPerformerSepEnd()));
                }

                nick = messageDTO.getRobotNickname();
            }

            playMessage.setPlayId(playId);
            playMessage.setRobotNickname(messageDTO.getRobotNickname());
            if (StringUtils.isNotEmpty(messageDTO.getCallRobotNickname())) {
                playMessage.setCallRobotNickname(messageDTO.getCallRobotNickname());
            }
            playMessage.setMessageSort(messageDTO.getMessageSort());
            // 设置uuid 混淆适配使用
            for (ContentJson contentJson : messageDTO.getMessageContent()) {
                contentJson.setUuid(IdWorker.getIdStr());
            }
            playMessage.setMessageContent(JSON.toJSONString(messageDTO.getMessageContent()));
            playMessage.setPlayErrorType(sendMechanism.getSendErrorType());

            //todo 自定义消息类型处理
//            for (ContentJson contentJson : messageDTO.getMessageContent()) {
//                if (contentJson.getMomentTypeId() == 2018) {
//                    contentJson.setSMateContent("");
//                }
//            }

            saveData.add(playMessage);
        }

        playMessageService.saveBatch(saveData);
    }

    private static int generateRandomNumber(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("最小值必须小于最大值");
        }

        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    private void saveRobotPack(String playId, List<Performer> performerList) {
        if (performerList.isEmpty()) {
            return;
        }

        List<PlayRobotPack> playRobotPackList = new ArrayList<>();
        for (Performer performer : performerList) {
            PlayRobotPack playRobotPack = new PlayRobotPack();
            BeanUtils.copyProperties(performer, playRobotPack);
            playRobotPack.setPlayId(playId);
            playRobotPackList.add(playRobotPack);
        }
        playRobotPackService.saveBatch(playRobotPackList);
    }

    private void saveGroupPack(String playId, GroupPack groupPack) {
        if (StringUtils.isEmpty(groupPack.getName()) && StringUtils.isEmpty(groupPack.getPic())) {
            return;
        }

        PlayGroupPack playGroupPack = new PlayGroupPack();
        playGroupPack.setPlayId(playId);
        playGroupPack.setName(String.join(",", groupPack.getName()));
        playGroupPack.setPic(String.join(",", groupPack.getPic()));
        playGroupPackMapper.insert(playGroupPack);
    }

    private void savePlay(PlayDTO dto, String id, Long productId) {
        Play play = new Play();
        BeanUtils.copyProperties(dto, play);
        play.setId(id);
        play.setProductId(productId);
        play.setSendMechanism(JSON.toJSONString(dto.getSendMechanism()));
        play.setAdMonitor(JSON.toJSONString(dto.getAdMonitor()));
        play.setPlayExt(JSON.toJSONString(dto.getPlayExt()));
        play.setUrlPool(String.join(",", dto.getUrlPool()));
        play.setUserId(dto.getLoginUser().getUserId());
        play.setMerchantId(dto.getLoginUser().getMerchantInfo().getMerchantId());
        if (dto.getGroupSource() == 1) {
            play.setGroupUrls(String.join(",", dto.getGroupUrls()));
            play.setGroupNum(dto.getGroupUrls().size());
        }
        if (dto.getStartType() == 0) {
            play.setStartGroupDate(new Date());
        }
        if (dto.getStartType() == 1) {
            play.setStartGroupDate(dto.getStartGroupDate());
        }
        play.setLockRobotStatus(dto.getPlayExt().getLockState());
        super.save(play);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public R<String> updatePlay(PlayDTO dto) {
        if (StringUtils.isEmpty(dto.getId())) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        Play play = super.getById(dto.getId());
        if (null == play) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "数据不存在"));
        }

        play.setName(dto.getName());
        play.setUrlPool(String.join(",", dto.getUrlPool()));
        play.setTargetCountyCode(dto.getTargetCountyCode());
        play.setTargetCountyName(dto.getTargetCountyName());
        play.setSendMechanism(JSON.toJSONString(dto.getSendMechanism()));
        play.setAdMonitor(JSON.toJSONString(dto.getAdMonitor()));
        play.setPlayExt(JSON.toJSONString(dto.getPlayExt()));
        updateById(play);

        //剧本内容
        playMessageMapper.delete(new LambdaQueryWrapper<PlayMessage>().eq(PlayMessage::getPlayId, dto.getId()));
        savePlayMessage(dto.getId(), dto.getPlayMessageList(), dto.getSendMechanism());

        //删除上传word文件
        if (StringUtils.isNotEmpty(dto.getFileId())) {
            playFileMapper.delete(new LambdaQueryWrapper<PlayFile>().eq(PlayFile::getFileId, dto.getFileId()));
        }

        return R.ok();
    }

    @Override
    public R<PlayVO> info(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        Play play = super.getById(playId);
        if (null == play) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "数据不存在"));
        }

        PlayVO ret = new PlayVO();
        BeanUtils.copyProperties(play, ret);
        ret.setSendMechanism(play.convertSendMechanismStr());
        ret.setAdMonitor(play.convertAdMonitorStr());
        ret.setPlayExt(play.convertPlayExtStr());
        ret.setUrlPool(Arrays.asList(play.getUrlPool().split(",")));
        if (play.getGroupSource() == 1) {
            ret.setGroupUrls(Arrays.asList(play.getGroupUrls().split(",")));
        }

        PlayGroupPack playGroupPack = playGroupPackMapper.selectOne(new LambdaQueryWrapper<PlayGroupPack>()
                .eq(PlayGroupPack::getPlayId, playId).last(" limit 1 "));
        if (null != playGroupPack) {
            GroupPack groupPack = new GroupPack();
            if (StringUtils.isNotEmpty(playGroupPack.getName())) {
                groupPack.setName(Arrays.asList(playGroupPack.getName().split(",")));
            }
            if (StringUtils.isNotEmpty(playGroupPack.getPic())) {
                groupPack.setPic(Arrays.asList(playGroupPack.getPic().split(",")));
            }
            ret.setGroupPack(groupPack);
        }

        List<PlayRobotPack> robotPackList = playRobotPackMapper.selectList(new QueryWrapper<PlayRobotPack>()
                .lambda().eq(PlayRobotPack::getPlayId, playId));
        if (!robotPackList.isEmpty()) {
            List<Performer> performerList = new ArrayList<>();
            for (PlayRobotPack robotPack : robotPackList) {
                Performer performer = new Performer();
                BeanUtils.copyProperties(robotPack, performer);
                performerList.add(performer);
            }
            ret.setPerformerList(performerList);
        }

        List<PlayMessage> playMessageList = playMessageMapper.selectList(new QueryWrapper<PlayMessage>()
                .lambda().eq(PlayMessage::getPlayId, playId));
        if (!playMessageList.isEmpty()) {
            List<PlayMessageDTO> playMessageDTOList = new ArrayList<>();
            for (PlayMessage playMessage : playMessageList) {
                PlayMessageDTO playMessageDTO = new PlayMessageDTO();
                BeanUtils.copyProperties(playMessage, playMessageDTO);
                playMessageDTO.setMessageContent(JSONArray.parseArray(playMessage.getMessageContent(), ContentJson.class));
                playMessageDTOList.add(playMessageDTO);
            }
            ret.setPlayMessageList(playMessageDTOList);
        }

        return R.ok(ret);
    }

    @Override
    public AdMonitor adInfo(String playId) {
        Play play = super.getOne(new QueryWrapper<Play>().lambda().select(Play::getAdMonitor)
                .eq(Play::getId,playId).last(" limit 1 "));
        if (null == play || StringUtils.isEmpty(play.getAdMonitor())) {
            return null;
        }

        return play.convertAdMonitorStr();
    }

    @Override
    public R<String> updateAdInfo(String playId, AdMonitor dto) {
        boolean status = super.update(null, new LambdaUpdateWrapper<Play>()
                .eq(Play::getId, playId)
                .in(Play::getState, Arrays.asList(PlayStatusConstants.DISPATCH, PlayStatusConstants.IN_PROGRESS, PlayStatusConstants.SUSPEND))
                .set(Play::getAdMonitor, JSON.toJSONString(dto)));
        if (!status) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "修改失败"));
        }

        //调用接口同步广告配置
        List<PlayGroupInfo> playGroupInfos = playGroupInfoMapper.selectGroupInfoByPlayId(playId);
        if (playGroupInfos.isEmpty()) {
            return R.ok();
        }

        GroupService groupService = SpringUtils.getBean(GroupService.class);
        for (PlayGroupInfo info : playGroupInfos) {
            groupService.setBotAdMonitor(info.getGroupId());
        }

        return R.ok();
    }

    @Override
    public Page<QueryPlayVO> page(QueryPlayDTO dto) {
        Page<QueryPlayVO> page = new Page<>(dto.getPage(), dto.getLimit());
        baseMapper.selectPage(page, dto);
        if (CollectionUtils.isEmpty(page.getRecords())) {
            return page;
        }
        List<String> ids = page.getRecords().stream()
                .map(QueryPlayVO::getId)
                .collect(Collectors.toList());
        List<PlayTaskProgressVO> taskProgressList = this.selectTaskProgress(ids);
        Map<String, PlayTaskProgressVO> map = taskProgressList.stream()
                .collect(Collectors.toMap(PlayTaskProgressVO::getPlayId, Function.identity(), (k1, k2) -> k2));
        for (QueryPlayVO record : page.getRecords()) {
            record.setPlayTaskProgressVO(map.get(record.getId()));
        }
        return page;
    }


    @Override
    public List<PlayTaskProgressVO> taskProgress(QueryTaskProgressDTO dto) {
        if (CollectionUtils.isEmpty(dto.getPlayIds())) {
            return null;
        }
        return this.selectTaskProgress(dto.getPlayIds());
    }

    private List<PlayTaskProgressVO> selectTaskProgress(List<String> playIds) {
        // 查询群维度的统计数据
        List<PlayTaskProgressVO> voList = baseMapper.selectTaskGroupProgress(playIds);
        // 再查询群消息维度的数据
        List<PlayTaskProgressVO> messageList = baseMapper.selectTaskProgress(playIds);
        Map<String, PlayTaskProgressVO> messageMap = messageList.stream()
                .collect(Collectors.toMap(PlayTaskProgressVO::getPlayId, Function.identity(), (k1, k2) -> k2));

        for (PlayTaskProgressVO vo : voList) {
            PlayTaskProgressVO messageVO = messageMap.get(vo.getPlayId());
            if (messageVO != null) {
                vo.setTotalNum(messageVO.getTotalNum());
                vo.setCurrentNum(messageVO.getCurrentNum());
                vo.setSendSuccessNum(messageVO.getSendSuccessNum());
                vo.setSendFailNum(messageVO.getSendFailNum());
            }
            vo.setTotalProgress(this.calculate(vo.getTotalNum(), vo.getCurrentNum()));
            vo.setGroupProgress(this.calculate(vo.getGroupTotalNum(), vo.getGroupCurrentNum()));
            vo.setPackProgress(this.calculate(vo.getPackTotalNum(), vo.getPackCurrentNum()));
            vo.setJoinGroupProgress(this.calculate(vo.getJoinGroupTotalNum(), vo.getJoinGroupCurrentNum()));
        }
        return voList;
    }

    /**
     * 计算百分比
     * @param totalNum
     * @param currentNum
     * @return
     */
    private BigDecimal calculate(Integer totalNum, Integer currentNum) {
        BigDecimal bigDecimal = new BigDecimal("0");
        bigDecimal.setScale(2);
        if (totalNum == null || totalNum.equals(0)) {
            return bigDecimal;
        }
        if (currentNum == null || currentNum.equals(0)) {
            return bigDecimal;
        }
        if (currentNum > totalNum) {
            bigDecimal = new BigDecimal("100");
            return bigDecimal;
        }
        BigDecimal multiply = new BigDecimal("100");
        BigDecimal totalBigDecimal = new BigDecimal(totalNum);
        BigDecimal currentBigDecimal = new BigDecimal(currentNum);
        bigDecimal = currentBigDecimal.divide(totalBigDecimal, 4, RoundingMode.UP);
        bigDecimal = bigDecimal.multiply(multiply);
        bigDecimal = bigDecimal.setScale(2);
        return bigDecimal;
    }

    @Override
    public List<PlayGroupProgressVO> groupProgress(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return null;
        }
        List<PlayGroupProgressVO> taskProgressList = baseMapper.selectGroupProgress(playId);
        return taskProgressList;
    }

    @Override
    public Page<QueryPushDetailVO> pushDetailPage(QueryPushDetailDTO dto) {
        Page<QueryPushDetailVO> page = new Page<>(dto.getPage(), dto.getLimit());
        baseMapper.selectPushDetailPage(page, dto);
        return page;
    }

    @Override
    public void setSpeed(SetSpeedDTO dto) {
        Play play = super.getById(dto.getPlayId());
        Assert.notNull(play, "炒群任务不存在");
        play.setSpeed(dto.getSpeed());
        super.updateById(play);
    }

    @Override
    public RobotStatisticsVO robotStatistics(String playId) {
        RobotStatisticsVO vo = new RobotStatisticsVO();
        if (StringUtils.isNotEmpty(playId)){
            return vo;
        }
        RobotStatisticsVO groupStatisticsVO = playMessagePushService.getRobotStatisticsVO(playId);
        // 统计群维度数据
        if (groupStatisticsVO != null) {
            vo.setGroupNum(groupStatisticsVO.getGroupNum());
            vo.setGroupClosureNum(groupStatisticsVO.getGroupClosureNum());
            vo.setJoinGroupFailNum(groupStatisticsVO.getJoinGroupFailNum());
        } else {
            vo.setGroupNum(0);
            vo.setGroupClosureNum(0);
            vo.setJoinGroupFailNum(0);
        }

        // 统计号维度数据
        List<PlayMessagePushDetail> pushDetails = playMessagePushDetailService.listByPlayIdStatistics(playId);
        List<String> navyRobotIds = new ArrayList<>();// 水军号
        List<String> spareRobotIds = new ArrayList<>();// 备用号
        for (PlayMessagePushDetail pushDetail : pushDetails) {
            String robotId = pushDetail.getRobotId();
            if (StringUtils.isNotEmpty(robotId)){
                navyRobotIds.add(robotId);
            }
            String spareRobot = pushDetail.getSpareRobot();
            if (StringUtils.isNotEmpty(spareRobot)){
                String[] split = spareRobot.split(",");
                spareRobotIds.addAll(Arrays.asList(split));
            }
        }
        vo.setNavyRobotNum(navyRobotIds.size());
        vo.setSpareRobotNum(spareRobotIds.size());

        // 统计号的双向、封号数
        navyRobotIds.addAll(spareRobotIds);
        RobotStatisticsVO robotStatisticsVO = robotService.getRobotStatisticsVO(navyRobotIds);
        if (robotStatisticsVO != null) {
            vo.setRobotClosureNum(robotStatisticsVO.getRobotClosureNum());
            vo.setBidirectionalRobotNum(robotStatisticsVO.getBidirectionalRobotNum());
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> repeatPlay(String playId, LoginUser loginUser) {
        if (StringUtils.isEmpty(playId)) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        Play play = super.getById(playId);
        if (null == play) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "数据不存在"));
        }

        PlayExt playExt = play.convertPlayExtStr();
        if (null == playExt || playExt.getLockState() != 1) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "该剧本未配置锁定水军"));
        }

        R<PlayVO> infoRet  = this.info(playId);
        if (infoRet.getCode() != SUCCESS) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "获取群信息失败"));
        }

        PlayDTO dto = new PlayDTO();
        BeanUtils.copyProperties(infoRet.getData(), dto);
        dto.setLoginUser(loginUser);

        R<String> ret = this.create(dto);
        if (ret.getCode() != SUCCESS) {
            return R.fail(ErrInfoConfig.getDynmic(10000, "创建剧本失败"));
        }
        String newPlayId = ret.getData();

        List<PlayGroupInfo> playGroupInfos = playGroupInfoMapper.selectGroupInfoByPlayId(play.getId());
        if (playGroupInfos.isEmpty()) {
            return R.ok();
        }

        List<PlayGroupInfo> savePlayGroupData = new ArrayList<>();
        for (PlayGroupInfo item : playGroupInfos) {
            PlayGroupInfo playGroupInfo = new PlayGroupInfo();
            BeanUtils.copyProperties(item, playGroupInfo);
            playGroupInfo.setPlayId(newPlayId);
            savePlayGroupData.add(playGroupInfo);
        }
        SpringUtils.getBean(PlayGroupInfoServiceImpl.class).saveBatch(savePlayGroupData);

        //保存重复炒日志
        PlayRepeatLog playRepeatLog = new PlayRepeatLog();
        playRepeatLog.setPlayId(playId);
        playRepeatLogMapper.insert(playRepeatLog);

        return R.ok(newPlayId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateState(PlayStateDTO dto) {
        if (dto == null || dto.getPlayIds().isEmpty()) {
            return Boolean.FALSE;
        }

        switch (dto.getState()) {
            case PlayStatusConstants.SUSPEND:
                this.suspendPlay(dto.getPlayIds());
                break;
            case PlayStatusConstants.CANCEL:
                this.cancelPlay(dto.getPlayIds());
                break;
            case PlayStatusConstants.IN_PROGRESS:
                this.startPlay(dto.getPlayIds());
                break;
        }
        return Boolean.TRUE;
    }

    private void suspendPlay(List<String> ids) {
        for (String id : ids) {
            boolean ret = super.update(null, new UpdateWrapper<Play>().lambda()
                    .eq(Play::getId, id)
                    .in(Play::getState, Collections.singletonList(PlayStatusConstants.IN_PROGRESS))
                    .set(Play::getState, PlayStatusConstants.SUSPEND));
            if (ret) {
                SpringUtils.getBean(PlayMessagePushService.class).update(null, new UpdateWrapper<PlayMessagePush>().lambda()
                        .eq(PlayMessagePush::getPlayId, id)
                        .eq(PlayMessagePush::getPushState, PushStateEnum.ING.getKey())
                        .set(PlayMessagePush::getPushState, PushStateEnum.USER_STOP.getKey()));
            }
        }
    }

    private void cancelPlay(List<String> ids) {
        for (String id : ids) {
            boolean ret = super.update(null, new UpdateWrapper<Play>().lambda()
                    .eq(Play::getId, id)
                    .in(Play::getState, Arrays.asList(PlayStatusConstants.IN_PROGRESS, PlayStatusConstants.SUSPEND))
                    .set(Play::getState, PlayStatusConstants.CANCEL));
            if (ret) {
                SpringUtils.getBean(PlayMessagePushService.class).update(null, new UpdateWrapper<PlayMessagePush>().lambda()
                        .eq(PlayMessagePush::getPlayId, id)
                        .in(PlayMessagePush::getPushState, Arrays.asList(PushStateEnum.ING.getKey(), PushStateEnum.USER_STOP.getKey()))
                        .set(PlayMessagePush::getPushState, PushStateEnum.CANCEL.getKey()));
            }
        }
    }

    private void startPlay(List<String> ids) {
        for (String id : ids) {
            boolean ret = super.update(null, new UpdateWrapper<Play>().lambda()
                    .eq(Play::getId, id)
                    .in(Play::getState, Collections.singletonList(PlayStatusConstants.SUSPEND))
                    .set(Play::getState, PlayStatusConstants.IN_PROGRESS));
            if (ret) {
                SpringUtils.getBean(PlayMessagePushService.class).update(null, new UpdateWrapper<PlayMessagePush>().lambda()
                        .eq(PlayMessagePush::getPlayId, id)
                        .in(PlayMessagePush::getPushState, Collections.singletonList(PushStateEnum.USER_STOP.getKey()))
                        .set(PlayMessagePush::getPushState, PushStateEnum.USER_STOP.getKey()));
            }
        }
    }

    @Override
    public R<String> releaseUser(String playId) {
        if (StringUtils.isEmpty(playId)) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "参数错误"));
        }

        Play play = super.getById(playId);
        if (null == play) {
            return R.fail(ErrInfoConfig.getDynmic(11000, "数据不存在"));
        }

        PlayExt playExt = play.convertPlayExtStr();
        if (null == playExt || playExt.getLockState() != 1) {
            return R.ok();
        }

        //已完成和已取消才可进行
        if (play.getState() != PlayStatusConstants.CANCEL && play.getState() != PlayStatusConstants.FINISHED) {
            return R.ok();
        }

        //获取剧本关联群
        List<PlayGroupInfo> playGroupInfos = playGroupInfoMapper.selectGroupInfoByPlayId(playId);
        if (playGroupInfos.isEmpty()) {
            return R.ok();
        }

        //获取群锁定水军
        List<String> groupIds = playGroupInfos.stream().map(PlayGroupInfo::getGroupId).collect(Collectors.toList());
        List<PlayRobotGroupRelation> playRobotGroupRelationList = playRobotGroupRelationMapper.selectWaitOutGroupByGroupIds(groupIds);
        if (null == playRobotGroupRelationList) {
            return R.ok();
        }

        //释放水军
        for (PlayRobotGroupRelation playRobotGroupRelation : playRobotGroupRelationList) {

        }

        //todo 退群

        return R.ok();
    }

    @Override
    public void jobReleaseRobot() {
        //查询配置锁定水军表数据
        //两天内是否有重复炒日志
    }
}
