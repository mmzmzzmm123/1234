package com.edgelight.ed.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edgelight.ed.mapper.EnterpriseInfoMapper;
import com.edgelight.ed.domain.EnterpriseInfo;
import com.edgelight.ed.service.IEnterpriseInfoService;

/**
 * 企业账号Service业务层处理
 * 
 * @author limingcai
 * @date 2021-04-09
 */
@Service
public class EnterpriseInfoServiceImpl implements IEnterpriseInfoService 
{
    @Autowired
    private EnterpriseInfoMapper enterpriseInfoMapper;

    /**
     * 查询企业账号
     * 
     * @param id 企业账号ID
     * @return 企业账号
     */
    @Override
    public EnterpriseInfo selectEnterpriseInfoById(Long id)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoById(id);
    }

    /**
     * 查询企业账号列表
     * 
     * @param enterpriseInfo 企业账号
     * @return 企业账号
     */
    @Override
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.selectEnterpriseInfoList(enterpriseInfo);
    }

    /**
     * 新增企业账号
     * 
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    @Override
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.insertEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 修改企业账号
     * 
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    @Override
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo)
    {
        return enterpriseInfoMapper.updateEnterpriseInfo(enterpriseInfo);
    }

    /**
     * 批量删除企业账号
     * 
     * @param ids 需要删除的企业账号ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoByIds(Long[] ids)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoByIds(ids);
    }

    /**
     * 删除企业账号信息
     * 
     * @param id 企业账号ID
     * @return 结果
     */
    @Override
    public int deleteEnterpriseInfoById(Long id)
    {
        return enterpriseInfoMapper.deleteEnterpriseInfoById(id);
    }
}
