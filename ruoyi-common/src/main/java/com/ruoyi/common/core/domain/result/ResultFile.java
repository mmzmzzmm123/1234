package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;

/**
 * @author shoreu
 */
public class ResultFile extends Result {

    private String url;
    private String fileName;
    private String newFileName;
    private String originalFilename;

    private ResultFile(String url, String fileName, String newFileName, String originalFilename) {
        super(HttpStatus.SUCCESS);
        this.url = url;
        this.fileName = fileName;
        this.newFileName = newFileName;
        this.originalFilename = originalFilename;
    }

    private ResultFile(String msg) {
        super(HttpStatus.ERROR, msg);
    }

    public static ResultFile uploadFile(String url, String fileName, String newFileName, String originalFilename) {
        return new ResultFile(url, fileName, newFileName, originalFilename);
    }

    public static ResultFile error(String msg) {
        return new ResultFile(msg);
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNewFileName() {
        return this.newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public String getOriginalFilename() {
        return this.originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }
}
