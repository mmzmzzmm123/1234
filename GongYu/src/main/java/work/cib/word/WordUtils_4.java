package work.cib.word;

import work.cib.util.StringUtils;
import org.apache.poi.ooxml.POIXMLTypeLoader;
import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// https://blog.csdn.net/weixin_43871371/article/details/109791282?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522164627851416780274152024%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=164627851416780274152024&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~hot_rank-2-109791282.nonecase&utm_term=poi+word%E5%B7%A5%E5%85%B7%E5%8C%85&spm=1018.2226.3001.4450
// https://www.oschina.net/question/852678_2263709
// https://www.it610.com/article/2256822.htm      单位换算链接
// mm=cm*10
// dxa=pt*20 *********************
// pt=inch*72
// emu=dxa*635
// dxa=inch*1440
// emu=inch*914400
public class WordUtils_4 {
    private static XWPFDocument document;

    public WordUtils_4(InputStream inputStream) throws IOException {
        document = new XWPFDocument(inputStream);
    }

    public static void main(String[] args) throws IOException {
        File file = new File("F:\\leecode\\src\\main\\java\\cib\\word\\resources\\WordUtils_2.docx");
        // 读取word模板
        FileInputStream fileInputStream = new FileInputStream(file);
        WordUtils_4 template = new WordUtils_4(fileInputStream);

        System.out.println(WordUtils_4.getHeaderHeight(document));
    }

    /**
     * 列宽自动分割 输入单位 磅
     *
     * @param tableWidth 表格宽度 磅
     */
    public static void setTableWidth_pt(XWPFTable table, int tableWidth) {
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblPr = ctTbl.getTblPr();
        if (tblPr == null) {
            tblPr = ctTbl.addNewTblPr();
        }
        CTTblWidth comTableWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        comTableWidth.setType(STTblWidth.DXA);
        comTableWidth.setW(BigInteger.valueOf(tableWidth * 20));
        List<XWPFTableRow> rows2 = table.getRows();
        for (XWPFTableRow xwpfTableRow : rows2) {
            xwpfTableRow.setCantSplitRow(true);
        }
    }

    /**
     * 设置图片页眉，给页眉添加图片，作为整个word 的边框 输入单位 磅
     *
     * @param w         h      图片宽高单位 磅
     * @param positionH 图片位置 左右方向 正文左边界处为0 ，单位 磅
     * @param positionV 图片位置 上下方向 正文上边界处为0， 单位 磅
     */
    public static void createHeader_useImage(XWPFDocument document, String imgPath, int w, int h, int positionH, int positionV) throws Exception {
        XWPFHeaderFooterPolicy headerFooterPolicy = getHeaderFooterPolicy(document);
        XWPFHeader header = headerFooterPolicy.createHeader(STHdrFtr.DEFAULT);
        XWPFParagraph paragraph = header.createParagraph();
        setParagraphSpacing(paragraph, 0, 0, 1);
        XWPFRun run = paragraph.createRun();
        setStyle(run, "", 5, false, "", null);         //页眉设置为0，还有一个文段的距离，这是设置为最小字体
        FileInputStream fileInputStream = new FileInputStream(new File(imgPath));
        String color = null;  //龚宇自己添加
        addPicture_Anchor(run, fileInputStream, XWPFDocument.PICTURE_TYPE_PNG, "",
                w, h, positionH, positionV, true, color);
        fileInputStream.close();
    }

    /**
     * 设置页脚文段
     */
    public static void createFooter_simpleNumber(XWPFDocument document, String version, String footTitle) {
        XWPFHeaderFooterPolicy headerFooterPolicy = getHeaderFooterPolicy(document);
        XWPFFooter footer = headerFooterPolicy.createFooter(STHdrFtr.DEFAULT);
        XWPFParagraph paragraph = footer.createParagraph();

        //段落左侧文本设置
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);

        XWPFRun run = paragraph.createRun();
        WordUtils_4.setStyle(run, "微软雅黑", 6, false, footTitle, "000000");

        //生成日期和版本号
        if (!StringUtils.isEmpty(version)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String format = sdf.format(new Date());
            XWPFRun versionRun = paragraph.createRun();
            WordUtils_4.setStyle(versionRun, "微软雅黑", 6, false,
                    "生成日期：    " + format + "   版本：   " + version, "000000");
            versionRun.addTab();    //也可以使用制表符控制格式，这里用tab定死格式
            versionRun.addTab();
            versionRun.addTab();
            versionRun.setText("      ");
        }

