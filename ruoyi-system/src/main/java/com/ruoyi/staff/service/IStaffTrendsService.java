package com.ruoyi.staff.service;

import java.util.List;
import com.ruoyi.staff.domain.StaffTrends;

/**
 * 员工动态Service接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface IStaffTrendsService {

    /**
     * 查询员工动态
     *
     * @param id 员工动态主键
     * @return 员工动态
     */
    public StaffTrends selectStaffTrendsById(Long id);

    /**
     * 查询员工动态列表
     *
     * @param staffTrends 员工动态
     * @return 员工动态集合
     */
    public List<StaffTrends> selectStaffTrendsList(StaffTrends staffTrends);

    /**
     * 新增员工动态
     *
     * @param staffTrends 员工动态
     * @return 结果
     */
    public int insertStaffTrends(StaffTrends staffTrends);

    /**
     * 修改员工动态
     *
     * @param staffTrends 员工动态
     * @return 结果
     */
    public int updateStaffTrends(StaffTrends staffTrends);

    /**
     * 批量删除员工动态
     *
     * @param ids 需要删除的员工动态主键集合
     * @return 结果
     */
    public int deleteStaffTrendsByIds(Long[] ids);

    /**
     * 删除员工动态信息
     *
     * @param id 员工动态主键
     * @return 结果
     */
    public int deleteStaffTrendsById(Long id);
}
