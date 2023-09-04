package com.ruoyi.user.service.impl;

import com.ruoyi.user.domain.PsyUserLikedConsult;
import com.ruoyi.user.mapper.PsyUserLikedConsultMapper;
import com.ruoyi.user.service.IPsyUserLikedConsultService;
import com.ruoyi.user.vo.PsyUserLikedConsultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PsyUserLikedConsultServiceImpl implements IPsyUserLikedConsultService {

    @Resource
    private PsyUserLikedConsultMapper psyConsultFocusMapper;

    @Override
    public PsyUserLikedConsult getLiked(PsyUserLikedConsult psyConsultFocus) {
        return psyConsultFocusMapper.getLiked(psyConsultFocus);
    }

    @Override
    public List<PsyUserLikedConsultVO> getLikes(Integer userId) {
        return psyConsultFocusMapper.getLikes(userId);
    }

    @Override
    public int add(PsyUserLikedConsult psyConsultFocus) {
        psyConsultFocus.setCreateTime(new Date());
        return psyConsultFocusMapper.add(psyConsultFocus);
    }

    @Override
    public int del(PsyUserLikedConsult psyConsultFocus) {
        return  psyConsultFocusMapper.del(psyConsultFocus);
    }
}
