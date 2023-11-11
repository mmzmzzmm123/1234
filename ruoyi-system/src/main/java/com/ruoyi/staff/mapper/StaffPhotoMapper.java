package com.ruoyi.staff.mapper;

import java.util.List;
import com.ruoyi.staff.domain.StaffPhoto;

/**
 * 员工相册Mapper接口
 *
 * @author Lam
 * @date 2023-09-14
 */
public interface StaffPhotoMapper {

    /**
     * 查询员工相册
     *
     * @param id 员工相册主键
     * @return 员工相册
     */
    public StaffPhoto selectStaffPhotoById(Long id);

    /**
     * 查询员工相册列表
     *
     * @param staffPhoto 员工相册
     * @return 员工相册集合
     */
    public List<StaffPhoto> selectStaffPhotoList(StaffPhoto staffPhoto);

    /**
     * 新增员工相册
     *
     * @param staffPhoto 员工相册
     * @return 结果
     */
    public int insertStaffPhoto(StaffPhoto staffPhoto);

    /**
     * 批量新增店员相册信息
     *
     * @param staffPhotos 集合
     * @return 结果
     * */
    public int insertOfList(List<StaffPhoto> staffPhotos);

    /**
     * 修改员工相册
     *
     * @param staffPhoto 员工相册
     * @return 结果
     */
    public int updateStaffPhoto(StaffPhoto staffPhoto);

    /**
     * 根据用户标识删除数据
     * */
    public int deleteByUserId(Long userId);

    /**
     * 删除员工相册
     *
     * @param id 员工相册主键
     * @return 结果
     */
    public int deleteStaffPhotoById(Long id);

    /**
     * 批量删除员工相册
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStaffPhotoByIds(Long[] ids);
}
