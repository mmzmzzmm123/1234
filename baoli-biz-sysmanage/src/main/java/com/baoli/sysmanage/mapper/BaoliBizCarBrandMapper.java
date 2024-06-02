package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizCarBrand;

/**
 * 汽车品牌Mapper接口
 * 
 * @author niujs
 * @date 2024-03-18
 */
public interface BaoliBizCarBrandMapper 
{
    /**
     * 查询汽车品牌
     * 
     * @param id 汽车品牌主键
     * @return 汽车品牌
     */
    public BaoliBizCarBrand selectBaoliBizCarBrandById(Long id);

    /**
     * 查询汽车品牌列表
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 汽车品牌集合
     */
    public List<BaoliBizCarBrand> selectBaoliBizCarBrandList(BaoliBizCarBrand baoliBizCarBrand);

    /**
     * 新增汽车品牌
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 结果
     */
    public int insertBaoliBizCarBrand(BaoliBizCarBrand baoliBizCarBrand);

    /**
     * 修改汽车品牌
     * 
     * @param baoliBizCarBrand 汽车品牌
     * @return 结果
     */
    public int updateBaoliBizCarBrand(BaoliBizCarBrand baoliBizCarBrand);

    /**
     * 删除汽车品牌
     * 
     * @param id 汽车品牌主键
     * @return 结果
     */
    public int deleteBaoliBizCarBrandById(Long id);

    /**
     * 批量删除汽车品牌
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizCarBrandByIds(Long[] ids);
}
