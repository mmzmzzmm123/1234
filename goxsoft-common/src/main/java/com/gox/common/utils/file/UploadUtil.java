package com.gox.common.utils.file;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

public class UploadUtil {
    private UploadUtil() {
    }

    public static String mergeChunk(String parent, Chunk chunk, HttpServletResponse response) {
        File f = new File(parent);
        if (!f.exists()) {
            f.mkdirs();
        }
        File file = new File(parent, chunk.getFilename());
        //第一个块,则新建文件
        if (chunk.getChunkNumber() == 1 && !file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                response.setStatus(500);
                return "exception:createFileException";
            }
        }

        //进行写文件操作
        try (
                //将块文件写入文件中
                InputStream fos = chunk.getFile().getInputStream();
                RandomAccessFile raf = new RandomAccessFile(file, "rw")
        ) {
            int len = -1;
            byte[] buffer = new byte[1024];
            raf.seek((chunk.getChunkNumber() - 1) * 1024 * 1024);
            while ((len = fos.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (chunk.getChunkNumber() == 1) {
                file.delete();
            }
            response.setStatus(507);
            return "exception:writeFileException";
        }
        return "";
    }
}
