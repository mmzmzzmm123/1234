package com.onethinker.bk.mapper;

import java.util.List;
import com.onethinker.bk.domain.Family;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 家庭信息Mapper接口
 *
 * @author yangyouqi
 * @date 2024-01-16
 */
public interface FamilyMapper extends BaseMapper<Family>{
    /**
     * 查询家庭信息
     *
     * @param id 家庭信息主键
     * @return 家庭信息
     */
    public Family selectFamilyById(Long id);

    /**
     * 查询家庭信息列表
     *
     * @param family 家庭信息
     * @return 家庭信息集合
     */
    public List<Family> selectFamilyList(Family family);

    /**
     * 新增家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    public int insertFamily(Family family);

    /**
     * 修改家庭信息
     *
     * @param family 家庭信息
     * @return 结果
     */
    public int updateFamily(Family family);

    /**
     * 删除家庭信息
     *
     * @param id 家庭信息主键
     * @return 结果
     */
    public int deleteFamilyById(Long id);

    /**
     * 批量删除家庭信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFamilyByIds(Long[] ids);
}
