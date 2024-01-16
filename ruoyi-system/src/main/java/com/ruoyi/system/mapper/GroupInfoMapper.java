package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.GroupInfo;
import com.ruoyi.system.domain.dto.GroupPageQueryDTO;
import com.ruoyi.system.domain.vo.GroupPageInfoVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 群基础信息(GroupInfo)表数据库访问层
 *
 * @author 朱少波
 * @since 2024-01-15 09:26:48
 */
@Repository
public interface GroupInfoMapper extends BaseMapper<GroupInfo> {

    Page<GroupPageInfoVO> page(Page page,@Param("dto") GroupPageQueryDTO dto);
}
