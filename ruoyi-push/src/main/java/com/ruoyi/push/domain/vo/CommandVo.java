package com.ruoyi.push.domain.vo;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.sign.Md5Utils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.push.consts.CommandConst;

/**
 * @Author lixin
 * @Date 2022/12/29 14:13
 */
public class CommandVo {
    /**
     * 盐， 如果修改，需要同步修改客户端，例如 前端文件: api/push/push.js ss
     */
    private static final String ss = "8489A0AC-7EF1-273B-41AE-71F784B7951A";

    /**
     * 命令
     */
    private String c;
    /**
     * 内容
     */
    private String b;
    /**
     * 发送时间
     */
    private Long d;
    /**
     * 签名
     */
    private String s;


    public CommandVo(){}

    public CommandVo (String c, Object b){
        this.c = c;
        this.b = JSON.toJSONString(b);
    }

    /**
     * 生成签名
     */
    public void generate() {
        this.d = DateUtils.getNowDate().getTime();
        //s = g(c + b + d + ss)
        this.s = Md5Utils.hash(this.c + this.b + this.d + ss);
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Long getD() {
        return d;
    }

    public void setD(Long d) {
        this.d = d;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }


}
