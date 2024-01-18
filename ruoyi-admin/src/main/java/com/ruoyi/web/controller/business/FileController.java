package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.WordPageDTO;
import com.ruoyi.system.domain.vo.AnalysisPlayFileVO;
import com.ruoyi.system.service.AnalysisFileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private AnalysisFileService analysisFileService;

    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation(value = "解析剧本word文件内容")
    @PostMapping("/analysis/playWord")
    public R<WordPageDTO> analysisPlayWord(@RequestParam(value = "file") MultipartFile file) {
        try {
            return R.ok(analysisFileService.analysisPlayWord(file));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
    }

    @ApiOperation(value = "获取上传的剧本word文件内容")
    @PostMapping("/playWord")
    public R<AnalysisPlayFileVO> playWordContentList(@RequestBody WordPageDTO dto) {
        try {
            return R.ok(analysisFileService.playWordContentList(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
    }
}
