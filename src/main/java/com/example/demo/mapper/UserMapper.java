package com.example.demo.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMapper
 * @Author UserMapper
 * @Date 2019/2/23 21:44
 * @Version 1.0
 **/

@Repository
public interface UserMapper extends BaseMapper<User> {

    User Sel(int id);

    int getUserSize();

}
