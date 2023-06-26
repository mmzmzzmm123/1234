package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.psychology.domain.PsyConsultBannerConfig;
import com.ruoyi.psychology.mapper.PsyConsultBannerConfigMapper;
import com.ruoyi.psychology.service.IPsyConsultBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultBannerConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 咨询banner配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultBannerConfigServiceImpl implements IPsyConsultBannerConfigService
{
    @Resource
    private PsyConsultBannerConfigMapper psyConsultBannerConfigMapper;

    /**
     * 查询咨询banner配置
     * 
     * @param id 咨询banner配置主键
     * @return 咨询banner配置
     */
    @Override
    public PsyConsultBannerConfigVO getOne(Long id)
    {
        return BeanUtil.toBean(psyConsultBannerConfigMapper.selectById(id), PsyConsultBannerConfigVO.class);
    }

    /**
     * 查询咨询banner配置列表
     *
     */
    @Override
    public List<PsyConsultBannerConfig> getList(PsyConsultBannerConfigVO req)
    {
        req.setDelFlag("0");
        return psyConsultBannerConfigMapper.getList(req);
    }

    /**
     * 新增咨询banner配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultBannerConfigVO req)
    {
        PsyConsultBannerConfig bean = BeanUtil.toBean(req, PsyConsultBannerConfig.class);
        bean.setType(1);
        return psyConsultBannerConfigMapper.insert(bean);
    }

    /**
     * 修改咨询banner配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultBannerConfigVO req)
    {
        return psyConsultBannerConfigMapper.updateById(BeanUtil.toBean(req, PsyConsultBannerConfig.class));
    }

    /**
     * 批量删除咨询banner配置
     * 
     * @param ids 需要删除的咨询banner配置主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultBannerConfigMapper.tombstonedByIds(ids);
    }

}
