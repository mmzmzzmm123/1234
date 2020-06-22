package com.ruoyi.project.data.price.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceSaleBasePrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@DS("compute")
public interface ArtificialResidenceSaleBasePriceMapper {

    /**
     * @param id
     * @return
     */
    ArtificialResidenceSaleBasePrice selectById(String id);

    /**
     * 新增
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int insert(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 新增价格插入至基价库
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int insertUVBasePrice(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 批量插入
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int insertUVDocument(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 修改上期价格
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int updateLastMonthPrice(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 创建用于批量导入的存储过程
     *
     * @param yearMonth
     * @return
     */
    int prepareBachImport(@Param("yearMonth") Integer yearMonth);

    /**
     * 分页列表
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    List<ArtificialResidenceSaleBasePrice> selectPageList(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 分页总数
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    Integer selectPageCount(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * 更新
     *
     * @param artificialResidenceSaleBasePrice
     * @return
     */
    int updateEntity(ArtificialResidenceSaleBasePrice artificialResidenceSaleBasePrice);

    /**
     * @return
     */
    List<VueSelectModel> yearMonthList();
}
