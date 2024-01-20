package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.config.ErrInfoConfig;
import com.ruoyi.common.core.controller.BaseController;
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
public class FileController extends BaseController {
    @Resource
    private AnalysisFileService analysisFileService;

    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation(value = "解析剧本word文件内容")
    @PostMapping("/analysis/playWord")
    public R<WordPageDTO> analysisPlayWord(@RequestParam(value = "file") MultipartFile file) {
        try {
            return R.ok(analysisFileService.analysisPlayWord(file, getLoginUser().getUserId()));
        } catch (Exception e) {
            logger.error("analysisPlayWord_err", e);
            return R.fail(10000, ErrInfoConfig.getDynmic(10000, e.getMessage()));
        }
    }

    @ApiOperation(value = "获取上传的剧本word文件内容")
    @PostMapping("/playWord")
    public R<AnalysisPlayFileVO> playWordContentList(@RequestBody WordPageDTO dto) {
        try {
            dto.setFileId(getLoginUser().getUserId().toString());
            return R.ok(analysisFileService.playWordContentList(dto));
        } catch (Exception e) {
            logger.error("playWordContentList_err", e);
            return R.fail(10000, ErrInfoConfig.getDynmic(10000, e.getMessage()));
        }
    }
}
