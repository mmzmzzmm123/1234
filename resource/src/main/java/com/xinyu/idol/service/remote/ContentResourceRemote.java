package com.xinyu.idol.service.remote;


import com.xinyu.idol.config.EnvironmentMatchMap;
import com.xinyu.idol.pojo.dto.GuidListDto;
import com.xinyu.idol.pojo.dto.InnerClassificationDto;
import com.xinyu.idol.pojo.dto.InnerResourceDto;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.xinyu.idol.pojo.entity.ContentEntity;
import com.xinyu.idol.utils.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ContentResourceRemote {

    @Autowired
    private EnvironmentMatchMap environmentMatchMap;

    private static String listResourceMapping="/inner/listByGuids";

    private static String listAllClassifications="/inner/listAllClassifications";

    private static String listIdentifier="/inner/listIdentifier";

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

    public List<ClassificationsEntity> getByClassificationIdList(String env){
        Assert.notNull(env,"env非空");

        String domain = environmentMatchMap.getDomain(env);

        String reqUrl=domain+listAllClassifications;

        InnerClassificationDto innerClassificationDto = OkHttpUtil.<InnerClassificationDto>postJsonAndToBean(reqUrl, null, InnerClassificationDto.class);

        Assert.notNull(innerClassificationDto,"innerClassificationDto非空");

        return innerClassificationDto.getClassificationsEntityList();

    }

    public List<ContentEntity> listIdentifier(List<String>guidList,String env){

        Assert.notNull(guidList,"guidList非空");
        Assert.notNull(env,"env非空");

        String domain = environmentMatchMap.getDomain(env);

        String reqUrl=domain+listIdentifier;

        InnerResourceDto innerResourceDto = new InnerResourceDto();
        //远程异常不抛出，返回默认值
        try {
            innerResourceDto = OkHttpUtil.<InnerResourceDto>postJsonAndToBean(reqUrl, GuidListDto.builder().guidList(guidList).build(), InnerResourceDto.class);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ArrayList<>();
        }
        Assert.notNull(innerResourceDto,"innerClassificationDto非空");

        return innerResourceDto.getContentEntityList();

    }
}
