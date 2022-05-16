package work.cib.word;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

//https://blog.csdn.net/weixin_43171019/article/details/94384900?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164627851416780274152024%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=164627851416780274152024&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~hot_rank-3-94384900.nonecase&utm_term=poi+word%E5%B7%A5%E5%85%B7%E5%8C%85&spm=1018.2226.3001.4450
public class word1 {
    private String tempLocalPath;
    private XWPFDocument xwpfDocument = null;
    private FileInputStream inputStream = null;
    private OutputStream outputStream = null;

    public word1(){

    }
    public word1(String tempLocalPath){
        this.tempLocalPath = tempLocalPath;
    }

    /**
     *  设置模板路径
     */
    public void setTempLocalPath(String tempLocalPath) {
        this.tempLocalPath = tempLocalPath;
    }

    /**
     *  初始化
     */
    public void init() throws IOException{
        inputStream = new FileInputStream(new File(this.tempLocalPath));
        xwpfDocument = new XWPFDocument(inputStream);
    }

    /**
     * 导出方法
     * @param params  表格里的数据列表
     * @param orgFullName   需要替换的页眉的公司的名称
     * @param logoFilePath  需要替换的页眉的Logo的图片的地址
     * @param tableIndex    需替换的第几个表格的下标
     * @param textMap       需替换的文本的数据入参
     */
    public boolean export(List<Map<String,String>> params,String orgFullName,String logoFilePath, int tableIndex,Map<String,String> textMap) throws Exception{
        //createWaterMark(xwpfDocument);
        createHeader(xwpfDocument,orgFullName,logoFilePath);
        replaceText(xwpfDocument,textMap);
        this.insertValueToTable(xwpfDocument,params,tableIndex);
        return true;
    }

    //添加水印  本示例没有用到水印  如果想添加水印  可以在导出方法里调用此方法
    public void createWaterMark(XWPFDocument doc){
        XWPFHeaderFooterPolicy policy=doc.getHeaderFooterPolicy();
        policy.createWatermark("中国华西监理信息管理平台");

    }



    /**
     * 替换非表格埋点值
     * @param textMap  需要替换的文本入参
     */
    public void replaceText(XWPFDocument xwpfDocument,Map<String,String> textMap){
        List<XWPFParagraph> paras=xwpfDocument.getParagraphs();
        Set<String> keySet=textMap.keySet();
        for (XWPFParagraph para : paras) {
            //当前段落的属性
            System.out.println("打印获取到的段落的每一行数据++++++++>>>>>>>"+para.getText());
            String str=para.getText();
            System.out.println("========================>>>>>>"+para.getParagraphText());

            List<XWPFRun> list=para.getRuns();
            for(XWPFRun run:list){
                for(String key:keySet){
                    if(key.equals(run.text())){
                        run.setText(textMap.get(key),0);
                    }
                }
            }
        }
    }


