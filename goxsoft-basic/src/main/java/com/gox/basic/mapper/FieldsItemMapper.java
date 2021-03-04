package com.gox.basic.mapper;

import com.gox.basic.domain.form.FieldsItem;
import com.gox.basic.domain.vo.TableFieldVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
@Repository
public interface FieldsItemMapper {
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public FieldsItem selectFieldsItemById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<FieldsItem> selectFieldsItemList(FieldsItem fieldsItem);

    public List<FieldsItem> selectFieldsItemListByFormId(Long formId);

    public List<TableFieldVo> selectTableFields(Long nodeId, Long deptId);

    /**
     * 新增【请填写功能名称】
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int insertFieldsItem(FieldsItem fieldsItem);

    /**
     * 修改【请填写功能名称】
     *
     * @param fieldsItem 【请填写功能名称】
     * @return 结果
     */
    public int updateFieldsItem(FieldsItem fieldsItem);

    public int insertFieldsItemBatch(Iterable<FieldsItem> fieldsItems);

    public int updateTableFieldsBatch(Iterable<TableFieldVo> fieldsItems);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteFieldsItemById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFieldsItemByIds(Long[] ids);

    public int deleteFieldsItemByFormId(Long id);

    List<TableFieldVo> selectTableTitle(Long nodeId, Long deptId);
}
