package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;

/**
 * @author shoreu
 */
public class ResultCaptcha extends Result {
    private String uuid;
    private String img;
    private Boolean captchaOnOff;

    private ResultCaptcha(Boolean captchaOnOff) {
        super(HttpStatus.SUCCESS);
        this.captchaOnOff = captchaOnOff;
    }


    private ResultCaptcha(String msg) {
        super(HttpStatus.ERROR, msg);
    }

    public static ResultCaptcha success(Boolean captchaOnOff) {
        return new ResultCaptcha(captchaOnOff);
    }

    public static ResultCaptcha error(String msg) {
        return new ResultCaptcha(msg);
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Boolean getCaptchaOnOff() {
        return this.captchaOnOff;
    }

    public void setCaptchaOnOff(Boolean captchaOnOff) {
        this.captchaOnOff = captchaOnOff;
    }

    public void setUuidImg(String uuid, String img) {
        this.uuid = uuid;
        this.img = img;
    }
}
