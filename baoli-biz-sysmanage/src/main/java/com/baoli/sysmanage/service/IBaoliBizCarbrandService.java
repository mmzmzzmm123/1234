package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizCarbrand;

/**
 * 汽车品牌Service接口
 * 
 * @author niujs
 * @date 2024-03-12
 */
public interface IBaoliBizCarbrandService 
{
    /**
     * 查询汽车品牌
     * 
     * @param id 汽车品牌主键
     * @return 汽车品牌
     */
    public BaoliBizCarbrand selectBaoliBizCarbrandById(Long id);

    /**
     * 查询汽车品牌列表
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 汽车品牌集合
     */
    public List<BaoliBizCarbrand> selectBaoliBizCarbrandList(BaoliBizCarbrand baoliBizCarbrand);

    /**
     * 新增汽车品牌
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 结果
     */
    public int insertBaoliBizCarbrand(BaoliBizCarbrand baoliBizCarbrand);

    /**
     * 修改汽车品牌
     * 
     * @param baoliBizCarbrand 汽车品牌
     * @return 结果
     */
    public int updateBaoliBizCarbrand(BaoliBizCarbrand baoliBizCarbrand);

    /**
     * 批量删除汽车品牌
     * 
     * @param ids 需要删除的汽车品牌主键集合
     * @return 结果
     */
    public int deleteBaoliBizCarbrandByIds(Long[] ids);

    /**
     * 删除汽车品牌信息
     * 
     * @param id 汽车品牌主键
     * @return 结果
     */
    public int deleteBaoliBizCarbrandById(Long id);
}
