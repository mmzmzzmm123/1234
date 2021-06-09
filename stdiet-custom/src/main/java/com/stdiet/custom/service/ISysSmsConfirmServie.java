package com.stdiet.custom.service;

public interface ISysSmsConfirmServie {

    public Integer sendSmsCode(String phone);

    public Integer checkSmsCode(String phone, String code);

}
