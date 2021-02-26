package com.stdiet.custom.utils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Document;
import com.stdiet.common.core.domain.AjaxResult;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class PdfUtils {

    /**
     * 生成PDF
     * @param templatePath PDF模板文件路径
     * @param filePath 目标文件路径
     * @param data 数据
     * @return
     */
    public static AjaxResult generatePdfFile(String templatePath, String filePath, Map<String,String> data){
        AjaxResult ajaxResult = AjaxResult.success();

        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;

        try {
            out = new FileOutputStream(filePath);// 输出流到新的pdf,没有b2.pdf时会创建
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            for(String key : data.keySet()){
                form.setField(key, data.get(key), true);
            }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();

            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = null;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                importPage = copy
                        .getImportedPage(new PdfReader(bos.toByteArray()), i);
                copy.addPage(importPage);
            }
            doc.close();

            //Runtime.getRuntime().exec("chmod 644 " + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ajaxResult = AjaxResult.error();
        } catch (IOException e) {
            e.printStackTrace();
            ajaxResult = AjaxResult.error();
        } catch (DocumentException e) {
            e.printStackTrace();
            ajaxResult = AjaxResult.error();
        }
        return ajaxResult;
    }
}
