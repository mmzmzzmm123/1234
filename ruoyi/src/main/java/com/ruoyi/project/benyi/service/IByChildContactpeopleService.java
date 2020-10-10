package com.ruoyi.project.benyi.service;

import java.util.List;

import com.ruoyi.project.benyi.domain.ByChildContactpeople;

/**
 * 幼儿紧急情况联系人Service接口
 *
 * @author tsbz
 * @date 2020-08-13
 */
public interface IByChildContactpeopleService {
    /**
     * 查询幼儿紧急情况联系人
     *
     * @param id 幼儿紧急情况联系人ID
     * @return 幼儿紧急情况联系人
     */
    public ByChildContactpeople selectByChildContactpeopleById(Long id);

    /**
     * 查询幼儿紧急情况联系人列表
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 幼儿紧急情况联系人集合
     */
    public List<ByChildContactpeople> selectByChildContactpeopleList(ByChildContactpeople byChildContactpeople);

    /**
     * 新增幼儿紧急情况联系人
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 结果
     */
    public int insertByChildContactpeople(ByChildContactpeople byChildContactpeople);

    /**
     * 修改幼儿紧急情况联系人
     *
     * @param byChildContactpeople 幼儿紧急情况联系人
     * @return 结果
     */
    public int updateByChildContactpeople(ByChildContactpeople byChildContactpeople);

    /**
     * 批量删除幼儿紧急情况联系人
     *
     * @param ids 需要删除的幼儿紧急情况联系人ID
     * @return 结果
     */
    public int deleteByChildContactpeopleByIds(Long[] ids);

    /**
     * 删除幼儿紧急情况联系人信息
     *
     * @param id 幼儿紧急情况联系人ID
     * @return 结果
     */
    public int deleteByChildContactpeopleById(Long id);

    /**
     * 批量删除幼儿紧急情况联系人
     *
     * @param childIds 需要删除的幼儿紧急情况联系人ID
     * @return 结果
     */
    public int deleteByChildContactpeopleByChildIds(Long[] childIds);
}
