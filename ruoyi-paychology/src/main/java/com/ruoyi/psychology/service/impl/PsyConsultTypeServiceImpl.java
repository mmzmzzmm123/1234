package com.ruoyi.psychology.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.vo.TreeVO;
import com.ruoyi.psychology.domain.PsyConsultType;
import com.ruoyi.psychology.mapper.PsyConsultTypeMapper;
import com.ruoyi.psychology.service.IPsyConsultTypeService;
import com.ruoyi.psychology.vo.PsyConsultTypeVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 咨询类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
@Service
public class PsyConsultTypeServiceImpl implements IPsyConsultTypeService 
{
    @Autowired
    private PsyConsultTypeMapper psyConsultTypeMapper;

    /**
     * 查询咨询类型
     * 
     * @param id 咨询类型主键
     * @return 咨询类型
     */
    @Override
    public PsyConsultType selectPsyConsultTypeById(Long id)
    {
        return psyConsultTypeMapper.selectPsyConsultTypeById(id);
    }

    /**
     * 查询咨询类型列表
     * 
     * @param psyConsultType 咨询类型
     * @return 咨询类型
     */
    @Override
    public List<PsyConsultType> selectPsyConsultTypeList(PsyConsultType psyConsultType)
    {
        psyConsultType.setDelFlag("0");
        return psyConsultTypeMapper.selectPsyConsultTypeList(psyConsultType);
    }

    @Override
    public List<TreeVO> getTrees() {
        List<TreeVO> tree = new ArrayList<>();
        LambdaQueryWrapper<PsyConsultType> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultType::getDelFlag, "0");
        wp.eq(PsyConsultType::getStatus, "0");
        List<PsyConsultType> psyConsultTypes = psyConsultTypeMapper.selectList(wp);
        if (CollectionUtils.isEmpty(psyConsultTypes)) {
            return tree;
        }

        psyConsultTypes.stream().filter(i -> i.getPid() == 0).forEach(a -> {
            TreeVO node = new TreeVO();
            node.setKey(a.getId());
            node.setLabel(a.getName());
            node.setValue(a.getName());
            node.setChildren(new ArrayList<>());
            tree.add(node);
        });

        tree.forEach(a -> {
            psyConsultTypes.stream().filter(b -> b.getPid().equals(a.getKey())).forEach(it -> {
                TreeVO vo1 = new TreeVO();
                vo1.setKey(it.getId());
                vo1.setLabel(it.getName());
                vo1.setValue(it.getName());
                a.getChildren().add(vo1);
            });
        });

        return tree;
    }

    private int checkName(String name) {
        LambdaQueryWrapper<PsyConsultType> wp = new LambdaQueryWrapper<>();
        wp.eq(PsyConsultType::getName, name);
        return psyConsultTypeMapper.selectCount(wp);
    }

    private String getPathByPId(Long pid) {
        PsyConsultType type = psyConsultTypeMapper.selectById(pid);
        if(null != type){
            return type.getPath() + pid + "/";
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(PsyConsultTypeVO req) {
        if (checkName(req.getName()) > 0) {
            throw new UtilException("类型已存在");
        }

        req.setPath(getPathByPId(req.getPid()));
        req.setDelFlag("0");
        return psyConsultTypeMapper.insert(BeanUtil.toBean(req, PsyConsultType.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(PsyConsultTypeVO req) {
        return psyConsultTypeMapper.updateById(BeanUtil.toBean(req, PsyConsultType.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAll(Long[] ids) {
        return psyConsultTypeMapper.tombstonedByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Long id) {
        return psyConsultTypeMapper.deleteById(id);
    }
}
