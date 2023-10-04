package com.ruoyi.api.staff.service;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.common.model.dto.ApiOssUploadSingleFileDto;
import com.ruoyi.api.common.model.vo.ApiOssUploadSingleFileVo;
import com.ruoyi.api.common.service.ApiFileService;
import com.ruoyi.api.staff.model.dto.ApiStaffTrendsFormDto;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.AudioUtils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.staff.domain.StaffTrends;
import com.ruoyi.staff.mapper.StaffTrendsMapper;
import javazoom.jl.decoder.JavaLayerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
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
}
