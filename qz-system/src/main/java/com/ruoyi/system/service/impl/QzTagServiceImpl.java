package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.QzTagMapper;
import com.ruoyi.system.domain.QzTag;
import com.ruoyi.system.service.IQzTagService;

/**
 * 标签Service业务层处理
 * 
 * @author zlx
 * @date 2021-08-10
 */
@Service
public class QzTagServiceImpl implements IQzTagService 
{
    @Autowired
    private QzTagMapper qzTagMapper;

    /**
     * 查询标签
     * 
     * @param id 标签主键
     * @return 标签
     */
    @Override
    public QzTag selectQzTagById(Long id)
    {
        return qzTagMapper.selectQzTagById(id);
    }

    /**
     * 查询标签列表
     * 
     * @param qzTag 标签
     * @return 标签
     */
    @Override
    public List<QzTag> selectQzTagList(QzTag qzTag)
    {
        return qzTagMapper.selectQzTagList(qzTag);
    }

    /**
     * 新增标签
     * 
     * @param qzTag 标签
     * @return 结果
     */
    @Override
    public int insertQzTag(QzTag qzTag)
    {
        qzTag.setCreateTime(DateUtils.getNowDate());
        return qzTagMapper.insertQzTag(qzTag);
    }

    /**
     * 修改标签
     * 
     * @param qzTag 标签
     * @return 结果
     */
    @Override
    public int updateQzTag(QzTag qzTag)
    {
        qzTag.setUpdateTime(DateUtils.getNowDate());
        return qzTagMapper.updateQzTag(qzTag);
    }

    /**
     * 批量删除标签
     * 
     * @param ids 需要删除的标签主键
     * @return 结果
     */
    @Override
    public int deleteQzTagByIds(Long[] ids)
    {
        return qzTagMapper.deleteQzTagByIds(ids);
    }

    /**
     * 删除标签信息
     * 
     * @param id 标签主键
     * @return 结果
     */
    @Override
    public int deleteQzTagById(Long id)
    {
        return qzTagMapper.deleteQzTagById(id);
    }
}
