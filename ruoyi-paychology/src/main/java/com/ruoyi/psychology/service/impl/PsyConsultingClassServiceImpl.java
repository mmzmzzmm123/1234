package com.ruoyi.psychology.service.impl;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.psychology.domain.PsyConsultingBannerConfig;
import com.ruoyi.psychology.vo.PsyConsultingBannerConfigVO;
import com.ruoyi.psychology.vo.PsyConsultingClassVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.psychology.mapper.PsyConsultingClassMapper;
import com.ruoyi.psychology.domain.PsyConsultingClass;
import com.ruoyi.psychology.service.IPsyConsultingClassService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 咨询类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultingClassServiceImpl implements IPsyConsultingClassService 
{
    @Autowired
    private PsyConsultingClassMapper psyConsultingClassMapper;

    /**
     * 查询咨询类型
     * 
     * @param id 咨询类型主键
     * @return 咨询类型
     */
    @Override
    public PsyConsultingClassVO getOne(Long id)
    {
        return BeanUtil.toBean(psyConsultingClassMapper.selectById(id), PsyConsultingClassVO.class);
    }

    /**
     * 查询咨询类型列表
     */
    @Override
    public List<PsyConsultingClass> getList(PsyConsultingClassVO req)
    {
        req.setDelFlag("0");
        return psyConsultingClassMapper.getList(req);
    }

    /**
     * 新增咨询类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultingClassVO req)
    {
        return psyConsultingClassMapper.insert(BeanUtil.toBean(req, PsyConsultingClass.class));
    }

    /**
     * 修改咨询类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultingClassVO req)
    {
        return psyConsultingClassMapper.updateById(BeanUtil.toBean(req, PsyConsultingClass.class));
    }

    /**
     * 批量删除咨询类型
     * 
     * @param ids 需要删除的咨询类型主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(List<Long> ids) {
        return psyConsultingClassMapper.deleteBatchIds(ids);
    }

    /**
     * 删除咨询类型信息
     * 
     * @param id 咨询类型主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultingClassMapper.deleteById(id);
    }
}
