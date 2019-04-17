package com.example.demo.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Author User
 * @Date 2019/2/23 19:48
 * @Version 1.0
 **/
@Data
//标记表名(此注解为必须，下面的字段注解可选)
@TableName("user")
public class User implements Serializable {
    //标记数据表主键
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    //标记数据表中的column名
    @TableField("userName")
    private String userName;
    @TableField("password")
    private String password;
    @TableField("realName")
    private String realName;

    // @TableField("CREATE_DATE")
    @TableField(exist = false)
    private Date createDate;
    // @TableField("SALT")
    @TableField(exist = false)
    private String salt;
    //标记数据表中不存在的字段
    @TableField(exist = false)
    private String email;


}
