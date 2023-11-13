package com.ruoyi.psychology.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.dto.PartnerDTO;
import com.ruoyi.psychology.request.PartnerReq;
import com.ruoyi.psychology.service.IPsyConsultPartnerItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyConsultPartnerMapper;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.service.IPsyConsultPartnerService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 咨询师入驻申请Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-11-07
 */
@Service
public class PsyConsultPartnerServiceImpl implements IPsyConsultPartnerService 
{
    @Resource
    private PsyConsultPartnerMapper psyConsultPartnerMapper;

    @Resource
    private IPsyConsultPartnerItemService partnerItemService;

    @Override
    public int addItem(PsyConsultPartnerItem item) {
        return partnerItemService.add(item);
    }

    @Override
    public int editItem(PsyConsultPartnerItem item) {
        return partnerItemService.edit(item);
    }

    @Override
    public int delItem(Long id) {
        return partnerItemService.delete(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void draft(Integer userId)
    {
        PartnerDTO one = getInfo(userId);
        if (one == null) {
            PsyConsultPartner partner = new PsyConsultPartner();
            partner.setId(IDhelper.getNextId());
            partner.setCreateBy("system");
            partner.setUpdateBy("system");
            partner.setCreateTime(new Date());
            partner.setUpdateTime(new Date());
            partner.setUserId(userId);
            psyConsultPartnerMapper.insert(partner);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(PsyConsultPartner entity) {
        return psyConsultPartnerMapper.updateById(entity);
    }

    @Override
    public PartnerDTO getInfo(Integer userId)
    {
        PartnerDTO dto = psyConsultPartnerMapper.getInfoByUserId(userId);
        if (StringUtils.isNotBlank(dto.getCardImg())) {
            dto.setCardImgs(Arrays.asList(dto.getCardImg().split(",")));
        }
        if (StringUtils.isNotBlank(dto.getExtraImg())) {
            dto.setExtraImgs(Arrays.asList(dto.getExtraImg().split(",")));
        }
        if (StringUtils.isNotBlank(dto.getLang())) {
            dto.setLangList(Arrays.asList(dto.getLang().split(",")));
        }
        if (StringUtils.isNotBlank(dto.getGenre())) {
            dto.setGenreList(Arrays.asList(dto.getGenre().split(",")));
        }

        if (dto.getId() != null) {
            dto.setItems(partnerItemService.getListById(dto.getId()));
        }

        return dto;
    }

    /**
     * 查询咨询师入驻申请列表
     * 
     * @param psyConsultPartner 咨询师入驻申请
     * @return 咨询师入驻申请
     */
    @Override
    public List<PsyConsultPartner> getList(PsyConsultPartner psyConsultPartner)
    {
        return psyConsultPartnerMapper.selectPsyConsultPartnerList(psyConsultPartner);
    }
}
