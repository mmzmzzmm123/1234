package work.cib.zpyz;


import work.cib.util.StringUtils;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import java.io.File;


//import com.cib.cap4j.core.exception.BusinessException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//import java.util.zip.ZipOutputStream;

public class Zip {
    public Zip() {
    }

    /**
     * 压缩整个目录
     */
    public void zip(String zipDirFileName, String fileDir) throws HsException {
        try {
            zip(zipDirFileName, new File(fileDir));
        } catch (Exception e) {
            throw new HsException(HsErrorMsg.ERR_DEFAULT, "压缩文件夹出错！", e);
        }
    }

    /**
     * 压缩整个目录
     */
    private void zip(String zipFileName, File inputFile) throws Exception {
        ZipArchiveOutputStream out = new ZipArchiveOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFile, "");
        System.out.println("zip done");
        out.close();
    }

    private void zip(ZipArchiveOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putArchiveEntry(new ZipArchiveEntry(base + "/"));
            base = base.length() == 0 ? "" : base + "/";
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + fl[i].getName());
            }
        } else {
//            out.putNextEntry(new ZipArchiveEntry(base));
            out.putArchiveEntry(new ZipArchiveEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            System.out.println(base);
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    /**
     * @param zipFileName 为需要解压的zip文件
     * @param extPlace    为解压后文件的存放路径
     * @param qName       解压后的名字前面加上前缀
     *                    return  所有文件
     */
    @SuppressWarnings("rawtypes")
    public String[] UnZip(String zipFileName, String extPlace, String qName) throws HsException {
        String files = "";
        try {
            ZipFile zipFile = new ZipFile(zipFileName, "GBK");
            Enumeration e = zipFile.getEntries();
            ZipArchiveEntry zipEntry = null;
            while (e.hasMoreElements()) {
                zipEntry = (ZipArchiveEntry) e.nextElement();
                String entryName = zipEntry.getName();
                String names[] = entryName.split("/");
                int length = names.length;
                String path = extPlace;
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) {
                        path += names[v] + "/";
                        new File(path).mkdir();
                    } else { // 最后一个

                        if (entryName.endsWith("/")) { // 为目录,则创建文件夹
                            new File(extPlace + entryName).mkdir();
                        } else {
                            InputStream in = zipFile.getInputStream(zipEntry);
                            OutputStream os = new FileOutputStream(new File(extPlace + qName + entryName));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                os.write(buf, 0, len);
                            }
                            files += qName + entryName + "|";
                            in.close();
                            os.close();
                        }
                    }
                }
            }
            zipFile.close();
            return StringUtils.split(files, "|");
        } catch (Exception e) {
//            throw new BusinessException(HsErrorMsg.FILE_ERROR, "文件解压缩出错，文件：" + zipFileName);
        }
        return new String[]{};
    }

    /**
     * @param zipFileName 为需要解压的zip文件
     * @param extPlace    为解压后文件的存放路径
     */
    @SuppressWarnings("rawtypes")
    public static void UnZip(String zipFileName, String extPlace) throws HsException {
        try {
            if (!extPlace.endsWith("/")) {
                extPlace = extPlace + "/";
            }
            ZipFile zipFile = new ZipFile(zipFileName, "GBK");
            String oldExtPlace = extPlace;
            zipFile.getEntry(zipFileName);
            Enumeration e = zipFile.getEntries();
            ZipArchiveEntry zipEntry = null;
            while (e.hasMoreElements()) {
                extPlace = oldExtPlace;
                zipEntry = (ZipArchiveEntry) e.nextElement();
                String entryName = zipEntry.getName();
                String names[] = entryName.split("/");
                int length = names.length;
                File file = new File(extPlace);
                if (!file.exists() && !file.isDirectory()) {
                    file.mkdirs();
                }
                for (int v = 0; v < length; v++) {
                    if (v < length - 1) {
                        extPlace += names[v] + "/";
                        new File(extPlace).mkdir();
                    } else { // 最后一个
                        if (entryName.endsWith("/")) { // 为目录,则创建文件夹
                            new File(oldExtPlace + entryName).mkdir();
                        } else {
                            InputStream in = zipFile.getInputStream(zipEntry);
                            OutputStream os = new FileOutputStream(new File(oldExtPlace + entryName));
                            byte[] buf = new byte[1024];
                            int len;
                            while ((len = in.read(buf)) > 0) {
                                os.write(buf, 0, len);
                            }
                            in.close();
                            os.close();
                        }
                    }
                }
            }
            zipFile.close();
        } catch (Exception e) {
            e.printStackTrace();
//            throw new BusinessException(HsErrorMsg.FILE_ERROR, "文件解压缩出错，文件：" + zipFileName + e);
        }
    }

    public static void main(String args[]) {
        Zip zip = new Zip();
        try {
            zip.zip("D:\\ECLIPSETEST\\bots\\tamcx\\app/bots/infoDown/10001/20080920/1.zip",
                    "D:\\ECLIPSETEST\\bots\\tamcx\\app/bots/infoDown/10001/20080920/zip/");
        } catch (Exception e) {
            // TODO 自动生成 catch 块

            e.printStackTrace();
        }
    }
}

