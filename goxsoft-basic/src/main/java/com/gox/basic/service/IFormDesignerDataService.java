package com.gox.basic.service;

import com.gox.basic.domain.form.FormDesignerData;

import java.util.List;

/**
 *   Service接口
 *
 * @author gox
 * @date 2021-02-02
 */
public interface IFormDesignerDataService {
    /**
     * 查询
     *
     * @param id   ID
     * @return
     */
    public FormDesignerData selectFormDesignerDataById(Long id);

//    /**
//     * 查询  列表
//     *
//     * @param formDesignerData
//     * @return   集合
//     */
//    public List<FormDesignerData> selectFormDesignerDataList(FormDesignerData formDesignerData);

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
     * 批量删除
     *
     * @param ids 需要删除的  ID
     * @return 结果
     */
    public int deleteFormDesignerDataByIds(Long[] ids);

    /**
     * 删除  信息
     *
     * @param id   ID
     * @return 结果
     */
    public int deleteFormDesignerDataById(Long id);
}
