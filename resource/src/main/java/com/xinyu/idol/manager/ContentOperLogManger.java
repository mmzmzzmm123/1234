package com.xinyu.idol.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyu.idol.mapper.ContentOperLogMapper;
import com.xinyu.idol.pojo.entity.ContentOperLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ContentOperLogManger {

    @Autowired
    private ContentOperLogMapper contentOperLogMapper;

    public void insert(ContentOperLogEntity contentOperLogEntity) {
        Assert.notNull(contentOperLogEntity, "contentOperLogEntity 为空");
        contentOperLogMapper.insert(contentOperLogEntity);

    }

//    public ContentOperLogEntity selectByIdentifier(String identifier) {
//        Assert.notNull(identifier, "identifier 为空");
//        return contentOperLogMapper.selectOne(new QueryWrapper<ContentOperLogEntity>().eq("identifier", identifier));
//
//    }


}
