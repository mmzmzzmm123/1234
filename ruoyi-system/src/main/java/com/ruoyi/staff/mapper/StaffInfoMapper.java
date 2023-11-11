package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffInfo;

/**
 * 员工信息Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface StaffInfoMapper {

    /**
     * 查询员工信息
     *
     * @param userId 员工信息主键
     * @return 员工信息
     */
    public StaffInfo selectStaffInfoByUserId(Long userId);

    /**
     * 查询员工信息列表
     *
     * @param staffInfo 员工信息
     * @return 员工信息集合
     */
    public List<StaffInfo> selectStaffInfoList(StaffInfo staffInfo);

    /**
     * 获取男生和女生排序最高的前三位(男三和女三)
     *
     * @return 结果
     * */
    public List<StaffInfo> getSortNumTopThreeOfBoyAndGirl();

    /**
     * 自定义查询员工信息列表
     *
     * @param staffInfo 员工信息
     * @return 员工信息集合
     * */
    public List<StaffInfo> customSelect(StaffInfo staffInfo);

    /**
     * 新增员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    public int insertStaffInfo(StaffInfo staffInfo);

    /**
     * 修改员工信息
     *
     * @param staffInfo 员工信息
     * @return 结果
     */
    public int updateStaffInfo(StaffInfo staffInfo);

    /**
     * 删除员工信息
     *
     * @param userId 员工信息主键
     * @return 结果
     */
    public int deleteStaffInfoByUserId(Long userId);

    /**
     * 批量删除员工信息
     *
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffInfoByUserIds(Long[] userIds);
}
