package com.ruoyi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.user.domain.PsyUserLikedConsult;
import com.ruoyi.user.vo.PsyUserLikedConsultVO;

import java.util.List;

/**
 * 咨询服务Mapper接口
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public interface PsyUserLikedConsultMapper extends BaseMapper<PsyUserLikedConsult>
{

    PsyUserLikedConsult getLiked(PsyUserLikedConsult psyConsultFocus);

    List<PsyUserLikedConsultVO> getLikes(Integer userId);

    int add(PsyUserLikedConsult psyConsultFocus);

    int del(PsyUserLikedConsult psyConsultFocus);

}
