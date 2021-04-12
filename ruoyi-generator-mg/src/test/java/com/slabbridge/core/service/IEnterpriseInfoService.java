package com.slabbridge.core.service;

import java.util.List;


import com.ruoyi.framework.service.IService;
import com.slabbridge.core.dao.EnterpriseInfoDAO;
import com.slabbridge.core.entity.EnterpriseInfo;

/**
 * 企业账号Service接口
 * 
 * @author limingcai
 * @date 2021-04-12
 */
public interface IEnterpriseInfoService extends IService<EnterpriseInfoDAO>
{
    /**
     * 查询企业账号
     * 
     * @param id 企业账号ID
     * @return 企业账号
     */
    public EnterpriseInfo selectEnterpriseInfoById(Long id);

    /**
     * 查询企业账号列表
     * 
     * @param enterpriseInfo 企业账号
     * @return 企业账号集合
     */
    public List<EnterpriseInfo> selectEnterpriseInfoList(EnterpriseInfo enterpriseInfo);

    /**
     * 新增企业账号
     * 
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    public int insertEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 修改企业账号
     * 
     * @param enterpriseInfo 企业账号
     * @return 结果
     */
    public int updateEnterpriseInfo(EnterpriseInfo enterpriseInfo);

    /**
     * 批量删除企业账号
     * 
     * @param ids 需要删除的企业账号ID
     * @return 结果
     */
    public int deleteEnterpriseInfoByIds(Long[] ids);

    /**
     * 删除企业账号信息
     * 
     * @param id 企业账号ID
     * @return 结果
     */
    public int deleteEnterpriseInfoById(Long id);
}
