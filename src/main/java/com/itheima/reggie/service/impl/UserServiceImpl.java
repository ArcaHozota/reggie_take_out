package com.itheima.reggie.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserDao;
import com.itheima.reggie.service.UserService;

/**
 * @author Administrator
 * @date 2022-11-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
