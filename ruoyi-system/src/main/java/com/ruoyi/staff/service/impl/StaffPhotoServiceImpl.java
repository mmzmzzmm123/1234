package com.ruoyi.staff.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.staff.mapper.StaffPhotoMapper;
import com.ruoyi.staff.domain.StaffPhoto;
import com.ruoyi.staff.service.IStaffPhotoService;

/**
 * 员工相册Service业务层处理
 *
 * @author Lam
 * @date 2023-09-14
 */
@Service
@RequiredArgsConstructor
public class StaffPhotoServiceImpl implements IStaffPhotoService {

    private final StaffPhotoMapper staffPhotoMapper;

    /**
     * 查询员工相册
     *
     * @param id 员工相册主键
     * @return 员工相册
     */
    @Override
    public StaffPhoto selectStaffPhotoById(Long id) {
        return staffPhotoMapper.selectStaffPhotoById(id);
    }

    /**
     * 查询员工相册列表
     *
     * @param staffPhoto 员工相册
     * @return 员工相册
     */
    @Override
    public List<StaffPhoto> selectStaffPhotoList(StaffPhoto staffPhoto) {
        return staffPhotoMapper.selectStaffPhotoList(staffPhoto);
    }

    /**
     * 新增员工相册
     *
     * @param staffPhoto 员工相册
     * @return 结果
     */
    @Override
    public int insertStaffPhoto(StaffPhoto staffPhoto) {
        Date now = DateUtils.getNowDate();
        staffPhoto.setCreateTime(now)
                .setUpdateTime(now);
        return staffPhotoMapper.insertStaffPhoto(staffPhoto);
    }

    /**
     * 修改员工相册
     *
     * @param staffPhoto 员工相册
     * @return 结果
     */
    @Override
    public int updateStaffPhoto(StaffPhoto staffPhoto) {
        staffPhoto.setUpdateBy(SecurityUtils.getUsername())
                .setUpdateTime(DateUtils.getNowDate());
        return staffPhotoMapper.updateStaffPhoto(staffPhoto);
    }

    /**
     * 批量删除员工相册
     *
     * @param ids 需要删除的员工相册主键
     * @return 结果
     */
    @Override
    public int deleteStaffPhotoByIds(Long[] ids) {
        return staffPhotoMapper.deleteStaffPhotoByIds(ids);
    }

    /**
     * 删除员工相册信息
     *
     * @param id 员工相册主键
     * @return 结果
     */
    @Override
    public int deleteStaffPhotoById(Long id) {
        return staffPhotoMapper.deleteStaffPhotoById(id);
    }
}
