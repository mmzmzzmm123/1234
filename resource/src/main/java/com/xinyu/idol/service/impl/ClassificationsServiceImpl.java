package com.xinyu.idol.service.impl;

import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.xinyu.idol.mapper.ClassificationsMapper;
import com.xinyu.idol.service.IClassificationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
