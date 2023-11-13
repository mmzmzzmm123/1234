package com.ruoyi.psychology.service;

import java.util.List;

import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.dto.PartnerDTO;
import com.ruoyi.psychology.request.PartnerReq;

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

    PartnerDTO getInfo(Integer userId);

    List<PsyConsultPartner> getList(PsyConsultPartner psyConsultPartner);

}
