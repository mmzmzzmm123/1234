package com.ruoyi.api.staff.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.common.model.dto.ApiOssUploadSingleFileDto;
import com.ruoyi.api.common.model.vo.ApiOssUploadSingleFileVo;
import com.ruoyi.api.common.service.ApiFileService;
import com.ruoyi.api.staff.model.dto.ApiSelectStaffTrendsDto;
import com.ruoyi.api.staff.model.dto.ApiStaffTrendsFormDto;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.StaffStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.AudioUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.domain.StaffTrends;
import com.ruoyi.staff.mapper.StaffTrendsMapper;
import com.ruoyi.user.domain.UserLikeStaffTrendsRecord;
import com.ruoyi.user.mapper.UserLikeStaffTrendsRecordMapper;
import javazoom.jl.decoder.JavaLayerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/3 16:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiStaffTrendsService {

    private final UserLikeStaffTrendsRecordMapper userLikeStaffTrendsRecordMapper;
    private final StaffTrendsMapper staffTrendsMapper;
    private final ApiFileService apiFileService;

    /**
     * 获取店员动态数据
     *
     * @return 结果
     */
    public List<StaffTrends> selectStaffTrends() {
        StaffTrends select = new StaffTrends();
        select.setUserId(TokenUtils.getUserId());
        return staffTrendsMapper.selectStaffTrendsList(select);
    }

    /**
     * 查询店员动态数据（级联查询）
     *
     * @param dto 条件
     * @return 结果
     * */
    public List<StaffTrends> select(ApiSelectStaffTrendsDto dto){
        StaffTrends staffTrends = new StaffTrends();
        StaffInfo staffInfo = new StaffInfo();
        staffInfo.setState(StaffStateEnums.NORMAL.getCode());
        if (ObjectUtil.isNotNull(dto.getSex())){
            staffInfo.setSex(dto.getSex());
        }
        staffTrends.setUserId(dto.getUserId())
                .setStaffInfo(staffInfo);
        return staffTrendsMapper.selectJoinStaffInfo(staffTrends);
    }

    /**
     * 发布动态
     *
     * @param dto 数据
     * @return 结果
     */
    public R<Boolean> submit(ApiStaffTrendsFormDto dto) {
        log.info("发布动态：开始，数据：{}", dto);
        Long userId = TokenUtils.getUserId();
        Date now = DateUtils.getNowDate();
        StaffTrends insert = new StaffTrends();
        BeanUtils.copyBeanProp(insert, dto);
        insert.setUserId(userId)
                .setCreateTime(now)
                .setUpdateTime(now);
        // 录音文件上传
        if (ObjectUtil.isNotNull(dto.getVoiceFile())) {
            ApiOssUploadSingleFileDto uploadSingleFileDto = new ApiOssUploadSingleFileDto();
            uploadSingleFileDto.setFile(dto.getVoiceFile())
                    .setOssKey(dto.getOssKey());
            ApiOssUploadSingleFileVo uploadSingleFileVo = apiFileService.uploadSingleFile(uploadSingleFileDto);
            insert.setVoiceUrl(uploadSingleFileVo.getFileUrl());
            // 音频时长解析
            if (ObjectUtil.isNull(insert.getVoiceTime())) {
                try {
                    int audioDurationInSeconds = AudioUtils.getAudioDurationInSeconds(dto.getVoiceFile());
                    insert.setVoiceTime(Long.parseLong(audioDurationInSeconds + ""));
                } catch (Exception e) {
                    e.printStackTrace();
                    insert.setVoiceTime(0L);
                }
            }
        }
        staffTrendsMapper.insertStaffTrends(insert);
        log.info("发布动态：完成");
        return R.ok(Boolean.TRUE);
    }

    /**
     * 根据主键删除动态记录
     *
     * @param id 标识
     * @return 结果
     * */
    public Boolean deleteById(Long id) {
        log.info("根据主键删除动态记录：开始，参数：{}", id);
        Long userId = TokenUtils.getUserId();
        StaffTrends staffTrends = staffTrendsMapper.selectStaffTrendsById(id);
        if (!userId.equals(staffTrends.getUserId())){
            log.warn("根据主键删除动态记录：失败，删除记录数据与token用户不符----{},{}", userId, staffTrends);
            throw new ServiceException("根据主键删除动态记录：失败");
        }
        staffTrendsMapper.deleteStaffTrendsById(id);
        log.info("根据主键删除动态记录：完成");
        return Boolean.TRUE;
    }

    /**
     * 点赞店员动态
     *
     * @param staffTrendsId 动态标识
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean likeStaffTrends(Long staffTrendsId) {
        log.info("点赞店员动态：开始，参数：{}", staffTrendsId);
        Long userId = TokenUtils.getUserId();
        List<UserLikeStaffTrendsRecord> userLikeStaffTrendsRecords = selectUserLikeStaffTrendsRecord(userId, staffTrendsId);
        if (ObjectUtil.isNotEmpty(userLikeStaffTrendsRecords)) {
            log.warn("点赞店员动态：完成，已点赞该动态");
            return Boolean.TRUE;
        }
        // 插入用户点赞记录
        UserLikeStaffTrendsRecord insert = new UserLikeStaffTrendsRecord();
        insert.setUserId(userId)
                .setStaffTrendsId(staffTrendsId)
                .setCreateTime(DateUtils.getNowDate());
        userLikeStaffTrendsRecordMapper.insertUserLikeStaffTrendsRecord(insert);
        // 店员动态点赞数加一
        staffTrendsMapper.likeNumAddOneById(staffTrendsId);
        log.info("点赞店员动态：完成");
        return Boolean.TRUE;
    }


    /**
     * 取消店员动态数据
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelLikeStaffTrends(Long staffTrendsId) {
        log.info("取消店员动态数据：开始，参数：{}", staffTrendsId);
        Long userId = TokenUtils.getUserId();
        List<UserLikeStaffTrendsRecord> userLikeStaffTrendsRecords = selectUserLikeStaffTrendsRecord(userId, staffTrendsId);
        if (ObjectUtil.isEmpty(userLikeStaffTrendsRecords)){
            log.warn("取消店员动态数据：完成，暂无对应数据");
            return Boolean.TRUE;
        }
        // 删除对应记录
        Long[] deleteIds = userLikeStaffTrendsRecords.stream().map(UserLikeStaffTrendsRecord::getId).toArray(Long[]::new);
        userLikeStaffTrendsRecordMapper.deleteUserLikeStaffTrendsRecordByIds(deleteIds);
        // 店员动态点赞数减一
        staffTrendsMapper.likeNumSubtractOneById(staffTrendsId);
        log.info("取消店员动态数据：完成");
        return Boolean.TRUE;
    }

    /**
     * 根据用户标识和动态标识查询记录数据
     *
     * @param userId   用户标识
     * @param trendsId 动态标识
     * @return 结果
     */
    private List<UserLikeStaffTrendsRecord> selectUserLikeStaffTrendsRecord(Long userId, Long trendsId) {
        log.info("根据用户标识和动态标识查询记录数据：开始，参数：{}，{}", userId, trendsId);
        if (ObjectUtil.isNull(trendsId)) {
            log.warn("根据用户标识和动态标识查询记录数据：失败，参数为空");
            throw new ServiceException("参数为空", HttpStatus.ERROR);
        }
        List<UserLikeStaffTrendsRecord> voList = new ArrayList<>();
        UserLikeStaffTrendsRecord select = new UserLikeStaffTrendsRecord();
        select.setUserId(userId)
                .setStaffTrendsId(trendsId);
        voList = userLikeStaffTrendsRecordMapper.selectUserLikeStaffTrendsRecordList(select);
        log.info("根据用户标识和动态标识查询记录数据：完成，返回数据：{}", voList);
        return voList;
    }
}
