package work.cib.word;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WordUtils_3 extends HttpServlet {
    private URL base = this.getClass().getResource("");
    /**
     * demoFile 模板文件
     * newFile 生成文件
     * map 要填充的数据
     */
    public void writeDoc(File demoFile, File newFile, Map<String, String> map) {
        try {
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        try {

//            String fileDir = new File(base.getFile(), "../../../../doc/").getCanonicalPath();
            String fileDir = "E:\\cib\\word\\";
            File demoFile = new File(fileDir + "WordUtils_3.doc");
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);

            //替换读取到的word模板内容的指定字段
            Range range = hdt.getRange();
            Map<String, String> map = new HashMap<String, String>();
            map.put("$QYMC$", "xx数码科技股份有限公司");
            map.put("$QYDZ$", "广东省广州市天河区xx路xx号");
            map.put("$QYFZR$", "张三");
            map.put("$FRDB$", "李四");
            map.put("$CJSJ$", "2000-11-10");
            map.put("$SCPZMSJWT$", "5");
            map.put("$XCJCJBQ$", "6");
            map.put("$JLJJJFF$", "7");
            map.put("$QYFZRQM$", "张三");
            map.put("$CPRWQM$", "赵六");
            map.put("$ZFZH$", "100001");
            map.put("$BZ$", "无");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
            }

            //输出word内容文件流，提供下载
            response.reset();
            response.setContentType("application/x-msdownload");
            response.addHeader("Content-Disposition", "attachment; filename=\"1.doc\"");
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            ServletOutputStream servletOS = response.getOutputStream();
            hdt.write(ostream);
            servletOS.write(ostream.toByteArray());
            servletOS.flush();
            servletOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String[] args) {
        WordUtils_3 rawDoc = new WordUtils_3();
        System.out.println(rawDoc.base.getFile());
//            String fileDir = new File(rawDoc.base.getFile(), "./resources/").getCanonicalPath();
        String fileDir = "F:\\leecode\\src\\main\\java\\cib\\word\\resources\\";
        // 获取模板文件
        File demoFile = new File(fileDir + "WordUtils_3.doc");
        // 创建生成的文件
        File newFile = new File(fileDir + "WordUtils_3_result.doc");
        Map<String, String> map = new HashMap<>();
        map.put("$QYMC$", "兴业银行重庆分行");
        map.put("$QYDZ$", "重庆市红旗河沟红黄路1号");
        map.put("$QYFZR$", "龚宇");
        map.put("$FRDB$", "龚宇");
        map.put("$CJSJ$", "2000-11-10");
        map.put("$SCPZMSJWT$", "5");
        map.put("$XCJCJBQ$", "6");
        map.put("$JLJJJFF$", "7");
        map.put("$QYFZRQM$", "张三");
        map.put("$CPRWQM$", "赵六");
        map.put("$ZFZH$", "100001");
        map.put("$BZ$", "无");
        rawDoc.writeDoc(demoFile, newFile, map);
        System.out.println("finish....");
    }
}
