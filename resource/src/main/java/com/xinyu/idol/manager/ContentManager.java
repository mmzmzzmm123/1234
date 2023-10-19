package com.xinyu.idol.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyu.idol.mapper.ContentMapper;
import com.xinyu.idol.pojo.entity.ContentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ContentManager {
    @Autowired
    ContentMapper contentMapper;

    public List selectAllTest(){

        List<ContentEntity> contentEntities = contentMapper.selectList(new QueryWrapper<>());
        return contentEntities;
    }

    public void insert(ContentEntity contentEntity){
        Assert.notNull(contentEntity,"contentEntity非空");
         contentMapper.insert(contentEntity);
    }

    public void updateByGuid(ContentEntity contentEntity){
        Assert.notNull(contentEntity,"contentEntity非空");
        //字段为null自动跳过
        contentMapper.update(contentEntity,new QueryWrapper<ContentEntity>().eq("guid",contentEntity.getGuid()));
    }
    public ContentEntity selectByIdentifier(String identifier){
        Assert.notNull(identifier,"identifier非空");
        ContentEntity contentEntity = contentMapper.selectOne(new QueryWrapper<ContentEntity>().eq("identifier", identifier));
        return contentEntity;
    }
}
