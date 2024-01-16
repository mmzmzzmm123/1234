package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.WordUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation(value = "解析剧本word文件内容")
    @PostMapping("/analysis/playWord")
    public R<List<String>> analysisPlayWord(@RequestParam(value = "file") @ApiParam("word资源文件") MultipartFile file) {
        return R.ok(WordUtil.readWordDocumentByUrl(file));
    }
}
