package com.ruoyi.data.service.impl;

import com.ruoyi.data.domain.User;
import com.ruoyi.data.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getById(Long userId) {
        return User.builder()
                .userName("测试")
                .id(1L)
                .build();
    }
}
