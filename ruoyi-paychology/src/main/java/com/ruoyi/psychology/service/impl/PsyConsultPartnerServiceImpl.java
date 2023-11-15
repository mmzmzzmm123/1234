package com.ruoyi.psychology.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.common.utils.NewDateUtil;
import com.ruoyi.common.vo.DateLimitUtilVO;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultPartner;
import com.ruoyi.psychology.domain.PsyConsultPartnerItem;
import com.ruoyi.psychology.domain.PsyUser;
import com.ruoyi.psychology.dto.PartnerDTO;
import com.ruoyi.psychology.mapper.PsyConsultPartnerMapper;
import com.ruoyi.psychology.request.PsyAdminPartnerReq;
import com.ruoyi.psychology.service.IPsyConsultPartnerItemService;
import com.ruoyi.psychology.service.IPsyConsultPartnerService;
import com.ruoyi.psychology.service.IPsyConsultService;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.psychology.vo.PsyConsultVO;
import com.ruoyi.system.service.ISysConfigService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private IPsyUserService psyUserService;

    @Resource
    private ISysConfigService configService;

    @Resource
    private IPsyConsultService consultService;

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
        PartnerDTO one = getInfoByUserId(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult createUser(Long id) {
        PsyConsultPartner partner = psyConsultPartnerMapper.selectById(id);
        if (!ConsultConstant.PARTNER_STATUS_2.equals(partner.getStatus()) && !ConsultConstant.PARTNER_STATUS_3.equals(partner.getStatus())) {
            return AjaxResult.error("单据状态异常");
        }

        String pwd = configService.selectConfigByKey("sys.user.initPassword");
        String fm = "登录地址：http://admin.ssgpsy.com/ \n登录账号：{} \n初始密码：{}";

        if (ConsultConstant.PARTNER_STATUS_2.equals(partner.getStatus()) && partner.getUserName()!= null) {
            AjaxResult ajaxResult = AjaxResult.success("账号已开通");

            ajaxResult.put("fm", StrUtil.format(fm, partner.getUserName(), pwd));
            return ajaxResult;
        }

        Long cId = IDhelper.getNextId();
        String userName = consultService.getAvailableUserName(partner.getName());

        partner.setCId(cId);
        partner.setUserName(userName);
        partner.setStatus(ConsultConstant.PARTNER_STATUS_2);
        save(partner);

        PsyConsultVO vo = new PsyConsultVO();
        PsyUser user = psyUserService.selectPsyUserById(partner.getUserId());
        String openid = user.getWxOpenid();

        vo.setId(cId);
        vo.setUserName(userName);
        vo.setStatus("1");
        vo.setSex(partner.getSex() == 1 ? "男" : "女");
        vo.setNickName(partner.getName());
        vo.setEmail(partner.getEmail());
        vo.setPhonenumber(partner.getPhone());
        vo.setProvince(partner.getProvince());
        vo.setCity(partner.getCity());
        vo.setLang(partner.getLang());
        vo.setOpenId(openid);
        vo.setGenre(partner.getGenre() + partner.getExtGenre());
        vo.setWorkHours(partner.getWorkHours());
        AjaxResult result = consultService.add(vo);
        result.put("fm", StrUtil.format(fm, userName, pwd));

        return result;
    }

    @Override
    public PartnerDTO getInfoByUserId(Integer userId)
    {
        PsyAdminPartnerReq req = new PsyAdminPartnerReq();
        req.setUserId(userId);
        return getPartnerDTO(req);
    }

    @Override
    public PartnerDTO getInfoById(Long id)
    {
        PsyAdminPartnerReq req = new PsyAdminPartnerReq();
        req.setId(id);
        return getPartnerDTO(req);
    }


    private PartnerDTO getPartnerDTO(PsyAdminPartnerReq req) {
        PartnerDTO dto = psyConsultPartnerMapper.getInfo(req);

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

        dto.setTypeName(getTypeName(dto.getType()));
        dto.setStatusName(getStatusName(dto.getStatus()));

        return dto;
    }

    /**
     * 查询咨询师入驻申请列表
     * 
     * @param req 咨询师入驻申请
     * @return 咨询师入驻申请
     */
    @Override
    public List<PsyConsultPartner> getList(PsyAdminPartnerReq req)
    {

        LambdaQueryWrapper<PsyConsultPartner> wp = new LambdaQueryWrapper<>();

        if (StringUtils.isNotEmpty(req.getName())) {
            wp.eq(PsyConsultPartner::getName, req.getName());
        }

        if (StringUtils.isNotEmpty(req.getStatus())) {
            wp.eq(PsyConsultPartner::getStatus, req.getStatus());
        }

        if (StrUtil.isNotBlank(req.getDateLimit())) {
            DateLimitUtilVO dateLimit = NewDateUtil.getDateLimit(req.getDateLimit());
            wp.between(PsyConsultPartner::getCreateTime, dateLimit.getStartTime(), dateLimit.getEndTime());
        }

        List<PsyConsultPartner> list = psyConsultPartnerMapper.selectList(wp);


        list.forEach(item -> {
            item.setTypeName(getTypeName(item.getType()));
            item.setStatusName(getStatusName(item.getStatus()));
        });

        return list;
    }

    private String getTypeName(Integer type) {
        if (ConsultConstant.PARTNER_TYPE_HZ.equals(type)) {
            return ConsultConstant.PARTNER_TYPE_HZ_STR;
        } else if (ConsultConstant.PARTNER_TYPE_TT.equals(type)) {
            return ConsultConstant.PARTNER_TYPE_TT_STR;
        } else if (ConsultConstant.PARTNER_TYPE_GT.equals(type)) {
            return ConsultConstant.PARTNER_TYPE_GT_STR;
        }
        return "";
    }

    private String getStatusName(String status) {
        switch (status) {
            case ConsultConstant.PARTNER_STATUS_0:
                return ConsultConstant.PARTNER_STATUS_0_STR;
            case ConsultConstant.PARTNER_STATUS_1:
                return ConsultConstant.PARTNER_STATUS_1_STR;
            case ConsultConstant.PARTNER_STATUS_2:
                return ConsultConstant.PARTNER_STATUS_2_STR;
            case ConsultConstant.PARTNER_STATUS_3:
                return ConsultConstant.PARTNER_STATUS_3_STR;
            case ConsultConstant.PARTNER_STATUS_4:
                return ConsultConstant.PARTNER_STATUS_4_STR;
        }
        return "";
    }

}
