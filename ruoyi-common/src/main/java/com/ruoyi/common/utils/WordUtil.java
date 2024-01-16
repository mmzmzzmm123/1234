package com.ruoyi.common.utils;

import com.ruoyi.common.core.domain.dto.play.PlayMessageDTO;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WordUtil {
    private static final Logger log = LoggerFactory.getLogger(WordUtil.class);

    private static final String REGEX = "<ms.*>(.*)</ms>";


    public static List<String> readWordDocument(String wordPath) {
        List<PlayMessageDTO> playMessageList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(wordPath);
            XWPFDocument document = new XWPFDocument(fis);
            // 读取段落
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                if (StringUtils.isEmpty(paragraph.getText())) {
                    continue;
                }

                System.out.println(paragraph.getText());
                Pattern pattern = Pattern.compile(REGEX);
                Matcher matcher = pattern.matcher(paragraph.getText());
                if (matcher.find()) {
                    System.out.println(matcher.group(1));
                }

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

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void main(String[] args) {
//        final List<String> strings = readWordDocument("C:\\Users\\Administrator\\Desktop\\Cinema XXI OKE (2).docx");
//        System.out.println(strings);

        String reg = "<ms[^>]*?>[\\s\\S]*?<\\/ms>";
        Pattern regExStyle = Pattern.compile(reg);

        String text = "<ms>Fake 1</ms>: group ap min\n" +
                "Fake 2: bisa jelaskan group apa ??\n" +
                "Fake 3: kenapa saya masuk sini\n" +
                "Fake 4: mana admin ??";
        Matcher m = regExStyle.matcher(text);
        if (m.find()) {
            System.out.println(m.group(0));
            System.out.println(m.group(0).length());
        }
    }
}
