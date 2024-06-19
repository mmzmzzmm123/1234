package com.renxin.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renxin.psychology.domain.PsyConsultPartner;
import com.renxin.psychology.dto.PartnerDTO;
import com.renxin.psychology.request.PsyAdminPartnerReq;

/**
 * 咨询师入驻申请Mapper接口
 * 
 * @author renxin
 * @date 2023-11-07
 */
public interface PsyConsultPartnerMapper extends BaseMapper<PsyConsultPartner>
{

    public PartnerDTO getInfo(PsyAdminPartnerReq req);

    public List<PsyConsultPartner> selectPsyConsultPartnerList(PsyAdminPartnerReq req);

    /**
     * 删除咨询师入驻申请
     * 
     * @param id 咨询师入驻申请主键
     * @return 结果
     */
    public int deletePsyConsultPartnerById(Long id);

    /**
     * 批量删除咨询师入驻申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePsyConsultPartnerByIds(Long[] ids);
}
