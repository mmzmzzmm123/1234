package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.system.domain.dto.play.QueryPlayDTO;
import com.ruoyi.system.domain.dto.play.QueryPushDetailDTO;
import com.ruoyi.system.domain.vo.PlayConfusionStateVO;
import com.ruoyi.system.domain.vo.play.PlayGroupProgressVO;
import com.ruoyi.system.domain.vo.play.PlayTaskProgressVO;
import com.ruoyi.system.domain.vo.play.QueryPlayVO;
import com.ruoyi.system.domain.vo.play.QueryPushDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayMapper extends BaseMapper<Play> {


    Page<QueryPlayVO> selectPage(Page page, @Param("dto") QueryPlayDTO dto);

    List<PlayTaskProgressVO> selectTaskProgress(@Param("playIds") List<String> playIds);

    /**
     * 获取任务 群维度的统计数据
     * @param playIds
     * @return
     */
    List<PlayTaskProgressVO> selectTaskGroupProgress(@Param("playIds") List<String> playIds);

    List<PlayGroupProgressVO> selectGroupProgress(@Param("playId") String playId);

    Page<QueryPushDetailVO> selectPushDetailPage(Page page, @Param("dto") QueryPushDetailDTO dto);

    List<Play> selectIntoGroupList(@Param("state")Integer state,@Param("progress")Integer progress);

    Play selectPlayById(@Param("playId")String playId);

    /**
     * 查询需要修改混淆状态的剧本
     * @return
     */
    List<PlayConfusionStateVO> selectConfusionStateStatistics();
}