    /**
     * 循环填充表格内容
     */
    private   void insertValueToTable(XWPFDocument xwpfDocument, List<Map<String,String>> params, int tableIndex) throws Exception {
        try {
            List<XWPFTable> tableList = xwpfDocument.getTables();
            if (tableList.size() <= tableIndex) {
                throw new Exception("tableIndex对应的表格不存在");
            }
            XWPFTable table = tableList.get(tableIndex);
            List<XWPFTableRow> rows = table.getRows();
            if (rows.size() < 2) {
                throw new Exception("tableIndex对应表格应该为2行");
            }
            //模板的那一行
            XWPFTableRow tmpRow = rows.get(1);
            List<XWPFTableCell> tmpCells = null;
            List<XWPFTableCell> cells = null;
            XWPFTableCell tmpCell = null;
            tmpCells = tmpRow.getTableCells();


            String cellText = null;
            String cellTextKey = null;
            Map<String, Object> totalMap = null;
            for (int i = 0, len = params.size(); i < len; i++) {
                Map<String, String> map = params.get(i);
                // 创建新的一行
                XWPFTableRow row = table.createRow();
                // 获取模板的行高 设置为新一行的行高
                row.setHeight(tmpRow.getHeight());
                cells = row.getTableCells();
                for (int k = 0, klen = cells.size(); k < klen; k++) {
                    tmpCell = tmpCells.get(k);
                    XWPFTableCell cell = cells.get(k);
                    cellText = tmpCell.getText();
                    if (StringUtils.isNotBlank(cellText)) {
                        //转换为mapkey对应的字段
                        cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
                        if (map.containsKey(cellTextKey)) {
                            // 填充内容 并且复制模板行的属性
                            setCellText(tmpCell, cell, map.get(cellTextKey));
                        }
                    }
                }

            }
            // 删除模版行
            table.removeRow(1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  复制模板行的属性
     */
    private void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell,String text) throws Exception {

        CTTc cttc2 = tmpCell.getCTTc();
        CTTcPr ctPr2 = cttc2.getTcPr();
        CTTc cttc = cell.getCTTc();
        CTTcPr ctPr = cttc.addNewTcPr();
        if (ctPr2.getTcW() != null) {
            ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
        }
        if (ctPr2.getVAlign() != null) {
            ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
        }
        if (cttc2.getPList().size() > 0) {
            CTP ctp = cttc2.getPList().get(0);
            if (ctp.getPPr() != null) {
                if (ctp.getPPr().getJc() != null) {
                    cttc.getPList().get(0).addNewPPr().addNewJc()
                            .setVal(ctp.getPPr().getJc().getVal());
                }
            }
        }
        if (ctPr2.getTcBorders() != null) {
            ctPr.setTcBorders(ctPr2.getTcBorders());
        }

        XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
        XWPFParagraph cellP = cell.getParagraphs().get(0);
        XWPFRun tmpR = null;
        if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {
            tmpR = tmpP.getRuns().get(0);
        }
        XWPFRun cellR = cellP.createRun();
        cellR.setText(text);
        // 复制字体信息
        if (tmpR != null) {
            if(!cellR.isBold()){
                cellR.setBold(tmpR.isBold());
            }
            cellR.setItalic(tmpR.isItalic());
            cellR.setUnderline(tmpR.getUnderline());
            cellR.setColor(tmpR.getColor());
            cellR.setTextPosition(tmpR.getTextPosition());
            if (tmpR.getFontSize() != -1) {
                cellR.setFontSize(tmpR.getFontSize());
            }
            if (tmpR.getFontFamily() != null) {
                cellR.setFontFamily(tmpR.getFontFamily());
            }
            if (tmpR.getCTR() != null) {
                if (tmpR.getCTR().isSetRPr()) {
                    CTRPr tmpRPr = tmpR.getCTR().getRPr();
//                    CTFonts font = tmpR.getCTR().addNewRPr().addNewRFonts();
                    System.out.println("tmpRPr.getRFonts()===="+tmpRPr.getRFontsArray(0));
                    //下面这个if被龚宇修改过
                    if (tmpRPr.getRFontsArray(0)!=null) {
                        CTFonts tmpFonts = tmpRPr.getRFontsArray(0);
                        CTRPr cellRPr = cellR.getCTR().isSetRPr() ?
                                cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
                        CTFonts cellFonts = cellRPr.getRFontsArray(0)!=null ?
                                cellRPr.getRFontsArray(0): cellRPr.addNewRFonts();
                        cellFonts.setAscii(tmpFonts.getAscii());
                        cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
                        cellFonts.setCs(tmpFonts.getCs());
                        cellFonts.setCstheme(tmpFonts.getCstheme());
                        cellFonts.setEastAsia(tmpFonts.getEastAsia());
                        cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
                        cellFonts.setHAnsi(tmpFonts.getHAnsi());
                        cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
                    }
                }
            }

        }
        // 复制段落信息
        cellP.setAlignment(tmpP.getAlignment());
        cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
        cellP.setBorderBetween(tmpP.getBorderBetween());
        cellP.setBorderBottom(tmpP.getBorderBottom());
        cellP.setBorderLeft(tmpP.getBorderLeft());
        cellP.setBorderRight(tmpP.getBorderRight());
        cellP.setBorderTop(tmpP.getBorderTop());
        cellP.setPageBreak(tmpP.isPageBreak());
        if (tmpP.getCTP() != null) {
            if (tmpP.getCTP().getPPr() != null) {
                CTPPr tmpPPr = tmpP.getCTP().getPPr();
                CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP
                        .getCTP().getPPr() : cellP.getCTP().addNewPPr();
                // 复制段落间距信息
                CTSpacing tmpSpacing = tmpPPr.getSpacing();
                if (tmpSpacing != null) {
                    CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr
                            .getSpacing() : cellPPr.addNewSpacing();
                    if (tmpSpacing.getAfter() != null) {
                        cellSpacing.setAfter(tmpSpacing.getAfter());
                    }
                    if (tmpSpacing.getAfterAutospacing() != null) {
                        cellSpacing.setAfterAutospacing(tmpSpacing
                                .getAfterAutospacing());
                    }
                    if (tmpSpacing.getAfterLines() != null) {
                        cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
                    }
                    if (tmpSpacing.getBefore() != null) {
                        cellSpacing.setBefore(tmpSpacing.getBefore());
                    }
                    if (tmpSpacing.getBeforeAutospacing() != null) {
                        cellSpacing.setBeforeAutospacing(tmpSpacing
                                .getBeforeAutospacing());
                    }
                    if (tmpSpacing.getBeforeLines() != null) {
                        cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
                    }
                    if (tmpSpacing.getLine() != null) {
                        cellSpacing.setLine(tmpSpacing.getLine());
                    }
                    if (tmpSpacing.getLineRule() != null) {
                        cellSpacing.setLineRule(tmpSpacing.getLineRule());
                    }
                }
                // 复制段落缩进信息
                CTInd tmpInd = tmpPPr.getInd();
                if (tmpInd != null) {
                    CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd()
                            : cellPPr.addNewInd();
                    if (tmpInd.getFirstLine() != null) {
                        cellInd.setFirstLine(tmpInd.getFirstLine());
                    }
                    if (tmpInd.getFirstLineChars() != null) {
                        cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
                    }
                    if (tmpInd.getHanging() != null) {
                        cellInd.setHanging(tmpInd.getHanging());
                    }
                    if (tmpInd.getHangingChars() != null) {
                        cellInd.setHangingChars(tmpInd.getHangingChars());
                    }
                    if (tmpInd.getLeft() != null) {
                        cellInd.setLeft(tmpInd.getLeft());
                    }
                    if (tmpInd.getLeftChars() != null) {
                        cellInd.setLeftChars(tmpInd.getLeftChars());
                    }
                    if (tmpInd.getRight() != null) {
                        cellInd.setRight(tmpInd.getRight());
                    }
                    if (tmpInd.getRightChars() != null) {
                        cellInd.setRightChars(tmpInd.getRightChars());
                    }
                }
            }
        }
    }



    //添加不带图片的页眉  如果页眉不做图片处理  可以用此方法
    public void createHeader(XWPFDocument doc, String orgFullName) throws Exception {
        /*
         * 对页眉段落作处理
         * */
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc,sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setBorderBottom(Borders.THICK);


        /*
         * 添加字体页眉，公司全称
         * */
        if (StringUtils.isNotEmpty(orgFullName)) {
            XWPFRun run = paragraph.createRun();
            run.setText(orgFullName);
            //setXWPFRunStyle(run,"新宋体",10);
        }
    }


    /**
     * 对页眉处理  页眉包含公司名称   二维码图片
     * @param doc
     * @param orgFullName   公司全称
     * @param logoFilePath   二维码图片的地址
     * @throws Exception
     */
    public void createHeader(XWPFDocument doc, String orgFullName, String logoFilePath) throws Exception {
        /*
         * 对页眉段落作处理，使二维码图片在页眉右边，公司全称在页眉左边
         * */
        CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc,    sectPr);
        XWPFHeader header = headerFooterPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);

        XWPFParagraph paragraph = header.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.BOTH);   // 设置段落左对齐
        paragraph.setBorderBottom(Borders.THICK);    // 设置下划线

