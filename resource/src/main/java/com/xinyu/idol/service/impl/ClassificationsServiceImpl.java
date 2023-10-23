package com.xinyu.idol.service.impl;

import com.xinyu.idol.common.utils.StringUtils;
import com.xinyu.idol.common.utils.bean.BeanUtils;
import com.xinyu.idol.manager.ClassificationManager;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.xinyu.idol.mapper.ClassificationsMapper;
import com.xinyu.idol.pojo.vo.ClassificationsReq;
import com.xinyu.idol.pojo.vo.ClassificationsResp;
import com.xinyu.idol.service.IClassificationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 多级分类树表 服务实现类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-16
 */
@Service
public class ClassificationsServiceImpl extends ServiceImpl<ClassificationsMapper, ClassificationsEntity> implements IClassificationsService {

    @Autowired
    private ClassificationManager classificationManager;


    @Override
    public List<ClassificationsResp> classifications(ClassificationsReq classificationsReq) {
        Assert.notNull(classificationsReq, "classificationsReq非空");
        String parentId = "0";
        if (StringUtils.isNotEmpty(classificationsReq.getClassification1())) {
            parentId = classificationsReq.getClassification1();
        }
        if (StringUtils.isNotEmpty(classificationsReq.getClassification2())) {
            parentId = classificationsReq.getClassification2();
        }
        if (StringUtils.isNotEmpty(classificationsReq.getClassification3())) {
            parentId = classificationsReq.getClassification3();
        }

        List<ClassificationsResp> classificationsRespList = new ArrayList<>();
        List<ClassificationsEntity> classificationsEntityList = classificationManager.selectClassificationsByParentId(parentId);
        classificationsEntityList.forEach(it -> {
            ClassificationsResp classificationsResp=new ClassificationsResp();
            BeanUtils.copyProperties(it,classificationsResp);
            classificationsResp.setClassificationLevel(it.getLevel());
            classificationsRespList.add(classificationsResp);
        });

        return classificationsRespList;

    }

    @Override
    public List<ClassificationsResp> classificationsByLevel(String level) {
        if(StringUtils.isEmpty(level)){
            throw new RuntimeException("level参数异常");
        }
        List<ClassificationsEntity> classificationsEntityList = classificationManager.selectClassificationsByLevel(level);

        List<ClassificationsResp> classificationsRespList = new ArrayList<>();

        classificationsEntityList.forEach(it -> {
            ClassificationsResp classificationsResp=new ClassificationsResp();
            BeanUtils.copyProperties(it,classificationsResp);
            classificationsResp.setClassificationLevel(it.getLevel());
            classificationsRespList.add(classificationsResp);
        });

        return classificationsRespList;

    }
}
