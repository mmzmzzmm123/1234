package com.ruoyi.project.data.price.service;

import com.ruoyi.project.common.VueSelectModel;
import com.ruoyi.project.data.price.domain.ArtificialResidenceRentBasePrice;
import com.ruoyi.project.data.price.domain.ComputeResidenceRentBasePrice;

import java.util.List;

/**
 * 人工修正住宅租赁Service接口
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public interface IArtificialResidenceRentPriceService {

    /**
     * 单条记录
     *
     * @param yearMonth
     * @param id
     * @return
     */
    ArtificialResidenceRentBasePrice selectById(Integer yearMonth, String id);

    /**
     * 查询人工修正住宅租赁列表
     *
     * @param officeBasePriceUltimate 人工修正住宅租赁
     * @return 人工修正住宅租赁集合
     */
    List<ArtificialResidenceRentBasePrice> selectList(ArtificialResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * @param officeBasePriceUltimate
     * @return
     */
    int selectCount(ArtificialResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * 获取表名
     *
     * @return
     */
    List<VueSelectModel> getYearMonth();

    /**
     * @param yearMonth
     */
    void importBySync(Integer yearMonth);

    /**
     * 更新
     * @param officeBasePriceUltimate
     * @return
     */
    int update(ArtificialResidenceRentBasePrice officeBasePriceUltimate);

    /**
     * 导入
     *
     * @param yearMonth
     * @param list
     * @return
     */
    String batchImport(Integer yearMonth, List<ComputeResidenceRentBasePrice> list);
}


