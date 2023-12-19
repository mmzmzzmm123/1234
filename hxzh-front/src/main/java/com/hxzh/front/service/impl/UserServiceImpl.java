package com.hxzh.front.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxzh.front.moudle.domain.User;
import com.hxzh.front.service.UserService;
import com.hxzh.front.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-12-20 13:20:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




