package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.PageDto;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.entity.SourceCalculationMaterialCost;
import com.ruoyi.entity.SourceMixtureResolve;
import com.ruoyi.mapper.SourceCalculationMaterialCostMapper;
import com.ruoyi.service.ISourceCalculationMaterialCostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 成本测算---材料费 服务实现类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Service
public class SourceCalculationMaterialCostServiceImpl extends ServiceImpl<SourceCalculationMaterialCostMapper, SourceCalculationMaterialCost> implements ISourceCalculationMaterialCostService {

    @Override
    public void importData(MultipartFile file) {

    }

    @Override
    public void saveOrUpdateData(SourceCalculationMaterialCost sourceCalculationMaterialCost) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (null == loginUser){
            throw new RuntimeException("获取用户信息异常");
        }

        if (null == sourceCalculationMaterialCost.getId()){
            // 新增
            sourceCalculationMaterialCost.setCreateTime(localDateTime);
            sourceCalculationMaterialCost.setUpdateTime(localDateTime);
            sourceCalculationMaterialCost.setCreateUser(loginUser.getUserId().toString());
            sourceCalculationMaterialCost.setUpdateUser(loginUser.getUserId().toString());
            sourceCalculationMaterialCost.setDeleted(Boolean.TRUE);
            save(sourceCalculationMaterialCost);
        }else {
            // 编辑
            sourceCalculationMaterialCost.setUpdateTime(localDateTime);
            sourceCalculationMaterialCost.setUpdateUser(loginUser.getUserId().toString());
            sourceCalculationMaterialCost.setDeleted(Boolean.TRUE);
            updateById(sourceCalculationMaterialCost);
        }
    }

    @Override
    public List<SourceCalculationMaterialCost> dataList() {
        return lambdaQuery().eq(SourceCalculationMaterialCost::getProjectNo,124)
                .eq(SourceCalculationMaterialCost::getDeleted,true)
                .list();
    }
}