        XWPFRun run = paragraph.createRun();
        setXWPFRunStyle(run,"新宋体",10);

        /*
         * 添加字体页眉，公司全称
         * 公司全称在右边
         * */
        if (StringUtils.isNotEmpty(orgFullName)) {
            run = paragraph.createRun();
            run.setText(orgFullName);
            setXWPFRunStyle(run,"新宋体",10);
        }

        //处理两个段落之间的制表符宽度
        run.addTab();

        CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
        tabStop.setVal(STTabJc.CENTER);
        int twipsPerInch =  1450;
        tabStop.setPos(BigInteger.valueOf(6 * twipsPerInch));

        /*
         * 取到二维码的图片的字节流
         * */
        if (StringUtils.isNotEmpty(logoFilePath)) {
            File file=new File(logoFilePath);
            InputStream is = new FileInputStream(file);
            XWPFPicture picture = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, logoFilePath, Units.toEMU(40), Units.toEMU(40));

            String blipID = "";
            for(XWPFPictureData picturedata : header.getAllPackagePictures()) {    //这段必须有，不然打开的logo图片不显示
                blipID = header.getRelationId(picturedata);
            }
            picture.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
            run.addTab();
            is.close();
        }

    }




    /**
     * 设置页脚的字体样式
     *
     * @param r1 段落元素
     */
    private void setXWPFRunStyle(XWPFRun r1,String font,int fontSize) {
        r1.setFontSize(fontSize);
        CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
        //下面代码被龚宇修改过
        CTFonts fonts = rpr.getRFontsArray(0)!=null ? rpr.getRFontsArray(0): rpr.addNewRFonts();
        fonts.setAscii(font);
        fonts.setEastAsia(font);
        fonts.setHAnsi(font);
    }


    /**
     *  收尾方法
     * @param outDocPath
     * @return
     * @throws IOException
     */
    public boolean generate(String outDocPath) throws IOException{
        outputStream = new FileOutputStream(outDocPath);
        xwpfDocument.write(outputStream);
        this.close(outputStream);
        this.close(inputStream);
        return true;
    }

    /**
     *  关闭输入流
     * @param is
     */
    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws Exception {

        // 添加假数据 这里你也可以从数据库里获取数据
        List<Map<String, String>> list = new ArrayList<>();
        for (int i =0;i < 10; i++){
            Map<String,String> map = new HashMap<>();
            map.put("index", "2018"+i);
            map.put("fileName", "我是第一列数据"+i);
            map.put("creationDate", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            map.put("supervisionOpinions", "监理意见"+i);
            map.put("comment", "我是备注"+i);
            list.add(map);
        }
        Map<String,String> textMap=new HashMap<>();
        textMap.put("companyName", "测试替换文本的的公司名");
        textMap.put("folderName", "测试替换文本里的第二个埋点");

        // 模板文件输入输出地址
        String filePath = "E:\\cib\\word\\测试模板.docx";
        String outPath = "E:\\cib\\word\\生成的示例文档0629.docx";

        String orgFullName="测试替换页眉的公司名";
        String imgFile="E:\\cib\\word\\lixinger.mybatis.test.jpg";
        word1 wordReporter = new word1();
        wordReporter.setTempLocalPath(filePath);    //设置模板的路径
        wordReporter.init();            //初始化工具类
        wordReporter.export(list,orgFullName,imgFile,0,textMap);   //写入相关数据
        wordReporter.generate(outPath);   //导出到目标文档

    }



}
