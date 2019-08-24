package com.springboot2.test5.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springboot2.test5.model.User;
import com.springboot2.test5.service.IndexService;



@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private IndexService indexService;
	
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping()
	public String index(HttpSession session){
		Subject subject = SecurityUtils.getSubject();
		String name = (String)subject.getPrincipal();
		System.out.println("subject:"+name);
		session.setAttribute("username", name);
		//System.out.println("==="+subject.getSession().getAttribute("username"));
		return "forward:/index/userList";
	}
	/**
	 * 登陆页面
	 * @return
	 */
	@RequestMapping(value="/loginIndex",method=RequestMethod.GET)
	public String loginIndex(){
		return "index";
	}
	/**
	 *  登陆
	 *  @PostMapping相当于@RequestMapping(method=RequestMethod.POST)
	 * @return
	 */
	@PostMapping("/login")
	public String login(String name,String password,String rememberMe
				,ModelMap modelMap){
	    boolean booRememberMe = false;
	    if("true".equals(rememberMe)){
	    	booRememberMe=true;
	    }
	   // System.out.println("===="+booRememberMe);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken usernamePasswordToken=new 
				UsernamePasswordToken(name,password,booRememberMe);
		
		try {
			subject.login(usernamePasswordToken);   //登录
		} catch (UnknownAccountException e) {
			modelMap.put("msg", "用户名不存在！");
			return "index";
		}catch (IncorrectCredentialsException ice) {
			modelMap.put("msg", "密码不正确！");
			return "index";
        }catch (LockedAccountException lae) {
            modelMap.put("msg", "账户已锁定！");
            return "index";
        } catch (ExcessiveAttemptsException eae) {
            modelMap.put("msg", "密码错误次数过多！");
        } catch (AuthenticationException ae) {
            modelMap.put("msg", "用户名或密码不正确！");
            return "index";
        }
		
		return "forward:/index";
	}
	/**
	 * 这边自定义logout
	 * shiro有默认logout,会自动清除相关信息，返回配置的登陆页面
	 * 用户登出
	 * @param 
	 * @return
	 */
    @RequestMapping("/logout")
    public String logout(ModelMap modelMap) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        modelMap.put("msg","安全退出！");
        return "index";
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
	 * @RequiresPermissions：shiro权限配置
	 * 如果没有权限的用户操作，会报错
	 * 解决方法1.捕获异常，后续进行相关提示或操作。
	 * 解决方法2.配置拦截  比如 /index/queryUser=perms[user:query];
	 * ShiroFilterFactoryBean设置没权限时的url setUnauthorizedUrl("/index/noperms")
	 * 这里使用了方法1  全局异常捕获处理
	 */
	@RequiresPermissions("user:query")
	@PostMapping("/queryUser")
	public String queryUser(User user,ModelMap modelMap){
	    List<User> userList = this.indexService.queryUserBy(user);
	   
	    modelMap.put("userList", userList);
		return "userList";
	}
	/**
	 * 
	 * @return
	 */
	@RequiresPermissions("user:add")
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
	@RequiresPermissions("user:update")
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
	@RequiresPermissions("user:delete")
	@GetMapping("/deleteUser")
	public String deleteUser(String id){
		this.indexService.deleteUser(id);
		return "redirect:/index/userList";
	}
	
	@RequestMapping("/noperms")
	public String noperms(String id){
		return "noPerms";
	}
}
