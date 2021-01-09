package com.gox.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class MergeRunnable implements Runnable{
    long startPos;
    String mergeFileName;
    File partFile;
    public MergeRunnable(long startPos, String mergeFileName, File partFile) {
        this.startPos = startPos;
        this.mergeFileName = mergeFileName;
        this.partFile = partFile;
    }
    @Override
    public void run() {
        RandomAccessFile rFile;
        try {
            rFile = new RandomAccessFile(mergeFileName, "rw");
            rFile.seek(startPos);
            FileInputStream fs = new FileInputStream(partFile);
            byte[] b = new byte[fs.available()];
            fs.read(b);
            fs.close();
            rFile.write(b);
            rFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
