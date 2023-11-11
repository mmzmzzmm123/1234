package com.xinyu.idol.mapper;

import com.xinyu.idol.pojo.entity.ClassificationsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 多级分类树表 Mapper 接口
 * </p>
 *
 * @author yu.li
 * @since 2023-10-16
 */
public interface ClassificationsMapper extends BaseMapper<ClassificationsEntity> {

    void insertListIgnore(@Param("classificationsEntityList") List<ClassificationsEntity> classificationsEntityList);

}
