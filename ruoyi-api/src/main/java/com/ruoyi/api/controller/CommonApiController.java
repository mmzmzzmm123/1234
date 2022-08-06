package com.ruoyi.api.controller;

import com.ruoyi.api.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@Api(value = "通用",tags = {"通用接口"})
@RestController
@RequestMapping("/api")
public class CommonApiController {
        private static final Logger log = LoggerFactory.getLogger(CommonApiController.class);


        @ApiOperation("通用下载")
        @ApiImplicitParams({
                @ApiImplicitParam(name = "fileUrl",value = "url",dataType = "string",example = "https://baidu.com/i.jpg",required = true),
        })
        @GetMapping("/common/download")
        public void fileDownload(String fileUrl, HttpServletResponse response) throws IOException {
            InputStream inputStream = null;
            ServletOutputStream outputStream = null;
            try {
                String filePath = "D:/ruoyi/uploadPath" +"/"+ FileUtil.interceptUrl(fileUrl);
                response.setHeader("Content-Disposition", "attachment;filename=" +fileUrl.substring(fileUrl.lastIndexOf("/")+1));
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
                inputStream = new FileInputStream(filePath);
                outputStream = response.getOutputStream();
                IOUtils.copy(inputStream, outputStream);
                outputStream.flush();
            }catch (Exception e) {
                log.error("下载文件失败:{}", e.getMessage());
            }finally {
                assert inputStream != null;
                assert outputStream != null;
                inputStream.close();
                outputStream.close();
            }
        }


}
