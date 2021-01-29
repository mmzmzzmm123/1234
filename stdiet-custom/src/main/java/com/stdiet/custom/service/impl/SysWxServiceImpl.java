package com.stdiet.custom.service.impl;

import com.stdiet.custom.service.ISysWxService;
import com.stdiet.custom.utils.WxTokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysWxServiceImpl implements ISysWxService {

    @Override
    public boolean wxCheckAuth(String signature, String timestamp, String nonce) {
        return WxTokenUtils.checkSignature(signature, timestamp, nonce);
    }
}
