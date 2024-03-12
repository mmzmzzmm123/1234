package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizCarmodelMapper;
import com.baoli.sysmanage.domain.BaoliBizCarmodel;
import com.baoli.sysmanage.service.IBaoliBizCarmodelService;

/**
 * 汽车型号Service业务层处理
 * 
 * @author niujs
 * @date 2024-03-12
 */
@Service
public class BaoliBizCarmodelServiceImpl implements IBaoliBizCarmodelService 
{
    @Autowired
    private BaoliBizCarmodelMapper baoliBizCarmodelMapper;

    /**
     * 查询汽车型号
     * 
     * @param id 汽车型号主键
     * @return 汽车型号
     */
    @Override
    public BaoliBizCarmodel selectBaoliBizCarmodelById(Long id)
    {
        return baoliBizCarmodelMapper.selectBaoliBizCarmodelById(id);
    }

    /**
     * 查询汽车型号列表
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 汽车型号
     */
    @Override
    public List<BaoliBizCarmodel> selectBaoliBizCarmodelList(BaoliBizCarmodel baoliBizCarmodel)
    {
        return baoliBizCarmodelMapper.selectBaoliBizCarmodelList(baoliBizCarmodel);
    }

    /**
     * 新增汽车型号
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 结果
     */
    @Override
    public int insertBaoliBizCarmodel(BaoliBizCarmodel baoliBizCarmodel)
    {
        return baoliBizCarmodelMapper.insertBaoliBizCarmodel(baoliBizCarmodel);
    }

    /**
     * 修改汽车型号
     * 
     * @param baoliBizCarmodel 汽车型号
     * @return 结果
     */
    @Override
    public int updateBaoliBizCarmodel(BaoliBizCarmodel baoliBizCarmodel)
    {
        return baoliBizCarmodelMapper.updateBaoliBizCarmodel(baoliBizCarmodel);
    }

    /**
     * 批量删除汽车型号
     * 
     * @param ids 需要删除的汽车型号主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarmodelByIds(Long[] ids)
    {
        return baoliBizCarmodelMapper.deleteBaoliBizCarmodelByIds(ids);
    }

    /**
     * 删除汽车型号信息
     * 
     * @param id 汽车型号主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCarmodelById(Long id)
    {
        return baoliBizCarmodelMapper.deleteBaoliBizCarmodelById(id);
    }
}
