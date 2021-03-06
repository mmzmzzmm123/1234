package com.gox.basic.mapper;

import com.gox.basic.domain.form.FormDesignerData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *   Mapper接口
 *
 * @author gox
 * @date 2021-02-02
 */
@Repository
public interface FormDesignerDataMapper {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public FormDesignerData selectFormDesignerDataById(Long id);

    public List<FormDesignerData> selectAll();

    /**
     * 查询  列表
     *
     * @param formDesignerData
     * @return   集合
     */
    public List<FormDesignerData> selectFormDesignerDataList(FormDesignerData formDesignerData);

    /**
     * 新增
     *
     * @param formDesignerData
     * @return 结果
     */
    public int insertFormDesignerData(FormDesignerData formDesignerData);

    /**
     * 修改
     *
     * @param formDesignerData
     * @return 结果
     */
    public int updateFormDesignerData(FormDesignerData formDesignerData);

    /**
     * 删除
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteFormDesignerDataById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormDesignerDataByIds(Long[] ids);
}
