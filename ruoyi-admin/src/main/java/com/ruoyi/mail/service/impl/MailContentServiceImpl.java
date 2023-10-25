package com.ruoyi.mail.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mail.mapper.MailContentMapper;
import com.ruoyi.mail.domain.MailContent;
import com.ruoyi.mail.service.IMailContentService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 晚安语列表Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-10-21
 */
@Slf4j
@Service
public class MailContentServiceImpl implements IMailContentService 
{

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    @Autowired
    private MailContentMapper mailContentMapper;

    /**
     * 查询晚安语列表
     * 
     * @param id 晚安语列表主键
     * @return 晚安语列表
     */
    @Override
    public MailContent selectMailContentById(Long id)
    {
        return mailContentMapper.selectMailContentById(id);
    }

    /**
     * 查询晚安语列表列表
     * 
     * @param mailContent 晚安语列表
     * @return 晚安语列表
     */
    @Override
    public List<MailContent> selectMailContentList(MailContent mailContent)
    {
        return mailContentMapper.selectMailContentList(mailContent);
    }

    /**
     * 新增晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    @Override
    public int insertMailContent(MailContent mailContent)
    {
        mailContent.setCreateTime(DateUtils.getNowDate());
        return mailContentMapper.insertMailContent(mailContent);
    }

    /**
     * 修改晚安语列表
     * 
     * @param mailContent 晚安语列表
     * @return 结果
     */
    @Override
    public int updateMailContent(MailContent mailContent)
    {
        mailContent.setUpdateTime(DateUtils.getNowDate());
        return mailContentMapper.updateMailContent(mailContent);
    }

    /**
     * 批量删除晚安语列表
     * 
     * @param ids 需要删除的晚安语列表主键
     * @return 结果
     */
    @Override
    public int deleteMailContentByIds(Long[] ids)
    {
        return mailContentMapper.deleteMailContentByIds(ids);
    }

    /**
     * 删除晚安语列表信息
     * 
     * @param id 晚安语列表主键
     * @return 结果
     */
    @Override
    public int deleteMailContentById(Long id)
    {
        return mailContentMapper.deleteMailContentById(id);
    }

    @Override
    public void importContentByExcel(MultipartFile file) {
        List<MailContent> contentList = new ArrayList<>();
        try {
            checkFile(file);

            Workbook workbook = getWorkBook(file);

            if(workbook != null) {
                for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {

                    //获得当前sheet工作表
                    Sheet sheet = workbook.getSheetAt(sheetNum);
                    if (sheet == null) {
                        continue;
                    }

                    //获得当前sheet的开始行
                    int firstRowNum = sheet.getFirstRowNum();
                    //获得当前sheet的结束行
                    int lastRowNum = sheet.getLastRowNum();
                    //循环除了第一行的所有行
                    for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {

                        MailContent content = new MailContent();
                        //获得当前行
                        Row row = sheet.getRow(rowNum);
                        if (row == null) {
                            continue;
                        }
                        //获得当前行的开始列
                        int firstCellNum = row.getFirstCellNum();
                        //获得当前行的列数
                        int lastCellNum = row.getPhysicalNumberOfCells();

                        for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                            Cell cell = row.getCell(cellNum);
                            String stringCellValue = cell.getStringCellValue();
                            if (StringUtils.isEmpty(stringCellValue)) {
                                return;
                            }
                            content.setContent(stringCellValue);
                            content.setCreateTime(new Date());
                            content.setIsDelete(0L);
                            content.setType(1L);
                        }
                        contentList.add(content);
                    }
                }
            }


            workbook.close();
        }catch (IOException e) {
            log.info("excel文件读取失败");
        }

        for (MailContent content : contentList) {
            mailContentMapper.insertMailContent(content);
        }

    }

    public  void checkFile(MultipartFile file) throws IOException{
        //判断文件是否存在
        if(null == file){
            log.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            log.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return workbook;
    }
}
