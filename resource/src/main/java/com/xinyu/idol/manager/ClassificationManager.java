package com.xinyu.idol.manager;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinyu.idol.mapper.ClassificationsMapper;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ClassificationManager {

    @Autowired
    private ClassificationsMapper classificationsMapper;

    public List<ClassificationsEntity> selectClassificationsByParentId(String parentId) {
        Assert.notNull(parentId, "classificationId非空");
        List<ClassificationsEntity> classificationsEntityList = classificationsMapper.selectList(new QueryWrapper<ClassificationsEntity>().eq("parent_id", parentId));

        return classificationsEntityList;
    }

    public List<ClassificationsEntity> selectClassificationsByLevel(String level) {
        Assert.notNull(level, "classificationId非空");
        List<ClassificationsEntity> classificationsEntityList = classificationsMapper.selectList(new QueryWrapper<ClassificationsEntity>().eq("level", level));
        return classificationsEntityList;
    }

    public List<ClassificationsEntity> selectAllClassifications() {

        List<ClassificationsEntity> classificationsEntityList = classificationsMapper.selectList(new QueryWrapper<ClassificationsEntity>());
        return classificationsEntityList;
    }




}
