package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChild;

/**
 * 幼儿信息Service接口
 *
 * @author tsbz
 * @date 2020-07-20
 */
public interface IByChildService {
    /**
     * 查询幼儿信息
     *
     * @param id 幼儿信息ID
     * @return 幼儿信息
     */
    public ByChild selectByChildById(Long id);

    /**
     * 查询幼儿信息列表
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息集合
     */
    public List<ByChild> selectByChildList(ByChild byChild);

    /**
     * 查询幼儿信息列表-用于考勤
     *
     * @param byChild 幼儿信息
     * @return 幼儿信息集合
     */
    public List<ByChild> selectByCheckList(ByChild byChild);

    /**
     * 新增幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    public int insertByChild(ByChild byChild);

    /**
     * 修改幼儿信息
     *
     * @param byChild 幼儿信息
     * @return 结果
     */
    public int updateByChild(ByChild byChild);

    /**
     * 批量删除幼儿信息
     *
     * @param ids 需要删除的幼儿信息ID
     * @return 结果
     */
    public int deleteByChildByIds(Long[] ids);

    /**
     * 删除幼儿信息信息
     *
     * @param id 幼儿信息ID
     * @return 结果
     */
    public int deleteByChildById(Long id);

    /**
     * 查询幼儿信息列表
     *
     * @param byChild 幼儿信息
     * @return 班级信息集合
     */
    public List<ByChild> selectststicstClassList(ByChild byChild);

    /**
     * 导入用户数据
     *
     * @param userChild 用户数据列表
     * @param operName  操作用户
     * @param deptId 部门id
     * @param  bjbh 班级编号
     * @return 结果
     */
    public String importChild(List<ByChild> userChild, String operName, Long deptId, String bjbh);
}
