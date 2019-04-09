package com.example.demo.service.serviceImpl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
// 事务注解
@Transactional(rollbackFor = Exception.class)
// @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
// 简单解析：如果有事务，那么加入事务，没有的话新建一个； 串行化最高级隔离级别； 遇到异常回滚。
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User Sel(int id){
        return userMapper.Sel(id);
    }

    @Override
    public int getUserSize() {
        return userMapper.getUserSize();
    }
}
