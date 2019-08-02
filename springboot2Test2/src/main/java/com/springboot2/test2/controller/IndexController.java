package com.springboot2.test2.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot2.test2.model.User;
import com.springboot2.test2.service.IndexService;



@Controller
@RequestMapping("/index")
public class IndexController {
	//日志示例， springboot默认采用logback日志，一般最好都用slf4j日志门面创建，可以兼容多个日志，
	//即使把 logback 换成 log4j2，日志代码都不会受到影响
	//logback 详细配置可 查看 根目录下logback.xml
	private static Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private IndexService indexService;
	/**
	 * 首页登陆
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(){
		return "index";
	}
	/**
	 *  登陆
	 *  @PostMapping相当于@RequestMapping(method=RequestMethod.POST)
	 * @return
	 */
	@PostMapping("/login")
	public String login(User user,ModelMap modelMap,HttpSession session){
		String pass = this.indexService.login(user.getName());
		if(pass==null){
			modelMap.put("msg", "用户名不存在！");
			return "index";
		}else if(!user.getPassword().equals(pass)){
			modelMap.put("msg", "密码错误！");
			return "index";
		}
		log.info("{}_用户登陆",user.getName());
		session.setAttribute("username", user.getName());
		return "forward:/index/userList";
	}
	/**
	 * 列出 所有用户
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(ModelMap modelMap){
		
		List<User> userList = this.indexService.findUserList();
		modelMap.put("userList", userList);
		return "userList";
	}
	@GetMapping("/toAdd")
	public String toAdd(){
		return "adduser";
	}
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	@PostMapping("/addUser")
	public String addUser(User user){
		this.indexService.insertUser(user);
		// redirect 重定向
		return "redirect:/index/userList";
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@GetMapping("/deleteUser")
	public String deleteUser(String id){
		this.indexService.deleteUser(id);
		return "redirect:/index/userList";
	}
}
