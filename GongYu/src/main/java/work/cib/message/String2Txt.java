package work.cib.message;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class String2Txt {


    private static void saveAsFileWriter(String content) {
        FileWriter fwriter = null;
        OutputStreamWriter fwriter2=null;
        String authorname = "GongYu";
        String rootPath = "E:\\cib\\";
        File dir = new File(rootPath);
        String2Txt.judeDirExists(dir);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Date date1 = new Date();
        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String filePath = rootPath + authorname + "_" + formatter1.format(date1) + "_" + uuid + ".txt";
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
//            fwriter = new FileWriter(filePath, false);
            fwriter2=new OutputStreamWriter(new FileOutputStream(filePath),"GBK");
            System.out.println(fwriter2.getEncoding());
            fwriter2.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter2.flush();
                fwriter2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdir();
        }
    }

    public static void main(String[] args) {
        String test = "asdfasdfasdf";
        String2Txt string2Txt = new String2Txt();
        string2Txt.saveAsFileWriter(test);

    }
}
