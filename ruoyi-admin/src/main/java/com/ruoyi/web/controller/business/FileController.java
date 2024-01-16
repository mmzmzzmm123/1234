package com.ruoyi.web.controller.business;

import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.utils.WordUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequestMapping("/file")
public class FileController {
    @RepeatSubmit(interval = 1000, message = "请求过于频繁")
    @ApiOperation(value = "解析剧本word文件内容")
    @PostMapping("/analysis/playWord")
    public R<List<String>> analysisPlayWord(@RequestParam(value = "fileUrl") String fileUrl) {
        return R.ok(WordUtil.readWordDocumentByUrl(fileUrl));
    }
}
