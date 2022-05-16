package work.cib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import cib.excel.ReadXlsxOrXls;
import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;


public class PublicUtil {
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;

    /**
     * 判断字符串是否为数字
     */
    public static boolean isNumericzidai(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 金额千分位分隔符，保留小数点后2位
     */
    public static String separatorToAmount(String amout) {
        BigDecimal bd = new BigDecimal(amout);
        DecimalFormat df = new DecimalFormat(",###,###.00");
        return df.format(bd);
    }

    /**
     * 组装文件绝对地址
     * @param path   文件路径
     * @param uuid   文件名称
     * @param suffix 文件类型
     * @param flag   true:走自定义的文件名称命名规则，false:取UIDUtil.getUid()作为文件名称
     */
    public static String generateUniqueFile(String path, String uuid, boolean flag, String suffix) {
        Date date = new Date();
        SimpleDateFormat sdfdhm = new SimpleDateFormat("ddhhmm");
        String dhmPrefix = sdfdhm.format(date);

        StringBuffer strbuf = null;
        File file;
        do {
            strbuf = new StringBuffer("");
            if (flag) {
                strbuf.append(path).append(dhmPrefix).append("_").append(randomString(4)).append(".").append(suffix);
            } else {
                strbuf.append(path).append(uuid).append(".").append(suffix);
            }
            file = new File(strbuf.toString());
        } while (file.exists());

        return strbuf.toString();
    }

    public static final String randomString(int length) {
        if (length < 1) {
            return null;
        }
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                    .toCharArray();
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    /**
     * 删除文件
     * @param path--文件路径
     * @param prefix--文件名称
     */
    public static void delFiles(String path, String prefix) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                if (tempList[i].getName().indexOf(prefix) == 0) {
                    tempList[i].delete();
                }
            }
        }
    }

    /**
     * Map--根据值排序
     */
    public static Map<String, Object> sortMapByValue(Map<String, Object> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        // 通过ArrayList构造函数把map.entrySet()转换成list
        List<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(oriMap.entrySet());
        // 通过比较器实现比较排序
        Collections.sort(list, new Comparator<Entry<String, Object>>() {
            @Override
            public int compare(Entry<String, Object> mapping1, Entry<String, Object> mapping2) {
                return mapping1.getKey().compareTo(mapping2.getKey());
            }
        });
        Map<String, Object> sortedMap = new LinkedHashMap<String, Object>();
        for (Entry<String, Object> mapping : list) {
            sortedMap.put(mapping.getKey(), mapping.getValue());
        }
        return sortedMap;
    }

    /**
     * 工作日耗时处理方法
     */
    public static String compute(int val, int param) {
        // 输入值
        BigDecimal valBig = new BigDecimal(Double.toString(val));
        // 分钟
        BigDecimal minBig = new BigDecimal(Double.toString(60));
        // 工作日
        BigDecimal workBig = new BigDecimal(Double.toString(6.5));
        // 保留6位小数
        double rslt1 = valBig.divide(minBig, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal rsltBig = new BigDecimal(Double.toString(rslt1));
        // 保留6位小数
        double rslt2 = rsltBig.divide(workBig, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal rslt2Big = new BigDecimal(Double.toString(rslt2));
        if (param != 0) {
            // 除以参数
            BigDecimal paramkBig = new BigDecimal(Double.toString(param));
            double rslt3 = rslt2Big.divide(paramkBig, 6, BigDecimal.ROUND_HALF_UP).doubleValue();
            BigDecimal rslt3Big = new BigDecimal(Double.toString(rslt3));
            // 进位处理（直接加1）
            double d = rslt3Big.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            return String.valueOf(d);
        } else {
            // 进位处理（直接加1）
            double d = rslt2Big.setScale(2, BigDecimal.ROUND_UP).doubleValue();
            return String.valueOf(d);

        }

    }

    /**
     * @param oneDay 一天有效工作的小时数
     * @param val    耗时分钟值
     */
    public static BigDecimal compute(int val, double oneDay) {
        BigDecimal divide = new BigDecimal(String.valueOf(val)).
                divide(new BigDecimal(String.valueOf(60)), 2, BigDecimal.ROUND_HALF_UP).
                divide(new BigDecimal(String.valueOf(oneDay)), 2, BigDecimal.ROUND_HALF_UP);
        return divide;
    }

    /**
     * 根据map->value获取key
     * @param map 数组
     * @param v   value
     */
    @SuppressWarnings("unchecked")
    public static String getMapKey(Map<String, Object> map, String v) {
        Set set = map.entrySet();
        Iterator iterator = set.iterator();
        String key = "";
        while (iterator.hasNext()) {
            Entry<String, Object> enter = (Entry<String, Object>)
                    iterator.next();
            if (enter.getValue().equals(v)) {
                key = enter.getKey();
            }
        }
        return key;
    }

    /**
     * 验证金额
     */
    public static boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        Matcher match = pattern.matcher(str);
        return match.matches();
    }

//    /**
//     * 解析excle数据
//     *
//     * @param request
//     * @param excelParams
//     * @return
//     */
//    public static List<Map<String, String>> getExcelList(MultipartHttpServletRequest request, String[] excelParams) {
//        MultipartFile file = request.getFile("uploadFile");
//        // 文件后缀
//        String stuff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        Workbook wb = null;
//        try {
//            wb = ReadXlsxOrXls.readExcel(file.getInputStream(), stuff);
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        // 获取最大行列数 lineMax columnMax
//        HashMap<String, Integer> excelListDispose = ReadXlsxOrXls.excelListDispose(wb);
//        // 解析xlx，xlsx excelList内部存入Map<String, String>类型数据，
//        return ReadXlsxOrXls.excelList(excelParams, excelListDispose, wb);
//    }
//
//    /**
//     * 解析excle数据-从指定的行  n 开始解析。n为需要转化的第一行数据。不可超过实际长度
//     *
//     * @param request
//     * @param excelParams
//     * @return
//     */
//    public static List<Map<String, String>> getExcelListByNRow(MultipartHttpServletRequest request, String[] excelParams, Integer beginRow) {
//        MultipartFile file = request.getFile("uploadFile");
//        // 文件后缀
//        String stuff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        Workbook wb = null;
//        try {
//            wb = ReadXlsxOrXls.readExcel(file.getInputStream(), stuff);
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        // 获取最大行列数 lineMax columnMax
//        HashMap<String, Integer> excelListDispose = ReadXlsxOrXls.excelListDispose(wb);
//        // 解析xlx，xlsx excelList内部存入Map<String, String>类型数据，
//        return ReadXlsxOrXls.excelListByNRow(excelParams, excelListDispose, wb, beginRow);
//    }

    /**
     * 写入文件数据
     * @param filePath 文件路径
     * @param list     文件数据集合
     */
    public static void writeTxtFile(String filePath, List<String> list) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));
            for (String str : list) {
                bw.write(str + "\r\n");
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * oracle导入txt文件需要的ctl文件
     * @param filePath   txt文件绝对地址
     * @param fileName   表名称
     * @param tableField 表字段
     * @param outPath    输出ctl文件
     */
    public static void writeOracleCtl(String filePath, String fileName, String tableField, String outPath) {
        FileWriter fw = null;
        try {
            // 组装导入ctl文件数据
            String strctl = "OPTIONS (skip=0)" + " LOAD DATA CHARACTERSET AL32UTF8 INFILE '"
                    + filePath + "'"
                    + " APPEND INTO TABLE " + fileName + "" + " FIELDS TERMINATED BY '|^|'"
                    + " OPTIONALLY  ENCLOSED BY \"'\""
                    + " TRAILING NULLCOLS " + tableField + "";
            fw = new FileWriter(outPath);
            // ctl文件写入数据
            fw.write(strctl);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.flush();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 调用cmd命令处理
     * @param cmdPar 命令参数
     */
    public static int useOrderCMD(String cmdPar) {
        InputStream ins = null;
        String[] cmd = new String[]{"cmd.exe", "/C", cmdPar}; // 命令
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            ins = process.getInputStream(); // 获取执行cmd命令后的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = reader.readLine()) != null) {
            }
            int exitValue = process.waitFor();
            process.getOutputStream().close(); // 关闭
            return exitValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static void main(String[] args) {
        String a ="/asdfasdf";
        System.out.println(useOrderCMD(a));
        System.out.println(randomString(5));
    }
}
