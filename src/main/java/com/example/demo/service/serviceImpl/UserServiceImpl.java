package com.example.demo.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.config.ShiroConfig;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
// 事务注解
@Transactional(rollbackFor = Exception.class)
// @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
// 简单解析：如果有事务，那么加入事务，没有的话新建一个； 串行化最高级隔离级别； 遇到异常回滚。
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private  UserMapper userMapper;


    @Override
    public User Sel(int id){
        return userMapper.Sel(id);
    }

    @Override
    public int getUserSize() {
        return userMapper.getUserSize();
    }

    @Override
    public User getUserTestById(int id) {
        return this.selectById(id);
    }

    /**
     * @author He
     * @description 注册测试
     * @date 2019/4/20 19:49
     * @param user
     * @return java.lang.Boolean
     **/
    @Override
    public Boolean register(User user) {
        // 注册时间
        user.setCreateDate(new Date());
        String salt = ShiroConfig.generateSalt(20);
        user.setPassword(ShiroConfig.encryptPassword("MD5", user.getPassword(), salt));
        // 保存盐，以用来登录时验证密码是否正确
        user.setSalt(salt);
        return this.insert(user);
    }

    /**
     * @author He
     * @description 登录测试
     * @date 2019/4/20 22:15
     * @param user
     * @return java.lang.String
     **/
    @Override
    public String login(User user) {
        Wrapper<User> userWrapper = new EntityWrapper();
        userWrapper.eq(User.ACCOUNT, user.getAccount());
        User ent = this.selectOne(userWrapper);
        // ServiceUtil.checkIsEntity(user);
        if (ent == null) {
            return "用户不存在！";
        }
        if (!StringUtils.equals(ent.getPassword(), ShiroConfig.encryptPassword("MD5", user.getPassword(), ent.getSalt()))) {
            return "密码错误！";
        }
        return "登陆成功";
    }
}
