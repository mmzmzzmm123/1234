package com.ruoyi.common.service.impl;

import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.domain.Sdhcircuit;
import com.ruoyi.common.mapper.SdhcircuitMapper;
import com.ruoyi.common.service.ISdhcircuitService;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 电路信息Service业务层处理
 *
 * @author ruoyi
 * @date 2023-12-23
 */
@Service
public class SdhcircuitServiceImpl implements ISdhcircuitService {
    @Autowired
    private SdhcircuitMapper sdhcircuitMapper;

    /**
     * 查询电路信息
     *
     * @param id 电路信息主键
     * @return 电路信息
     */
    @Override
    public Sdhcircuit selectSdhcircuitById(Long id) {
        return sdhcircuitMapper.selectSdhcircuitById(id);
    }

    /**
     * 查询电路信息列表
     *
     * @param sdhcircuit 电路信息
     * @return 电路信息
     */
    @Override
    public List<Sdhcircuit> selectSdhcircuitList(Sdhcircuit sdhcircuit) {
        return sdhcircuitMapper.selectSdhcircuitList(sdhcircuit);
    }

    /**
     * 导出电路信息列表
     *
     * @param sdhcircuit 电路信息
     * @return 电路信息
     */
    @Override
    public List<Sdhcircuit> exportSdhcircuitList(Sdhcircuit sdhcircuit) {
        return sdhcircuitMapper.exportSdhcircuitList(sdhcircuit);
    }

    /**
     * 新增电路信息
     *
     * @param sdhcircuit 电路信息
     * @return 结果
     */
    @Override
    public int insertSdhcircuit(Sdhcircuit sdhcircuit) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        sdhcircuit.setOperator(loginUser.getUsername());
        sdhcircuit.setUpdateoperator(loginUser.getUsername());
        sdhcircuit.setUpdatetime(new Date());
        return sdhcircuitMapper.insertSdhcircuit(sdhcircuit);
    }

    /**
     * 修改电路信息
     *
     * @param sdhcircuit 电路信息
     * @return 结果
     */
    @Override
    public int updateSdhcircuit(Sdhcircuit sdhcircuit) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        sdhcircuit.setUpdateoperator(loginUser.getUsername());
        return sdhcircuitMapper.updateSdhcircuit(sdhcircuit);
    }

    /**
     * 批量删除电路信息
     *
     * @param ids 需要删除的电路信息主键
     * @return 结果
     */
    @Override
    public int deleteSdhcircuitByIds(Long[] ids) {
        return sdhcircuitMapper.deleteSdhcircuitByIds(ids);
    }

    /**
     * 删除电路信息信息
     *
     * @param id 电路信息主键
     * @return 结果
     */
    @Override
    public int deleteSdhcircuitById(Long id) {
        return sdhcircuitMapper.deleteSdhcircuitById(id);
    }
}
