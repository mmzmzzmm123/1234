package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;

import java.util.List;

/**
 * @author shoreu
 */
public class ResultFileArray extends Result {
    private List<String> urls;
    private List<String> fileNames;
    private List<String> newFileNames;
    private List<String> originalFilenames;

    private ResultFileArray(List<String> urls, List<String> fileNames, List<String> newFileNames, List<String> originalFilenames) {
        super(HttpStatus.SUCCESS);
        this.urls = urls;
        this.fileNames = fileNames;
        this.newFileNames = newFileNames;
        this.originalFilenames = originalFilenames;
    }

    private ResultFileArray(String msg) {
        super(HttpStatus.ERROR, msg);
    }

    public static ResultFileArray uploadFiles(List<String> urls, List<String> fileNames, List<String> newFileNames, List<String> originalFilenames) {
        return new ResultFileArray(urls, fileNames, newFileNames, originalFilenames);
    }

    public static ResultFileArray error(String msg) {
        return new ResultFileArray(msg);
    }

    public List<String> getUrls() {
        return this.urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getFileNames() {
        return this.fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public List<String> getNewFileNames() {
        return this.newFileNames;
    }

    public void setNewFileNames(List<String> newFileNames) {
        this.newFileNames = newFileNames;
    }

    public List<String> getOriginalFilenames() {
        return this.originalFilenames;
    }

    public void setOriginalFilenames(List<String> originalFilenames) {
        this.originalFilenames = originalFilenames;
    }
}
