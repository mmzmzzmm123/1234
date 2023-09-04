package com.ruoyi.user.service;

import com.ruoyi.user.domain.PsyUserLikedConsult;
import com.ruoyi.user.vo.PsyUserLikedConsultVO;

import java.util.List;

public interface IPsyUserLikedConsultService {

    PsyUserLikedConsult getLiked(PsyUserLikedConsult psyConsultFocus);

    List<PsyUserLikedConsultVO> getLikes(Integer userId);

    int add(PsyUserLikedConsult psyConsultFocus);

    int del(PsyUserLikedConsult psyConsultFocus);


}
