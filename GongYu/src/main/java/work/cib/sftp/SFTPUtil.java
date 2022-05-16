package work.cib.sftp;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @author Freedom
 * 依赖 jsch-0.1.55.jar 不可使用过低的版本，如.29会存在连接时algorithm negotiation fail 密令算法错误的问题。
 * @ClassName: SFTPUtil
 * @Description: sftp工具类
 */
public class SFTPUtil {
    public Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

    /**
     * Session
     */
    private Session session = null;
    /**
     * Channel
     */
    private ChannelSftp channel = null;
    /**
     * SFTP服务器IP地址
     */
    private String host;
    /**
     * SFTP服务器端口
     */
    private int port;
    /**
     * 连接超时时间，单位毫秒
     */
    private int timeout;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * SFTP 安全文件传送协议
     *
     * @param host     SFTP服务器IP地址
     * @param port     SFTP服务器端口
     * @param timeout  连接超时时间，单位毫秒
     * @param username 用户名
     * @param password 密码
     */
    public SFTPUtil(String host, int port, int timeout, String username, String password) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.username = username;
        this.password = password;
    }

    public SFTPUtil() {
        // TODO 自动生成的构造函数存根
    }

    /**
     * 登陆SFTP服务器
     *
     * @return boolean
     */
    public boolean login() {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setTimeout(timeout);
            session.connect();
            logger.debug("sftp session connected");

            logger.debug("opening channel");
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            logger.debug("connected successfully");
            return true;
        } catch (JSchException e) {
            logger.error("sftp login failed", e);
            return false;
        } catch (Exception e) {
            logger.error("sftp login failed", e);
            return false;
        }
    }

    /**
     * 以文件上传
     *
     * @param remotePath SFTP服务器目录
     * @param fileName   服务器上保存的文件名
     * @param localPath  本地文件夹
     * @return boolean
     */
    public boolean uploadFile(String remotePath, String localPath, String fileName) {
        String currentDir = currentDir();
        if (!changeDir(remotePath)) {
            return false;
        }

        try {
            String backup_path = localPath + "backup\\";
            File file_backup = new File(backup_path);
            file_backup.mkdirs();
            String localFile = localPath + fileName;
            channel.put(localFile, remotePath, ChannelSftp.OVERWRITE);
            cutFile(localPath + fileName, backup_path + fileName);
            if (!existFile(fileName)) {
                logger.debug("upload failed");
                return false;
            }
            logger.debug("upload successful");
            return true;
        } catch (SftpException e) {
            logger.error("upload failed", e);
            return false;
        } catch (Exception e) {
            logger.error("upload failed", e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    /**
     * 批量文件上传
     *
     * @param remotePath SFTP服务器目录
     * @param localPath  本地文件夹
     * @return boolean
     */
    public boolean uploadBatchFile(String remotePath, String localPath) {
        String currentDir = currentDir();
        if (!changeDir(remotePath)) {
            return false;
        }

        try {
            File file = new File(localPath);
            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && !files[i].getName().contains("bak")) {
                    this.uploadFile(remotePath, localPath, files[i].getName());
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("upload failed", e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    /**
     * 下载文件
     *
     * @param remotePath SFTP服务器目录
     * @param fileName   服务器上需要下载的文件名
     * @param localPath  本地保存路径
     * @return boolean
     */
    public boolean downloadFile(String remotePath, String localPath, String fileName) throws SftpException {
        String currentDir = currentDir();
        if (!changeDir(remotePath)) {
            return false;
        }

        try {
            String localFilePath = localPath + fileName;
            channel.get(fileName, localFilePath);
            File localFile = new File(localFilePath);
            if (!localFile.exists()) {
                logger.debug("download file failed");
                return false;
            }
            logger.debug("download successful");
            return true;
        } catch (SftpException e) {
            logger.error("download file failed", e);
            return false;
        } finally {
            changeDir(currentDir);
        }
    }

    /**
     * 批量下载文件
     *
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载特定字符开头的文件(为空不做检验)
     * @param fileEndFormat：下载特定字符结尾的文件(为空不做检验)
     * @return
     */
    public List<String> downloadBatchFile(String remotePath, String localPath, String fileFormat, String fileEndFormat) throws SftpException {
        List<String> filenames = new ArrayList<String>();
        try {
            // connect();
            Vector v = listFiles(remotePath);
            // sftp.cd(remotePath);
            if (v.size() > 0) {
                logger.debug("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat.trim();
                        fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();

                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
                            if (filename.startsWith(fileFormat)) {
                                flag = downloadFile(remotePath, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
                            if (filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else {
                            flag = downloadFile(remotePath, localPath, filename);
                            if (flag) {
                                filenames.add(localFileName);
                            }
                        }
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("download file is success:remotePath=" + remotePath
                        + "and localPath=" + localPath + ",file size is"
                        + v.size());
            }
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            // this.disconnect();
        }
        return filenames;
    }

    /**
     * 将输入流的数的上传到stfp
     *
     * @param directory    上次目录
     * @param sftpFileName sftp端文件名
     * @param input        输入流
     * @throws SftpException
     */
    public void uploadNew(String directory, String sftpFileName, InputStream input) {
        try {
            channel.cd(directory);
        } catch (SftpException e) {
            try {
                channel.mkdir(directory);
                channel.cd(directory);
            } catch (SftpException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                logger.info(e.getMessage());
            }
        }
        try {
            channel.put(input, sftpFileName);
        } catch (SftpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.info(e.getMessage());
        }
        logger.info("通过sftp上传文件成功");
    }

    /**
     * 上传文件
     *
     * @param directory  服务器文件地址
     * @param uploadFile 本地文件地址-绝对地址
     * @throws FileNotFoundException
     * @throws SftpException
     */
    public void uploadNew(String directory, String uploadFile) {
        File file = new File(uploadFile);
        try {
            uploadNew(directory, file.getName(), new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.info(e.getMessage());
        }
    }

    /**
     * 剪切本地文件
     *
     * @param sourcePath 源文件路径
     * @param newPath    新文件路径
     * @return 新文件
     */
    public static File cutFile(String sourcePath, String newPath) {
        // 参数校验
        if (sourcePath == null || newPath == null) {
            return null;
        }
        File newFile;  // 要返回的新文件
        try {
            newFile = new File(newPath);  // 创建新文件
            File sourceFile = new File(sourcePath);//源文件
            FileInputStream inputStream = new FileInputStream(sourceFile);  //源文件的输入流
            FileOutputStream outputStream = new FileOutputStream(newFile);  //新文件的输出流

            // 复制数据
            byte[] bytes = new byte[1024];  // 字节数组用来保存读取的数据
            int readLen = 0;//读取的长度
            while ((readLen = inputStream.read(bytes, 0, 1024)) != -1) {
                // 输出流写出读取的数据
                outputStream.write(bytes, 0, readLen);
            }

            // 关闭输入流和输出流
            inputStream.close();
            outputStream.close();

            //则删除源文件
            sourceFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return newFile;
    }

    /**
     * 创建remote目录
     */
    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.channel.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    channel.cd(filePath.toString());
                } else {
                    // 建立目录
                    channel.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    channel.cd(filePath.toString());
                }
            }
            this.channel.cd(createpath);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 切换remote工作目录
     *
     * @param pathName 路径
     * @return boolean
     */
    public boolean changeDir(String pathName) {
        if (pathName == null || pathName.trim().equals("") || !isDirExist(pathName)) {
            logger.debug("invalid pathName");
            return false;
        }

        try {
            channel.cd(pathName.replaceAll("\\\\", "/"));
            logger.debug("directory successfully changed,current dir=" + channel.pwd());
            return true;
        } catch (SftpException e) {
            logger.error("failed to change directory", e);
            return false;
        }
    }

    /**
     * 列出remote目录下的文件
     *
     * @param directory：要列出的目录
     */
    public Vector listFiles(String directory) throws SftpException {
        return channel.ls(directory);
    }

    /**
     * 切换到remote上一级目录
     *
     * @return boolean
     */
    public boolean changeToParentDir() {
        return changeDir("src/main");
    }

    /**
     * 切换到remote根目录-已验证：windows下会转向用户目录（不论当前所在路径,但未验证不同盘符的情况）
     *
     * @return boolean
     */
    public boolean changeToHomeDir() {
        String homeDir = null;
        try {
            homeDir = channel.getHome();
        } catch (Exception e) {
            logger.error("can not get home directory", e);
            return false;
        }
        return changeDir(homeDir);
    }

    /**
     * 创建remote目录
     *
     * @param dirName 目录
     * @return boolean
     */
    public boolean makeDir(String dirName) {
        try {
            channel.mkdir(dirName);
            logger.debug("directory successfully created,dir=" + dirName);
            return true;
        } catch (SftpException e) {
            logger.error("failed to create directory", e);
            return false;
        }
    }

    /**
     * 删除remote文件夹
     *
     * @param dirName
     * @return boolean
     */
    @SuppressWarnings("unchecked")
    public boolean delDir(String dirName) {
        if (!changeDir(dirName)) {
            return false;
        }

        Vector<LsEntry> list = null;
        try {
            list = channel.ls(channel.pwd());
        } catch (SftpException e) {
            logger.error("can not list directory", e);
            return false;
        }

        for (LsEntry entry : list) {
            String fileName = entry.getFilename();
            if (!fileName.equals(".") && !fileName.equals("src/main")) {
                if (entry.getAttrs().isDir()) {
                    delDir(fileName);
                } else {
                    delFile(fileName);
                }
            }
        }

        if (!changeToParentDir()) {
            return false;
        }

        try {
            channel.rmdir(dirName);
            logger.debug("directory " + dirName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            logger.error("failed to delete directory " + dirName, e);
            return false;
        }
    }

    /**
     * 删除文件
     *
     * @param fileName 文件名
     * @return boolean
     */
    public boolean delFile(String fileName) {
        if (fileName == null || fileName.trim().equals("")) {
            logger.debug("invalid filename");
            return false;
        }

        try {
            channel.rm(fileName);
            logger.debug("file " + fileName + " successfully deleted");
            return true;
        } catch (SftpException e) {
            logger.error("failed to delete file " + fileName, e);
            return false;
        }
    }

    /**
     * 当前remote目录下文件及文件夹名称列表
     *
     * @return String[]
     */
    public String[] ls() {
        return list(Filter.ALL);
    }

    /**
     * 指定remote目录下文件及文件夹名称列表
     *
     * @return String[]
     */
    public String[] ls(String pathName) {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.ALL);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    /**
     * 当前remote目录下文件名称列表
     *
     * @return String[]
     */
    public String[] lsFiles() {
        return list(Filter.FILE);
    }

    /**
     * 指定remote目录下文件名称列表
     *
     * @return String[]
     */
    public String[] lsFiles(String pathName) {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.FILE);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    /**
     * 当前remote目录下文件夹名称列表
     *
     * @return String[]
     */
    public String[] lsDirs() {
        return list(Filter.DIR);
    }

    /**
     * 指定remote目录下文件夹名称列表
     *
     * @return String[]
     */
    public String[] lsDirs(String pathName) {
        String currentDir = currentDir();
        if (!changeDir(pathName)) {
            return new String[0];
        }

        String[] result = list(Filter.DIR);
        if (!changeDir(currentDir)) {
            return new String[0];
        }
        return result;
    }

    /**
     * 当前remote目录是否存在文件或文件夹
     *
     * @param name 名称
     * @return boolean
     */
    public boolean exist(String name) {
        return exist(ls(), name);
    }

    /**
     * 指定remote目录下，是否存在文件或文件夹-输入的目录必须存在，否则报异常
     *
     * @param path 目录
     * @param name 名称
     * @return boolean
     */
    public boolean exist(String path, String name) {
        return exist(ls(path), name);
    }

    /**
     * 当前remote目录是否存在文件
     *
     * @param name 文件名
     * @return boolean
     */
    public boolean existFile(String name) {
        return exist(lsFiles(), name);
    }

    /**
     * 指定remote目录下，是否存在文件-输入的目录必须存在，否则报异常
     *
     * @param path 目录
     * @param name 文件名
     * @return boolean
     */
    public boolean existFile(String path, String name) {
        return exist(lsFiles(path), name);
    }

    /**
     * 当前remote目录是否存在文件夹
     *
     * @param name 文件夹名称
     * @return boolean
     */
    public boolean existDir(String name) {
        return exist(lsDirs(), name);
    }

    /**
     * 判断remote目录是否存在
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channel.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 指定remote目录下，是否存在文件夹-输入的目录必须存在，否则报异常
     *
     * @param path 目录
     * @param name 文家夹名称
     * @return boolean
     */
    public boolean existDir(String path, String name) {
        return exist(lsDirs(path), name);
    }

    /**
     * 当前remote工作目录
     *
     * @return String
     */
    public String currentDir() {
        try {
            return channel.pwd();
        } catch (Exception e) {
            logger.error("failed to get current dir", e);
            return homeDir();
        }
    }

    /**
     * 登出
     */
    public void logout() {
        if (channel != null) {
            channel.quit();
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        logger.debug("logout successfully");
    }

    // ------private method ------

    /**
     * 枚举，用于过滤文件和文件夹
     */
    private enum Filter {
        /**
         * 文件及文件夹
         */
        ALL,
        /**
         * 文件
         */
        FILE,
        /**
         * 文件夹
         */
        DIR
    }

    /**
     * 列出当前remote目录下的文件及文件夹
     *
     * @param filter 过滤参数
     * @return String[]
     */
    @SuppressWarnings("unchecked")
    private String[] list(Filter filter) {
        Vector<LsEntry> list = null;
        try {
            // ls方法会返回两个特殊的目录，当前目录(.)和父目录(..)
            list = channel.ls(channel.pwd());
        } catch (SftpException e) {
            logger.error("can not list directory", e);
            return new String[0];
        }

        List<String> resultList = new ArrayList<String>();
        for (LsEntry entry : list) {
            if (filter(entry, filter)) {
                resultList.add(entry.getFilename());
            }
        }
        return resultList.toArray(new String[0]);
    }

    /**
     * 判断是否是否过滤条件
     *
     * @param entry LsEntry
     * @param f     过滤参数
     * @return boolean
     */
    private boolean filter(LsEntry entry, Filter f) {
        if (f.equals(Filter.ALL)) {
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("src/main");
        } else if (f.equals(Filter.FILE)) {
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("src/main") && !entry.getAttrs().isDir();
        } else if (f.equals(Filter.DIR)) {
            return !entry.getFilename().equals(".") && !entry.getFilename().equals("src/main") && entry.getAttrs().isDir();
        }
        return false;
    }

    /**
     * remote根目录
     *
     * @return String
     */
    private String homeDir() {
        try {
            return channel.getHome();
        } catch (Exception e) {
            return "/";
        }
    }

    /**
     * 判断字符串是否存在于数组中
     *
     * @param strArr 字符串数组
     * @param str    字符串
     * @return boolean
     */
    private boolean exist(String[] strArr, String str) {
        if (strArr == null || strArr.length == 0) {
            return false;
        }
        if (str == null || str.trim().equals("")) {
            return false;
        }
        for (String s : strArr) {
            if (s.equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }
}