package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffTrendsMapper;
import com.ruoyi.staff.domain.StaffTrends;
import com.ruoyi.staff.service.IStaffTrendsService;

/**
 * 员工动态Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffTrendsServiceImpl implements IStaffTrendsService {

    private final StaffTrendsMapper staffTrendsMapper;

    /**
     * 查询员工动态
     *
     * @param id 员工动态主键
     * @return 员工动态
     */
    @Override
    public StaffTrends selectStaffTrendsById(Long id) {
        return staffTrendsMapper.selectStaffTrendsById(id);
    }

    /**
     * 查询员工动态列表
     *
     * @param staffTrends 员工动态
     * @return 员工动态
     */
    @Override
    public List<StaffTrends> selectStaffTrendsList(StaffTrends staffTrends) {
        return staffTrendsMapper.selectStaffTrendsList(staffTrends);
    }

    /**
     * 新增员工动态
     *
     * @param staffTrends 员工动态
     * @return 结果
     */
    @Override
    public int insertStaffTrends(StaffTrends staffTrends) {
        Date now = DateUtils.getNowDate();
        staffTrends.setCreateTime(now).setUpdateTime(now);
        return staffTrendsMapper.insertStaffTrends(staffTrends);
    }

    /**
     * 修改员工动态
     *
     * @param staffTrends 员工动态
     * @return 结果
     */
    @Override
    public int updateStaffTrends(StaffTrends staffTrends) {
        staffTrends.setUpdateTime(DateUtils.getNowDate());
        return staffTrendsMapper.updateStaffTrends(staffTrends);
    }

    /**
     * 批量删除员工动态
     *
     * @param ids 需要删除的员工动态主键
     * @return 结果
     */
    @Override
    public int deleteStaffTrendsByIds(Long[] ids) {
        return staffTrendsMapper.deleteStaffTrendsByIds(ids);
    }

    /**
     * 删除员工动态信息
     *
     * @param id 员工动态主键
     * @return 结果
     */
    @Override
    public int deleteStaffTrendsById(Long id) {
        return staffTrendsMapper.deleteStaffTrendsById(id);
    }
}
