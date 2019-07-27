package com.springboot2.test1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot2.test1.model.User;

@Controller
@RequestMapping("/index")
public class IndexController {
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(User user,ModelMap map){
		System.out.println("IndexController-用户名："+user.getUsername()
			+",密码："+user.getPassword());
		map.put("username", user.getUsername());
		return "success";
	}
}
