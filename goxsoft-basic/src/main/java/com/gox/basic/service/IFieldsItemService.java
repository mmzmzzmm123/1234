package com.gox.basic.service;

import com.gox.basic.domain.form.FieldsItem;
import com.gox.basic.domain.vo.TableFieldVo;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IFieldsItemService {
    /**
     * 查询
     * @param id   ID
     * @return   
     */
    public FieldsItem selectFieldsItemById(Long id);

    public List<TableFieldVo> selectTableFieldByNodeIdAndDeptId(Long nodeId, Long deptId);
    public List<FieldsItem> selectFieldsItemByFormId(Long formId);
    /**
     * 查询  列表
     *
     * @param fieldsItem   
     * @return   集合
     */
    public List<FieldsItem> selectFieldsItemList(FieldsItem fieldsItem);

    int updateTableFieldsBatch(Iterable<TableFieldVo> fieldVos);

    /**
     * 新增  
     *
     * @param fieldsItem   
     * @return 结果
     */
    public int insertFieldsItem(FieldsItem fieldsItem);

    /**
     * 批量插入
     *
     * @param fieldsItems
     */
    public int insertFieldsItems(Iterable<FieldsItem> fieldsItems, Long formId);

    /**
     * 修改  
     *
     * @param fieldsItem   
     * @return 结果
     */
    public int updateFieldsItem(FieldsItem fieldsItem);

    /**
     * 批量删除  
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteFieldsItemByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteFieldsItemById(Long id);

    int deleteFieldsByFormId(Long id);

    List<TableFieldVo> selectTableTitle(Long nodeId, Long deptId);
}
