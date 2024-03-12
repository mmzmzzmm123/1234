package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizCarbrandMapper;
import com.baoli.sysmanage.domain.BaoliBizCarbrand;
import com.baoli.sysmanage.service.IBaoliBizCarbrandService;

/**
 * 汽车品牌Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-12
 */
@Service
public class BaoliBizCarbrandServiceImpl implements IBaoliBizCarbrandService 
{
    @Autowired
    private BaoliBizCarbrandMapper baoliBizCarbrandMapper;

    /**
     * 查询汽车品牌
     * 
     * @param id 汽车品牌主键
     * @return 汽车品牌
     */
    @Override
    public BaoliBizCarbrand selectBaoliBizCarbrandById(Long id)
    {
        return baoliBizCarbrandMapper.selectBaoliBizCarbrandById(id);
    }

    /**
     * 查询汽车品牌列表
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 汽车品牌
     */
    @Override
    public List<BaoliBizCarbrand> selectBaoliBizCarbrandList(BaoliBizCarbrand baoliBizCarbrand)
    {
        return baoliBizCarbrandMapper.selectBaoliBizCarbrandList(baoliBizCarbrand);
    }

    /**
     * 新增汽车品牌
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 结果
     */
    @Override
    public int insertBaoliBizCarbrand(BaoliBizCarbrand baoliBizCarbrand)
    {
        return baoliBizCarbrandMapper.insertBaoliBizCarbrand(baoliBizCarbrand);
    }

    /**
     * 修改汽车品牌
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 结果
     */
    @Override
    public int updateBaoliBizCarbrand(BaoliBizCarbrand baoliBizCarbrand)
    {
        return baoliBizCarbrandMapper.updateBaoliBizCarbrand(baoliBizCarbrand);
    }

    /**
     * 批量删除汽车品牌
     * 
     * @param ids 需要删除的汽车品牌主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarbrandByIds(Long[] ids)
    {
        return baoliBizCarbrandMapper.deleteBaoliBizCarbrandByIds(ids);
    }

    /**
     * 删除汽车品牌信息
     * 
     * @param id 汽车品牌主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarbrandById(Long id)
    {
        return baoliBizCarbrandMapper.deleteBaoliBizCarbrandById(id);
    }
}
