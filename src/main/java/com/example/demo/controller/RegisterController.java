package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName RegisterController
 * @Author RegisterController
 * @Date 2019/4/20 19:34
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/login")
public class RegisterController {

	@Autowired
	private UserService userService;

	/**
	 * @author He
	 * @description 登录页面跳转
	 * @date 2019/4/20 22:12
	 * @param
	 * @return java.lang.String
	 **/
	@RequestMapping(value = "/index")
	public String loginIndex() {
		return "registerPage";
	}

	/**
	 * @author He
	 * @description 登录
	 * @date 2019/4/20 22:13
	 * @param user
	 * @return java.lang.String
	 **/
	@ResponseBody
	@RequestMapping(value = "/login")
	public String login(User user) {
		return userService.login(user);
	}

	/**
	 * @author He
	 * @description 注册
	 * @date 2019/4/20 22:12
	 * @param user
	 * @return java.lang.Boolean
	 **/
	@ResponseBody
	@RequestMapping(value = "/register")
	public Boolean register(User user) {
		return userService.register(user);
	}



}
