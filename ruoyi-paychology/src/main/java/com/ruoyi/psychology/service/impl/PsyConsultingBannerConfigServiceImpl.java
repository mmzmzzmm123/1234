package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.mapper.PsyConsultingBannerConfigMapper;
import com.ruoyi.psychology.service.IPsyConsultingBannerConfigService;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 咨询banner配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultingBannerConfigServiceImpl implements IPsyConsultingBannerConfigService 
{
    @Autowired
    private PsyConsultingBannerConfigMapper psyConsultingBannerConfigMapper;

    /**
     * 查询咨询banner配置
     * 
     * @param id 咨询banner配置主键
     * @return 咨询banner配置
     */
    @Override
    public PsyConsultingBannerConfigVO getOne(Long id)
    {
        return BeanUtil.toBean(psyConsultingBannerConfigMapper.selectById(id), PsyConsultingBannerConfigVO.class);
    }

    /**
     * 查询咨询banner配置列表
     *
     */
    @Override
    public List<PsyConsultingBannerConfig> getList(PsyConsultingBannerConfigVO req)
    {
        req.setDelFlag("0");
        return psyConsultingBannerConfigMapper.getList(req);
    }

    /**
     * 新增咨询banner配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultingBannerConfigVO req)
    {
        PsyConsultingBannerConfig bean = BeanUtil.toBean(req, PsyConsultingBannerConfig.class);
        bean.setType(1);
        return psyConsultingBannerConfigMapper.insert(bean);
    }

    /**
     * 修改咨询banner配置
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultingBannerConfigVO req)
    {
        return psyConsultingBannerConfigMapper.updateById(BeanUtil.toBean(req, PsyConsultingBannerConfig.class));
    }

    /**
     * 批量删除咨询banner配置
     * 
     * @param ids 需要删除的咨询banner配置主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(List<Long> ids) {
        return psyConsultingBannerConfigMapper.deleteBatchIds(ids);
    }

    /**
     * 删除咨询banner配置信息
     * 
     * @param id 咨询banner配置主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultingBannerConfigMapper.deleteById(id);
    }
}
