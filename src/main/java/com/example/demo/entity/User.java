package com.example.demo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * @ClassName User
 * @Author User
 * @Date 2019/2/23 19:48
 * @Version 1.0
 **/
@Data
//标记表名(此注解为必须，下面的字段注解可选)
@TableName("user")
public class User extends BaseEntity {

    /**
     * 账号
     **/
    //标记数据表中的column名
    @TableField("ACCOUNT")
    private String account;

    /**
     * 用户名
     **/
    @TableField("USER_NAME")
    private String userName;

    /**
     * 密码
     **/
    @TableField("PASSWORD")
    private String password;

    /**
     * 盐
     **/
    @TableField("SALT")
    // @TableField(exist = false)
    private String salt;

    /**
     * 邮箱
     **/
    //标记数据表中不存在的字段
    // @TableField(exist = false)
    @TableField("EMAIL")
    private String email;

    public static String USER_NAME = "USER_NAME";

    public static String ACCOUNT = "ACCOUNT";




}
