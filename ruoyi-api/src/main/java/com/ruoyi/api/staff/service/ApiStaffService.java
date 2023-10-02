package com.ruoyi.api.staff.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PhoneUtil;
import com.ruoyi.api.common.model.dto.ApiOssUploadSingleFileDto;
import com.ruoyi.api.common.model.vo.ApiOssUploadSingleFileVo;
import com.ruoyi.api.common.service.ApiFileService;
import com.ruoyi.api.staff.model.dto.ApiStaffInfoDto;
import com.ruoyi.api.staff.model.vo.ApiStaffInfoVo;
import com.ruoyi.api.staff.model.vo.ApiStaffLevelConfigVo;
import com.ruoyi.api.staff.model.vo.ApiStaffPhotoVo;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.StaffStateEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.domain.StaffLevelConfig;
import com.ruoyi.staff.domain.StaffPhoto;
import com.ruoyi.staff.domain.StaffServiceConfig;
import com.ruoyi.staff.mapper.StaffInfoMapper;
import com.ruoyi.staff.mapper.StaffLevelConfigMapper;
import com.ruoyi.staff.mapper.StaffPhotoMapper;
import com.ruoyi.staff.mapper.StaffServiceConfigMapper;
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LAM
 * @date 2023/9/20 16:05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiStaffService {

    private final UserInfoMapper userInfoMapper;
    private final StaffLevelConfigMapper staffLevelConfigMapper;
    private final StaffInfoMapper staffInfoMapper;
    private final StaffPhotoMapper photoMapper;
    private final StaffServiceConfigMapper staffServiceConfigMapper;
    private final RedisCache redisCache;
    private final ApiFileService apiFileService;

    /**
     * 获取员工等级配置
     *
     * @return 结果
     */
    public List<ApiStaffLevelConfigVo> selectStaffLevelConfig() {
        log.info("获取员工等级配置：开始");
        List<ApiStaffLevelConfigVo> voList = new ArrayList<>();
        String key = RedisKeyConstants.STAFF_LEVEL_CONFIG_DATA;
        if (redisCache.hasKey(key)) {
            voList = redisCache.getCacheList(key);
            log.info("获取员工等级配置：完成，存在缓存，直接返回：{}", voList);
            return voList;
        }
        // 查询数据库
        List<StaffLevelConfig> staffLevelConfigs = staffLevelConfigMapper.selectStaffLevelConfigList(new StaffLevelConfig());
        for (StaffLevelConfig item : staffLevelConfigs) {
            ApiStaffLevelConfigVo vo = new ApiStaffLevelConfigVo();
            BeanUtils.copyBeanProp(vo, item);
            voList.add(vo);
        }
        log.info("获取员工等级配置：完成，返回数据：{}", voList);
        return voList;
    }

    /**
     * 根据用户标识查询员工信息
     *
     * @param userId 用户标识
     * @return 结果
     */
    public ApiStaffInfoVo selectByUserId(Long userId) {
        log.info("根据用户标识查询员工信息：开始，参数：{}", userId);
        ApiStaffInfoVo vo = new ApiStaffInfoVo();
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(userId);
        if (ObjectUtil.isNotNull(staffInfo)) {
            BeanUtils.copyBeanProp(vo, staffInfo);
            log.info("根据用户标识查询员工信息：完成，返回数据：{}", vo);
            return vo;
        }
        log.info("根据用户标识查询员工信息：完成，无员工信息");
        return null;
    }

    /**
     * 申请成为店员
     *
     * @param dto 数据
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> apply(ApiStaffInfoDto dto) {
        log.info("申请成为店员：开始，参数：{}", dto);
        // 判断该用户是否已申请
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(dto.getUserId());
        if (ObjectUtil.isNotNull(staffInfo)) {
            log.warn("申请成为店员：失败，已申请店员信息");
            return R.warn("亲爱的，您已申请过了哟");
        }
        // 校验手机号码是否正确
        if (!PhoneUtil.isMobile(dto.getPhone())){
            log.warn("申请成为店员：失败，手机号码不正确");
            return R.warn("亲爱的，帮忙输入个正确的手机号码呗");
        }
        Date now = DateUtils.getNowDate();
        // copy属性值
        staffInfo = new StaffInfo();
        BeanUtils.copyBeanProp(staffInfo, dto);
        staffInfo.setBirthDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, dto.getBirthDate()))
                .setCreateTime(now)
                .setUpdateTime(now);
        // 开始处理店员语音数据
        try {
            handleSoundRecordFile(staffInfo, dto);
        }catch (Exception e){
            log.warn("申请成为店员：异常，文件处理失败，信息：{}", e.getMessage());
            return R.warn("请上传m4a/mp3格式的音频文件");
        }
        // 先插入店员相册数据
        List<StaffPhoto> photoList = buildStaffPhotoData(dto.getUserId(), dto.getImageArr());
        if (ObjectUtil.isNotEmpty(photoList)){
            photoMapper.insertOfList(photoList);
        }
        // 推荐码处理
        handleReferralCode(staffInfo, dto);
        // 开始新增记录
        staffInfoMapper.insertStaffInfo(staffInfo);
        log.info("申请成为店员：完成");
        return R.ok(Boolean.TRUE);
    }

    /**
     * 修改申请数据
     *
     * @param dto 数据
     * @return 结果
     * */
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> updateApply(ApiStaffInfoDto dto) {
        log.info("修改申请数据：开始，参数：{}", dto);
        // 校验手机号码是否正确
        if (!PhoneUtil.isMobile(dto.getPhone())){
            log.warn("申请成为店员：失败，手机号码不正确");
            return R.warn("亲爱的，帮忙输入个正确的手机号码呗");
        }
        StaffInfo staffInfo = new StaffInfo();
        BeanUtils.copyBeanProp(staffInfo, dto);
        staffInfo.setBirthDate(DateUtils.dateTime(DateUtils.YYYY_MM_DD, dto.getBirthDate()))
                .setUpdateTime(DateUtils.getNowDate());
        // 开始处理店员语音数据
        try {
            handleSoundRecordFile(staffInfo, dto);
        }catch (Exception e){
            log.warn("申请成为店员：异常，文件处理失败，信息：{}", e.getMessage());
            return R.warn("请上传m4a/mp3格式的音频文件");
        }
        // 删除相册数据再插入
        photoMapper.deleteByUserId(dto.getUserId());
        List<StaffPhoto> staffPhotos = buildStaffPhotoData(dto.getUserId(), dto.getImageArr());
        if (ObjectUtil.isNotEmpty(staffPhotos)){
            photoMapper.insertOfList(staffPhotos);
        }
        // 推荐码处理
        handleReferralCode(staffInfo, dto);
        staffInfo.setState(StaffStateEnums.EXAMINE.getCode());
        staffInfoMapper.updateStaffInfo(staffInfo);
        log.info("修改申请数据：完成");
        return R.ok(Boolean.TRUE);
    }

    /**
     * 处理推荐码数据
     * */
    private void handleReferralCode(StaffInfo staffInfo, ApiStaffInfoDto dto){
        if (ObjectUtil.isNotNull(dto.getReferralCode())){
            UserInfo userInfo = selectByReferralCode(dto.getReferralCode());
            if (ObjectUtil.isNotNull(userInfo)){
                staffInfo.setReferralUserId(userInfo.getId());
            }
        }
    }

    /**
     * 处理录音文件
     *
     * @param staffInfo 店员信息
     * @param dto 数据
     * */
    private void handleSoundRecordFile(StaffInfo staffInfo, ApiStaffInfoDto dto) throws IOException, UnsupportedAudioFileException {
        // 开始处理店员语音数据
        if (ObjectUtil.isNotNull(dto.getSoundRecordFile())){
            ApiOssUploadSingleFileDto uploadSingleFileDto = new ApiOssUploadSingleFileDto();
            uploadSingleFileDto.setFile(dto.getSoundRecordFile())
                    .setOssKey(dto.getOssKey());
            ApiOssUploadSingleFileVo uploadSingleFileVo = apiFileService.uploadSingleFile(uploadSingleFileDto);
            staffInfo.setVoiceUrl(uploadSingleFileVo.getFileUrl())
                    .setVoiceTime((Long.parseLong(StringUtils.split(uploadSingleFileVo.getFileUrl(),"=")[1])/1000)%60);
        }
    }

    /**
     * 根据推荐码获取用户信息
     *
     * @param code 推荐吗
     * @return 结果
     * */
    private UserInfo selectByReferralCode(Long code){
        UserInfo select = new UserInfo();
        select.setReferralCode(code);
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoList(select);
        if (ObjectUtil.isNotEmpty(userInfos)){
            return userInfos.get(0);
        }
        return null;
    }

    /**
     * 构建店员相册数据
     *
     * @param userId 店员标识
     * @param images 相片数据字符串
     * @return 结果
     */
    private List<StaffPhoto> buildStaffPhotoData(Long userId, String images) {
        List<StaffPhoto> staffPhotos = new ArrayList<>();
        String[] imageArr = StringUtils.split(images, ",");
        Date now = DateUtils.getNowDate();
        for (String img : imageArr) {
            StaffPhoto temp = new StaffPhoto();
            temp.setUserId(userId)
                    .setImgUrl(img)
                    .setCreateTime(now)
                    .setUpdateTime(now);
            staffPhotos.add(temp);
        }
        return staffPhotos;
    }

    /**
     * 根据用户标识获取员工相册
     *
     * @param userId 用户标识
     * @return 结果
     * */
    public List<ApiStaffPhotoVo> selectPhotoByUserId(Long userId) {
        log.info("根据用户标识获取员工相册：开始，参数：{}", userId);
        List<ApiStaffPhotoVo> voList = new ArrayList<>();
        StaffPhoto select = new StaffPhoto();
        select.setUserId(userId);
        List<StaffPhoto> staffPhotos = photoMapper.selectStaffPhotoList(select);
        if (ObjectUtil.isNotEmpty(staffPhotos)){
            for (StaffPhoto item : staffPhotos){
                ApiStaffPhotoVo vo = new ApiStaffPhotoVo();
                BeanUtils.copyBeanProp(vo, item);
                voList.add(vo);
            }
        }
        log.info("根据用户标识获取员工相册：完成，返回数据：{}", voList);
        return voList;
    }

    /**
     * 获取店员配置接单服务id
     *
     * @param userId 店员用户标识
     * @return 结果
     * */
    public List<Long> selectServiceConfigIds(Long userId) {
        log.info("获取店员配置接单服务id：开始，参数：{}", userId);
        List<Long> serviceIdArr = new ArrayList<>();
        if (ObjectUtil.isNull(userId)){
            throw new ServiceException("亲爱的，你来自哪里");
        }
        StaffServiceConfig select = new StaffServiceConfig();
        select.setStaffId(userId);
        List<StaffServiceConfig> staffServiceConfigs = staffServiceConfigMapper.selectStaffServiceConfigList(select);
        if (ObjectUtil.isNotEmpty(staffServiceConfigs)){
            serviceIdArr = staffServiceConfigs.stream().map(StaffServiceConfig::getServiceId).collect(Collectors.toList());
        }
        log.info("获取店员配置接单服务id：完成，返回数据：{}", serviceIdArr);
        return serviceIdArr;
    }
}
