package com.example.demo.service;

import com.example.demo.entity.User;


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

    User getUserTestById(int id);

    /**
     * @author He
     * @description 注册测试
     * @date 2019/4/20 19:57
     * @param user
     * @return java.lang.Boolean
     **/
    Boolean register(User user);

    /**
     * @author He
     * @description 登录测试
     * @date 2019/4/20 22:15
     * @param user
     * @return java.lang.String
     **/
    String login(User user);
}
