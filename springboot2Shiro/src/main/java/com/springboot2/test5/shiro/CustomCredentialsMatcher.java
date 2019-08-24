package com.springboot2.test5.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
/**
 * 自定义凭证匹配器
 * @author 
 *
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	    @Override
	    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
	    	UsernamePasswordToken utoken=(UsernamePasswordToken) token;
	        //获得用户输入的密码
	        String inPassword = new String(utoken.getPassword());
	        //获得数据库中的密码
	        String dbPassword=(String) info.getCredentials();
	        //进行密码的比对(可以采用加盐的方式去检验，这边直接明文匹配)
	        return this.equals(inPassword, dbPassword);
	    }

}
