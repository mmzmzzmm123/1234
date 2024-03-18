package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizCarBrandMapper;
import com.baoli.sysmanage.domain.BaoliBizCarBrand;
import com.baoli.sysmanage.service.IBaoliBizCarBrandService;

/**
 * 汽车品牌Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-18
 */
@Service
public class BaoliBizCarBrandServiceImpl implements IBaoliBizCarBrandService 
{
    @Autowired
    private BaoliBizCarBrandMapper baoliBizCarBrandMapper;

    /**
     * 查询汽车品牌
     * 
     * @param id 汽车品牌主键
     * @return 汽车品牌
     */
    @Override
    public BaoliBizCarBrand selectBaoliBizCarBrandById(Long id)
    {
        return baoliBizCarBrandMapper.selectBaoliBizCarBrandById(id);
    }

    /**
     * 查询汽车品牌列表
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 汽车品牌
     */
    @Override
    public List<BaoliBizCarBrand> selectBaoliBizCarBrandList(BaoliBizCarBrand baoliBizCarBrand)
    {
        return baoliBizCarBrandMapper.selectBaoliBizCarBrandList(baoliBizCarBrand);
    }

    /**
     * 新增汽车品牌
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 结果
     */
    @Override
    public int insertBaoliBizCarBrand(BaoliBizCarBrand baoliBizCarBrand)
    {
        baoliBizCarBrand.setCreateTime(DateUtils.getNowDate());
        return baoliBizCarBrandMapper.insertBaoliBizCarBrand(baoliBizCarBrand);
    }

    /**
     * 修改汽车品牌
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 结果
     */
    @Override
    public int updateBaoliBizCarBrand(BaoliBizCarBrand baoliBizCarBrand)
    {
        baoliBizCarBrand.setUpdateTime(DateUtils.getNowDate());
        return baoliBizCarBrandMapper.updateBaoliBizCarBrand(baoliBizCarBrand);
    }

    /**
     * 批量删除汽车品牌
     * 
     * @param ids 需要删除的汽车品牌主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarBrandByIds(Long[] ids)
    {
        return baoliBizCarBrandMapper.deleteBaoliBizCarBrandByIds(ids);
    }

    /**
     * 删除汽车品牌信息
     * 
     * @param id 汽车品牌主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarBrandById(Long id)
    {
        return baoliBizCarBrandMapper.deleteBaoliBizCarBrandById(id);
    }
}
