package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.PageDto;
import com.ruoyi.entity.SourceCalculationMaterialCost;
import com.ruoyi.entity.SourceMixtureResolve;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 成本测算---材料费 服务类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
public interface ISourceCalculationMaterialCostService extends IService<SourceCalculationMaterialCost> {

    void importData(MultipartFile file);

    void saveOrUpdateData(SourceCalculationMaterialCost sourceCalculationMaterialCost);

    Page<SourceCalculationMaterialCost> dataList(PageDto pageDto);

}
