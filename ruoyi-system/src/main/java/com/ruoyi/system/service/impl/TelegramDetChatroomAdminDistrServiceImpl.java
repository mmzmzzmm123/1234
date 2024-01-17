package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.robot.TelegramDetChatroomAdminDistr;
import com.ruoyi.system.mapper.TelegramDetChatroomAdminDistrMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.output.ExtTgTelegramDetChatroomAdminDistr;
import com.ruoyi.system.service.TelegramDetChatroomAdminDistrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class TelegramDetChatroomAdminDistrServiceImpl extends ServiceImpl<TelegramDetChatroomAdminDistrMapper,TelegramDetChatroomAdminDistr> implements TelegramDetChatroomAdminDistrService {

    @Autowired
    private TelegramDetChatroomAdminDistrMapper telegramDetChatroomAdminDistrMapper;

    @Override
    public void story(List<TelegramDetChatroomAdminDistr> newAdminDistrs) {
        //塞进map方便对比
        Map<String,TelegramDetChatroomAdminDistr> newDataMap = new HashMap<>();
        newAdminDistrs.forEach(item->newDataMap.put(item.getAdminSerialNo(),item));
        //查询所有旧数据,用作对比
        List<TelegramDetChatroomAdminDistr> oldAdminDistrs = telegramDetChatroomAdminDistrMapper.selectList(null);
        if(CollectionUtils.isEmpty(oldAdminDistrs)){
            saveBatch(newAdminDistrs);
            return;
        }
        Map<String,TelegramDetChatroomAdminDistr> oldDataMap = new HashMap<>();
        oldAdminDistrs.forEach(item->oldDataMap.put(item.getAdminSerialNo(),item));

        ArrayList<TelegramDetChatroomAdminDistr> saveDataList = Lists.newArrayList();
        ArrayList<TelegramDetChatroomAdminDistr> updateDataList = Lists.newArrayList();
        ArrayList<String> deleteDataList = Lists.newArrayList();

        //不存在的新数据新增
        for (final TelegramDetChatroomAdminDistr newAdminDistr : newAdminDistrs) {
            if(!oldDataMap.containsKey(newAdminDistr.getAdminSerialNo())){
                saveDataList.add(newAdminDistr);
            }
        }

        //旧数据存在的修改,不存在的删除
        for (TelegramDetChatroomAdminDistr oldData : oldAdminDistrs) {
            if(newDataMap.containsKey(oldData.getAdminSerialNo())){
                boolean flag = false;
                TelegramDetChatroomAdminDistr newData = newDataMap.get(oldData.getAdminSerialNo());
                if(newData.getAccountNum() != oldData.getAccountNum()){
                    oldData.setAccountNum(newData.getAccountNum());
                    flag = true;
                }
                if(!newData.getAccountRate().equals(oldData.getAccountRate())){
                    oldData.setAccountRate(newData.getAccountRate());
                    flag = true;
                }
                if(newData.getAccountForbiddenNum() != oldData.getGroupForbiddenNum()){
                    oldData.setAccountForbiddenNum(newData.getAccountForbiddenNum());
                    flag = true;
                }
                if(newData.getGroupNum() != oldData.getGroupNum()){
                    oldData.setGroupNum(newData.getGroupNum());
                    flag = true;
                }
                if(!newData.getGroupRate().equals(oldData.getGroupRate())){
                    oldData.setGroupRate(newData.getGroupRate());
                    flag = true;
                }
                if(newData.getGroupForbiddenNum() != oldData.getAccountForbiddenNum()){
                    oldData.setGroupForbiddenNum(newData.getGroupForbiddenNum());
                    flag = true;
                }
                if(flag){
                    updateDataList.add(newData);
                }
                continue;
            }
            deleteDataList.add(oldData.getAdminSerialNo());
        }

        if(!CollectionUtils.isEmpty(updateDataList)){
            this.updateBatchById(updateDataList);
        }
        if(!CollectionUtils.isEmpty(saveDataList)){
            this.saveBatch(saveDataList);
        }
        if(CollectionUtils.isEmpty(deleteDataList)){
            this.removeByIds(deleteDataList);
        }

    }

    @Override
    public void syncChatroomAdminDistr() {
        OpenApiResult<List<ExtTgTelegramDetChatroomAdminDistr>> result = OpenApiClient.selectChatroomAdminDistrAllByThirdUtchatTg();
        log.info("syncChatroomAdminDistr result:{}", JSON.toJSONString(result));
        if(result.isSuccess()){
            List<ExtTgTelegramDetChatroomAdminDistr> data = result.getData();
            if(CollectionUtils.isEmpty(data)){
                List<TelegramDetChatroomAdminDistr> distrList = JSONArray.parseArray(JSONArray.toJSONString(data), TelegramDetChatroomAdminDistr.class);
                this.story(distrList);
            }

        }

    }
}
