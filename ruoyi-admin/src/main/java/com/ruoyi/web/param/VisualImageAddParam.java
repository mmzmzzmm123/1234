package com.ruoyi.web.param;


import org.springframework.web.multipart.MultipartFile;

public class VisualImageAddParam {
    private MultipartFile file;

    private Integer width;

    private Integer heigth;

    public VisualImageAddParam() {
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeigth() {
        return heigth;
    }

    public void setHeigth(Integer heigth) {
        this.heigth = heigth;
    }
}
