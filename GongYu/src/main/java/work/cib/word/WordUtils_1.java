package work.cib.word;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class WordUtils_1 {

    public static void main(String[] args) throws Exception {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = new FileOutputStream("F:\\leecode\\src\\main\\java\\cib\\word\\resources\\WordUtils_1.docx");
        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        // 设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("这是一个标题");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);

        // 段落
        XWPFParagraph firstParagraph = document.createParagraph();
        XWPFRun run = firstParagraph.createRun();
        run.setText("Java POI 生成word阿的说法所多发所多发所多发所多发所多发所多发所多发所多发所多发所多发所多发所所所所所所所所所所所所所所所沙发上的发送到发送到发送到发文件。");
        run.setColor("696969");
        run.setFontSize(16);
        run.setText("\n");

        // 设置段落背景颜色
        CTShd cTShd = run.getCTR().addNewRPr().addNewShd();
        cTShd.setVal(STShd.PCT_5);
        cTShd.setFill("97FFFF");

        // 换段落
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFRun paragraphRun1 = paragraph1.createRun();
        paragraphRun1.setText("的看法和拉屎的复活了卡加斯的回复立卡户了客家话啦是记得发货拉丝机逗号立方卡死了东风科技哈里斯就发啦加快递费来看");
        // 回车键
        paragraphRun1.setText("\r");

        // 基本信息表格
        XWPFTable infoTable = document.createTable();
        // 去表格边框
        infoTable.getCTTbl().getTblPr().unsetTblBorders();
        // 列宽自动
        CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        // 表格第一行
        // 表格创建后自动就会有一行一列，不用infoTable.createRow()和infoTableRowOne.addNewTableCell()
        // 对于同一个表格，前面创建了一列，后面就不用重复创建了
        XWPFTableRow infoTableRowOne = infoTable.getRow(0);
        infoTableRowOne.getCell(0).setText("职位：");
        infoTableRowOne.addNewTableCell().setText("学生");

        // 表格第二行
        XWPFTableRow infoTableRowTwo = infoTable.createRow();
        infoTableRowTwo.getCell(0).setText("姓名：");
        infoTableRowTwo.getCell(1).setText("龚宇");

        // 表格完成后换行
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setText("\n");

        // 新建表格
        XWPFTable ComTable = document.createTable();
        // 表格整体居中
        ComTable.setTableAlignment(TableRowAlign.CENTER);

        // 表格第一行
        XWPFTableRow comTableRowOne = ComTable.getRow(0);
        comTableRowOne.getCell(0).setText("开始时间");
        comTableRowOne.addNewTableCell().setText("结束时间");
        comTableRowOne.addNewTableCell().setText("公司名称");
        comTableRowOne.addNewTableCell().setText("title");

        // 表格第二行
        XWPFTableRow comTableRowTwo = ComTable.createRow();
        comTableRowTwo.getCell(0).setText("2016-09-06");
        comTableRowTwo.getCell(1).setText("至今");
        comTableRowTwo.getCell(2).setText("seawater");
        comTableRowTwo.getCell(3).setText("Java开发工程师");

        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy policy = new XWPFHeaderFooterPolicy(document, sectPr);

        //表格3--------------------------------------
        XWPFParagraph paragraph2 = document.createParagraph();
        List<String[]> tableList = new ArrayList<>();
        tableList.add(new String[]{"11", "12", "13"});
        tableList.add(new String[]{"21", "22", "23"});
        tableList.add(new String[]{"31", "32", "33"});
        tableList.add(new String[]{"41", "42"});
        XWPFTable ComTable1 = document.createTable();
        int maxCellLen = 1;
        for (int i = 0; i < tableList.size()-1; i++) {
            ComTable1.createRow();
        }
        for (int i = 0; i < tableList.size(); i++) {
            for (int j = 0; j < tableList.get(i).length; j++) {
                ComTable1.getRow(i).addNewTableCell();
            }
        }


        //遍历表格插入数据
        List<XWPFTableRow> rows = ComTable1.getRows();
        for (int i = 0; i < rows.size(); i++) {
            List<XWPFTableCell> cells = rows.get(i).getTableCells();
            for (int j = 0; j < cells.size() - 1; j++) {
                XWPFTableCell cell = cells.get(j);
                // 设置水平居中,需要ooxml-schemas包支持
                CTTc cttc = cell.getCTTc();
                CTTcPr ctPr = cttc.addNewTcPr();
                ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
                //将下面代码注释则无法居中
                cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);

                cell.setText(tableList.get(i)[j]);
            }
        }

        // 添加页眉
        CTP ctpHeader = CTP.Factory.newInstance();
        CTR ctrHeader = ctpHeader.addNewR();
        CTText ctHeader = ctrHeader.addNewT();
        ctHeader.setStringValue("这是一个页眉");
        XWPFParagraph headerParagraph = new XWPFParagraph(ctpHeader, document);
        // 设置为右对齐
        headerParagraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFParagraph[] parsHeader = new XWPFParagraph[1];
        parsHeader[0] = headerParagraph;
        policy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

        //添加页脚
        CTP ctpFooter = CTP.Factory.newInstance();
        CTR ctrFooter = ctpFooter.addNewR();
        CTText ctFooter = ctrFooter.addNewT();
        ctFooter.setStringValue("这是一个页脚");
        XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, document);
        headerParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFParagraph[] parsFooter = new XWPFParagraph[1];
        parsFooter[0] = footerParagraph;
        policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);
        document.write(out);
        out.close();
        System.out.println("create_table document written success.");
    }


}
