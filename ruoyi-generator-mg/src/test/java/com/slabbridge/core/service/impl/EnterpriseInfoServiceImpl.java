package com.slabbridge.core.service.impl;

import java.util.List;
import java.util.Arrays;

import com.ruoyi.framework.service.BaseService;
import com.slabbridge.core.dao.EnterpriseInfoDAO;
import org.springframework.stereotype.Service;
import com.slabbridge.core.entity.EnterpriseInfo;
import com.slabbridge.core.service.IEnterpriseInfoService;
import com.slabbridge.core.entity.EnterpriseInfoExample;

/**
 * 企业账号Service业务层处理
 *
 * @author limingcai
 * @date 2021-04-12
 */
@Service
public class EnterpriseInfoServiceImpl extends BaseService<EnterpriseInfoDAO> implements IEnterpriseInfoService {
    /**
     * 查询企业账号
     *
     * @param id 企业账号ID
     * @return 企业账号
     */
    @Override
    public EnterpriseInfo selectEnterpriseInfoById(Long id) {
        return baseDAO.selectByPrimaryKey(id);
    }

    /**
     * 查询企业账号列表
     *
     * @param enterpriseInfo 企业账号
     * @return 企业账号
     */
    @Override
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo) {
        EnterpriseInfoExample example = new EnterpriseInfoExample();
        return baseDAO.selectByExample(example);
    }

    /**
     * 新增企业账号
     *
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    @Override
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return baseDAO.insert(enterpriseInfo);
    }

    /**
     * 修改企业账号
     *
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo) {
        return baseDAO.updateByPrimaryKeySelective(enterpriseInfo);
    }

    /**
     * 批量删除企业账号
     *
     * @param ids 需要删除的企业账号ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoByIds(Long[] ids) {
        if (null == ids || ids.length == 0) {
            return 0;
        }
        return baseDAO.batchDelete(Arrays.asList(ids));
    }

    /**
     * 删除企业账号信息
     *
     * @param id 企业账号ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoById(Long id) {
        return baseDAO.deleteByPrimaryKey(id);
    }

}
