package work.cib.sftp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import com.jcraft.jsch.SftpException;
import org.junit.Test;

/**
使用参考及测试
 */
public class TestFSFTPutil {

	private static SFTPUtilMain getSftpUtil() {
		SFTPUtilMain sftp = new SFTPUtilMain("192.168.42.10", 22, 6000, "root", "root");
		return sftp;
	}
	

	public static void main(String []args) throws SftpException {
//        testLogin();
//        testMakeDir();
//        testDelFile();
//        testDelEmptyDir();
//        testDir();
//        testLs();
//        testParamLs();
//        testChangeDir();
//        testExist();
//        testParamExist();
        testUploadFile();
//        testUploadFile2();
//        testDownload();
//		testDownload1();
	}
	//登录登出
	public static void testLogin() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.logout();
	}

	//在远程机器上创建目录
	public static void testMakeDir() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.makeDir("qWerf");
		sftp.changeDir("./qWerf");
		sftp.makeDir("qWerf_1");
		sftp.logout();
	}
	
	//删除文件
	public static void testDelFile() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.changeDir("./qWerf");
		sftp.delFile("file.txt");
		sftp.logout();
	}

	//删除已存在的文件
	public static void testDelEmptyDir() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.changeDir("./qWerf");
		sftp.delDir("qWerf_1");
		sftp.logout();
	}
	
	//删除未存在的文件夹
	public static void testDir() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.delDir("test4");
		sftp.logout();
	}

	//展示当前目录下。。
	public static void testLs() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.changeDir("/src/main/work/cib/cib_SMS/");
		System.out.println(Arrays.toString(sftp.ls()));  //看文件和文件夹
		System.out.println(Arrays.toString(sftp.lsFiles()));  // 看文件
		System.out.println(Arrays.toString(sftp.lsDirs()));  // 看文件夹
		sftp.logout();
	}
	//展示指定目录
	public static void testParamLs() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		System.out.println(Arrays.toString(sftp.ls("qWerf")));
		System.out.println(Arrays.toString(sftp.lsFiles("qWerf")));
		System.out.println(Arrays.toString(sftp.lsDirs("qWerf")));
		sftp.logout();
	}

	//目录切换
	public static void testChangeDir() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.changeDir("./qWerf/qWe");
		sftp.changeToParentDir();
		sftp.changeToHomeDir();
		sftp.logout();
	}

	//判断文件、夹是否存在
	public static void testExist() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.changeDir("./qWerf/");
		System.out.println(sftp.exist("2fs.docx"));
		System.out.println(sftp.exist("qWerf"));
		System.out.println(sftp.existDir("qWerf"));
		System.out.println(sftp.existDir("2sfs.txt"));
		System.out.println(sftp.existFile("ssh"));
		System.out.println(sftp.existFile("test2"));
		sftp.logout();
	}

	//判断指定目录 文件、夹是否存在
	public static void testParamExist() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		System.out.println(sftp.exist("qWerf", "file1"));
		System.out.println(sftp.exist("test1", "test_bak.jpg"));
		System.out.println(sftp.existDir("/test1", "test3"));
		System.out.println(sftp.existDir("./qWerf", "file.txt"));
		System.out.println(sftp.existFile("qWerf", "file.txt"));
		System.out.println(sftp.existFile("test1", "test2"));
		sftp.logout();
	}

	//上传单个文件
	public static void testUploadFile() { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.uploadFile("/src/main/work/cib/cib_SMS", "E:\\cib\\","asdf.txt");
		sftp.logout();
	}

	//批量上传文件
	public static void testUploadFile2() {
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.uploadBatchFile("/src/main/work/cib/cib_SMS", "E:\\cib\\");
		sftp.logout();
	}

	//下载单个文件
	public static void testDownload() throws SftpException { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.downloadFile("/src/main/work/cib/cib_SMS", "E:\\cib\\lixinger.mybatis.test\\", "asdf.txt");
		sftp.logout();
	}

	//批量下载文件
	public static void testDownload1() throws SftpException { // OK
		SFTPUtil sftp = getSftpUtil();
		sftp.login();
		sftp.downloadBatchFile("/src/main/work/cib/cib_SMS", "E:\\cib\\lixinger.mybatis.test\\","",".txt");
		//sftp.downloadFile("test1/test2", "a.jar", "D:/");
		sftp.logout();
	}

}
