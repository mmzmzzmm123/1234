
package work.cib.zpyz;

/**
 * 系统名称: 兴业银行  批量模块
 */
public class RaR {
    /*
     * cmd 压缩与解压缩命令
     */
    private static String rarCmd = "C:\\Program Files\\WinRAR\\Rar.exe a ";
    private static String unrarCmd = "C:\\Program Files\\WinRAR\\UnRar x ";

    /**
     * 将1个文件压缩成RAR格式
     * rarName 压缩后的压缩文件名(不包含后缀)
     * fileName 需要压缩的文件名(必须包含路径)
     * destDir 压缩后的压缩文件存放路径
     */
    public static void RARFile(String rarName, String fileName, String destDir) {
//	       rarCmd = destDir + rarName + ".rar " + fileName;   
        try {
            Runtime rt = Runtime.getRuntime();
//	           Process p = rt.exec(rarCmd+destDir + rarName + ".rar " + fileName);   
            rt.exec(rarCmd + destDir + rarName + ".rar " + fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 将1个RAR文件解压
     * rarFileName 需要解压的RAR文件(必须包含路径信息以及后缀)
     * destDir 解压后的文件放置目录
     */
    public static void unRARFile(String rarFileName, String destDir) {
//	       unrarCmd += rarFileName + " " + destDir;   
        try {
            Runtime rt = Runtime.getRuntime();
//	           Process p = rt.exec(unrarCmd + rarFileName + " " + destDir);    
            rt.exec(unrarCmd + rarFileName + " " + destDir);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        unRARFile("d:/cap4jv3/upload/2021/8/C01CB15F51B74872948E69C856CBFE28.rar", "E:/zpyz/20210802/3/unzip/");
    }
}
