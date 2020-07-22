package com.ruoyi.project.data.basis.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.data.basis.domain.UVBasePrice;
import com.ruoyi.project.data.basis.domain.UVBasePriceQueryModel;
import com.ruoyi.project.data.basis.domain.UvCommunityGeo;

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

}
