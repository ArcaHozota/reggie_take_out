package jp.co.reggie.newdeal.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.co.reggie.newdeal.entity.User;
import jp.co.reggie.newdeal.mapper.UserDao;
import jp.co.reggie.newdeal.service.UserService;

/**
 * @author Administrator
 * @date 2022-11-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
