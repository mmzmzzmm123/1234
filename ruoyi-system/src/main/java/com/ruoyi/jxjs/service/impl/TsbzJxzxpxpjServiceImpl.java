package com.ruoyi.jxjs.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jxjs.mapper.TsbzJxzxpxpjMapper;
import com.ruoyi.jxjs.domain.TsbzJxzxpxpj;
import com.ruoyi.jxjs.service.ITsbzJxzxpxpjService;

/**
 * 见习之星教师培训评价Service业务层处理
 *
 * @author ruoyi
 * @date 2020-11-09
 */
@Service
public class TsbzJxzxpxpjServiceImpl implements ITsbzJxzxpxpjService {
    @Autowired
    private TsbzJxzxpxpjMapper tsbzJxzxpxpjMapper;

    /**
     * 查询见习之星教师培训评价
     *
     * @param id 见习之星教师培训评价ID
     * @return 见习之星教师培训评价
     */
    @Override
    public TsbzJxzxpxpj selectTsbzJxzxpxpjById(Long id) {
        return tsbzJxzxpxpjMapper.selectTsbzJxzxpxpjById(id);
    }

    /**
     * 查询见习之星教师培训评价列表
     *
     * @param tsbzJxzxpxpj 见习之星教师培训评价
     * @return 见习之星教师培训评价
     */
    @Override
    public List<TsbzJxzxpxpj> selectTsbzJxzxpxpjList(TsbzJxzxpxpj tsbzJxzxpxpj) {
        return tsbzJxzxpxpjMapper.selectTsbzJxzxpxpjList(tsbzJxzxpxpj);
    }


    /**
     * 查询见习之星教师培训评价列表
     *
     * @param tsbzJxzxpxpj 见习之星教师培训评价
     * @return 见习之星教师培训评价集合
     */
    @Override
    public List<TsbzJxzxpxpj> selectTsbzJxzxpxpjListExport(TsbzJxzxpxpj tsbzJxzxpxpj) {
        return tsbzJxzxpxpjMapper.selectTsbzJxzxpxpjListExport(tsbzJxzxpxpj);
    }

    /**
     * 新增见习之星教师培训评价
     *
     * @param tsbzJxzxpxpj 见习之星教师培训评价
     * @return 结果
     */
    @Override
    public int insertTsbzJxzxpxpj(TsbzJxzxpxpj tsbzJxzxpxpj) {
        tsbzJxzxpxpj.setCreateTime(DateUtils.getNowDate());
        return tsbzJxzxpxpjMapper.insertTsbzJxzxpxpj(tsbzJxzxpxpj);
    }

    /**
     * 修改见习之星教师培训评价
     *
     * @param tsbzJxzxpxpj 见习之星教师培训评价
     * @return 结果
     */
    @Override
    public int updateTsbzJxzxpxpj(TsbzJxzxpxpj tsbzJxzxpxpj) {
        return tsbzJxzxpxpjMapper.updateTsbzJxzxpxpj(tsbzJxzxpxpj);
    }

    /**
     * 批量删除见习之星教师培训评价
     *
     * @param ids 需要删除的见习之星教师培训评价ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxpxpjByIds(Long[] ids) {
        return tsbzJxzxpxpjMapper.deleteTsbzJxzxpxpjByIds(ids);
    }

    /**
     * 删除见习之星教师培训评价信息
     *
     * @param id 见习之星教师培训评价ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJxzxpxpjById(Long id) {
        return tsbzJxzxpxpjMapper.deleteTsbzJxzxpxpjById(id);
    }
}
