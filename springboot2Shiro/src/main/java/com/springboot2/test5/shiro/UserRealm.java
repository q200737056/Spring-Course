package com.springboot2.test5.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.springboot2.test5.service.IndexService;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	private IndexService indexService;
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> stringSet = new HashSet<>();
        //这边直接赋权限了，正式项目中，从数据库查询该用户的角色（role），角色的权限(perm)
        if("admin".equals(username)){
        	stringSet.add("user:query");
            stringSet.add("user:update");
            stringSet.add("user:add");
            stringSet.add("user:delete");
        }else{
        	stringSet.add("user:query");
        }
        info.setStringPermissions(stringSet);
        return info;

	}
	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		
	        String userName = (String) token.getPrincipal();
	        //查询数据库
	        String userPwd = this.indexService.login(userName);
	     
	        if (userPwd == null) {
	            throw new UnknownAccountException();//用户名不存在
	        } 
	        return new SimpleAuthenticationInfo(userName, userPwd,getName());
	
	}

}
