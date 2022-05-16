package work.cib.zpyz;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.apache.commons.codec.binary.Base64;

/**
 * 图片转base64
 *
 */
public class PicToBase64 {

	public static byte[] ImageToBase64(String imgPath) {
		// TODO 自动生成的方法存根
		InputStream in = null;
		byte[] data = null;
		//	读取图片字节数组
		try{
			File file = new File(imgPath);
			if(!file.exists() && !file.isDirectory()){
				file.mkdirs();
			}
			in = new FileInputStream(imgPath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		//	对字节数组Base64编码
		Base64 encoder = new Base64();
		//	返回Base64编码过的字节数组字符串
		return encoder.encode(data);
	}
	
	
	

}
