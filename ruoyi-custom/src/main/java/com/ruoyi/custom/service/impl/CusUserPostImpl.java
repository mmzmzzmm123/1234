package com.ruoyi.custom.service.impl;

import com.ruoyi.custom.domain.CusUserPost;
import com.ruoyi.custom.mapper.CusUserPostMapper;
import com.ruoyi.custom.service.ICusUserPostService;
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
