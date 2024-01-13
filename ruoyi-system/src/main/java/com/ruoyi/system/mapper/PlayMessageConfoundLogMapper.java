package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.system.domain.PlayMessageConfoundLog;
import com.ruoyi.system.domain.dto.QueryConfoundLogDTO;
import com.ruoyi.system.domain.vo.QueryConfoundLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.uc.linkmaster.firegroup.domain.PlayMessageConfoundLog
 */
public interface PlayMessageConfoundLogMapper extends BaseMapper<PlayMessageConfoundLog> {

    List<PlayMessageConfoundLog> selectConfoundList(@Param("uuid") String uuid);

    List<PlayMessageConfoundLog> selectRetryingList();

    Page<QueryConfoundLogVO> page(Page page, @Param("dto") QueryConfoundLogDTO dto);


}




