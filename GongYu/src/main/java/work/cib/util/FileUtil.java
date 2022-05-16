package work.cib.util;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 验证目录并创建
     */
    public static void checkExistDir(String path) {
        File file = new File(path);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 写申报数据文件（txt）
     */
    public static String writeTxt(byte[] data, String fileDir, String fileName) {
        checkExistDir(fileDir);
        String filePath = fileDir + fileName + ".txt";
        logger.info("开始写入数据[申报数据文件]到：" + filePath);

        try {
            FileUtils.writeByteArrayToFile(new File(filePath), data);
        } catch (IOException e) {
            logger.error("写文件异常：" + e.toString());
        }

        return filePath;
    }

    /**
     * 写订单信息回馈文件（txt）
     */
    public static String writeTxt(String is, String fileDir, String fileName) {
        checkExistDir(fileDir);
        String filePath = fileDir + fileName + ".txt";
        logger.info("开始写入数据[订单信息回馈文件]到：" + filePath);

        try {
            FileUtils.writeStringToFile(new File(filePath), is);
        } catch (IOException e) {
            logger.error("写文件异常：" + e.toString());
            throw new RuntimeException("写文件异常：" + e.toString());
        }

        return filePath;
    }


    /**
     * 文件重命名
     */
    public static String renameFile(String file, String toFile) {

        File toBeRenamed = new File(file);
        //检查要重命名的文件是否存在，是否是文件  
        if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {
            logger.error("文件：" + file + "修改文件名为：" + toFile + "，文件不存在：" + file);
            return "";
        }

        File newFile = new File(toFile);

        //修改文件名  
        if (toBeRenamed.renameTo(newFile)) {
            logger.error("文件：" + file + "修改文件名为：" + toFile + "：修改成功！");
            return toFile;
        } else {
            logger.error("文件：" + file + "修改文件名为：" + toFile + "：修改失败！");
            return file;
        }

    }


    /**
     * 文件下载
     */
    public static void download(HttpServletRequest req, HttpServletResponse resp, FileInputStream fileStream, String name)
            throws IOException {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident"))
            name = java.net.URLEncoder.encode(name, "UTF-8");
        else
            name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
        resp.reset();
        resp.setContentType("application/vnd.ms-excel;charset=utf-8");
        resp.setHeader("Content-Disposition", "attachment;filename=" + name);
        BufferedInputStream bis = new BufferedInputStream(fileStream);
        ServletOutputStream out = resp.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] bytz = new byte[2048];
        int tmp = 0;
        try {
            while ((tmp = bis.read(bytz, 0, bytz.length)) != -1) {
                bos.write(bytz, 0, tmp);
            }
            bos.flush();
        } finally {
            try {
                fileStream.close();
                out.close();
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读txt文件转换为String
     */
    public static String readTxt(String dirPath, String fileName) throws IOException, Exception {
        File dir = new File(dirPath);
        File file = new File(dirPath + "" + fileName);
        if (!dir.isDirectory()) throw new Exception("文件目录不存在:[" + dirPath + "]");
        if (!file.exists()) throw new Exception("文件不存在:[" + dirPath + "" + fileName + "]");
        Reader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
        char[] cache = new char[1024];
        int count = 0;
        StringBuffer sb = new StringBuffer();
        while ((count = in.read(cache)) > 0) {
            sb.append(new String(cache, 0, count));
        }
        if (in != null) {
            in.close();
        }
        return sb.toString();
    }

    public static void zipFiles(List<File> srcfile, File zipfile) {
        String zipPath = zipfile.getPath();

        int idx = zipPath.lastIndexOf("/");
        if (idx == -1) {
            idx = zipPath.lastIndexOf("\\");
        }
        if (idx != -1)
            FileUtil.checkExistDir(zipPath.substring(0, idx));

        logger.info("开始压缩文件：{}", zipPath);

        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            out = new ZipOutputStream(new FileOutputStream(zipfile), Charset.forName("GBK"));
            for (File file : srcfile) {
                logger.info("压缩文件：{}中_开始：{} ", zipfile.getName(), file.getName());
                FileInputStream in = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            logger.info("压缩文件完成：{}", zipfile.getName());
            //fis = new FileInputStream(zipfile);
            //FileUtil.download(request, response, fis, zipfile.getName());
        } catch (Exception e) {
            logger.error("压缩文件{}_异常：" + e.toString(), zipfile.getName());
            throw new RuntimeException("解压文件_异常：" + e.toString());
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void zipFiles(List<File> srcfile, List<String> fileNames, File zipfile) {
        String zipPath = zipfile.getPath();

        int idx = zipPath.lastIndexOf("/");
        if (idx == -1) {
            idx = zipPath.lastIndexOf("\\");
        }
        if (idx != -1)
            FileUtil.checkExistDir(zipPath.substring(0, idx));

        logger.info("开始压缩文件：{}", zipPath);

        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            //ZipOutputStream类：完成文件或文件夹的压缩
            out = new ZipOutputStream(new FileOutputStream(zipfile),Charset.forName("GBK"));

            for (int i = 0; i < srcfile.size(); i++) {
                logger.info("压缩文件：{}中_开始：{} ", zipfile.getName(), srcfile.get(i).getName());
                FileInputStream in = new FileInputStream(srcfile.get(i));
                out.putNextEntry(new ZipEntry(fileNames.get(i)));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            logger.info("压缩文件完成：{}", zipfile.getName());
            //fis = new FileInputStream(zipfile);
            //FileUtil.download(request, response, fis, zipfile.getName());
        } catch (Exception e) {
            logger.error("压缩文件{}_异常：" + e.toString(), zipfile.getName());
            throw new RuntimeException("解压文件_异常：" + e.toString());
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件下载
     */
    public static void download1(HttpServletRequest req, HttpServletResponse resp, FileInputStream fileStream,
                                 String name) throws IOException {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident"))
            name = java.net.URLEncoder.encode(name, "UTF-8");
        else
            name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
        resp.reset();
        resp.setContentType("application/vnd.ms-excel;charset=utf-8");
        resp.setHeader("Content-Disposition", "attachment;filename=" + name);
        BufferedInputStream bis = new BufferedInputStream(fileStream);
        ServletOutputStream out = resp.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] bytz = new byte[2048];
        int tmp = 0;
        try {
            while ((tmp = bis.read(bytz, 0, bytz.length)) != -1) {
                bos.write(bytz, 0, tmp);
            }
            bos.flush();
        } finally {
            try {
                fileStream.close();
                out.close();
                bis.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main1(String[] args) {
        byte[] buf = new byte[1024];
        ZipOutputStream out = null;
        try {
            // ZipOutputStream类：完成文件或文件夹的压缩
            out = new ZipOutputStream(new FileOutputStream("E:\\tets.zip"),Charset.forName("GBK"));
            FileInputStream in = new FileInputStream("F:\\cfems\\zllr\\GWA160000\\UWY3F0098\\Eclipse打开时报错.docx.docx");
            out.putNextEntry(new ZipEntry("Eclipse打开时报错.docx"));
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.closeEntry();
            in.close();
            //fis = new FileInputStream(zipfile);
            //FileUtil.download(request, response, fis, zipfile.getName());
        } catch (Exception e) {
            throw new RuntimeException("解压文件_异常：" + e.toString());
        } finally {
            try {
                if (null != out)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FileUtil.checkExistDir("c://cnm//");
    }
}
