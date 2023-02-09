package com.ruoyi.contest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.contest.entity.Dept;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lsyonlygoddes
 * @since 2022-12-07
 */
public interface DeptMapper extends BaseMapper<Dept> {

    Dept selByDeptId(@Param("deptId") Long deptId);

}
