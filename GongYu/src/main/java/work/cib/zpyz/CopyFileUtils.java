package work.cib.zpyz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件复制
 *
 */
public class CopyFileUtils {
	
	public static void copyFileUsingStream(File source,File dest) throws IOException{
		InputStream is = null;
		OutputStream os = null;
		try{
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] bytes = new byte[1024];
			int length;
			while((length = is.read(bytes)) > 0){
				os.write(bytes,0,length);
			}
		}finally{
			is.close();
			os.close();
		}		
	}
}
