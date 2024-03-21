package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizCarModelMapper;
import com.baoli.sysmanage.domain.BaoliBizCarModel;
import com.baoli.sysmanage.service.IBaoliBizCarModelService;

/**
 * 汽车型号Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-20
 */
@Service
public class BaoliBizCarModelServiceImpl implements IBaoliBizCarModelService 
{
    @Autowired
    private BaoliBizCarModelMapper baoliBizCarModelMapper;

    /**
     * 查询汽车型号
     * 
     * @param id 汽车型号主键
     * @return 汽车型号
     */
    @Override
    public BaoliBizCarModel selectBaoliBizCarModelById(Long id)
    {
        return baoliBizCarModelMapper.selectBaoliBizCarModelById(id);
    }

    /**
     * 查询汽车型号列表
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 汽车型号
     */
    @Override
    public List<BaoliBizCarModel> selectBaoliBizCarModelList(BaoliBizCarModel baoliBizCarModel)
    {
        return baoliBizCarModelMapper.selectBaoliBizCarModelList(baoliBizCarModel);
    }

    /**
     * 新增汽车型号
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 结果
     */
    @Override
    public int insertBaoliBizCarModel(BaoliBizCarModel baoliBizCarModel)
    {
        baoliBizCarModel.setCreateTime(DateUtils.getNowDate());
        return baoliBizCarModelMapper.insertBaoliBizCarModel(baoliBizCarModel);
    }

    /**
     * 修改汽车型号
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 结果
     */
    @Override
    public int updateBaoliBizCarModel(BaoliBizCarModel baoliBizCarModel)
    {
        baoliBizCarModel.setUpdateTime(DateUtils.getNowDate());
        return baoliBizCarModelMapper.updateBaoliBizCarModel(baoliBizCarModel);
    }

    /**
     * 批量删除汽车型号
     * 
     * @param ids 需要删除的汽车型号主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarModelByIds(Long[] ids)
    {
        return baoliBizCarModelMapper.deleteBaoliBizCarModelByIds(ids);
    }

    /**
     * 删除汽车型号信息
     * 
     * @param id 汽车型号主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarModelById(Long id)
    {
        return baoliBizCarModelMapper.deleteBaoliBizCarModelById(id);
    }
}
