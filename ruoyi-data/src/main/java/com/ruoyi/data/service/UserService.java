package com.ruoyi.data.service;

import com.ruoyi.data.domain.User;
import org.springframework.stereotype.Service;


public interface UserService {
    public User getById(Long userId);
}
