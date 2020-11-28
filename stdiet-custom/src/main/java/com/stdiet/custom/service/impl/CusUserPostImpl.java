package com.stdiet.custom.service.impl;

import com.stdiet.custom.domain.CusUserPost;
import com.stdiet.custom.mapper.CusUserPostMapper;
import com.stdiet.custom.service.ICusUserPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusUserPostImpl implements ICusUserPostService {

    @Autowired
    private CusUserPostMapper cusUserPostMapper;

    @Override
    public List<CusUserPost> selectAllCusUserPost() {
        return cusUserPostMapper.selectAllCusUserPost();
    }
}
