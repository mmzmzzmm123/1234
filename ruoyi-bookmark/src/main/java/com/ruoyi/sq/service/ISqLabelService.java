package com.ruoyi.sq.service;

import com.ruoyi.sq.domain.SqLabel;

import java.util.List;

/**
 * 书签_标签Service接口
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public interface ISqLabelService 
{
    /**
     * 查询书签_标签
     * 
     * @param id 书签_标签ID
     * @return 书签_标签
     */
    public SqLabel selectSqLabelById(Long id);

    /**
     * 查询书签_标签列表
     * 
     * @param sqLabel 书签_标签
     * @return 书签_标签集合
     */
    public List<SqLabel> selectSqLabelList(SqLabel sqLabel);

    /**
     * 新增书签_标签
     * 
     * @param sqLabel 书签_标签
     * @return 结果
     */
    public int insertSqLabel(SqLabel sqLabel);

    /**
     * 修改书签_标签
     * 
     * @param sqLabel 书签_标签
     * @return 结果
     */
    public int updateSqLabel(SqLabel sqLabel);

    /**
     * 批量删除书签_标签
     * 
     * @param ids 需要删除的书签_标签ID
     * @return 结果
     */
    public int deleteSqLabelByIds(Long[] ids);

    /**
     * 删除书签_标签信息
     * 
     * @param id 书签_标签ID
     * @return 结果
     */
    public int deleteSqLabelById(Long id);
}
