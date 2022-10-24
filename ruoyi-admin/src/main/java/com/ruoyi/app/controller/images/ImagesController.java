package com.ruoyi.app.controller.images;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @User hogan
 * @Time 2022/10/19 15:42
 * @e-mail hkcugwh@163.com
 **/
@RestController
@RequestMapping("/app/image")
@Slf4j
public class ImagesController {

    /**
     * 获取图片
     * @param url
     * @param response
     */
    @GetMapping("/getImage")
    public void getImage(@RequestParam(value = "url") String url, HttpServletResponse response) {
        try {
            BufferedImage image = ImageIO.read(Files.newInputStream(Paths.get(url)));
            response.setHeader("Cache-Control", "no-store ,no-cache");
            response.setContentType(ContentType.IMAGE_PNG.getMimeType());
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            log.info("getImage_IOException:{}" , ExceptionUtil.getExceptionMessage(e));
            throw new ServiceException("图片下载失败");
        }

    }

}
