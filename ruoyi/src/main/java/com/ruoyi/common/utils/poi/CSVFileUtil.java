package com.ruoyi.common.utils.poi;

import com.csvreader.CsvReader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CSVFileUtil {

    public static final String ENCODE = "UTF-8";

    /**
     * readCsv:根据路径读取CSV文件.<br/>
     *
     * @param csvFilePath
     * @return
     */
    public static List<String[]> readCsv(String csvFilePath) {
        List<String[]> csvList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName(ENCODE));
            /** 跳过表头 如果需要表头的话，不要写这句 */
            reader.readHeaders();
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }

    /**
     * readCsv:根据数据流读取CSV文件.<br/>
     *
     * @param csvIs
     * @return
     */
    public static List<String[]> readCsv(InputStream csvIs) {

        List<String[]> csvList = new ArrayList<String[]>();
        try {
            CsvReader reader = new CsvReader(csvIs, Charset.forName(ENCODE));
            /** 跳过表头 如果需要表头的话，不需要写这句 */
            reader.readHeaders();
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvList;
    }



}