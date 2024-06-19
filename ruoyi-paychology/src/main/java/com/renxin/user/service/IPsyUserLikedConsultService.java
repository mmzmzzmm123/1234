package com.renxin.user.service;

import com.renxin.user.domain.PsyUserLikedConsult;
import com.renxin.user.vo.PsyUserLikedConsultVO;

import java.util.List;

public interface IPsyUserLikedConsultService {

    PsyUserLikedConsult getLiked(PsyUserLikedConsult psyConsultFocus);

    List<PsyUserLikedConsultVO> getLikes(Integer userId);

    int add(PsyUserLikedConsult psyConsultFocus);

    int del(PsyUserLikedConsult psyConsultFocus);


}
