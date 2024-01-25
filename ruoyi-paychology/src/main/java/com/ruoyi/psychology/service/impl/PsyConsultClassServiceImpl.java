package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.psychology.domain.PsyConsultClass;
import com.ruoyi.psychology.mapper.PsyConsultClassMapper;
import com.ruoyi.psychology.service.IPsyConsultClassService;
import com.ruoyi.psychology.vo.PsyConsultClassVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

/**
 * 咨询类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
@Service
public class PsyConsultClassServiceImpl implements IPsyConsultClassService
{
    @Resource
    private PsyConsultClassMapper psyConsultClassMapper;

    /**
     * 查询咨询类型
     * 
     * @param id 咨询类型主键
     * @return 咨询类型
     */
    @Override
    public PsyConsultClassVO getOne(Long id)
    {
        return BeanUtil.toBean(psyConsultClassMapper.selectById(id), PsyConsultClassVO.class);
    }

    /**
     * 查询咨询类型列表
     */
    @Override
    public List<PsyConsultClass> getList(PsyConsultClassVO req)
    {
        req.setDelFlag("0");
        return psyConsultClassMapper.getList(req);
    }

    /**
     * 新增咨询类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultClassVO req)
    {
        converToStr(req);
        return psyConsultClassMapper.insert(BeanUtil.toBean(req, PsyConsultClass.class));
    }

    /**
     * 修改咨询类型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultClassVO req)
    {
        converToStr(req);
        return psyConsultClassMapper.updateById(BeanUtil.toBean(req, PsyConsultClass.class));
    }

    private void converToStr(PsyConsultClassVO req) {
        HashSet<String> way = new HashSet<>();
        if (StringUtils.isNotEmpty(req.getWay())) {
            List<String> jsonArray = JSON.parseArray(req.getWay(), String.class);
            jsonArray.forEach(a -> {
                List<String> json = JSON.parseArray(a, String.class);
                way.add(json.get(1));
            });
        }
        req.setWayStr(String.join(",", way));
    }

    /**
     * 批量删除咨询类型
     * 
     * @param ids 需要删除的咨询类型主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultClassMapper.tombstonedByIds(ids);
    }

}
