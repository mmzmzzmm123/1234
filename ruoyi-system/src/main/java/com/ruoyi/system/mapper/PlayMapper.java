package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.system.domain.dto.play.QueryPlayDTO;
import com.ruoyi.system.domain.vo.play.PlayTaskProgressVO;
import com.ruoyi.system.domain.vo.play.QueryPlayVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayMapper extends BaseMapper<Play> {


    Page<QueryPlayVO> selectPage(Page page, @Param("dto") QueryPlayDTO dto);

    List<PlayTaskProgressVO> selectTaskProgress(@Param("ids") List<String> ids);

}
