package com.ruoyi.psychology.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.psychology.domain.PsyConsultType;

/**
 * 咨询类型Mapper接口
 * 
 * @author ruoyi
 * @date 2023-07-14
 */
public interface PsyConsultTypeMapper extends BaseMapper<PsyConsultType>
{
    /**
     * 查询咨询类型
     * 
     * @param id 咨询类型主键
     * @return 咨询类型
     */
    public PsyConsultType selectPsyConsultTypeById(Long id);

    /**
     * 查询咨询类型列表
     * 
     * @param psyConsultType 咨询类型
     * @return 咨询类型集合
     */
    public List<PsyConsultType> selectPsyConsultTypeList(PsyConsultType psyConsultType);

    int tombstonedByIds(Long[] ids);

}
