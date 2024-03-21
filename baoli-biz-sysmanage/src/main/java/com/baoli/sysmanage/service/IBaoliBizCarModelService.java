package com.baoli.sysmanage.service;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizCarModel;

/**
 * 汽车型号Service接口
 * 
 * @author niujs
 * @date 2024-03-20
 */
public interface IBaoliBizCarModelService 
{
    /**
     * 查询汽车型号
     * 
     * @param id 汽车型号主键
     * @return 汽车型号
     */
    public BaoliBizCarModel selectBaoliBizCarModelById(Long id);

    /**
     * 查询汽车型号列表
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 汽车型号集合
     */
    public List<BaoliBizCarModel> selectBaoliBizCarModelList(BaoliBizCarModel baoliBizCarModel);

    /**
     * 新增汽车型号
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 结果
     */
    public int insertBaoliBizCarModel(BaoliBizCarModel baoliBizCarModel);

    /**
     * 修改汽车型号
     * 
     * @param baoliBizCarModel 汽车型号
     * @return 结果
     */
    public int updateBaoliBizCarModel(BaoliBizCarModel baoliBizCarModel);

    /**
     * 批量删除汽车型号
     * 
     * @param ids 需要删除的汽车型号主键集合
     * @return 结果
     */
    public int deleteBaoliBizCarModelByIds(Long[] ids);

    /**
     * 删除汽车型号信息
     * 
     * @param id 汽车型号主键
     * @return 结果
     */
    public int deleteBaoliBizCarModelById(Long id);
}
