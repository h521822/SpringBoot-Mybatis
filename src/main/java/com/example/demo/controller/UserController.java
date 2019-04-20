package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Author UserController
 * @Date 2019/2/23 21:42
 * @Version 1.0
 * spring boot + Mybatis整合  测试
 * 参考资料：https://blog.csdn.net/iku5200/article/details/82856621
 **/
@RestController
@RequestMapping("/testBoot")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser/{id}")
    public User GetUser(@PathVariable int id){
        return userService.Sel(id);
    }

    @RequestMapping("/getUserTest/{id}")
    public User getUserTest(@PathVariable int id){
        return userService.getUserTestById(id);
    }

    @RequestMapping("/getUserSize")
    public int getUserSize(){
        return userService.getUserSize();
    }

}
