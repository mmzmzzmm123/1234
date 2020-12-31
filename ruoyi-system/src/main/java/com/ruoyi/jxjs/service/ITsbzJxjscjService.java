package com.ruoyi.jxjs.service;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxjscj;

/**
 * 见习教师成绩Service接口
 *
 * @author ruoyi
 * @date 2020-11-09
 */
public interface ITsbzJxjscjService {
    /**
     * 查询见习教师成绩
     *
     * @param id 见习教师成绩ID
     * @return 见习教师成绩
     */
    public TsbzJxjscj selectTsbzJxjscjById(Long id);

    /**
     * 通过用户名查询用户
     *
     * @param jsid 用户名
     * @return 用户对象信息
     */
    public TsbzJxjscj selectTsbzJxjscjByJsid(Long jsid);

    /**
     * 查询见习教师成绩列表
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 见习教师成绩集合
     */
    public List<TsbzJxjscj> selectTsbzJxjscjList(TsbzJxjscj tsbzJxjscj);

    /**
     * 新增见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    public int insertTsbzJxjscj(TsbzJxjscj tsbzJxjscj);

    /**
     * 修改见习教师成绩
     *
     * @param tsbzJxjscj 见习教师成绩
     * @return 结果
     */
    public int updateTsbzJxjscj(TsbzJxjscj tsbzJxjscj);

    /**
     * 批量删除见习教师成绩
     *
     * @param ids 需要删除的见习教师成绩ID
     * @return 结果
     */
    public int deleteTsbzJxjscjByIds(Long[] ids);

    /**
     * 删除见习教师成绩信息
     *
     * @param id 见习教师成绩ID
     * @return 结果
     */
    public int deleteTsbzJxjscjById(Long id);

    /**
     * 导入用户数据
     *
     * @param tsbzJxjscjList 教师成绩数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importTsbzJxjscj(List<TsbzJxjscj> tsbzJxjscjList, Boolean isUpdateSupport, String operName);
}
