package com.xinyu.idol.mapper;

import com.xinyu.idol.pojo.entity.ContentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import java.util.List;

/**
 * <p>
 * 资源冗余大表 Mapper 接口
 * </p>
 *
 * @author yu.li
 * @since 2023-10-17
 */
public interface ContentMapper extends BaseMapper<ContentEntity> {

    //void updateByGuid(@Param("contentEntity") ContentEntity contentEntity);

    List<ContentEntity> listOrderByField(@Param("guidList") List<String>guidList);

    void insertList(@Param("contentList") List<ContentEntity>contentList);

    List<ContentEntity> listOrderByGuid(@Param("guidList") List<String>guidList);


}
