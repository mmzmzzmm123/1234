package work.cib.sftp;


/**
 * @ClassName: SftpUtilMain
 * @Description: cap4j-sftp使用-工具类参数初始化设置. 方法使用入口
 * @author WangSZ
 * @date 2021年2月23日
 */
public class SFTPUtilMain extends SFTPUtil {
//	/** SFTP 登录用户名 */
//	private static String username = ApplicationContext.getInstance().getEnvPropertyValue("sftp.username");
//	
//	/** SFTP 登录密码 */
//	private static String password = ApplicationContext.getInstance().getEnvPropertyValue("sftp.password");
//	static {
//		System.out.println(username);
//	}
//	/** SFTP 服务器地址IP地址 */
//	private static String host = ApplicationContext.getInstance().getEnvPropertyValue("sftp.host");
//	/** SFTP 端口 */
//	private static int port = Integer.valueOf(ApplicationContext.getInstance().getEnvPropertyValue("sftp.port"));
//	/** SFTP 超时时间 */
//	private static int timeout = Integer.valueOf(ApplicationContext.getInstance().getEnvPropertyValue("sftp.timeout"));
	
	/*
	private static String host = "192.168.1.40";
	private static int port = 22;
	private static int timeout = 10000;
	private static String username = "wangzheng";
	private static String password = "123456";
	 */ 
	
//	public SFTPUtilMain() {		
//		super(host, port, timeout, username, password);	
//	}
	public SFTPUtilMain(){
		super();
	}
	public SFTPUtilMain(String host, int port, int timeout, String username, String password) {
		super("192.168.42.10", 22, 6000, "root", "root");
//		super(host, Integer.valueOf(port), Integer.valueOf(timeout), username, password);
	}
	
}
