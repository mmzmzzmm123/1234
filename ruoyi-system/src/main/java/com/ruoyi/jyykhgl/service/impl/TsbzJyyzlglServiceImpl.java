package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyyzlglMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyyzlgl;
import com.ruoyi.jyykhgl.service.ITsbzJyyzlglService;

/**
 * 质量管理（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-16
 */
@Service
public class TsbzJyyzlglServiceImpl implements ITsbzJyyzlglService {
    @Autowired
    private TsbzJyyzlglMapper tsbzJyyzlglMapper;

    /**
     * 查询质量管理（教研员）
     *
     * @param id 质量管理（教研员）ID
     * @return 质量管理（教研员）
     */
    @Override
    public TsbzJyyzlgl selectTsbzJyyzlglById(Long id) {
        return tsbzJyyzlglMapper.selectTsbzJyyzlglById(id);
    }

    /**
     * 查询质量管理（教研员）列表
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 质量管理（教研员）
     */
    @Override
    public List<TsbzJyyzlgl> selectTsbzJyyzlglList(TsbzJyyzlgl tsbzJyyzlgl) {
        return tsbzJyyzlglMapper.selectTsbzJyyzlglList(tsbzJyyzlgl);
    }

    /**
     * 新增质量管理（教研员）
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyyzlgl(TsbzJyyzlgl tsbzJyyzlgl) {
        tsbzJyyzlgl.setCreateTime(DateUtils.getNowDate());
        return tsbzJyyzlglMapper.insertTsbzJyyzlgl(tsbzJyyzlgl);
    }

    /**
     * 修改质量管理（教研员）
     *
     * @param tsbzJyyzlgl 质量管理（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyyzlgl(TsbzJyyzlgl tsbzJyyzlgl) {
        return tsbzJyyzlglMapper.updateTsbzJyyzlgl(tsbzJyyzlgl);
    }

    /**
     * 批量删除质量管理（教研员）
     *
     * @param ids 需要删除的质量管理（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyzlglByIds(Long[] ids) {
        return tsbzJyyzlglMapper.deleteTsbzJyyzlglByIds(ids);
    }

    /**
     * 删除质量管理（教研员）信息
     *
     * @param id 质量管理（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyzlglById(Long id) {
        return tsbzJyyzlglMapper.deleteTsbzJyyzlglById(id);
    }
}
