package com.ruoyi.fin.service.impl;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.fin.domain.old.Userinfo;
import com.ruoyi.fin.mapper.TransferMapper;
import com.ruoyi.fin.service.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TransferServiceImpl implements ITransferService {
    @Resource
    private TransferMapper transferMapper;
    @Override
    public void execDataTransfer(String tabName){
        for (int i = 1; i < 10000; i++) {
            PageUtils.startPage(i,500,true);
            PageUtils.orderBy("openid");

            List<Userinfo> list = transferMapper.getSourceUser();
            if(list.size() == 0){
                break;
            }

        }
    }
}
