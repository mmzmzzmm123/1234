package com.ruoyi.project.data.price.compute.service;

import com.ruoyi.project.data.price.compute.domain.OfficeBasePriceUltimate;
import com.ruoyi.project.system.domain.SysUser;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IOfficeBasePriceUltimateService {

    /**
     * 查询【请填写功能名称】列表
     *
     * @param officeBasePriceUltimate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    List<OfficeBasePriceUltimate> selectOfficeBasePriceUltimateList(OfficeBasePriceUltimate officeBasePriceUltimate);

    int selectOfficeBasePriceUltimateListCount(OfficeBasePriceUltimate officeBasePriceUltimate);


    OfficeBasePriceUltimate selectOfficeBasePriceUltimateById(String id);

    int updateOfficeBasePriceUltimate(OfficeBasePriceUltimate officeBasePriceUltimate);

    /**
     *
     * @param officeBasePriceUltimates
     * @param operName
     * @return
     */
    String batchImport(List<OfficeBasePriceUltimate> officeBasePriceUltimates,String operName);

}


