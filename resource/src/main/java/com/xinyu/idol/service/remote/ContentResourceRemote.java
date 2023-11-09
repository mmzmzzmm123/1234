package com.xinyu.idol.service.remote;


import com.xinyu.idol.config.EnvironmentMatchMap;
import com.xinyu.idol.pojo.dto.GuidListDto;
import com.xinyu.idol.pojo.dto.InnerResourceDto;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.utils.OkHttpUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentResourceRemote {

    @Autowired
    private EnvironmentMatchMap environmentMatchMap;

    private static String listResourceMapping="/inner/listByGuids";

    public List<ContentEntity> getByGuidList(List<String> guidList,String env){
        if(CollectionUtils.isEmpty(guidList)){
            return new ArrayList<>();
        }
        String domain = environmentMatchMap.getDomain(env);

        String reqUrl=domain+listResourceMapping;

        InnerResourceDto innerResourceDto = OkHttpUtil.<InnerResourceDto>postJsonAndToBean(reqUrl, GuidListDto.builder().guidList(guidList).build(), InnerResourceDto.class);

        Assert.notNull(innerResourceDto,"innerResourceDto非空");

        return innerResourceDto.getContentEntityList();

    }
}
