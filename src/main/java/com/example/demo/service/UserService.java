package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public interface UserService {
    
    /**
     * @author He
     * @description 根据ID查询用户信息
     * @date 2019/2/24 22:05
     * @param id
     * @return com.example.demo.entity.User
     **/
    User Sel(int id);

    /**
     * @author He
     * @description 查询用户数量
     * @date 2019/2/24 22:05
     * @param
     * @return int
     **/
    int getUserSize();

    // @Autowired
    // UserMapper userMapper;
    //
    // public User Sel(int id){
    //     return userMapper.Sel(id);
    // }
    //
    // public int getUserSize() {
    //     return userMapper.getUserSize();
    // }
}
