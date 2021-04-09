package com.edgelight.ed.mapper;

import java.util.List;
import com.edgelight.ed.domain.EnterpriseInfo;

/**
 * 企业账号Mapper接口
 * 
 * @author limingcai
 * @date 2021-04-09
 */
public interface EnterpriseInfoMapper 
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
     * 删除企业账号
     * 
     * @param id 企业账号ID
     * @return 结果
     */
    public int deleteEnterpriseInfoById(Long id);

    /**
     * 批量删除企业账号
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteEnterpriseInfoByIds(Long[] ids);
}
