package com.itheima.reggie.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;

/**
 * @author Administrator
 * @date 2022-11-29
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
