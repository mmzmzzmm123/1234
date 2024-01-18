package com.onethinker.bk.service;

import com.onethinker.bk.domain.Label;

import java.util.List;

/**
 * 标签Service接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface ILabelService {
    /**
     * 查询标签
     *
     * @param id 标签主键
     * @return 标签
     */
    Label selectLabelById(Long id);

    /**
     * 查询标签列表
     *
     * @param label 标签
     * @return 标签集合
     */
    List<Label> selectLabelList(Label label);

    /**
     * 新增标签
     *
     * @param label 标签
     * @return 结果
     */
    int insertLabel(Label label);

    /**
     * 修改标签
     *
     * @param label 标签
     * @return 结果
     */
    int updateLabel(Label label);

    /**
     * 删除标签信息
     *
     * @param id 标签主键
     * @return 结果
     */
    int deleteLabelById(Long id);

    /**
     * 获取所有标签信息
     *
     * @return
     */
    List<Label> getLabelInfo();
}
