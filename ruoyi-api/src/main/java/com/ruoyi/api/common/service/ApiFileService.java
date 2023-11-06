package com.ruoyi.api.common.service;

import com.ruoyi.api.common.model.dto.ApiOssUploadSingleFileDto;
import com.ruoyi.api.common.model.vo.ApiOssUploadSingleFileVo;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.tencent.OssService;
import com.ruoyi.common.tencent.model.dto.OssUploadSingleFileDto;
import com.ruoyi.common.tencent.model.vo.OssUploadSingleFileVo;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LAM
 * @date 2023/9/21 0:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiFileService {

    private final OssService ossService;

    /**
     * 单文件上传
     *
     * @param dto 参数
     * @return 结果
     */
    public ApiOssUploadSingleFileVo uploadSingleFile(ApiOssUploadSingleFileDto dto) {
        log.info("单文件上传：开始，参数：{}", dto);
        ApiOssUploadSingleFileVo vo = new ApiOssUploadSingleFileVo();
        OssUploadSingleFileDto ossUploadSingleFileDto = new OssUploadSingleFileDto();
        String key = dto.getOssKey();
        if(StringUtils.isBlank(key)){
            key = "other";
        }
        ossUploadSingleFileDto.setFile(dto.getFile()).setOssKey(key);
        OssUploadSingleFileVo ossUploadSingleFileVo = ossService.uploadSingleFile(ossUploadSingleFileDto);
        BeanUtils.copyBeanProp(vo, ossUploadSingleFileVo);
        log.info("单文件上传：完成，返回参数：{}", vo);
        return vo;
    }
}
