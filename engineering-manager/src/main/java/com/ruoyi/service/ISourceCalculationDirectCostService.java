package com.ruoyi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.PageDto;
import com.ruoyi.entity.SourceCalculationDirectCost;
import com.ruoyi.entity.SourceZeroBill;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 成本测算导入（直接费） 服务类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
public interface ISourceCalculationDirectCostService extends IService<SourceCalculationDirectCost> {

    void importData(MultipartFile file);

    void saveOrUpdateData(SourceCalculationDirectCost sourceCalculationDirectCost);

    Page<SourceCalculationDirectCost> dataList(PageDto pageDto);
}
