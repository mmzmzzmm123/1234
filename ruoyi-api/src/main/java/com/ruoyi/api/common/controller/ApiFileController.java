package com.ruoyi.api.common.controller;

import com.ruoyi.api.common.model.dto.ApiOssUploadSingleFileDto;
import com.ruoyi.api.common.model.vo.ApiOssUploadSingleFileVo;
import com.ruoyi.api.common.service.ApiFileService;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author LAM
 * @date 2023/9/21 0:03
 */
@Anonymous
@Api("api-文件相关接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class ApiFileController {

    private final ApiFileService service;

    @ApiOperation("单文件上传")
    @PostMapping("/uploadSingleFile")
    public R<ApiOssUploadSingleFileVo> uploadSingleFile(@Valid ApiOssUploadSingleFileDto dto) {
        return R.ok(service.uploadSingleFile(dto));
    }
}