        //页码，最右边
        WordUtils_4.setStyle(paragraph.createRun(), "微软雅黑", 8, false, "第", "000000");

        CTFldChar fldChar = paragraph.createRun().getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        XWPFRun numberRun = paragraph.createRun();
        CTText ctText = numberRun.getCTR().addNewInstrText();
        ctText.setStringValue("PAGE  \\* MERGEFORMAT");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        WordUtils_4.setStyle(numberRun, "微软雅黑", 8, false, null, "000000");

        fldChar = paragraph.createRun().getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        WordUtils_4.setStyle(paragraph.createRun(), "微软雅黑", 8, false, "页", "000000");
    }

    /**
     * 获取页眉页脚策略对象（创建页眉页脚的对象）
     */
    public static XWPFHeaderFooterPolicy getHeaderFooterPolicy(XWPFDocument document) {
        CTBody body = document.getDocument().getBody();
        CTSectPr ctSectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        return new XWPFHeaderFooterPolicy(document, ctSectPr);
    }

    /**
     * 添加图片[环绕模式：文字上方/下方] 输入单位 磅
     *
     * @param pictureData 图片数据
     * @param pictureType 图片类型，参考  ({@link XWPFDocument#PICTURE_TYPE_PNG})
     * @param filename    文件名
     * @param width       图片宽  单位 磅
     * @param height      图片高  单位 磅
     * @param behind      环绕模式：true 在文字下方 ， false 在文字上方
     * @param positionH   图片位置 左右方向 正文左边界处为0/段落开始位置为0 ，单位 磅
     * @param positionV   图片位置 上下方向 正文上边界处为0/段落开始位置为0 ， 单位 磅
     * @param backGColor  图片染色
     * @Auther lu zhifeng
     * @Date 2020/11/12 10:37 下午
     */
    public static void addPicture_Anchor(XWPFRun run, InputStream pictureData, int pictureType, String filename,
                                         int width, int height, int positionH, int positionV, boolean behind, String backGColor) throws IOException {
        try {
            //poi提供的添加图片的方法，环绕模式是嵌入式(行内模式)（猜测是两部分，一部分是数据存放，一部分是模式设置）,这里先添加图片，以便下面数据获取
            run.addPicture(pictureData, pictureType, filename, Units.toEMU(width), Units.toEMU(height));
            //获取刚才添加的图片数据
            CTDrawing drawing = run.getCTR().getDrawingArray(0);
            CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();

            long id = drawing.getInlineArray(0).getDocPr().getId();     //由于计算id的方法是私有的，这里调用不了，这里使用原来的id

            //设置一个浮动模式，替换掉默认的行内模式
            CTAnchor ctAnchor = drawing.addNewAnchor();
            String xml = "<a:graphic xmlns:a=\"" + CTGraphicalObject.type.getName().getNamespaceURI() + "\">" + "<a:graphicData uri=\"" + org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture.type.getName().getNamespaceURI() + "\">" + "<pic:pic xmlns:pic=\"" + org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture.type.getName().getNamespaceURI() + "\" />" + "</a:graphicData>" + "</a:graphic>";
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = DocumentHelper.readDocument(is);
            ctAnchor.set(org.apache.xmlbeans.XmlToken.Factory.parse(doc.getDocumentElement(), POIXMLTypeLoader.DEFAULT_XML_OPTIONS));
            ctAnchor.setDistT(0L);
            ctAnchor.setDistR(0L);
            ctAnchor.setDistB(0L);
            ctAnchor.setDistL(0L);
            ctAnchor.setBehindDoc(behind);
            ctAnchor.addNewPositionH().setPosOffset(Units.toEMU(positionH));
            ctAnchor.addNewPositionV().setPosOffset(Units.toEMU(positionV));
            CTNonVisualDrawingProps docPr = ctAnchor.addNewDocPr();
            docPr.setId(id);
            docPr.setName("Drawing " + id);
            docPr.setDescr(filename);

            CTPositiveSize2D extent = ctAnchor.addNewExtent();
            extent.setCx(Units.toEMU(width));
            extent.setCy(Units.toEMU(height));

            ctAnchor.setGraphic(graphicalobject);

            drawing.setAnchorArray(new CTAnchor[]{ctAnchor});//添加浮动属性
            drawing.removeInline(0);//删除行内属性

            //给图片添加颜色
            if (!StringUtils.isEmpty(backGColor)) {
                String xmlColor = "<a:duotone>\n" +
                        "<a:prstClr val=\"black\"/>\n" +
                        "<a:srgbClr val=\"" + backGColor + "\">\n" +
                        "</a:srgbClr>\n" +
                        "</a:duotone>\n" +
                        "</a:blip>";


                String string = ctAnchor.getGraphic().getGraphicData().xmlText();

                String toReplaceStr = string.replaceAll(".*(<a:blip.*?/>).*", "$1");
                String targetReplace = toReplaceStr.replaceAll("/>", ">") + xmlColor;
                String result = string.replaceAll(toReplaceStr, targetReplace);

                XmlObject parse = XmlObject.Factory.parse(result);
                ctAnchor.getGraphic().getGraphicData().set(parse);
            }

        } catch (XmlException | SAXException | InvalidFormatException var31) {
            throw new IllegalStateException(var31);
        }
    }

    /**
     * 设置页眉页脚缩进，设置页眉页脚距离顶端/底端距离 输入单位 磅
     *
     * @param headerMar 页眉距离顶端距离  单位 磅
     * @param footerMar 页脚距离底部距离  单位 磅
     */
    public static void setHeaderFooterMar(XWPFDocument document, int headerMar, int footerMar) {
        //一磅 = 20 缇
        CTBody body = document.getDocument().getBody();
        CTSectPr sectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        CTPageMar pgMar = sectPr.isSetPgMar() ? sectPr.getPgMar() : sectPr.addNewPgMar();
        pgMar.setHeader(BigInteger.valueOf(headerMar * 20));
        pgMar.setFooter(BigInteger.valueOf(footerMar * 20));
    }

    /**
     * 获取页眉高度  返回单位 dxa
     */
    public static Object getHeaderHeight(XWPFDocument document) {
        try {
            return document.getDocument().getBody().getSectPr().getPgMar().getHeader();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取页脚高度  返回单位 dxa
     */
    public static Object getFooterHeight(XWPFDocument document) {
        try {
            return document.getDocument().getBody().getSectPr().getPgMar().getFooter() ;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 设置样式
     */
    public static void setStyle(XWPFRun run, String fontFamily, int fontSize, boolean bold, String text, String color) {
        run.setBold(bold);
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        if (!StringUtils.isEmpty(text)) {
            run.setText(text);
        }
        run.setColor(StringUtils.isEmpty(color) ? "000000" : color);             //颜色默认黑色
    }

    /**
     * 设置样式，设置样式【下划线】 输入单位 磅
     *
     * @param underType 下划线类型 参考：({@link UnderlinePatterns#SINGLE})
     * @Return
     */
    public static void setStyle(XWPFRun run, String fontFamily, int fontSize, boolean bold, String text, String color, UnderlinePatterns underType) {
        setStyle(run, fontFamily, fontSize, bold, text, color);
        run.setUnderline(underType);
    }

    /**
     * 设置文段内文本的位置和字符间距 输入单位 磅
     * <p>
     * 设置文段Run位置【对应word字体 - 高级 - 字符间距】
     * 注意：设置run的高低都会是段落变高，即位置变化皆在段落内
     *
     * @param positionValue 【上下位置调整正负】 单位 磅
     * @param spacingValue  【字符间距调整 宽窄-正负】单位 磅
     */
    public static void setPosition_run(XWPFRun run, int positionValue, int spacingValue) {
        CTRPr rPr = run.getCTR().getRPr();
        if (rPr == null) {
            rPr = run.getCTR().addNewRPr();
        }
        List<CTSignedHpsMeasure> position = rPr.getPositionList();
        if (position.size() == 0) {
            rPr.addNewPosition().setVal(BigInteger.valueOf(positionValue * 2));
        } else {
            position.get(0).setVal(BigInteger.valueOf(positionValue * 2));
        }
        List<CTSignedTwipsMeasure> spacing = rPr.getSpacingList();
        if (spacing.size() == 0) {
            rPr.addNewSpacing().setVal(BigInteger.valueOf(spacingValue * 20));
        } else {
            spacing.get(0).setVal(BigInteger.valueOf(spacingValue * 20));
        }
    }

    /**
     * 调整段落间距【对应word段落设置-间距设置】 输入单位 磅
     *
     * @param before   段前  磅
     * @param after    段后  磅
     * @param multiple 几倍行距
     */
    public static void setParagraphSpacing(XWPFParagraph paragraph, int before, int after, float multiple) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) {
            ppr = paragraph.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setBefore(BigInteger.valueOf(before * 20));
        spacing.setAfter(BigInteger.valueOf(after * 20));
        spacing.setLine(BigInteger.valueOf((int) (240 * multiple)));
        spacing.setLineRule(STLineSpacingRule.AUTO);
    }

    /**
     * 设置段落缩进 输入单位 磅
     *
     * @param left  左缩进值 磅
     * @param right 右缩进值 磅
     */
    public static void setParagraphInd(XWPFParagraph paragraph, int left, int right) {
        CTPPr ppr = paragraph.getCTP().getPPr();
        if (ppr == null) {
            ppr = paragraph.getCTP().addNewPPr();
        }
        CTInd ctInd = ppr.isSetInd() ? ppr.getInd() : ppr.addNewInd();
        ctInd.setLeft(BigInteger.valueOf(left * 20));
        ctInd.setRight(BigInteger.valueOf(right * 20));
    }

    /**
     * 设置页边距 输入单位 磅
     */
    public static void setPageMargin(XWPFDocument document, int left, int top, int right, int bottom) {
        //一磅 = 20缇
        CTBody body = document.getDocument().getBody();
        CTSectPr sectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        CTPageMar ctpagemar = sectPr.isSetPgMar() ? sectPr.getPgMar() : sectPr.addNewPgMar();
        ctpagemar.setLeft(new BigInteger((left * 20) + ""));
        ctpagemar.setTop(new BigInteger((top * 20) + ""));
        ctpagemar.setRight(new BigInteger((right * 20) + ""));
        ctpagemar.setBottom(new BigInteger((bottom * 20) + ""));
    }

    /**
     * 设置纸张大小 输入单位 磅
     */
    public static void setPaperSize(XWPFDocument document, String wordConstants_paperSize) {
        String[] split = wordConstants_paperSize.split("-");
        CTBody body = document.getDocument().getBody();
        CTSectPr sectPr = body.isSetSectPr() ? body.getSectPr() : body.addNewSectPr();
        CTPageSz pageSize = sectPr.addNewPgSz();
        pageSize.setW(BigInteger.valueOf(Integer.parseInt(split[0]) * 20));
        pageSize.setH(BigInteger.valueOf(Integer.parseInt(split[1]) * 20));
        pageSize.setOrient(STPageOrientation.PORTRAIT);
    }

    /**
     * 设置表格边框 上边框
     *
     * @param type  边框线类型({@link STBorder#SINGLE})
     * @param size  边框线大小
     * @param space 内边距
     * @param color 颜色
     */
    public static void setTopBorder(XWPFTable table, STBorder.Enum type, int size, int space, String color) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();
        CTBorder top = borders.isSetTop() ? borders.getTop() : borders.addNewTop();
        top.setVal(type);
        top.setSz(BigInteger.valueOf(size));
        top.setSpace(BigInteger.valueOf(space));
        top.setColor(color);
    }

    /**
     * 设置表格边框 左边框
     *
     * @param type  边框线类型({@link STBorder#SINGLE})
     * @param size  边框线大小
     * @param space 内边距
     * @param color 颜色
     */
    public static void setLeftBorder(XWPFTable table, STBorder.Enum type, int size, int space, String color) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();
        CTBorder left = borders.isSetLeft() ? borders.getLeft() : borders.addNewLeft();
        left.setVal(type);
        left.setSz(BigInteger.valueOf(size));
        left.setSpace(BigInteger.valueOf(space));
        left.setColor(color);
    }

    /**
     * 设置表格边框 右边框
     *
     * @param type  边框线类型({@link STBorder#SINGLE})
     * @param size  边框线大小
     * @param space 内边距
     * @param color 颜色
     */
    public static void setRightBorder(XWPFTable table, STBorder.Enum type, int size, int space, String color) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();
        CTBorder right = borders.isSetRight() ? borders.getRight() : borders.addNewRight();
        right.setVal(type);
        right.setSz(BigInteger.valueOf(size));
        right.setSpace(BigInteger.valueOf(space));
        right.setColor(color);
    }

    /**
     * 设置表格边框 下边框
     *
     * @param type  边框线类型({@link STBorder#SINGLE})
     * @param size  边框线大小
     * @param space 内边距
     * @param color 颜色
     */
    public static void setBottomBorder(XWPFTable table, STBorder.Enum type, int size, int space, String color) {
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        if (tblPr == null) {
            tblPr = table.getCTTbl().addNewTblPr();
        }
        CTTblBorders borders = tblPr.isSetTblBorders() ? tblPr.getTblBorders() : tblPr.addNewTblBorders();
        CTBorder bottom = borders.isSetBottom() ? borders.getBottom() : borders.addNewBottom();
        bottom.setVal(type);
        bottom.setSz(BigInteger.valueOf(size));
        bottom.setSpace(BigInteger.valueOf(space));
        bottom.setColor(color);
    }

    /**
     * 去除table所有边框，
     */
    public static void unsetTableBorder(XWPFTable table) {
        WordUtils_4.setLeftBorder(table, STBorder.NONE, 0, 0, "");
        WordUtils_4.setTopBorder(table, STBorder.NONE, 0, 0, "");
        WordUtils_4.setRightBorder(table, STBorder.NONE, 0, 0, "");
        WordUtils_4.setBottomBorder(table, STBorder.NONE, 0, 0, "");
        table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 0, 0, "");            //单元格底边框
        table.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");                     //单元格右边框
    }

    /**
     * 设置表格位置
     *
     * @param tblpX X方向右移的距离  磅
     * @param tblpY Y方向下移的距离  磅
     *              # 用于单元格内部表格位置调整，正文表格未测
     */
    public static void setTableSite(XWPFTable table, int tblpX, int tblpY) {
        CTTbl ctTbl = table.getCTTbl();
        CTTblPr tblPr = ctTbl.getTblPr();
        if (tblPr == null) {
            tblPr = ctTbl.addNewTblPr();
        }
        CTTblPPr ctTblPPr = tblPr.isSetTblpPr() ? tblPr.getTblpPr() : tblPr.addNewTblpPr();
        ctTblPPr.setTblpX(BigInteger.valueOf(tblpX * 20));
        ctTblPPr.setTblpY(BigInteger.valueOf(tblpY * 20));
    }

    /**
     * 设置单元格背景色。
     */
    public static void setCellBackGround(XWPFTableCell cell, String color) {
        CTTc ctTc = cell.getCTTc();
        CTTcPr tcPr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTShd ctShd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
        ctShd.setVal(STShd.CLEAR);
        ctShd.setFill(color);
    }

    /**
     * 设置单元格宽度   单位 磅
     */
    public static void setCellW(XWPFTableCell cell, int w) {
        CTTc ctTc = cell.getCTTc();
        CTTcPr tcPr = ctTc.isSetTcPr() ? ctTc.getTcPr() : ctTc.addNewTcPr();
        CTTblWidth tcW = tcPr.isSetTcW() ? tcPr.getTcW() : tcPr.addNewTcW();
        tcW.setType(STTblWidth.DXA);
        tcW.setW(BigInteger.valueOf(w * 20));
    }

    /**
     * 设置表格行 的 高度  单位 磅 。
     */
    public static void setRowH(XWPFTableRow row, int h) {
        row.setHeight(h * 20);
    }

    /**
     * 获取word纸张宽度 单位 dxa 【获取不到返回0】
     */
    public static Object getPaperSizeW(XWPFDocument docxDocument) {
        try {
            return docxDocument.getDocument().getBody().getSectPr().getPgSz().getW();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取word纸张高度 单位 磅 【获取不到返回0】
     */
    public static Object getPaperSizeH(XWPFDocument docxDocument) {
        try {
            return docxDocument.getDocument().getBody().getSectPr().getPgSz().getH();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 写入response
     *
     * @param fileName 文件名
     */
    public static void writeResponse(HttpServletResponse response, String fileName, XWPFDocument document) throws Exception {
        if (response != null) {
            response.setContentType("application/binary;charset=UTF-8");

//            response.setCharacterEncoding("UTF-8");
            ServletOutputStream out = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8") + ".docx");

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                document.write(os);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = os.toByteArray();
            // 返回头增加文件大小
            response.addHeader("length", content.length + "");

            final String savePath = "F:\\leecode\\src\\main\\java\\cib\\word\\resources\\barchart.docx";
            File file = new File(savePath);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(savePath);
            document.write(fos);

//            InputStream is = new ByteArrayInputStream(content);
//            Document doc = new Document(is);
//
//            doc.save(out, SaveFormat.DOCX);

            os.flush();
            os.close();
            out.flush();
            out.close();
        }
        document.close();
    }

    /**
     * 下载失败
     */
    public static void downloadFail(HttpServletResponse response) throws Exception {
        response.setContentType("application/binary;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = "下载失败！";
        out.write(str.getBytes());
        out.flush();
        out.close();
    }


}
