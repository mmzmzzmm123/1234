package com.ruoyi.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;
import com.ruoyi.project.data.basis.domain.UvCommunityGeo;
import com.ruoyi.project.data.basis.domain.UvGeo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 小区坐标Mapper
 *
 * @author lihe
 */
@DS("teemlink")
public interface UvCommunityGeoMapper {

    /**
     * 获取所有
     *
     * @return
     */
    List<UvCommunityGeo> all();

    /**
     * 更新高德坐标
     *
     * @param uvCommunityGeo
     * @return
     */
    int updateGaoDeCoordinate(UvCommunityGeo uvCommunityGeo);

    /**
     * 获取指定表的geo数据
     *
     * @param tableName
     * @return
     */
    List<UvGeo> getSomeGeo(@Param("tableName") String tableName);

    /**
     * 更新geo
     * @param tableName
     * @param id
     * @param geo
     * @return
     */
    int updateSomeGeo(@Param("tableName") String tableName, @Param("geo") String geo, @Param("id") Integer id);

}
