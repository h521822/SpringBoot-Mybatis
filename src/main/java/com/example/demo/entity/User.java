package com.example.demo.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName User
 * @Author User
 * @Date 2019/2/23 19:48
 * @Version 1.0
 **/
@Data
public class User {
    private Integer id;
    private String userName;
    private String password;
    private String realName;

    private Date createDate;
    private String salt;
    private String email;


}
