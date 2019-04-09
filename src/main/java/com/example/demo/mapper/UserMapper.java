package com.example.demo.mapper;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName UserMapper
 * @Author UserMapper
 * @Date 2019/2/23 21:44
 * @Version 1.0
 **/

@Repository
public interface UserMapper {

    User Sel(int id);

    int getUserSize();

}
