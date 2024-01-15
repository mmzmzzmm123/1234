package com.ruoyi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.entity.SourceCalculationDirectCost;
import com.ruoyi.mapper.SourceCalculationDirectCostMapper;
import com.ruoyi.service.ISourceCalculationDirectCostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 成本测算导入（直接费） 服务实现类
 * </p>
 *
 * @author HH
 * @since 2024-01-13
 */
@Service
public class SourceCalculationDirectCostServiceImpl extends ServiceImpl<SourceCalculationDirectCostMapper, SourceCalculationDirectCost> implements ISourceCalculationDirectCostService {

    @Override
    public void importData(MultipartFile file) {

    }

    @Override
    public void saveOrUpdateData(SourceCalculationDirectCost sourceCalculationDirectCost) {
        LocalDateTime localDateTime = LocalDateTime.now();
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (null == loginUser) {
            throw new RuntimeException("获取用户信息异常");
        }

        if (null == sourceCalculationDirectCost.getId()) {
            // 新增
            sourceCalculationDirectCost.setCreateTime(localDateTime);
            sourceCalculationDirectCost.setUpdateTime(localDateTime);
            sourceCalculationDirectCost.setCreateUser(loginUser.getUserId().toString());
            sourceCalculationDirectCost.setUpdateUser(loginUser.getUserId().toString());
            sourceCalculationDirectCost.setDeleted(Boolean.TRUE);
            save(sourceCalculationDirectCost);
        } else {
            // 编辑
            sourceCalculationDirectCost.setUpdateTime(localDateTime);
            sourceCalculationDirectCost.setUpdateUser(loginUser.getUserId().toString());
            sourceCalculationDirectCost.setDeleted(Boolean.TRUE);
            updateById(sourceCalculationDirectCost);
        }
    }

    @Override
    public List<SourceCalculationDirectCost> dataList() {
        return lambdaQuery().eq(SourceCalculationDirectCost::getProjectNo, 124)
                .eq(SourceCalculationDirectCost::getDeleted, true)
                .list();
    }
}
