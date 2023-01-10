package jp.co.reggie.newdeal.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import jp.co.reggie.newdeal.entity.User;

/**
 * @author Administrator
 * @date 2022-11-29
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
