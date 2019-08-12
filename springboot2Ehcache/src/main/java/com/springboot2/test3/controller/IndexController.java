package com.springboot2.test3.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot2.test3.model.User;
import com.springboot2.test3.service.IndexService;



@Controller
@RequestMapping("/index")
public class IndexController {
	
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
	
	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	@PostMapping("/queryUser")
	public String queryUser(User user,ModelMap modelMap){
		List<User> userList = this.indexService.queryUserById(user.getId()+"");
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
	@GetMapping("/toUpdate")
	public String toUpdate(User user,ModelMap modelMap){
		modelMap.put("user", user);
		return "updateuser";
	}
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	@PostMapping("/updateUser")
	public String updateUser(User user){
		this.indexService.updateUser(user);
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
