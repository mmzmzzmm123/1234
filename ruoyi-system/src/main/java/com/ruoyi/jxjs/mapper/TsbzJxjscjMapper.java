package com.ruoyi.jxjs.mapper;

import java.util.List;

import com.ruoyi.jxjs.domain.TsbzJxjscj;

/**
 * 见习教师成绩Mapper接口
 *
 * @author ruoyi
 * @date 2020-11-09
 */
public interface TsbzJxjscjMapper {
    /**
     * 查询见习教师成绩
     *
     * @param id 见习教师成绩ID
     * @return 见习教师成绩
     */
    public TsbzJxjscj selectTsbzJxjscjById(Long id);

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
     * 删除见习教师成绩
     *
     * @param id 见习教师成绩ID
     * @return 结果
     */
    public int deleteTsbzJxjscjById(Long id);

    /**
     * 批量删除见习教师成绩
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTsbzJxjscjByIds(Long[] ids);

    /**
     * 通过用户名查询用户
     *
     * @param jsid 教师名
     * @return 用户对象信息
     */
    public TsbzJxjscj selectTsbzJxjscjByJsid(Long jsid);
}
