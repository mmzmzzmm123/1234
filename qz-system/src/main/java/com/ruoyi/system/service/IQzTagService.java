package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.QzTag;

/**
 * 标签Service接口
 * 
 * @author zlx
 * @date 2021-08-10
 */
public interface IQzTagService 
{
    /**
     * 查询标签
     * 
     * @param id 标签主键
     * @return 标签
     */
    public QzTag selectQzTagById(Long id);

    /**
     * 查询标签列表
     * 
     * @param qzTag 标签
     * @return 标签集合
     */
    public List<QzTag> selectQzTagList(QzTag qzTag);

    /**
     * 新增标签
     * 
     * @param qzTag 标签
     * @return 结果
     */
    public int insertQzTag(QzTag qzTag);

    /**
     * 修改标签
     * 
     * @param qzTag 标签
     * @return 结果
     */
    public int updateQzTag(QzTag qzTag);

    /**
     * 批量删除标签
     * 
     * @param ids 需要删除的标签主键集合
     * @return 结果
     */
    public int deleteQzTagByIds(Long[] ids);

    /**
     * 删除标签信息
     * 
     * @param id 标签主键
     * @return 结果
     */
    public int deleteQzTagById(Long id);
}
