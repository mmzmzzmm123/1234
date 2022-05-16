package work.cib.sftp;

import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor implements SftpProgressMonitor {
    //init():    当文件开始传输时，调用init方法。
    //count():   当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小。
    //end():     当传输结束时，调用end方法。

    private long transfered;
    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        System.out.println("Currently transferred total size: " + transfered + " bytes");
        return true;
    }

    @Override
    public void end() {
        System.out.println("Transferring done.");
    }

    @Override
    public void init(int op, String src, String dest, long max) {
        System.out.println("Transferring begin.");
    }
}
