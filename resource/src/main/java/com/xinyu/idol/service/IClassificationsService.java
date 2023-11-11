package com.xinyu.idol.service;

import com.xinyu.idol.pojo.dto.InnerClassificationDto;
import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyu.idol.pojo.vo.ClassificationsReq;
import com.xinyu.idol.pojo.vo.ClassificationsResp;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 多级分类树表 服务类
 * </p>
 *
 * @author yu.li
 * @since 2023-10-16
 */
public interface IClassificationsService extends IService<ClassificationsEntity> {

    List<ClassificationsResp> classifications(ClassificationsReq classificationsReq);

    List<ClassificationsResp> classificationsByLevel(String level);

    Map<String,ClassificationsEntity> mapAllClassifications();

    void addOrUpdateClassifications(List<ClassificationsEntity> classificationsList);

    InnerClassificationDto listAll();

    void insertListSkip(List<ClassificationsEntity> classificationsList);



}
