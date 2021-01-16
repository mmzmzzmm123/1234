package com.ruoyi.jyykhgl.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.jyykhgl.mapper.TsbzJyyfwjcMapper;
import com.ruoyi.jyykhgl.domain.TsbzJyyfwjc;
import com.ruoyi.jyykhgl.service.ITsbzJyyfwjcService;

/**
 * 服务基层（教研员）Service业务层处理
 *
 * @author tsbz
 * @date 2021-01-16
 */
@Service
public class TsbzJyyfwjcServiceImpl implements ITsbzJyyfwjcService {
    @Autowired
    private TsbzJyyfwjcMapper tsbzJyyfwjcMapper;

    /**
     * 查询服务基层（教研员）
     *
     * @param id 服务基层（教研员）ID
     * @return 服务基层（教研员）
     */
    @Override
    public TsbzJyyfwjc selectTsbzJyyfwjcById(Long id) {
        return tsbzJyyfwjcMapper.selectTsbzJyyfwjcById(id);
    }

    /**
     * 查询服务基层（教研员）列表
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 服务基层（教研员）
     */
    @Override
    public List<TsbzJyyfwjc> selectTsbzJyyfwjcList(TsbzJyyfwjc tsbzJyyfwjc) {
        return tsbzJyyfwjcMapper.selectTsbzJyyfwjcList(tsbzJyyfwjc);
    }

    /**
     * 新增服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    @Override
    public int insertTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc) {
        tsbzJyyfwjc.setCreateTime(DateUtils.getNowDate());
        return tsbzJyyfwjcMapper.insertTsbzJyyfwjc(tsbzJyyfwjc);
    }

    /**
     * 修改服务基层（教研员）
     *
     * @param tsbzJyyfwjc 服务基层（教研员）
     * @return 结果
     */
    @Override
    public int updateTsbzJyyfwjc(TsbzJyyfwjc tsbzJyyfwjc) {
        return tsbzJyyfwjcMapper.updateTsbzJyyfwjc(tsbzJyyfwjc);
    }

    /**
     * 批量删除服务基层（教研员）
     *
     * @param ids 需要删除的服务基层（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyfwjcByIds(Long[] ids) {
        return tsbzJyyfwjcMapper.deleteTsbzJyyfwjcByIds(ids);
    }

    /**
     * 删除服务基层（教研员）信息
     *
     * @param id 服务基层（教研员）ID
     * @return 结果
     */
    @Override
    public int deleteTsbzJyyfwjcById(Long id) {
        return tsbzJyyfwjcMapper.deleteTsbzJyyfwjcById(id);
    }
}
