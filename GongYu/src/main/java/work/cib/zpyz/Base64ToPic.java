package work.cib.zpyz;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

//import org.castor.core.util.Base64Decoder;


/**
 * base64转文件
 *
 * @author cib
 */
public class Base64ToPic {
    /**
     * @param base64Str base664字符串
     * @param path      存放路径
     * @param ext       图片后缀
     * @return 存储地址
     */
    public static String Base64ToImage(String base64Str, String uploadName, String path, String ext) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
//		String uploadFileName = UUID.randomUUID().toString();
        File targetFile = new File(path, uploadName + "." + ext);
        try (OutputStream out = new FileOutputStream(targetFile)) {
            byte[] bytes = Base64.decodeBase64(base64Str);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {
                    bytes[i] += 256;
                }
            }
            out.write(bytes);
            out.flush();
            if (path.endsWith("/")) {
                return path + uploadName + "." + ext;
            } else {
                return path + "/" + uploadName + "." + ext;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
