package com.ruoyi.psychology.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.dto.PartnerDTO;
import com.ruoyi.psychology.request.PartnerReq;
import com.ruoyi.psychology.request.PsyAdminPartnerReq;

/**
 * 咨询师入驻申请Service接口
 *
 * @author ruoyi
 * @date 2023-11-07
 */
public interface IPsyConsultPartnerService {

    // 点击填写入驻资料，查询/创建草稿
    // 查询本人申请表 + 字表
    // 编辑主表资料
    // 编辑字表资料
    // 确认并提交资料

    void draft(Integer userId);

    int addItem(PsyConsultPartnerItem item);

    int editItem(PsyConsultPartnerItem item);

    int delItem(Long id);

    int save(PsyConsultPartner entity);

    AjaxResult createUser(Long id);

    PartnerDTO getInfoByUserId(Integer userId);

    PartnerDTO getInfoById(Long userId);

    List<PsyConsultPartner> getList(PsyAdminPartnerReq psyConsultPartner);

}
