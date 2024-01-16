package com.ruoyi.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordUtil {
    private static final Logger log = LoggerFactory.getLogger(WordUtil.class);

    public static List<String> readWordDocumentByUrl(String wordPath) {
        List<String> list = new ArrayList<>();
        final InputStream inputStream = UrlTools.getInputStreamByUrl(wordPath);
        if (inputStream == null) {
            return list;
        }

        try {
            XWPFDocument document = new XWPFDocument(inputStream);
            // 读取段落
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                list.add(paragraph.getText());

                // 处理段落中的图片
                final List<XWPFRun> collect = paragraph.getRuns().stream().filter(it -> StringUtils.isEmpty(it.text())).collect(Collectors.toList());
                collect.forEach(xwpfRun -> {
                    List<XWPFPicture> pictures = xwpfRun.getEmbeddedPictures();
                    for (XWPFPicture picture : pictures) {
                        XWPFPictureData pictureData = picture.getPictureData();
                        String extension = pictureData.suggestFileExtension(); // 图片文件扩展名
                        byte[] bytes = pictureData.getData(); // 图片二进制数据
                        // 将图片二进制数据转换为Base64字符串
                        String base64Image = Base64.encodeBase64String(bytes);
                        list.add("data:image/"+extension+";base64,"+base64Image);
                        // 在这里可以将图片保存到文件或进行其他处理
//                        System.out.println("Picture found in paragraph: " + base64Image);
                    }
                });
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }
}